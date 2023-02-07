package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.common.blocks.entities.CarBodyBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderCarBody extends GeoBlockRenderer<CarBodyBlockEntity> {

    public RenderCarBody() {
        super(new ModelCarBody());
    }
}
