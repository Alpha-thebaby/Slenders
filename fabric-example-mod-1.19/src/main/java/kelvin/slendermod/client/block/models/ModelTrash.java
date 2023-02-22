package kelvin.slendermod.client.block.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.blockentity.TrashBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ModelTrash extends GeoModel<TrashBlockEntity> {
    @Override
    public Identifier getModelResource(TrashBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "geo/trash.geo.json");
    }

    @Override
    public Identifier getTextureResource(TrashBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "textures/block/trash.png");
    }

    @Override
    public Identifier getAnimationResource(TrashBlockEntity animatable) {
        return new Identifier(SlenderMod.MODID, "animations/trash.animation.json");
    }
}
