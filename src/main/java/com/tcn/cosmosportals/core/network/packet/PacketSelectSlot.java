package com.tcn.cosmosportals.core.network.packet;

import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.network.PortalPacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PacketSelectSlot(BlockPos pos, boolean left) implements CustomPacketPayload, PortalPacket{
	
	public static final CustomPacketPayload.Type<PacketSelectSlot> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "packet_select_slot"));
	
	public static final StreamCodec<ByteBuf, PacketSelectSlot> STREAM_CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC,
			PacketSelectSlot::pos,
			ByteBufCodecs.BOOL,
			PacketSelectSlot::left,
			PacketSelectSlot::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
