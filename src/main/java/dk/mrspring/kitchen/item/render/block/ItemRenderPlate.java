package dk.mrspring.kitchen.item.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.config.ClientConfig;
import dk.mrspring.kitchen.item.render.ItemRenderer;
import dk.mrspring.kitchen.item.render.PlateRender;
import dk.mrspring.kitchen.model.ModelPlate;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static dk.mrspring.kitchen.ClientUtils.rotate;
import static dk.mrspring.kitchen.ClientUtils.translate;

/**
 * Created on 15-11-2015 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ItemRenderPlate extends ItemRenderer
{
    ModelPlate plate = new ModelPlate();

    public ItemRenderPlate()
    {
        this.renderLikeBlock();
    }

    @Override
    public void renderAnyTypes(ItemStack item, Object... data)
    {
        plate.simpleRender(0F);

        rotate(180F, 0F, 0F, 1F);
        translate(0F, -1.39F, 0F);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        PlateRender.renderPlateContents(new PlateRender.Plate(item));
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    @Override
    public ClientConfig.RenderConfig getRenderConfig()
    {
        return ModConfig.getClientConfig().plate_rendering;
    }
/*
    ModelPlate model = new ModelPlate();
    ResourceLocation texture = new ResourceLocation(ModInfo.modid + ":textures/models/plate.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return ClientProxy.clientConfig.plate_rendering.handleType(type);
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
        this.model.simpleRender(0F);
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
//            double height = TileEntityPlateRenderer.renderPlateContents(plate.items, null);
//            item.getTagCompound().getCompoundTag(RENDERING_ON_PLATE).setDouble(CUSTOM_HEIGHT, height);
            TileEntityPlateRenderer.renderItems(plate.items);
            GL11.glPopMatrix();
        }
    }

    private class Plate
    {
        ItemStack[] items;

        public Plate(ItemStack stack)
        {
            NBTTagCompound info = stack.getTagCompound().getCompoundTag("PlateData");
            NBTTagList list = info.getTagList("Items", 10);
            if (list.tagCount() > 0)
            {
                items = new ItemStack[list.tagCount()];
                for (int i = 0; i < list.tagCount(); i++)
                    items[i] = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
            }
        }
    }
*/
}
