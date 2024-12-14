package com.tcn.cosmosportals.core.management;

import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.network.ServerPacketHandler;
import com.tcn.cosmosportals.core.network.packet.PacketColour;
import com.tcn.cosmosportals.core.network.packet.PacketGuideUpdate;
import com.tcn.cosmosportals.core.network.packet.PacketNextSlot;
import com.tcn.cosmosportals.core.network.packet.PacketPortalDock;
import com.tcn.cosmosportals.core.network.packet.PacketWorkbenchName;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CosmosPortals.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModPacketManager {
	
	@SubscribeEvent
	public static void register(final RegisterPayloadHandlersEvent event) {
	    final PayloadRegistrar registrar = event.registrar("1");
	    registrar.playToServer(PacketColour.TYPE, PacketColour.STREAM_CODEC, ServerPacketHandler::handleDataOnNetwork);
	    registrar.playToServer(PacketGuideUpdate.TYPE, PacketGuideUpdate.STREAM_CODEC, ServerPacketHandler::handleDataOnNetwork);
	    registrar.playToServer(PacketNextSlot.TYPE, PacketNextSlot.STREAM_CODEC, ServerPacketHandler::handleDataOnNetwork);
	    registrar.playToServer(PacketPortalDock.TYPE, PacketPortalDock.STREAM_CODEC, ServerPacketHandler::handleDataOnNetwork);
	    registrar.playToServer(PacketWorkbenchName.TYPE, PacketWorkbenchName.STREAM_CODEC, ServerPacketHandler::handleDataOnNetwork);
	}
}