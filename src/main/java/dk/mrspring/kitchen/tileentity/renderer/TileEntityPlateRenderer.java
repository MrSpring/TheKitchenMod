package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.item.ItemSandwich;
import dk.mrspring.kitchen.item.render.SandwichRender;
import dk.mrspring.kitchen.model.ModelPlate;
import dk.mrspring.kitchen.tileentity.TileEntityPlate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityPlateRenderer extends TileEntitySpecialRenderer
{
    ModelPlate modelPlate;
    ResourceLocation texture;

    public TileEntityPlateRenderer()
    {
        this.modelPlate = new ModelPlate();
        this.texture = new ResourceLocation(ModInfo.modid + ":textures/models/plate.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        // TODO Coffee Color - GL11.glColor4f(0.247F, 0.168F, 0.062F, 1.0F);
        int metadata = var1.getBlockMetadata();
        GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);
        this.modelPlate.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0635F);

        GL11.glPopMatrix();

        GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);

        renderItems(((TileEntityPlate) var1).getItemsAsArray());

        /*for (ItemStack itemStack : ((TileEntityPlate) var1).getItemsAsArray())
        {
            if (itemStack != null)
            {
                if (itemStack.getItem() instanceof ItemSandwich)
                {
                    GL11.glTranslatef(0.0F,-0.1F,0.0F);
                    this.renderSadwich(itemStack);
                }
                else
                {
                    this.renderItem(itemStack, 0, yItemOffset + 1.4, -0.225F);
                    yItemOffset -= 0.03;
                }
            }
        }*/

        GL11.glPopMatrix();
    }

    public static void renderItems(ItemStack[] items)
    {
        double yItemOffset = 0;
        for (ItemStack itemStack : items)
        {
            if (itemStack != null)
            {
                if (itemStack.getItem() instanceof ItemSandwich)
                {
                    GL11.glTranslatef(0.0F, -0.1F, 0.0F);
                    renderSadwich(itemStack);
                } else
                {
                    renderItem(itemStack, 0, yItemOffset + 1.4, -0.225F);
                    yItemOffset -= 0.03;
                }
            }
        }
    }

    private static void renderItem(ItemStack item, double xOffset, double yOffset, double zOffset)
    {
        if (item != null)
        {
            GL11.glPushMatrix();

            GL11.glTranslated(xOffset, yOffset, zOffset);

            ItemStack toRender = item.copy();
            toRender.stackSize = 1;

            EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, toRender);
            itemEntity.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            GL11.glRotatef(180, 0, 1, 1);
            GL11.glRotatef(180, 0, 1, 0);
            RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;

            GL11.glPopMatrix();
        }
    }

    private static void renderSadwich(ItemStack item)
    {
        GL11.glPushMatrix();
        float scale = 1.0F;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glTranslatef(0, 0F, 0F);
        SandwichRender.renderSandwich(item, null);
        GL11.glPopMatrix();
    }
}
