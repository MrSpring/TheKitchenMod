package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Konrad on 12-04-2015.
 */
public class ItemDirtyHandMixer extends ItemBase
{
    public ItemDirtyHandMixer()
    {
        super("dirty_hand_mixer", true);
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer player)
    {
        System.out.println("On eaten");
        MovingObjectPosition movingObjectPosition = getMovingObjectPositionFromPlayer(world, player, true);
        if (lookingAtFilledCauldronOrWater(movingObjectPosition, item, world, player))
        {
            int x = movingObjectPosition.blockX, y = movingObjectPosition.blockY, z = movingObjectPosition.blockZ;
            Block block = world.getBlock(x, y, z);
            if (block == Blocks.cauldron)
                ((BlockCauldron) block).func_150024_a(world, x, y, z, world.getBlockMetadata(x, y, z) - 1);
            item.stackSize--;
            return new ItemStack(KitchenItems.hand_mixer);
        }
        return super.onEaten(item, world, player);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        System.out.println("On item right click");
        MovingObjectPosition movingObjectPosition = getMovingObjectPositionFromPlayer(world, player, true);
        if (lookingAtFilledCauldronOrWater(movingObjectPosition, item, world, player) && player.getItemInUse() != item)
            player.setItemInUse(item, getMaxItemUseDuration(item));
        return item;
    }

    private boolean lookingAtFilledCauldronOrWater(MovingObjectPosition movingobjectposition, ItemStack item, World world, EntityPlayer player)
    {
        if (movingobjectposition == null)
            return false;
        else
        {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k))
                    return false;
                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item))
                    return false;
                if (world.getBlock(i, j, k).getMaterial() == Material.water || (world.getBlock(i, j, k) == Blocks.cauldron && world.getBlockMetadata(i, j, k) > 0))
                    return true;
            }
            return false;
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.drink;
    }

    @Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        System.out.println("Item use");
        return false;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        System.out.println("Item use first");
        Block block = world.getBlock(x, y, z);
        if (block != null && world.getBlockMetadata(x, y, z) > 0)
            if (block instanceof BlockCauldron)
                player.setItemInUse(stack, getMaxItemUseDuration(stack));
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List information, boolean p_77624_4_)
    {
        information.add(StatCollector.translateToLocal("item.dirty_hand_mixer.desc"));
        super.addInformation(stack, player, information, p_77624_4_);
    }
}
