package com.tcn.cosmosportals.core.item;

import java.util.List;

import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

public class BlockItemPortalDockController8 extends BlockItem {

	public BlockItemPortalDockController8(Block blockIn, Properties propertiesIn) {
		super(blockIn, propertiesIn);
	}

	@Override
	public void appendHoverText(ItemStack stackIn, Item.TooltipContext context, List<Component> toolTipIn, TooltipFlag flagIn) {
		if (!ComponentHelper.isShiftKeyDown(Minecraft.getInstance())) {
			toolTipIn.add(ComponentHelper.getTooltipInfo("cosmosportals.block_info.dock_controller8"));
			
			if (ComponentHelper.displayShiftForDetail) {
				toolTipIn.add(ComponentHelper.shiftForMoreDetails());
			}
		} else {
			toolTipIn.add(ComponentHelper.getTooltipOne("cosmosportals.block_info.dock_controller8_one"));
			toolTipIn.add(ComponentHelper.getTooltipTwo("cosmosportals.block_info.dock_controller_two"));
			toolTipIn.add(ComponentHelper.getTooltipFour("cosmosportals.block_info.dock_controller_three"));
			
			toolTipIn.add(ComponentHelper.shiftForLessDetails());
		}
		super.appendHoverText(stackIn, context, toolTipIn, flagIn);
	}
}