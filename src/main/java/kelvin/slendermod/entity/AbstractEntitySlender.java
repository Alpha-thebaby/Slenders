package kelvin.slendermod.entity;

import kelvin.slendermod.SlenderMod;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class AbstractEntitySlender extends PathAwareEntity implements GeoEntity {

    private static final TrackedData<Integer> CURRENT_ANIMATION = DataTracker.registerData(AbstractEntitySlender.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> ROAR_TRACKER = DataTracker.registerData(AbstractEntitySlender.class, TrackedDataHandlerRegistry.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final List<RawAnimation> animations = List.of(getIdleAnim(), getWalkAnim(), getRoarAnim(), getRunAnim(), getLookAnim(), getAttackAnim());
    private final EntityGameEventHandler<SlenderRoarEventListener> slenderRoarEventHandler;
    private State currentState = State.IDLE;
    private int timeInState = 0;
    private int angerTimer;
    private int anger;
    private int searchCount;
    private Entity target;
    private Vec3d lastSeenPos;
    private BlockPos doorPos;
    private int doorHits;

    protected AbstractEntitySlender(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        PositionSource source = new EntityPositionSource(this, getStandingEyeHeight());
        slenderRoarEventHandler = new EntityGameEventHandler<>(new SlenderRoarEventListener(source, SlenderMod.GUN_SHOT.getRange()));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(CURRENT_ANIMATION, 0);
        dataTracker.startTracking(ROAR_TRACKER, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("currentAnimation", dataTracker.get(CURRENT_ANIMATION));
        nbt.putBoolean("roarTracker", dataTracker.get(ROAR_TRACKER));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(CURRENT_ANIMATION, nbt.getInt("currentAnimation"));
        dataTracker.set(ROAR_TRACKER, nbt.getBoolean("roarTracker"));
    }

    @Override
    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        if (world instanceof ServerWorld serverWorld) {
            callback.accept(slenderRoarEventHandler, serverWorld);
        }
    }

    private void travelToGunShot(Entity entity) {
        if (anger <= 0) {
            setAngryAt(entity);
        }
    }

    public void setAngryAt(Entity newTarget) {
        target = newTarget;
        changeState(State.CHASING);
        timeInState = 80;
        anger = 2;
        angerTimer = 200;
        lastSeenPos = newTarget.getPos();
    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient()) {
            double speed = Math.sqrt(Math.pow(getVelocity().x, 2) + Math.pow(getVelocity().z, 2));

            if (target == null) {
                tryToSpotTargets();
            }

            switch (getCurrentState()) {
                case IDLE -> {
                    setCurrentAnimation(getIdleAnim());
                    changeState(State.WANDERING);
                    if (timeInState > 20 * 3) {
                        if (random.nextBoolean()) {
                            changeState(State.WANDERING);
                        }
                    }
                }
                case WANDERING -> {
                    if (speed > 0.01f) {
                        setCurrentAnimation(getWalkAnim());
                    }
                    else {
                        setCurrentAnimation(getIdleAnim());
                    }
                    if (navigation.isIdle()) {
                        var pos = getPos();

                        if (searchCount > 0 && lastSeenPos != null) {
                            searchCount--;
                            pos = lastSeenPos;
                        }
                        else {
                            PlayerEntity target = world.getClosestPlayer(getX(), getY(), getZ(), 1000, true);
                            if (target != null) {
                                pos = target.getPos();
                            }
                        }
                        Vec3d current_point = pos.add(random.nextInt(40) - 20, random.nextInt(30) - 15, random.nextInt(40) - 20);

                        if (getPos().distanceTo(current_point) <= 10) {
                            current_point = current_point.subtract(getPos()).normalize().multiply(10).add(getPos());
                        }

                        navigation.startMovingTo(current_point.x, current_point.y, current_point.z, 0.25f);
                    }
                    if (timeInState > 20 * 12) {
                        if (random.nextBoolean()) {
                            changeState(State.WANDERING);
                        }
                        else {
                            navigation.startMovingTo(getX(), getY(), getZ(), 0);
                            changeState(State.LOOKING);
                        }
                    }
                }
                case LOOKING -> {
                    navigation.stop();
                    setCurrentAnimation(getLookAnim());
                    setVelocity(0, getVelocity().y, 0);
                    if (timeInState > 20 * 6) {
                        changeState(State.WANDERING);
                    }
                }
                case CHASING -> {
                    float yaw = -(float) Math.toRadians(getYaw());
                    float look_x = (float) Math.sin(yaw);
                    float look_z = (float) Math.cos(yaw);
                    if (doorPos == null) {
                        BlockPos findDoorPos = new BlockPos(getX() + look_x, getY() + 0.5f, getZ() + look_z);
                        if (canBreakDoors() && world.getBlockState(findDoorPos).getBlock() instanceof DoorBlock) {
                            doorPos = findDoorPos;
                            changeState(State.ATTACKING);
                        }
                        else {
                            if (timeInState < 80 && angerTimer <= 0) {
                                setCurrentAnimation(getRoarAnim());
                                if (target != null) {
                                    lookAtEntity(target, 180, 90);
                                }

                                if (timeInState == 10) {
                                    dataTracker.set(ROAR_TRACKER, true);
                                }
                            }
                            else {
                                angerTimer = 20 * 10;
                                if (!isInsideWaterOrBubbleColumn()) {
                                    setCurrentAnimation(getRunAnim());
                                }
                                else {
                                    setCurrentAnimation(getWalkAnim());
                                }

                                if (anger > 0) {
                                    if (!canSee(target)) {
                                        anger--;
                                    }
                                    lastSeenPos = new Vec3d(target.getX(), target.getY(), target.getZ());

                                    navigation.startMovingTo(target, getFastMovementSpeed());

                                    if (target.distanceTo(this) <= 4 && timeInState > 20) {
                                        changeState(State.ATTACKING);
                                    }
                                }
                                else {
                                    if (lastSeenPos != null) {
                                        navigation.startMovingTo(lastSeenPos.x, lastSeenPos.y, lastSeenPos.z, getSlowMovementSpeed());
                                        changeState(State.CONFUSED);
                                        target = null;
                                    }
                                    else {
                                        changeState(State.WANDERING);
                                    }
                                }
                            }
                        }
                    }
                    else {
                        if (timeInState > 40) {
                            changeState(State.ATTACKING);
                        }
                    }
                }
                case CONFUSED -> {
                    target = null;
                    if (navigation.isIdle() || timeInState > 20 * 3) {
                        changeState(State.WANDERING);
                        searchCount = 3;
                    }
                }
                case ATTACKING -> {
                    setCurrentAnimation(getAttackAnim());
                    if (target != null) {
                        lookAtEntity(target, 180, 90);
                    }
                    if (timeInState > 20) {
                        if (anger > 0) {
                            changeState(State.CHASING);
                        }
                        else {
                            changeState(State.IDLE);
                        }
                    }
                    else if (timeInState == 4) {
                        if (canBreakDoors() && doorPos != null) {

                            if (doorHits < 2) {
                                world.playSound(getX(), getY(), getZ(), SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.HOSTILE, 1.0f, 1.0f + random.nextFloat() * 0.25f, true);

                                doorHits++;
                            }
                            else {
                                world.playSound(getX(), getY(), getZ(), SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.HOSTILE, 1.0f, 1.0f + random.nextFloat() * 0.25f, true);

                                world.breakBlock(doorPos, true, this);
                                doorPos = null;

                                doorHits = 0;
                            }
                            setCurrentAnimation(getRunAnim());

                            changeState(State.CHASING);
                        }
                        else {
                            if (target != null) {
                                if (target.getPos().distanceTo(getPos().add(0, getDamageHeight(), 0)) <= getReach()) {
                                    target.damage(DamageSource.mob(this), getDamage());
                                }
                            }
                        }
                    }
                }
                default -> {
                    navigation.stop();
                    changeState(State.IDLE);
                }
            }
        }
        else {
            if (dataTracker.get(ROAR_TRACKER)) {
                world.playSound(getX(), getY(), getZ(), getAngrySound(), SoundCategory.HOSTILE, 2.0f, (float)(random.nextDouble() - 0.5f) / 5.0f + 1.0f, true);
                dataTracker.set(ROAR_TRACKER, false);
            }
        }

        timeInState++;
        angerTimer--;
    }

    protected void tryToSpotTargets() {
        Entity closest = world.getClosestPlayer(getX(), getY(), getZ(), 1000, true);

        float max_anger = 2;
        if (anger < 20 * max_anger) {
            if (closest != null) {

                if (canSee(closest)) {
                    int light = Math.max(world.getLightLevel(LightType.BLOCK, closest.getBlockPos().up()), world.getLightLevel(LightType.SKY, closest.getBlockPos().up()));

                    if (closest.distanceTo(this) <= 3) {
                        anger += max_anger;
                    }
                    else {
                        Vec3d look = new Vec3d(lookControl.getLookX(), lookControl.getLookY(), lookControl.getLookZ()).subtract(getPos()).normalize();
                        Vec3d direction = closest.getPos().subtract(getPos()).normalize();
                        double dot = look.dotProduct(direction);

                        if (closest.distanceTo(this) <= 10) {

                            if (light > 7) {
                                if (dot > 0) {
                                    anger += max_anger;
                                }
                                else if (closest.isSprinting()) {
                                    anger += max_anger * (0.8f);
                                }
                                else {
                                    anger++;
                                }
                            }
                            else {
                                if (dot > 0) {
                                    if (closest.isSprinting()) {
                                        anger += max_anger * (0.8f);
                                    }
                                    else if (closest.isSneaking()) {
                                        anger += 0.25f;
                                    }
                                    else {
                                        anger += 0.75f;
                                    }
                                }
                                else if (closest.isSprinting()) {
                                    anger++;
                                }
                                else if (!closest.isSneaking()) {
                                    anger += 0.5f;
                                }
                            }
                        }
                        else if (closest.distanceTo(this) <= 20) {
                            if (light > 7) {
                                if (dot > 0) {
                                    if (closest.isSprinting()) {
                                        anger++;
                                    }
                                    else if (!closest.isSneaking()) {
                                        anger += 0.25f;
                                    }
                                }
                            }
                            else {
                                if (closest.isSprinting()) {
                                    anger += 0.25f;
                                }
                            }
                        }
                    }
                }
            }
            else {
                if (anger > 0) {
                    anger -= 0.1f;
                }
                else {
                    anger = 0;
                }
            }
        }
        else {
            target = closest;
            if (closest != null && getCurrentState() != State.CHASING) {
                lastSeenPos = target.getPos();

                if (getCurrentState() == State.IDLE || getCurrentState() == State.WANDERING || getCurrentState() == State.LOOKING) {
                    changeState(State.CHASING);
                }
            }
        }
    }

    protected void changeState(State newState) {
        if (getCurrentState() != newState) {
            timeInState = 0;
            currentState = newState;
        }
    }

    protected void setCurrentAnimation(RawAnimation animation) {
        int index = animations.indexOf(animation);
        if (dataTracker.get(CURRENT_ANIMATION) != index) {
            dataTracker.set(CURRENT_ANIMATION, index);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "controller", 0, (animState) -> {
            var controller = animState.getController();
            controller.setAnimation(animations.get(dataTracker.get(CURRENT_ANIMATION)));
            controller.setTransitionLength(0);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (getCurrentState() == State.CHASING || getCurrentState() == State.ATTACKING) {
            return getAngrySound();
        }
        else if (getCurrentState() == State.LOOKING) {
            return getLookingSound();
        }
        return super.getAmbientSound();
    }

    public State getCurrentState() {
        return currentState;
    }

    protected abstract boolean canBreakDoors();

    protected abstract float getFastMovementSpeed();

    protected abstract float getSlowMovementSpeed();

    protected abstract int getDamage();

    protected abstract int getReach();

    protected abstract int getDamageHeight();

    protected abstract SoundEvent getAngrySound();

    protected abstract SoundEvent getLookingSound();

    protected abstract RawAnimation getIdleAnim();

    protected abstract RawAnimation getWalkAnim();

    protected abstract RawAnimation getRoarAnim();

    protected abstract RawAnimation getRunAnim();

    protected abstract RawAnimation getLookAnim();

    protected abstract RawAnimation getAttackAnim();

    public enum State {
        IDLE,
        WANDERING,
        LOOKING,
        CHASING,
        CONFUSED,
        ATTACKING;
    }

    private class SlenderRoarEventListener implements GameEventListener {

        private final PositionSource source;
        private final int range;

        public SlenderRoarEventListener(PositionSource source, int range) {
            this.source = source;
            this.range = range;
        }

        @Override
        public PositionSource getPositionSource() {
            return source;
        }

        @Override
        public int getRange() {
            return range;
        }

        @Override
        public boolean listen(ServerWorld world, GameEvent event, GameEvent.Emitter emitter, Vec3d emitterPos) {
            if (event == SlenderMod.GUN_SHOT && emitter.sourceEntity() != null) {
                AbstractEntitySlender.this.travelToGunShot(emitter.sourceEntity());
                return true;
            }
            return false;
        }
    }
}
