package kelvin.slendermod.client.block.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.blockentity.UFOInteriorBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ModelUFOInterior extends GeoModel<UFOInteriorBlockEntity> {
    @Override
    public Identifier getModelResource(UFOInteriorBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "geo/ufo_interior.geo.json");
    }

    @Override
    public Identifier getTextureResource(UFOInteriorBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "textures/block/ufo_interior.png");
    }

    @Override
    public Identifier getAnimationResource(UFOInteriorBlockEntity animatable) {
        return new Identifier(SlenderMod.MODID, "animations/ufo_interior.animation.json");
    }
}
