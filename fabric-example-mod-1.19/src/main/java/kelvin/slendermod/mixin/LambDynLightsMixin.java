package kelvin.slendermod.mixin;

import dev.lambdaurora.lambdynlights.LambDynLights;
import kelvin.slendermod.common.items.ItemFlashlight;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LambDynLights.class)
public class LambDynLightsMixin {

    @Inject(method = "getLuminanceFromItemStack", at = @At("RETURN"), cancellable = true)
    private static void getLuminanceFromItemStack(ItemStack stack, boolean submergedInWater, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem() instanceof ItemFlashlight) {
            boolean powered = false;
            NbtCompound nbt = stack.getOrCreateSubNbt("Flashlight");
            if (nbt.contains("powered")) {
                powered = nbt.getBoolean("powered");
            }
            cir.setReturnValue(powered ? 15 : 0);
        }
    }
}
