package com.tcn.cosmosportals.client.renderer;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tcn.cosmosportals.core.blockentity.BlockEntityContainerWorkbench;
import com.tcn.cosmosportals.core.management.ModConfigManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RendererContainerWorkbench implements BlockEntityRenderer<BlockEntityContainerWorkbench> {
	
	//private BlockEntityRendererProvider.Context context;

	public RendererContainerWorkbench(BlockEntityRendererProvider.Context contextIn) {
		//this.context = contextIn;
	}	

	@Override
	public void render(BlockEntityContainerWorkbench blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Minecraft minecraft = Minecraft.getInstance();
		LocalPlayer player = minecraft.player;
		BlockPos pos = player.blockPosition();
		BlockPos blockPos = blockEntity.getBlockPos();
		
		double distanceToPlayer = pos.distManhattan(blockPos);
		
		if (blockEntity.getLevel().getBlockState(blockPos.above()).isAir()) {
			if (distanceToPlayer <= ModConfigManager.getInstance().getLabelMaximumDistance()) {
				if (!blockEntity.getItem(1).isEmpty()) {
					ItemStack renderStack = blockEntity.getItem(1);

					poseStack.pushPose();
					Quaternionf rotation = Axis.XP.rotationDegrees(90);
					
					poseStack.translate(0.5F, 1.02F, 0.5F);
					poseStack.mulPose(rotation);
					
					Minecraft.getInstance().getItemRenderer().renderStatic(renderStack, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, blockEntity.getLevel(), 0);
					
					poseStack.popPose();
				}
			}
		}
	}
}