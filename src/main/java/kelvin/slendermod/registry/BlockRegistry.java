package kelvin.slendermod.registry;

import kelvin.slendermod.block.*;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;

import static kelvin.slendermod.SlenderMod.id;

public class BlockRegistry {

    public static SlendermanHeadBlock SLENDER_HEAD = register("slender_head",
            new SlendermanHeadBlock(AbstractBlock.Settings.copy(Blocks.SKELETON_SKULL))
    );

    public static LeavesBlock DEAD_LEAVES = register("dead_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES))
    );

    public static Block EXIT_SIGN = register("exit_sign",
            new ExitSignBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_SIGN).mapColor(MapColor.WHITE))
    );

    public static Block SHELF_CONS = register("shelf_cons",
            new ShelfConsBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).noCollision())
    );

    public static Block BED = register("bed",
            new SubBedBlock(DyeColor.WHITE, AbstractBlock.Settings.copy(Blocks.RED_BED))
    );

    public static Block HOSPITAL_BED = register("hospital_bed",
            new HospitalBedBlock(DyeColor.WHITE, AbstractBlock.Settings.copy(Blocks.RED_BED))
    );

    public static Block DEAD_TREE = register("dead_tree",
            new RotatableBlockEntityBlock<>(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque().mapColor(MapColor.SPRUCE_BROWN), "dead_tree")
    );

    public static Block SCRATCHED_DEAD_TREE = register("scratched_dead_tree",
            new RotatableBlockEntityBlock<>(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque().mapColor(MapColor.SPRUCE_BROWN), "scratched_dead_tree")
    );

    public static Block CAR_BODY = register("car_body",
            new RotatableBlockEntityBlock<>(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque(), "car_body")
    );

    public static Block TRASH = register("trash",
            new RotatableBlockEntityBlock<>(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque(), "trash")
    );

    public static Block DEBRIS = register("debris",
            new RotatableBlockEntityBlock<>(AbstractBlock.Settings.copy(Blocks.STONE_BUTTON).nonOpaque(), "debris")
    );

    public static Block UFO_INTERIOR = register("ufo_interior",
            new RotatableBlockEntityBlock<>(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque().noCollision(), "ufo_interior")
    );

    public static Block BARBED_WIRE_FENCE = register("barbed_wire_fence",
            new CustomFenceBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_FENCE))
    );

    public static Block BONES = register("bones",
            new BonesBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.PALE_YELLOW).strength(2.0F).sounds(BlockSoundGroup.BONE).noCollision())
    );

    public static Block DEAD_GRASS_BLOCK = register("dead_grass_block",
            new Block(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK))
    );

    public static Block MANHOLE_COVER = register("manhole_cover",
            new ManholeCoverBlock(AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR))
    );

    public static Block ACCESS_READER = register("access_reader",
            new AccessReaderBlock(AbstractBlock.Settings.copy(Blocks.STONE))
    );

    public static void register() {
    }

    private static <T extends Block> T register(String name, T block) {
        T instance = Registry.register(Registries.BLOCK, id(name), block);
        Registry.register(Registries.ITEM, id(name), new BlockItem(instance, new Item.Settings()));
        return instance;
    }
}
