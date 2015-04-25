package dk.mrspring.kitchen.block.plant;

import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.item.Item;

import java.util.Random;

/**
 * Created by Konrad on 25-04-2015.
 */
public class BlockPeanutCrop extends BlockCropBase
{
    public BlockPeanutCrop(String namePrefix, String dropped)
    {
        super(namePrefix, dropped);
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int p_149650_3_)
    {
        System.out.println("Returning, metadata: " + metadata);
        if (metadata == 7) return KitchenItems.peanuts_in_shell;
        else return KitchenItems.peanut;
    }

    @Override
    protected Item getDroppedItem()
    {
        return KitchenItems.peanuts_in_shell;
    }
}