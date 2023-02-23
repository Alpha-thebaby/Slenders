package kelvin.slendermod.mixin;

import com.ultreon.mods.pixelguns.item.gun.GunItem;
import kelvin.slendermod.entity.AbstractEntitySlender;
import kelvin.slendermod.network.server.ServerPacketHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GunItem.class)
public class GunItemMixin {

    @Inject(method = "handleHit", at = @At("HEAD"))
    void handleHit(HitResult result, ServerWorld world, ServerPlayerEntity damageSource, CallbackInfo ci) {
        if (result instanceof EntityHitResult hitResult) {
            if (hitResult.getEntity() instanceof AbstractEntitySlender slender) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeUuid(slender.getUuid());
                buf.writeBlockPos(slender.getBlockPos());
                ClientPlayNetworking.send(ServerPacketHandler.SLENDER_SHOT_ID, buf);
            }
        }
    }
}
