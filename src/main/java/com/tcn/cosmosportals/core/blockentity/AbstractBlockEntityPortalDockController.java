package com.tcn.cosmosportals.core.blockentity;

import com.tcn.cosmoslibrary.common.interfaces.block.IBlockNotifier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class AbstractBlockEntityPortalDockController extends BlockEntity implements IBlockNotifier {
	
	public int buttonTimer = 20;
	public int maxButtonTimer = 20;
	public boolean buttonPressed = false;

	private boolean linked = false;
	private BlockPos dockPos = BlockPos.ZERO;
	
	
	public AbstractBlockEntityPortalDockController(BlockEntityType<?> typeIn, BlockPos posIn, BlockState stateIn) {
		super(typeIn, posIn, stateIn);
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
		BlockEntity testEntity = this.getLevel().getBlockEntity(this.dockPos);
		
		if (testEntity != null) {
			if (testEntity instanceof AbstractBlockEntityPortalDock) {
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
	
}