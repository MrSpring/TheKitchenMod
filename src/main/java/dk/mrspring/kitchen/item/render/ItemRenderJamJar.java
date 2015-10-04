package dk.mrspring.kitchen.item.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.item.ItemJamJar;
import dk.mrspring.kitchen.model.ModelJamJar;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 25-09-2014 for ModJam4.
 */
@SideOnly(Side.CLIENT)
public class ItemRenderJamJar implements IItemRenderer
{
    public static final ColorHandler COLOR_HANDLER = new ColorHandler()
    {
        @Override
        String getIdentifierFromStack(ItemStack stack)
        {
            return ItemJamJar.getJamFromStack(stack);
        }

        @Override
        public void loadDefaults()
        {
            registerColor("strawberry", 16196364);
            registerColor("apple", 14415786);
            registerColor("peanut", 9659689);
            registerColor("cocoa", 0x895836);
            registerColor("ketchup", 0xFF3200);
        }
    };

    ModelJamJar model = new ModelJamJar();
    ResourceLocation texture = ModInfo.toResource("textures/models/jar.png");

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
        int metadata = item.getItemDamage();
        int color = COLOR_HANDLER.getColorFromStack(item);

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

                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                model.render((Entity) data[1], 0F, 0F, 0F, 0F, 0F, 0.0625F, color, metadata);

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

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid, "textures/models/jar.png"));
                model.render((Entity) data[1], 0F, 0F, 0F, 0F, 0F, 0.0625F, color, metadata);

                GL11.glPopMatrix();

                break;
            }
            default:
                break;
        }
    }
}