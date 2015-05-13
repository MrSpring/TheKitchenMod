package dk.mrspring.kitchen.api_impl.client.board;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.board.IBoardRenderingHandler;
import dk.mrspring.kitchen.model.ModelBreadSliceBottom;
import dk.mrspring.kitchen.model.ModelBreadSliceTop;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public class BreadSliceRenderingHandler implements IBoardRenderingHandler
{
    ModelBreadSliceTop top = new ModelBreadSliceTop();
    ModelBreadSliceBottom bottom = new ModelBreadSliceBottom();

    @Override
    public boolean shouldBeUsed(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        return rendering.getItem() == KitchenItems.bread_slice;
    }

    @Override
    public void render(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(180, 0, 0, 1);
        float s = 0.65F;
        GL11.glScalef(s, s, s);
        GL11.glTranslatef(0, 0.8F, 0);
        if (indexInList + 1 >= layers.size())
            top.render(null, 0, 0, 0, 0, 0, 0.0625F);
        else bottom.render(null, 0, 0, 0, 0, 0, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public double getModelHeight(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagInfo, ItemStack rendering)
    {
        return 0.085;
    }
}
