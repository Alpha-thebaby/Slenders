package kelvin.slendermod.client.rendering.entities;

import kelvin.slendermod.client.rendering.entities.models.ModelSlenderman;
import kelvin.slendermod.common.entities.EntitySlenderman;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RendererSlenderman extends GeoEntityRenderer<EntitySlenderman> {
    public RendererSlenderman(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ModelSlenderman());
        super.layerRenderers.add(new RenderPutridHorrorLayerGlowing(this));
    }

    @Override
    public RenderLayer getRenderType(EntitySlenderman animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityCutoutNoCull(textureLocation);
    }
}
