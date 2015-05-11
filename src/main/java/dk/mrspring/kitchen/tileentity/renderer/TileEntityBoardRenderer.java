package dk.mrspring.kitchen.tileentity.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.item.render.SandwichRender;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;
import java.util.List;

@SideOnly(Side.CLIENT)
public class TileEntityBoardRenderer extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
    {
        GL11.glPushMatrix();

        TileEntityBoard tileEntity = (TileEntityBoard) var1;
        GL11.glTranslated(x, y + 1.56, z);
        int metadata = tileEntity.getBlockMetadata();
        double sandwichHeight = 0.0625;
        if (metadata == 0)
            GL11.glTranslated(.5, sandwichHeight, .5);
        else
        {
            GL11.glRotatef(90, 0, 1, 0);
            GL11.glTranslated(-.5, sandwichHeight, .5);
        }

        List<ItemStack> layers = tileEntity.getLayers();

        /*double pixel = 0.0418;

        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(8).put(new double[]{1, 0, 0, 0.001 + (pixel * 3)});
        buffer.flip();
        GL11.glClipPlane(GL11.GL_CLIP_PLANE5, buffer);
        GL11.glEnable(GL11.GL_CLIP_PLANE5);*/

        NBTTagCompound specialInfo = tileEntity.getSpecialInfo();
        if (specialInfo.hasKey("SliceCount") && specialInfo.getInteger("SliceCount") > 0)
        {
            int sliceCount = specialInfo.getInteger("SliceCount");
            float distance = 0.0425F;
            switch (sliceCount)
            {
                case 1:
                    GL11.glPushMatrix();
                    GL11.glTranslatef(distance / 2F, 0, 0);
                    enableClipPlane(11, false, GL11.GL_CLIP_PLANE0);
                    SandwichRender.renderSandwich(layers, specialInfo);
                    disableClipPlane(GL11.GL_CLIP_PLANE0);
                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    GL11.glTranslatef(-distance / 2F, 0, 0);
                    enableClipPlane(5, true, GL11.GL_CLIP_PLANE0);
                    SandwichRender.renderSandwich(layers, specialInfo);
                    disableClipPlane(GL11.GL_CLIP_PLANE0);
                    GL11.glPopMatrix();
                    break;
                case 2:
                    GL11.glPushMatrix();
                    enableClipPlane(11, false, GL11.GL_CLIP_PLANE0);
                    enableClipPlane(11, true, GL11.GL_CLIP_PLANE1);
                    SandwichRender.renderSandwich(layers, specialInfo);
                    disableClipPlane(GL11.GL_CLIP_PLANE0);
                    disableClipPlane(GL11.GL_CLIP_PLANE1);
                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    GL11.glTranslatef(-distance, 0, 0);
                    enableClipPlane(5, true, GL11.GL_CLIP_PLANE0);
                    SandwichRender.renderSandwich(layers, specialInfo);
                    disableClipPlane(GL11.GL_CLIP_PLANE0);
                    GL11.glPopMatrix();

                    GL11.glPushMatrix();
                    GL11.glTranslatef(distance, 0, 0);
                    enableClipPlane(5, false, GL11.GL_CLIP_PLANE0);
                    SandwichRender.renderSandwich(layers, specialInfo);
                    disableClipPlane(GL11.GL_CLIP_PLANE0);
                    GL11.glPopMatrix();
                    break;
            }
        } else SandwichRender.renderSandwich(layers, specialInfo);
        //GL11.glDisable(GL11.GL_CLIP_PLANE5);
        GL11.glPopMatrix();
    }

    private void enableClipPlane(int pixels, boolean flip, int plane)
    {
        double pixel = 0.0418;

        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(8).put(new double[]{flip ? -1 : 1/*(pixels - 8) >= 0 ? 1 : -1*/, 0, 0, 0.001 + (pixel * (pixels - 8))});
        buffer.flip();
        GL11.glClipPlane(plane, buffer);
        GL11.glEnable(plane);
    }

    private void disableClipPlane(int plane)
    {
        GL11.glDisable(plane);
    }
}
