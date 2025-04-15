package com.tcn.cosmosportals.core.blockentity;

import com.tcn.cosmoslibrary.common.interfaces.block.IBlockInteract;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BlockEntityPortalDockController4 extends AbstractBlockEntityPortalDockController implements IBlockInteract {
		
	public BlockEntityPortalDockController4(BlockPos posIn, BlockState stateIn) {
		super(ModRegistrationManager.BLOCK_ENTITY_TYPE_PORTAL_DOCK_CONTROLLER4.get(), posIn, stateIn);
	}
	
	@Override
	public void onLoad() { }

	public static void tick(Level levelIn, BlockPos posIn, BlockState stateIn, BlockEntityPortalDockController4 entityIn) {
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
					if ((hitX >= 9 && hitX <= 15) && (hitY >= 9 & hitY <= 15) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= 1 && hitX < 8) && (hitY >= 9 & hitY <= 15) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 9 && hitX < 16) && (hitY >= 1 & hitY < 8) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					} if ((hitX >= 1 && hitX <= 9) && (hitY >= 1 & hitY < 8) && ((hitZ >= -1 && hitZ < 0))) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					}
				}
				
				if (levelIn.getBlockState(posIn.south()).isAir()) {//SOUTH
					if ((hitX >= 1 && hitX <= 7) && (hitY >= 9 & hitY <= 15) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= 9 && hitX <= 15) && (hitY >= 9 & hitY <= 15) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 1 && hitX <= 7) && (hitY >= 1 & hitY <= 7) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					} if ((hitX >= 9 && hitX <= 15) && (hitY >= 1 & hitY <= 7) && (hitZ >= 16 && hitZ <= 17)) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					}
				}

				if (levelIn.getBlockState(posIn.west()).isAir()) {//WEST
					if ((hitX >= -1 && hitX < 0) && (hitY >= 9 & hitY <= 15) && (hitZ >= 1 && hitZ <= 7)) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= -1 && hitX < 0) && (hitY >= 9 & hitY <= 15) && (hitZ >= 9 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= -1 && hitX < 0) && (hitY >= 1 & hitY <= 7) && (hitZ >= 1 && hitZ <= 7)) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					} if ((hitX >= -1 && hitX < 0) && (hitY >= 1 & hitY <= 7) && (hitZ >= 9 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					}
				}

				if (levelIn.getBlockState(posIn.east()).isAir()) { //EAST
					if ((hitX >= 16 && hitX <= 17) && (hitY >= 9 & hitY <= 15) && (hitZ >= 9 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					}
					if ((hitX >= 16 && hitX <= 17) && (hitY >= 9 & hitY <= 15) && (hitZ >= 1 && hitZ <= 7)) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 16 && hitX <= 17) && (hitY >= 1 & hitY <= 7) && (hitZ >= 9 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					} if ((hitX >= 16 && hitX <= 17) && (hitY >= 1 & hitY <= 7) && (hitZ >= 1 && hitZ <= 7)) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
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