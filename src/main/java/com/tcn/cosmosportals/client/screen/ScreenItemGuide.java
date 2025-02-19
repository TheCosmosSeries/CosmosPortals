package com.tcn.cosmosportals.client.screen;

import java.util.Arrays;
import java.util.UUID;

import com.tcn.cosmoslibrary.client.ui.CosmosUISystem;
import com.tcn.cosmoslibrary.client.ui.screen.widget.CosmosButtonUIMode;
import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper.Value;
import com.tcn.cosmosportals.CosmosPortalsReference;
import com.tcn.cosmosportals.client.screen.button.GuideButton;
import com.tcn.cosmosportals.client.screen.button.GuideChangeButton;
import com.tcn.cosmosportals.core.item.ItemPortalGuide;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;
import com.tcn.cosmosportals.core.network.packet.PacketGuideUpdate;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class ScreenItemGuide extends Screen {
	private ResourceLocation FLAT_TEXTURES = CosmosPortalsReference.GUIDE_FLAT_TEXTURES;
	private ResourceLocation BLOCK_TEXTURES = CosmosPortalsReference.GUIDE_BLOCK_TEXTURES;
	
	private ItemStack stack;
	
	private int flipTimer = 0;
	private int flipTimerMulti = 0;
	
	private int currPage;
	private int pageCount = 17;

	protected CosmosButtonUIMode uiModeButton;
	private UUID playerUUID;
	
	private GuideChangeButton buttonNextPage;
	private GuideChangeButton buttonPreviousPage;
	private GuideButton buttonExit;
	private GuideButton buttonHome;
	
	private GuideButton tabIntroduction;
	private GuideButton tabPortals;
	private GuideButton tabItems;
	private GuideButton tabConfiguration;
	private GuideButton tabRecipes;
	private GuideButton tabCredits;
	
	private GuideButton tabPlaceholder;
	private GuideButton tabPlaceholder2;
	
	private final boolean pageTurnSounds;

	public ScreenItemGuide(boolean pageTurnSoundsIn, UUID playerUUIDIn, ItemStack stackIn) {
		super(ComponentHelper.title("cosmosportals.guide.heading"));
		
		this.pageTurnSounds = pageTurnSoundsIn;
		this.stack = stackIn;
		this.playerUUID = playerUUIDIn;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	protected void init() {
		this.drawRenderableWidgets();
		super.init();
		
		this.currPage = ItemPortalGuide.getPage(this.stack);
	}
	
	@Override
	public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.renderBackground(graphics, mouseX, mouseY, partialTicks);
		int[] screen_coords = CosmosUISystem.Init.getScreenCoords(this, 202, 225);

		if (this.stack != null) {
			CosmosUISystem.Render.renderStaticElementWithUIMode(graphics, screen_coords, 0, 0, 0, 0, 202, 255, this.getUIMode(), CosmosPortalsReference.GUIDE);
		} else {
			CosmosUISystem.Render.renderStaticElementWithUIMode(graphics, screen_coords, 0, 0, 0, 0, 202, 255, EnumUIMode.DARK, CosmosPortalsReference.GUIDE);
		}
		
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		int[] screen_coords = CosmosUISystem.Init.getScreenCoords(this, 202, 225);
		
		if (this.stack != null) {
			CosmosUISystem.Render.renderStaticElementWithUIMode(graphics, screen_coords, 0, 0, 0, 0, 202, 255, this.getUIMode(), CosmosPortalsReference.GUIDE);
		} else {
			CosmosUISystem.Render.renderStaticElementWithUIMode(graphics, screen_coords, 0, 0, 0, 0, 202, 255, EnumUIMode.DARK, CosmosPortalsReference.GUIDE);
		}
		
		super.render(graphics, mouseX, mouseY, partialTicks);
		
		CosmosUISystem.Setup.setTextureColour(new float[] { 5, 5, 5, 1 });

		if (this.flipTimer < 2000) {
			this.flipTimer++;
		} else {
			this.flipTimer = 0;
		}

		if (this.flipTimerMulti < 8000) {
			this.flipTimerMulti++;
		} else {
			this.flipTimerMulti = 0;
		}

		this.drawRenderableWidgets();

		CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 23, 10, true, ComponentHelper.style2(ComponentColour.BLACK, "cosmosportals.guide.page", Integer.toString(this.currPage)));
		CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 76, 10, true, ComponentHelper.style(ComponentColour.BLACK, "underline", "cosmosportals.guide.heading"));
		
		if (this.currPage == 0) {
			CosmosUISystem.FontRenderer.drawWrappedStringBR(graphics, font, screen_coords, 104, -4, 0, ComponentColour.SCREEN_LIGHT.dec(), ComponentHelper.locString("cosmosportals.guide.one_body")
					+ ComponentHelper.locString(Value.GRAY + Value.UNDERLINE, "cosmosportals.guide.one_body_one") + ComponentHelper.locString("cosmosportals.guide.one_body_two"));
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 75, ComponentColour.SCREEN_LIGHT.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.one_body_heading"), false);
			
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 30, 120, true, ComponentHelper.style(ComponentColour.POCKET_PURPLE_LIGHT, "cosmosportals.guide.two_heading"));
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 173, 120, true, ComponentHelper.style(ComponentColour.POCKET_PURPLE_LIGHT, "1"));
			
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 30, 130, true, ComponentHelper.style(ComponentColour.CYAN, "cosmosportals.guide.three_heading"));
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 161, 130, true, ComponentHelper.style(ComponentColour.CYAN, "2-5"));
			
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 30, 140, true, ComponentHelper.style(ComponentColour.LIGHT_BLUE, "cosmosportals.guide.four_heading"));
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 161, 140, true, ComponentHelper.style(ComponentColour.LIGHT_BLUE, "6-7"));
			
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 30, 150, true, ComponentHelper.style(ComponentColour.GREEN, "cosmosportals.guide.five_heading"));
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 155, 150, true, ComponentHelper.style(ComponentColour.GREEN, "8-12"));
			
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 30, 160, true, ComponentHelper.style(ComponentColour.DARK_GREEN, "cosmosportals.guide.six_heading"));
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 149, 160, true, ComponentHelper.style(ComponentColour.DARK_GREEN, "13-15"));
			
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 30, 170, true, ComponentHelper.style(ComponentColour.RED, "cosmosportals.guide.seven_tab"));
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 167, 170, true, ComponentHelper.style(ComponentColour.RED, "16"));
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_one", " ]"), false);
		} 
		
		else if (this.currPage == 1) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -8, ComponentColour.POCKET_PURPLE_LIGHT.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.two_heading"), false);
			
			CosmosUISystem.FontRenderer.drawWrappedStringBR(graphics, font, screen_coords, 104, 4, 0, ComponentColour.BLACK.dec(), ComponentHelper.locString("cosmosportals.guide.two_body_one"));
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 65, ComponentColour.POCKET_PURPLE_LIGHT.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.two_body_two"), false);
			
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 25, 110, true, ComponentHelper.style(ComponentColour.CYAN, "cosmosportals.guide.two_sub_one"));
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 25, 120, true, ComponentHelper.style(ComponentColour.LIGHT_BLUE, "cosmosportals.guide.two_sub_two"));
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 25, 130, true, ComponentHelper.style(ComponentColour.GREEN, "cosmosportals.guide.two_sub_three"));
			CosmosUISystem.FontRenderer.drawString(graphics, font, screen_coords, 25, 140, true, ComponentHelper.style(ComponentColour.DARK_GREEN, "cosmosportals.guide.two_sub_four"));
			
			CosmosUISystem.FontRenderer.drawWrappedStringBR(graphics, font, screen_coords, 104, 140, 0, ComponentColour.BLACK.dec(), ComponentHelper.locString("cosmosportals.guide.two_body_three"));
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_two", " ]"), false);
		}
		
		else if (this.currPage == 2) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -8, ComponentColour.CYAN.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.three_heading"), false);
			
			CosmosUISystem.Setup.setTextureWithColour(graphics.pose(), BLOCK_TEXTURES, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 75, 40, 0, 0, 60, 60, FLAT_TEXTURES);
			
			CosmosUISystem.FontRenderer.drawWrappedStringBR(graphics, font, screen_coords, 104, 70, 0, ComponentColour.BLACK.dec(), ComponentHelper.locString("cosmosportals.guide.three_body_one"));
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_three", " 1 ]"), false);
		} 
		
		else if (this.currPage == 3) {
			CosmosUISystem.FontRenderer.drawWrappedStringBR(graphics, font, screen_coords, 104, -4, 0, ComponentColour.BLACK.dec(), ComponentHelper.locString("cosmosportals.guide.three_body_one_"));
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_three", " 2 ]"), false);
		} 
		
		else if (this.currPage == 4) {
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_three", " 3 ]"), false);
		} 
		
		else if (this.currPage == 5) {
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_three", " 4 ]"), false);
		} 
		
		//Modules
		else if (this.currPage == 6) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -8, ComponentColour.LIGHT_BLUE.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.four_heading"), false);

			CosmosUISystem.FontRenderer.drawWrappedStringBR(graphics, font, screen_coords, 104, 4, 0, ComponentColour.BLACK.dec(), ComponentHelper.locString("cosmosportals.guide.four_body"));
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_four", " 1 ]"), false);
		} 
		
		else if (this.currPage == 7) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -8, ComponentColour.LIGHT_BLUE.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.four_heading_one"), false);
			CosmosUISystem.Setup.setTextureWithColour(graphics.pose(), FLAT_TEXTURES, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, (202) / 2 - 16, 40, 192, 224, 32, 32, FLAT_TEXTURES);
			
			CosmosUISystem.FontRenderer.drawWrappedStringBR(graphics, font, screen_coords, 104, 50, 0, ComponentColour.BLACK.dec(), ComponentHelper.locString("cosmosportals.guide.four_body"));
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_four", " 1 ]"), false);
		} 

		else if (this.currPage == 8) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -8, ComponentColour.GREEN.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.five_heading"), false);
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_five", " 0 ]"), false);
		}
		
		else if (this.currPage == 9) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -8, ComponentColour.GREEN.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.five_heading_one"), false);
			
			CosmosUISystem.Setup.setTextureWithColour(graphics.pose(), FLAT_TEXTURES, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 30,  40, 0,   128, 20, 20, FLAT_TEXTURES);
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 55,  40, 20,  128, 20, 20, FLAT_TEXTURES);
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 80,  40, 40,  128, 20, 20, FLAT_TEXTURES);
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 105, 40, 60,  128, 20, 20, FLAT_TEXTURES);
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 130, 40, 80,  128, 20, 20, FLAT_TEXTURES);
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 155, 40, 100, 128, 20, 20, FLAT_TEXTURES);
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_five", " 1 ]"), false);
		} 

		else if (this.currPage == 10) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -8, ComponentColour.GREEN.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.five_heading_two"), false);

			//CosmosUISystem.setTexture(graphics, FLAT_TEXTURES, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_five", " 2 ]"), false);
		} 
		
		else if (this.currPage == 11) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -8, ComponentColour.GREEN.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.five_heading_three"), false);

			//CosmosUISystem.setTexture(graphics, FLAT_TEXTURES, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_five", " 3 ]"), false);
		} 

		else if (this.currPage == 12) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -8, ComponentColour.GREEN.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.five_heading_four"), false);
			
			//CosmosUISystem.setTexture(graphics, FLAT_TEXTURES, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_five", " 4 ]"), false);
		} 
		
		else if (this.currPage == 13) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -10, ComponentColour.DARK_GREEN.dec(),ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.six_heading"), false);
			
			CosmosUISystem.Setup.setTextureWithColour(graphics.pose(), FLAT_TEXTURES, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, (202) / 2 - 16, 40, 192, 96, 32, 32, FLAT_TEXTURES);
			
			CosmosUISystem.FontRenderer.drawWrappedStringBR(graphics, font, screen_coords, 104, 45, 0, ComponentColour.BLACK.dec(), ComponentHelper.locString("cosmosportals.guide.six_body"));
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_six", " Intro ]"), false);
		}
		
		else if (this.currPage >= 14 && this.currPage <= 15) {
			CosmosUISystem.Setup.setTextureWithColourAlpha(graphics.pose(), FLAT_TEXTURES, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 202 / 2 - 30, 225 / 2 - 30, 128, 0, 64, 64, FLAT_TEXTURES);

			CosmosUISystem.Setup.setTextureWithColour(graphics.pose(), this.getTexture(), new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 30, 35, 202, 0, 54, 74, this.getTexture());
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 30, 125, 202, 0, 54, 74, this.getTexture());
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 123, 35, 202, 0, 54, 74, this.getTexture());
			CosmosUISystem.Render.renderStaticElement(graphics, screen_coords, 123, 125, 202, 0, 54, 74, this.getTexture());

			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_six", " " + (this.currPage - 13) + " ]"), false);
			
			if (this.currPage == 14) {
				CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, -10, ComponentColour.DARK_GREEN.dec(),ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.six_heading_one"), false);
				
				graphics.drawString(this.font, Value.BOLD + "8", screen_coords[0] + 67, screen_coords[1] + 102, ComponentColour.BLACK.dec());
				graphics.drawString(this.font, Value.BOLD + "2", screen_coords[0] + 160, screen_coords[1] + 102, ComponentColour.BLACK.dec());
				graphics.drawString(this.font, Value.BOLD + "2", screen_coords[0] + 67, screen_coords[1] + 192, ComponentColour.BLACK.dec());
			}
			
			if (this.currPage == 15) {
				graphics.drawString(this.font, Value.BOLD + "2", screen_coords[0] + 160, screen_coords[1] + 102, ComponentColour.BLACK.dec());
			}
		}
		
		else if (this.currPage == 16) {
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 20, ComponentColour.RED.dec(), ComponentHelper.locString(Value.UNDERLINE, "cosmosportals.guide.seven_heading"), false);
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 40, ComponentColour.POCKET_PURPLE_LIGHT.dec(), "TheCosmicNebula", false);
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 50, ComponentColour.POCKET_PURPLE_LIGHT.dec(), "(Lead Programmer)", false);
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 70, ComponentColour.BLUE.dec(), "XCompWiz + Team", false);
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 80, ComponentColour.BLUE.dec(), "(Original Mod, Concept)", false);
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 90, ComponentColour.BLUE.dec(), "(Mystcraft Portals)", false);
			
			/*
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 40, ComponentColour.DARK_GREEN.dec(), "Apolybrium", false);
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 50, ComponentColour.DARK_GREEN.dec(), "(Texture Artist, Creative Input)", false);
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 60, ComponentColour.DARK_GREEN.dec(), "(Sound Design)", false);
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 80, ComponentColour.BLUE.dec(), "Scarlet Spark", false);
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 90, ComponentColour.BLUE.dec(), "(Lead Beta Tester)", false);
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 100, ComponentColour.BLUE.dec(), "(Creative Lead, Ideas)", false);
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 120, ComponentColour.PURPLE.dec(), "Rechalow", false);
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 130, ComponentColour.PURPLE.dec(), "(Chinese Translation)", false);
			*/
			
			CosmosUISystem.FontRenderer.drawCenteredString(graphics, font, screen_coords, 104, 174, ComponentColour.GRAY.dec(), ComponentHelper.locString("[ ", "cosmosportals.guide.foot_seven", " 1 ]"), false);
		}
		
		//CosmosUISystem.setTexture(graphics, TEXTURE, new float[] { 1.0F, 1.0F, 1.0F, 1.0F });
		this.renderComponentHoverEffect(graphics, Style.EMPTY, mouseX, mouseY);
	}
	
	public void renderComponentHoverEffect(GuiGraphics graphics, Style style, int mouseX, int mouseY) {
		int[] screen_coords = CosmosUISystem.Init.getScreenCoords(this, 202, 225);
		
		if (this.buttonExit.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.RED, "cosmosportals.guide.button_one"), mouseX, mouseY);
		} else if (this.buttonHome.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.GREEN, "cosmosportals.guide.button_two"), mouseX, mouseY);
		} 
		
		else if (this.buttonNextPage.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.guide.button_three"), mouseX, mouseY);
		} else if (this.buttonPreviousPage.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.LIGHT_GRAY, "cosmosportals.guide.button_four"), mouseX, mouseY);
		} 
		
		else if (this.tabIntroduction.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.GRAY, "bold", "cosmosportals.guide.two_heading"), mouseX, mouseY);
		} else if (this.tabPortals.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.CYAN, "bold", "cosmosportals.guide.three_heading"), mouseX, mouseY);
		} else if (this.tabItems.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.LIGHT_BLUE, "bold", "cosmosportals.guide.four_heading"), mouseX, mouseY);
		} else if (this.tabConfiguration.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.GREEN, "bold", "cosmosportals.guide.five_heading"), mouseX, mouseY);
		}  else if (this.tabRecipes.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.DARK_GREEN, "bold", "cosmosportals.guide.six_heading"), mouseX, mouseY);
		} else if (this.tabCredits.isMouseOver(mouseX, mouseY)) {
			graphics.renderTooltip(this.font, ComponentHelper.style(ComponentColour.RED, "bold", "cosmosportals.guide.seven_tab"), mouseX, mouseY);
		}
		
		/*
		else if (this.tabPlaceholder.isMouseOver(mouseX, mouseY)) {
			this.renderTooltip(graphics, ComponentHelper.style(ComponentColour.BLUE, true, "cosmosportals.guide.four_heading"), mouseX, mouseY);
		} else if (this.tabPlaceholder2.isMouseOver(mouseX, mouseY)) {
			this.renderTooltip(graphics, ComponentHelper.style(ComponentColour.GRAY, true, "cosmosportals.guide.seven_heading"), mouseX, mouseY);
		}
		*/
		
		else if (this.uiModeButton.isMouseOver(mouseX, mouseY)) {
			Component[] comp = new Component[] { 
				ComponentHelper.style(ComponentColour.WHITE, "cosmoslibrary.gui.ui_mode.info"),
				ComponentHelper.style(ComponentColour.GRAY, "cosmoslibrary.gui.ui_mode.value").append(this.getUIMode().getColouredComp())
			};
				
				graphics.renderComponentTooltip(this.font, Arrays.asList(comp), mouseX, mouseY);
		}
		
		if (this.currPage == 14) {
			this.renderCraftingGrid(graphics, screen_coords, mouseX, mouseY, new int[] { 1, 3, 1, 3, 5, 3, 1, 3, 1, 6 }, 0);
			this.renderCraftingGrid(graphics, screen_coords, mouseX, mouseY, new int[] { -1, 5, -1, 5, 4, 5, -1, 5, -1, 8 }, 1);
			
			this.renderCraftingGrid(graphics, screen_coords, mouseX, mouseY, new int[] { -1, 5, -1, 5, 2, 5, -1, 5, -1, 7 }, 2);
			this.renderCraftingGrid(graphics, screen_coords, mouseX, mouseY, new int[] { 6, 7, 6, 7, 8, 7, 6, 7, 6, 9 }, 3);
		}
		
		else if (this.currPage == 15) {
			this.renderCraftingGrid(graphics, screen_coords, mouseX, mouseY, new int[] { 6, 6, 6, 6, 6, 6, 6, 6, 6, 10 }, 0);
			this.renderCraftingGrid(graphics, screen_coords, mouseX, mouseY, new int[] { 6, 5, 6, 5, 10, 5, 6, 5, 6, 11 }, 1);

			this.renderCraftingGrid(graphics, screen_coords, mouseX, mouseY, new int[] { 11, 7, 11, 7, 8, 7, 11, 7, 11, 12 }, 2);
			//this.renderCraftingGrid(graphics, screen_coords, mouseX, mouseY, new int[] { 6, 7, 6, 7, 8, 7, 6, 7, 6, 9 }, 3);
		} 

		
		graphics.renderComponentHoverEffect(this.font, style, mouseX, mouseY);
	}
	
	protected void drawRenderableWidgets() {
		this.clearWidgets();
		
		this.uiModeButton = this.addRenderableWidget(new CosmosButtonUIMode(this.getUIMode(), this.width / 2 + 71, this.height / 2 - 90, false, true, true, ComponentHelper.empty(), (button) -> { this.changeUIMode(); } ));
		
		this.buttonNextPage = this.addRenderableWidget(new GuideChangeButton(this.width / 2 + 58, this.height / 2 + 92, true, (p_214159_1_) -> { this.nextPage(); }, this.pageTurnSounds, this.getTexture()));
		this.buttonPreviousPage = this.addRenderableWidget(new GuideChangeButton(this.width / 2 - 79, this.height / 2 + 92, false, (p_214158_1_) -> { this.previousPage(); }, this.pageTurnSounds, this.getTexture()));
		
		this.buttonExit = this.addRenderableWidget(new GuideButton(this.width / 2 + 70, this.height / 2 - 105, 13, 0, ComponentColour.WHITE.dec(), this.getTexture(), (button) -> { this.onClose(); }));
		this.buttonHome = this.addRenderableWidget(new GuideButton(this.width / 2 + 54, this.height / 2 - 105, 13, 1, ComponentColour.WHITE.dec(), this.getTexture(), (button) -> { this.showPage(0); }));
		
		this.tabIntroduction = this.addRenderableWidget(new GuideButton(this.width / 2 + 85, this.height / 2 - 106, ComponentColour.GRAY.dec(), this.getTexture(), (button) -> { this.showPage(1); }));
		this.tabPortals = this.addRenderableWidget(new GuideButton(this.width / 2 + 85, this.height / 2 - 80, ComponentColour.CYAN.dec(), this.getTexture(), (button) -> { this.showPage(2); }));
		this.tabItems = this.addRenderableWidget(new GuideButton(this.width / 2 + 85, this.height / 2 - 54, ComponentColour.LIGHT_BLUE.dec(), this.getTexture(), (button) -> { this.showPage(6); }));
		this.tabConfiguration = this.addRenderableWidget(new GuideButton(this.width / 2 + 85, this.height / 2 - 28, ComponentColour.LIME.dec(), this.getTexture(), (button) -> { this.showPage(8); }));
		this.tabRecipes = this.addRenderableWidget(new GuideButton(this.width / 2 + 85, this.height / 2 - 2, ComponentColour.DARK_GREEN.dec(), this.getTexture(), (button) -> { this.showPage(13); }));
		this.tabCredits = this.addRenderableWidget(new GuideButton(this.width / 2 + 85, this.height / 2 + 24, ComponentColour.RED.dec(), this.getTexture(), (button) -> { this.showPage(16); }));

		
		this.tabPlaceholder = this.addRenderableWidget(new GuideButton(this.width / 2 + 85, this.height / 2 + 50, ComponentColour.BLACK.dec(), this.getTexture(), (button) -> { }));
		this.tabPlaceholder2 = this.addRenderableWidget(new GuideButton(this.width / 2 + 85, this.height / 2 + 76, ComponentColour.BLACK.dec(), this.getTexture(), (button) -> { }));
		
		this.tabPlaceholder.active = false;
		this.tabPlaceholder2.active = false;
			
		this.updateButtons();
	}

	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (super.keyPressed(keyCode, scanCode, modifiers)) {
			return true;
		} else {
			switch (keyCode) {
			case 266:
				this.buttonPreviousPage.onPress();
				return true;
			case 267:
				this.buttonNextPage.onPress();
				return true;
			default:
				return false;
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean handleComponentClicked(Style style) {
		ClickEvent clickevent = style.getClickEvent();
		if (clickevent == null) {
			return false;
		} else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
			String s = clickevent.getValue();

			try {
				int i = Integer.parseInt(s) - 1;
				return this.showPage(i);
			} catch (Exception exception) {
				return false;
			}
		} else {
			boolean flag = super.handleComponentClicked(style);
			if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
				this.minecraft.setScreen((Screen) null);
			}

			return flag;
		}
	}

	public ResourceLocation getTexture() {
		EnumUIMode mode = this.getUIMode();
		
		if (mode.equals(EnumUIMode.DARK)) {
			return CosmosPortalsReference.GUIDE[1];
		} else {
			return CosmosPortalsReference.GUIDE[0];
		}
	}
	
	public EnumUIMode getUIMode() {
		if (this.stack != null) {
			return ItemPortalGuide.getUIMode(this.stack);
		}
		
		return EnumUIMode.DARK;
	}

	private void changeUIMode() {
		PacketDistributor.sendToServer(new PacketGuideUpdate(this.playerUUID, this.currPage, this.getUIMode().getNextState()));
		ItemPortalGuide.setUIMode(this.stack, this.getUIMode().getNextState());
	}

	private void updateButtons() {
		this.buttonNextPage.visible = this.currPage < this.pageCount - 1;
		this.buttonPreviousPage.visible = this.currPage > 0;
	}

	public boolean showPage(int pageNum) {
		int i = Mth.clamp(pageNum, 0, this.pageCount - 1);
		if (i != this.currPage) {
			this.currPage = i;
			this.updateButtons();
			return true;
		} else {
			return false;
		}
		
	}
	
	@Override
	public void onClose() {
		PacketDistributor.sendToServer(new PacketGuideUpdate(this.playerUUID, this.currPage, null));
		super.onClose();
	}

	protected void previousPage() {
		if (this.currPage > 0) {
			--this.currPage;
		}
		this.updateButtons();
	}
	
	protected void nextPage() {
		if (this.currPage < this.pageCount - 1) {
			++this.currPage;
		}
		this.updateButtons();
	}
	
	@SuppressWarnings("unused")
	private boolean shouldDrawRecipe() {
		return getRecipeType() >= 0;
	}

	private int getRecipeType() {
		int type = -1;

		if (currPage == 0) {
			type = 0;
		} else if (currPage == 5) {
			type = 1;
		} else if (currPage == 6) {
			type = 2;
		}

		return type;
	}
	
	public void renderCraftingGrid(GuiGraphics graphics, int[] screen_coords, int mouseX, int mouseY, int[] ref, int grid_ref) {
		int[] LX = new int[] { 31, 49, 67 }; //left to right [L]
		int[] RX = new int[] { 124, 142, 160 }; //left to right [R]
		int[] TY = new int[] { 36, 54, 72, 92 }; //top to bottom [T]
		int[] BY = new int[] { 126, 144, 162, 182 }; // top to bottom [B]
		
		int[] SLX = new int[] { 31, 67, 49,  124, 160, 142 };
		
		int[] STY = new int[] { 36, 56, 78, 98,  120, 140, 162, 182 };
		
		final ItemStack[] items = new ItemStack[] {
			ItemStack.EMPTY, // 0
			
			new ItemStack(Items.IRON_INGOT), //  1
			new ItemStack(Items.DIAMOND), //  2
			new ItemStack(Items.COPPER_INGOT), //  3
			new ItemStack(Items.ENDER_PEARL), //  4

			new ItemStack(ModRegistrationManager.COSMIC_MATERIAL.get()), // 5
			new ItemStack(ModRegistrationManager.COSMIC_INGOT.get()), // 6
			new ItemStack(ModRegistrationManager.COSMIC_GEM.get()), // 7
			new ItemStack(ModRegistrationManager.COSMIC_PEARL.get()), // 8
			
			new ItemStack(ModRegistrationManager.DIMENSION_CONTAINER.get()), // 9

			new ItemStack(ModRegistrationManager.COSMIC_BLOCK.get()), // 10
			new ItemStack(ModRegistrationManager.PORTAL_FRAME.get()), // 11
			new ItemStack(ModRegistrationManager.PORTAL_DOCK.get()), // 12
		};
		
		if (grid_ref == 0) {
			//Top Left
			
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, LX[0], TY[0], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, LX[1], TY[0], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, LX[2], TY[0], mouseX, mouseY, true); }// 2
			if (ref[3] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[3]], screen_coords, LX[0], TY[1], mouseX, mouseY, true); }// 3
			if (ref[4] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[4]], screen_coords, LX[1], TY[1], mouseX, mouseY, true); }// 4
			if (ref[5] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[5]], screen_coords, LX[2], TY[1], mouseX, mouseY, true); }// 5
			if (ref[6] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[6]], screen_coords, LX[0], TY[2], mouseX, mouseY, true); }// 6
			if (ref[7] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[7]], screen_coords, LX[1], TY[2], mouseX, mouseY, true); }// 7
			if (ref[8] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[8]], screen_coords, LX[2], TY[2], mouseX, mouseY, true); }// 8
			if (ref[9] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[9]], screen_coords, LX[1], TY[3], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 2) {
			
			//Bottom Left
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, LX[0], BY[0], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, LX[1], BY[0], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, LX[2], BY[0], mouseX, mouseY, true); }// 2
			if (ref[3] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[3]], screen_coords, LX[0], BY[1], mouseX, mouseY, true); }// 3
			if (ref[4] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[4]], screen_coords, LX[1], BY[1], mouseX, mouseY, true); }// 4
			if (ref[5] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[5]], screen_coords, LX[2], BY[1], mouseX, mouseY, true); }// 5
			if (ref[6] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[6]], screen_coords, LX[0], BY[2], mouseX, mouseY, true); }// 6
			if (ref[7] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[7]], screen_coords, LX[1], BY[2], mouseX, mouseY, true); }// 7
			if (ref[8] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[8]], screen_coords, LX[2], BY[2], mouseX, mouseY, true); }// 8
			if (ref[9] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[9]], screen_coords, LX[1], BY[3], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 1) {
			
			//Top Right
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, RX[0], TY[0], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, RX[1], TY[0], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, RX[2], TY[0], mouseX, mouseY, true); }// 2
			if (ref[3] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[3]], screen_coords, RX[0], TY[1], mouseX, mouseY, true); }// 3
			if (ref[4] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[4]], screen_coords, RX[1], TY[1], mouseX, mouseY, true); }// 4
			if (ref[5] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[5]], screen_coords, RX[2], TY[1], mouseX, mouseY, true); }// 5
			if (ref[6] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[6]], screen_coords, RX[0], TY[2], mouseX, mouseY, true); }// 6
			if (ref[7] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[7]], screen_coords, RX[1], TY[2], mouseX, mouseY, true); }// 7
			if (ref[8] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[8]], screen_coords, RX[2], TY[2], mouseX, mouseY, true); }// 8
			if (ref[9] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[9]], screen_coords, RX[1], TY[3], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 3) {
			
			//Bottom Right
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, RX[0], BY[0], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, RX[1], BY[0], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, RX[2], BY[0], mouseX, mouseY, true); }// 2
			if (ref[3] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[3]], screen_coords, RX[0], BY[1], mouseX, mouseY, true); }// 3
			if (ref[4] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[4]], screen_coords, RX[1], BY[1], mouseX, mouseY, true); }// 4
			if (ref[5] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[5]], screen_coords, RX[2], BY[1], mouseX, mouseY, true); }// 5
			if (ref[6] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[6]], screen_coords, RX[0], BY[2], mouseX, mouseY, true); }// 6
			if (ref[7] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[7]], screen_coords, RX[1], BY[2], mouseX, mouseY, true); }// 7
			if (ref[8] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[8]], screen_coords, RX[2], BY[2], mouseX, mouseY, true); }// 8
			if (ref[9] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[9]], screen_coords, RX[1], BY[3], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 10) {
			
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, SLX[0], STY[0], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, SLX[1], STY[0], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, SLX[2], STY[1], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 11) {
			
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, SLX[0], STY[2], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, SLX[1], STY[2], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, SLX[2], STY[3], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 12) {
			
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, SLX[0], STY[4], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, SLX[1], STY[4], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, SLX[2], STY[5], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 13) {
			
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, SLX[0], STY[6], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, SLX[1], STY[6], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, SLX[2], STY[7], mouseX, mouseY, true); }// Out
		}else if (grid_ref == 14) {
			
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, SLX[3], STY[0], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, SLX[4], STY[0], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, SLX[5], STY[1], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 15) {
			
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, SLX[3], STY[2], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, SLX[4], STY[2], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, SLX[5], STY[3], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 16) {
			
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, SLX[3], STY[4], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, SLX[4], STY[4], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, SLX[5], STY[5], mouseX, mouseY, true); }// Out
		} else if (grid_ref == 17) {
			
			if (ref[0] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[0]], screen_coords, SLX[3], STY[6], mouseX, mouseY, true); }// 0
			if (ref[1] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[1]], screen_coords, SLX[4], STY[6], mouseX, mouseY, true); }// 1
			if (ref[2] != -1) { CosmosUISystem.Render.renderItemStack(this, font, graphics, items[ref[2]], screen_coords, SLX[5], STY[7], mouseX, mouseY, true); }// Out
		}
	}

}