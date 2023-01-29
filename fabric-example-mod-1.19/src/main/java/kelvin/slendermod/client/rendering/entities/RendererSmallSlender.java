package kelvin.slendermod.client.rendering.entities;

import kelvin.slendermod.client.rendering.entities.models.ModelSmallSlender;
import kelvin.slendermod.common.entities.EntitySmallSlender;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RendererSmallSlender extends GeoEntityRenderer<EntitySmallSlender> {
    public RendererSmallSlender(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ModelSmallSlender());
    }

    @Override
    public RenderLayer getRenderType(EntitySmallSlender animatable, float partialTicks, MatrixStack stack,
                                      VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                      Identifier textureLocation) {
        return RenderLayer.getEntityCutoutNoCull(textureLocation);
    }

    @Override
    public void render(GeoModel model, EntitySmallSlender animatable, float partialTicks, RenderLayer type, MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.push();
        matrixStackIn.scale(0.5f, 0.5f, 0.5f);
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }
}
