package kelvin.slendermod.common.blocks;

import kelvin.slendermod.common.blocks.entities.DeadTreeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class DeadTreeBlock extends CustomFacingBlock implements BlockEntityProvider {

    private final boolean isScratched;

    public DeadTreeBlock(Settings settings, boolean isScratched) {
        super(settings);
        this.isScratched = isScratched;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DeadTreeBlockEntity(pos, state, isScratched);
    }
}
