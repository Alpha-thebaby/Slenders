package kelvin.slendermod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ExitSignBlock extends CustomFacingBlock {

    public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1, 6, 7, 15, 13, 9),
            Block.createCuboidShape(9.25, 13, 7.3, 10.75, 16.5, 8.7),
            Block.createCuboidShape(5.25, 13, 7.3, 6.75, 16.5, 8.7));
    public static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 6, 1, 9, 13, 15),
            Block.createCuboidShape(7.3, 13, 5.25, 8.7, 16.5, 6.75),
            Block.createCuboidShape(7.3, 13, 9.25, 8.7, 16.5, 10.75));
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1, 6, 7, 15, 13, 9),
            Block.createCuboidShape(5.25, 13, 7.3, 6.75, 16.5, 8.7),
            Block.createCuboidShape(9.25, 13, 7.3, 10.75, 16.5, 8.7));
    public static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 6, 1, 9, 13, 15),
            Block.createCuboidShape(7.3, 13, 9.25, 8.7, 16.5, 10.75),
            Block.createCuboidShape(7.3, 13, 5.25, 8.7, 16.5, 6.75));

    public ExitSignBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        return switch (direction) {
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }
}
