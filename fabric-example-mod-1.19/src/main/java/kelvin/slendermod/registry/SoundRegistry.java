package kelvin.slendermod.registry;

import kelvin.slendermod.SlenderMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {

    public static SoundEvent HORROR_ROAR = registerSoundEvent("horror_roar");
    public static SoundEvent HORROR_GROWL = registerSoundEvent("horror_growl");
    public static SoundEvent SMALL_SLENDER_LOOKING = registerSoundEvent("small_slender_looking");
    public static SoundEvent SMALL_SLENDER_CHASING = registerSoundEvent("small_slender_chasing");
    public static SoundEvent SHOCK = registerSoundEvent("shock");
    public static SoundEvent BREATHING = registerSoundEvent("breathing");
    public static SoundEvent HEARTBEAT = registerSoundEvent("heartbeat");
    public static SoundEvent SOMETHING_APPROACHES = registerSoundEvent("something_approaches");
    public static SoundEvent WIND = registerSoundEvent("wind");
    public static SoundEvent BUZZING = registerSoundEvent("buzzing");
    public static SoundEvent BOSS_DASH = registerSoundEvent("boss_dash");
    public static SoundEvent BOSS_ATTACK = registerSoundEvent("boss_attack");
    public static SoundEvent BOSS_IDLE = registerSoundEvent("boss_idle");
    public static SoundEvent FLASHLIGHT_SWITCH = registerSoundEvent("flashlight_switch");

    public static void register() {
    }

    public static SoundEvent registerSoundEvent(String location, float distance) {
        Identifier id = SlenderMod.id(location);
        SoundEvent sound = SoundEvent.of(id, distance);
        Registry.register(Registries.SOUND_EVENT, id, sound);
        return sound;
    }

    public static SoundEvent registerSoundEvent(String location) {
        Identifier id = SlenderMod.id(location);
        SoundEvent sound = SoundEvent.of(id);
        Registry.register(Registries.SOUND_EVENT, id, sound);
        return sound;
    }
}
