package kelvin.slendermod.common.sounds;

import kelvin.slendermod.SlenderMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundRegistry {

    public static SoundEvent RegisterSoundEvent(String location, float distance) {
        var id = new Identifier(SlenderMod.MODID, location);
        var sound = new SoundEvent(id, distance);
        Registry.register(Registry.SOUND_EVENT, id, sound);
        return sound;
    }
    public static SoundEvent RegisterSoundEvent(String location) {
        var id = new Identifier(SlenderMod.MODID, location);

        var sound = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, sound);
        return sound;
    }


    public static SoundEvent HORROR_ROAR;
    public static SoundEvent HORROR_GROWL;
    public static SoundEvent KID_LAUGH;
    public static SoundEvent KID_ROAR;
    public static SoundEvent SHOCK;
    public static SoundEvent BREATHING;
    public static SoundEvent HEARTBEAT;
    public static SoundEvent SOMETHING_APPROACHES;
    public static SoundEvent WIND;

    public static void Register() {
        HORROR_ROAR = RegisterSoundEvent("horror_roar");
        HORROR_GROWL = RegisterSoundEvent("horror_growl");
        KID_LAUGH = RegisterSoundEvent("kid_laugh");
        KID_ROAR = RegisterSoundEvent("kid_roar");
        SHOCK = RegisterSoundEvent("shock");
        BREATHING = RegisterSoundEvent("breathing");
        HEARTBEAT = RegisterSoundEvent("heartbeat");
        SOMETHING_APPROACHES = RegisterSoundEvent("something_approaches");
        WIND = RegisterSoundEvent("wind");
    }

}
