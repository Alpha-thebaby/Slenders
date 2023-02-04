package kelvin.slendermod.common.entities;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;

public class EntityRegistry {
    public static EntityType<EntitySlenderman> SLENDERMAN = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("slendermod", "slenderman"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EntitySlenderman::new).dimensions(EntityDimensions.fixed(0.75f, 2.9f)).build()
    );

    public static EntityType<EntitySmallSlender> SMALL_SLENDER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("slendermod", "small_slender"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EntitySmallSlender::new).dimensions(EntityDimensions.fixed(0.75f, 1.5f)).build()
    );

    public static EntityType<EntitySlenderBoss> SLENDER_BOSS = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("slendermod", "slender_boss"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EntitySlenderBoss::new).dimensions(EntityDimensions.fixed(2, 4)).build()
    );

    public static void Register()
    {
        FabricDefaultAttributeRegistry.register(SLENDERMAN, EntitySlenderman.createMobAttributes());
        FabricDefaultAttributeRegistry.register(SLENDER_BOSS, EntitySlenderBoss.createAttributes());
        FabricDefaultAttributeRegistry.register(SMALL_SLENDER, EntitySmallSlender.createMobAttributes());

        spawnSlender(SLENDERMAN);
        spawnSlender(SMALL_SLENDER);
    }

    private static <T extends MobEntity> void spawnSlender(EntityType<T> slender) {
        SpawnRestriction.register(slender, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (type, world, spawnReason, pos, random) ->
                world.getDifficulty() != Difficulty.PEACEFUL && world.getBlockState(pos.down()).allowsSpawning(world, pos, type));

        BiomeModifications.addSpawn(context -> context.hasTag(BiomeTags.IS_OVERWORLD) || context.hasTag(BiomeTags.IS_NETHER) || context.hasTag(BiomeTags.IS_END), SpawnGroup.CREATURE, slender, 25, 1, 3);
    }
}
