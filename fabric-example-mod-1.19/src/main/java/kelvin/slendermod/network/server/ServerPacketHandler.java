package kelvin.slendermod.network.server;

import kelvin.slendermod.SlenderMod;
import kelvin.slendermod.util.IPlayerCrawling;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class ServerPacketHandler {

    public static final Identifier CRAWL_PACKET_ID = new Identifier("slendermod:crawl_packet");
    public static final Identifier SLENDER_SHOT_ID = new Identifier("slendermod:slender_shot");

    public static void Start() {
        ServerPlayNetworking.registerGlobalReceiver(CRAWL_PACKET_ID, (server, player, handler, buf, responseSender) ->
                server.execute(() -> ((IPlayerCrawling)player).Crawl()));

        ServerPlayNetworking.registerGlobalReceiver(SLENDER_SHOT_ID, (server, player, handler, buf, responseSender) -> {
            ServerWorld world = player.getWorld();
            UUID uuid = buf.readUuid();
            Entity entity = world.getEntity(uuid);
            BlockPos pos = buf.readBlockPos();
            server.execute(() -> world.emitGameEvent(entity, SlenderMod.SLENDER_ROAR, pos));
        });
    }
}
