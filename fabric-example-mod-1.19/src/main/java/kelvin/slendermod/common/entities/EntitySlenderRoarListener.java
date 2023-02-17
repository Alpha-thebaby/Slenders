package kelvin.slendermod.common.entities;

import kelvin.slendermod.SlenderMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.function.BiConsumer;

public abstract class EntitySlenderRoarListener extends PathAwareEntity implements GeoEntity {

    private final EntityGameEventHandler<SlenderRoarEventListener> slenderRoarEventHandler;

    protected EntitySlenderRoarListener(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        PositionSource source = new EntityPositionSource(this, getStandingEyeHeight());
        slenderRoarEventHandler = new EntityGameEventHandler<>(new SlenderRoarEventListener(source, SlenderMod.SLENDER_ROAR.getRange()));
    }

    @Override
    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        if (world instanceof ServerWorld serverWorld) {
            callback.accept(slenderRoarEventHandler, serverWorld);
        }
    }

    private void travelToRoar(BlockPos pos) {
        if (getAnger() <= 0) {
            setChasing(pos);
        }
    }

    protected abstract float getAnger();

    protected abstract void setChasing(BlockPos pos);

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
            if (event == SlenderMod.SLENDER_ROAR && emitter.sourceEntity() != null) {
                if (!emitter.sourceEntity().getUuid().equals(EntitySlenderRoarListener.this.getUuid())) {
                    EntitySlenderRoarListener.this.travelToRoar(new BlockPos(emitterPos));
                    return true;
                }
            }
            return false;
        }
    }
}
