package kelvin.slendermod.item;

import kelvin.slendermod.SlenderMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.resource.Resource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class ItemGrimoire extends WrittenBookItem {

    public ItemGrimoire() {
        super(new Settings());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        setNbt(stack);
        super.appendTooltip(stack, world, tooltip, context);
    }

    public ItemStack setNbt(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();

        NbtList nbtList = new NbtList();

        String[] pagesLines = getBookFileText("pages.txt");
        for (String pagesLine : pagesLines) {
            String[] pageLines = getBookFileText(pagesLine);
            String pageText = "";

            for (String line : pageLines) {
                pageText += line;
            }

            nbtList.add(NbtString.of(pageText));
        }

        stack.setSubNbt("pages", nbtList);
        nbtCompound.putString("author", "Unknown");
        nbtCompound.putString("title", "Grimoire");
        nbtCompound.putInt("generation", 0);
        return stack;
    }

    private String[] getBookFileText(String fileName) {
        try {
            Optional<Resource> file = MinecraftClient.getInstance().getResourceManager().getResource(new Identifier("slendermod", "book/" + fileName.trim()));
            InputStream stream = file.get().getInputStream();
            String text = new String(stream.readAllBytes());
            return text.split("\r");
        }
        catch (Exception e) {
            SlenderMod.LOGGER.warn("Failed to read book text file");
        }
        return new String[] {};
    }
}
