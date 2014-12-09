package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelMixingBowl;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by MrSpring on 10-12-2014 for TheKitchenMod.
 */
public class ItemMixingBowlRenderer implements IItemRenderer
{
    ModelMixingBowl model = new ModelMixingBowl();

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
        switch (type)
        {
            case EQUIPPED:
            {
                GL11.glPushMatrix();

                GL11.glRotatef(180, 1, 0, 0);
                GL11.glRotatef(-10, 0, 0, 1);
                GL11.glTranslatef(.6F, -1.5F, .09F);

                float scale = 1.2F;

                GL11.glScalef(scale, scale, scale);

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid, "textures/models/mixing_bowl.png"));

                model.render((Entity) data[1], 0F, 0F, 0F, 0F, 0F, 0.0625F, item.getItemDamage());

                GL11.glPopMatrix();

                break;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                GL11.glPushMatrix();

                GL11.glRotatef(35, 0F, 0F, 1F);
                GL11.glTranslatef(.6F, .8F, -.5F);

                float scale = 1.5F;

                GL11.glScalef(1, scale, scale);

                GL11.glTranslatef(.5F, .5F, .5F);

                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glTranslatef(0.1F, 0, 0);
                GL11.glRotatef(10F, 1F, 0, 1F);

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid, "textures/models/mixing_bowl.png"));

                model.render((Entity) data[1], 0F, 0F, 0F, 0F, 0F, 0.0625F, item.getItemDamage());

                GL11.glPopMatrix();

                break;
            }
            default:
                break;
        }
    }
}
