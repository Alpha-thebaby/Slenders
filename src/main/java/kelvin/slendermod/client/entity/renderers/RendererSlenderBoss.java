package kelvin.slendermod.client.entity.renderers;

import kelvin.slendermod.client.entity.models.ModelSlenderBoss;
import kelvin.slendermod.entity.EntitySlenderBoss;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RendererSlenderBoss extends GeoEntityRenderer<EntitySlenderBoss> {

    public RendererSlenderBoss(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ModelSlenderBoss());
        //super.layerRenderers.add(new RenderPutridHorrorLayerGlowing(this));
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
