package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.common.blocks.entities.CarBodyBlockEntity;
import kelvin.slendermod.common.blocks.entities.TrashBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelTrash extends AnimatedGeoModel<TrashBlockEntity> {
    @Override
    public Identifier getModelResource(TrashBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "geo/trash.geo.json");
    }

    @Override
    public Identifier getTextureResource(TrashBlockEntity object) {
        return new Identifier(SlenderMod.MODID, "textures/block/trash.png");
    }

    @Override
    public Identifier getAnimationResource(TrashBlockEntity animatable) {
        return new Identifier(SlenderMod.MODID, "animations/trash.animation.json");
    }
}
