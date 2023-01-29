package kelvin.slendermod.mixin;

import kelvin.slendermod.SlenderMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Shadow @Mutable
    public KeyBinding[] allKeys;

    @Shadow @Final public KeyBinding attackKey;

    @Shadow @Final public KeyBinding useKey;

    @Shadow @Final public KeyBinding forwardKey;

    @Shadow @Final public KeyBinding leftKey;

    @Shadow @Final public KeyBinding backKey;

    @Shadow @Final public KeyBinding rightKey;

    @Shadow @Final public KeyBinding jumpKey;

    @Shadow @Final public KeyBinding sneakKey;

    @Shadow @Final public KeyBinding sprintKey;

    @Shadow @Final public KeyBinding dropKey;

    @Shadow @Final public KeyBinding inventoryKey;

    @Shadow @Final public KeyBinding chatKey;

    @Shadow @Final public KeyBinding playerListKey;

    @Shadow @Final public KeyBinding pickItemKey;

    @Shadow @Final public KeyBinding commandKey;

    @Shadow @Final public KeyBinding socialInteractionsKey;

    @Shadow @Final public KeyBinding screenshotKey;

    @Shadow @Final public KeyBinding togglePerspectiveKey;

    @Shadow @Final public KeyBinding smoothCameraKey;

    @Shadow @Final public KeyBinding fullscreenKey;

    @Shadow @Final public KeyBinding spectatorOutlinesKey;

    @Shadow @Final public KeyBinding swapHandsKey;

    @Shadow @Final public KeyBinding saveToolbarActivatorKey;

    @Shadow @Final public KeyBinding loadToolbarActivatorKey;

    @Shadow @Final public KeyBinding advancementsKey;

    @Shadow @Final public KeyBinding[] hotbarKeys;

    @Inject(at=@At("RETURN"), method="<init>")
    public void Init(MinecraftClient client, File optionsFile, CallbackInfo info) {
        this.allKeys = (KeyBinding[]) ArrayUtils.addAll(new KeyBinding[]{this.attackKey, this.useKey, this.forwardKey, this.leftKey, this.backKey, this.rightKey, this.jumpKey, this.sneakKey, this.sprintKey, this.dropKey, this.inventoryKey, this.chatKey, this.playerListKey, this.pickItemKey, this.commandKey, this.socialInteractionsKey, this.screenshotKey, this.togglePerspectiveKey, this.smoothCameraKey, this.fullscreenKey, this.spectatorOutlinesKey, this.swapHandsKey, this.saveToolbarActivatorKey, this.loadToolbarActivatorKey, this.advancementsKey, SlenderMod.crawlKey}, this.hotbarKeys);
    }

}
