package com.tcn.cosmosportals.core.network.packet;

import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.network.PortalPacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PacketWorkbenchName(BlockPos pos, String displayName) implements CustomPacketPayload, PortalPacket {

	public static final CustomPacketPayload.Type<PacketWorkbenchName> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "packet_workbench_name"));

	public static final StreamCodec<ByteBuf, PacketWorkbenchName> STREAM_CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC,
		PacketWorkbenchName::pos, 
		ByteBufCodecs.STRING_UTF8,
		PacketWorkbenchName::displayName, 
		PacketWorkbenchName::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}