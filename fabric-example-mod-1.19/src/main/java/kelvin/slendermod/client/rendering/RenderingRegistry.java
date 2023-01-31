package kelvin.slendermod.client.rendering;

import kelvin.slendermod.client.rendering.blocks.models.*;
import kelvin.slendermod.client.rendering.entities.RendererSlenderman;
import kelvin.slendermod.client.rendering.entities.RendererSlenderBoss;
import kelvin.slendermod.client.rendering.entities.RendererSmallSlender;
import kelvin.slendermod.common.blocks.BlockRegistry;
import kelvin.slendermod.common.entities.EntityRegistry;
import ladysnake.satin.api.util.RenderLayerHelper;
import ladysnake.satin.impl.BlockRenderLayerRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

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

        BlockEntityRendererRegistry.register(BlockRegistry.DEAD_TREE_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new RenderDeadTree());
        BlockEntityRendererRegistry.register(BlockRegistry.CAR_BODY_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new RenderCarBody());
        BlockEntityRendererRegistry.register(BlockRegistry.TRASH_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new RenderTrash());
        BlockEntityRendererRegistry.register(BlockRegistry.DEBRIS_ENTITY, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new RenderDebris());
    }
}
