package kelvin.slendermod.client.block.models;

import kelvin.slendermod.blockentity.CarBodyBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static kelvin.slendermod.SlenderMod.id;

public class ModelCarBody extends GeoModel<CarBodyBlockEntity> {
    @Override
    public Identifier getModelResource(CarBodyBlockEntity object) {
        return id("geo/car_body.geo.json");
    }

    @Override
    public Identifier getTextureResource(CarBodyBlockEntity object) {
        return id("textures/block/car_body.png");
    }

    @Override
    public Identifier getAnimationResource(CarBodyBlockEntity animatable) {
        return id("animations/car_body.animation.json");
    }
}
