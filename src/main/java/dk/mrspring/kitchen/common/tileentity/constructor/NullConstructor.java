package dk.mrspring.kitchen.common.tileentity.constructor;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created on 31-03-2016 for TheKitchenMod.
 */
public class NullConstructor extends TileEntityConstructor
{
    @Override
    public TileEntity construct(World world, int metadata)
    {
        return null;
    }

    @Override
    public void register(String name)
    {
    }
}
