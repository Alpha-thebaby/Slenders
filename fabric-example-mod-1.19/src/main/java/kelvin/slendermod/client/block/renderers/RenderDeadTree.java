package kelvin.slendermod.client.block.renderers;

import kelvin.slendermod.blockentity.DeadTreeBlockEntity;
import kelvin.slendermod.client.block.models.ModelDeadTree;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderDeadTree extends GeoBlockRenderer<DeadTreeBlockEntity> {

    public RenderDeadTree() {
        super(new ModelDeadTree());
    }
}
