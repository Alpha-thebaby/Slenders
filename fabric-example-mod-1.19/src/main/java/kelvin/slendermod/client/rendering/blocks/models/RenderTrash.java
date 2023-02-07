package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.common.blocks.entities.TrashBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderTrash extends GeoBlockRenderer<TrashBlockEntity> {

    public RenderTrash() {
        super(new ModelTrash());
    }
}
