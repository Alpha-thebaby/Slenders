package kelvin.slendermod.mixin;

import kelvin.slendermod.common.blocks.BlockRegistry;
import kelvin.slendermod.common.blocks.entities.FroglightBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PillarBlock.class)
public abstract class PillarBlockMixin extends Block implements BlockEntityProvider {

    public PillarBlockMixin(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (isFroglight(state)) {
            return new FroglightBlockEntity(pos, state);
        }
        else {
            return null;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (isFroglight(state)) {
            return checkType(type, BlockRegistry.FROGLIGHT_ENTITY, FroglightBlockEntity::tick);
        }
        return null;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state != newState) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FroglightBlockEntity froglightBlockEntity) {
                froglightBlockEntity.onRemoved();
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    private <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    private static boolean isFroglight(BlockState state) {
        return state.getBlock() == Blocks.OCHRE_FROGLIGHT || state.getBlock() == Blocks.PEARLESCENT_FROGLIGHT || state.getBlock() == Blocks.VERDANT_FROGLIGHT;
    }
}
