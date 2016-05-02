package dk.mrspring.kitchen.client.api.render.oven.builtin;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.client.api.render.oven.OvenItemRenderer;
import dk.mrspring.kitchen.client.api.render.oven.OvenItemRendererFactory;
import dk.mrspring.kitchen.client.tileentity.TileEntityClientOven;
import dk.mrspring.kitchen.common.util.ItemUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import static dk.mrspring.kitchen.client.util.ClientUtils.*;

/**
 * Created on 29-04-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class BasicItemRendererFactory extends OvenItemRendererFactory
{
    final String ITEM_STACK = "ItemStack";

    @Override
    public OvenItemRenderer make(NBTTagCompound compound)
    {
        if (compound.getString(RENDER_AS).equals(ITEM_STACK))
            return new BasicItemRenderer().loadFrom(compound);
        else return null;
    }

    public static class BasicItemRenderer extends OvenItemRenderer
    {
        final String RENDER_STACK = "RenderItemStack";
        final String TRANSFORMATIONS = "Transforms";
        final String X_OFFSET = "XOffset", Y_OFFSET = "YOffset", Z_OFFSET = "ZOffset";
        final String X_ROTATION = "XRotate", Y_ROTATION = "YRotate", Z_ROTATION = "ZRotate";
        final String X_SCALE = "XScale", Y_SCALE = "YScale", Z_SCALE = "ZScale";

        ItemStack rendering;
        float xOffset = 0F, yOffset = 0F, zOffset = 0F;
        float xRotation = 0F, yRotation = 0F, zRotation = 0F;
        float xScale = 1F, yScale = 1F, zScale = 1F;

        @Override
        public void render(TileEntityClientOven oven)
        {
            renderItemStack(rendering);
        }

        public BasicItemRenderer loadFrom(NBTTagCompound c)
        {
            this.rendering = ItemStack.loadItemStackFromNBT(c.getCompoundTag(RENDER_STACK));
            if (c.hasKey(TRANSFORMATIONS, ItemUtils.COMPOUND))
            {
                NBTTagCompound transforms = c.getCompoundTag(TRANSFORMATIONS);
                this.xOffset = transforms.getFloat(X_OFFSET);
                this.yOffset = transforms.getFloat(Y_OFFSET);
                this.zOffset = transforms.getFloat(Z_OFFSET);

                this.xRotation = transforms.getFloat(X_ROTATION);
                this.yRotation = transforms.getFloat(Y_ROTATION);
                this.zRotation = transforms.getFloat(Z_ROTATION);

                this.xScale = transforms.getFloat(X_SCALE);
                this.yScale = transforms.getFloat(Y_SCALE);
                this.zScale = transforms.getFloat(Z_SCALE);
            }
            return this;
        }
    }
}
