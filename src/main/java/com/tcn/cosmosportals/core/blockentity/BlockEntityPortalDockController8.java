package com.tcn.cosmosportals.core.blockentity;

import com.tcn.cosmoslibrary.common.interfaces.block.IBlockInteract;
import com.tcn.cosmoslibrary.common.interfaces.block.IBlockNotifier;
import com.tcn.cosmosportals.core.block.BlockPortalDockController8;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings({ "unused" })
public class BlockEntityPortalDockController8 extends BlockEntity implements IBlockNotifier, IBlockInteract {
	
	public int buttonTimer = 20;
	public int maxButtonTimer = 20;
	public boolean buttonPressed = false;

	private boolean linked = false;
	private BlockPos dockPos = BlockPos.ZERO;
	
	
	public BlockEntityPortalDockController8(BlockPos posIn, BlockState stateIn) {
		super(ModRegistrationManager.BLOCK_ENTITY_TYPE_PORTAL_DOCK_CONTROLLER8.get(), posIn, stateIn);
	}

	public void sendUpdates(boolean update) {
		if (this.getLevel() != null) {
			this.setChanged();
			BlockState state = this.getBlockState();
			
			this.getLevel().sendBlockUpdated(this.getBlockPos(), state, state, 3);
			
			if (update) {
				if (!this.getLevel().isClientSide()) {
					this.getLevel().setBlockAndUpdate(this.getBlockPos(), state.updateShape(Direction.DOWN, state, level, worldPosition, worldPosition));
				}
			}
		}
	}

	@Override
	public void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
		super.saveAdditional(compound, provider);
		
		compound.putInt("dockX", this.dockPos.getX());
		compound.putInt("dockY", this.dockPos.getY());
		compound.putInt("dockZ", this.dockPos.getZ());
		
		compound.putBoolean("linked", linked);
		
		compound.putBoolean("pressed", this.buttonPressed);
	}

	@Override
	public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
		super.loadAdditional(compound, provider);
		
		this.dockPos = new BlockPos(compound.getInt("dockX"), compound.getInt("dockY"), compound.getInt("dockZ"));
		
		this.linked = compound.getBoolean("linked");
		
		this.buttonPressed = compound.getBoolean("pressed");
	}
	
	public boolean setDockPos(BlockPos posIn) {
		if (posIn.distManhattan(this.getBlockPos()) < 16) {
			this.dockPos = posIn;
			this.setLinked(true);
			
			return true;
		} else {
			return false;
		}
	}
	
	public BlockPos getDockPos() {
		return this.dockPos;
	}

	public boolean isLinked() {
		return this.linked;
	}
	
	public void setLinked(boolean linked) {
		this.linked = linked;
		this.sendUpdates(true);
	}
	
	public boolean performLinkCheck() {
		BlockEntity entity = this.getLevel().getBlockEntity(this.dockPos);
		
		if (entity != null) {
			if (entity instanceof AbstractBlockEntityPortalDock) {
				return true;
			} else {
				this.setLinked(false);
				return false;
			}
		} else {
			this.setLinked(false);
			return false;
		}
	}
	
	//Set the data once it has been received. [NBT > TE]
	@Override
	public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
		this.loadAdditional(tag, provider);
	}
	
	//Retrieve the data to be stored. [TE > NBT]
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag, provider);
		return tag;
	}
	
	//Actually sends the data to the server. [NBT > SER]
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	//Method is called once packet has been received by the client. [SER > CLT]
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider provider) {
		super.onDataPacket(net, pkt, provider);
		CompoundTag tag_ = pkt.getTag();
		
		this.handleUpdateTag(tag_, provider);
	}

	@Override
	public void onLoad() { }

	public static void tick(Level levelIn, BlockPos posIn, BlockState stateIn, BlockEntityPortalDockController8 entityIn) {
		if (entityIn.buttonPressed) {
			if (entityIn.buttonTimer > 0) {
				entityIn.buttonTimer--;
			} else {
				entityIn.buttonPressed = false;
				entityIn.buttonTimer = entityIn.maxButtonTimer;
			}
		}
	}

	@Override
	public void attack(BlockState state, Level levelIn, BlockPos pos, Player player) { }

	public void pressButton(Level levelIn, Player playerIn, int buttonID) {
		if (this.isLinked()) {
			if (this.performLinkCheck()) {
				BlockEntity entity = levelIn.getBlockEntity(this.getDockPos());
				
				if (entity != null) {
					AbstractBlockEntityPortalDock dockEntity = (AbstractBlockEntityPortalDock) entity;
					
					if (!(levelIn.isClientSide())) {
						dockEntity.setCurrentSlot(buttonID);
						this.buttonPressed = true;
						dockEntity.sendUpdates(true);
					}
					
				    this.getLevel().playSound(playerIn, this.getBlockPos(), BlockSetType.STONE.buttonClickOn(), SoundSource.BLOCKS);
				} else {
					this.performLinkCheck();
				}
			}
		}
	}

	@Override
	public BlockState playerWillDestroy(Level levelIn, BlockPos posIn, BlockState stateIn, Player playerIn) { 
		return stateIn;
	}

	@Override
	public void setPlacedBy(Level levelIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) { }

	@Override
	public void onPlace(BlockState state, Level levelIn, BlockPos pos, BlockState oldState, boolean isMoving) { }
	
	@Override
	public void neighborChanged(BlockState state, Level levelIn, BlockPos posIn, Block blockIn, BlockPos fromPos, boolean isMoving) { }
	
	@Override
	public void setChanged() {
		super.setChanged();		
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stackIn, BlockState state, Level levelIn, BlockPos posIn, Player playerIn, InteractionHand handIn, BlockHitResult hit) {
		boolean success = false;
		
		if (!this.buttonPressed) {
			if (this.performLinkCheck()) {
				Vec3 hitLocation = hit.getLocation();
				
				double hitX = Math.round(((hitLocation.x() - posIn.getX()) * 16) * 10D) / 10D;
				double hitY = Math.round(((hitLocation.y() - posIn.getY()) * 16) * 10D) / 10D;
				double hitZ = Math.round(((hitLocation.z() - posIn.getZ()) * 16) * 10D) / 10D;
				
				//MATHS

				if (levelIn.getBlockState(posIn.north()).isAir()) { //NORTH
					if ((hitX >= 11 && hitX <= 15) && (hitY >= 11 & hitY <= 15) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= 6 && hitX < 10) && (hitY >= 11 & hitY <= 15) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 1 && hitX < 5) && (hitY >= 11 & hitY <= 15) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					} 
					
					if ((hitX >= 11 && hitX <= 15) && (hitY >= 6 & hitY <= 10) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					} if ((hitX >= 1 && hitX < 5) && (hitY >= 6 & hitY <= 10) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 4);
					} 
					
					if ((hitX >= 11 && hitX < 16) && (hitY >= 1 & hitY < 5) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 5);
					} if ((hitX >= 6 && hitX <= 10) && (hitY >= 1 & hitY < 5) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 6);
					} if ((hitX >= 1 && hitX <= 5) && (hitY >= 1 & hitY < 5) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 7);
					}
				}
				
				if (levelIn.getBlockState(posIn.south()).isAir()) {//SOUTH
					if ((hitX >= 1 && hitX <= 5) && (hitY >= 9 & hitY <= 15) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= 6 && hitX <= 10) && (hitY >= 9 & hitY <= 15) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 11 && hitX <= 15) && (hitY >= 9 & hitY <= 15) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					}
					
					if ((hitX >= 1 && hitX <= 5) && (hitY >= 6 & hitY <= 10) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					} if ((hitX >= 11 && hitX <= 15) && (hitY >= 6 & hitY <= 10) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 4);
					}
					
					if ((hitX >= 1 && hitX <= 5) && (hitY >= 1 & hitY <= 5) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 5);
					} if ((hitX >= 6 && hitX <= 10) && (hitY >= 1 & hitY <= 5) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 6);
					} if ((hitX >= 11 && hitX <= 15) && (hitY >= 1 & hitY <= 5) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 7);
					}
				} 

				if (levelIn.getBlockState(posIn.west()).isAir()) {//WEST
					if ((hitX >= -1 && hitX < 0) && (hitY >= 11 & hitY <= 15) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= -1 && hitX < 0) && (hitY >= 11 & hitY <= 15) && (hitZ >= 6 && hitZ <= 10)) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= -1 && hitX < 0) && (hitY >= 11 & hitY <= 15) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					} 
					
					if ((hitX >= -1 && hitX < 0) && (hitY >= 6 & hitY <= 10) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					} if ((hitX >= -1 && hitX < 0) && (hitY >= 6 & hitY <= 10) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 4);
					} 
					
					if ((hitX >= -1 && hitX < 0) && (hitY >= 1 & hitY <= 5) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 5);
					} if ((hitX >= -1 && hitX < 0) && (hitY >= 1 & hitY <= 5) && (hitZ >= 6 && hitZ <= 10)) {
						success = true;
						this.pressButton(levelIn, playerIn, 6);
					} if ((hitX >= -1 && hitX < 0) && (hitY >= 1 & hitY <= 5) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 7);
					}
				} 

				if (levelIn.getBlockState(posIn.east()).isAir()) { //EAST
					if ((hitX >= 16 && hitX <= 17) && (hitY >= 11 & hitY <= 15) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= 16 && hitX <= 17) && (hitY >= 11 & hitY <= 15) && (hitZ >= 6 && hitZ <= 10)) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 16 && hitX <= 17) && (hitY >= 11 & hitY <= 15) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					}
					
					if ((hitX >= 16 && hitX <= 17) && (hitY >= 6 & hitY <= 10) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					} if ((hitX >= 16 && hitX <= 17) && (hitY >= 6 & hitY <= 10) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 4);
					}
					
					if ((hitX >= 16 && hitX <= 17) && (hitY >= 1 & hitY <= 5) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 5);
					} if ((hitX >= 16 && hitX <= 17) && (hitY >= 1 & hitY <= 5) && (hitZ >= 6 && hitZ <= 10)) {
						success = true;
						this.pressButton(levelIn, playerIn, 6);
					} if ((hitX >= 16 && hitX <= 17) && (hitY >= 1 & hitY <= 5) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 7);
					}
				}
			}
		}
		
		return success ? ItemInteractionResult.sidedSuccess(levelIn.isClientSide()) : ItemInteractionResult.FAIL;
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level levelIn, BlockPos posIn, Player playerIn, BlockHitResult hit) {
		return InteractionResult.FAIL;
	}
}