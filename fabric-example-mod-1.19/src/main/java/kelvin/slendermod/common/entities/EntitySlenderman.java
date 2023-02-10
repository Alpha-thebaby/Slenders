package kelvin.slendermod.common.entities;

import kelvin.slendermod.common.sounds.SoundRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EntitySlenderman extends EntitySlenderRoarListener {
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation ANIM_IDLE = RawAnimation.begin().then("animation.walk", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_WALK = RawAnimation.begin().then("animation.walk", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_ROAR = RawAnimation.begin().then("animation.roar", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation ANIM_RUNNING = RawAnimation.begin().then("animation.running", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_LOOK = RawAnimation.begin().then("animation.look", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_ATTACK = RawAnimation.begin().then("animation.attack", Animation.LoopType.PLAY_ONCE);

    private static final RawAnimation[] ANIMATIONS = {ANIM_IDLE, ANIM_WALK, ANIM_ROAR, ANIM_RUNNING, ANIM_LOOK, ANIM_ATTACK};

    private int ANIM_ID_IDLE = 0, ANIM_ID_WALK = 1, ANIM_ID_ROAR = 2, ANIM_ID_RUNNING = 3, ANIM_ID_LOOK = 4, ANIM_ID_ATTACK = 5;

    private static final int IDLE = 0, WANDERING = 1, LOOKING = 2, CHASING = 3, CONFUSED = 4, ATTACKING = 5;

    private int current_state = 0;

    private int time_in_state = 0;
    private Entity targetEntity;

    private Vec3d lastSeenPos;

    private int anger_timer = 0;
    private int search_count = 0;

    private float anger = 0;

    private static final TrackedData<Integer> CURRENT_ANIMATION = DataTracker.registerData(EntitySlenderman.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> GROWL_TRACKER = DataTracker.registerData(EntitySlenderman.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ROAR_TRACKER = DataTracker.registerData(EntitySlenderman.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected EntitySlenderman(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0f;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CURRENT_ANIMATION, 0);
        this.dataTracker.startTracking(GROWL_TRACKER, false);
        this.dataTracker.startTracking(ROAR_TRACKER, false);
    }

    @Override
    public void pushAway(Entity entity) {

    }

    @Override
    public void pushAwayFrom(Entity entity) {

    }

    @Override
    public float getAnger() {
        return anger;
    }

    @Override
    protected void setChasing(BlockPos pos) {
        changeState(CHASING);
        time_in_state = 80;
        anger_timer = 1;
        lastSeenPos = pos.toCenterPos();
    }

    @Override
    public void tick() {
        super.tick();


        if (!world.isClient) {

            double speed = Math.sqrt(Math.pow(this.getVelocity().x, 2) + Math.pow(this.getVelocity().z, 2));

            if (targetEntity == null) {
                tryToSpotTargets();
            }

            switch (current_state) {
                case IDLE:
                    setCurrentAnimation(ANIM_ID_IDLE);
                    changeState(WANDERING);

                    if (time_in_state > 20 * 3) {
                        if (random.nextBoolean()) {
                            changeState(WANDERING);
                        }
                    }
                    break;
                case WANDERING:

                    if (speed > 0.01f) {
                        setCurrentAnimation(ANIM_ID_WALK);
                    } else {
                        setCurrentAnimation(ANIM_ID_IDLE);
                    }

                    if (navigation.isIdle()) {

                        var pos = getPos();

                        if (search_count > 0 && lastSeenPos != null) {
                            search_count--;
                            pos = lastSeenPos;
                        }
                        else {
                            var target = getClosestTarget();
                            if (target != null) {
                                pos = target.getPos();
                            }
                        }
                        var current_point = pos.add(random.nextInt(40) - 20, random.nextInt(30) - 15, random.nextInt(40) - 20);



                        if (getPos().distanceTo(current_point) <= 10) {
                            current_point = current_point.subtract(getPos()).normalize().multiply(10).add(getPos());
                        }

                        navigation.startMovingTo(current_point.x, current_point.y, current_point.z, 0.25f);
                    }

                    if (time_in_state > 20 * 12) {
                        if (random.nextBoolean()) {
                            changeState(WANDERING);
                        } else {
                            navigation.startMovingTo(getPos().x, getPos().y, getPos().z, 0);
                            changeState(LOOKING);
                        }
                    }

                    break;
                case LOOKING:
                    navigation.stop();
                    if (dataTracker.get(CURRENT_ANIMATION).intValue() != ANIM_ID_LOOK) {
                        dataTracker.set(GROWL_TRACKER, true);
                    }
                    setCurrentAnimation(ANIM_ID_LOOK);
                    setVelocity(0, getVelocity().y, 0);
                    if (time_in_state > 20 * 6) {
                        changeState(WANDERING);
                    }

                    break;
                case CHASING:
                    if (time_in_state < 80 && this.anger_timer <= 0) {
                        setCurrentAnimation(ANIM_ID_ROAR);
                        if (targetEntity != null) {
                            this.lookAtEntity(targetEntity, 180, 90);
                        }

                        if (time_in_state == 10) {
                            dataTracker.set(ROAR_TRACKER, true);
                        }
                    } else {
                        anger_timer = 20 * 10;
                        setCurrentAnimation(ANIM_ID_RUNNING);

                        if (anger > 0) {
                            if (!canSee(targetEntity)) {
                                anger--;
                            }
                            lastSeenPos = new Vec3d(targetEntity.getPos().x, targetEntity.getPos().y, targetEntity.getPos().z);

                            this.navigation.startMovingTo(targetEntity, 0.5f);

                            if (targetEntity.distanceTo(this) <= 4 && time_in_state > 20 * 2) {
                                changeState(ATTACKING);
                            }
                        } else {
                            if (lastSeenPos != null) {
                                this.navigation.startMovingTo(lastSeenPos.x, lastSeenPos.y, lastSeenPos.z, 0.45f);
                                changeState(CONFUSED);
                                targetEntity = null;
                            } else {
                                changeState(WANDERING);
                            }
                        }

                    }

                    break;
                case CONFUSED:
                    targetEntity = null;
                    if (navigation.isIdle() || time_in_state > 20 * 3) {
                        changeState(WANDERING);
                        search_count = 3;
                    }
                    break;
                case ATTACKING:
                    this.setCurrentAnimation(ANIM_ID_ATTACK);
                    if (targetEntity != null) {
                        this.lookAtEntity(targetEntity, 180, 90);
                    }
                    if (time_in_state > 20) {
                        if (anger> 0)
                        {
                            changeState(CHASING);
                        } else {
                            changeState(IDLE);
                        }
                    } else if (time_in_state == 4) {
                        if (targetEntity != null) {
                            if (targetEntity.getPos().distanceTo(getPos().add(0, 2, 0)) <= 4) {
                                targetEntity.damage(DamageSource.mob(this), 15);
                            }
                        }
                    }
                    break;
                default:
                    navigation.stop();
                    changeState(IDLE);
            }
        } else {
            if (dataTracker.get(GROWL_TRACKER).booleanValue()) {
                world.playSound(getX(), getY(), getZ(), SoundRegistry.HORROR_GROWL, SoundCategory.HOSTILE, 2.5f, (float)(random.nextDouble() - 0.5f) / 5.0f + 1.0f, true);
                dataTracker.set(GROWL_TRACKER, false);
            } else if (dataTracker.get(ROAR_TRACKER).booleanValue()) {
                world.playSound(getX(), getY(), getZ(), SoundRegistry.HORROR_ROAR, SoundCategory.HOSTILE, 2.0f, (float)(random.nextDouble() - 0.5f) / 5.0f + 1.0f, true);
                dataTracker.set(ROAR_TRACKER, false);
            }
        }


        time_in_state++;
        anger_timer--;

    }

    private void changeState(int new_state) {
        time_in_state = 0;
        current_state = new_state;
    }

    private void setCurrentAnimation(int animation) {

        if (dataTracker.get(CURRENT_ANIMATION).intValue() != animation) {
            dataTracker.set(CURRENT_ANIMATION, animation);
        }
    }

    private void tryToSpotTargets() {
        var entities = this.world.getPlayers();

        double distance = 1000;
        Entity closest = null;

        for (int i = 0; i < entities.size(); i++) {
            if (this.canSee(entities.get(i))) {
                var entity = entities.get(i);
                var dist = entity.distanceTo(this);
                if (dist < distance) {
                    if (entity.isCreative()) {
                        continue;
                    }
                    closest = entity;
                    distance = dist;
                }
            }
        }

        float max_anger = 2;
        if (anger < 20 * max_anger) {
            if (closest != null) {

                if (canSee(closest)) {
                    int light = Math.max(world.getLightLevel(LightType.BLOCK, closest.getBlockPos().up()), world.getLightLevel(LightType.SKY, closest.getBlockPos().up()));

                    if (closest.distanceTo(this) <= 3) {
                        anger += max_anger;
                    }
                    else {

                        Vec3d look = new Vec3d(this.lookControl.getLookX(), this.lookControl.getLookY(), this.lookControl.getLookZ()).subtract(getPos()).normalize();
                        Vec3d direction = closest.getPos().subtract(getPos()).normalize();
                        double dot = look.dotProduct(direction);

                        if (closest.distanceTo(this) <= 10) {

                            if (light > 7) {
                                if (dot > 0) {
                                    anger += max_anger;
                                } else if (closest.isSprinting()) {
                                    anger += max_anger * (0.8f);
                                } else {
                                    anger ++;
                                }
                            } else {
                                if (dot > 0) {
                                    if (closest.isSprinting()) {
                                        anger += max_anger * (0.8f);
                                    } else if (closest.isSneaking()) {
                                        anger += 0.25f;
                                    } else {
                                        anger += 0.75f;
                                    }
                                } else if (closest.isSprinting()) {
                                    anger++;
                                } else if (!closest.isSneaking()) {
                                    anger += 0.5f;
                                }
                            }
                        } else if (closest.distanceTo(this) <= 20) {
                            if (light > 7) {
                                if (dot > 0) {
                                    if (closest.isSprinting()) {
                                        anger++;
                                    } else if (!closest.isSneaking()) {
                                        anger += 0.25f;
                                    }
                                }
                            } else {
                                if (closest.isSprinting()) {
                                    anger += 0.25f;
                                }
                            }
                        }
                    }
                }
            } else {
                if (anger > 0) {
                    anger -= 0.1f;
                } else {
                    anger = 0;
                }
            }
        } else {
            targetEntity = closest;
            if (closest != null && current_state != CHASING) {
                lastSeenPos = targetEntity.getPos();

                if (current_state == IDLE || current_state == WANDERING || current_state == LOOKING) {
                    this.changeState(CHASING);
                }
            }
        }

    }

    private Entity getClosestTarget() {
        var entities = this.world.getPlayers();

        double distance = 1000;
        Entity closest = null;

        for (int i = 0; i < entities.size(); i++) {
            var entity = entities.get(i);
            var dist = entity.distanceTo(this);
            if (dist < distance) {
                if (entity.isCreative()) {
                    continue;
                }

                closest = entity;
                distance = dist;
            }
        }

        return closest;
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        event.getController().setAnimation(ANIMATIONS[dataTracker.get(CURRENT_ANIMATION).intValue()]);
        event.getController().setTransitionLength(0);
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
}
