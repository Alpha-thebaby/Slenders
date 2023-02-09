package kelvin.slendermod.common.blocks;

import kelvin.slendermod.common.blocks.entities.UFOInteriorBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class UFOInteriorBlock extends CustomFacingBlock implements BlockEntityProvider {
    public UFOInteriorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new UFOInteriorBlockEntity(pos, state);
    }
}
