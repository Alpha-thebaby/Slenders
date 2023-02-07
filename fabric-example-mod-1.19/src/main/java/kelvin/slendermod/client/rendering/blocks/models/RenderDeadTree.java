package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.common.blocks.entities.DeadTreeBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderDeadTree extends GeoBlockRenderer<DeadTreeBlockEntity> {

    public RenderDeadTree() {
        super(new ModelDeadTree());
    }
}
