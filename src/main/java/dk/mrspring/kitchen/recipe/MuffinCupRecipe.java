package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.item.ItemMuffinCup;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Konrad on 09-08-2015.
 */
public class MuffinCupRecipe implements net.minecraft.item.crafting.IRecipe
{
    public MuffinCupRecipe()
    {
        // int p_i1917_1_, int p_i1917_2_, ItemStack[] p_i1917_3_, ItemStack p_i1917_4_
//        super(2, 1, new ItemStack[]{new ItemStack(Items.paper), new ItemStack(Items.paper),
//                        new ItemStack(Items.dye, 1, ItemMuffinCup.WHITE)},
//                new ItemStack(KitchenItems.empty_muffin_cup, 6, ItemMuffinCup.WHITE));
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world)
    {
        boolean dye = false, match = false;
        int skipX = -1, skipY = -1;
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
            {
                if (x == skipX && y == skipY) continue;
                ItemStack inSlot = inventory.getStackInRowAndColumn(x, y);
                if (ItemUtils.item(inSlot, Items.paper))
                {
                    if (match) return false;
                    ItemStack nextSlot = inventory.getStackInRowAndColumn(x + 1, y);
                    if (ItemUtils.item(nextSlot, Items.paper))
                    {
                        match = true;
                        skipX = x + 1;
                        skipY = y;
                    } else
                        return false;
                } else if (ItemUtils.itemDict(inSlot, "dye"))
                {
                    if (dye) return false;
                    else dye = true;
                }
            }
        return match;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory)
    {
        ItemStack normalResult = getRecipeOutput();
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
            {
                ItemStack inSlot = inventory.getStackInRowAndColumn(x, y);
                if (ItemUtils.itemDict(inSlot, "dye"))
                    normalResult.setItemDamage(inSlot.getItemDamage());
            }
        return normalResult;
    }

    @Override
    public int getRecipeSize()
    {
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return new ItemStack(KitchenItems.empty_muffin_cup, 6, ItemMuffinCup.WHITE);
    }
}
