package kelvin.slendermod.common.blocks;

import kelvin.slendermod.common.blocks.entities.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {

    public static SlendermanHeadBlock SLENDER_HEAD = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "slender_head"),
            new SlendermanHeadBlock(AbstractBlock.Settings.copy(Blocks.SKELETON_SKULL))
    );

    public static LeavesBlock DEAD_LEAVES = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "dead_leaves"),
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES))
    );

    public static Block EXIT_SIGN = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "exit_sign"),
            new ExitSignBlock(AbstractBlock.Settings.copy(Blocks.ACACIA_SIGN).mapColor(MapColor.WHITE))
    );

    public static Block SHELF_CONS = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "shelf_cons"),
            new ShelfConsBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).noCollision())
    );

    public static Block BED = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "bed"),
            new SubBedBlock(DyeColor.WHITE, AbstractBlock.Settings.copy(Blocks.RED_BED))
    );

    public static Block HOSPITAL_BED = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "hospital_bed"),
            new HospitalBedBlock(DyeColor.WHITE, AbstractBlock.Settings.copy(Blocks.RED_BED))
    );

    public static Block DEAD_TREE = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "dead_tree"),
            new DeadTreeBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque().mapColor(MapColor.SPRUCE_BROWN), false)
    );

    public static Block SCRATCHED_DEAD_TREE = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "scratched_dead_tree"),
            new DeadTreeBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque().mapColor(MapColor.SPRUCE_BROWN), true)
    );

    public static Block CAR_BODY = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "car_body"),
            new CarBodyBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque())
    );

    public static Block TRASH = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "trash"),
            new TrashBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque())
    );

    public static Block DEBRIS = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "debris"),
            new DebrisBlock(AbstractBlock.Settings.copy(Blocks.STONE_BUTTON).nonOpaque())
    );

    public static BlockEntityType<?> DEAD_TREE_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            "slendermod:dead_tree",
            BlockEntityType.Builder.create(DeadTreeBlockEntity::new, DEAD_TREE, SCRATCHED_DEAD_TREE).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, "slendermod:dead_tree"))
    );

    public static BlockEntityType<?> CAR_BODY_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            "slendermod:car_body",
            BlockEntityType.Builder.create(CarBodyBlockEntity::new, CAR_BODY).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, "slendermod:car_body"))
    );

    public static BlockEntityType<?> TRASH_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            "slendermod:trash",
            BlockEntityType.Builder.create(TrashBlockEntity::new, TRASH).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, "slendermod:trash"))
    );

    public static BlockEntityType<?> DEBRIS_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            "slendermod:debris",
            BlockEntityType.Builder.create(DebrisBlockEntity::new, DEBRIS).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, "slendermod:debris"))
    );

    public static BlockEntityType<FroglightBlockEntity> FROGLIGHT_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            "slendermod:froglight",
            BlockEntityType.Builder.create(FroglightBlockEntity::new, Blocks.OCHRE_FROGLIGHT, Blocks.VERDANT_FROGLIGHT, Blocks.PEARLESCENT_FROGLIGHT).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, "slendermod:froglight"))
    );

    public static Block BARBED_WIRE_FENCE = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "barbed_wire_fence"),
            new CustomFenceBlock(AbstractBlock.Settings.copy(Blocks.SPRUCE_FENCE))
    );

    public static Block BONES = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "bones"),
            new BonesBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.PALE_YELLOW).strength(2.0F).sounds(BlockSoundGroup.BONE).noCollision())
    );

    public static Block DEAD_GRASS_BLOCK = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "dead_grass_block"),
            new Block(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK))
    );

    public static Block MANHOLE_COVER = Registry.register(
            Registry.BLOCK,
            new Identifier("slendermod", "manhole_cover"),
            new ManholeCoverBlock(AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR))
    );

    public static void Register() {

    }
}
