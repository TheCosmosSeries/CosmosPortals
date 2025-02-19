package com.tcn.cosmosportals.core.management;

import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.blockentity.AbstractBlockEntityPortalDock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = CosmosPortals.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModGameEventsManager {
	
	@SubscribeEvent
	public static void onBlockBreakEvent(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		
		if (player != null) {
			Level level = player.level();
			BlockPos pos = event.getPos();
			
			BlockEntity blockEntity = level.getBlockEntity(pos);
			
			if (blockEntity instanceof AbstractBlockEntityPortalDock dockEntity) {
				if (!dockEntity.checkIfOwner(player)) {
					event.setCanceled(true);
					
					level.setBlockAndUpdate(pos, level.getBlockState(pos));
				}
			}
		}
	}
}