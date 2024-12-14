package com.tcn.cosmosportals.core.network.packet;

import java.util.UUID;

import com.tcn.cosmoslibrary.common.enums.EnumUIMode;
import com.tcn.cosmosportals.CosmosPortals;
import com.tcn.cosmosportals.core.network.PortalPacket;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PacketGuideUpdate(UUID playerUUID, int pageNum, EnumUIMode mode) implements CustomPacketPayload, PortalPacket {

	public static final CustomPacketPayload.Type<PacketGuideUpdate> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CosmosPortals.MOD_ID, "packet_guide_update"));

	public static final StreamCodec<ByteBuf, PacketGuideUpdate> STREAM_CODEC = StreamCodec.composite(
		UUIDUtil.STREAM_CODEC,
		PacketGuideUpdate::playerUUID, 
		ByteBufCodecs.VAR_INT,
		PacketGuideUpdate::pageNum, 
		EnumUIMode.STREAM_CODEC,
		PacketGuideUpdate::mode,
		PacketGuideUpdate::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}