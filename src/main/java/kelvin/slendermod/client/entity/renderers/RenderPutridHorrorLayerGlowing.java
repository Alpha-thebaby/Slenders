package kelvin.slendermod.client.entity.renderers;

import kelvin.slendermod.entity.EntitySlenderman;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class RenderPutridHorrorLayerGlowing extends GeoRenderLayer<EntitySlenderman> {

    public RenderPutridHorrorLayerGlowing(GeoRenderer<EntitySlenderman> entityRendererIn) {
        super(entityRendererIn);
    }

//    @Override
//    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, EntitySlenderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//        renderCopyModel(getRenderer(), new Identifier(SlenderMod.MODID, "textures/entities/putrid_horror_glowing.png"),
//                matrixStackIn, bufferIn, 0xFFFFFF, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
//                headPitch, partialTicks, 1, 1, 1);
//        var model = getRenderer().getGeoModel();
//        getRenderer().actuallyRender(model.getModelResource(entitylivingbaseIn));
//    }
}
