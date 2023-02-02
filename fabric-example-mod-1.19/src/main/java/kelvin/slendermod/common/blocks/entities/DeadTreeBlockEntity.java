package kelvin.slendermod.common.blocks.entities;

import kelvin.slendermod.common.blocks.BlockRegistry;
import kelvin.slendermod.common.blocks.DeadTreeBlock;
import kelvin.slendermod.common.entities.EntitySlenderman;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DeadTreeBlockEntity extends BlockEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private final boolean isScratched;

    public DeadTreeBlockEntity(BlockPos pos, BlockState state, boolean isScratched) {
        super(BlockRegistry.DEAD_TREE_ENTITY, pos, state);
        this.isScratched = isScratched;
    }

    public DeadTreeBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, false);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        //event.getController().setAnimation();
        //event.getController().transitionLengthTicks = 0;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<DeadTreeBlockEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public boolean isScratched() {
        return isScratched;
    }
}
