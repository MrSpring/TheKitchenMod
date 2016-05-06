package dk.mrspring.kitchen.client.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.tileentity.render.anim.OpeningAnimation;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class TileEntityClientOpenableOven extends TileEntityClientModernOven
{
    private final int MIN_ANGLE = 0, MAX_ANGLE = 65, ANGLE_STEP = 10;

    boolean isOpen = false;
    public OpeningAnimation openingAnimation = new OpeningAnimation(MIN_ANGLE, MAX_ANGLE, ANGLE_STEP, isOpen);

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        openingAnimation.update(isOpen);
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        super.readDataFromNBT(compound);
        this.isOpen = compound.getBoolean("IsOpen");
    }
}
