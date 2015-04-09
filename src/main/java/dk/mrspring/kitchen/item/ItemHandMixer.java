package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.item.ItemStack;

import java.util.Random;

/**
 * Created by Konrad on 23-03-2015.
 */
public class ItemHandMixer extends ItemBase
{
    public ItemHandMixer()
    {
        super("hand_mixer", true);

        this.setSelfAsContainerItem().setDoesItemLeaveCraftingGrid(false);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        if (new Random().nextInt(100) > 25)
            return super.getContainerItem(itemStack);
        else return new ItemStack(KitchenItems.dirty_hand_mixer, itemStack.stackSize);
    }
}
