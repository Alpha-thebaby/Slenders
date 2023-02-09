package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.common.blocks.entities.UFOInteriorBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderUFOInterior extends GeoBlockRenderer<UFOInteriorBlockEntity> {

    public RenderUFOInterior() {
        super(new ModelUFOInterior());
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, UFOInteriorBlockEntity animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.scale(1.5F, 1.5F, 1.5F);
        poseStack.translate(-0.18F, 0.1F, 0);
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
