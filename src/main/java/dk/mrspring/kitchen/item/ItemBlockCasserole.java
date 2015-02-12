package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.tileentity.casserole.Casserole;
import dk.mrspring.kitchen.tileentity.casserole.Layer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Created by Konrad on 11-02-2015.
 */
public class ItemBlockCasserole extends ItemBlock
{
    public ItemBlockCasserole(Block p_i45328_1_)
    {
        super(p_i45328_1_);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
    {
        super.addInformation(stack, player, list, p_77624_4_);

        Casserole fromStack = Casserole.loadFromItemStack(stack);
        if (fromStack != null && fromStack.getLayers().size() > 0)
        {
            list.add(StatCollector.translateToLocal("tile.casserole.contents") + ":");
            for (Layer layer : fromStack.getLayers())
                list.add(layer.getTranslatedName());
        }
    }
}
