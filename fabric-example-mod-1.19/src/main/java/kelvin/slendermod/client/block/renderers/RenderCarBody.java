package kelvin.slendermod.client.block.renderers;

import kelvin.slendermod.blockentity.CarBodyBlockEntity;
import kelvin.slendermod.client.block.models.ModelCarBody;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderCarBody extends GeoBlockRenderer<CarBodyBlockEntity> {

    public RenderCarBody() {
        super(new ModelCarBody());
    }
}
