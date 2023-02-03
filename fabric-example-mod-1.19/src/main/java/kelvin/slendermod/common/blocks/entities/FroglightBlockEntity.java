package kelvin.slendermod.common.blocks.entities;

import kelvin.slendermod.common.blocks.BlockRegistry;
import kelvin.slendermod.common.sounds.SoundRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FroglightBlockEntity extends BlockEntity {

    private long tickCount;
    private long startTick;
    private PositionedSoundInstance currentSound;
    private final MinecraftClient minecraft;

    public FroglightBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.FROGLIGHT_ENTITY, pos, state);
        minecraft = MinecraftClient.getInstance();
    }

    public static void tick(World world, BlockPos pos, BlockState state, FroglightBlockEntity blockEntity) {
        if (world.isClient() && blockEntity.minecraft != null) {
            if (blockEntity.tickCount <= 0 || blockEntity.tickCount >= (blockEntity.startTick + 40)) {  // 40 is the length of the sound in ticks
                blockEntity.startTick = blockEntity.tickCount;
                if (blockEntity.currentSound == null) {
                    blockEntity.currentSound = new PositionedSoundInstance(SoundRegistry.BUZZING, SoundCategory.BLOCKS, 0.3F, 1, Random.create(world.random.nextLong()), pos.getX(), pos.getY(), pos.getZ());
                }
                playSound(blockEntity.minecraft, blockEntity.currentSound);
            }
            ++blockEntity.tickCount;
        }
    }

    public void onRemoved() {
        stopSound();
    }

    private static void playSound(MinecraftClient minecraft, SoundInstance instance) {
        if (!playerInRange(minecraft, instance)) {
            return;
        }

        minecraft.getSoundManager().play(instance);
    }

    private static boolean playerInRange(MinecraftClient minecraft, SoundInstance instance) {
        PlayerEntity player = minecraft.player;
        if (player == null) {
            return false;
        }

        Sound sound = instance.getSound();
        if (sound == null) {
            instance.getSoundSet(minecraft.getSoundManager());
            sound = instance.getSound();
        }

        float scaledDistance = Math.max(instance.getVolume(), 1) * sound.getAttenuation();
        return player.getPos().squaredDistanceTo(instance.getX(), instance.getY(), instance.getZ()) < scaledDistance * scaledDistance;
    }

    private void stopSound() {
        if (currentSound != null) {
            minecraft.getSoundManager().stop(currentSound);
        }
    }
}
