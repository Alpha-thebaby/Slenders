package kelvin.slendermod.registry;

import kelvin.slendermod.entity.EntitySlenderBoss;
import kelvin.slendermod.entity.EntitySlenderman;
import kelvin.slendermod.entity.EntitySmallSlender;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;

import static kelvin.slendermod.SlenderMod.id;

public class EntityRegistry {

    public static EntityType<EntitySlenderman> SLENDERMAN = register("slenderman",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntitySlenderman::new).dimensions(EntityDimensions.fixed(1.3964844f, 4f)).build()
    );

    public static EntityType<EntitySmallSlender> SMALL_SLENDER = register("small_slender",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntitySmallSlender::new).dimensions(EntityDimensions.fixed(0.75f, 1.5f)).build()
    );

    public static EntityType<EntitySlenderBoss> SLENDER_BOSS = register("slender_boss",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntitySlenderBoss::new).dimensions(EntityDimensions.fixed(2, 4)).build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(SLENDERMAN, EntitySlenderman.createAttributes());
        FabricDefaultAttributeRegistry.register(SLENDER_BOSS, EntitySlenderBoss.createAttributes());
        FabricDefaultAttributeRegistry.register(SMALL_SLENDER, EntitySmallSlender.createAttributes());

        spawnSlender(SLENDERMAN);
        spawnSlender(SMALL_SLENDER);

        BiomeModifications.create(id("remove_all_entities")).add(ModificationPhase.REMOVALS, biomeSelectionContext ->
                biomeSelectionContext.hasTag(BiomeTags.IS_OVERWORLD), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getSpawnSettings().removeSpawns((spawnGroup, spawnEntry) ->
                    spawnGroup == SpawnGroup.MONSTER && spawnEntry.type != SLENDERMAN && spawnEntry.type != SMALL_SLENDER);
        });
    }

    private static <T extends MobEntity> void spawnSlender(EntityType<T> slender) {
        SpawnRestriction.register(slender, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (type, world, spawnReason, pos, random) ->
                world.getDifficulty() != Difficulty.PEACEFUL && MobEntity.canMobSpawn(type, world, spawnReason, pos, random));

        BiomeModifications.addSpawn(context -> context.hasTag(BiomeTags.IS_OVERWORLD) || context.hasTag(BiomeTags.IS_NETHER) || context.hasTag(BiomeTags.IS_END), SpawnGroup.MONSTER, slender, 37, 1, 3);
    }

    private static <T extends EntityType<?>> T register(String name, T entityType) {
        return Registry.register(Registries.ENTITY_TYPE, id(name), entityType);
    }
}
