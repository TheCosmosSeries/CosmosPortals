package com.tcn.cosmosportals.core.network.packet;

import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.network.PortalPacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PacketNextSlot(BlockPos pos, boolean forward) implements CustomPacketPayload, PortalPacket {

	public static final CustomPacketPayload.Type<PacketNextSlot> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "packet_next_slot"));

	public static final StreamCodec<ByteBuf, PacketNextSlot> STREAM_CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC,
		PacketNextSlot::pos, 
		ByteBufCodecs.BOOL,
		PacketNextSlot::forward, 
		PacketNextSlot::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
