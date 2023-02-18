package kelvin.slendermod.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemFlashlight extends Item {

    public ItemFlashlight(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ItemStack heldStack = user.getStackInHand(hand);
            if (heldStack.getItem() instanceof ItemFlashlight) {
                boolean powered = false;
                NbtCompound nbt = heldStack.getOrCreateSubNbt("Flashlight");
                if (nbt.contains("powered")) {
                    powered = nbt.getBoolean("powered");
                }
                nbt.putBoolean("powered", !powered);
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }
}
