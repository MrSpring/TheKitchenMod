package dk.mrspring.kitchen.tileentity.casserole;

import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 27-01-2015.
 */
public class LayerLasagnaPlates implements Layer
{
    int plates = 0;

    @Override
    public boolean handleRightClick(ItemStack stack)
    {
        if (stack.getItem() == KitchenItems.lasagna_plate)
        {
            int beforeStack = plates;
            this.plates += Math.min(stack.stackSize, 3 - this.plates);
            stack.stackSize -= (plates - beforeStack);
            return true;
        } else return false;
    }

    @Override
    public String getTypeName()
    {
        return "LasagnaPlates";
    }

    @Override
    public ItemStack[] removeLayer()
    {
        return plates > 0 ? new ItemStack[]{new ItemStack(KitchenItems.lasagna_plate, plates)} : null;
    }

    @Override
    public boolean canAddLayerOnTop(ItemStack stack)
    {
        return plates >= 3;
    }

    @Override
    public void render(Casserole.CasseroleState state)
    {
        GL11.glPushMatrix();
        for (int i = 0; i < plates; i++)
        {
            ItemStack item = new ItemStack(KitchenItems.lasagna_plate);

            GL11.glTranslated(0, 0, 0.5);

            GL11.glPushMatrix();

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
        GL11.glPopMatrix();
    }

    @Override
    public Layer loadFromNBT(NBTTagCompound compound)
    {
        LayerLasagnaPlates layer = new LayerLasagnaPlates();
        layer.plates = compound.getInteger("PlateCount");
        return layer;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("PlateCount", this.plates);
        return compound;
    }

    @Override
    public Layer newInstance(ItemStack stack)
    {
        Layer layer = new LayerLasagnaPlates();
        if (stack != null)
            layer.handleRightClick(stack);
        return layer;
    }
}
