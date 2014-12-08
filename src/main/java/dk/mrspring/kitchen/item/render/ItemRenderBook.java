package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelHandBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by MrSpring on 06-12-2014 for TheKitchenMod.
 */
public class ItemRenderBook implements IItemRenderer
{
    ModelHandBook model = new ModelHandBook();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case EQUIPPED_FIRST_PERSON:
                return true;
            case EQUIPPED:
                return true;
            case ENTITY:
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

        EntityClientPlayerMP entity = (EntityClientPlayerMP) data[1];


//        GL11.glRotatef(35, 0F, 0F, 1F);
        GL11.glTranslatef(.6F, .8F, -.5F);

        float scale = 10F;

        GL11.glScalef(scale, scale, scale);


        GL11.glTranslatef(.5F, .5F, .5F);

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        float rotation = entity.rotationPitch;
        float maxRotation = 70, minRotation = 35;

        if (rotation > maxRotation)
        {
            rotation = maxRotation;
        } else if (rotation < minRotation)
        {
            rotation = minRotation;
        }

        GL11.glRotatef(rotation, 0, 0, 1);
        GL11.glRotatef(-90, 0, 1, 0);

        GL11.glRotatef(20, 0.3F, 0, 0);
        GL11.glTranslatef(-0.25F, -.5F, -0.2F);

        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid, "textures/models/hand_book.png"));

        model.render((Entity) data[1], 0F, 0F, 0F, 0F, 0F, 0.0625F);

        GL11.glPopMatrix();
    }
}
