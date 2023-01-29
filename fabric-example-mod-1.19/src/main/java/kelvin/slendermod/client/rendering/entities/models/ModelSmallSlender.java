package kelvin.slendermod.client.rendering.entities.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.common.entities.EntitySmallSlender;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelSmallSlender extends AnimatedGeoModel<EntitySmallSlender> {
    @Override
    public Identifier getModelResource(EntitySmallSlender object) {
        return new Identifier(SlenderMod.MODID, "geo/slenderkid.geo.json");
    }

    @Override
    public Identifier getTextureResource(EntitySmallSlender object) {
        return new Identifier(SlenderMod.MODID, "textures/entities/slenderkid.png");
    }

    @Override
    public Identifier getAnimationResource(EntitySmallSlender animatable) {
        return new Identifier(SlenderMod.MODID, "animations/slenderkid.animation.json");
    }
}