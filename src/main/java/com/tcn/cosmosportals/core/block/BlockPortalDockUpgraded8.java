package com.tcn.cosmosportals.core.block;

import javax.annotation.Nullable;

import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockUpgraded8;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPortalDockUpgraded8 extends AbstractBlockPortalDock {

	public BlockPortalDockUpgraded8(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos posIn, BlockState stateIn) {
		return new BlockEntityPortalDockUpgraded8(posIn, stateIn);
	}
	
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level levelIn, BlockState stateIn, BlockEntityType<T> entityTypeIn) {
		return createTicker(levelIn, entityTypeIn, ModRegistrationManager.BLOCK_ENTITY_TYPE_PORTAL_DOCK_UPGRADED8.get());
	}
}