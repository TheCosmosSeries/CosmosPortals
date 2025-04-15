package com.tcn.cosmosportals.client.screen;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.tcn.cosmoslibrary.client.ui.screen.option.CosmosOptionBoolean;
import com.tcn.cosmoslibrary.client.ui.screen.option.CosmosOptionBoolean.TYPE;
import com.tcn.cosmoslibrary.client.ui.screen.option.CosmosOptionInstance;
import com.tcn.cosmoslibrary.client.ui.screen.option.CosmosOptionTitle;
import com.tcn.cosmoslibrary.client.ui.screen.option.CosmosOptionsList;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmosportals.core.management.ModConfigManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModContainer;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public final class PortalsConfigScreen extends OptionsSubScreen {

	private final Screen parent;

	private final int OPTIONS_LIST_TOP_HEIGHT = 24;
	private final int OPTIONS_LIST_BOTTOM_OFFSET = 32;
	private final int OPTIONS_LIST_ITEM_HEIGHT = 25;
	private final int OPTIONS_LIST_BUTTON_HEIGHT = 20;

	private final int BIG_WIDTH = 310;
	
	private final ComponentColour DESC = ComponentColour.LIGHT_GRAY;

	private CosmosOptionsList OPTIONS_ROW_LIST;

	@SuppressWarnings("resource")
	public PortalsConfigScreen(ModContainer container, Screen parent) {
		super(parent, Minecraft.getInstance().options, ComponentHelper.style(ComponentColour.GREEN, "boldunderline", "cosmosportals.gui.config.name"));

		this.parent = parent;
	}

	@Override
	protected void init() {
		super.init();
		this.initRowList();
		this.addWidget(this.OPTIONS_ROW_LIST);
	}

	protected void initRowList() {
		this.OPTIONS_ROW_LIST.addBig(
			new CosmosOptionTitle(ComponentHelper.style(ComponentColour.WHITE, "boldunderline", "cosmosportals.gui.config.general_title"))
		);
		
		this.OPTIONS_ROW_LIST.addBig(
			CosmosOptionInstance.createIntSlider(ComponentHelper.style(ComponentColour.ORANGE, "cosmosportals.gui.config.size"),
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.size_info"), ComponentHelper.style(ComponentColour.RED, "cosmosportals.gui.config.size_info_two"), ComponentHelper.style(ComponentColour.LIGHT_RED, "cosmosportals.gui.config.size_info_three")), 
				ModConfigManager.getInstance().getPortalMaximumSize(), 2, 9, 5,
				ComponentColour.WHITE, ComponentHelper.style(ComponentColour.GREEN, "2 (Min)"), ComponentHelper.style(ComponentColour.YELLOW, "Blocks"), ComponentHelper.style(ComponentColour.RED, "9 (Max)"), (intValue) -> {
				ModConfigManager.getInstance().setPortalMaximumSize(intValue);
			})
		);
		
		this.OPTIONS_ROW_LIST.addSmall(			
			new CosmosOptionBoolean(
				ComponentColour.ORANGE, "", "cosmosportals.gui.config.sound.travel", TYPE.YES_NO,
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.sound.travel_info")),
				ModConfigManager.getInstance().getPlayPortalTravelSounds(),
				(newValue) -> ModConfigManager.getInstance().setPlayPortalTravelSounds(newValue), ":"
			),
			new CosmosOptionBoolean(
				ComponentColour.ORANGE, "", "cosmosportals.gui.config.sound.ambient", TYPE.YES_NO,
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.sound.ambient_info")),
				ModConfigManager.getInstance().getPlayPortalAmbientSounds(),
				(newValue) -> ModConfigManager.getInstance().setPlayPortalAmbientSounds(newValue), ":"
			)
		);
		
		this.OPTIONS_ROW_LIST.addBig(
			CosmosOptionInstance.createIntSlider(ComponentHelper.style(ComponentColour.ORANGE, "cosmosportals.gui.config.portal_name_length"),
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.portal_name_length_info"), ComponentHelper.style(ComponentColour.LIGHT_RED, "cosmosportals.gui.config.portal_name_length_info_one")), 
				ModConfigManager.getInstance().getPortalNameLength(), 6, 24, 12,
				ComponentColour.WHITE, ComponentHelper.style(ComponentColour.GREEN, "6 (Min)"), ComponentHelper.style(ComponentColour.YELLOW, "Chars"), ComponentHelper.style(ComponentColour.RED, "24 (Max)"), (intValue) -> {
				ModConfigManager.getInstance().setPortalNameLength(intValue);
			})
		);
		
		
		this.OPTIONS_ROW_LIST.addBig(
			new CosmosOptionTitle(ComponentHelper.style(ComponentColour.WHITE, "boldunderline", "cosmosportals.gui.config.messages_title"))
		);
		
		
		this.OPTIONS_ROW_LIST.addSmall(
			new CosmosOptionBoolean(
				ComponentColour.YELLOW, "", "cosmosportals.gui.config.message.info", TYPE.ON_OFF,
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.message.info_desc"), ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.gui.config.message.restart")),
				ModConfigManager.getInstance().getInfoMessage(),
				(newValue) -> ModConfigManager.getInstance().setInfoMessage(newValue), ":"
			),
			new CosmosOptionBoolean(
				ComponentColour.YELLOW, "", "cosmosportals.gui.config.message.debug", TYPE.ON_OFF,
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.message.debug_desc"), ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.gui.config.message.restart")),
				ModConfigManager.getInstance().getDebugMessage(),
				(newValue) -> ModConfigManager.getInstance().setDebugMessage(newValue), ":"
			)
		);
		
		
		this.OPTIONS_ROW_LIST.addBig(
			new CosmosOptionTitle(ComponentHelper.style(ComponentColour.WHITE, "boldunderline", "cosmosportals.gui.config.visual_title"))
		);
		
		
		this.OPTIONS_ROW_LIST.addSmall(
			new CosmosOptionBoolean(
				ComponentColour.TURQUOISE, "", "cosmosportals.gui.config.frame_textures", TYPE.ON_OFF,
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.frame_textures_info")),
				ModConfigManager.getInstance().getFrameConnectedTextures(),
				(newValue) -> ModConfigManager.getInstance().setFrameConnectedTextures(newValue), ":"
			),
			new CosmosOptionBoolean(
				ComponentColour.TURQUOISE, "", "cosmosportals.gui.config.portal_textures", TYPE.ON_OFF, 
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.portal_textures_info")),
				ModConfigManager.getInstance().getPortalConnectedTextures(),
				(newValue) -> ModConfigManager.getInstance().setPortalConnectedTextures(newValue), ":"
			)
		);

		this.OPTIONS_ROW_LIST.addSmall(
			new CosmosOptionBoolean(
				ComponentColour.TURQUOISE, "", "cosmosportals.gui.config.labels", TYPE.ON_OFF,
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.labels_info")),
				ModConfigManager.getInstance().getRenderPortalLabels(),
				(newValue) -> ModConfigManager.getInstance().setRenderPortalLabels(newValue), ":"
			),
			new CosmosOptionBoolean(
				ComponentColour.TURQUOISE, "", "cosmosportals.gui.config.particles", TYPE.ON_OFF,
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.particles_info")),
				ModConfigManager.getInstance().getRenderPortalParticleEffects(),
				(newValue) -> ModConfigManager.getInstance().setRenderPortalParticleEffects(newValue), ":"
			)
		);
		
		this.OPTIONS_ROW_LIST.addBig(
			CosmosOptionInstance.createIntSlider(
				ComponentHelper.style(ComponentColour.TURQUOISE, "cosmosportals.gui.config.label_distance"), 
				CosmosOptionInstance.getTooltipSplitComponent(ComponentHelper.style(DESC, "cosmosportals.gui.config.label_distance_info")), 
				ModConfigManager.getInstance().getLabelMaximumDistance(), 8, 64, 32,	
				ComponentColour.WHITE, ComponentHelper.style(ComponentColour.GREEN, "Min"), ComponentHelper.style(ComponentColour.YELLOW, "Blocks"), ComponentHelper.style(ComponentColour.RED, "Max"), (intValue) -> {
				ModConfigManager.getInstance().setLabelMaximumDistance(intValue);
			})
		);
	}

	@Override
	public void renderBackground(GuiGraphics graphicsIn, int mouseX, int mouseY, float ticks) {
		super.renderBackground(graphicsIn, mouseX, mouseY, ticks);
	}

	@Override
	public void render(GuiGraphics graphicsIn, int mouseX, int mouseY, float ticks) {
		super.render(graphicsIn, mouseX, mouseY, ticks);
		this.OPTIONS_ROW_LIST.render(graphicsIn, mouseX, mouseY, ticks);
	}

	public void updateWidgets() {
		double scroll = this.OPTIONS_ROW_LIST.getScrollAmount();
		this.OPTIONS_ROW_LIST.clear();
		this.initRowList();
		this.OPTIONS_ROW_LIST.setScrollAmount(scroll);
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(this.parent);
		ModConfigManager.save();
	}

	@Override
	protected void addOptions() {
		this.OPTIONS_ROW_LIST = new CosmosOptionsList(
			this.minecraft, this.width, this.height, 40, 33,
			OPTIONS_LIST_ITEM_HEIGHT, OPTIONS_LIST_BUTTON_HEIGHT, 310, 26
		);
	}
	
	@Override
	public void resize(Minecraft minecraft, int width, int height) {
		this.OPTIONS_ROW_LIST.resize(minecraft, width, height);
		this.updateWidgets();
		super.resize(minecraft, width, height);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int ticks, double dragX, double dragY) {
		if (this.getChildAt(mouseX, mouseY).isPresent()) {
			for (GuiEventListener listener : this.OPTIONS_ROW_LIST.children()) {
				if (listener.isMouseOver(mouseX, mouseY)) {
					this.updateWidgets();
				}
			}
		}
		return super.mouseDragged(mouseX, mouseY, ticks, dragX, dragY);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int ticks) {
		boolean clicked = super.mouseClicked(mouseX, mouseY, ticks);
		
		if (this.getChildAt(mouseX, mouseY).isPresent()) {
			for (GuiEventListener listener : this.OPTIONS_ROW_LIST.children()) {
				if (listener.isMouseOver(mouseX, mouseY)) {
					this.updateWidgets();
				}
			}
		}
		return clicked;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
		if (this.OPTIONS_ROW_LIST.isMouseOver(mouseX, mouseY)) {
			return this.OPTIONS_ROW_LIST.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
		}
		return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
	}

	@SuppressWarnings("unchecked")
	public static List<FormattedCharSequence> tooltipAt(CosmosOptionsList listIn, int mouseX, int mouseY) {
		Optional<AbstractWidget> optional = listIn.getMouseOver((double)  mouseX, (double) mouseY);
		return (List<FormattedCharSequence>) (optional.isPresent() && optional.get() instanceof AbstractWidget ? ((AbstractWidget) optional.get()).getTooltip() : ImmutableList.of());
	}
}