package com.tcn.cosmosportals;

import com.tcn.cosmoslibrary.runtime.common.CosmosRuntime;
import com.tcn.cosmosportals.client.screen.PortalsConfigScreen;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = CosmosPortals.MOD_ID, dist = Dist.CLIENT)
public class CosmosPortalsClient {

	public CosmosPortalsClient(ModContainer container) {
		CosmosRuntime.Client.regiserConfigScreen(container, PortalsConfigScreen::new);
	}
}
