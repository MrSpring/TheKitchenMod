package dk.mrspring.kitchen.tileentity.casserole;

import dk.mrspring.kitchen.KitchenItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 27-01-2015.
 */
public class LayerSalad implements Layer
{
    boolean hasTomatoes = false, hasLettuce = false, hasCarrots = false;

    @Override
    public String getTypeName()
    {
        return "Salad";
    }

    @Override
    public String getTranslatedName()
    {
        return StatCollector.translateToLocal("item.salad.name");
    }

    @Override
    public boolean handleRightClick(ItemStack stack)
    {
        if (stack.getItem() == KitchenItems.tomato_slice && !hasTomatoes)
            this.hasTomatoes = true;
        else if (stack.getItem() == KitchenItems.lettuce_leaf && !hasLettuce)
            this.hasLettuce = true;
        else if (stack.getItem() == KitchenItems.carrot_slice && !hasCarrots)
            this.hasCarrots = true;
        else return false;

        stack.stackSize--;
        return true;
    }

    @Override
    public ItemStack[] removeLayer()
    {
        List<ItemStack> stackList = new ArrayList<ItemStack>();
        if (hasTomatoes) stackList.add(new ItemStack(KitchenItems.tomato_slice));
        if (hasLettuce) stackList.add(new ItemStack(KitchenItems.lettuce_leaf));
        if (hasCarrots) stackList.add(new ItemStack(KitchenItems.carrot_slice));
        if (stackList.size() > 0)
            return stackList.toArray(new ItemStack[stackList.size()]);
        else return null;
    }

    @Override
    public boolean canAddLayerOnTop(ItemStack stack)
    {
        return hasTomatoes && hasLettuce && hasCarrots;
    }

    @Override
    public void render(Casserole.CasseroleState state)
    {
        GL11.glPushMatrix();

        if (hasTomatoes)
        {
            ItemStack item = new ItemStack(KitchenItems.tomato_slice);

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
        if (hasCarrots)
        {
            ItemStack item = new ItemStack(KitchenItems.carrot_slice);

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
        if (hasLettuce)
        {
            ItemStack item = new ItemStack(KitchenItems.lettuce_leaf);

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
        LayerSalad layer = new LayerSalad();
        layer.hasTomatoes = compound.getBoolean("HasTomatoes");
        layer.hasLettuce = compound.getBoolean("HasLettuce");
        layer.hasCarrots = compound.getBoolean("HasCarrots");
        return layer;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setBoolean("HasTomatoes", this.hasTomatoes);
        compound.setBoolean("HasLettuce", this.hasLettuce);
        compound.setBoolean("HasCarrots", this.hasCarrots);
        return compound;
    }

    @Override
    public Layer newInstance(ItemStack stack)
    {
        Layer newLayer = new LayerSalad();
        if (stack != null)
            newLayer.handleRightClick(stack);
        return newLayer;
    }
}
