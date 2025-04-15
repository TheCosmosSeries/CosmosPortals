package com.tcn.cosmosportals.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tcn.cosmoslibrary.client.renderer.CosmosRendererHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmosportals.core.block.BlockPortal;
import com.tcn.cosmosportals.core.blockentity.AbstractBlockEntityPortalDock;
import com.tcn.cosmosportals.core.management.ModConfigManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RendererPortalDock implements BlockEntityRenderer<AbstractBlockEntityPortalDock> {
	
	private BlockEntityRendererProvider.Context context;

	public RendererPortalDock(BlockEntityRendererProvider.Context contextIn) {
		this.context = contextIn;
	}	

	@SuppressWarnings("deprecation")
	@Override
	public void render(AbstractBlockEntityPortalDock entityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Minecraft minecraft = Minecraft.getInstance();
		LocalPlayer player = minecraft.player;
		BlockPos pos = player.blockPosition();
		BlockPos blockPos = entityIn.getBlockPos();
		
		double distanceToPlayer = pos.distManhattan(blockPos);
		
		if (distanceToPlayer <= ModConfigManager.getInstance().getLabelMaximumDistance()) {
			Font fontRenderer = this.context.getFont();
			Level world = entityIn.getLevel();
			int colour = entityIn.getDisplayColour();
			String humanName = entityIn.getContainerDisplayName();
	
			BlockState above = world.getBlockState(entityIn.getBlockPos().above());
			BlockState below = world.getBlockState(entityIn.getBlockPos().below());
	
			poseStack.pushPose();
			if (entityIn.renderLabel && entityIn.isPortalFormed && ModConfigManager.getInstance().getRenderPortalLabels()) {
				if (above.getBlock() instanceof BlockPortal) {
					Axis axis = above.getValue(BlockPortal.AXIS);
					poseStack.translate(0.5F, 1.5F, 0.5F);
					
					if (axis.equals(Axis.Z)) {
						//Facing EAST/WEST
						poseStack.pushPose();
						poseStack.translate(0.5F, 0.0F, 0.0F);
						
						if (entityIn.portalWidth > 0 && (entityIn.portalWidth & 1) == 0) {
							Direction dir = entityIn.findLongest();
							
							if (dir != null) {
								if (dir.equals(Direction.NORTH)) {
									poseStack.translate(0.0F, 0.0F, 0.5F);
								} else if (dir.equals(Direction.SOUTH)) {
									poseStack.translate(0.0F, 0.0F, -0.5F);
								}
							}
						}
						
						poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(90));
						if (!world.getBlockState(blockPos.above().east()).isSolid()) {
							CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, ComponentHelper.style(colour, humanName), bufferIn, combinedLightIn, true, false);
						}
						poseStack.popPose();
						
						poseStack.pushPose();
						poseStack.translate(-0.5F, 0.0F, 0.0F);

						if (entityIn.portalWidth > 0 && (entityIn.portalWidth & 1) == 0) {
							Direction dir = entityIn.findLongest();

							if (dir != null) {
								if (dir.equals(Direction.NORTH)) {
									poseStack.translate(0.0F, 0.0F, 0.5F);
								} else if (dir.equals(Direction.SOUTH)) {
									poseStack.translate(0.0F, 0.0F, -0.5F);
								}
							}
						}
						
						poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(-90));
						if (!world.getBlockState(blockPos.above().west()).isSolid()) {
							CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, ComponentHelper.style(colour, humanName), bufferIn, combinedLightIn, true, false);
						}
						poseStack.popPose();
					} else {
						//Facing NORTH/SOUTH
						poseStack.pushPose();
						poseStack.translate(0.0F, 0.0F, -0.5F);

						if (entityIn.portalWidth > 0 && (entityIn.portalWidth & 1) == 0) {
							Direction dir = entityIn.findLongest();

							if (dir != null) {
								if (dir.equals(Direction.EAST)) {
									poseStack.translate(0.5F, 0.0F, 0.0F);
								} else if (dir.equals(Direction.WEST)) {
									poseStack.translate(-0.5F, 0.0F, 0.0F);
								}
							}
						}
						
						if (!world.getBlockState(blockPos.above().north()).isSolid()) {
							CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, ComponentHelper.style(colour, humanName), bufferIn, combinedLightIn, true, false);
						}
						poseStack.popPose();
						
						poseStack.pushPose();
						poseStack.translate(0.0F, 0.0F, 0.5F);

						if (entityIn.portalWidth > 0 && (entityIn.portalWidth & 1) == 0) {
							Direction dir = entityIn.findLongest();

							if (dir != null) {
								if (dir.equals(Direction.EAST)) {
									poseStack.translate(0.5F, 0.0F, 0.0F);
								} else if (dir.equals(Direction.WEST)) {
									poseStack.translate(-0.5F, 0.0F, 0.0F);
								}
							}
						}
						
						poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(180));
						if (!world.getBlockState(blockPos.above().south()).isSolid()) {
							CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, ComponentHelper.style(colour, humanName), bufferIn, combinedLightIn, true, false);
						}
						poseStack.popPose();
					}
				} else if (below.getBlock() instanceof BlockPortal) {
					Axis axis = below.getValue(BlockPortal.AXIS);
		
					poseStack.translate(0.5F, -0.3F, 0.5F);
					if (axis.equals(Axis.Z)) {
						//Facing East
						poseStack.pushPose();
						poseStack.translate(0.5F, 0.0F, 0.0F);

						if (entityIn.portalWidth > 0 && (entityIn.portalWidth & 1) == 0) {
							Direction dir = entityIn.findLongest();
							
							if (dir != null) {
								if (dir.equals(Direction.NORTH)) {
									poseStack.translate(0.0F, 0.0F, 0.5F);
								} else if (dir.equals(Direction.SOUTH)) {
									poseStack.translate(0.0F, 0.0F, -0.5F);
								}
							}
						}
						
						poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(90));
						if (!world.getBlockState(blockPos.below().east()).isSolid()) {
							CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, ComponentHelper.style(colour, humanName), bufferIn, combinedLightIn, true, false);
						}
						poseStack.popPose();
						
						poseStack.pushPose();
						poseStack.translate(-0.5F, 0.0F, 0.0F);

						if (entityIn.portalWidth > 0 && (entityIn.portalWidth & 1) == 0) {
							Direction dir = entityIn.findLongest();
							
							if (dir != null) {
								if (dir.equals(Direction.NORTH)) {
									poseStack.translate(0.0F, 0.0F, 0.5F);
								} else if (dir.equals(Direction.SOUTH)) {
									poseStack.translate(0.0F, 0.0F, -0.5F);
								}
							}
						}
						
						poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(-90));
						if (!world.getBlockState(blockPos.below().west()).isSolid()) {
							CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, ComponentHelper.style(colour, humanName), bufferIn, combinedLightIn, true, false);
						}
						poseStack.popPose();
					} else {
						//Facing East
						poseStack.pushPose();
						poseStack.translate(0.0F, 0.0F, -0.5F);

						if (entityIn.portalWidth > 0 && (entityIn.portalWidth & 1) == 0) {
							Direction dir = entityIn.findLongest();

							if (dir != null) {
								if (dir.equals(Direction.EAST)) {
									poseStack.translate(0.5F, 0.0F, 0.0F);
								} else if (dir.equals(Direction.WEST)) {
									poseStack.translate(-0.5F, 0.0F, 0.0F);
								}
							}
						}
						
						if (!world.getBlockState(blockPos.below().north()).isSolid()) {
							CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, ComponentHelper.style(colour, humanName), bufferIn, combinedLightIn, true, false);
						}
						poseStack.popPose();
						
						poseStack.pushPose();
						poseStack.translate(0.0F, 0.0F, 0.5F);

						if (entityIn.portalWidth > 0 && (entityIn.portalWidth & 1) == 0) {
							Direction dir = entityIn.findLongest();

							if (dir != null) {
								if (dir.equals(Direction.EAST)) {
									poseStack.translate(0.5F, 0.0F, 0.0F);
								} else if (dir.equals(Direction.WEST)) {
									poseStack.translate(-0.5F, 0.0F, 0.0F);
								}
							}
						}
						
						poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(180));
						if (!world.getBlockState(blockPos.below().south()).isSolid()) {
							CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, ComponentHelper.style(colour, humanName), bufferIn, combinedLightIn, true, false);
						}
						poseStack.popPose();
					}
				}
			}	
			poseStack.popPose();
		}
	}
}