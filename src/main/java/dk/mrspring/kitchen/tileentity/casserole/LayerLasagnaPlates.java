package dk.mrspring.kitchen.tileentity.casserole;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.model.ModelLasagnaPlates;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

/**
 * Created by Konrad on 27-01-2015.
 */
public class LayerLasagnaPlates implements Layer
{
    int plates = 0;

    @SideOnly(Side.CLIENT)
    ModelLasagnaPlates model = new ModelLasagnaPlates();

    @Override
    public boolean handleRightClick(ItemStack stack)
    {
        if (stack.getItem() == KitchenItems.lasagna_plate)
        {
            int beforeStack = plates;
            this.plates += Math.min(stack.stackSize, 3 - this.plates);
            stack.stackSize -= (plates - beforeStack);
            return true;
        } else return false;
    }

    @Override
    public String getTypeName()
    {
        return "LasagnaPlates";
    }

    @Override
    public String getTranslatedName()
    {
        return StatCollector.translateToLocal("item.lasagna_plate.name");
    }

    @Override
    public ItemStack[] removeLayer()
    {
        if (plates > 0)
        {
            ItemStack stack = new ItemStack(KitchenItems.lasagna_plate, plates);
            return new ItemStack[]{stack};
        } else return new ItemStack[0];
    }

    @Override
    public boolean canAddLayerOnTop(ItemStack stack)
    {
        return plates >= 3;
    }

    @Override
    public void render(Casserole.CasseroleState state)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("kitchen", "textures/models/ice_cream_cone.png"));
        model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, plates);
        GL11.glPopMatrix();
    }

    @Override
    public Layer loadFromNBT(NBTTagCompound compound)
    {
        LayerLasagnaPlates layer = new LayerLasagnaPlates();
        layer.plates = compound.getInteger("PlateCount");
        return layer;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("PlateCount", this.plates);
        return compound;
    }

    @Override
    public Layer newInstance(ItemStack stack)
    {
        Layer layer = new LayerLasagnaPlates();
        if (stack != null)
            layer.handleRightClick(stack);
        return layer;
    }
}
