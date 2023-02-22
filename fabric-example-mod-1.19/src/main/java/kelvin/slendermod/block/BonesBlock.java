package kelvin.slendermod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BonesBlock extends CustomFacingBlock {

    public static VoxelShape NORTH_SHAPE = Block.createCuboidShape(1.0, 0.0, 0.0, 14.0, 6.0, 16.0);
    public static VoxelShape SOUTH_SHAPE = Block.createCuboidShape(2.0, 0.0, 0.0, 15.0, 6.0, 16.0);
    public static VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 2.0, 16.0, 6.0, 15.0);
    public static VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 1.0, 16.0, 6.0, 14.0);

    public BonesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        return switch (direction) {
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }
}
