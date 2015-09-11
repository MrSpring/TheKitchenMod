package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.entity.CookingBookUnlocksProperties;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created on 11-09-2015 for TheKitchenMod.
 */
public class ItemBlockBoard extends ItemBlock
{
    public ItemBlockBoard(Block p_i45328_1_)
    {
        super(p_i45328_1_);
    }

    @Override
    public void onCreated(ItemStack created, World world, EntityPlayer player)
    {
        super.onCreated(created, world, player);
        CookingBookUnlocksProperties.unlockChapter("cuttingboard", player);
    }
}
