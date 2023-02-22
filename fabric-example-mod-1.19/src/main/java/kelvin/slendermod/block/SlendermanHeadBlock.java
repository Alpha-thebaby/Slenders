package kelvin.slendermod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SlendermanHeadBlock extends CustomFacingBlock {

    public static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.2, 12, 10.8, 11.5);
    public static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.5, 13, 10.8, 12.8);
    public static final VoxelShape EAST_SHAPE = Block.createCuboidShape(4.5, 0, 3, 12.8, 10.8, 12);
    public static final VoxelShape WEST_SHAPE = Block.createCuboidShape(3.2, 0.0, 4.0, 11.5, 10.8, 13.0);

    public SlendermanHeadBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
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
