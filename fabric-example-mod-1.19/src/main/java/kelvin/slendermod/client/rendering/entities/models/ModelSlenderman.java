package kelvin.slendermod.client.rendering.entities.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.common.entities.EntitySlenderman;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSlenderman extends AnimatedGeoModel<EntitySlenderman> {
    @Override
    public Identifier getModelResource(EntitySlenderman object) {
        return new Identifier(SlenderMod.MODID, "geo/slenderman.geo.json");
    }

    @Override
    public Identifier getTextureResource(EntitySlenderman object) {
        return new Identifier(SlenderMod.MODID, "textures/entities/slenderman.png");
    }

    @Override
    public Identifier getAnimationResource(EntitySlenderman animatable) {
        return new Identifier(SlenderMod.MODID, "animations/slenderman.animation.json");
    }
}