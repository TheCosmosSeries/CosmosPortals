package com.tcn.cosmosportals.client.renderer;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tcn.cosmoslibrary.client.renderer.lib.CosmosRendererHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.MathHelper;
import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.client.renderer.model.DockButtonModel4;
import com.tcn.cosmosportals.core.blockentity.AbstractBlockEntityPortalDock;
import com.tcn.cosmosportals.core.blockentity.BlockEntityDockController;
import com.tcn.cosmosportals.core.item.ItemCosmicWrench;
import com.tcn.cosmosportals.core.management.ModConfigManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RendererDockController8 implements BlockEntityRenderer<BlockEntityDockController> {
	
	public static final ResourceLocation BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "textures/model/dock_controller_button.png");
	
	private BlockEntityRendererProvider.Context context;

	private Model buttonModel = new DockButtonModel4();
	
	public RendererDockController8(BlockEntityRendererProvider.Context contextIn) {
		this.context = contextIn;
	}	

	@Override
	public void render(BlockEntityDockController entityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		Minecraft minecraft = Minecraft.getInstance();
		Font fontRenderer = this.context.getFont();
		
		LocalPlayer player = minecraft.player;
		Level level = entityIn.getLevel();
		
		BlockPos pos = player.blockPosition();
		BlockPos entityPos = entityIn.getBlockPos();
		double distanceToPlayer = pos.distManhattan(entityPos);
		
		BlockEntity testEntity = level.getBlockEntity(entityIn.getDockPos());
		
		
		BlockState north = level.getBlockState(entityIn.getBlockPos().north(1));
		BlockState east = level.getBlockState(entityIn.getBlockPos().east(1));
		BlockState south = level.getBlockState(entityIn.getBlockPos().south(1));
		BlockState west = level.getBlockState(entityIn.getBlockPos().west(1));

		int g = (int) (ComponentColour.GRAY.dec() / 255F);
		int lg = (int) (120 / 255F);
		
		int col1 = 0; int col2 = 0; int col3 = 0; int col4 = 0;
		
		if (entityIn.isLinked()) {
			AbstractBlockEntityPortalDock dockEntity = (AbstractBlockEntityPortalDock) testEntity;
			
			if (dockEntity != null) {
				ComponentColour[] colours = dockEntity.getCustomColours(true);
				//int[] coloursItem = new int[] { dockEntity.getItem(0), dockEntity.getItem(0), dockEntity.getItem(0), dockEntity.getItem(0) };
				
				ComponentColour colour0 = colours[0];
				ComponentColour colour1 = colours[1];
				ComponentColour colour2 = colours[2];
				ComponentColour colour3 = colours[3];
				
				col1 = dockEntity.getMaxSlotIndex() >= 0 ? (colour0 == ComponentColour.EMPTY ? lg : colour0.dec()) : g;
				col2 = dockEntity.getMaxSlotIndex() >= 0 ? (colour1 == ComponentColour.EMPTY ? lg : colour0.dec()) : g;
				col3 = dockEntity.getMaxSlotIndex() >= 0 ? (colour2 == ComponentColour.EMPTY ? lg : colour0.dec()) : g;
				col4 = dockEntity.getMaxSlotIndex() >= 0 ? (colour3 == ComponentColour.EMPTY ? lg : colour0.dec()) : g;
												
				if (player != null) {
					if (distanceToPlayer <= ModConfigManager.getInstance().getLabelMaximumDistance()) {
						if (MathHelper.isPlayerLookingAt(player, entityPos, false)) {
							ItemStack playerStack = player.getItemInHand(InteractionHand.MAIN_HAND);
							
							if (playerStack.getItem() instanceof ItemCosmicWrench) {
	
								poseStack.pushPose();
								
								RenderSystem.disableBlend();
								RenderSystem.disableDepthTest();
								RenderSystem.depthMask(false);
								
					            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					            GL11.glEnable(GL11.GL_LINE_SMOOTH);
					            
					            int transX = MathHelper.offsetBlockPos(entityPos, dockEntity.getBlockPos()).getX();
					            int transY = MathHelper.offsetBlockPos(entityPos, dockEntity.getBlockPos()).getY();
					            int transZ = MathHelper.offsetBlockPos(entityPos, dockEntity.getBlockPos()).getZ();
					            
								poseStack.translate(-transX, -transY, -transZ);
								
								poseStack.pushPose();
								VertexConsumer consumer = bufferIn.getBuffer(RenderType.LINES);
								LevelRenderer.renderLineBox(poseStack, consumer, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1);
	
								RenderSystem.enableDepthTest();
								RenderSystem.depthMask(true);
								RenderSystem.enableBlend();
								
								poseStack.popPose();
	
					            GL11.glDisable(GL11.GL_LINE_SMOOTH);
	
								poseStack.popPose();
							}
						}
					}
				}
			}
		}

		VertexConsumer buttonBuilder = bufferIn.getBuffer(this.buttonModel.renderType(BUTTON_TEXTURE));
		if (distanceToPlayer <= ModConfigManager.getInstance().getLabelMaximumDistance()) {
			poseStack.pushPose();
			
			poseStack.pushPose();
			if (north.isAir()) {
				poseStack.pushPose();
				
				poseStack.translate(1 / 16D, 0.0D, 9 / 16D);
				poseStack.mulPose(Axis.YP.rotationDegrees(90));
				
				poseStack.pushPose();
				
				poseStack.translate(9 / 16D, 9 / 16D, 8 / 16D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col1);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 9 / 16D, 0.0D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col2);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 1 / 16D, 8 / 16D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col3);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 1 / 16D, 0.0D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col4);
				poseStack.popPose();
				
				poseStack.popPose();
			}
			poseStack.popPose();
			
			poseStack.pushPose();
			if (south.isAir()) {
				poseStack.mulPose(Axis.YP.rotationDegrees(-180));
				
				poseStack.pushPose();
				
				poseStack.mulPose(Axis.YP.rotationDegrees(90));
				//poseStack.translate(1 / 16D, 0.0D, 9 / 16D);
				poseStack.translate(7 / 16.0D, 0.0D, -15 / 16D);
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 9 / 16D, 8 / 16D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col1);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 9 / 16D, 0.0D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col2);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 1 / 16D, 8 / 16D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col3);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 1 / 16D, 0.0D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col4);
				poseStack.popPose();
				
				poseStack.popPose();
			}
			poseStack.popPose();
	
			poseStack.pushPose();
			if (east.isAir()) {
				poseStack.mulPose(Axis.YP.rotationDegrees(90));
				
				poseStack.pushPose();
				
				poseStack.mulPose(Axis.YP.rotationDegrees(-90));
				poseStack.translate(7 / 16.0D, 0.0D, 1 / 16D);
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 9 / 16D, 8 / 16D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col1);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 9 / 16D, 0.0D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col2);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 1 / 16D, 8 / 16D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col3);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 1 / 16D, 0.0D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col4);
				poseStack.popPose();
				
				poseStack.popPose();
			}
			poseStack.popPose();
			
			poseStack.pushPose();
			if (west.isAir()) {
				poseStack.mulPose(Axis.YP.rotationDegrees(-90));
				
				poseStack.pushPose();
				
				poseStack.mulPose(Axis.YP.rotationDegrees(-90));
				poseStack.translate(-9 / 16.0D, 0.0D, -15 / 16D);
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 9 / 16D, 8 / 16D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col1);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 9 / 16D, 0.0D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col2);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 1 / 16D, 8 / 16D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col3);
				poseStack.popPose();
	
				poseStack.pushPose();
				poseStack.translate(9 / 16D, 1 / 16D, 0.0D);
				buttonModel.renderToBuffer(poseStack, buttonBuilder, combinedLightIn, combinedOverlayIn, col4);
				poseStack.popPose();
				
				poseStack.popPose();
			}
			poseStack.popPose();
	
			poseStack.popPose();
			
			if (ModConfigManager.getInstance().getRenderPortalLabels()) {
				if (distanceToPlayer <= ModConfigManager.getInstance().getLabelMaximumDistance()) {
					ComponentColour textColour = entityIn.isLinked() ? ComponentColour.GREEN : ComponentColour.RED;
	
					int currentIndex = -1;
					int maxIndex = -1;
					String modifier = "";
					
					if (entityIn.isLinked()) {
						AbstractBlockEntityPortalDock portalEntity = (AbstractBlockEntityPortalDock) testEntity;
						
						if (portalEntity != null) {
							currentIndex = portalEntity.getCurrentSlotIndex();
							maxIndex = portalEntity.getMaxSlotIndex();
						}
					}
					
					MutableComponent one = ComponentHelper.style(maxIndex >= 0 ? textColour : ComponentColour.RED, currentIndex == 0 ? modifier : "", "1");
					MutableComponent two = ComponentHelper.style(maxIndex >= 1 ? textColour : ComponentColour.RED, currentIndex == 1 ? modifier : "", "2");
					MutableComponent three = ComponentHelper.style(maxIndex >= 2 ? textColour : ComponentColour.RED, currentIndex == 2 ? modifier : "", "3");
					MutableComponent four = ComponentHelper.style(maxIndex >= 3 ? textColour : ComponentColour.RED, currentIndex == 3 ? modifier : "", "4");
	
					poseStack.pushPose();
					if (north.isAir()) {
						poseStack.pushPose();
						
						poseStack.translate(8 / 16F, 8 / 16F, 8 / 16F);
					
						poseStack.pushPose();
						poseStack.translate(3.8 / 16F, 5.4 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, one, bufferIn, combinedLightIn, false, currentIndex == 0);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(-4.2 / 16F, 5.4 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, two, bufferIn, combinedLightIn, false, currentIndex == 1);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(3.8 / 16F, -2.6 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, three, bufferIn, combinedLightIn, false, currentIndex == 2);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(-4.2 / 16F, -2.6 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, four, bufferIn, combinedLightIn, false, currentIndex == 3);
						poseStack.popPose();
						
						poseStack.popPose();
					}
					poseStack.popPose();
					
					poseStack.pushPose();
					if (south.isAir()) {
						poseStack.mulPose(Axis.YP.rotationDegrees(180));
						
						poseStack.pushPose();
						
						poseStack.translate(-8 / 16F, 8 / 16F, -8 / 16F);
	
						poseStack.pushPose();
						poseStack.translate(3.8 / 16F, 5.4 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, one, bufferIn, combinedLightIn, false, currentIndex == 0);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(-4.2 / 16F, 5.4 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, two, bufferIn, combinedLightIn, false, currentIndex == 1);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(3.8 / 16F, -2.6 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, three, bufferIn, combinedLightIn, false, currentIndex == 2);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(-4.2 / 16F, -2.6 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, four, bufferIn, combinedLightIn, false, currentIndex == 3);
						poseStack.popPose();
						
						poseStack.popPose();
					}
					poseStack.popPose();
	
					poseStack.pushPose();
					if (east.isAir()) {
						poseStack.mulPose(Axis.YP.rotationDegrees(-90));
						
						poseStack.pushPose();
						
						poseStack.translate(8 / 16F, 8 / 16F, -8 / 16F);
	
						poseStack.pushPose();
						poseStack.translate(3.8 / 16F, 5.4 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, one, bufferIn, combinedLightIn, false, currentIndex == 0);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(-4.2 / 16F, 5.4 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, two, bufferIn, combinedLightIn, false, currentIndex == 1);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(3.8 / 16F, -2.6 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, three, bufferIn, combinedLightIn, false, currentIndex == 2);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(-4.2 / 16F, -2.6 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, four, bufferIn, combinedLightIn, false, currentIndex == 3);
						poseStack.popPose();
						
						poseStack.popPose();
					}
					poseStack.popPose();
	
					poseStack.pushPose();
					if (west.isAir()) {
						poseStack.mulPose(Axis.YP.rotationDegrees(90));
						
						poseStack.pushPose();
						
						poseStack.translate(-8 / 16F, 8 / 16F, 8 / 16F);
	
						poseStack.pushPose();
						poseStack.translate(3.8 / 16F, 5.4 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, one, bufferIn, combinedLightIn, false, currentIndex == 0);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(-4.2 / 16F, 5.4 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, two, bufferIn, combinedLightIn, false, currentIndex == 1);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(3.8 / 16F, -2.6 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, three, bufferIn, combinedLightIn, false, currentIndex == 2);
						poseStack.popPose();
	
						poseStack.pushPose();
						poseStack.translate(-4.2 / 16F, -2.6 / 16F, -9 / 16F);
						CosmosRendererHelper.renderLabelInWorld(fontRenderer, poseStack, four, bufferIn, combinedLightIn, false, currentIndex == 3);
						poseStack.popPose();
						
						poseStack.popPose();
					}
					poseStack.popPose();
				}
			}
		}
	}
	
}