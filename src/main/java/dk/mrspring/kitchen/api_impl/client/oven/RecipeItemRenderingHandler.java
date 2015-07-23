package dk.mrspring.kitchen.api_impl.client.oven;

import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api.oven.IOvenItemRenderingHandler;
import dk.mrspring.kitchen.api_impl.common.oven.RecipeOvenItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import static dk.mrspring.kitchen.api_impl.common.oven.RecipeOvenItem.RECIPE_INPUT;
import static dk.mrspring.kitchen.api_impl.common.oven.RecipeOvenItem.RECIPE_OUTPUT;

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
    public void render(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
        NBTTagCompound compound = oven.getSpecialInfo(slot);
        NBTTagCompound itemCompound = compound.getCompoundTag(oven.isFinished() ? RECIPE_OUTPUT : RECIPE_INPUT);
        if (itemCompound != null)
        {
            GL11.glPushMatrix();
            ItemStack rendering = ItemStack.loadItemStackFromNBT(itemCompound);
            renderItem(rendering, 0, 0, 0);
            GL11.glPopMatrix();
        }
    }

    private void renderItem(ItemStack item, double xOffset, double yOffset, double zOffset)
    {
        if (item != null)
        {
            GL11.glPushMatrix();
            GL11.glTranslated(xOffset, yOffset, zOffset);
            EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, item);
            itemEntity.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            GL11.glRotatef(180, 0, 1, 1);
            GL11.glRotatef(180, 0, 1, 0);
            GL11.glTranslatef(0, 0, .1F);
            RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;
            GL11.glPopMatrix();
        }
    }
}
