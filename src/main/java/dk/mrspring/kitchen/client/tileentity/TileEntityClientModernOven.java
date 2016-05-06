package dk.mrspring.kitchen.client.tileentity;

import dk.mrspring.kitchen.client.ClientProxy;
import dk.mrspring.kitchen.client.api.render.oven.IClientOven;
import dk.mrspring.kitchen.client.api.render.oven.OvenItemRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import static dk.mrspring.kitchen.common.util.ItemUtils.*;

/**
 * Created on 02-05-2016 for TheKitchenMod.
 */
public class TileEntityClientModernOven extends TileEntityClientBase implements IClientOven
{
    int slotCount = 0;
    public OvenItemRenderer[] renderers = new OvenItemRenderer[slotCount];

    @Override
    public void translateToSlot(int slot)
    {
        // TODO: Translate
    }

    @Override
    public int getSlotCount()
    {
        return slotCount;
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        this.slotCount = compound.getInteger("SlotCount");
        this.renderers = new OvenItemRenderer[slotCount];
        if (compound.hasKey("RenderInfo", LIST))
        {
            NBTTagList list = compound.getTagList("RenderInfo", COMPOUND);
            for (int i = 0; i < list.tagCount(); i++)
            {
                NBTTagCompound slotCompound = list.getCompoundTagAt(i);
                if (slotCompound.hasKey("Slot", INT))
                {
                    OvenItemRenderer renderer = ClientProxy.ovenRenderers.getFrom(slotCompound);
                    if (renderer != null)
                    {
                        int slot = slotCompound.getInteger("Slot");
                        renderers[slot] = renderer;
                    }
                }
            }
        }
    }
}
