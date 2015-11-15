package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelOven;
import dk.mrspring.kitchen.model.ModelToaster;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created on 15-11-2015 for TheKitchenMod.
 */
public class ItemRenderToaster implements IItemRenderer
{
    ModelToaster model = new ModelToaster();
    ResourceLocation texture = new ResourceLocation(ModInfo.modid + ":textures/models/toaster.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
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
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case BLOCK_3D:
                return type != ItemRenderType.ENTITY;
            case INVENTORY_BLOCK:
                return type == ItemRenderType.INVENTORY;
            case ENTITY_BOBBING:
            case ENTITY_ROTATION:
                return type == ItemRenderType.ENTITY;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(ItemRenderType renderType, ItemStack item, Object... data)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        switch (renderType)
        {
            case EQUIPPED_FIRST_PERSON:
                GL11.glRotatef(25, 0F, 0F, 1F);
                GL11.glTranslatef(.4F, 1.3F, -.2F);
                GL11.glRotatef(40, 0, 1, 0);
                float scale = 0.9F;
                GL11.glScalef(0.8F, scale, scale);
                GL11.glTranslatef(.5F, -.2F, .5F);
                break;
            case EQUIPPED:
                GL11.glRotatef(25F, 0F, 0F, 1F);
                GL11.glRotatef(40, 0, 1, 0);
                GL11.glTranslatef(0.7F, 1.1F, 0.7F);
                break;
            case INVENTORY:
                GL11.glRotatef(180, 0, 1, 0);
                GL11.glTranslatef(0.035F,0,0);
                scale = 2F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0F, 1.3F, 0F);
                break;
            case ENTITY:
                scale = 0.7F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.0625F, 1.3F, 0F);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, false);
        GL11.glPopMatrix();
    }
}
