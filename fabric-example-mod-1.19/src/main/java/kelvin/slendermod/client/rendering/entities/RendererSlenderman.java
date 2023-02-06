package kelvin.slendermod.client.rendering.entities;

import kelvin.slendermod.client.rendering.entities.models.ModelSlenderman;
import kelvin.slendermod.common.entities.EntitySlenderman;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RendererSlenderman extends GeoEntityRenderer<EntitySlenderman> {
    public RendererSlenderman(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ModelSlenderman());
        super.renderLayers.add(new RenderPutridHorrorLayerGlowing(this));
    }
}
