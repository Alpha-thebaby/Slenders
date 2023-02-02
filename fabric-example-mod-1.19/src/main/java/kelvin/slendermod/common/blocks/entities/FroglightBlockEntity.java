package kelvin.slendermod.common.blocks.entities;

import kelvin.slendermod.common.blocks.BlockRegistry;
import kelvin.slendermod.common.sounds.SoundRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FroglightBlockEntity extends BlockEntity {

    private long tickCount;
    private long startTick;

    public FroglightBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.FROGLIGHT_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FroglightBlockEntity blockEntity) {
        if (blockEntity.tickCount <= 0 || blockEntity.tickCount >= (blockEntity.startTick + 40)) {
            blockEntity.startTick = blockEntity.tickCount;
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundRegistry.BUZZING, SoundCategory.BLOCKS, 0.1F, 1, true);
        }
        ++blockEntity.tickCount;
    }
}
