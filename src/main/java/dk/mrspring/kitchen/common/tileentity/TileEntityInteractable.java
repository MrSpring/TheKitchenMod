package dk.mrspring.kitchen.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created on 23-03-2016 for TheKitchenMod.
 */
public abstract class TileEntityInteractable extends TileEntityBase
{
    public abstract boolean activated(EntityPlayer player, int side, float clickX, float clickY, float clickZ);

    public abstract void placed(EntityPlayer player);

    public abstract void broken(EntityPlayer player);
}
