package dk.mrspring.kitchen.api_impl.client.board;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.api.board.IBoardRenderingHandler;
import dk.mrspring.kitchen.util.RenderUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public class ItemRenderingHandler implements IBoardRenderingHandler
{
    @Override
    public boolean shouldBeUsed(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        return true;
    }

    @Override
    public void render(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(0F, 0F, 1F, 0F);
        GL11.glTranslatef(.0F, -1.491F, -0.205F);
        RenderUtils.renderItem(rendering, 0D, 0D, 0D, ModConfig.getClientConfig().force_3d_item_rendering);
        GL11.glPopMatrix();
    }

    @Override
    public double getModelHeight(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagInfo, ItemStack rendering)
    {
        return 0.0325;
    }
}
