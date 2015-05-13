package dk.mrspring.kitchen.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 13-05-2015.
 */
public class ItemWaffle extends ItemArmor
{
    private static final ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("WAFFLE", 20, new int[]{0, 0, 0, 0}, 0);

    public ItemWaffle()
    {
        super(MATERIAL, 0, 0);

        this.setUnlocalizedName("waffle");
        this.setTextureName(ModInfo.toTexture("waffle"));
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (player.canEat(false))
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.eat;
    }

    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        --stack.stackSize;
        int healAmount = 5;
        if (stack.hasTagCompound())
            healAmount += (stack.getTagCompound().getTagList("IceCream", 8).tagCount());
        player.getFoodStats().addStats(healAmount, 0.6F);//func_151686_a(this, stack);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        return stack;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return ModInfo.toTexture("textures/empty.png");
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
