package com.tcn.cosmosportals.core.network.packet;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.network.PortalPacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PacketColour(BlockPos pos, ComponentColour colour, int slotIndex) implements CustomPacketPayload, PortalPacket {

	public static final CustomPacketPayload.Type<PacketColour> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "packet_colour"));

	public static final StreamCodec<ByteBuf, PacketColour> STREAM_CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC,
		PacketColour::pos, 
		ComponentColour.STREAM_CODEC,
		PacketColour::colour, 
		ByteBufCodecs.VAR_INT,
		PacketColour::slotIndex,
		PacketColour::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}