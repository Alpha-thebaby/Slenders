package kelvin.slendermod.common.entities;

import kelvin.slendermod.common.sounds.SoundRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
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

public class EntitySlenderBoss extends PathAwareEntity implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    private AnimationBuilder anim_idle = new AnimationBuilder().addAnimation("animation.slender_boss.idle", true);
    private AnimationBuilder anim_walk = new AnimationBuilder().addAnimation("animation.slender_boss.crawl", true);
    private AnimationBuilder anim_attack = new AnimationBuilder().addAnimation("animation.slender_boss.attack", false);
    private AnimationBuilder anim_dash = new AnimationBuilder().addAnimation("animation.slender_boss.dash", false);

    private AnimationBuilder[] animations = {anim_idle, anim_walk, anim_attack, anim_dash };

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
                if (timeInState == 10) {
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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(animations[dataTracker.get(CURRENT_ANIMATION).intValue()]);
        event.getController().transitionLengthTicks = 5;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<EntitySlenderBoss>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
