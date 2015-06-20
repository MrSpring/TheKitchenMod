package dk.mrspring.kitchen.api_impl.client.ingredient;

import static dk.mrspring.kitchen.api_impl.common.ingredient.RecipeIngredient.*;

import dk.mrspring.kitchen.api.ingredient.IFryingPan;
import dk.mrspring.kitchen.api.ingredient.IIngredient;
import dk.mrspring.kitchen.api.ingredient.IIngredientRenderingHandler;
import dk.mrspring.kitchen.api_impl.common.ingredient.RecipeIngredient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 02-06-2015.
 */
public class RecipeIngredientRenderingHandler implements IIngredientRenderingHandler
{
    @Override
    public boolean shouldBeUsed(IFryingPan fryingPan, IIngredient ingredient)
    {
        return ingredient instanceof RecipeIngredient;
    }

    @Override
    public void render(IFryingPan fryingPan, IIngredient ingredient)
    {
        GL11.glPushMatrix();
        NBTTagCompound compound = fryingPan.getSpecialInfo();
        NBTTagCompound itemCompound = compound.getCompoundTag(fryingPan.isFinished() ? RECIPE_OUTPUT : RECIPE_INPUT);
        ItemStack rendering = ItemStack.loadItemStackFromNBT(itemCompound);
        rendering.stackSize = 1;

        GL11.glTranslatef(0, -1.36F, -0.143F);
        float s = 0.7F;
        GL11.glScalef(s, s, s);

        renderItem(rendering, 0, 0, 0);
        GL11.glPopMatrix();
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
