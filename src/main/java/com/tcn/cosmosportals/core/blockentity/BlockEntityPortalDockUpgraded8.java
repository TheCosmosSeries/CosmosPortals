package com.tcn.cosmosportals.core.blockentity;

import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmosportals.client.container.ContainerPortalDockUpgraded8;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityPortalDockUpgraded8 extends AbstractBlockEntityPortalDock {

	public BlockEntityPortalDockUpgraded8(BlockPos posIn, BlockState stateIn) {
		super(ModRegistrationManager.BLOCK_ENTITY_TYPE_PORTAL_DOCK_UPGRADED8.get(), posIn, stateIn, 7);
	}

	@Override
	public Component getDisplayName() {
		return ComponentHelper.title("cosmosportals.gui.dock_upgraded_eight");
	}
	
	@Override
	public AbstractContainerMenu createMenu(int idIn, Inventory playerInventoryIn, Player playerIn) {
		return new ContainerPortalDockUpgraded8(idIn, playerInventoryIn, this, ContainerLevelAccess.create(this.getLevel(), this.getBlockPos()), this.getBlockPos());
	}

	@Override
	public Component getName() {
		return ComponentHelper.title("cosmosportals.gui.dock_upgraded_eight");
	}
}