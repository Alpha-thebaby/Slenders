package kelvin.slendermod.common.entities;

import kelvin.slendermod.common.sounds.SoundRegistry;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntitySmallSlender extends PathAwareEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    private AnimationBuilder anim_idle = new AnimationBuilder().addAnimation("animation.idle", true);
    private AnimationBuilder anim_walk = new AnimationBuilder().addAnimation("animation.walk", true);
    private AnimationBuilder anim_roar = new AnimationBuilder().addAnimation("animation.roar", false);
    private AnimationBuilder anim_running = new AnimationBuilder().addAnimation("animation.running", true);
    private AnimationBuilder anim_look = new AnimationBuilder().addAnimation("animation.look", true);
    private AnimationBuilder anim_attack = new AnimationBuilder().addAnimation("animation.attack", false);

    private AnimationBuilder[] animations = {anim_idle, anim_walk, anim_roar, anim_running, anim_look, anim_attack };

    private int ANIM_ID_IDLE = 0, ANIM_ID_WALK = 1, ANIM_ID_ROAR = 2, ANIM_ID_RUNNING = 3, ANIM_ID_LOOK = 4, ANIM_ID_ATTACK = 5;

    private static final int IDLE = 0, WANDERING = 1, LOOKING = 2, CHASING = 3, CONFUSED = 4, ATTACKING = 5;

    private int current_state = 0;

    private int time_in_state = 0;
    private Entity targetEntity;

    private Vec3d lastSeenPos;

    private int anger_timer = 0;
    private int search_count = 0;

    private float anger = 0;

    private static final TrackedData<Integer> CURRENT_ANIMATION = DataTracker.registerData(EntitySmallSlender.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> GROWL_TRACKER = DataTracker.registerData(EntitySmallSlender.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ROAR_TRACKER = DataTracker.registerData(EntitySmallSlender.class, TrackedDataHandlerRegistry.BOOLEAN);

    private BlockPos doorPos;
    private int door_hits = 0;

    protected EntitySmallSlender(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0f;
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
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

                    float yaw = -(float)Math.toRadians(getYaw());

                    float look_x = (float)Math.sin(yaw);
                    float look_z = (float)Math.cos(yaw);


                    if (doorPos == null) {
                        BlockPos findDoorPos = new BlockPos(getX() + look_x, getY() + 0.5f, getZ() + look_z);
                        if (world.getBlockState(findDoorPos).getBlock() instanceof DoorBlock) {
                            doorPos = findDoorPos;
                            changeState(ATTACKING);
                        } else {
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

                                    this.navigation.startMovingTo(targetEntity, 0.75f);

                                    if (targetEntity.distanceTo(this) <= 4 && time_in_state > 20) {
                                        changeState(ATTACKING);
                                    }
                                } else {
                                    if (lastSeenPos != null) {
                                        this.navigation.startMovingTo(lastSeenPos.x, lastSeenPos.y, lastSeenPos.z, 0.65f);
                                        changeState(CONFUSED);
                                        targetEntity = null;
                                    } else {
                                        changeState(WANDERING);
                                    }
                                }

                            }
                        }
                    } else {
                        if (time_in_state > 40) {
                            changeState(ATTACKING);
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
                        if (doorPos != null) {

                            if (door_hits < 2) {
                                world.playSound(getX(), getY(), getZ(), SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.HOSTILE, 1.0f, 1.0f + random.nextFloat() * 0.25f, true);

                                door_hits++;
                            } else {
                                world.playSound(getX(), getY(), getZ(), SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.HOSTILE, 1.0f, 1.0f + random.nextFloat() * 0.25f, true);

                                world.breakBlock(doorPos, true, this);
                                doorPos = null;

                                door_hits = 0;
                            }
                            this.setCurrentAnimation(ANIM_ID_RUNNING);

                            changeState(CHASING);
                        } else {
                            if (targetEntity != null) {
                                if (targetEntity.getPos().distanceTo(getPos()) <= 2) {
                                    targetEntity.damage(DamageSource.mob(this), 5);
                                }
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
                world.playSound(getX(), getY(), getZ(), SoundRegistry.KID_LAUGH, SoundCategory.HOSTILE, 2.5f, (float)(random.nextDouble() - 0.5f) / 5.0f + 1.0f, true);
                dataTracker.set(GROWL_TRACKER, false);
            } else if (dataTracker.get(ROAR_TRACKER).booleanValue()) {
                world.playSound(getX(), getY(), getZ(), SoundRegistry.KID_ROAR, SoundCategory.HOSTILE, 2.0f, (float)(random.nextDouble() - 0.5f) / 5.0f + 1.0f, true);
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(animations[dataTracker.get(CURRENT_ANIMATION).intValue()]);
        event.getController().transitionLengthTicks = 0;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<EntitySmallSlender>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
