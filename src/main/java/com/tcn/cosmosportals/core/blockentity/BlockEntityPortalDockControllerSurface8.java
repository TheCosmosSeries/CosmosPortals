package com.tcn.cosmosportals.core.blockentity;

import com.tcn.cosmoslibrary.common.interfaces.block.IBlockInteract;
import com.tcn.cosmoslibrary.common.interfaces.block.IBlockNotifier;
import com.tcn.cosmosportals.core.block.BlockPortalDockControllerSurface8;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class BlockEntityPortalDockControllerSurface8 extends AbstractBlockEntityPortalDockController implements IBlockNotifier, IBlockInteract {
		
	public BlockEntityPortalDockControllerSurface8(BlockPos posIn, BlockState stateIn) {
		super(ModRegistrationManager.BLOCK_ENTITY_TYPE_PORTAL_DOCK_CONTROLLER_SURFACE8.get(), posIn, stateIn);
	}

	@Override
	public void onLoad() { }

	public static void tick(Level levelIn, BlockPos posIn, BlockState stateIn, BlockEntityPortalDockControllerSurface8 entityIn) {
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
				if (state.getValue(BlockPortalDockControllerSurface8.FACING).equals(Direction.NORTH)) { //NORTH
					if ((hitX >= 1 && hitX <= 4) && (hitY >= 11 & hitY <= 15) && ((hitZ >= -1 && hitZ <= 2))) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= 6 && hitX <= 10) && (hitY >= 11 & hitY <= 15) && ((hitZ >= -1 && hitZ <= 2))) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 11 && hitX <= 15) && (hitY >= 11 & hitY <= 15) && ((hitZ >= -1 && hitZ <= 2))) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					} 
					
					if ((hitX >= 1 && hitX <= 4) && (hitY >= 6 & hitY <= 10) && ((hitZ >= -1 && hitZ <= 2))) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					} if ((hitX >= 11 && hitX <= 15) && (hitY >= 6 & hitY <= 10) && ((hitZ >= -1 && hitZ <= 2))) {
						success = true;
						this.pressButton(levelIn, playerIn, 4);
					} 
					
					if ((hitX >= 1 && hitX <= 4) && (hitY >= 1 & hitY <= 5) && ((hitZ >= -1 && hitZ <= 2))) {
						success = true;
						this.pressButton(levelIn, playerIn, 5);
					} if ((hitX >= 6 && hitX <= 10) && (hitY >= 1 & hitY <= 4) && ((hitZ >= -1 && hitZ <= 2))) {
						success = true;
						this.pressButton(levelIn, playerIn, 6);
					} if ((hitX >= 11 && hitX <= 15) && (hitY >= 1 & hitY <= 4) && ((hitZ >= -1 && hitZ <= 2))) {
						success = true;
						this.pressButton(levelIn, playerIn, 7);
					}
				}
				
				if (state.getValue(BlockPortalDockControllerSurface8.FACING).equals(Direction.SOUTH)) {//SOUTH
					if ((hitX >= 11 && hitX <= 15) && (hitY >= 9 & hitY <= 15) && (hitZ >= 14 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= 6 && hitX <= 10) && (hitY >= 9 & hitY <= 15) && (hitZ >= 14 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 1 && hitX <= 5) && (hitY >= 9 & hitY <= 15) && (hitZ >= 14 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					}
					
					if ((hitX >= 11 && hitX <= 15) && (hitY >= 6 & hitY <= 10) && (hitZ >= 14 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					} if ((hitX >= 1 && hitX <= 5) && (hitY >= 6 & hitY <= 10) && (hitZ >= 14 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 4);
					}
					
					if ((hitX >= 11 && hitX <= 15) && (hitY >= 1 & hitY <= 5) && (hitZ >= 14 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 5);
					} if ((hitX >= 6 && hitX <= 10) && (hitY >= 1 & hitY <= 5) && (hitZ >= 14 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 6);
					} if ((hitX >= 1 && hitX <= 5) && (hitY >= 1 & hitY <= 5) && (hitZ >= 14 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 7);
					}
				}

				if (state.getValue(BlockPortalDockControllerSurface8.FACING).equals(Direction.WEST)) {//WEST
					if ((hitX >= 1 && hitX <= 2) && (hitY >= 11 & hitY <= 15) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= 1 && hitX <= 2) && (hitY >= 11 & hitY <= 15) && (hitZ >= 6 && hitZ <= 10)) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 1 && hitX <= 2) && (hitY >= 11 & hitY <= 15) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					} 
					
					if ((hitX >= 1 && hitX <= 2) && (hitY >= 6 & hitY <= 10) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					} if ((hitX >= 1 && hitX <= 2) && (hitY >= 6 & hitY <= 10) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 4);
					} 
					
					if ((hitX >= 1 && hitX <= 2) && (hitY >= 1 & hitY <= 5) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 5);
					} if ((hitX >= 1 && hitX <= 2) && (hitY >= 1 & hitY <= 5) && (hitZ >= 6 && hitZ <= 10)) {
						success = true;
						this.pressButton(levelIn, playerIn, 6);
					} if ((hitX >= 1 && hitX <= 2) && (hitY >= 1 & hitY <= 5) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 7);
					}
				}

				if (state.getValue(BlockPortalDockControllerSurface8.FACING).equals(Direction.EAST)) { //EAST
					if ((hitX >= 14 && hitX <= 15) && (hitY >= 11 & hitY <= 15) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 0);
					} if ((hitX >= 14 && hitX <= 15) && (hitY >= 11 & hitY <= 15) && (hitZ >= 6 && hitZ <= 10)) {
						success = true;
						this.pressButton(levelIn, playerIn, 1);
					} if ((hitX >= 14 && hitX <= 15) && (hitY >= 11 & hitY <= 15) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 2);
					}
					
					if ((hitX >= 14 && hitX <= 15) && (hitY >= 6 & hitY <= 10) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 3);
					} if ((hitX >= 14 && hitX <= 15) && (hitY >= 6 & hitY <= 10) && (hitZ >= 11 && hitZ <= 15)) {
						success = true;
						this.pressButton(levelIn, playerIn, 4);
					}
					
					if ((hitX >= 14 && hitX <= 15) && (hitY >= 1 & hitY <= 5) && (hitZ >= 1 && hitZ <= 5)) {
						success = true;
						this.pressButton(levelIn, playerIn, 5);
					} if ((hitX >= 14 && hitX <= 15) && (hitY >= 1 & hitY <= 5) && (hitZ >= 6 && hitZ <= 10)) {
						success = true;
						this.pressButton(levelIn, playerIn, 6);
					} if ((hitX >= 14 && hitX <= 15) && (hitY >= 1 & hitY <= 5) && (hitZ >= 11 && hitZ <= 15)) {
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