package dk.mrspring.kitchen.item.render.block;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelPlate;
import dk.mrspring.kitchen.tileentity.renderer.TileEntityPlateRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static dk.mrspring.kitchen.tileentity.renderer.TileEntityPlateRenderer.CUSTOM_HEIGHT;
import static dk.mrspring.kitchen.tileentity.renderer.TileEntityPlateRenderer.RENDERING_ON_PLATE;

/**
 * Created on 15-11-2015 for TheKitchenMod.
 */
public class ItemRenderPlate implements IItemRenderer
{
    ModelPlate model = new ModelPlate();
    ResourceLocation texture = new ResourceLocation(ModInfo.modid + ":textures/models/plate.png");

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
        GL11.glPushMatrix();
        switch (renderType)
        {
            case EQUIPPED_FIRST_PERSON:
                GL11.glRotatef(25, 0F, 0F, 1F);
                GL11.glTranslatef(.3F, 1.2F, -.1F);
                GL11.glRotatef(40, 0, 1, 0);
                float scale = 0.8F;
                GL11.glScalef(0.6F, scale, scale);
                GL11.glTranslatef(.5F, -.2F, .5F);
                this.renderPlateItems(item);
                break;
            case EQUIPPED:
                GL11.glRotatef(25F, 0F, 0F, 1F);
                GL11.glRotatef(40, 0, 1, 0);
                GL11.glTranslatef(0.6F, 0.85F, 0.6F);
                scale = 0.7F;
                GL11.glScalef(scale, scale, scale);
                break;
            case INVENTORY:
                GL11.glRotatef(180, 0, 1, 0);
                scale = 1.3F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.0F, 1.4F, 0F);
                break;
            case ENTITY:
                scale = 0.5F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0F, 1.3F, 0F);
                GL11.glTranslatef(0F, 0F, -0.1F);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    private void renderPlateItems(ItemStack item)
    {
        if (!item.hasTagCompound()) return;
        Plate plate = new Plate(item);
        if (plate.items != null)
        {
            GL11.glPushMatrix();
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            double height = TileEntityPlateRenderer.renderPlateContents(plate.items);
            item.getTagCompound().getCompoundTag(RENDERING_ON_PLATE).setDouble(CUSTOM_HEIGHT, height);
            GL11.glPopMatrix();
        }
    }

    private class Plate
    {
        List<ItemStack> items;

        public Plate(ItemStack stack)
        {
            NBTTagCompound info = stack.getTagCompound().getCompoundTag("PlateData");
            NBTTagList list = info.getTagList("Items", 10);
            if (list.tagCount() > 0)
            {
                items = new ArrayList<ItemStack>();
                for (int i = 0; i < list.tagCount(); i++)
                    items.add(ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i)));
            }
        }
    }
}
