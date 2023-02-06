package kelvin.slendermod.client.rendering.entities.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.common.entities.EntitySlenderman;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ModelSlenderman extends GeoModel<EntitySlenderman> {
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

    @Override
    public RenderLayer getRenderType(EntitySlenderman animatable, Identifier texture) {
        return RenderLayer.getEntityCutoutNoCull(texture);
    }
}