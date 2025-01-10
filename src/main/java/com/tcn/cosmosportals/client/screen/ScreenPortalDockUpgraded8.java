package com.tcn.cosmosportals.client.screen;

import java.util.Arrays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tcn.cosmoslibrary.CosmosReference;
import com.tcn.cosmoslibrary.client.ui.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.screen.CosmosScreenBlockEntityUI;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonWithType;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonWithType.TYPE;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosColourButton;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmosportals.CosmosPortalsReference;
import com.tcn.cosmosportals.client.container.ContainerPortalDockUpgraded8;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockUpgraded8;
import com.tcn.cosmosportals.core.network.packet.PacketColour;
import com.tcn.cosmosportals.core.network.packet.PacketNextSlot;
import com.tcn.cosmosportals.core.network.packet.PacketPortalDock;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;

public class ScreenPortalDockUpgraded8 extends CosmosScreenBlockEntityUI<ContainerPortalDockUpgraded8> {
	
	private CosmosButtonWithType toggleLabelButton;     private int[] indexL   = new int[] { 16,  139, 20 };
	private CosmosButtonWithType toggleSoundButton;     private int[] indexS   = new int[] { 46,  139, 20 };
	private CosmosButtonWithType toggleEntityButton; 	private int[] indexE   = new int[] { 114, 139, 20 };
	private CosmosButtonWithType toggleParticlesButton; private int[] indexP   = new int[] { 144, 139, 20 };
	
	private CosmosButtonWithType cycleSlotButtonUp;     private int[] indexCyU = new int[] { 145, 35, 18 };
	private CosmosButtonWithType cycleSlotButtonDown;   private int[] indexCyD = new int[] { 17,  35, 18 };
	
	private int[] indexCol = new int[] { 9, 167, 58, 78, 98, 118, 58, 78, 98, 118 };
	
	private int[][] slotIndexes = new int[][] { new int[] { 15, 55 }, new int[] { 15, 75 }, new int[] { 15, 95 }, new int[] { 15, 115 }, new int[] { 143, 55 }, new int[] { 143, 75 }, new int[] { 143, 95 }, new int[] { 143, 115 } };
	
	int[] colSize = new int[] { 6, 18 };
	
	private int[][] indexCs = new int[][] { new int[]{ 8, 57 }, new int[] { 8, 77 }, new int[] { 8, 97 }, new int[] { 8, 117 }, new int[]{ 166, 57 }, new int[] { 166, 77 }, new int[] { 166, 97 }, new int[] { 166, 117 } };
	private CosmosColourButton colourButton0; private CosmosColourButton colourButton1; private CosmosColourButton colourButton2; private CosmosColourButton colourButton3;
	private CosmosColourButton colourButton4; private CosmosColourButton colourButton5; private CosmosColourButton colourButton6; private CosmosColourButton colourButton7;
		
	public ScreenPortalDockUpgraded8(ContainerPortalDockUpgraded8 containerIn, Inventory playerInventoryIn, Component titleIn) {
		super(containerIn, playerInventoryIn, titleIn);

		this.setImageDims(194, 256);

		this.setLight(CosmosPortalsReference.DOCK_UPGRADED8[0]);
		this.setDark(CosmosPortalsReference.DOCK_UPGRADED8[1]);

		this.setUIModeButtonIndex(177, 5);
		this.setUIHelpButtonIndex(177, 33);
		this.setUILockButtonIndex(177, 19);
		this.setUIHelpElementDeadzone(0, 0, 169, 164);
		this.setUIHelpTitleOffset(4);
		
		this.setInventoryLabelDims(9, 166);
		this.setNoTitleLabel();
	}

	@Override
	protected void init() {
		super.init();
	}
	
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);
		
		this.renderPortalLabel(graphics);

		if (this.getBlockEntity() instanceof BlockEntityPortalDockUpgraded8 blockEntity) {
			graphics.drawString(font, ComponentHelper.style(ComponentColour.GREEN, "bold", "" + (blockEntity.getCurrentSlotIndex() + 1)), this.getScreenCoords()[0] + 151, this.getScreenCoords()[1] + 18, ComponentColour.WHITE.dec(), false);
		}
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		super.renderBg(graphics, partialTicks, mouseX, mouseY);
		
		if (this.getBlockEntity() instanceof BlockEntityPortalDockUpgraded8 blockEntity) {
			int portalColour = blockEntity.getDisplayColour();
			float frame[] = ComponentColour.rgbFloatArray(ComponentColour.GRAY);
			float[] colour = new float[] {((portalColour >> 16) & 255) / 255.0F, ((portalColour >> 8) & 255) / 255.0F, (portalColour & 255) / 255.0F, 1F};
			
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), 0, 0, 0, 0, this.imageWidth, this.imageHeight, new float[] { frame[0], frame[1], frame[2], 1.0F }, CosmosPortalsReference.DOCK_FRAME_UPGRADED8);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), 0, 0, 0, 0, this.imageWidth, this.imageHeight, CosmosPortalsReference.DOCK_BACKING_UPGRADED8);
			
			if (blockEntity.isPortalFormed) {
				RenderSystem.enableBlend();
				CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), 0, 0, 0, 0, this.imageWidth, this.imageHeight, colour, CosmosPortalsReference.DOCK_PORTAL_UPGRADED8);
				RenderSystem.disableBlend();
			}
			
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), 0, 0, 0, 0, this.imageWidth, this.imageHeight, colour, CosmosPortalsReference.DOCK_CONTAINER_UPGRADED8);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), 75, 134, 30, 0, 32, 32, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);
			
			CosmosUISystem.Render.renderStaticElementWithUIMode(graphics, this.getScreenCoords(), 0, 0, 0, 0, this.imageWidth, this.imageHeight, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, blockEntity, CosmosPortalsReference.DOCK_SLOTS_UPGRADED8);
			
			if (blockEntity.isPortalFormed && blockEntity.renderLabel) {
				String human_name = blockEntity.getContainerDisplayName();
				int width = this.font.width(human_name) + 4;
				
				CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), (this.imageWidth - 11) / 2 - width / 2, 117, 0, 0, width, 12, new float[] { 1.0F, 1.0F, 1.0F, 0.6F }, CosmosPortalsReference.DOCK_LABEL_UPGRADED8);
			}

			int currentSlot = blockEntity.getCurrentSlotIndex();
			int[] currentSlotPos = this.slotIndexes[currentSlot];
			
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), currentSlotPos[0] + 2, currentSlotPos[1] + 2, 18, 72, 18, 18, CosmosReference.RESOURCE.BASE.GUI_SLOT_LOC);
			
			ComponentColour[] customColours = blockEntity.customColours;
			
			for (int i = 0; i <= blockEntity.getMaxSlotIndex(); i++) {
				ComponentColour customColour = customColours[i];
				
				if (customColour != ComponentColour.EMPTY) {
					float[] floatCol = new float[] { 
						ComponentColour.rgbFloatArray(customColour.dec())[0], 
						ComponentColour.rgbFloatArray(customColour.dec())[1],
						ComponentColour.rgbFloatArray(customColour.dec())[2],
						1 
					};
					
					CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), indexCol[i > 3 ? 1 : 0], indexCol[i + 2], 0, 22, 4, 16, floatCol, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);
				}
			}
			
			boolean hovered0 = this.colourButton0.isMouseOver(mouseX, mouseY);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), indexCs[0][0], indexCs[0][1], hovered0 ? 6 : 0, 38, 6, 18, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);

			boolean hovered1 = this.colourButton1.isMouseOver(mouseX, mouseY);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), indexCs[1][0], indexCs[1][1], hovered1 ? 6 : 0, 38, 6, 18, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);

			boolean hovered2 = this.colourButton2.isMouseOver(mouseX, mouseY);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), indexCs[2][0], indexCs[2][1], hovered2 ? 6 : 0, 38, 6, 18, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);

			boolean hovered3 = this.colourButton3.isMouseOver(mouseX, mouseY);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), indexCs[3][0], indexCs[3][1], hovered3 ? 6 : 0, 38, 6, 18, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);
			
			boolean hovered4 = this.colourButton0.isMouseOver(mouseX, mouseY);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), indexCs[4][0], indexCs[4][1], hovered4 ? 6 : 0, 38, 6, 18, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);

			boolean hovered5 = this.colourButton1.isMouseOver(mouseX, mouseY);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), indexCs[5][0], indexCs[5][1], hovered5 ? 6 : 0, 38, 6, 18, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);

			boolean hovered6 = this.colourButton2.isMouseOver(mouseX, mouseY);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), indexCs[6][0], indexCs[6][1], hovered6 ? 6 : 0, 38, 6, 18, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);

			boolean hovered7 = this.colourButton3.isMouseOver(mouseX, mouseY);
			CosmosUISystem.Render.renderStaticElement(graphics, this.getScreenCoords(), indexCs[7][0], indexCs[7][1], hovered7 ? 6 : 0, 38, 6, 18, CosmosPortalsReference.DOCK_OVERLAY_ONE_UPGRADED8);
		}
	}
	
	@Override
	public void renderStandardHoverEffect(GuiGraphics graphics, Style style, int mouseX, int mouseY) {
		if (this.getBlockEntity() instanceof BlockEntityPortalDockUpgraded8 blockEntity) {
			if (this.toggleLabelButton.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmosportals.gui.dock.label_info"), 
					ComponentHelper.style2(ComponentColour.GRAY, "cosmosportals.gui.dock.label_value", " ")
					.append(blockEntity.renderLabel ? ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.gui.dock.label_shown") : ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.gui.dock.label_hidden"))
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}
			
			if (this.toggleSoundButton.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmosportals.gui.dock.sounds_info"), 
					ComponentHelper.style2(ComponentColour.GRAY, "cosmosportals.gui.dock.sounds_value", " ")
					.append(blockEntity.playSound ? ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.gui.dock.sounds_played") : ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.gui.dock.sounds_muffled"))
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}
			
			if (this.toggleEntityButton.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmosportals.gui.dock.entity_info"), 
					ComponentHelper.style2(ComponentColour.GRAY, "cosmosportals.gui.dock.entity_value", " ")
					.append(blockEntity.allowedEntities.getColouredComp())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}

			if (this.toggleParticlesButton.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmosportals.gui.dock.particle_info"), 
					ComponentHelper.style2(ComponentColour.GRAY, "cosmosportals.gui.dock.particle_value", " ")
					.append(blockEntity.showParticles ? ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.gui.dock.particle_shown") : ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.gui.dock.particle_hidden"))
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}
			
			if (this.colourButton0.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.colour.info"), 
					ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.colour.value").append(blockEntity.getCustomColoursComp(false)[0].getColouredName())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}

			if (this.colourButton1.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.colour.info"), 
					ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.colour.value").append(blockEntity.getCustomColoursComp(false)[1].getColouredName())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}

			if (this.colourButton2.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.colour.info"), 
					ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.colour.value").append(blockEntity.getCustomColoursComp(false)[2].getColouredName())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}

			if (this.colourButton3.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.colour.info"), 
					ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.colour.value").append(blockEntity.getCustomColoursComp(false)[3].getColouredName())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}
			
			if (this.colourButton4.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.colour.info"), 
					ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.colour.value").append(blockEntity.getCustomColoursComp(false)[4].getColouredName())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}

			if (this.colourButton5.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.colour.info"), 
					ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.colour.value").append(blockEntity.getCustomColoursComp(false)[5].getColouredName())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}

			if (this.colourButton6.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.colour.info"), 
					ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.colour.value").append(blockEntity.getCustomColoursComp(false)[6].getColouredName())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}

			if (this.colourButton7.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.colour.info"), 
					ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.colour.value").append(blockEntity.getCustomColoursComp(false)[7].getColouredName())
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}
			
			if (this.cycleSlotButtonDown.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.GREEN, "cosmosportals.gui.dock.cycle_down"), 
					//ComponentHelper.style(ComponentColour.GRAY, "cosmosportals.gui.dock.cycle_down_value")
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}

			if (this.cycleSlotButtonUp.isMouseOver(mouseX, mouseY)) {
				MutableComponent[] comp = new MutableComponent[] {
					ComponentHelper.style(ComponentColour.GREEN, "cosmosportals.gui.dock.cycle_up"), 
					//ComponentHelper.style(ComponentColour.GRAY, "cosmosportals.gui.dock.cycle_up_value")
				};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
			}

		}
		super.renderStandardHoverEffect(graphics, style, mouseX, mouseY);
	}
		
	@Override
	protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawCenteredString(font, this.title, ((this.imageWidth - 11) / 2) - 8, 17, CosmosUISystem.DEFAULT_COLOUR_FONT_LIST);

		for (int i = 0; i < 4; i++) {
			int startX = 28;
			int startY = 66;
			int yOffset = 20;
			
			Slot slotI = this.menu.getSlot(i);
			
			if (slotI.getItem().isEmpty()) {
				graphics.drawString(this.font, ComponentHelper.locString("" + (i + 1)), startX, startY + (i * yOffset), ComponentColour.BLACK.dec(), false);
			}
		}

		for (int i = 4; i < 8; i++) {
			int startX = 156;
			int startY = 66;
			int yOffset = 20;
			
			Slot slotI = this.menu.getSlot(i);
			
			if (slotI.getItem().isEmpty()) {
				graphics.drawString(this.font, ComponentHelper.locString("" + (i + 1)), startX, startY + ((i - 4) * yOffset), ComponentColour.BLACK.dec(), false);
			}
		}
		
		super.renderLabels(graphics, mouseX, mouseY);
	}
	
	@Override
	protected void addButtons() {
		super.addButtons();

		if (this.getBlockEntity() instanceof BlockEntityPortalDockUpgraded8 blockEntity) {
			int i = 0;
			int j = blockEntity.allowedEntities.getIndex();
			
			if (j == 0) {
				i = 2;
			} else if (j == 1) {
				i = 15;
			} else if (j == 2) {
				i = 19;
			} else if (j == 3) {
				i = 22;
			} else if (j == 4) {
				i = 1;
			}

			this.toggleLabelButton = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + indexL[0], this.getScreenCoords()[1] + indexL[1], indexL[2], true, true, blockEntity.renderLabel ? 1 : 2, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.toggleLabelButton, isLeftClick);} ));
			this.toggleSoundButton = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + indexS[0], this.getScreenCoords()[1] + indexS[1], indexS[2], true, true, blockEntity.playSound ? 1 : 2, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.toggleSoundButton, isLeftClick); }));
			this.toggleEntityButton = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + indexE[0], this.getScreenCoords()[1] + indexE[1], indexE[2], true, true, i, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.toggleEntityButton, isLeftClick); }));
			this.toggleParticlesButton = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + indexP[0], this.getScreenCoords()[1] + indexP[1], indexP[2], true, true, blockEntity.showParticles ? 1 : 2, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.toggleParticlesButton, isLeftClick); }));
			
			this.colourButton0 = this.addRenderableWidget(new CosmosColourButton(blockEntity.getCustomColoursComp(false)[0], this.getScreenCoords()[0] + indexCs[0][0], this.getScreenCoords()[1] + indexCs[0][1], colSize[0], colSize[1], true, true, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.colourButton0, isLeftClick); }));
			this.colourButton1 = this.addRenderableWidget(new CosmosColourButton(blockEntity.getCustomColoursComp(false)[1], this.getScreenCoords()[0] + indexCs[1][0], this.getScreenCoords()[1] + indexCs[1][1], colSize[0], colSize[1], true, true, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.colourButton1, isLeftClick); }));
			this.colourButton2 = this.addRenderableWidget(new CosmosColourButton(blockEntity.getCustomColoursComp(false)[2], this.getScreenCoords()[0] + indexCs[2][0], this.getScreenCoords()[1] + indexCs[2][1], colSize[0], colSize[1], true, true, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.colourButton2, isLeftClick); }));
			this.colourButton3 = this.addRenderableWidget(new CosmosColourButton(blockEntity.getCustomColoursComp(false)[3], this.getScreenCoords()[0] + indexCs[3][0], this.getScreenCoords()[1] + indexCs[3][1], colSize[0], colSize[1], true, true, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.colourButton3, isLeftClick); }));
			this.colourButton4 = this.addRenderableWidget(new CosmosColourButton(blockEntity.getCustomColoursComp(false)[4], this.getScreenCoords()[0] + indexCs[4][0], this.getScreenCoords()[1] + indexCs[4][1], colSize[0], colSize[1], true, true, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.colourButton4, isLeftClick); }));
			this.colourButton5 = this.addRenderableWidget(new CosmosColourButton(blockEntity.getCustomColoursComp(false)[5], this.getScreenCoords()[0] + indexCs[5][0], this.getScreenCoords()[1] + indexCs[5][1], colSize[0], colSize[1], true, true, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.colourButton5, isLeftClick); }));
			this.colourButton6 = this.addRenderableWidget(new CosmosColourButton(blockEntity.getCustomColoursComp(false)[6], this.getScreenCoords()[0] + indexCs[6][0], this.getScreenCoords()[1] + indexCs[6][1], colSize[0], colSize[1], true, true, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.colourButton6, isLeftClick); }));
			this.colourButton7 = this.addRenderableWidget(new CosmosColourButton(blockEntity.getCustomColoursComp(false)[7], this.getScreenCoords()[0] + indexCs[7][0], this.getScreenCoords()[1] + indexCs[7][1], colSize[0], colSize[1], true, true, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.colourButton7, isLeftClick); }));
			
			this.cycleSlotButtonDown = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + indexCyU[0], this.getScreenCoords()[1] + indexCyU[1], indexCyU[2], true, true, 7, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(cycleSlotButtonDown, isLeftClick); }));
			this.cycleSlotButtonUp = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + indexCyD[0], this.getScreenCoords()[1] + indexCyD[1], indexCyD[2], true, true, 6, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(cycleSlotButtonUp, isLeftClick); }));
			
			//this.reformPortalButton = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + indexR[0], this.getScreenCoords()[1] + indexR[1], indexR[2], !blockEntity.isPortalFormed, true,          1, ComponentHelper.style(""), (button) -> { this.clickButton(this.reformPortalButton);    }));
		}
	}
	
	@Override
	public void clickButton(Button button, boolean isLeftClick) {
		super.clickButton(button, isLeftClick);
		
		if (this.getBlockEntity() instanceof BlockEntityPortalDockUpgraded8 blockEntity) {
			if (isLeftClick) {
				if (button.equals(this.toggleLabelButton)) {
					PacketDistributor.sendToServer(new PacketPortalDock(this.menu.getBlockPos(), 0));
					blockEntity.toggleRenderLabel();
				}
				
				if (button.equals(this.toggleSoundButton)) {
					PacketDistributor.sendToServer(new PacketPortalDock(this.menu.getBlockPos(), 1));
					blockEntity.togglePlaySound();
				}
				
				if (button.equals(this.toggleEntityButton)) {
					PacketDistributor.sendToServer(new PacketPortalDock(this.menu.getBlockPos(), 2));
					blockEntity.toggleEntities(false);
				}
	
				if (button.equals(this.toggleParticlesButton)) {
					PacketDistributor.sendToServer(new PacketPortalDock(this.menu.getBlockPos(), 3));
					blockEntity.toggleParticles();
				}
				
				if (button.equals(this.colourButton0)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[0].getNextVanillaColour(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 0));
				}

				if (button.equals(this.colourButton1)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[1].getNextVanillaColour(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 1));
				}

				if (button.equals(this.colourButton2)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[2].getNextVanillaColour(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 2));
				}

				if (button.equals(this.colourButton3)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[3].getNextVanillaColour(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 3));
				}

				if (button.equals(this.colourButton4)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[4].getNextVanillaColour(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 4));
				}

				if (button.equals(this.colourButton5)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[5].getNextVanillaColour(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 5));
				}

				if (button.equals(this.colourButton6)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[6].getNextVanillaColour(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 6));
				}

				if (button.equals(this.colourButton7)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[7].getNextVanillaColour(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 7));
				}

				if (button.equals(this.cycleSlotButtonUp)) {
					PacketDistributor.sendToServer(new PacketNextSlot(this.menu.getBlockPos(), false));
					blockEntity.selectNextSlot(false);
				}
				
				if (button.equals(this.cycleSlotButtonDown)) {
					PacketDistributor.sendToServer(new PacketNextSlot(this.menu.getBlockPos(), true));
					blockEntity.selectNextSlot(true);
				}
			} else {
				if (button.equals(this.toggleEntityButton)) {
					PacketDistributor.sendToServer(new PacketPortalDock(this.menu.getBlockPos(), 4));
					blockEntity.toggleEntities(true);
				}
	
				if (button.equals(this.colourButton0)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[0].getNextVanillaColourReverse(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 0));
				}

				if (button.equals(this.colourButton1)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[1].getNextVanillaColourReverse(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 1));
				}

				if (button.equals(this.colourButton2)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[2].getNextVanillaColourReverse(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 2));
				}

				if (button.equals(this.colourButton3)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[3].getNextVanillaColourReverse(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 3));
				}

				if (button.equals(this.colourButton4)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[4].getNextVanillaColourReverse(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 4));
				}

				if (button.equals(this.colourButton5)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[5].getNextVanillaColourReverse(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 5));
				}

				if (button.equals(this.colourButton6)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[6].getNextVanillaColourReverse(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 6));
				}

				if (button.equals(this.colourButton7)) {
					ComponentColour colour = hasShiftDown() ? ComponentColour.EMPTY : blockEntity.getCustomColoursComp(false)[7].getNextVanillaColourReverse(true);
					PacketDistributor.sendToServer(new PacketColour(this.menu.getBlockPos(), colour, 7));
				}
			}
		} 
	}
	
	@Override
	protected void addUIHelpElements() {
		super.addUIHelpElements();

		this.addRenderableUIHelpElement(this.getScreenCoords(), 73, 132, 34, 34, ComponentColour.WHITE, ComponentHelper.style(ComponentColour.WHITE, "bold", "cosmosportals.ui.help.container"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.container_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.container_two")
		);
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 16, 56, 20, 80, ComponentColour.RED, ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.ui.help.container_items"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.container_items_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.container_items_two")
		);

		this.addRenderableUIHelpElement(this.getScreenCoords(), 7, 56, 8, 80, ComponentColour.YELLOW, ComponentHelper.style(ComponentColour.YELLOW, "bold", "cosmosportals.ui.help.colours"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.colours_one"),
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.colours_two"),
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.colours_three")
		);

		this.addRenderableUIHelpElement(this.getScreenCoords(), 144, 56, 20, 80, ComponentColour.RED, ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.ui.help.container_items"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.container_items_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.container_items_two")
		);

		this.addRenderableUIHelpElement(this.getScreenCoords(), 165, 56, 8, 80, ComponentColour.YELLOW, ComponentHelper.style(ComponentColour.YELLOW, "bold", "cosmosportals.ui.help.colours"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.colours_one"),
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.colours_two"),
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.colours_three")
		);

		this.addRenderableUIHelpElement(this.getScreenCoords(), 15, 138, 22, 22, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.ui.help.button_label"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.button_label_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.button_label_two")
		);
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 45, 138, 22, 22, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.ui.help.button_sounds"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.button_sounds_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.button_sounds_two")
		);
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 113, 138, 22, 22, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.ui.help.button_entity"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.button_entity_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.button_entity_two"),
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.button_entity_three")
		);
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 143, 138, 22, 22, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.ui.help.button_effects"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.button_effects_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.button_effects_two")
		);
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 43, 115, 94, 16, ComponentColour.DARK_GREEN, ComponentHelper.style(ComponentColour.DARK_GREEN, "bold", "cosmosportals.ui.help.label"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.label_one"),
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.label_two"),
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.label_three")
		);
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 16, 34, 20, 20, ComponentColour.LIGHT_BLUE, ComponentHelper.style(ComponentColour.LIGHT_BLUE, "bold", "cosmosportals.ui.help.cycle_up"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.cycle_up_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.cycle_up_two")
		);
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 144, 34, 20, 20, ComponentColour.LIGHT_BLUE, ComponentHelper.style(ComponentColour.LIGHT_BLUE, "bold", "cosmosportals.ui.help.cycle_down"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.cycle_down_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.cycle_down_two")
		);
	}
	
	public void renderPortalLabel(GuiGraphics graphics) {
		if (this.getBlockEntity() instanceof BlockEntityPortalDockUpgraded8 blockEntity) {
			if (blockEntity.isPortalFormed && blockEntity.renderLabel) {
				int portalColour = blockEntity.getDisplayColour();
				
				graphics.drawCenteredString(font, blockEntity.getContainerDisplayName(), this.getScreenCoords()[0] + (this.imageWidth - 11) / 2, this.getScreenCoords()[1] + 119, portalColour);
			}
		}
	}
	
}