package com.tcn.cosmosportals.core.management;

import java.util.ArrayList;
import java.util.function.Supplier;

import com.tcn.cosmoslibrary.common.block.CosmosBlock;
import com.tcn.cosmoslibrary.common.item.CosmosItem;
import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmoslibrary.common.lib.ComponentHelper;
import com.tcn.cosmoslibrary.common.runtime.CosmosRuntimeHelper;
import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.client.colour.BlockColour;
import com.tcn.cosmosportals.client.colour.ItemColour;
import com.tcn.cosmosportals.client.container.ContainerContainerWorkbench;
import com.tcn.cosmosportals.client.container.ContainerPortalDock;
import com.tcn.cosmosportals.client.container.ContainerPortalDockUpgraded4;
import com.tcn.cosmosportals.client.container.ContainerPortalDockUpgraded8;
import com.tcn.cosmosportals.client.renderer.RendererContainerWorkbench;
import com.tcn.cosmosportals.client.renderer.RendererDockController4;
import com.tcn.cosmosportals.client.renderer.RendererPortalDock;
import com.tcn.cosmosportals.client.screen.ScreenContainerWorkbench;
import com.tcn.cosmosportals.client.screen.ScreenPortalDock;
import com.tcn.cosmosportals.client.screen.ScreenPortalDockUpgraded4;
import com.tcn.cosmosportals.client.screen.ScreenPortalDockUpgraded8;
import com.tcn.cosmosportals.core.block.BlockContainerWorkbench;
import com.tcn.cosmosportals.core.block.BlockDockController;
import com.tcn.cosmosportals.core.block.BlockPortal;
import com.tcn.cosmosportals.core.block.BlockPortalDock;
import com.tcn.cosmosportals.core.block.BlockPortalDockUpgraded4;
import com.tcn.cosmosportals.core.block.BlockPortalDockUpgraded8;
import com.tcn.cosmosportals.core.block.BlockPortalFrame;
import com.tcn.cosmosportals.core.blockentity.BlockEntityContainerWorkbench;
import com.tcn.cosmosportals.core.blockentity.BlockEntityDockController;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortal;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDock;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockUpgraded4;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockUpgraded8;
import com.tcn.cosmosportals.core.item.BlockItemContainerWorkbench;
import com.tcn.cosmosportals.core.item.BlockItemDockController;
import com.tcn.cosmosportals.core.item.BlockItemPortal;
import com.tcn.cosmosportals.core.item.BlockItemPortalDock;
import com.tcn.cosmosportals.core.item.BlockItemPortalDockUpgraded4;
import com.tcn.cosmosportals.core.item.BlockItemPortalDockUpgraded8;
import com.tcn.cosmosportals.core.item.BlockItemPortalFrame;
import com.tcn.cosmosportals.core.item.ItemCosmicWrench;
import com.tcn.cosmosportals.core.item.ItemPortalContainer;
import com.tcn.cosmosportals.core.item.ItemPortalGuide;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = CosmosPortals.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistrationManager {
	
	/** - DEFERRED REGISTERS - */
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CosmosPortals.MOD_ID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CosmosPortals.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CosmosPortals.MOD_ID);
	public static final ArrayList<Supplier<? extends ItemLike>> TAB_ITEMS = new ArrayList<>();
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CosmosPortals.MOD_ID);
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, CosmosPortals.MOD_ID);
	
	public static final Supplier<CreativeModeTab> COSMOS_PORTALS_ITEM_GROUP = TABS.register("cosmos_portals", 
		() -> CreativeModeTab.builder()
		.title(ComponentHelper.style(ComponentColour.GREEN, "Cosmos Portals"))
		.icon(() -> new ItemStack(ModRegistrationManager.DIMENSION_CONTAINER.get()))
		.displayItems((params, output) -> TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
		.build()
	);
	
	/** - ITEMS - */
	public static final DeferredItem<Item> COSMIC_GUIDE = addToTab(ITEMS.register("item_cosmic_guide", () ->  new ItemPortalGuide(new Item.Properties().stacksTo(1))));
	
	public static final DeferredItem<Item> DIMENSION_CONTAINER_LINKED = addToTab(ITEMS.register("item_dimension_container", () -> new ItemPortalContainer(new Item.Properties().stacksTo(1).fireResistant(), true)));
	public static final DeferredItem<Item> DIMENSION_CONTAINER = addToTab(ITEMS.register("item_dimension_container_unlinked", () -> new ItemPortalContainer(new Item.Properties().stacksTo(16).fireResistant(), false)));

	public static final DeferredItem<Item> COSMIC_MATERIAL = addToTab(ITEMS.register("item_cosmic_material", () -> new CosmosItem(new Item.Properties())));
	public static final DeferredItem<Item> COSMIC_INGOT = addToTab(ITEMS.register("item_cosmic_ingot", () -> new CosmosItem(new Item.Properties())));
	public static final DeferredItem<Item> COSMIC_PEARL = addToTab(ITEMS.register("item_cosmic_pearl", () -> new CosmosItem(new Item.Properties())));
	public static final DeferredItem<Item> COSMIC_GEM = addToTab(ITEMS.register("item_cosmic_gem", () -> new CosmosItem(new Item.Properties())));
	
	public static final DeferredItem<Item> COSMIC_WRENCH = addToTab(ITEMS.register("item_cosmic_wrench", () -> new ItemCosmicWrench(new Item.Properties().stacksTo(1))));

	
	/** - BLOCKS - */
	public static final DeferredBlock<Block> COSMIC_ORE = BLOCKS.register("block_cosmic_ore", () -> new CosmosBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).requiresCorrectToolForDrops().strength(2.0F, 3.0F)));
	public static final DeferredItem<Item> ITEM_COSMIC_ORE = addToTab(ITEMS.register("block_cosmic_ore", () -> new BlockItem(COSMIC_ORE.get(), new Item.Properties())));
	
	public static final DeferredBlock<Block> DEEPSLATE_COSMIC_ORE = BLOCKS.register("block_deepslate_cosmic_ore", () -> new CosmosBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).requiresCorrectToolForDrops().strength(4.0F, 6.0F).sound(SoundType.DEEPSLATE)));
	public static final DeferredItem<Item> ITEM_DEEPSLATE_COSMIC_ORE = addToTab(ITEMS.register("block_deepslate_cosmic_ore", () -> new BlockItem(DEEPSLATE_COSMIC_ORE.get(), new Item.Properties())));
	
	public static final DeferredBlock<Block> COSMIC_BLOCK = BLOCKS.register("block_cosmic", () -> new CosmosBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 7.0F)));
	public static final DeferredItem<Item> ITEM_COSMIC_BLOCK = addToTab(ITEMS.register("block_cosmic", () -> new BlockItem(COSMIC_BLOCK.get(), new Item.Properties())));
	
	public static final DeferredBlock<Block> PORTAL_FRAME = BLOCKS.register("block_portal_frame", () -> new BlockPortalFrame(BlockBehaviour.Properties.of().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(6.0F, 8.0F).lightLevel((state) -> { return 10; })));
	public static final DeferredItem<Item> ITEM_PORTAL_FRAME = addToTab(ITEMS.register("block_portal_frame", () -> new BlockItemPortalFrame(PORTAL_FRAME.get(), new Item.Properties())));
	
	public static final DeferredBlock<Block> BLOCK_PORTAL = BLOCKS.register("block_portal", () -> new BlockPortal(Block.Properties.of().sound(SoundType.METAL).strength(-1, 3600000.0F).noOcclusion().randomTicks().noCollission().lightLevel((state) -> { return 10; } )));
	public static final DeferredItem<Item> ITEM_PORTAL = ITEMS.register("block_portal", () -> new BlockItemPortal(BLOCK_PORTAL.get(), new Item.Properties()));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityPortal>> BLOCK_ENTITY_TYPE_PORTAL = BLOCK_ENTITY_TYPES.register("tile_portal", () -> BlockEntityType.Builder.of(BlockEntityPortal::new, BLOCK_PORTAL.get()).build(null));
	
	public static final DeferredBlock<Block> PORTAL_DOCK = BLOCKS.register("block_portal_dock", () -> new BlockPortalDock(BlockBehaviour.Properties.of().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(6.0F, 8.0F).lightLevel((state) -> { return 7; } )));
	public static final DeferredItem<Item> ITEM_PORTAL_DOCK = addToTab(ITEMS.register("block_portal_dock", () -> new BlockItemPortalDock(PORTAL_DOCK.get(), new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityPortalDock>> BLOCK_ENTITY_TYPE_PORTAL_DOCK = BLOCK_ENTITY_TYPES.register("tile_portal_dock", () -> BlockEntityType.Builder.of(BlockEntityPortalDock::new, PORTAL_DOCK.get()).build(null));
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerPortalDock>> MENU_TYPE_PORTAL_DOCK = MENU_TYPES.register("container_portal_dock", () -> IMenuTypeExtension.create(ContainerPortalDock::new));

	public static final DeferredBlock<Block> PORTAL_DOCK_UPGRADED = BLOCKS.register("block_portal_dock_upgraded4", () -> new BlockPortalDockUpgraded4(BlockBehaviour.Properties.of().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(6.0F, 8.0F).lightLevel((state) -> { return 7; } )));
	public static final DeferredItem<Item> ITEM_PORTAL_DOCK_UPGRADED = addToTab(ITEMS.register("block_portal_dock_upgraded4", () -> new BlockItemPortalDockUpgraded4(PORTAL_DOCK_UPGRADED.get(), new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityPortalDockUpgraded4>> BLOCK_ENTITY_TYPE_PORTAL_DOCK_UPGRADED = BLOCK_ENTITY_TYPES.register("tile_portal_dock_upgraded4", () -> BlockEntityType.Builder.of(BlockEntityPortalDockUpgraded4::new, PORTAL_DOCK_UPGRADED.get()).build(null));
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerPortalDockUpgraded4>> MENU_TYPE_PORTAL_DOCK_UPGRADED = MENU_TYPES.register("container_portal_dock_upgraded4", () -> IMenuTypeExtension.create(ContainerPortalDockUpgraded4::new));

	public static final DeferredBlock<Block> PORTAL_DOCK_UPGRADED8 = BLOCKS.register("block_portal_dock_upgraded8", () -> new BlockPortalDockUpgraded8(BlockBehaviour.Properties.of().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(6.0F, 8.0F).lightLevel((state) -> { return 7; } )));
	public static final DeferredItem<Item> ITEM_PORTAL_DOCK_UPGRADED8 = addToTab(ITEMS.register("block_portal_dock_upgraded8", () -> new BlockItemPortalDockUpgraded8(PORTAL_DOCK_UPGRADED8.get(), new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityPortalDockUpgraded8>> BLOCK_ENTITY_TYPE_PORTAL_DOCK_UPGRADED8 = BLOCK_ENTITY_TYPES.register("tile_portal_dock_upgraded8", () -> BlockEntityType.Builder.of(BlockEntityPortalDockUpgraded8::new, PORTAL_DOCK_UPGRADED8.get()).build(null));
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerPortalDockUpgraded8>> MENU_TYPE_PORTAL_DOCK_UPGRADED8 = MENU_TYPES.register("container_portal_dock_upgraded8", () -> IMenuTypeExtension.create(ContainerPortalDockUpgraded8::new));
	
	public static final DeferredBlock<Block> DOCK_CONTROLLER = BLOCKS.register("block_dock_controller", () -> new BlockDockController(BlockBehaviour.Properties.of()));
	public static final DeferredItem<Item> ITEM_DOCK_CONTROLLER = addToTab(ITEMS.register("block_dock_controller", () -> new BlockItemDockController(DOCK_CONTROLLER.get(), new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityDockController>> BLOCK_ENTITY_TYPE_DOCK_CONTROLLER = BLOCK_ENTITY_TYPES.register("tile_dock_controller", () -> BlockEntityType.Builder.of(BlockEntityDockController::new, DOCK_CONTROLLER.get()).build(null));
	
	public static final DeferredBlock<Block> CONTAINER_WORKBENCH = BLOCKS.register("block_container_workbench", () -> new BlockContainerWorkbench(BlockBehaviour.Properties.of().sound(SoundType.METAL).requiresCorrectToolForDrops().strength(4.0F,6.0F).noOcclusion()));
	public static final DeferredItem<Item> ITEM_CONTAINER_WORKBENCH = addToTab(ITEMS.register("block_container_workbench", () -> new BlockItemContainerWorkbench(CONTAINER_WORKBENCH.get(), new Item.Properties())));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityContainerWorkbench>> BLOCK_ENTITY_TYPE_CONTAINER_WORKBENCH = BLOCK_ENTITY_TYPES.register("tile_container_workbench", () -> BlockEntityType.Builder.of(BlockEntityContainerWorkbench::new, CONTAINER_WORKBENCH.get()).build(null));
	public static final DeferredHolder<MenuType<?>, MenuType<ContainerContainerWorkbench>> MENU_TYPE_CONTAINER_WORKBENCH = MENU_TYPES.register("container_container_workbench", () -> IMenuTypeExtension.create(ContainerContainerWorkbench::new));
	
	public static void register(IEventBus bus) {
		ITEMS.register(bus);
		BLOCKS.register(bus);
		
		BLOCK_ENTITY_TYPES.register(bus);
		MENU_TYPES.register(bus);
		
		TABS.register(bus);
	}

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) { }
    
	@SubscribeEvent
	public static void onBlockEntityRendererRegistry(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(BLOCK_ENTITY_TYPE_PORTAL_DOCK.get(), RendererPortalDock::new);
		event.registerBlockEntityRenderer(BLOCK_ENTITY_TYPE_PORTAL_DOCK_UPGRADED.get(), RendererPortalDock::new);
		event.registerBlockEntityRenderer(BLOCK_ENTITY_TYPE_PORTAL_DOCK_UPGRADED8.get(), RendererPortalDock::new);
		event.registerBlockEntityRenderer(BLOCK_ENTITY_TYPE_CONTAINER_WORKBENCH.get(), RendererContainerWorkbench::new);
		event.registerBlockEntityRenderer(BLOCK_ENTITY_TYPE_DOCK_CONTROLLER.get(), RendererDockController4::new);
		CosmosPortals.CONSOLE.startup("BlockEntityRenderer Registration complete.");
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRegisterColourHandlersEventBlock(RegisterColorHandlersEvent.Block event) {
		CosmosRuntimeHelper.registerBlockColours(event, new BlockColour(), 
			PORTAL_FRAME.get(),
			PORTAL_DOCK.get(),
			PORTAL_DOCK_UPGRADED.get(),
			PORTAL_DOCK_UPGRADED8.get(),
			BLOCK_PORTAL.get(),
			DOCK_CONTROLLER.get()
		);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onRegisterColourHandlersEventItem(RegisterColorHandlersEvent.Item event) {
		CosmosRuntimeHelper.registerItemColours(event, new ItemColour(), 
			DIMENSION_CONTAINER_LINKED.get(),
			PORTAL_FRAME.get(),
			PORTAL_DOCK.get(),
			PORTAL_DOCK_UPGRADED.get(),
			PORTAL_DOCK_UPGRADED8.get(),
			DOCK_CONTROLLER.get()
		);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {
		/*CosmosRuntimeHelper.registerSpecialModels(event, CosmosPortals.MOD_ID, 
			"model/dock_controller_button"
		);*/
	}
	
	@SubscribeEvent
	public static void registerMenuScreensEvent(RegisterMenuScreensEvent event) {
		event.register(MENU_TYPE_PORTAL_DOCK.get(), ScreenPortalDock::new);
		event.register(MENU_TYPE_PORTAL_DOCK_UPGRADED.get(), ScreenPortalDockUpgraded4::new);
		event.register(MENU_TYPE_PORTAL_DOCK_UPGRADED8.get(), ScreenPortalDockUpgraded8::new);
		event.register(MENU_TYPE_CONTAINER_WORKBENCH.get(), ScreenContainerWorkbench::new);
	}
	
	@SuppressWarnings("deprecation")
	@OnlyIn(Dist.CLIENT)
	public static void onFMLClientSetup(FMLClientSetupEvent event) {
		RenderType cutoutMipped = RenderType.cutoutMipped();
		RenderType translucent = RenderType.translucent();
		
		CosmosRuntimeHelper.setRenderLayers(cutoutMipped, PORTAL_DOCK.get());
		CosmosRuntimeHelper.setRenderLayers(cutoutMipped, PORTAL_DOCK_UPGRADED.get());
		CosmosRuntimeHelper.setRenderLayers(cutoutMipped, PORTAL_DOCK_UPGRADED8.get());
		CosmosRuntimeHelper.setRenderLayers(translucent, BLOCK_PORTAL.get());
	}

    public static <T extends Item> DeferredItem<T> addToTab(DeferredItem<T> itemLike) {
        TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    public static <A extends Block> DeferredBlock<A> addToTabA(DeferredBlock<A> itemLike) {
        TAB_ITEMS.add(itemLike);
        return itemLike;
    }
    
}