package kelvin.slendermod.common.items;

import kelvin.slendermod.common.blocks.BlockRegistry;
import kelvin.slendermod.common.entities.EntityRegistry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
    public static Item FLASHLIGHT = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "flashlight"),
            new Item(new Item.Settings())
    );

    public static Item BOSS_ATTACK = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "boss_hit"),
            new ItemBossAttack()
    );

    public static Item BOSS_DASH = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "boss_dash"),
            new ItemBossDash()
    );

    public static ItemGrimoire GRIMOIRE = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "grimoire"),
            new ItemGrimoire()
    );

    public static BlockItem SLENDER_HEAD = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "slender_head"),
            new BlockItem(BlockRegistry.SLENDER_HEAD, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem BED = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "bed"),
            new BlockItem(BlockRegistry.BED, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem HOSPITAL_BED = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "hospital_bed"),
            new BlockItem(BlockRegistry.HOSPITAL_BED, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem SHELF_CONS = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "shelf_cons"),
            new BlockItem(BlockRegistry.SHELF_CONS, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem EXIT_SIGN = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "exit_sign"),
            new BlockItem(BlockRegistry.EXIT_SIGN, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem DEAD_LEAVES = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "dead_leaves"),
            new BlockItem(BlockRegistry.DEAD_LEAVES, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem DEAD_TREE = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "dead_tree"),
            new BlockItem(BlockRegistry.DEAD_TREE, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem SCRATCHED_DEAD_TREE = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "scratched_dead_tree"),
            new BlockItem(BlockRegistry.SCRATCHED_DEAD_TREE, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem BARBED_WIRE_FENCE = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "barbed_wire_fence"),
            new BlockItem(BlockRegistry.BARBED_WIRE_FENCE, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem BONES = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "bones"),
            new BlockItem(BlockRegistry.BONES, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static Item SLENDERMAN_SPAWN_EGG = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "slenderman_spawn_egg"),
            new SpawnEggItem(EntityRegistry.SLENDERMAN, 9078144, 5722960, new Item.Settings().group(ItemGroup.MISC))
    );

    public static Item SMALL_SLENDER_SPAWN_EGG = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "small_slender_spawn_egg"),
            new SpawnEggItem(EntityRegistry.SMALL_SLENDER, 5722960, 9078144, new Item.Settings().group(ItemGroup.MISC))
    );

    public static Item SLENDER_BOSS_SPAWN_EGG = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "slender_boss_spawn_egg"),
            new SpawnEggItem(EntityRegistry.SLENDER_BOSS, 11578536, 12688761, new Item.Settings().group(ItemGroup.MISC))
    );

    public static BlockItem TRASH = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "trash"),
            new BlockItem(BlockRegistry.TRASH, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem DEBRIS = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "debris"),
            new BlockItem(BlockRegistry.DEBRIS, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem CAR_BODY = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "car_body"),
            new BlockItem(BlockRegistry.CAR_BODY, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem DEAD_GRASS_BLOCK = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "dead_grass_block"),
            new BlockItem(BlockRegistry.DEAD_GRASS_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static Item ACCESS_CARD = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "access_card"),
            new Item(new Item.Settings().group(ItemGroup.MISC))
    );

    public static BlockItem MANHOLE_COVER = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "manhole_cover"),
            new BlockItem(BlockRegistry.MANHOLE_COVER, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static BlockItem ACCESS_READER = Registry.register(
            Registry.ITEM,
            new Identifier("slendermod", "access_reader"),
            new BlockItem(BlockRegistry.ACCESS_READER, new Item.Settings().group(ItemGroup.DECORATIONS))
    );

    public static void Register() {

    }

}
