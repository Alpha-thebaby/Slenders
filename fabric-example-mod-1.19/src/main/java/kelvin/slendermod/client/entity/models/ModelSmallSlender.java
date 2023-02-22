package kelvin.slendermod.client.entity.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.entity.EntitySmallSlender;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ModelSmallSlender extends GeoModel<EntitySmallSlender> {
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

    @Override
    public RenderLayer getRenderType(EntitySmallSlender animatable, Identifier texture) {
        return RenderLayer.getEntityCutoutNoCull(texture);
    }
}