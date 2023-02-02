package kelvin.slendermod.client.rendering.blocks.models;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.common.blocks.DeadTreeBlock;
import kelvin.slendermod.common.blocks.entities.DeadTreeBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class ModelDeadTree extends AnimatedGeoModel<DeadTreeBlockEntity> {
    @Override
    public Identifier getModelResource(DeadTreeBlockEntity object) {
        if (object.isScratched()) {
            return new Identifier(SlenderMod.MODID, "geo/scratched_dead_tree.geo.json");
        }
        return new Identifier(SlenderMod.MODID, "geo/dead_tree.geo.json");
    }

    @Override
    public Identifier getTextureResource(DeadTreeBlockEntity object) {
        if (object.isScratched()) {
            return new Identifier(SlenderMod.MODID, "textures/block/scratched_dead_tree.png");
        }
        return new Identifier(SlenderMod.MODID, "textures/block/dead_tree.png");
    }

    @Override
    public Identifier getAnimationResource(DeadTreeBlockEntity animatable) {
        if (animatable.isScratched()) {
            return new Identifier(SlenderMod.MODID, "animations/scratched_dead_tree.animation.json");
        }
        return new Identifier(SlenderMod.MODID, "animations/dead_tree.animation.json");
    }
}
