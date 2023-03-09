package kelvin.slendermod.item;

import kelvin.slendermod.SlenderMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.resource.Resource;
import net.minecraft.util.ActionResult;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Optional;

public class ItemGrimoire extends WrittenBookItem {

    public ItemGrimoire() {
        super(new Settings());
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    public static boolean writeCustomNBT(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();

        if (nbtCompound != null && !nbtCompound.getBoolean(RESOLVED_KEY)) {
            NbtList nbtList = new NbtList();
            String[] pageNames = getBookFileLines("pages.txt");
            for (String pageName : pageNames) {
                String[] pageLines = getBookFileLines(pageName);
                StringBuilder pageText = new StringBuilder();

                for (String line : pageLines) {
                    pageText.append(line);
                }

                if (pageText.length() > MAX_PAGE_VIEW_LENGTH) {
                    return false;
                }

                nbtList.add(NbtString.of(pageText.toString()));
            }

            nbtCompound.putString(TITLE_KEY, "Grimoire");
            nbtCompound.putString(AUTHOR_KEY, "Unknown");
            nbtCompound.putInt(GENERATION_KEY, 0);
            stack.setSubNbt(PAGES_KEY, nbtList);
            return true;
        }
        else {
            return false;
        }
    }

    private static String[] getBookFileLines(String fileName) {
        try {
            Optional<Resource> file = MinecraftClient.getInstance().getResourceManager().getResource(SlenderMod.id("book/" + fileName.trim()));
            InputStream stream = file.orElseThrow(FileNotFoundException::new).getInputStream();
            String text = new String(stream.readAllBytes());
            return text.split("\r");
        }
        catch (Exception e) {
            SlenderMod.LOGGER.warn("Failed to read book text file");
        }
        return new String[] {};
    }
}
