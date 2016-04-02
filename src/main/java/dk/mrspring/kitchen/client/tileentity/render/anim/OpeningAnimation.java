package dk.mrspring.kitchen.client.tileentity.render.anim;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.util.ClientUtils;
import net.minecraft.util.MathHelper;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class OpeningAnimation
{
    int min, max, step;
    int angle, direction;

    public OpeningAnimation(int min, int max, int step, boolean state)
    {
        this.min = min;
        this.max = max;
        this.step = step;

        this.angle = state ? max : min;
    }

    public void update(boolean dir)
    {
        angle += direction;
        direction = dir ? Math.min(max - angle, step) : Math.max(min - angle, -step);
    }

    public float getDegrees(float partial)
    {
        return MathHelper.clamp_float((float) angle + (float) direction * partial, (float) min, (float) max);
    }

    public float getRadians(float partial)
    {
        return ClientUtils.fromDegrees(getDegrees(partial));
    }
}
