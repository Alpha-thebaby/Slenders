package kelvin.slendermod.mixin;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.network.server.ServerPacketHandler;
import kelvin.slendermod.util.IForceCrawlingPlayer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends LivingEntity {

    private boolean isCrawlKeyPressed;

    protected ClientPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isCrawling() {
        return super.isCrawling() || ((IForceCrawlingPlayer) this).isForcedCrawling();
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
        if (SlenderMod.CRAWL_KEY.isPressed()) {
            if (!isCrawlKeyPressed) {
                isCrawlKeyPressed = true;
                ((IForceCrawlingPlayer) this).toggleForcedCrawling();
                ClientPlayNetworking.send(ServerPacketHandler.TOGGLED_FORCED_CRAWLING_ID, PacketByteBufs.create());
            }
        }
        else {
            isCrawlKeyPressed = false;
        }
    }
}
