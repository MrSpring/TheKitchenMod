package dk.mrspring.kitchen.api_impl.client.oven;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api.oven.IOvenItemRenderingHandler;
import dk.mrspring.kitchen.api_impl.common.oven.RecipeOvenItem;
import dk.mrspring.kitchen.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 20-07-2015.
 */
public class RecipeItemRenderingHandler implements IOvenItemRenderingHandler
{
    @Override
    public boolean shouldBeUsed(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
        return item instanceof RecipeOvenItem;
    }

    @Override
    public void renderPreTranslate(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {

    }

    @Override
    public void render(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
        NBTTagCompound compound = oven.getSpecialInfo(slot);
        NBTTagCompound itemCompound = compound.getCompoundTag(oven.isFinished(slot) ? (oven.isBurnt(slot) ? RecipeOvenItem.RECIPE_BURNT_OUTPUT : RecipeOvenItem.RECIPE_OUTPUT) : RecipeOvenItem.RECIPE_INPUT);
        ItemStack rendering = ItemStack.loadItemStackFromNBT(itemCompound);
        if (rendering != null)
        {
            GL11.glPushMatrix();
            float s = 0.7F;
            GL11.glScalef(s, s, s);
            GL11.glRotatef(90,1,0,0); // TODO: Place properly
            RenderUtils.renderItem(rendering, 0, 0, 0, ModConfig.getClientConfig().force_3d_item_rendering);
            GL11.glPopMatrix();
        }
    }
}
