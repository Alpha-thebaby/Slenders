package kelvin.slendermod.client.block.models;

import kelvin.slendermod.blockentity.DebrisBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static kelvin.slendermod.SlenderMod.id;

public class ModelDebris extends GeoModel<DebrisBlockEntity> {
    @Override
    public Identifier getModelResource(DebrisBlockEntity object) {
        return id("geo/debris.geo.json");
    }

    @Override
    public Identifier getTextureResource(DebrisBlockEntity object) {
        return id("textures/block/debris.png");
    }

    @Override
    public Identifier getAnimationResource(DebrisBlockEntity animatable) {
        return id("animations/debris.animation.json");
    }
}
