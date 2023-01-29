package kelvin.slendermod.client.rendering.entities;

import kelvin.slendermod.client.rendering.entities.models.ModelSlenderBoss;
import kelvin.slendermod.common.entities.EntitySlenderBoss;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RendererSlenderBoss extends GeoEntityRenderer<EntitySlenderBoss> {
    public RendererSlenderBoss(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ModelSlenderBoss());
        //super.layerRenderers.add(new RenderPutridHorrorLayerGlowing(this));
    }

    @Override
    public RenderLayer getRenderType(EntitySlenderBoss animatable, float partialTicks, MatrixStack stack,
                                      VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                      Identifier textureLocation) {
        return RenderLayer.getEntityCutoutNoCull(textureLocation);
    }

    @Override
    public void render(EntitySlenderBoss entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        stack.push();
        stack.scale(0.5f, 0.5f, 0.5f);
        //stack.translate(0, 2.5f, 0);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.pop();
    }
}
