package kelvin.slendermod.client.rendering.entities;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.common.entities.EntitySlenderman;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class RenderPutridHorrorLayerGlowing extends GeoLayerRenderer<EntitySlenderman> {
    public RenderPutridHorrorLayerGlowing(IGeoRenderer<EntitySlenderman> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, EntitySlenderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        renderCopyModel(getEntityModel(), new Identifier(SlenderMod.MODID, "textures/entities/putrid_horror_glowing.png"),
                matrixStackIn, bufferIn, 0xFFFFFF, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
                headPitch, partialTicks, 1, 1, 1);
    }
}
