package com.tcn.cosmosportals.core.network;

import com.tcn.cosmoslibrary.common.util.CosmosUtil;
import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.blockentity.AbstractBlockEntityPortalDock;
import com.tcn.cosmosportals.core.blockentity.BlockEntityContainerWorkbench;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockUpgraded4;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockUpgraded8;
import com.tcn.cosmosportals.core.item.ItemPortalGuide;
import com.tcn.cosmosportals.core.network.packet.PacketColour;
import com.tcn.cosmosportals.core.network.packet.PacketGuideUpdate;
import com.tcn.cosmosportals.core.network.packet.PacketNextSlot;
import com.tcn.cosmosportals.core.network.packet.PacketPortalDock;
import com.tcn.cosmosportals.core.network.packet.PacketWorkbenchName;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ServerPacketHandler {

	public static void handleDataOnNetwork(final PortalPacket data, final IPayloadContext context) {
		if (data instanceof PacketColour packet) {
			context.enqueueWork(() -> {
				ServerLevel world = (ServerLevel) context.player().level();
				BlockEntity tile = world.getBlockEntity(packet.pos());
				
				if (tile instanceof AbstractBlockEntityPortalDock tileDock) {
					tileDock.updateColour(packet.colour(), packet.slotIndex());
					tileDock.sendUpdates(true);
				} else if (tile instanceof BlockEntityContainerWorkbench tileDock) {
					tileDock.updateColour(packet.colour());
					tileDock.sendUpdates(true);
				} else {
					CosmosPortals.CONSOLE.debugWarn("[Packet Delivery Failure] <portaldock> Block Entity not equal to expected.");
				}			
			});
		}
		
		if (data instanceof PacketGuideUpdate packet) {
			context.enqueueWork(() -> {
				ServerPlayer player = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(packet.playerUUID());
				
				ItemStack stack = CosmosUtil.getStack(player);
				
				if (stack != null) {
					if (packet.pageNum() > -1) {
						ItemPortalGuide.setPage(stack, packet.pageNum());
					}
					
					if (packet.mode() != null) {
						ItemPortalGuide.setUIMode(stack, packet.mode());
					}
				}
			});
		}
		
		if (data instanceof PacketNextSlot packet) {
			context.enqueueWork(() -> {
				ServerLevel world = (ServerLevel) context.player().level();
				BlockEntity entity = world.getBlockEntity(packet.pos());
				
				if (entity instanceof BlockEntityPortalDockUpgraded4 tileEntity) {
					tileEntity.selectNextSlot(packet.forward());
				} else if (entity instanceof BlockEntityPortalDockUpgraded8 tileEntity) {
					tileEntity.selectNextSlot(packet.forward());
				} else {
					CosmosPortals.CONSOLE.debugWarn("[Packet Delivery Failure] <portaldockupgraded> Block Entity not equal to expected.");
				}		
			});
		}
		
		if (data instanceof PacketPortalDock packet) {
			context.enqueueWork(() -> {
				ServerLevel world = (ServerLevel) context.player().level();
				BlockEntity entity = world.getBlockEntity(packet.pos());
				
				if (entity instanceof AbstractBlockEntityPortalDock tileEntity) {
					if (packet.id() == 0) {
						tileEntity.toggleRenderLabel();
					} else if (packet.id() == 1) {
						tileEntity.togglePlaySound();
					} else if (packet.id() == 2) {
						tileEntity.toggleEntities(false);
					} else if (packet.id() == 3) {
						tileEntity.toggleParticles();
					} else if (packet.id() == 4) {
						tileEntity.toggleEntities(true);
					} else {
						CosmosPortals.CONSOLE.debugWarn("[Packet Delivery Failure] <portaldock> Setting Id: { " + packet.id() + " } not recognised.");
					}
				} else {
					CosmosPortals.CONSOLE.debugWarn("[Packet Delivery Failure] <portaldock> Block Entity not equal to expected.");
				}			
			});
		}
		
		if (data instanceof PacketWorkbenchName packet) {
			context.enqueueWork(() -> {
				ServerLevel world = (ServerLevel) context.player().level();
				BlockEntity entity = world.getBlockEntity(packet.pos());
				
				if (entity instanceof BlockEntityContainerWorkbench tileEntity) {
					tileEntity.setContainerDisplayName(packet.displayName());
				} else {
					CosmosPortals.CONSOLE.debugWarn("[Packet Delivery Failure] <container_workbench> Block Entity not equal to expected.");
				}
				
			});
		}
	}
}