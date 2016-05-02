package dk.mrspring.kitchen.client.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.ClientProxy;
import dk.mrspring.kitchen.client.api.render.oven.OvenItemRenderer;
import dk.mrspring.kitchen.client.tileentity.render.anim.OpeningAnimation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import static dk.mrspring.kitchen.common.util.ItemUtils.*;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class TileEntityClientOven extends TileEntityClientBase
{
    private final int MIN_ANGLE = 0, MAX_ANGLE = 65, ANGLE_STEP = 10;

    boolean isOpen = false;
    public OpeningAnimation openingAnimation = new OpeningAnimation(MIN_ANGLE, MAX_ANGLE, ANGLE_STEP, isOpen);
    public OvenItemRenderer[] renderers = new OvenItemRenderer[4];

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        openingAnimation.update(isOpen);
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        this.renderers = new OvenItemRenderer[compound.getInteger("SlotCount")];
        this.isOpen = compound.getBoolean("IsOpen");
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
