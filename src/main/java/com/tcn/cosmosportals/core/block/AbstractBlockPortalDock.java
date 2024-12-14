package com.tcn.cosmosportals.core.block;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.block.CosmosEntityBlock;
import com.tcn.cosmosportals.core.blockentity.AbstractBlockEntityPortalDock;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class AbstractBlockPortalDock extends CosmosEntityBlock implements PortalFrameBlock {

	public AbstractBlockPortalDock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos posIn, BlockState stateIn) {
		return null;
	}
	
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level levelIn, BlockState stateIn, BlockEntityType<T> entityTypeIn) {
		return null;
	}
	
	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level levelIn, BlockEntityType<T> entityTypeIn, BlockEntityType<? extends AbstractBlockEntityPortalDock> entityIn) {
		return createTickerHelper(entityTypeIn, entityIn, AbstractBlockEntityPortalDock::tick);
	}

	@Override
	public void attack(BlockState state, Level world, BlockPos pos, Player player) {
		BlockEntity tileEntity = world.getBlockEntity(pos);
		
		if (tileEntity instanceof AbstractBlockEntityPortalDock) {
			((AbstractBlockEntityPortalDock) tileEntity).attack(state, world, pos, player);
		}
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stackIn, BlockState state, Level levelIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult hit) {
		BlockEntity tileEntity = levelIn.getBlockEntity(pos);
		
		if (tileEntity instanceof AbstractBlockEntityPortalDock) {
			return ((AbstractBlockEntityPortalDock) tileEntity).useItemOn(stackIn, state, levelIn, pos, playerIn, handIn, hit);
		}
		
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level levelIn, BlockPos pos, Player playerIn, BlockHitResult hit) {
		BlockEntity tileEntity = levelIn.getBlockEntity(pos);
		
		if (tileEntity instanceof AbstractBlockEntityPortalDock) {
			return ((AbstractBlockEntityPortalDock) tileEntity).useWithoutItem(state, levelIn, pos, playerIn, hit);
		}
		
		return InteractionResult.PASS;
	}

	@Override
	public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		
		if (tileEntity instanceof AbstractBlockEntityPortalDock) {
			((AbstractBlockEntityPortalDock) tileEntity).onPlace(state, worldIn, pos, oldState, isMoving);
		}
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		
		if (tileEntity instanceof AbstractBlockEntityPortalDock) {
			((AbstractBlockEntityPortalDock) tileEntity).setPlacedBy(worldIn, pos, state, placer, stack);
		}
	}

	@Override
	public BlockState playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		
		if (tileEntity instanceof AbstractBlockEntityPortalDock) {
			return ((AbstractBlockEntityPortalDock) tileEntity).playerWillDestroy(worldIn, pos, state, player);
		}
		
		return this.defaultBlockState();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, Level levelIn, BlockPos posIn, RandomSource randIn) {
		BlockEntity tileEntity = levelIn.getBlockEntity(posIn);
		
		if (tileEntity instanceof AbstractBlockEntityPortalDock) {
			((AbstractBlockEntityPortalDock) tileEntity).animateTick(stateIn, levelIn, posIn, randIn);
		}
	}
	
	@Override
	public void neighborChanged(BlockState state, Level levelIn, BlockPos posIn, Block blockIn, BlockPos fromPos, boolean isMoving) {
		BlockEntity tileEntity = levelIn.getBlockEntity(posIn);
		
		if (tileEntity instanceof AbstractBlockEntityPortalDock) {
			((AbstractBlockEntityPortalDock) tileEntity).neighborChanged(state, levelIn, posIn, blockIn, fromPos, isMoving);
		}
	}

	@Override
	public RenderShape getRenderShape(BlockState stateIn) {
		return RenderShape.MODEL;
	}
}