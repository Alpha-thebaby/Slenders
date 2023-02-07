package kelvin.slendermod.common.items;

import kelvin.slendermod.ModDynamicLights;
import kelvin.slendermod.mixin.StaticItemLightSourceAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemFlashlight extends Item {

    private static boolean IS_ON;

    public ItemFlashlight(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            IS_ON = !IS_ON;
            ((StaticItemLightSourceAccessor) ModDynamicLights.FLASHLIGHT).setLuminance(IS_ON ? 15 : 0);
        }
        return super.use(world, user, hand);
    }
}
