package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelPan;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created on 15-11-2015 for TheKitchenMod.
 */
public class ItemRenderFryingPan implements IItemRenderer
{
    ModelPan model = new ModelPan();
    ResourceLocation texture = new ResourceLocation(ModInfo.modid + ":textures/models/pan.png");

    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
    {
        switch (type)
        {
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON:
            case INVENTORY:
            case ENTITY:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
    {
        switch (helper)
        {
            case BLOCK_3D:
                return type != IItemRenderer.ItemRenderType.ENTITY;
            case INVENTORY_BLOCK:
                return type == IItemRenderer.ItemRenderType.INVENTORY;
            case ENTITY_BOBBING:
            case ENTITY_ROTATION:
                return type == IItemRenderer.ItemRenderType.ENTITY;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(IItemRenderer.ItemRenderType renderType, ItemStack item, Object... data)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        switch (renderType)
        {
            case EQUIPPED_FIRST_PERSON:
                GL11.glRotatef(35, 0F, 0F, 1F);
                GL11.glTranslatef(0.55F, .8F, -.6F);
                float scale = 1.0F;
                GL11.glScalef(0.8F, scale, scale);
                GL11.glTranslatef(.5F, .5F, .5F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                break;
            case EQUIPPED:
                GL11.glRotatef(27, 0, 0, 1);
                GL11.glRotatef(17, 1, 0, 0);
                GL11.glRotatef(85, 0, 1, 0);
                GL11.glTranslatef(0.3F, 2.0F, 0.875F);
                scale = 1.4F;
                GL11.glScalef(scale, scale, scale);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                break;
            case INVENTORY:
                GL11.glRotatef(-90, 0, 1, 0);
                scale = 2F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.0F, 1.4F, 0F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                break;
            case ENTITY:
                scale = 1.0F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0F, 1.4F, 0F);
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                break;
        }

        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
