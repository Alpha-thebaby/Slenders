package kelvin.slendermod.client.entity.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.entity.EntitySlenderBoss;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ModelSlenderBoss extends GeoModel<EntitySlenderBoss>
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

    @Override
    public RenderLayer getRenderType(EntitySlenderBoss animatable, Identifier texture) {
        return RenderLayer.getEntityCutoutNoCull(texture);
    }
}