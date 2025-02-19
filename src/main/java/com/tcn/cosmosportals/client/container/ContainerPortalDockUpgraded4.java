package com.tcn.cosmosportals.client.container;

import com.tcn.cosmoslibrary.client.container.CosmosContainerMenuBlockEntity;
import com.tcn.cosmoslibrary.client.container.slot.SlotSpecifiedItem;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ContainerPortalDockUpgraded4 extends CosmosContainerMenuBlockEntity {

	private int START_INDEX = 0;
	private int END_INDEX = 3;

	public ContainerPortalDockUpgraded4(int indexIn, Inventory playerInventoryIn, FriendlyByteBuf extraData) {
		this(indexIn, playerInventoryIn, new SimpleContainer(4), ContainerLevelAccess.NULL, extraData.readBlockPos());
	}

	public ContainerPortalDockUpgraded4(int indexIn, Inventory playerInventoryIn, Container contentsIn, ContainerLevelAccess accessIn, BlockPos posIn) {
		super(ModRegistrationManager.MENU_TYPE_PORTAL_DOCK_UPGRADED.get(), indexIn, playerInventoryIn, accessIn, posIn);
		
		/** - @ContainerSlots - */
		this.addSlot(new SlotSpecifiedItem(contentsIn, 0, 146, 45, ModRegistrationManager.DIMENSION_CONTAINER_LINKED.get(), 1) {
			@Override
			public void setChanged() {
				super.setChanged();
				ContainerPortalDockUpgraded4.this.slotsChanged(this.container);
				this.container.setChanged();
				contentsIn.setChanged();
			}
		});
		
		this.addSlot(new SlotSpecifiedItem(contentsIn, 1, 146, 67, ModRegistrationManager.DIMENSION_CONTAINER_LINKED.get(), 1) {
			@Override
			public void setChanged() {
				super.setChanged();
				ContainerPortalDockUpgraded4.this.slotsChanged(this.container);
				this.container.setChanged();
				contentsIn.setChanged();
			}
		});
		
		this.addSlot(new SlotSpecifiedItem(contentsIn, 2, 146, 89, ModRegistrationManager.DIMENSION_CONTAINER_LINKED.get(), 1) {
			@Override
			public void setChanged() {
				super.setChanged();
				ContainerPortalDockUpgraded4.this.slotsChanged(this.container);
				this.container.setChanged();
				contentsIn.setChanged();
			}
		});
		
		this.addSlot(new SlotSpecifiedItem(contentsIn, 3, 146, 111, ModRegistrationManager.DIMENSION_CONTAINER_LINKED.get(), 1) {
			@Override
			public void setChanged() {
				super.setChanged();
				ContainerPortalDockUpgraded4.this.slotsChanged(this.container);
				this.container.setChanged();
				contentsIn.setChanged();
			}
		});
		
		/** @Inventory */
		for (int k = 0; k < 3; ++k) {
			for (int i1 = 0; i1 < 9; ++i1) {
				this.addSlot(new Slot(playerInventoryIn, i1 + k * 9 + 9, 10 + i1 * 18, 176 + k * 18));
			}
		}

		/** @Actionbar */
		for (int l = 0; l < 9; ++l) {
			this.addSlot(new Slot(playerInventoryIn, l, 10 + l * 18, 234));
		}
	}
	
	@Override
	public void setItem(int i, int j, ItemStack stackIn) {
		super.setItem(i, j, stackIn);
		this.broadcastChanges();
	}
	
	@Override
	public void addSlotListener(ContainerListener listenerIn) {
		super.addSlotListener(listenerIn);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void removeSlotListener(ContainerListener listenerIn) {
		super.removeSlotListener(listenerIn);
	}

	@Override
	public void slotsChanged(Container inventoryIn) {
		super.slotsChanged(inventoryIn);
		this.broadcastChanges();
	}
	
	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
	}

	@Override
	public boolean stillValid(Player playerIn) {		
		return stillValid(this.access, playerIn, ModRegistrationManager.PORTAL_DOCK_UPGRADED.get());
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int indexIn) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(indexIn);

		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			
			if (indexIn >= this.START_INDEX && indexIn <= this.END_INDEX) {
				if (!this.moveItemStackTo(itemstack1, this.END_INDEX + 1, this.slots.size() - 9, false)) {
					if (!this.moveItemStackTo(itemstack1, this.slots.size() - 9, this.slots.size(), false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (indexIn > this.END_INDEX && indexIn <= this.slots.size() - 9) {
				if (itemstack.getItem().equals(ModRegistrationManager.DIMENSION_CONTAINER_LINKED.get())) {
					if (!this.moveItemStackTo(itemstack1, this.START_INDEX, this.END_INDEX + 1, false)) {
						if (!this.moveItemStackTo(itemstack1, this.slots.size() - 9, this.slots.size(), false)) {
							return ItemStack.EMPTY;
						}
					}
				} else {
					if (!this.moveItemStackTo(itemstack1, this.slots.size() - 9, this.slots.size(), false)) {
						return ItemStack.EMPTY;
					}
				}
			} else {
				if (itemstack.getItem().equals(ModRegistrationManager.DIMENSION_CONTAINER_LINKED.get())) {
					if (!this.moveItemStackTo(itemstack1, this.START_INDEX, this.END_INDEX + 1, false)) {
						if (!this.moveItemStackTo(itemstack1, this.END_INDEX + 1, this.slots.size() - 9, false)) {
							return ItemStack.EMPTY;
						}
					}
				} else {
					if (!this.moveItemStackTo(itemstack1, this.END_INDEX + 1, this.slots.size() - 9, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			
			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return !itemstack.isEmpty() ? itemstack : ItemStack.EMPTY;
	}
}