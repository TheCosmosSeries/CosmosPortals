package com.tcn.cosmosportals.core.management;

import org.apache.commons.lang3.tuple.Pair;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public class ModConfigManager {

	public static final ModConfigManager CONFIG;
	public static final ModConfigSpec SPEC;
	
	static {
		{
			final Pair<ModConfigManager, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(ModConfigManager::new);
			CONFIG = specPair.getLeft();
			SPEC = specPair.getRight();
		}
	}
	
	public static void save() {
		SPEC.save();
	}

	private final IntValue portal_maximum_size;
	private final BooleanValue playPortalTravelSounds;
	private final BooleanValue playPortalAmbientSounds;
	private final BooleanValue allowWardenTeleport;
	
	private final BooleanValue info_message;
	private final BooleanValue debug_message;
	
	private final BooleanValue frameConnectedTextures;
	private final BooleanValue portalConnectedTextures;
	private final BooleanValue renderPortalLabels;
	private final BooleanValue renderPortalParticleEffects;
	private final IntValue label_maximum_distance;
	
	ModConfigManager(final ModConfigSpec.Builder builder) {
		builder.push("general");
		{
			portal_maximum_size = builder.comment("Allows you to change the maximum size of Portals. WARNING: Larger portals WILL create lag").defineInRange("internal_height", 5, 2, 9);
			playPortalTravelSounds = builder.comment("Whether this mod will play Portal Travel Sounds").define("travel_sounds", true);
			playPortalAmbientSounds = builder.comment("Whether this mod will play Ambient Portal Sounds").define("ambient_sounds", true);
			allowWardenTeleport = builder.comment("Whether Wardens can travel through Portals").define("warden_teleport", true);
		}
		builder.pop();
		
		builder.push("console_messages");
		{
			info_message = builder.comment("Whether this mod will print [INFO] messages to the console/log").define("info", true);
			debug_message = builder.comment("Whether this mod will print [DEBUG] messages to the console/log").define("debug", false);
		}
		builder.pop();
		
		builder.push("visual");
		{
			frameConnectedTextures = builder.comment("Whether or not Portal Frames use Connected Textures").define("frame_connected_textures", true);
			portalConnectedTextures = builder.comment("Whether or not Portals use Connected Textures").define("portal_connected_textures", true);
			renderPortalLabels = builder.comment("Whether or not Portal Labels are rendered").define("render_portal_labels", true);
			renderPortalParticleEffects = builder.comment("Whether or not Portal Particle Effects are rendered").define("render_portal_particle_effects", true);
			label_maximum_distance = builder.comment("How far away Portal Labels will still render.").defineInRange("label_maximum_distance", 32, 8, 64);
		}
		builder.pop();
	}
	
	public static ModConfigManager getInstance() {
		return CONFIG;
	}

	public int getPortalMaximumSize() {
		return this.portal_maximum_size.get();
	}
	
	public void setPortalMaximumSize(int value) {
		this.portal_maximum_size.set(value);
	}
	
	public boolean getPlayPortalTravelSounds() {
		return playPortalTravelSounds.get();
	}
	
	public void setPlayPortalTravelSounds(boolean value) {
		this.playPortalTravelSounds.set(value);
	}

	public boolean getPlayPortalAmbientSounds() {
		return playPortalAmbientSounds.get();
	}
	
	public void setPlayPortalAmbientSounds(boolean value) {
		this.playPortalAmbientSounds.set(value);
	}
	
	public boolean getAllowWardenTeleport() {
		return this.allowWardenTeleport.get();
	}

	public void setAllowWardenTeleport(boolean value) {
		this.allowWardenTeleport.set(value);
	}
	
	/** -Messages- */
	public boolean getInfoMessage() {
		return info_message.get();
	}
	
	public void setInfoMessage(boolean value) {
		this.info_message.set(value);
	}
	
	public boolean getDebugMessage() {
		return debug_message.get();
	}
	
	public void setDebugMessage(boolean value) {
		this.debug_message.set(value);
	}
	
	/** -Visual- */
	public boolean getFrameConnectedTextures() {
		return this.frameConnectedTextures.get();
	}
	
	public void setFrameConnectedTextures(boolean value) {
		this.frameConnectedTextures.set(value);
	}
	
	public boolean getPortalConnectedTextures() {
		return this.portalConnectedTextures.get();
	}
	
	public void setPortalConnectedTextures(boolean value) {
		this.portalConnectedTextures.set(value);
	}
	
	public boolean getRenderPortalLabels() {
		return renderPortalLabels.get();
	}
	
	public void setRenderPortalLabels(boolean value) {
		this.renderPortalLabels.set(value);
	}
	
	public boolean getRenderPortalParticleEffects() {
		return renderPortalParticleEffects.get();
	}
	
	public void setRenderPortalParticleEffects(boolean value) {
		this.renderPortalParticleEffects.set(value);
	}
	
	public int getLabelMaximumDistance() {
		return label_maximum_distance.get();
	}
	
	public void setLabelMaximumDistance(int value) {
		this.label_maximum_distance.set(value);
	}
}