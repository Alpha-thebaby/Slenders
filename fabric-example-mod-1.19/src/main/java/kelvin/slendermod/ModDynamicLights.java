package kelvin.slendermod;

import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSource;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSources;
import kelvin.slendermod.common.items.ItemRegistry;
import net.minecraft.util.Identifier;

public class ModDynamicLights implements DynamicLightsInitializer {

    public static final ItemLightSource FLASHLIGHT = new ItemLightSource.StaticItemLightSource(new Identifier("slendermod", "flash_light"), ItemRegistry.FLASHLIGHT, 15, false);

    @Override
    public void onInitializeDynamicLights() {
        ItemLightSources.registerItemLightSource(FLASHLIGHT);
    }
}
