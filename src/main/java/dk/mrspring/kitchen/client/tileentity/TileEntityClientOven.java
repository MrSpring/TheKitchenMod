package dk.mrspring.kitchen.client.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.tileentity.render.anim.OpeningAnimation;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created on 27-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class TileEntityClientOven extends TileEntityClient
{
    private final int MIN_ANGLE = 0, MAX_ANGLE = 65, ANGLE_STEP = 10;

    boolean isOpen = false;
    //    public int hatchAngle = 0, hatchDirection = 0;
    public OpeningAnimation openingAnimation = new OpeningAnimation(MIN_ANGLE, MAX_ANGLE, ANGLE_STEP, isOpen);

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        openingAnimation.update(isOpen);
//        hatchAngle += hatchDirection;
//        hatchDirection = handleOpeningAnimation(hatchAngle, MIN_ANGLE, MAX_ANGLE, ANGLE_STEP, isOpen);
        /*isOpen ?
                (Math.min(MAX_ANGLE - hatchAngle, ANGLE_STEP)*//*hatchAngle < MAX_ANGLE ? ANGLE_STEP : 0*//*) :
                (Math.max(MIN_ANGLE - hatchAngle, -ANGLE_STEP)*//*hatchAngle > MIN_ANGLE ? -ANGLE_STEP : 0*//*);
        //hatchAngle = MathHelper.clamp_int(hatchAngle, MIN_ANGLE, MAX_ANGLE);
        System.out.println(hatchDirection + ", " + hatchAngle);*/
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        this.isOpen = compound.getBoolean("IsOpen");
        System.out.println(isOpen);
//        System.out.println(compound.getString("id"));
    }
}
