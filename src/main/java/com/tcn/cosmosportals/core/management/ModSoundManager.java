package com.tcn.cosmosportals.core.management;

import com.tcn.cosmosportals.CosmosPortals;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSoundManager {
	
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CosmosPortals.MOD_ID);
	
	public static final DeferredHolder<SoundEvent, SoundEvent> PORTAL_TRAVEL = SOUNDS.register("portal_travel", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "portal_travel")));
	
	public static final DeferredHolder<SoundEvent, SoundEvent> PORTAL_CREATE = SOUNDS.register("portal_create", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "portal_create")));
	public static final DeferredHolder<SoundEvent, SoundEvent> PORTAL_DESTROY = SOUNDS.register("portal_destroy", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "portal_destroy")));
	
	public static void register(IEventBus bus) {
		SOUNDS.register(bus);
	}
}