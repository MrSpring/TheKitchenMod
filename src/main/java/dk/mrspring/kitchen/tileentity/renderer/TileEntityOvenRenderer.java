package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api.oven.IOvenItemRenderingHandler;
import dk.mrspring.kitchen.api_impl.client.oven.OvenRenderingRegistry;
import dk.mrspring.kitchen.model.ModelOven;
import dk.mrspring.kitchen.tileentity.TileEntityOven;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TileEntityOvenRenderer extends TileEntityTimeableRenderer
{
    protected ModelOven model;
    protected ResourceLocation inactiveTexture;
    protected ResourceLocation activeTexture;

    ItemStack[] itemStacks;

    public TileEntityOvenRenderer()
    {
        super();

        this.model = new ModelOven();

        this.inactiveTexture = new ResourceLocation(ModInfo.modid + ":textures/models/oven.png");
        this.activeTexture = new ResourceLocation(ModInfo.modid + ":textures/models/oven_active.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
    {
        super.renderTileEntityAt(var1, x, y, z, var8);

        TileEntityOven oven = (TileEntityOven) var1;

        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        bindTexture(oven.getCookTime() > 0 ? activeTexture : inactiveTexture);

        GL11.glPushMatrix();

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        GL11.glPushMatrix();
        int metadata;
        metadata = oven.getBlockMetadata();

        GL11.glRotatef(metadata * (90), 0F, 1F, 0F);

        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, oven.isOpen() ? 1 : 0);

        GL11.glPushMatrix();

        GL11.glTranslatef(0, 2, 0);

        IOvenItem previousItem = null;
        float f = 0.2F;
        float yF = 0F;
        GL11.glTranslatef(0.05F, -0.8F, 0F);
        for (int i = 0; i < oven.getSlotCount(); i++)
        {
            IOvenItem item = oven.getItemAt(i);
            boolean first = previousItem == null || previousItem == item;
            if (item != null)
            {
                IOvenItemRenderingHandler handler =
                        OvenRenderingRegistry.getInstance().getHandlerFor(oven, item, i, first);
//                GL11.glTranslatef(0.1F, 0, 0);

                GL11.glPushMatrix();
                switch (i)
                {
                    case 0:
                        GL11.glTranslatef(f, yF, -f);
                        break;
                    case 1:
                        GL11.glTranslatef(f, yF, f);
                        break;
                    case 2:
                        GL11.glTranslatef(-f, yF, f);
                        break;
                    case 3:
                        GL11.glTranslatef(-f, yF, -f);
                        break;
                }

                handler.render(oven, item, i, first);
                GL11.glPopMatrix();
            }
            previousItem = item;
        }

        GL11.glPopMatrix();

        GL11.glPopMatrix();

//        itemStacks = new ItemStack[4];

        // TODO: Fix rendering items using IOvenItemRenderer
        /*itemStacks = tileEntityOven.getOvenItems();

        double d = 0.2;

        GL11.glScalef(0.75F, 0.75F, 0.75F);

        for (int i = 0; i < itemStacks.length; ++i)
        {
            if (itemStacks[i] != null)
            {
                switch (i)
                {
                    case 0:
                        renderItem(itemStacks[i], d, 1.6, -d - 0.2);
                        break;
                    case 1:
                        renderItem(itemStacks[i], d, 1.6, d - 0.2);
                        break;
                    case 2:
                        renderItem(itemStacks[i], -d, 1.6, d - 0.2);
                        break;
                    case 3:
                        renderItem(itemStacks[i], -d, 1.6, -d - 0.2);
                        break;
                }
            }
        }*/

        GL11.glRotatef(metadata * (90), 0F, 1F, 0F);

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    private boolean eq(IOvenItem item1, IOvenItem item2)
    {
        return item1 == item2;
    }

    private void renderItem(ItemStack item, double xOffset, double yOffset, double zOffset)
    {
        if (item != null)
        {
            GL11.glPushMatrix();

            GL11.glTranslated(xOffset, yOffset, zOffset);

            ItemStack toRender = item.copy();

            EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, toRender);
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

    @Override
    public void transformTimer(TileEntity tileEntity)
    {
        int metadata = tileEntity.getBlockMetadata();
        GL11.glRotatef(metadata * 90, 0, 1, 0);

        GL11.glTranslatef(0.405F, 0.4F, -0.385F);
        float scale = 0.25F;
        GL11.glScalef(scale, scale, scale);
    }
}
