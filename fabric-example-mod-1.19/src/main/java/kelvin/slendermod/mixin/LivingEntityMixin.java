package kelvin.slendermod.mixin;

import kelvin.slendermod.common.blocks.SlendermanHeadBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(at=@At("HEAD"), method="getPreferredEquipmentSlot", cancellable = true)
    private static void getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> info) {
        if (stack.getItem() != null && (stack.getItem().asItem() instanceof BlockItem && ((BlockItem)stack.getItem().asItem()).getBlock() instanceof SlendermanHeadBlock)) {
            info.setReturnValue(EquipmentSlot.HEAD);
        }
    }
}
