package com.tcn.cosmosportals.core.blockentity;

import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmosportals.client.container.ContainerPortalDockUpgraded4;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityPortalDockUpgraded4 extends AbstractBlockEntityPortalDock {

	public BlockEntityPortalDockUpgraded4(BlockPos posIn, BlockState stateIn) {
		super(ModRegistrationManager.BLOCK_ENTITY_TYPE_PORTAL_DOCK_UPGRADED.get(), posIn, stateIn, 3);
	}

	@Override
	public Component getDisplayName() {
		return ComponentHelper.title("cosmosportals.gui.dock_upgraded");
	}
	
	@Override
	public AbstractContainerMenu createMenu(int idIn, Inventory playerInventoryIn, Player playerIn) {
		return new ContainerPortalDockUpgraded4(idIn, playerInventoryIn, this, ContainerLevelAccess.create(this.getLevel(), this.getBlockPos()), this.getBlockPos());
	}

	@Override
	public Component getName() {
		return ComponentHelper.title("cosmosportals.gui.dock_upgraded");
	}
}