package kelvin.slendermod.registry;

import kelvin.slendermod.item.ItemBossAttack;
import kelvin.slendermod.item.ItemBossDash;
import kelvin.slendermod.item.ItemFlashlight;
import kelvin.slendermod.item.ItemGrimoire;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static kelvin.slendermod.SlenderMod.id;

public class ItemRegistry {

    public static Item FLASHLIGHT = register("flashlight",
            new ItemFlashlight(new Item.Settings().maxCount(1))
    );

    public static Item BOSS_ATTACK = register("boss_hit",
            new ItemBossAttack()
    );

    public static Item BOSS_DASH = register("boss_dash",
            new ItemBossDash()
    );

    public static ItemGrimoire GRIMOIRE = register("grimoire",
            new ItemGrimoire()
    );

    public static Item ACCESS_CARD = register("access_card",
            new Item(new Item.Settings())
    );

    public static Item SLENDERMAN_SPAWN_EGG = register("slenderman_spawn_egg",
            new SpawnEggItem(EntityRegistry.SLENDERMAN, 9078144, 5722960, new Item.Settings())
    );

    public static Item SMALL_SLENDER_SPAWN_EGG = register("small_slender_spawn_egg",
            new SpawnEggItem(EntityRegistry.SMALL_SLENDER, 5722960, 9078144, new Item.Settings())
    );

    public static Item SLENDER_BOSS_SPAWN_EGG = register("slender_boss_spawn_egg",
            new SpawnEggItem(EntityRegistry.SLENDER_BOSS, 11578536, 12688761, new Item.Settings())
    );

    public static void register() {
    }

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, id(name), item);
    }
}
