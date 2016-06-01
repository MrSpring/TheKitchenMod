package dk.mrspring.kitchen.comp.waila;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
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
public class WailaBoardDataProvider implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> strings, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        TileEntityBoard tileEntity = (TileEntityBoard) accessor.getTileEntity();
        if (config.getConfig("show_is_sandwich_ready",true)&&tileEntity.getLayers().size()>1)
        {
            String toAdd = StatCollector.translateToLocal("waila.is_sandwich_ready")+": ";
            if (ModConfig.getSandwichConfig().isBread(tileEntity.getTop()) && ModConfig.getSandwichConfig().isBread(tileEntity.getLayers().get(0)))
                toAdd += StatCollector.translateToLocal("waila.true");
            else toAdd += StatCollector.translateToLocal("waila.false");
            strings.add(toAdd);
        }
        return strings;
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
