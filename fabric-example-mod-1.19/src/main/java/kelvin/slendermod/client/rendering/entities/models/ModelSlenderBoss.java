package kelvin.slendermod.client.rendering.entities.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.common.entities.EntitySlenderBoss;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSlenderBoss extends AnimatedGeoModel<EntitySlenderBoss>
{

    @Override
    public Identifier getModelResource(EntitySlenderBoss object) {
        return new Identifier(SlenderMod.MODID, "geo/slender_boss.geo.json");
    }

    @Override
    public Identifier getTextureResource(EntitySlenderBoss object) {
        return new Identifier(SlenderMod.MODID, "textures/entities/slender_boss.png");
    }

    @Override
    public Identifier getAnimationResource(EntitySlenderBoss animatable) {
        return new Identifier(SlenderMod.MODID, "animations/slender_boss.animation.json");
    }
}