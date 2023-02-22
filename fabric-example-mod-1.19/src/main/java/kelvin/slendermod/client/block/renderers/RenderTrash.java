package kelvin.slendermod.client.block.renderers;

import kelvin.slendermod.blockentity.TrashBlockEntity;
import kelvin.slendermod.client.block.models.ModelTrash;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RenderTrash extends GeoBlockRenderer<TrashBlockEntity> {

    public RenderTrash() {
        super(new ModelTrash());
    }
}
