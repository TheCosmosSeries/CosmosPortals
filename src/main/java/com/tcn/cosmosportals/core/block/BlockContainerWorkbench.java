package com.tcn.cosmosportals.core.block;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.block.CosmosEntityBlock;
import com.tcn.cosmosportals.core.blockentity.BlockEntityContainerWorkbench;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockContainerWorkbench extends CosmosEntityBlock {

	public BlockContainerWorkbench(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos posIn, BlockState stateIn) {
		return new BlockEntityContainerWorkbench(posIn, stateIn);
	}
	
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level levelIn, BlockState stateIn, BlockEntityType<T> entityTypeIn) {
		return createTicker(levelIn, entityTypeIn, ModRegistrationManager.BLOCK_ENTITY_TYPE_CONTAINER_WORKBENCH.get());
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level levelIn, BlockEntityType<T> entityTypeIn, BlockEntityType<? extends BlockEntityContainerWorkbench> entityIn) {
		return createTickerHelper(entityTypeIn, entityIn, BlockEntityContainerWorkbench::tick);
	}

	@Override
	public void attack(BlockState state, Level world, BlockPos pos, Player player) {
		BlockEntity tileEntity = world.getBlockEntity(pos);
		
		if (tileEntity instanceof BlockEntityContainerWorkbench blockEntity) {
			blockEntity.attack(state, world, pos, player);
		}
		super.attack(state, world, pos, player);
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level levelIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult hit) {
		BlockEntity tileEntity = levelIn.getBlockEntity(pos);
		
		if (tileEntity instanceof BlockEntityContainerWorkbench blockEntity) {
			return blockEntity.useItemOn(stack, state, levelIn, pos, playerIn, handIn, hit);
		}
		
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		
		if (tileEntity instanceof BlockEntityContainerWorkbench blockEntity) {
			blockEntity.onPlace(state, worldIn, pos, oldState, isMoving);
		}
		super.onPlace(state, worldIn, pos, oldState, isMoving);
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		
		if (tileEntity instanceof BlockEntityContainerWorkbench blockEntity) {
			blockEntity.setPlacedBy(worldIn, pos, state, placer, stack);
		}
		super.setPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public BlockState playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		
		if (tileEntity instanceof BlockEntityContainerWorkbench blockEntity) {
			return blockEntity.playerWillDestroy(worldIn, pos, state, player);
		}
		return super.playerWillDestroy(worldIn, pos, state, player);
	}
	
	@Override
	public void neighborChanged(BlockState state, Level levelIn, BlockPos posIn, Block blockIn, BlockPos fromPos, boolean isMoving) {
		BlockEntity tileEntity = levelIn.getBlockEntity(posIn);
		
		if (tileEntity instanceof BlockEntityContainerWorkbench blockEntity) {
			blockEntity.neighborChanged(state, levelIn, posIn, blockIn, fromPos, isMoving);
		}
		super.neighborChanged(state, levelIn, posIn, blockIn, fromPos, isMoving);
	}

	@Override
	public RenderShape getRenderShape(BlockState stateIn) {
		return RenderShape.MODEL;
	}
}