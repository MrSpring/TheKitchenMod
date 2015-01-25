package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.model.ModelIceCream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konrad on 24-01-2015.
 */
public class ItemIceCreamableRenderer implements IItemRenderer
{
    public class IceCream
    {
        String name;
        float[] color;

        public IceCream(String name, float[] colors)
        {
            this.name = name;
            this.color = colors;
        }

        public IceCream(String name, float r, float g, float b)
        {
            this(name, new float[]{r, g, b});
        }

        public IceCream(String name, int color)
        {
            float red = ((color >> 16) & 0xFF);
            float green = ((color >> 8) & 0xFF);
            float blue = (color & 0xFF);
            float[] colorAsRGB = new float[]{red, green, blue};

            this.name = name;
            this.color = colorAsRGB;
        }
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case EQUIPPED:
                return true;
            case EQUIPPED_FIRST_PERSON:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();

        switch (type)
        {
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0.25F, 0.6F, -0.4F);
                GL11.glRotatef(20, 0, 0, 1);
                break;
            case EQUIPPED:
                GL11.glScalef(2, 2, 2);
                GL11.glRotatef(-60, 1, 0, 0);
                GL11.glRotatef(17.5F, 0, 0, 1);
                GL11.glTranslatef(0.2F, 0.2F, 0);
                break;
        }

        if (item.hasTagCompound())
        {
            if (item.getTagCompound().hasKey("IceCream"))
            {
                IceCream[] creams = getIceCreamsFromStack(item);
                if (creams.length > 0)
                    renderIceCream(creams[0].color, -0.05F, 0, 0.11F);
                if (creams.length > 1)
                    renderIceCream(creams[1].color, 0.1F, 0.04F, 0.0F);
                if (creams.length > 2)
                    renderIceCream(creams[2].color, 0, 0.03F, -0.12F);
                if (creams.length > 3)
                    renderIceCream(creams[3].color, 0.04F, 0.2F, 0.1F);
            }
        }
        renderItem(item, 0, 0, 0);

        GL11.glPopMatrix();
    }

    public IceCream[] getIceCreamsFromStack(ItemStack stack)
    {
        List<IceCream> iceCreams = new ArrayList<IceCream>();

        NBTTagCompound compound = stack.getTagCompound();
        NBTTagList iceCreamsCompound = compound.getTagList("IceCream", 8);

        for (int i = 0; i < iceCreamsCompound.tagCount(); i++)
        {
            String iceCreamName = iceCreamsCompound.getStringTagAt(i);
            if (iceCreamName != null)
            {
                int color = ItemMixingBowlRenderer.getColorAsInteger(iceCreamName);
                iceCreams.add(new IceCream(iceCreamName, color));
            }
        }

        return iceCreams.toArray(new IceCream[iceCreams.size()]);
    }

    private void renderIceCream(float[] color, float xOffset, float yOffset, float zOffset)
    {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glTranslatef(-0.025F, 1.15F, 0.41F);
        GL11.glTranslatef(xOffset, yOffset, zOffset);
        GL11.glColor3f(color[0] / 255, color[1] / 255, color[2] / 255);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/models/jam.png"));
        new ModelIceCream().render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);
        GL11.glPopMatrix();
    }

    private void renderItem(ItemStack toRender, double xOffset, double yOffset, double zOffset)
    {
        if (toRender != null)
        {
            GL11.glPushMatrix();

            GL11.glScalef(1, 2, 1);
            GL11.glTranslated(xOffset, yOffset, zOffset);
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
}
