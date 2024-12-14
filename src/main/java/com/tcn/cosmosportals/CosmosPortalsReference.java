package com.tcn.cosmosportals;

import net.minecraft.resources.ResourceLocation;

public class CosmosPortalsReference {
	
	public static final String PRE = CosmosPortals.MOD_ID + ":";

	public static final String RESOURCE = PRE + "textures/";
	public static final String GUI = RESOURCE + "gui/";

	public static final String BLOCKS = PRE + "blocks/";
	public static final String ITEMS = RESOURCE + "items/";
	
	public static final String MODELS = RESOURCE + "models/";
		
	public static final ResourceLocation[] DOCK = new ResourceLocation[] { ResourceLocation.parse(GUI + "dock/background.png"), ResourceLocation.parse(GUI + "dock/background_dark.png") };
	public static final ResourceLocation[] DOCK_SLOTS = new ResourceLocation[] { ResourceLocation.parse(GUI + "dock/slots.png"), ResourceLocation.parse(GUI+ "dock/slots_dark.png") };
	public static final ResourceLocation DOCK_FRAME = ResourceLocation.parse(GUI + "dock/frame.png");
	public static final ResourceLocation DOCK_PORTAL = ResourceLocation.parse(GUI + "dock/portal.png");
	public static final ResourceLocation DOCK_BACKING = ResourceLocation.parse(GUI + "dock/backing.png");
	public static final ResourceLocation DOCK_CONTAINER = ResourceLocation.parse(GUI + "dock/container.png");
	public static final ResourceLocation DOCK_LABEL = ResourceLocation.parse(GUI + "dock/label.png");
	
	public static final ResourceLocation[] DOCK_UPGRADED4 = new ResourceLocation[] { ResourceLocation.parse(GUI + "dock_upgraded/background.png"), ResourceLocation.parse(GUI + "dock_upgraded/background_dark.png") };
	public static final ResourceLocation[] DOCK_SLOTS_UPGRADED4 = new ResourceLocation[] { ResourceLocation.parse(GUI + "dock_upgraded/slots.png"), ResourceLocation.parse(GUI + "dock_upgraded/slots_dark.png") };
	public static final ResourceLocation DOCK_FRAME_UPGRADED4 = ResourceLocation.parse(GUI + "dock_upgraded/frame.png");
	public static final ResourceLocation DOCK_PORTAL_UPGRADED4 = ResourceLocation.parse(GUI + "dock_upgraded/portal.png");
	public static final ResourceLocation DOCK_BACKING_UPGRADED4 = ResourceLocation.parse(GUI + "dock_upgraded/backing.png");
	public static final ResourceLocation DOCK_CONTAINER_UPGRADED4 = ResourceLocation.parse(GUI + "dock_upgraded/container.png");
	public static final ResourceLocation DOCK_LABEL_UPGRADED4 = ResourceLocation.parse(GUI + "dock_upgraded/label.png");
	public static final ResourceLocation DOCK_OVERLAY_ONE_UPGRADED4 = ResourceLocation.parse(GUI + "dock_upgraded/overlay.png");

	public static final ResourceLocation[] DOCK_UPGRADED8 = new ResourceLocation[] { ResourceLocation.parse(GUI + "dock_upgraded_eight/background.png"), ResourceLocation.parse(GUI + "dock_upgraded_eight/background_dark.png") };
	public static final ResourceLocation[] DOCK_SLOTS_UPGRADED8 = new ResourceLocation[] { ResourceLocation.parse(GUI + "dock_upgraded_eight/slots.png"), ResourceLocation.parse(GUI + "dock_upgraded_eight/slots_dark.png") };
	public static final ResourceLocation DOCK_FRAME_UPGRADED8 = ResourceLocation.parse(GUI + "dock_upgraded_eight/frame.png");
	public static final ResourceLocation DOCK_PORTAL_UPGRADED8 = ResourceLocation.parse(GUI + "dock_upgraded_eight/portal.png");
	public static final ResourceLocation DOCK_BACKING_UPGRADED8 = ResourceLocation.parse(GUI + "dock_upgraded_eight/backing.png");
	public static final ResourceLocation DOCK_CONTAINER_UPGRADED8 = ResourceLocation.parse(GUI + "dock_upgraded_eight/container.png");
	public static final ResourceLocation DOCK_LABEL_UPGRADED8 = ResourceLocation.parse(GUI + "dock_upgraded_eight/label.png");
	public static final ResourceLocation DOCK_OVERLAY_ONE_UPGRADED8 = ResourceLocation.parse(GUI + "dock_upgraded_eight/overlay.png");
	
	public static final ResourceLocation[] WORKBENCH = new ResourceLocation[] { ResourceLocation.parse(GUI + "workbench/background.png"), ResourceLocation.parse(GUI + "workbench/background_dark.png") };
	public static final ResourceLocation[] WORKBENCH_SLOTS = new ResourceLocation[] { ResourceLocation.parse(GUI + "workbench/slots.png"), ResourceLocation.parse(GUI + "workbench/slots_dark.png") };
	public static final ResourceLocation[] WORKBENCH_OVERLAY = new ResourceLocation[] { ResourceLocation.parse(GUI + "workbench/overlay.png"), ResourceLocation.parse(GUI + "workbench/overlay_dark.png") };
	
	public static final ResourceLocation[] GUIDE = new ResourceLocation[] { ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "textures/gui/guide/guide.png"), ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "textures/gui/guide/guide_dark.png") };
	public static final ResourceLocation GUIDE_FLAT_TEXTURES = ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "textures/gui/guide/textures_flat.png");
	public static final ResourceLocation GUIDE_BLOCK_TEXTURES = ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "textures/gui/guide/blocks.png");
}