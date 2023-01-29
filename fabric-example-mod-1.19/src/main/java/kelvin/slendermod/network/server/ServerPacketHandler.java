package kelvin.slendermod.network.server;

import kelvin.slendermod.util.IPlayerCrawling;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ServerPacketHandler {

    public static Identifier CRAWL_PACKET_ID = new Identifier("slendermod:crawl_packet");

    public static void Start() {

        ServerPlayNetworking.registerGlobalReceiver(CRAWL_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            System.out.println("Receive Packet");
            server.execute(() -> {
                System.out.println("crawl!");
                ((IPlayerCrawling)player).Crawl();
            });
        });
    }
}
