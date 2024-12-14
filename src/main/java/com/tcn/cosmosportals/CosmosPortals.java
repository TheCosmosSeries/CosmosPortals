package com.tcn.cosmosportals;

import com.tcn.cosmoslibrary.common.runtime.CosmosConsoleManager;
import com.tcn.cosmosportals.core.management.ModConfigManager;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;
import com.tcn.cosmosportals.core.management.ModSoundManager;
import com.tcn.cosmosportals.core.management.ModWorldgenManager;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(CosmosPortals.MOD_ID)
public class CosmosPortals {
	
	//This must NEVER EVER CHANGE!
	public static final String MOD_ID = "cosmosportals";

	public static CosmosConsoleManager CONSOLE = new CosmosConsoleManager(CosmosPortals.MOD_ID, true, true);

	public CosmosPortals(ModContainer container, IEventBus bus) {
		container.registerConfig(ModConfig.Type.COMMON, ModConfigManager.SPEC, "cosmos-portals-common.toml");

		ModRegistrationManager.register(bus);
		ModWorldgenManager.register(bus);
		ModSoundManager.register(bus);
		
		bus.addListener(this::onFMLCommonSetup);
		bus.addListener(this::onFMLClientSetup);
	}

	public void onFMLCommonSetup(final FMLCommonSetupEvent event) {
		CONSOLE = new CosmosConsoleManager(CosmosPortals.MOD_ID, ModConfigManager.getInstance().getDebugMessage(), ModConfigManager.getInstance().getInfoMessage());
		
		CONSOLE.startup("CosmosPortals Common Setup complete.");
	}

	public void onFMLClientSetup(final FMLClientSetupEvent event) {		
		ModRegistrationManager.onFMLClientSetup(event);
		
		CONSOLE.startup("CosmosPortals Client Setup complete.");
	}
}