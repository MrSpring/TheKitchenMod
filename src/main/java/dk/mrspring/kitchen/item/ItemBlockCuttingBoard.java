package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.ModAchievements;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Konrad on 24-01-2015.
 */
public class ItemBlockCuttingBoard extends ItemBlock
{
    public ItemBlockCuttingBoard(Block p_i45328_1_)
    {
        super(p_i45328_1_);
    }

    @Override
    public void onCreated(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer player)
    {
        player.triggerAchievement(ModAchievements.cutting_board);
    }
}
