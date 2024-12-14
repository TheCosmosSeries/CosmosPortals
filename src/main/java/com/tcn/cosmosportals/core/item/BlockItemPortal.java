package com.tcn.cosmosportals.core.item;

import java.util.List;

import com.tcn.cosmoslibrary.common.lib.ComponentHelper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

public class BlockItemPortal extends BlockItem {

	public BlockItemPortal(Block blockIn, Properties propertiesIn) {
		super(blockIn, propertiesIn);
	}

	@Override
	public void appendHoverText(ItemStack stackIn, Item.TooltipContext context, List<Component> toolTipIn, TooltipFlag flagIn) {
		super.appendHoverText(stackIn, context, toolTipIn, flagIn);
		
		toolTipIn.add(ComponentHelper.getTooltipInfo("cosmosportals.block_info.portal"));
		toolTipIn.add(ComponentHelper.getTooltipLimit("cosmosportals.block_info.portal_one"));
		toolTipIn.add(ComponentHelper.getErrorText("cosmosportals.block_info.portal_two"));
	}
}