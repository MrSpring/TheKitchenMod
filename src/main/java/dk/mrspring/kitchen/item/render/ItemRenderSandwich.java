package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import static dk.mrspring.kitchen.ClientUtils.rotate;
import static dk.mrspring.kitchen.ClientUtils.translate;

public class ItemRenderSandwich extends ItemRenderer
{
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            case INVENTORY:
                return false;
            default:
                return super.handleRenderType(item, type);
        }
    }

    @Override
    public void renderEquippedFirstPerson(ItemRenderType type, ItemStack stack, Object... data)
    {
        translate(0.5F, 0.3F, 0F);
        rotate(90F, 0F, 1F, 0F);
        super.renderEquippedFirstPerson(type, stack, data);
    }

    @Override
    public void renderAnyTypes(ItemStack item, Object... data)
    {
        SandwichRender.renderSandwich(new SandwichRender.Sandwich(item));
    }

    @Override
    public ClientConfig.RenderConfig getRenderConfig()
    {
        return ModConfig.getClientConfig().sandwich_rendering;
    }
/*
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
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
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case ENTITY_BOBBING:
            case ENTITY_ROTATION:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch (type)
        {
            case EQUIPPED:
            {
                GL11.glPushMatrix();

                GL11.glRotatef(-60, 1, 0, 0);
                GL11.glRotatef(20, 0, 0, 1);
                GL11.glTranslatef(.4F, 1.2F, .125F);
                float f = 1.1F;
                GL11.glScalef(f, f, f);

                SandwichRender.renderSandwich(new SandwichRender.Sandwich(item));

                GL11.glPopMatrix();

                break;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                GL11.glPushMatrix();

                //GL11.glTranslatef(-1F, 0F, 0F);
                GL11.glTranslatef(.35F, 1.3F, -0.2F);
                GL11.glRotatef(90, 0, 1, 0);
                GL11.glRotatef(-10, 1, 0, 0);

                float scale = 0.7F;
                GL11.glScalef(scale, scale, scale);

                SandwichRender.renderSandwich(new SandwichRender.Sandwich(item));

                GL11.glPopMatrix();

                break;
            }
            case ENTITY:
            {
                GL11.glPushMatrix();

                GL11.glTranslatef(0F, 2F, 0F);
                float f = 1.5F;
                GL11.glScalef(f, f, f);


                SandwichRender.renderSandwich(new SandwichRender.Sandwich(item));

                GL11.glPopMatrix();

                break;
            }
        }
    }
*/
}
