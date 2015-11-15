package dk.mrspring.kitchen.api_impl.common.oven;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.item.block.ItemBlockMuffinTray;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created on 14-11-2015 for TheKitchenMod.
 */
public class MuffinTrayOvenItem extends RecipeOvenItem
{
    @Override
    public int getSize(IOven oven)
    {
        return 4;
    }

    @Override
    public boolean consecutive(IOven oven)
    {
        return true;
    }

    @Override
    public boolean isForItem(IOven oven, ItemStack item, EntityPlayer player, boolean[] freeSlots)
    {
        return ItemUtils.item(item, KitchenBlocks.muffin_tray);
    }

    @Override
    public ItemStack getRecipeInput(IOven oven, ItemStack clicked)
    {
        return super.getRecipeInput(oven, clicked);
    }

    @Override
    public ItemStack[] getRecipeOutput(IOven oven, ItemStack input)
    {
        ItemBlockMuffinTray.Tray tray = new ItemBlockMuffinTray.Tray(input);
        tray.cook();
        ItemStack[] results = new ItemStack[2];
        ItemStack cooked = input.copy();
        tray.writeToStack(cooked);
        results[0] = cooked;
        ItemStack burnt = input.copy();
        tray.writeToStack(burnt);
        results[1] = burnt;
        return results;
    }
}
