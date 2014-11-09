package dk.mrspring.kitchen.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Created by MrSpring on 04-11-2014.
 */
public class ModelItemBase extends ModelBase
{
    private final ItemStack rendering;

    public ModelItemBase(ItemStack toRender)
    {
        this.rendering=toRender;
    }

    public ModelItemBase(Item toRender)
    {
        this(new ItemStack(toRender));
    }

    @Override
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef(0,2.822F,0);
        float s=0.8F;
        GL11.glScalef(s,1,s);

        EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, rendering);
        itemEntity.hoverStart = 0.0F;
        RenderItem.renderInFrame = true;
        GL11.glRotatef(180, 0, 1, 1);
        GL11.glTranslatef(.0F,-.2F,-1.395F);
        RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        RenderItem.renderInFrame = false;

        GL11.glPopMatrix();
    }
}
