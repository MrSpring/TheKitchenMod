package dk.mrspring.kitchen.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created on 28-03-2016 for TheKitchenMod.
 */
public abstract class TileEntityConstructor
{
    public TileEntity construct(World world, int metadata)
    {
        return null;
    }
}
