package com.tcn.cosmosportals.client.screen;

import com.tcn.cosmoslibrary.client.ui.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.screen.CosmosScreenBlockEntityUI;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonWithType;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonWithType.TYPE;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmosportals.CosmosPortalsReference;
import com.tcn.cosmosportals.client.container.ContainerContainerCopier;
import com.tcn.cosmosportals.core.blockentity.BlockEntityContainerCopier;
import com.tcn.cosmosportals.core.network.packet.PacketCopyItem;
import com.tcn.cosmosportals.core.network.packet.PacketSelectSlot;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;

public class ScreenContainerCopier extends CosmosScreenBlockEntityUI<ContainerContainerCopier> {
	
	private CosmosButtonWithType leftButton;  private int[] LB = new int[] { 29, 21, 18 };
	private CosmosButtonWithType rightButton; private int[] RB = new int[] { 53, 21, 18 };
	private CosmosButtonWithType applyButton; private int[] indexA = new int[] { 125, 21, 18 };
	
	private int[] slotsX = new int[] { 5, 23, 41, 59, 77, 95, 113, 131, 149 };
		
	public ScreenContainerCopier(ContainerContainerCopier containerIn, Inventory playerInventoryIn, Component titleIn) {
		super(containerIn, playerInventoryIn, titleIn);

		this.setImageDims(172, 157);

		this.setLight(CosmosPortalsReference.COPIER[0]);
		this.setDark(CosmosPortalsReference.COPIER[1]);

		this.setUIModeButtonIndex(155, 5);
		this.setUIHelpButtonIndex(155, 19);
		this.setUIHelpElementDeadzone(144, 109, 163, 128);
		this.setUIHelpTitleOffset(4);
		
		this.setUIHelpElementDeadzone(29, 14, 143, 32);
		
		this.setInventoryLabelDims(5, 66);
		this.setTitleLabelDims(5, 5);
		this.setHasEditBox();
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);
		
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		super.renderBg(graphics, partialTicks, mouseX, mouseY);
		
		if (this.getBlockEntity() instanceof BlockEntityContainerCopier blockEntity) {
			CosmosUISystem.Render.renderStaticElementWithUIMode(graphics, this.getScreenCoords(), 0, 0, 0, 0, this.imageWidth, this.imageHeight, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, blockEntity, CosmosPortalsReference.COPIER_SLOTS);
			
			CosmosUISystem.Render.renderStaticElementWithUIMode(graphics, this.getScreenCoords(), slotsX[blockEntity.getSelectedSlot()], 45, 172, 0, 18, 18, blockEntity, CosmosPortalsReference.COPIER_OVERLAY);
		}
	}
	
	@Override
	protected void addUIHelpElements() {
		super.addUIHelpElements();
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 76, 20, 20, 20, ComponentColour.CYAN, ComponentHelper.style(ComponentColour.CYAN, "bold", "cosmosportals.ui.help.copier.container"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.container_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.container_two"),
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.container_three")
		);
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 4, 44, 164, 20, ComponentColour.YELLOW, ComponentHelper.style(ComponentColour.YELLOW, "bold", "cosmosportals.ui.help.copier.container_linked"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.container_linked_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.container_linked_two")
		);

		this.addRenderableUIHelpElement(this.getScreenCoords(), 100, 20, 20, 20, ComponentColour.LIGHT_BLUE, ComponentHelper.style(ComponentColour.LIGHT_BLUE, "bold", "cosmosportals.ui.help.copier.container_copied"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.container_copied_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.container_copied_two")
		);

		this.addRenderableUIHelpElement(this.getScreenCoords(), 124, 20, 20, 20, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.ui.help.copier.button_apply"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.button_apply_one"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.button_apply_two")
		);
		
		this.addRenderableUIHelpElement(this.getScreenCoords(), 28, 20, 20, 20, ComponentColour.ORANGE, ComponentHelper.style(ComponentColour.ORANGE, "bold", "cosmosportals.ui.help.copier.button_left"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.button_left_one")
		);

		this.addRenderableUIHelpElement(this.getScreenCoords(), 52, 20, 20, 20, ComponentColour.ORANGE, ComponentHelper.style(ComponentColour.ORANGE, "bold", "cosmosportals.ui.help.copier.button_right"), 
			ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.ui.help.copier.button_right_one")
		);
	}
	
	@Override
	public void renderStandardHoverEffect(GuiGraphics graphics, Style style, int mouseX, int mouseY) {
		BlockEntity entity = this.getBlockEntity();

		if (entity instanceof BlockEntityContainerCopier) {
			if (this.leftButton.isMouseOver(mouseX, mouseY)) {
//				graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.RED, "cosmosportals.gui.button.text.clear"), mouseX, mouseY);
			}
			
			if (this.rightButton.isMouseOver(mouseX, mouseY)) {
//				graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.RED, "cosmosportals.gui.button.text.clear"), mouseX, mouseY);
			}

			if (this.applyButton.isMouseOver(mouseX, mouseY)) {
				
			}
		}
		super.renderStandardHoverEffect(graphics, style, mouseX, mouseY);
	}
	
	@Override
	protected void addButtons() {
		super.addButtons();
		if (this.getBlockEntity() instanceof BlockEntityContainerCopier) {
			this.leftButton = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + LB[0], this.getScreenCoords()[1] + LB[1], 18, true, true, 6, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.leftButton, isLeftClick);}));
			this.rightButton = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + RB[0], this.getScreenCoords()[1] + RB[1], 18, true, true, 7, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.rightButton, isLeftClick);}));
			
			this.applyButton = this.addRenderableWidget(new CosmosButtonWithType(TYPE.GENERAL, this.getScreenCoords()[0] + indexA[0], this.getScreenCoords()[1] + indexA[1], indexA[2], true, true, 1, ComponentHelper.empty(), (button, isLeftClick) -> { this.clickButton(this.applyButton, isLeftClick); }));
		}
	}
	
	@Override
	public void clickButton(Button button, boolean isLeftClick) {
		super.clickButton(button, isLeftClick);
		
		if (this.getBlockEntity() instanceof BlockEntityContainerCopier) {
			if (isLeftClick) {
				if (button.equals(this.applyButton)) {
					PacketDistributor.sendToServer(new PacketCopyItem(this.menu.getBlockPos()));
				}
	
				if (button.equals(this.leftButton)) {
					PacketDistributor.sendToServer(new PacketSelectSlot(this.menu.getBlockPos(), true));
				}
				
				if (button.equals(this.rightButton)) {
					PacketDistributor.sendToServer(new PacketSelectSlot(this.menu.getBlockPos(), false));
				}
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean keyPressed(int keyCode, int mouseX, int mouseY) {
		return super.keyPressed(keyCode, mouseX, mouseY);
	}

	@Override
	public boolean charTyped(char charIn, int charCode) {
		return super.charTyped(charIn, charCode);
	}
	
}