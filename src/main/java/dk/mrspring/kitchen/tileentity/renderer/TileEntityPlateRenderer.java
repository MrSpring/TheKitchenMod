package dk.mrspring.kitchen.tileentity.renderer;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelPlate;
import dk.mrspring.kitchen.tileentity.TileEntityPlate;
import dk.mrspring.nbtjson.NBTType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TileEntityPlateRenderer extends TileEntitySpecialRenderer
{
    public static final String RENDERING_ON_PLATE = "RenderingOnKitchenPlateInfo", CUSTOM_HEIGHT = "CustomHeight";

    ModelPlate modelPlate;
    ResourceLocation texture;

    public TileEntityPlateRenderer()
    {
        this.modelPlate = new ModelPlate();
        this.texture = new ResourceLocation(ModInfo.modid + ":textures/models/plate.png");
    }

    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
    {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);

        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        int metadata = var1.getBlockMetadata();
        GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);
        this.modelPlate.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0635F);

        GL11.glPopMatrix();

        GL11.glRotatef(metadata * (45F), 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0, -0.7F, 0);
        float s = 1.5F;
        GL11.glScalef(s, s, s);
        renderPlateContents(((TileEntityPlate) var1).getItems());

        GL11.glPopMatrix();
    }

    public static double renderPlateContents(List<ItemStack> stacks)
    {
        GL11.glPushMatrix();
        double yItemOffset = 0;
        for (ItemStack stack : stacks)
        {
            if (stack != null)
            {
                ItemStack itemStack = stack.copy();
                itemStack.stackSize = 1;
                itemStack.setTagInfo(RENDERING_ON_PLATE, new NBTTagCompound());
                renderItem(itemStack, 0, yItemOffset + 1.4, -0.20F);
                NBTTagCompound info = itemStack.getTagCompound().getCompoundTag(RENDERING_ON_PLATE);
                if (info.hasKey(CUSTOM_HEIGHT, NBTType.DOUBLE.getId()))
                    yItemOffset -= info.getDouble(CUSTOM_HEIGHT);
                else yItemOffset -= 0.03;
                itemStack.getTagCompound().removeTag(RENDERING_ON_PLATE);
            }
        }
        GL11.glPopMatrix();
        return -yItemOffset;
    }

    private static void renderItem(ItemStack item, double xOffset, double yOffset, double zOffset)
    {
        if (item != null)
        {
            GL11.glPushMatrix();

            GL11.glTranslated(xOffset, yOffset, zOffset);

            EntityItem itemEntity = new EntityItem(Minecraft.getMinecraft().thePlayer.getEntityWorld(), 0D, 0D, 0D, item);
            itemEntity.hoverStart = 0.0F;
            RenderItem.renderInFrame = true;
            GL11.glRotatef(180, 0, 1, 1);
            GL11.glRotatef(180, 0, 1, 0);
            RenderManager.instance.renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;

            GL11.glPopMatrix();
        }
    }

    private static void renderSandwich(ItemStack item)
    {
        /*GL11.glPushMatrix();
        float scale = 1.0F;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glTranslatef(0, 0F, 0F);
        SandwichRender.renderSandwich(item, null);
        GL11.glPopMatrix();*/ // TODO: FIX
    }
}
