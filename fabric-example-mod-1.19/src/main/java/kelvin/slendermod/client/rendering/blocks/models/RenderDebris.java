package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.common.blocks.entities.DebrisBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderDebris extends GeoBlockRenderer<DebrisBlockEntity> {
    public RenderDebris() {
        super(new ModelDebris());
    }
}
