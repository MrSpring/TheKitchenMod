package dk.mrspring.kitchen.common.api.oven.item;

import dk.mrspring.kitchen.common.api.oven.IOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
public class OvenItem
{
    public final String RENDER_AS = "RenderAs";

    public boolean canAddToOven(EntityPlayer player, IOven oven, int[] emptySlots)
    {
        return false;
    }

    public boolean onItemRightClick(EntityPlayer player, IOven oven, OvenItemStack stack, int slot)
    {
        return false;
    }

    /**
     * @param compound Write anything to this {@link NBTTagCompound} that is needed to render this Oven Item. Handling
     *                 rendering this way allows server-side-only OvenItems to still be rendered client-side.
     *                 TODO: Default stack rendering example.
     */
    public void writeRenderingInfoToClient(IOven oven, OvenItemStack stack, int slot, NBTTagCompound compound)
    {
        compound.setString("RenderAs", "Empty");
    }
}
