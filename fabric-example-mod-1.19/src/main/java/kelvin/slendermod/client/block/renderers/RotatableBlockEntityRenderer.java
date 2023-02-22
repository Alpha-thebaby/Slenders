package kelvin.slendermod.client.block.renderers;

import kelvin.slendermod.blockentity.RotatableBlockEntity;
import kelvin.slendermod.client.block.models.RotatableBlockEntityModel;
import kelvin.slendermod.registry.BlockRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RotatableBlockEntityRenderer extends GeoBlockRenderer<RotatableBlockEntity> {

    public RotatableBlockEntityRenderer() {
        super(new RotatableBlockEntityModel<>());
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, RotatableBlockEntity animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (animatable.getCachedState().getBlock() == BlockRegistry.UFO_INTERIOR) {
            poseStack.scale(1.5F, 1.5F, 1.5F);
            poseStack.translate(-0.18F, 0.1F, 0);
        }
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
