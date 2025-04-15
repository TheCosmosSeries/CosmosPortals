package com.tcn.cosmosportals.core.block;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.tcn.cosmoslibrary.common.block.CosmosEntityBlock;
import com.tcn.cosmosportals.core.blockentity.BlockEntityPortalDockControllerSurface4;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockPortalDockControllerSurface4 extends CosmosEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	private VoxelShape BOX = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	
	private VoxelShape NORTH = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
	private VoxelShape SOUTH = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
	private VoxelShape WEST = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
	private VoxelShape EAST = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	
	public BlockPortalDockControllerSurface4(BlockBehaviour.Properties properties) {
		super(properties);
		
		this.hasDynamicShape();
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos posIn, BlockState stateIn) {
		return new BlockEntityPortalDockControllerSurface4(posIn, stateIn);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level levelIn, BlockState stateIn, BlockEntityType<T> entityTypeIn) {
		return createTicker(levelIn, entityTypeIn, ModRegistrationManager.BLOCK_ENTITY_TYPE_PORTAL_DOCK_CONTROLLER_SURFACE4.get());
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level levelIn, BlockEntityType<T> entityTypeIn, BlockEntityType<? extends BlockEntityPortalDockControllerSurface4> entityIn) {
		return createTickerHelper(entityTypeIn, entityIn, BlockEntityPortalDockControllerSurface4::tick);
	}

	@Override
	public RenderShape getRenderShape(BlockState stateIn) {
		return RenderShape.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState stateIn, BlockGetter blockGetterIn, BlockPos posIn, CollisionContext contextIn) {
		ArrayList<VoxelShape> shapes = new ArrayList<VoxelShape>();
		
		if (stateIn.getValue(FACING).equals(Direction.SOUTH)) {
			shapes.add(Block.box(1.0D, 9.0D, 14.0D, 7.0D , 15.0D, 15.0D)); //TOP LEFT
			shapes.add(Block.box(9.0D, 9.0D, 14.0D, 15.0D, 15.0D, 15.0D)); //TOP RIGHT
			shapes.add(Block.box(1.0D, 1.0D, 14.0D, 7.0D , 7.0D , 15.0D)); //BOTTOM LEFT
			shapes.add(Block.box(9.0D, 1.0D, 14.0D, 15.0D, 7.0D , 15.0D)); //BOTTOM RIGHT
		}
		
		if (stateIn.getValue(FACING).equals(Direction.WEST)) {
			shapes.add(Block.box(1.0D, 9.0D, 1.0D, 2.0D, 15.0D, 7.0D )); //TOP LEFT
			shapes.add(Block.box(1.0D, 9.0D, 9.0D, 2.0D, 15.0D, 15.0D)); //TOP RIGHT
			shapes.add(Block.box(1.0D, 1.0D, 1.0D, 2.0D, 7.0D , 7.0D )); //BOTTOM LEFT
			shapes.add(Block.box(1.0D, 1.0D, 9.0D, 2.0D, 7.0D,  15.0D)); //BOTTOM RIGHT
		}
		
		if (stateIn.getValue(FACING).equals(Direction.NORTH)) {
			shapes.add(Block.box(1.0D, 9.0D, 1.0D, 7.0D , 15.0D, 2.0D)); //TOP LEFT
			shapes.add(Block.box(9.0D, 9.0D, 1.0D, 15.0D, 15.0D, 2.0D)); //TOP RIGHT
			shapes.add(Block.box(1.0D, 1.0D, 1.0D, 7.0D , 7.0D , 2.0D)); //BOTTOM LEFT
			shapes.add(Block.box(9.0D, 1.0D, 1.0D, 15.0D, 7.0D,  2.0D)); //BOTTOM RIGHT
		}
		
		if (stateIn.getValue(FACING).equals(Direction.EAST)) {
			shapes.add(Block.box(14.0D, 9.0D, 1.0D, 15.0D, 15.0D, 7.0D )); //TOP LEFT
			shapes.add(Block.box(14.0D, 9.0D, 9.0D, 15.0D, 15.0D, 15.0D)); //TOP RIGHT
			shapes.add(Block.box(14.0D, 1.0D, 1.0D, 15.0D, 7.0D , 7.0D )); //BOTTOM LEFT
			shapes.add(Block.box(14.0D, 1.0D, 9.0D, 15.0D, 7.0D,  15.0D)); //BOTTOM RIGHT
		}
		
		return or(this.getInteractionShape(stateIn, blockGetterIn, posIn), shapes);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState stateIn, BlockGetter blockGetterIn, BlockPos posIn, CollisionContext contextIn) {
		return this.getInteractionShape(stateIn, blockGetterIn, posIn);
	}

	@Override
	public VoxelShape getVisualShape(BlockState stateIn, BlockGetter blockGetterIn, BlockPos posIn, CollisionContext contextIn) {
		return this.getInteractionShape(stateIn, blockGetterIn, posIn);
	}

	@Override
	public VoxelShape getInteractionShape(BlockState stateIn, BlockGetter blockGetterIn, BlockPos posIn) {
		switch (stateIn.getValue(FACING)) {
			case NORTH:
				return SOUTH;
			case SOUTH:
				return NORTH;
			case WEST:
				return WEST;
			case EAST:
				return EAST;
			default:
				return BOX;
		}
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level levelIn, BlockPos posIn, Player playerIn, InteractionHand handIn, BlockHitResult hit) {
		BlockEntity entity = levelIn.getBlockEntity(posIn);
		
		if (entity instanceof BlockEntityPortalDockControllerSurface4 blockEntity) {
			return blockEntity.useItemOn(stack, state, levelIn, posIn, playerIn, handIn, hit);
		}
		
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level levelIn, BlockPos posIn, Player playerIn, BlockHitResult hit) {
		BlockEntity entity = levelIn.getBlockEntity(posIn);
		
		if (entity instanceof BlockEntityPortalDockControllerSurface4 blockEntity) {
			return blockEntity.useWithoutItem(state, levelIn, posIn, playerIn, hit);
		}
		
		return InteractionResult.PASS;
	}

	public static VoxelShape or(VoxelShape baseShape, ArrayList<VoxelShape> shapeArray) {
		return shapeArray.stream().reduce(baseShape, Shapes::or);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }
}