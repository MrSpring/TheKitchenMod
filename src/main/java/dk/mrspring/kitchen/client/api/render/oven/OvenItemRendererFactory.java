package dk.mrspring.kitchen.client.api.render.oven;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.api.render.IRenderFactory;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 29-04-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public abstract class OvenItemRendererFactory implements IRenderFactory<OvenItemRenderer, NBTTagCompound>
{
    public abstract OvenItemRenderer make(NBTTagCompound compound);
}
