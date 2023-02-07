package kelvin.slendermod.mixin;

import dev.lambdaurora.lambdynlights.api.item.ItemLightSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(ItemLightSource.StaticItemLightSource.class)
public interface StaticItemLightSourceAccessor {

    @Mutable
    @Accessor("luminance")
    void setLuminance(int luminance);
}
