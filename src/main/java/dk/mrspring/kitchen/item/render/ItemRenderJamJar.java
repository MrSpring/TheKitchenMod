package dk.mrspring.kitchen.item.render;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelJamJar;
import dk.mrspring.kitchen.pan.Jam;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 25-09-2014 for ModJam4.
 */
public class ItemRenderJamJar implements IItemRenderer
{
    ModelJamJar model = new ModelJamJar();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        switch (type)
        {
            /*case INVENTORY:
                return true;*/
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

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid, "textures/models/jar.png"));

                NBTTagCompound compound = item.getTagCompound();
                Jam jam = Jam.getJam("empty");
                int fill = 0;
                if (compound != null)
                {
                    NBTTagCompound jamInfo = compound.getCompoundTag("JamInfo");
                    if (jamInfo != null)
                    {
                        jam = Jam.getJam(jamInfo.getString("JamType"));
                        fill = jamInfo.getInteger("UsesLeft");
                    }
                }

                model.simpleRender(0F, new ModelJamJar.Parameters(jam, fill));

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

                NBTTagCompound compound = item.getTagCompound();
                Jam jam = Jam.getJam("empty");
                int fill = 0;
                if (compound != null)
                {
                    NBTTagCompound jamInfo = compound.getCompoundTag("JamInfo");
                    if (jamInfo != null)
                    {
                        jam = Jam.getJam(jamInfo.getString("JamType"));
                        fill = jamInfo.getInteger("UsesLeft");
                    }
                }

                GL11.glTranslatef(.5F, .5F, .5F);

                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid, "textures/models/jar.png"));

                model.simpleRender(0F, new ModelJamJar.Parameters(jam, fill));

                GL11.glPopMatrix();

                break;
            }
            /*case INVENTORY:
            {
                GL11.glPushMatrix();

                float scale = 16;

                GL11.glScalef(scale, scale, scale);

                float pixel = 0.0625F;

                GL11.glTranslatef(.5F,-.5F-(3*pixel),0);
                GL11.glRotatef(180,0,0,1);
                GL11.glRotatef(45,0F,1F,0);

                NBTTagCompound compound = item.getTagCompound();
                Jam jam = Jam.EMPTY;
                int jamHeight = 0;
                int metadata = item.getItemDamage();
                if (compound != null)
                {
                    NBTTagCompound jamInfo = compound.getCompoundTag("JamInfo");
                    if (jamInfo != null)
                    {
                        jam = Jam.valueOf(jamInfo.getString("JamType").toUpperCase());
                        jamHeight = jamInfo.getInteger("UsesLeft");
                    }
                }

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.modid, "textures/models/jar.png"));

                //System.out.println("Rendering");

                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

                GL11.glPushMatrix();

                GL11.glRotatef(25,-1F,0F,1F);

                model.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F, metadata == 0, jam.getRed(), jam.getGreen(), jam.getBlue(), jamHeight);

                GL11.glPopMatrix();

                GL11.glPopMatrix();

                break;
            }*/

            default:
                break;
        }
    }
}