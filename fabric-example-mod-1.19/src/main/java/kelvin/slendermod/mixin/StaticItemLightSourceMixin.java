package kelvin.slendermod.mixin;

import dev.lambdaurora.lambdynlights.api.item.ItemLightSource;
import kelvin.slendermod.common.items.ItemFlashlight;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemLightSource.StaticItemLightSource.class)
public class StaticItemLightSourceMixin {

    @Inject(method = "getLuminance", at = @At("RETURN"), cancellable = true)
    private void getLuminanceFromItemStack(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem() instanceof ItemFlashlight flashlight) {
            if (flashlight.isOff) {
                cir.setReturnValue(0);
            }
        }
    }
}
