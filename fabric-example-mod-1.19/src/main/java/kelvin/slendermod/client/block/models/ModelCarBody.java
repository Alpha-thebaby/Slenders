package kelvin.slendermod.client.block.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.blockentity.CarBodyBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ModelCarBody extends GeoModel<CarBodyBlockEntity> {
    @Override
    public Identifier getModelResource(CarBodyBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "geo/car_body.geo.json");
    }

    @Override
    public Identifier getTextureResource(CarBodyBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "textures/block/car_body.png");
    }

    @Override
    public Identifier getAnimationResource(CarBodyBlockEntity animatable) {
        return new Identifier(SlenderMod.MODID, "animations/car_body.animation.json");
    }
}
