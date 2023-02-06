package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.common.blocks.entities.DebrisBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ModelDebris extends GeoModel<DebrisBlockEntity> {
    @Override
    public Identifier getModelResource(DebrisBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "geo/debris.geo.json");
    }

    @Override
    public Identifier getTextureResource(DebrisBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "textures/block/debris.png");
    }

    @Override
    public Identifier getAnimationResource(DebrisBlockEntity animatable) {
        return new Identifier(SlenderMod.MODID, "animations/debris.animation.json");
    }
}
