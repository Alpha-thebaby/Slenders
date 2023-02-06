package kelvin.slendermod.client.rendering.entities;

import kelvin.slendermod.client.rendering.entities.models.ModelSmallSlender;
import kelvin.slendermod.common.entities.EntitySlenderBoss;
import kelvin.slendermod.common.entities.EntitySmallSlender;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RendererSmallSlender extends GeoEntityRenderer<EntitySmallSlender> {

    public RendererSmallSlender(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ModelSmallSlender());
    }

    @Override
    public void render(EntitySmallSlender entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        stack.push();
        stack.scale(0.5f, 0.5f, 0.5f);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.pop();
    }
}
