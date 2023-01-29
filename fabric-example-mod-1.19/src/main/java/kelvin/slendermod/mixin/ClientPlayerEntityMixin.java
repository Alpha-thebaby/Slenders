package kelvin.slendermod.mixin;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.network.server.ServerPacketHandler;
import kelvin.slendermod.util.IPlayerCrawling;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends LivingEntity {
    @Shadow @Final protected MinecraftClient client;

    @Shadow @Final public ClientPlayNetworkHandler networkHandler;
    boolean crawl_down = false;

    protected ClientPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isCrawling() {
        return super.isCrawling() || ((IPlayerCrawling)(PlayerEntity)(Object)this).IsForcedCrawl();
    }

    @Inject(at=@At("HEAD"), method="tick")
    public void tick(CallbackInfo info) {

        if (SlenderMod.crawlKey.isPressed()) {
            if (!crawl_down) {
                crawl_down = true;
                ((IPlayerCrawling)(PlayerEntity)(Object)this).Crawl();

                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBoolean(true);

                ClientPlayNetworking.send(ServerPacketHandler.CRAWL_PACKET_ID, buf);
            }
        } else {
            crawl_down = false;
        }
    }
}
