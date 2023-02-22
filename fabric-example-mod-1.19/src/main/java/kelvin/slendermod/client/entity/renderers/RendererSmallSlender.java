package kelvin.slendermod.client.entity.renderers;

import kelvin.slendermod.client.entity.models.ModelSmallSlender;
import kelvin.slendermod.entity.EntitySmallSlender;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
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
