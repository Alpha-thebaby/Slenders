package kelvin.slendermod.client.block.models;

import kelvin.slendermod.blockentity.TrashBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static kelvin.slendermod.SlenderMod.id;

public class ModelTrash extends GeoModel<TrashBlockEntity> {
    @Override
    public Identifier getModelResource(TrashBlockEntity object) {
        return id("geo/trash.geo.json");
    }

    @Override
    public Identifier getTextureResource(TrashBlockEntity object) {
        return id("textures/block/trash.png");
    }

    @Override
    public Identifier getAnimationResource(TrashBlockEntity animatable) {
        return id("animations/trash.animation.json");
    }
}
