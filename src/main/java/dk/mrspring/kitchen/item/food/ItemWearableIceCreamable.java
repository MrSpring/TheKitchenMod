package dk.mrspring.kitchen.item.food;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 13-05-2015.
 */
public class ItemWearableIceCreamable extends ItemFoodArmorBase
{
    public ItemWearableIceCreamable(String name, String textureName, int baseHealAmount, String armorTexture, int armorType)
    {
        super(name, textureName, baseHealAmount, armorTexture, armorType);
    }

    public ItemWearableIceCreamable(String name, int baseHealAmount, String armorTexture, int armorType)
    {
        super(name, baseHealAmount, armorTexture, armorType);
    }

    @Override
    public int getHealAmount(ItemStack stack)
    {
        int healBase = super.getHealAmount(stack);
        if (stack.hasTagCompound())
            healBase += (stack.getTagCompound().getTagList("IceCream", 8).tagCount());
        return healBase;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, final ItemStack itemStack, int armorSlot)
    {
        return new ModelBiped(0F)
        {
            @Override
            public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float headRotateX, float headRotateY, float p_78088_7_)
            {
                super.render(entity, p_78088_2_, p_78088_3_, p_78088_4_, headRotateX, headRotateY, p_78088_7_);

//                this.bipedHead.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
//                this.bipedHead.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);

                GL11.glPushMatrix();
                EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, itemStack);
//                System.out.println(entity.getRotationYawHead());
//                float yaw = entity.getRotationYawHead();
//                System.out.println(entity.getClass().getName());
//                float rotateX = headRotateX / (180F / (float) Math.PI);
//                float rotateY = headRotateY / (180F / (float) Math.PI);
//                System.out.println("X: " + rotateX + ", Y: " + rotateY); One: 45 degrees
//                System.out.println("X: " + this.bipedHead.rotateAngleX + ", Y: " + this.bipedHead.rotateAngleY);
//                System.out.println(entity.getClass().getSimpleName());
                GL11.glRotatef((float) Math.toDegrees((double) this.bipedHead.rotateAngleY), 0, 1, 0);
                GL11.glRotatef((float) Math.toDegrees((double) this.bipedHead.rotateAngleX), 1, 0, 0);
                GL11.glPushMatrix();
                float s = 1.4F;
                GL11.glScalef(s, s, s);
                GL11.glRotatef(90, 1, 0, 0);
                GL11.glTranslatef(0, 1.495F, 0.52F);
                itemEntity.hoverStart = 0.0F;
                RenderItem.renderInFrame = true;
                GL11.glRotatef(180, 0, 1, 1);
                GL11.glTranslatef(.0F, -.2F, -1.395F);
                RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, -0.08385D, 0.0F, 0.0F);
                RenderItem.renderInFrame = false;
                GL11.glPopMatrix();
                GL11.glPopMatrix();
            }
        };
    }
}
