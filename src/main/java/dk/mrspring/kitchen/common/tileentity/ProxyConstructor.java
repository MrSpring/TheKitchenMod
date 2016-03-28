package dk.mrspring.kitchen.common.tileentity;

import dk.mrspring.kitchen.Kitchen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created on 28-03-2016 for TheKitchenMod.
 */
public class ProxyConstructor extends TileEntityConstructor
{
    String proxyName;

    public ProxyConstructor(String name)
    {
        this.proxyName = name;
    }

    @Override
    public TileEntity construct(World world, int metadata)
    {
        try
        {
            Class<? extends TileEntity> clazz = Kitchen.proxy.tileEntities.get(proxyName);
            return clazz.newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
