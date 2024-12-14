package com.tcn.cosmosportals.core.management;

import java.util.function.Supplier;

import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.worldgen.CosmicOreFeature;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModWorldgenManager {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, CosmosPortals.MOD_ID);
	
	public static final Supplier<Feature<?>> COSMIC_ORE = FEATURES.register("ore_cosmic", CosmicOreFeature::new);
	
	public static void register(IEventBus bus) {
		FEATURES.register(bus);
	}
}