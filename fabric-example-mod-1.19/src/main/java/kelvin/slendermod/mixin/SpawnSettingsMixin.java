package kelvin.slendermod.mixin;

import kelvin.slendermod.common.entities.EntityRegistry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnSettings.Builder.class)
public class SpawnSettingsMixin {

    @Inject(method = "spawn", at = @At("HEAD"), cancellable = true)
    public void spawn(SpawnGroup spawnGroup, SpawnSettings.SpawnEntry spawnEntry, CallbackInfoReturnable<SpawnSettings.Builder> cir) {
        if (spawnEntry.type != EntityRegistry.SMALL_SLENDER && spawnEntry.type != EntityRegistry.SLENDERMAN) {
            cir.setReturnValue((SpawnSettings.Builder) (Object)this);
        }
    }
}
