package dk.mrspring.kitchen.api_impl.client.board;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.api.board.IBoardRenderingHandler;
import dk.mrspring.kitchen.model.ModelBaconCooked;
import dk.mrspring.kitchen.model.ModelBaconRaw;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Konrad on 13-05-2015.
 */
public class BaconRenderingHandler implements IBoardRenderingHandler
{
    private static ModelBaconRaw raw = new ModelBaconRaw();
    private static ModelBaconCooked cooked = new ModelBaconCooked();

    @Override
    public boolean shouldBeUsed(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        return rendering.getItem() == KitchenItems.bacon || rendering.getItem() == KitchenItems.raw_bacon;
    }

    @Override
    public void render(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagCompound, ItemStack rendering)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(180, 0, 0, 1);
        float s = 0.65F;
        GL11.glScalef(s, s, s);
        GL11.glTranslatef(0, 0.8F, 0);
        if (rendering.getItem() == KitchenItems.bacon)
            cooked.render(null, 0, 0, 0, 0, 0, 0.0625F);
        else raw.render(null, 0, 0, 0, 0, 0, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public double getModelHeight(List<ItemStack> layers, int indexInList, NBTTagCompound specialTagInfo, ItemStack rendering)
    {
        return rendering.getItem() == KitchenItems.bacon ? 0.085 : 0.0425;
    }
}
