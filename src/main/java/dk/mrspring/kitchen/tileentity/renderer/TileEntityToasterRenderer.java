package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelToaster;
import dk.mrspring.kitchen.tileentity.TileEntityTimeable;
import dk.mrspring.kitchen.tileentity.TileEntityToaster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class TileEntityToasterRenderer extends TileEntityTimeableRenderer
{
    ModelToaster model = new ModelToaster();
    ResourceLocation texture = new ResourceLocation(ModInfo.toTexture("textures/models/toaster.png"));

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float p_147500_8_)
    {
        super.renderTileEntityAt(var1, x, y, z, p_147500_8_);

        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        ItemStack stack1 = ((TileEntityToaster) var1).getStack1(), stack2 = ((TileEntityToaster) var1).getStack2();


        int metadata = var1.getBlockMetadata();
        GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0635F, ((TileEntityToaster) var1).isCooking());

        float yOffset = 0;

        if (((TileEntityToaster) var1).isCooking())
            yOffset = 0.08F;

        if (stack1 != null)
            this.renderItemStack(stack1, 0, yOffset, 0.12F);
        if (stack2 != null)
            this.renderItemStack(stack2, 0, yOffset, 0);

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }

    public void renderItemStack(ItemStack stack, float offsetX, float offsetY, float offsetZ)
    {
        float scale = 0.8F;
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(0, 1.6F + offsetY, -0.03F + offsetZ);
        GL11.glTranslatef(offsetX, offsetY, offsetZ);
        EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, stack);
        itemEntity.hoverStart = 0.0F;
        RenderItem.renderInFrame = true;
        GL11.glRotatef(180, 0, 0, 1);
        RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, -0.08385D, 0.0F, 0.0F);
        RenderItem.renderInFrame = false;
        GL11.glPopMatrix();
    }

    @Override
    public void transformTimer(TileEntity tileEntity)
    {
        int metadata = tileEntity.getBlockMetadata();
        GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);

        GL11.glTranslatef(-0.02F, 0.75F, -0.205F);

        float scale = 0.4F;
        GL11.glScalef(scale, scale, scale);
    }
}
