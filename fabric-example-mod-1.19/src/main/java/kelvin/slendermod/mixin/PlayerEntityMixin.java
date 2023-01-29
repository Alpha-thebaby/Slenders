package kelvin.slendermod.mixin;

import kelvin.slendermod.util.IPlayerCrawling;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.EntityList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerCrawling {

    @Shadow protected abstract void updatePose();

    boolean crawling = false;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void Crawl() {
        crawling = !crawling;
    }

    @Override
    public boolean IsForcedCrawl() {
        return crawling;
    }

    @Inject(at=@At("HEAD"), method="updatePose", cancellable = true)
    protected void updatePose(CallbackInfo info) {
        if (crawling) {
            this.setPose(EntityPose.SWIMMING);
            info.cancel();
        }
    }
}
