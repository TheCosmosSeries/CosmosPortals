package com.tcn.cosmosportals.core.network.packet;

import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.network.PortalPacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PacketPortalDock(BlockPos pos, int id) implements CustomPacketPayload, PortalPacket {

	public static final CustomPacketPayload.Type<PacketPortalDock> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "packet_portal_dock"));

	public static final StreamCodec<ByteBuf, PacketPortalDock> STREAM_CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC,
		PacketPortalDock::pos, 
		ByteBufCodecs.VAR_INT,
		PacketPortalDock::id, 
		PacketPortalDock::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}