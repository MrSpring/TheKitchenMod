package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelTimer;
import dk.mrspring.kitchen.tileentity.TileEntityTimeable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by MrSpring on 13-12-2014 for TheKitchenMod.
 */
public abstract class TileEntityTimeableRenderer extends TileEntitySpecialRenderer implements ITimeableRenderer
{
    ModelTimer model = new ModelTimer();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
    {
        if (tileEntity instanceof TileEntityTimeable)
            if (((TileEntityTimeable) tileEntity).getHasTimer())
                this.renderTimer(tileEntity, p_147500_2_, p_147500_4_, p_147500_6_);
    }

    private void renderTimer(TileEntity tileEntity, double x, double y, double z)
    {
        GL11.glPushMatrix();

        float cookTime = ((TileEntityTimeable) tileEntity).getTime();
        float doneTime = ((TileEntityTimeable) tileEntity).getDoneTime();

//        System.out.println("cookTime = " + cookTime);
//        System.out.println("doneTime = " + doneTime);

        float time;

        if (cookTime != 0 && doneTime != 0)
        {
            time = cookTime / doneTime;
        } else time = 0;

        if (time > 1)
            time = 1;
        else if (time < 0)
            time = 0;


        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.transformTimer(tileEntity);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.toTexture("textures/models/timer.png")));
        model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, time);

        GL11.glPopMatrix();
    }
}
