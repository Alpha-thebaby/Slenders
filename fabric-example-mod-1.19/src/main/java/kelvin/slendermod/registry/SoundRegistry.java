package kelvin.slendermod.registry;

import kelvin.slendermod.SlenderMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {

    public static SoundEvent HORROR_ROAR = register("horror_roar");
    public static SoundEvent HORROR_GROWL = register("horror_growl");
    public static SoundEvent SMALL_SLENDER_LOOKING = register("small_slender_looking");
    public static SoundEvent SMALL_SLENDER_CHASING = register("small_slender_chasing");
    public static SoundEvent SHOCK = register("shock");
    public static SoundEvent BREATHING = register("breathing");
    public static SoundEvent HEARTBEAT = register("heartbeat");
    public static SoundEvent SOMETHING_APPROACHES = register("something_approaches");
    public static SoundEvent WIND = register("wind");
    public static SoundEvent BUZZING = register("buzzing");
    public static SoundEvent BOSS_DASH = register("boss_dash");
    public static SoundEvent BOSS_ATTACK = register("boss_attack");
    public static SoundEvent BOSS_IDLE = register("boss_idle");
    public static SoundEvent FLASHLIGHT_SWITCH = register("flashlight_switch");

    public static void register() {
    }

    public static SoundEvent register(String location, float distance) {
        Identifier id = SlenderMod.id(location);
        SoundEvent sound = SoundEvent.of(id, distance);
        Registry.register(Registries.SOUND_EVENT, id, sound);
        return sound;
    }

    public static SoundEvent register(String location) {
        Identifier id = SlenderMod.id(location);
        SoundEvent sound = SoundEvent.of(id);
        Registry.register(Registries.SOUND_EVENT, id, sound);
        return sound;
    }
}
