package kelvin.slendermod.entity;

import kelvin.slendermod.registry.SoundRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animation.*;

public class EntitySmallSlender extends AbstractEntitySlender {

    private static final RawAnimation ANIM_IDLE = RawAnimation.begin().then("animation.idle", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_WALK = RawAnimation.begin().then("animation.walk", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_ROAR = RawAnimation.begin().then("animation.roar", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation ANIM_RUN = RawAnimation.begin().then("animation.running", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_LOOK = RawAnimation.begin().then("animation.look", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_ATTACK = RawAnimation.begin().then("animation.attack", Animation.LoopType.PLAY_ONCE);

    public EntitySmallSlender(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        stepHeight = 1.0f;
        ((MobNavigation)getNavigation()).setCanPathThroughDoors(true);
    }

    @Override
    protected boolean canBreakDoors() {
        return true;
    }

    @Override
    protected float getFastMovementSpeed() {
        return 0.75f;
    }

    @Override
    protected float getSlowMovementSpeed() {
        return 0.65f;
    }

    @Override
    protected int getDamage() {
        return 5;
    }

    @Override
    protected int getReach() {
        return 2;
    }

    @Override
    protected int getDamageHeight() {
        return 0;
    }

    @Override
    protected SoundEvent getAngrySound() {
        return SoundRegistry.SMALL_SLENDER_CHASING;
    }

    @Override
    protected SoundEvent getLookingSound() {
        return SoundRegistry.SMALL_SLENDER_LOOKING;
    }

    @Override
    protected RawAnimation getIdleAnim() {
        return ANIM_IDLE;
    }

    @Override
    protected RawAnimation getWalkAnim() {
        return ANIM_WALK;
    }

    @Override
    protected RawAnimation getRoarAnim() {
        return ANIM_ROAR;
    }

    @Override
    protected RawAnimation getRunAnim() {
        return ANIM_RUN;
    }

    @Override
    protected RawAnimation getLookAnim() {
        return ANIM_LOOK;
    }

    @Override
    protected RawAnimation getAttackAnim() {
        return ANIM_ATTACK;
    }
}
