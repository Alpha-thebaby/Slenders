package kelvin.slendermod.common.items;

import kelvin.slendermod.common.entities.EntitySlenderBoss;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBossDash extends Item {


    public ItemBossDash() {
        super(new Settings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            if (user.hasVehicle()) {
                var entity = user.getVehicle();
                if (entity instanceof EntitySlenderBoss boss) {
                    if (boss.GetState() == EntitySlenderBoss.STATE_DEFAULT) {
                        boss.SetState(EntitySlenderBoss.STATE_DASH);
                        user.getItemCooldownManager().set(this, 30);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }
}
