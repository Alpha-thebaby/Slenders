package kelvin.slendermod.common.sounds;

import kelvin.slendermod.SlenderMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {

    public static SoundEvent RegisterSoundEvent(String location, float distance) {
        var id = new Identifier(SlenderMod.MODID, location);
        var sound = SoundEvent.of(id, distance);
        Registry.register(Registries.SOUND_EVENT, id, sound);
        return sound;
    }

    public static SoundEvent RegisterSoundEvent(String location) {
        var id = new Identifier(SlenderMod.MODID, location);

        var sound = SoundEvent.of(id);
        Registry.register(Registries.SOUND_EVENT, id, sound);
        return sound;
    }

    public static SoundEvent HORROR_ROAR;
    public static SoundEvent HORROR_GROWL;
    public static SoundEvent SMALL_SLENDER_LOOKING;
    public static SoundEvent SMALL_SLENDER_CHASING;
    public static SoundEvent SHOCK;
    public static SoundEvent BREATHING;
    public static SoundEvent HEARTBEAT;
    public static SoundEvent SOMETHING_APPROACHES;
    public static SoundEvent WIND;
    public static SoundEvent BUZZING;
    public static SoundEvent BOSS_DASH;
    public static SoundEvent BOSS_ATTACK;
    public static SoundEvent BOSS_IDLE;

    public static void Register() {
        HORROR_ROAR = RegisterSoundEvent("horror_roar");
        HORROR_GROWL = RegisterSoundEvent("horror_growl");
        SMALL_SLENDER_LOOKING = RegisterSoundEvent("small_slender_looking");
        SMALL_SLENDER_CHASING = RegisterSoundEvent("small_slender_chasing");
        SHOCK = RegisterSoundEvent("shock");
        BREATHING = RegisterSoundEvent("breathing");
        HEARTBEAT = RegisterSoundEvent("heartbeat");
        SOMETHING_APPROACHES = RegisterSoundEvent("something_approaches");
        WIND = RegisterSoundEvent("wind");
        BUZZING = RegisterSoundEvent("buzzing");
        BOSS_DASH = RegisterSoundEvent("boss_dash");
        BOSS_ATTACK = RegisterSoundEvent("boss_attack");
        BOSS_IDLE = RegisterSoundEvent("boss_idle");
    }
}
