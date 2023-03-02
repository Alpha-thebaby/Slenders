package kelvin.slendermod;

import kelvin.slendermod.network.server.ServerPacketHandler;
import kelvin.slendermod.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlenderMod implements ModInitializer {

    public static final String MODID = "slendermod";
    public static final Logger LOGGER = LoggerFactory.getLogger("slendermod");
    public static final ItemGroup SLENDERMOD_TAB = FabricItemGroup.builder(id("slendermod_tab"))
            .icon(() -> new ItemStack(BlockRegistry.SLENDER_HEAD))
            .entries((enabledFeatures, entries, operatorEnabled) -> {
                Registries.ITEM.stream().filter(item -> Registries.ITEM.getId(item).getNamespace().equals(MODID)).forEach(item -> {
                    entries.add(item, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
                });
            }).build();
    public static final GameEvent GUN_SHOT = Registry.register(
            Registries.GAME_EVENT,
            SlenderMod.id("gun_shot"),
            new GameEvent("gun_shot", 64)
    );

    @Override
    public void onInitialize() {
        EntityRegistry.register();
        BlockRegistry.register();
        BlockEntityRegistry.register();
        ItemRegistry.register();

        ServerPacketHandler.start();
        ConfigRegistry.INSTANCE.load();
    }

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }
}
