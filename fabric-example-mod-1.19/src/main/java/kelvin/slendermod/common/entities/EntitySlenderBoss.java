package kelvin.slendermod.common.entities;

import kelvin.slendermod.common.sounds.SoundRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EntitySlenderBoss extends PathAwareEntity implements GeoEntity {
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation ANIM_IDLE = RawAnimation.begin().then("animation.slender_boss.idle", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_WALK = RawAnimation.begin().then("animation.slender_boss.crawl", Animation.LoopType.LOOP);
    private static final RawAnimation ANIM_ATTACK = RawAnimation.begin().then("animation.slender_boss.attack", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation ANIM_DASH = RawAnimation.begin().then("animation.slender_boss.dash", Animation.LoopType.PLAY_ONCE);

    private static final RawAnimation[] ANIMATIONS = {ANIM_IDLE, ANIM_WALK, ANIM_ATTACK, ANIM_DASH};

    private int ANIM_ID_IDLE = 0, ANIM_ID_WALK = 1, ANIM_ID_ATTACK = 2, ANIM_ID_DASH = 3;

    public static final int STATE_DEFAULT = 0, STATE_ATTACK = 1, STATE_DASH = 2;

    private int STATE = 0;

    private int timeInState = 0;
    private int move_timer = 0;
    private int transitionTicks = 10;

    private Vec3d startPos = new Vec3d(0, 0, 0);

    private static final TrackedData<Integer> CURRENT_ANIMATION = DataTracker.registerData(EntitySlenderBoss.class, TrackedDataHandlerRegistry.INTEGER);

    protected EntitySlenderBoss(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.stepHeight = 1.0f;
        startPos = new Vec3d(getPos().x, getPos().y, getPos().z);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 300.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0);
    }
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CURRENT_ANIMATION, 0);
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

        if (!world.isClient()) {
            if (world.getTime() % (100) == 0) {
                if (GetState() != STATE_ATTACK || GetState() != STATE_DASH) {
                    playSound(SoundRegistry.BOSS_IDLE, 1, 1);
                }
            }

            int animation = ANIM_ID_IDLE;
            if (STATE == STATE_DEFAULT) {
                var first = getFirstPassenger();
                if (first instanceof ServerPlayerEntity) {
                    ServerPlayerEntity player = (ServerPlayerEntity) first;

                    float yaw = -(float)Math.toRadians(player.getYaw());

                    float look_x = (float)Math.sin(yaw);
                    float look_z = (float)Math.cos(yaw);

                    float motion_x = 0;
                    float motion_z = 0;

                    motion_x = look_x * player.forwardSpeed * 0.2f;
                    motion_z = look_z * player.forwardSpeed * 0.2f;

                    if (player.forwardSpeed != 0) {
                        animation = ANIM_ID_WALK;
                        this.move(MovementType.SELF, new Vec3d(motion_x, 0, motion_z));
                    }
                    this.setYaw(player.getYaw());
                    this.setBodyYaw(player.getBodyYaw());
                    this.setHeadYaw(player.getHeadYaw());
                } else {
                    if (move_timer == 0) {
                        if (random.nextInt(100) == 0) {
                            int radius = 15;
                            this.navigation.startMovingTo((int)(startPos.x + random.nextInt(radius * 2) - radius), (int)startPos.y, (int)(startPos.z + random.nextInt(radius * 2) - radius), 1.0f);
                            move_timer = 20 * 5;
                        }
                    } else {
                        animation = ANIM_ID_WALK;
                        move_timer--;
                    }
                }
            } else if (STATE == STATE_ATTACK) {
                animation = ANIM_ID_ATTACK;
                if (timeInState == 0) {
                    playSound(SoundRegistry.BOSS_ATTACK, 1, 1);
                }
                else if (timeInState == 10) {
                    float yaw = -(float)Math.toRadians(getYaw());

                    float look_x = (float)Math.sin(yaw);
                    float look_z = (float)Math.cos(yaw);

                    float motion_x = 0;
                    float motion_z = 0;

                    motion_x = look_x;
                    motion_z = look_z;

                    setVelocity(new Vec3d(motion_x, 0.1, motion_z));

                    Vec3d forward = GetForward(3);

                    double range = 2;

                    var entities = world.getOtherEntities(this, new Box(forward.subtract(range, range, range), forward.add(range, range, range)));
                    for (int i = 0; i < entities.size(); i++) {
                        entities.get(i).damage(DamageSource.mob(this), 10);
                    }

                }
                else if (timeInState > 30) {
                    SetState(STATE_DEFAULT);
                }
            } else if (STATE == STATE_DASH) {
                transitionTicks = 0;
                animation = ANIM_ID_DASH;
                if (timeInState == 0) {
                    float yaw = -(float)Math.toRadians(getYaw());

                    float look_x = (float)Math.sin(yaw);
                    float look_z = (float)Math.cos(yaw);

                    float motion_x = 0;
                    float motion_z = 0;

                    motion_x = look_x * 3;
                    motion_z = look_z * 3;

                    setVelocity(new Vec3d(motion_x, 0.25, motion_z));
                    playSound(SoundRegistry.BOSS_DASH, 1, 1);
                }
                else if (timeInState > 5) {
                    transitionTicks = 10;
                    SetState(STATE_DEFAULT);
                } else {

                    Vec3d forward = GetForward(3);

                    double range = 2;

                    var entities = world.getOtherEntities(this, new Box(forward.subtract(range, range, range), forward.add(range, range, range)));
                    for (int i = 0; i < entities.size(); i++) {
                        entities.get(i).damage(DamageSource.mob(this), 10);
                    }
                }
            }
            timeInState++;
            setCurrentAnimation(animation);
        }
    }

    public Vec3d GetForward(double distance) {
        return getPos().add(GetForwardVector().multiply(distance));
    }

    public Vec3d GetForwardVector() {
        double yaw = -Math.toRadians(getYaw());
        return new Vec3d(Math.sin(yaw), 0, Math.cos(yaw));
    }

    public int GetState() {
        return STATE;
    }

    public void SetState(int state) {
        if (STATE != state) {
            STATE = state;
            timeInState = 0;
        }
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand == Hand.OFF_HAND) {
            if (!hasPassengers()) {
                if (player.isCreative()) player.startRiding(this);
            }
        }
        return super.interactMob(player, hand);
    }

    private void setCurrentAnimation(int animation) {
        if (dataTracker.get(CURRENT_ANIMATION).intValue() != animation) {
            dataTracker.set(CURRENT_ANIMATION, animation);
        }
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        event.getController().setAnimation(ANIMATIONS[dataTracker.get(CURRENT_ANIMATION).intValue()]);
        event.getController().setTransitionLength(5);
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
