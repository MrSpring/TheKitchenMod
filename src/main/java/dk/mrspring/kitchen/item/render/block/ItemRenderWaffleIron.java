package dk.mrspring.kitchen.item.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.block.ModelWaffleIron;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import static dk.mrspring.kitchen.tileentity.renderer.TileEntityPlateRenderer.CUSTOM_HEIGHT;
import static dk.mrspring.kitchen.tileentity.renderer.TileEntityPlateRenderer.RENDERING_ON_PLATE;

/**
 * Created on 15-11-2015 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ItemRenderWaffleIron implements IItemRenderer
{
    ModelWaffleIron model = new ModelWaffleIron();
    ResourceLocation texture = new ResourceLocation(ModInfo.modid + ":textures/models/waffle_iron.png");

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
                GL11.glTranslatef(.4F, 1.3F, .3F);
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
                scale = 1.5F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.0F, 1.25F, 0F);
                break;
            case ENTITY:
                scale = 0.6F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0F, 1.3F, 0F);
                if (item.hasTagCompound() && item.getTagCompound().hasKey(RENDERING_ON_PLATE))
                    this.translateForPlate(item);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, 0, 0, 0, null, 0F);
        GL11.glPopMatrix();
    }

    private void translateForPlate(ItemStack stack)
    {
        stack.getTagCompound().getCompoundTag(RENDERING_ON_PLATE).setDouble(CUSTOM_HEIGHT, 0.162D);
        GL11.glRotatef(90, 1, 0, 0);
        float s = 1.3F;
        GL11.glScalef(s, s, s);
        GL11.glTranslatef(0F, 1.425F, 0.7F);
    }
}
