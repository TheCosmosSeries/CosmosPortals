package com.tcn.cosmosportals.core.management;

import com.tcn.cosmoslibrary.common.event.PortalEvent;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

public class ModEventFactory {
	
	public static boolean onPortalTravel(Entity entityIn, BlockPos entityPosIn, BlockPos destPosIn, ResourceLocation destDimensionIn) {
		return !(NeoForge.EVENT_BUS.post(new PortalEvent.PortalTravel(entityIn, entityPosIn, destPosIn, destDimensionIn, true)).isCanceled());
	}
	
	public static boolean onContainerLink(Player entityIn, BlockPos entityPosIn, BlockPos destPosIn, ResourceLocation destDimensionIn) {
		return !(NeoForge.EVENT_BUS.post(new PortalEvent.LinkContainer(entityIn, entityPosIn, destPosIn, destDimensionIn, true)).isCanceled());
	}
}