package kelvin.slendermod.client;

import kelvin.slendermod.client.block.renderers.*;
import kelvin.slendermod.client.entity.renderers.RendererSlenderman;
import kelvin.slendermod.client.entity.renderers.RendererSlenderBoss;
import kelvin.slendermod.client.entity.renderers.RendererSmallSlender;
import kelvin.slendermod.registry.BlockRegistry;
import kelvin.slendermod.registry.EntityRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class RenderingRegistry {

    public static void Register() {
        EntityRendererRegistry.register(EntityRegistry.SLENDERMAN, RendererSlenderman::new);
        EntityRendererRegistry.register(EntityRegistry.SLENDER_BOSS, RendererSlenderBoss::new);
        EntityRendererRegistry.register(EntityRegistry.SMALL_SLENDER, RendererSmallSlender::new);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SLENDER_HEAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.DEAD_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SHELF_CONS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BARBED_WIRE_FENCE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BONES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.HOSPITAL_BED, RenderLayer.getTranslucent());

        BlockEntityRendererFactories.register(BlockRegistry.DEAD_TREE_ENTITY, ctx -> new RenderDeadTree());
        BlockEntityRendererFactories.register(BlockRegistry.CAR_BODY_ENTITY, ctx -> new RenderCarBody());
        BlockEntityRendererFactories.register(BlockRegistry.TRASH_ENTITY, ctx -> new RenderTrash());
        BlockEntityRendererFactories.register(BlockRegistry.DEBRIS_ENTITY, ctx -> new RenderDebris());
        BlockEntityRendererFactories.register(BlockRegistry.UFO_INTERIOR_ENTITY, ctx -> new RenderUFOInterior());
    }
}
