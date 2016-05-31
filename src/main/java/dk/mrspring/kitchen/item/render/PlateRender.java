package dk.mrspring.kitchen.item.render;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

import static dk.mrspring.kitchen.ClientUtils.*;

/**
 * Created on 31-05-2016 for TheKitchenMod.
 */
public class PlateRender
{
    public static final String IS_RENDERING_ON_PLATE = "IsRenderingOnPlate";
    public static final String PLATE_ITEM_HEIGHT = "PlateItemHeight";

    public static double renderPlateContents(Plate plate)
    {
        ItemStack[] stacks = plate.items;
        double yOffset = 0D;
        for (ItemStack stack : stacks)
        {
            push();
            translate(0D, yOffset, 0D);
            yOffset += renderItem(stack);
            pop();
        }
        return yOffset;
    }

    private static double renderItem(ItemStack stack)
    {
        if (stack.hasTagCompound())
            stack.getTagCompound().setBoolean(IS_RENDERING_ON_PLATE, true);
        rotate(90F, 1F, 0F, 0F);
        push();
        renderItemStack(stack);
        pop();
        if (stack.hasTagCompound())
        {
            stack.getTagCompound().removeTag(IS_RENDERING_ON_PLATE);
            if (stack.getTagCompound().hasKey(PLATE_ITEM_HEIGHT, 6))
                return stack.getTagCompound().getDouble(PLATE_ITEM_HEIGHT);
        }
        return ITEM_PIXEL;
    }

    public static class Plate
    {
        ItemStack[] items;

        public Plate(ItemStack stack)
        {
            if (stack.hasTagCompound() && stack.getTagCompound().hasKey("PlateData", 10))
            {
                NBTTagCompound info = stack.getTagCompound().getCompoundTag("PlateData");
                NBTTagList list = info.getTagList("Items", 10);
                if (list.tagCount() > 0)
                {
                    items = new ItemStack[list.tagCount()];
                    for (int i = 0; i < list.tagCount(); i++)
                        items[i] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
                } else items = new ItemStack[0];
            } else items = new ItemStack[0];
        }

        public Plate(List<ItemStack> items)
        {
            this.items = items.toArray(new ItemStack[items.size()]);
        }
    }
}
