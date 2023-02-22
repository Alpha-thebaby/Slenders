package kelvin.slendermod.client.block.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.blockentity.UFOInteriorBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static kelvin.slendermod.SlenderMod.id;

public class ModelUFOInterior extends GeoModel<UFOInteriorBlockEntity> {
    @Override
    public Identifier getModelResource(UFOInteriorBlockEntity object) {
        return id("geo/ufo_interior.geo.json");
    }

    @Override
    public Identifier getTextureResource(UFOInteriorBlockEntity object) {
        return id("textures/block/ufo_interior.png");
    }

    @Override
    public Identifier getAnimationResource(UFOInteriorBlockEntity animatable) {
        return id("animations/ufo_interior.animation.json");
    }
}
