package kelvin.slendermod.entity;

import kelvin.slendermod.registry.SoundRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.RawAnimation;

public class EntitySlenderman extends AbstractEntitySlender {

    private static final RawAnimation ANIM_IDLE = RawAnimation.begin().then("animation.idle", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_WALK = RawAnimation.begin().then("animation.walk", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_ROAR = RawAnimation.begin().then("animation.roar", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation ANIM_RUN = RawAnimation.begin().then("animation.running", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_LOOK = RawAnimation.begin().then("animation.look", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_ATTACK = RawAnimation.begin().then("animation.attack", Animation.LoopType.PLAY_ONCE);

    public EntitySlenderman(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        stepHeight = 1.0f;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40);
    }

    @Override
    protected boolean canBreakDoors() {
        return false;
    }

    @Override
    protected float getFastMovementSpeed() {
        return 0.5f;
    }

    @Override
    protected float getSlowMovementSpeed() {
        return 0.45f;
    }

    @Override
    protected int getDamage() {
        return 15;
    }

    @Override
    protected int getReach() {
        return 4;
    }

    @Override
    protected int getDamageHeight() {
        return 2;
    }

    @Override
    protected SoundEvent getAngrySound() {
        return SoundRegistry.HORROR_ROAR;
    }

    @Override
    protected SoundEvent getLookingSound() {
        return SoundRegistry.HORROR_GROWL;
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
