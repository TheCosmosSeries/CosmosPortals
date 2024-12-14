package com.tcn.cosmosportals.core.block;

import javax.annotation.Nonnull;

import com.tcn.cosmoslibrary.common.block.CosmosBlockConnected;
import com.tcn.cosmosportals.core.management.ModConfigManager;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPortalFrame extends CosmosBlockConnected implements PortalFrameBlock {

	public BlockPortalFrame(Properties properties) {
		super(properties);
	}

	@Override
	protected boolean canConnect(@Nonnull BlockState orig, @Nonnull BlockState conn) {
		if (ModConfigManager.getInstance().getFrameConnectedTextures()) {
			if (conn.getBlock().equals(Blocks.AIR)) {
				return false;
			} else if (orig.getBlock().equals(conn.getBlock())) {
				return true;
			} else if (conn.getBlock() instanceof BlockPortalDock) {
				return true;
			} else if (conn.getBlock() instanceof BlockPortalDockUpgraded4) {
				return true;
			} else if (conn.getBlock() instanceof BlockDockController) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

}
