package com.tcn.cosmosportals.client.colour;

import com.tcn.cosmoslibrary.common.lib.ComponentColour;
import com.tcn.cosmosportals.core.block.BlockPortalDockController4;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortal;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDock;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockUpgraded4;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockUpgraded8;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockColour implements BlockColor {

	@Override
	public int getColor(BlockState stateIn, BlockAndTintGetter displayReaderIn, BlockPos posIn, int tintIndexIn) {
		BlockEntity tile = displayReaderIn.getBlockEntity(posIn);
		
		if (stateIn.getBlock() instanceof BlockPortalDockController4) {
			if (tintIndexIn == 0) {
				return ComponentColour.GRAY.dec();
			} else if (tintIndexIn == 1) {
				 return ComponentColour.WHITE.dec();
			}
		}
		
		if (tile instanceof BlockEntityPortal portalTile) {
			return portalTile.getDisplayColour();
		}
		
		else if (tile instanceof BlockEntityPortalDock portalTile) {
			if (tintIndexIn == 1) {
				return portalTile.getDisplayColour();
			} else {
				return ComponentColour.GRAY.dec();
			}
		}

		else if (tile instanceof BlockEntityPortalDockUpgraded4 portalTile) {
			if (tintIndexIn == 1) {
				return portalTile.getDisplayColour();
			} else if (tintIndexIn == 2) {
				 return ComponentColour.WHITE.dec();
			} else {
				return ComponentColour.GRAY.dec();
			}
		}

		else if (tile instanceof BlockEntityPortalDockUpgraded8 portalTile) {
			if (tintIndexIn == 1) {
				return portalTile.getDisplayColour();
			} else if (tintIndexIn == 2) {
				 return ComponentColour.WHITE.dec();
			} else {
				return ComponentColour.GRAY.dec();
			}
		}
		
		return ComponentColour.GRAY.dec();
	}

}