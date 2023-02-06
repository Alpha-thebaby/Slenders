package kelvin.slendermod.common.blocks.entities;

import kelvin.slendermod.common.blocks.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DeadTreeBlockEntity extends BlockEntity implements GeoBlockEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private final boolean isScratched;

    public DeadTreeBlockEntity(BlockPos pos, BlockState state, boolean isScratched) {
        super(BlockRegistry.DEAD_TREE_ENTITY, pos, state);
        this.isScratched = isScratched;
    }

    public DeadTreeBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, false);
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        //event.getController().setAnimation();
        //event.getController().transitionLengthTicks = 0;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    public boolean isScratched() {
        return isScratched;
    }
}
