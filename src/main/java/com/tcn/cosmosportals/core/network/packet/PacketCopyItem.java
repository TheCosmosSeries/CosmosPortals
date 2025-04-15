package com.tcn.cosmosportals.core.network.packet;

import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.network.PortalPacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PacketCopyItem(BlockPos pos) implements CustomPacketPayload, PortalPacket{
	
	public static final CustomPacketPayload.Type<PacketCopyItem> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "packet_copy_item"));
	
	public static final StreamCodec<ByteBuf, PacketCopyItem> STREAM_CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC,
			PacketCopyItem::pos,
			PacketCopyItem::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
