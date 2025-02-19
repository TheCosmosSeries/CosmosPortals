package com.tcn.cosmosportals.core.item;

import java.util.List;

import com.tcn.cosmoslibrary.common.chat.CosmosChatUtil;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmosportals.core.blockentity.AbstractBlockEntityPortalDock;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockController4;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockController8;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ItemCosmicWrench extends Item {

	public ItemCosmicWrench(Item.Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, context, tooltip, flagIn);
		
		if (!ComponentHelper.isShiftKeyDown(Minecraft.getInstance())) {
			tooltip.add(ComponentHelper.getTooltipInfo("cosmosportals.item_info.wrench"));
			
			if (ComponentHelper.displayShiftForDetail) {
				tooltip.add(ComponentHelper.shiftForMoreDetails());
			}
		} else {
			tooltip.add(ComponentHelper.getTooltipOne("cosmosportals.item_info.wrench_one"));
			tooltip.add(ComponentHelper.getTooltipTwo("cosmosportals.item_info.wrench_two"));
			tooltip.add(ComponentHelper.getTooltipThree("cosmosportals.item_info.wrench_four"));
			tooltip.add(ComponentHelper.getTooltipFour("cosmosportals.item_info.wrench_three"));
			
			tooltip.add(ComponentHelper.shiftForLessDetails());
		}
		
		if (stack.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag stackTag = stack.get(DataComponents.CUSTOM_DATA).copyTag();
			CompoundTag dockInfo = stackTag.getCompound("dockInfo");
			
			int X = dockInfo.getInt("dockX");
			int Y = dockInfo.getInt("dockY");
			int Z = dockInfo.getInt("dockZ");
		
			if (!ComponentHelper.isControlKeyDown(Minecraft.getInstance())) {
	
				if (ComponentHelper.displayCtrlForDetail) {
					tooltip.add(ComponentHelper.ctrlForMoreDetails());
				}
				
			} else {
				tooltip.add(ComponentHelper.style(ComponentColour.GRAY, "", "cosmosportals.item_info.wrench_info")
						.append(ComponentHelper.style(ComponentColour.GREEN, "bold", "[" + X + ", " + Y + ", " + Z + "]")));
				
				tooltip.add(ComponentHelper.ctrlForLessDetails());
			}
		}
	}

	@Override
	public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
		return false;
	}
	
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
		return true;
	}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		Player playerIn = context.getPlayer();
		
		BlockPos pos = context.getClickedPos();
		Level world = context.getLevel();
		BlockEntity entity = world.getBlockEntity(pos);

		if (playerIn.isShiftKeyDown()) {
			if (entity != null) {
				if (entity instanceof AbstractBlockEntityPortalDock blockEntity) {
					CompoundTag tag = new CompoundTag();
					CompoundTag dockInfo = new CompoundTag();
					
					dockInfo.putInt("dockX", blockEntity.getBlockPos().getX());
					dockInfo.putInt("dockY", blockEntity.getBlockPos().getY());
					dockInfo.putInt("dockZ", blockEntity.getBlockPos().getZ());
					
					tag.put("dockInfo", dockInfo);
					stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
					
					CosmosChatUtil.sendClientPlayerMessage(playerIn, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.item.use.wrench_one"));
					return InteractionResult.SUCCESS;
				}
				
				if (entity instanceof BlockEntityPortalDockController4 blockEntity) {
					if (stack.has(DataComponents.CUSTOM_DATA)) {
						CompoundTag stackTag = stack.get(DataComponents.CUSTOM_DATA).copyTag();
						CompoundTag dockInfo = stackTag.getCompound("dockInfo");
						
						int X = dockInfo.getInt("dockX");
						int Y = dockInfo.getInt("dockY");
						int Z = dockInfo.getInt("dockZ");
						
						BlockPos setPos = new BlockPos(X, Y, Z);
						
						if (!blockEntity.setDockPos(setPos)) {
							CosmosChatUtil.sendClientPlayerMessage(playerIn, ComponentHelper.style(ComponentColour.RED, "boldunderline", "cosmosportals.item.use.wrench_two"));
							return InteractionResult.FAIL;
						}

						CosmosChatUtil.sendClientPlayerMessage(playerIn, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.item.use.wrench_three"));
						return InteractionResult.SUCCESS;
					} else {
						CosmosChatUtil.sendClientPlayerMessage(playerIn, ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.item.use.wrench_four"));
						return InteractionResult.FAIL;
					}
				}

				if (entity instanceof BlockEntityPortalDockController8 blockEntity) {
					if (stack.has(DataComponents.CUSTOM_DATA)) {
						CompoundTag stackTag = stack.get(DataComponents.CUSTOM_DATA).copyTag();
						CompoundTag dockInfo = stackTag.getCompound("dockInfo");
						
						int X = dockInfo.getInt("dockX");
						int Y = dockInfo.getInt("dockY");
						int Z = dockInfo.getInt("dockZ");
						
						BlockPos setPos = new BlockPos(X, Y, Z);
						
						if (!blockEntity.setDockPos(setPos)) {
							CosmosChatUtil.sendClientPlayerMessage(playerIn, ComponentHelper.style(ComponentColour.RED, "boldunderline", "cosmosportals.item.use.wrench_two"));
							return InteractionResult.FAIL;
						}

						CosmosChatUtil.sendClientPlayerMessage(playerIn, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.item.use.wrench_three"));
						return InteractionResult.SUCCESS;
					} else {
						CosmosChatUtil.sendClientPlayerMessage(playerIn, ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.item.use.wrench_four"));
						return InteractionResult.FAIL;
					}
				}
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
		return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
	}
}