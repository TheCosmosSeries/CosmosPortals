package com.tcn.cosmosportals.client.colour;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmosportals.core.item.ItemPortalContainer;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemColour implements ItemColor {

	@Override
	public int getColor(ItemStack stackIn, int itemLayerIn) {
		Item item = stackIn.getItem();
		
		if (item.equals(ModRegistrationManager.DIMENSION_CONTAINER_LINKED.get())) {
			ItemPortalContainer itemS = (ItemPortalContainer) item;
			
			if (stackIn.has(DataComponents.CUSTOM_DATA)) {
				CompoundTag stack_tag = stackIn.get(DataComponents.CUSTOM_DATA).copyTag();
				
				if (stack_tag.contains("nbt_data")) {
					CompoundTag nbt_data = stack_tag.getCompound("nbt_data");
					
					if (nbt_data.contains("dimension_data")) {
						CompoundTag dimension_data = nbt_data.getCompound("dimension_data");
						
						if (dimension_data.contains("colour")) {
							int colour = dimension_data.getInt("colour");
							
							if (nbt_data.contains("display_data")) {
								int colourS = itemS.getContainerDisplayColour(stackIn);
																
								if (itemLayerIn == 0) {
									if (colourS > 0) {
										return ComponentColour.opaque(colourS);
									} else {
										return ComponentColour.opaque(colour);
									}
								}
							}
						}
					}
				}
			} else {
				return ComponentColour.WHITE.decOpaque();
			}
		} else if (item.equals(ModRegistrationManager.PORTAL_FRAME.asItem())) {
			return ComponentColour.GRAY.decOpaque();
		} else if (item.equals(ModRegistrationManager.PORTAL_DOCK.asItem())) {
			if (itemLayerIn == 0) {
				return ComponentColour.GRAY.decOpaque();
			} else if (itemLayerIn == 1) {
				return ComponentColour.WHITE.decOpaque();
			}
		} else if (item.equals(ModRegistrationManager.PORTAL_DOCK_UPGRADED.asItem())) {
			if (itemLayerIn == 0) {
				return ComponentColour.GRAY.decOpaque();
			} else if (itemLayerIn == 1) {
				return ComponentColour.WHITE.decOpaque();
			} else if (itemLayerIn == 2) {
				return ComponentColour.WHITE.decOpaque();
			}
		} else if (item.equals(ModRegistrationManager.PORTAL_DOCK_UPGRADED8.asItem())) {
			if (itemLayerIn == 0) {
				return ComponentColour.GRAY.decOpaque();
			} else if (itemLayerIn == 1) {
				return ComponentColour.WHITE.decOpaque();
			} else if (itemLayerIn == 2) {
				return ComponentColour.WHITE.decOpaque();
			}
		}
		
		if (itemLayerIn == 0) {
			return ComponentColour.GRAY.decOpaque();
		} else {
			return ComponentColour.WHITE.decOpaque();
		}
	}
}