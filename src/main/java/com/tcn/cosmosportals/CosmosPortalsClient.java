package com.tcn.cosmosportals;

import com.tcn.cosmosportals.client.screen.ScreenConfigurationCommon;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = CosmosPortals.MOD_ID, dist = Dist.CLIENT)
public class CosmosPortalsClient {

	public CosmosPortalsClient(ModContainer container) {
		container.registerExtensionPoint(IConfigScreenFactory.class, ScreenConfigurationCommon::new);
	}
}
