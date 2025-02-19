package com.tcn.cosmosportals.core.portal;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.enums.EnumAllowedEntities;
import com.tcn.cosmosportals.core.block.BlockPortal;
import com.tcn.cosmosportals.core.block.PortalFrameBlock;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortal;
import com.tcn.cosmosportals.core.management.ModConfigManager;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CustomPortalShape {
	private static final BlockBehaviour.StatePredicate FRAME = (stateIn, worldIn, posIn) -> {
		return stateIn.getBlock() instanceof  PortalFrameBlock;
	};

	private final LevelAccessor level;
	private final Direction.Axis axis;
	private final Direction rightDir;
	private int numPortalBlocks;
	@Nullable
	private BlockPos bottomLeft;
	private int height;
	private int width;

	public static Optional<CustomPortalShape> findEmptyPortalShape(LevelAccessor worldIn, BlockPos posIn, Direction.Axis axisIn) {
		return findPortalShape(worldIn, posIn, (portalSize) -> {
			return portalSize.isValid() && portalSize.numPortalBlocks == 0;
		}, axisIn);
	}

	public static Optional<CustomPortalShape> findPortalShape(LevelAccessor worldIn, BlockPos posIn, Predicate<CustomPortalShape> predicate, Direction.Axis axisIn) {
		Optional<CustomPortalShape> optional = Optional.of(new CustomPortalShape(worldIn, posIn, axisIn)).filter(predicate);
		if (optional.isPresent()) {
			return optional;
		} else {
			Direction.Axis direction$axis = (axisIn == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
			return Optional.of(new CustomPortalShape(worldIn, posIn, direction$axis)).filter(predicate);
		}
	}

	public CustomPortalShape(LevelAccessor worldIn, BlockPos posIn, Direction.Axis axisIn) {
		this.level = worldIn;
		this.axis = axisIn;
		this.rightDir = (axisIn == Direction.Axis.X ? Direction.WEST : Direction.SOUTH);
		this.bottomLeft = this.calculateBottomLeft(posIn);
		
		if (this.bottomLeft == null) {
			this.bottomLeft = posIn;
			this.width = 1;
			this.height = 1;
		} else {
			this.width = this.calculateWidth();
			if (this.width > 0) {
				this.height = this.calculateHeight();
			}
		}
	}

	@Nullable
	private BlockPos calculateBottomLeft(BlockPos posIn) {
		for (int i = (int) Math.max(0, posIn.getY() - (ModConfigManager.getInstance().getPortalMaximumSize())); posIn.getY() > i && isEmpty(this.level.getBlockState(posIn.below())); posIn = posIn.below()) { }

		Direction direction = this.rightDir.getOpposite();
		int j = this.getDistanceUntilEdgeAboveFrame(posIn, direction) - 1;
		return j < 0 ? null : posIn.relative(direction, j);
	}

	private int calculateWidth() {
		int i = this.getDistanceUntilEdgeAboveFrame(this.bottomLeft, this.rightDir);
		return i >= 1 && i <= (ModConfigManager.getInstance().getPortalMaximumSize()) ? i : 0;
	}

	private int getDistanceUntilEdgeAboveFrame(BlockPos posIn, Direction directionIn) {
		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

		for (int i = 0; i <= (ModConfigManager.getInstance().getPortalMaximumSize()); i++) {
			blockpos$mutable.set(posIn).move(directionIn, i);
			BlockState blockstate = this.level.getBlockState(blockpos$mutable);
			if (!isEmpty(blockstate)) {
				if (FRAME.test(blockstate, this.level, blockpos$mutable)) {
					return i;
				}
				break;
			}

			BlockState blockstate1 = this.level.getBlockState(blockpos$mutable.move(Direction.DOWN));
			if (!FRAME.test(blockstate1, this.level, blockpos$mutable)) {
				break;
			}
		}

		return 0;
	}

	private int calculateHeight() {
		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
		int i = this.getDistanceUntilTop(blockpos$mutable);
		return i >= 2 && i <= (ModConfigManager.getInstance().getPortalMaximumSize()) && this.hasTopFrame(blockpos$mutable, i) ? i : 0;
	}

	private boolean hasTopFrame(BlockPos.MutableBlockPos mutableIn, int distance) {
		for (int i = 0; i < this.width; ++i) {
			BlockPos.MutableBlockPos blockpos$mutable = mutableIn.set(this.bottomLeft).move(Direction.UP, distance).move(this.rightDir, i);
			if (!FRAME.test(this.level.getBlockState(blockpos$mutable), this.level, blockpos$mutable)) {
				return false;
			}
		}

		return true;
	}

	private int getDistanceUntilTop(BlockPos.MutableBlockPos mutableIn) {
		for (int i = 0; i < (ModConfigManager.getInstance().getPortalMaximumSize() + 2); ++i) {
			mutableIn.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, -1);
			if (!FRAME.test(this.level.getBlockState(mutableIn), this.level, mutableIn)) {
				return i;
			}

			mutableIn.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, this.width);
			if (!FRAME.test(this.level.getBlockState(mutableIn), this.level, mutableIn)) {
				return i;
			}

			for (int j = 0; j < this.width; ++j) {
				mutableIn.set(this.bottomLeft).move(Direction.UP, i).move(this.rightDir, j);
				BlockState blockstate = this.level.getBlockState(mutableIn);
				if (!isEmpty(blockstate)) {
					return i;
				}

				if (blockstate.is(ModRegistrationManager.BLOCK_PORTAL.get())) {
					++this.numPortalBlocks;
				}
			}
		}

		return (int) (ModConfigManager.getInstance().getPortalMaximumSize() + 2);
	}

	private static boolean isEmpty(BlockState stateIn) {
		return stateIn.isAir() || stateIn.is(ModRegistrationManager.BLOCK_PORTAL.get());
	}

	public boolean isValid() {
		return this.bottomLeft != null && this.width >= 1 && this.width <= (ModConfigManager.getInstance().getPortalMaximumSize()) && this.height >= 2 && this.height <= (ModConfigManager.getInstance().getPortalMaximumSize());
	}

	public void createPortalBlocks(Level worldIn, ResourceLocation dimensionIn, BlockPos teleportPos, float pitchIn, float yawIn, int colourIn, boolean playSound, EnumAllowedEntities allowEntities, boolean showParticles) {
		BlockState blockstate = ModRegistrationManager.BLOCK_PORTAL.get().defaultBlockState().setValue(BlockPortal.AXIS, this.axis);
		
		BlockPos.betweenClosed(this.bottomLeft,	this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach((pos) -> {
			if (!(this.level.getBlockState(pos).getBlock() instanceof BlockPortal)) {
				this.level.setBlock(pos, blockstate, 18);
				
				if (this.level.getBlockEntity(pos) != null) {
					BlockEntity entity = this.level.getBlockEntity(pos);
					
					if (entity instanceof BlockEntityPortal blockEntity) {
						blockEntity.setDestDimension(dimensionIn);
						blockEntity.setDestInfo(teleportPos, yawIn, pitchIn);
						blockEntity.setDisplayColour(colourIn);
						
						blockEntity.setPlaySound(playSound);
						blockEntity.setAllowedEntities(allowEntities);
						blockEntity.setShowParticles(showParticles);
						
						blockEntity.sendUpdates(true);
					}
				}
			}
		});
	}
	
	public LinkedHashMap<Integer, BlockPos> getPortalBlocks(Level worldIn, ResourceLocation dimensionIn) {
		LinkedHashMap<Integer, BlockPos> blockMap = new LinkedHashMap<Integer, BlockPos>();
		
		BlockPos.betweenClosed(this.bottomLeft,	this.bottomLeft.relative(Direction.UP, this.height - 1).relative(this.rightDir, this.width - 1)).forEach((pos) -> {
			if ((worldIn.getBlockState(pos).getBlock() instanceof BlockPortal)) {
				if (worldIn.getBlockEntity(pos) != null) {
					BlockEntity entity = worldIn.getBlockEntity(pos);
					
					if (entity instanceof BlockEntityPortal blockEntity) {
						if (blockEntity.destDimension.equals(dimensionIn)) {
							blockMap.put(blockMap.size(), new BlockPos(pos));
						}
					}
				}
			}
		});

		return blockMap;
	}

	public boolean isComplete(boolean ignoreUpdate) {
		return this.isValid() && (!ignoreUpdate && this.numPortalBlocks == (this.width * this.height));
	}

	public static Vec3 getRelativePosition(BlockUtil.FoundRectangle resultIn, Direction.Axis axisIn, Vec3 vecIn, EntityDimensions entitySizeIn) {
		double d0 = (double) resultIn.axis1Size - (double) entitySizeIn.width();
		double d1 = (double) resultIn.axis2Size - (double) entitySizeIn.height();
		BlockPos blockpos = resultIn.minCorner;
		
		double d2;
		if (d0 > 0.0D) {
			float f = (float) blockpos.get(axisIn) + entitySizeIn.width() / 2.0F;
			d2 = Mth.clamp(Mth.inverseLerp(vecIn.get(axisIn) - (double) f, 0.0D, d0), 0.0D, 1.0D);
		} else {
			d2 = 0.5D;
		}

		double d4;
		if (d1 > 0.0D) {
			Direction.Axis direction$axis = Direction.Axis.Y;
			d4 = Mth.clamp(Mth.inverseLerp(vecIn.get(direction$axis) - (double) blockpos.get(direction$axis), 0.0D, d1), 0.0D, 1.0D);
		} else {
			d4 = 0.0D;
		}

		Direction.Axis direction$axis1 = axisIn == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
		double d3 = vecIn.get(direction$axis1) - ((double) blockpos.get(direction$axis1) + 0.5D);
		return new Vec3(d2, d4, d3);
	}
}