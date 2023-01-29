package kelvin.slendermod.common.blocks;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.DyeColor;

public class CustomBedBlock extends BedBlock {
    public CustomBedBlock(DyeColor color, Settings settings) {
        super(color, settings);
    }

    public BlockRenderType getRenderType(BlockState state) {

        return BlockRenderType.MODEL;
    }

}
