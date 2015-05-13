package dk.mrspring.kitchen.api_impl.client.board;

import dk.mrspring.kitchen.api.board.BoardItemRenderingHandler;
import dk.mrspring.kitchen.api.board.IBoardRenderingHandler;
import dk.mrspring.kitchen.item.render.SandwichRender;
import dk.mrspring.kitchen.pan.ItemBaseRenderingHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;
import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public class SlicingRenderingHandler extends BoardItemRenderingHandler
{
    @Override
    public boolean shouldBeUsed(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        return layers.size() == 1 && specialTagCompound.getInteger("SliceCount") > 0;
    }

    @Override
    public void render(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
//        super.render(layers, indexInList, specialTagCompound, rendering);
        int sliceCount = specialTagCompound.getInteger("SliceCount");
        float distance = 0.0425F;
        switch (sliceCount)
        {
            case 1:
                GL11.glPushMatrix();
                GL11.glTranslatef(distance / 2F, 0, 0);
                enableClipPlane(11, false, GL11.GL_CLIP_PLANE0);
                super.render(layers, indexInList, specialTagCompound, rendering);
                disableClipPlane(GL11.GL_CLIP_PLANE0);
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                GL11.glTranslatef(-distance / 2F, 0, 0);
                enableClipPlane(5, true, GL11.GL_CLIP_PLANE0);
                super.render(layers, indexInList, specialTagCompound, rendering);
                disableClipPlane(GL11.GL_CLIP_PLANE0);
                GL11.glPopMatrix();
                break;
            case 2:
                GL11.glPushMatrix();
                enableClipPlane(11, false, GL11.GL_CLIP_PLANE0);
                enableClipPlane(11, true, GL11.GL_CLIP_PLANE1);
                super.render(layers, indexInList, specialTagCompound, rendering);
                disableClipPlane(GL11.GL_CLIP_PLANE0);
                disableClipPlane(GL11.GL_CLIP_PLANE1);
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                GL11.glTranslatef(-distance, 0, 0);
                enableClipPlane(5, true, GL11.GL_CLIP_PLANE0);
                super.render(layers, indexInList, specialTagCompound, rendering);
                disableClipPlane(GL11.GL_CLIP_PLANE0);
                GL11.glPopMatrix();

                GL11.glPushMatrix();
                GL11.glTranslatef(distance, 0, 0);
                enableClipPlane(5, false, GL11.GL_CLIP_PLANE0);
                super.render(layers, indexInList, specialTagCompound, rendering);
                disableClipPlane(GL11.GL_CLIP_PLANE0);
                GL11.glPopMatrix();
                break;
        }
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

    @Override
    public double getModelHeight(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagInfo, ItemStack rendering)
    {
        return 0;
    }
}
