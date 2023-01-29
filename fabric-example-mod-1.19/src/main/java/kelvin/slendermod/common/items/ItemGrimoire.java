package kelvin.slendermod.common.items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ItemGrimoire extends WrittenBookItem {
    public ItemGrimoire() {
        super(new Settings().group(ItemGroup.MISC));
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();

        ArrayList<String> pages = new ArrayList<>();

        var resoure = MinecraftClient.getInstance().getResourceManager().getResource(new Identifier("slendermod", "book/pages.txt"));

        try {
            BufferedReader reader = resoure.get().getReader();

            var is = resoure.get().getInputStream();

            String str = new String(is.readAllBytes());

            String[] files = str.split("\n");

            for (String line : files) {
                var file = MinecraftClient.getInstance().getResourceManager().getResource(new Identifier("slendermod", "book/"+line.trim()));

                try {
                    var stream = file.get().getInputStream();

                    String all_text = new String(stream.readAllBytes());

                    System.out.println(all_text);

                    String[] lines = all_text.split("\n");

                    String page = "";

                    int max_lines = 14;
                    int chars_per_line = 19;
                    for (int i = 0; i < 10; i++)
                    {
                        page += "A";
                    }

                    pages.add(page.trim());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NbtList nbtList = new NbtList();
        for (int i = 0; i < pages.size(); i++) {
            nbtList.add(NbtString.of(pages.get(i)));
        }

        stack.setSubNbt("pages", nbtList);
        nbtCompound.putString("author", "Unknown");
        nbtCompound.putString("title", "Grimoire");
        nbtCompound.putInt("generation", 0);
        stack.setSubNbt("pages", nbtList);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
