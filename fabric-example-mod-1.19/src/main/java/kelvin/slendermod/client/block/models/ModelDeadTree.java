package kelvin.slendermod.client.block.models;

import kelvin.slendermod.blockentity.DeadTreeBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static kelvin.slendermod.SlenderMod.id;

public class ModelDeadTree extends GeoModel<DeadTreeBlockEntity> {
    @Override
    public Identifier getModelResource(DeadTreeBlockEntity object) {
        if (object.isScratched()) {
            return id("geo/scratched_dead_tree.geo.json");
        }
        return id("geo/dead_tree.geo.json");
    }

    @Override
    public Identifier getTextureResource(DeadTreeBlockEntity object) {
        if (object.isScratched()) {
            return id("textures/block/scratched_dead_tree.png");
        }
        return id("textures/block/dead_tree.png");
    }

    @Override
    public Identifier getAnimationResource(DeadTreeBlockEntity animatable) {
        if (animatable.isScratched()) {
            return id("animations/scratched_dead_tree.animation.json");
        }
        return id("animations/dead_tree.animation.json");
    }
}
