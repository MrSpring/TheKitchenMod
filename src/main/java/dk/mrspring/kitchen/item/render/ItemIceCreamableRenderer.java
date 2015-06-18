package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.model.ModelIceCream;
import dk.mrspring.kitchen.model.ModelIceCreamCone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad on 24-01-2015.
 */
public class ItemIceCreamableRenderer implements IItemRenderer
{
    public static Map<Item, ICustomModel> specialItemModels;

    public static void load()
    {
        specialItemModels = new HashMap<Item, ICustomModel>();

        specialItemModels.put(KitchenItems.ice_cream_cone, new SimpleCustomModel(new ModelIceCreamCone())
        {
        /*.setPositionOffset(0, 0.1F, 0)*/

            @Override
            public void preRender(ItemStack rendering, ItemRenderType renderType)
            {
                //super.preRender(rendering);
                if (renderType == ItemRenderType.ENTITY)
                {
                    GL11.glScalef(0.75F, 0.75F, 0.75F);
                    GL11.glTranslatef(0, 0.5F, 0);
                }
            }
        });
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        if (getIceCreamsFromStack(item).length > 0)
            switch (type)
            {
                case EQUIPPED:
                    return true;
                case EQUIPPED_FIRST_PERSON:
                    return true;
                case ENTITY:
                    return true;
                default:
                    return false;
            }
        else return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case ENTITY_BOBBING:
                return true;
            case ENTITY_ROTATION:
                return true;
        }
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
            case ENTITY:
                GL11.glScalef(2, 2, 2);
                GL11.glTranslatef(0.01F, 0.15F, -0.2F);
                break;
        }


        if (specialItemModels.containsKey(item.getItem()))
        {
            ICustomModel customModel = specialItemModels.get(item.getItem());
            customModel.preRender(item, type);
            customModel.getModel(item).render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);
        } else
            renderItem(new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()), 0, 0, 0);

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

        GL11.glPopMatrix();
    }

    public IceCream[] getIceCreamsFromStack(ItemStack stack)
    {
        List<IceCream> iceCreams = new ArrayList<IceCream>();

        NBTTagCompound compound = stack.getTagCompound();
        if (compound != null)
        {
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

    private void renderItem(ItemStack stack, double xOffset, double yOffset, double zOffset)
    {
        if (stack != null)
        {
            GL11.glPushMatrix();

            ItemStack toRender = stack.copy();
            toRender.stackSize = 1;
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

    public interface ICustomModel
    {
        ModelBase getModel(ItemStack rendering);

        void preRender(ItemStack rendering, ItemRenderType renderType);
    }

    public static class SimpleCustomModel implements ICustomModel
    {
        ModelBase model;
        float[] translateOffset = new float[]{0, 0, 0};

        public SimpleCustomModel(ModelBase model)
        {
            this.model = model;
        }

        public SimpleCustomModel setPositionOffset(float x, float y, float z)
        {
            this.translateOffset = new float[]{x, y, z};
            return this;
        }

        @Override
        public ModelBase getModel(ItemStack rendering)
        {
            return model;
        }

        @Override
        public void preRender(ItemStack rendering, ItemRenderType renderType)
        {
            GL11.glTranslatef(translateOffset[0], translateOffset[1], translateOffset[2]);
        }
    }

    public class IceCream // TODO: Strawberry jam on top of IceCream
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
            this.name = name;
            this.color = ItemMixingBowlRenderer.intAsFloatArray(color);
        }
    }
}
