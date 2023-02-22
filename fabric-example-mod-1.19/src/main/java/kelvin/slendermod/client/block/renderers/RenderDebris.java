package kelvin.slendermod.client.block.renderers;

import kelvin.slendermod.blockentity.DebrisBlockEntity;
import kelvin.slendermod.client.block.models.ModelDebris;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderDebris extends GeoBlockRenderer<DebrisBlockEntity> {
    public RenderDebris() {
        super(new ModelDebris());
    }
}
