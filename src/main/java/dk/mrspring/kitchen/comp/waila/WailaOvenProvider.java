package dk.mrspring.kitchen.comp.waila;

import dk.mrspring.kitchen.tileentity.TileEntityOven;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Konrad on 05-10-2014 for TheKitchenMod.
 */
public class WailaOvenProvider implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        /*TileEntityOven tileEntityOven = (TileEntityOven) accessor.getTileEntity();

        if (config.getConfig("show_oven_contents"))
            if (tileEntityOven.getOvenItems()[0] != null)
            {
                currentTip.add(StatCollector.translateToLocal("waila.oven_items") + ":");
                for (ItemStack item : tileEntityOven.getOvenItems())
                    if (item != null)
                    {
                        String toAdd = "- " + item.getDisplayName();
                        if (item.stackSize > 1)
                        {
                            toAdd += " * " + item.stackSize;
                        }
                        currentTip.add(toAdd);
                    }
            }

        if (config.getConfig("show_oven_status"))
            currentTip.add(StatCollector.translateToLocal("waila.oven_has_coal") + ": " + StatCollector.translateToLocal("waila." + tileEntityOven.hasCoal()));
*/
        return currentTip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    public NBTTagCompound getNBTData(EntityPlayerMP entityPlayerMP, TileEntity tileEntity, NBTTagCompound nbtTagCompound, World world, int i, int i1, int i2)
    {
        return null;
    }
}
