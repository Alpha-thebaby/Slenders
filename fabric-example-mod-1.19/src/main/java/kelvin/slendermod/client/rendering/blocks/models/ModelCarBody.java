package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.common.blocks.entities.CarBodyBlockEntity;
import kelvin.slendermod.common.blocks.entities.DeadTreeBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelCarBody extends AnimatedGeoModel<CarBodyBlockEntity> {
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
