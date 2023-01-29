package kelvin.slendermod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class BonesBlock extends CustomFacingBlock {
    public static VoxelShape shape = Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 8.0, 12.0);

    public BonesBlock(Settings settings) {
        super(settings);
    }


}
