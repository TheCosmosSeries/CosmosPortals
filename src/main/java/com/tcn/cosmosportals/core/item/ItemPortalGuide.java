package com.tcn.cosmosportals.core.item;

import java.util.List;

import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmosportals.client.screen.ScreenItemGuide;

import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ItemPortalGuide extends Item {

	public ItemPortalGuide(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if (worldIn.isClientSide) {
			if (playerIn.getDisplayName().getString().equals("TheCosmicNebula_")) {
				this.openGUI(playerIn);
			} else {
				playerIn.sendSystemMessage(ComponentHelper.style(ComponentColour.RED, "This feature is a WIP, sorry for the inconvenience."));
			}
		}
		
		playerIn.swing(handIn);
		return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
	}

	@OnlyIn(Dist.CLIENT)
	public void openGUI(Player playerIn) {
		Minecraft.getInstance().setScreen(new ScreenItemGuide(true, playerIn.getUUID(), CosmosUtil.getStack(playerIn)));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		
		if (!ComponentHelper.isShiftKeyDown(Minecraft.getInstance())) {
			tooltip.add(ComponentHelper.getTooltipInfo("cosmosportals.item_info.guide"));
			
			if (ComponentHelper.displayShiftForDetail) {
				tooltip.add(ComponentHelper.shiftForMoreDetails());
			}
		} else {
			tooltip.add(ComponentHelper.getTooltipOne("cosmosportals.item_info.guide_one"));
			tooltip.add(ComponentHelper.getTooltipTwo("cosmosportals.item_info.guide_two"));
			
			tooltip.add(ComponentHelper.shiftForLessDetails());
		}
	}
	
	public static void setPage(ItemStack stackIn, int page) {
		if (stackIn.has(DataComponents.CUSTOM_DATA)) {
			CustomData data = stackIn.get(DataComponents.CUSTOM_DATA);
			CompoundTag compound = data.copyTag();
			
			compound.putInt("page", page);
			stackIn.set(DataComponents.CUSTOM_DATA, CustomData.of(compound));
		} else {
			CompoundTag compound = new CompoundTag();
			
			compound.putInt("page", page);

			stackIn.set(DataComponents.CUSTOM_DATA, CustomData.of(compound));
		}
	}
	
	public static int getPage(ItemStack stackIn) {
		if (stackIn.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag compound = stackIn.get(DataComponents.CUSTOM_DATA).copyTag();
			
			return compound.getInt("page");
		}
		
		return 0;
	}
	

	public static void setUIMode(ItemStack stackIn, EnumUIMode mode) {
		if (stackIn.has(DataComponents.CUSTOM_DATA)) {
			CustomData data = stackIn.get(DataComponents.CUSTOM_DATA);
			CompoundTag compound = data.copyTag();
			
			compound.putInt("mode", mode.getIndex());
			stackIn.set(DataComponents.CUSTOM_DATA, CustomData.of(compound));
		} else {
			CompoundTag compound = new CompoundTag();
			
			compound.putInt("mode", mode.getIndex());
			stackIn.set(DataComponents.CUSTOM_DATA, CustomData.of(compound));
		}
	}
	
	public static EnumUIMode getUIMode(ItemStack stackIn) {
		if (stackIn.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag compound = stackIn.get(DataComponents.CUSTOM_DATA).copyTag();
			
			return EnumUIMode.getStateFromIndex(compound.getInt("mode"));
		}
		
		return EnumUIMode.DARK;
	}
}
