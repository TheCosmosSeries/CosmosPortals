package com.tcn.cosmosportals.core.block;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.block.CosmosEntityBlock;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockController4;
import com.tcn.cosmosportals.core.management.ModRegistrationManager;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockPortalDockController4 extends CosmosEntityBlock implements PortalFrameBlock, PortalFrameBlockNoUpdate {

	public static final BooleanProperty NORTH = BooleanProperty.create("north");
	public static final BooleanProperty SOUTH = BooleanProperty.create("south");
	public static final BooleanProperty WEST = BooleanProperty.create("west");
	public static final BooleanProperty EAST = BooleanProperty.create("east");
	
	private VoxelShape BASE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private VoxelShape BASE_ONE = Block.box(-1.0D, -1.0D, -1.0D, 17.0D, 17.0D, 17.0D);
	
	public BlockPortalDockController4(BlockBehaviour.Properties properties) {
		super(properties);
		
		this.hasDynamicShape();
		this.registerDefaultState(this.defaultBlockState()
			.setValue(EAST, Boolean.FALSE)
			.setValue(NORTH, Boolean.FALSE)
			.setValue(SOUTH, Boolean.FALSE)
			.setValue(WEST, Boolean.FALSE));
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos posIn, BlockState stateIn) {
		return new BlockEntityPortalDockController4(posIn, stateIn);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level levelIn, BlockState stateIn, BlockEntityType<T> entityTypeIn) {
		return createTicker(levelIn, entityTypeIn, ModRegistrationManager.BLOCK_ENTITY_TYPE_PORTAL_DOCK_CONTROLLER4.get());
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level levelIn, BlockEntityType<T> entityTypeIn, BlockEntityType<? extends BlockEntityPortalDockController4> entityIn) {
		return createTickerHelper(entityTypeIn, entityIn, BlockEntityPortalDockController4::tick);
	}

	@Override
	public RenderShape getRenderShape(BlockState stateIn) {
		return RenderShape.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState stateIn, BlockGetter blockGetterIn, BlockPos posIn, CollisionContext contextIn) {
		ArrayList<VoxelShape> shapes = new ArrayList<VoxelShape>();
		
		if (blockGetterIn.getBlockState(posIn.north()).isAir()) {
			shapes.add(Block.box(1.0D, 9.0D, -1.0D, 7.0D , 15.0D, 0.0D)); //TOP LEFT
			shapes.add(Block.box(9.0D, 9.0D, -1.0D, 15.0D, 15.0D, 0.0D)); //TOP RIGHT
			shapes.add(Block.box(1.0D, 1.0D, -1.0D, 7.0D , 7.0D , 0.0D)); //BOTTOM LEFT
			shapes.add(Block.box(9.0D, 1.0D, -1.0D, 15.0D, 7.0D , 0.0D)); //BOTTOM RIGHT
		}
		
		if (blockGetterIn.getBlockState(posIn.east()).isAir()) {
			shapes.add(Block.box(16.0D, 9.0D, 1.0D, 17.0D, 15.0D, 7.0D )); //TOP LEFT
			shapes.add(Block.box(16.0D, 9.0D, 9.0D, 17.0D, 15.0D, 15.0D)); //TOP RIGHT
			shapes.add(Block.box(16.0D, 1.0D, 1.0D, 17.0D, 7.0D , 7.0D )); //BOTTOM LEFT
			shapes.add(Block.box(16.0D, 1.0D, 9.0D, 17.0D, 7.0D,  15.0D)); //BOTTOM RIGHT
		}
		
		if (blockGetterIn.getBlockState(posIn.south(1)).isAir()) {
			shapes.add(Block.box(1.0D, 9.0D, 16.0D, 7.0D , 15.0D, 17.0D)); //TOP LEFT
			shapes.add(Block.box(9.0D, 9.0D, 16.0D, 15.0D, 15.0D, 17.0D)); //TOP RIGHT
			shapes.add(Block.box(1.0D, 1.0D, 16.0D, 7.0D , 7.0D , 17.0D)); //BOTTOM LEFT
			shapes.add(Block.box(9.0D, 1.0D, 16.0D, 15.0D, 7.0D,  17.0D)); //BOTTOM RIGHT
		}
		
		if (blockGetterIn.getBlockState(posIn.west(1)).isAir()) {
			shapes.add(Block.box(-1.0D, 9.0D, 1.0D, 0.0D, 15.0D, 7.0D )); //TOP LEFT
			shapes.add(Block.box(-1.0D, 9.0D, 9.0D, 0.0D, 15.0D, 15.0D)); //TOP RIGHT
			shapes.add(Block.box(-1.0D, 1.0D, 1.0D, 0.0D, 7.0D , 7.0D )); //BOTTOM LEFT
			shapes.add(Block.box(-1.0D, 1.0D, 9.0D, 0.0D, 7.0D,  15.0D)); //BOTTOM RIGHT
		}
		
		return or(BASE, shapes);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState stateIn, BlockGetter blockGetterIn, BlockPos posIn, CollisionContext contextIn) {
		return BASE;
	}

	@Override
	public VoxelShape getInteractionShape(BlockState stateIn, BlockGetter blockGetterIn, BlockPos posIn) {
		return BASE_ONE;
	}

	@Override
	public VoxelShape getVisualShape(BlockState stateIn, BlockGetter blockGetterIn, BlockPos posIn, CollisionContext contextIn) {
		return BASE_ONE;
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level levelIn, BlockPos posIn, Player playerIn, InteractionHand handIn, BlockHitResult hit) {
		BlockEntity entity = levelIn.getBlockEntity(posIn);
		
		if (entity instanceof BlockEntityPortalDockController4 blockEntity) {
			return blockEntity.useItemOn(stack, state, levelIn, posIn, playerIn, handIn, hit);
		}
		
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level levelIn, BlockPos posIn, Player playerIn, BlockHitResult hit) {
		BlockEntity entity = levelIn.getBlockEntity(posIn);
		
		if (entity instanceof BlockEntityPortalDockController4 blockEntity) {
			return blockEntity.useWithoutItem(state, levelIn, posIn, playerIn, hit);
		}
		
		return InteractionResult.PASS;
	}

	public static VoxelShape or(VoxelShape baseShape, ArrayList<VoxelShape> shapeArray) {
		return shapeArray.stream().reduce(baseShape, Shapes::or);
	}
	
	@Override
    protected void neighborChanged(BlockState stateIn, Level levelIn, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		levelIn.setBlock(pos, stateIn
			.setValue(EAST,  this.shouldSideRenderFrame(levelIn, pos, Direction.EAST))
			.setValue(NORTH, this.shouldSideRenderFrame(levelIn, pos, Direction.NORTH))
			.setValue(SOUTH, this.shouldSideRenderFrame(levelIn, pos, Direction.SOUTH))
			.setValue(WEST,  this.shouldSideRenderFrame(levelIn, pos, Direction.WEST)), 18);
    }

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(EAST, NORTH, SOUTH, WEST);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext contextIn) {
		Level level = contextIn.getLevel();
		BlockPos clickedPos = contextIn.getClickedPos();
		
		return this.defaultBlockState().updateShape(Direction.NORTH, level.getBlockState(clickedPos), level, clickedPos, clickedPos.offset(Direction.NORTH.getNormal()));
	}
	
	private boolean shouldSideRenderFrame(LevelAccessor world, BlockPos pos, Direction facing) {
		final BlockState orig = world.getBlockState(pos);
		final BlockState conn = world.getBlockState(pos.offset(facing.getNormal()));
		
		return orig != null && conn != null && this.testBlock(orig, conn);
	}

	protected boolean testBlock(@Nonnull BlockState orig, @Nonnull BlockState conn) {
		return !conn.isAir();
	}
}