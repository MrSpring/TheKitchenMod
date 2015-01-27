package dk.mrspring.kitchen.tileentity.casserole;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Konrad on 27-01-2015.
 */
public interface Layer
{
    public boolean handleRightClick(ItemStack stack);

    public String getTypeName();

    /***
     * Removes the layer from the Casserole.
     * @return Returns items that should be dropped.
     */
    public ItemStack[] removeLayer();

    public boolean canAddLayerOnTop(ItemStack stack);

    /**
     * Renders the layer.
     *
     * @param state The current state of the Casserole, 0: RAW, 1: COOKED, 2: BURNT
     */
    @SideOnly(Side.CLIENT)
    public void render(Casserole.CasseroleState state);

    public Layer loadFromNBT(NBTTagCompound compound);

    public NBTTagCompound writeToNBT(NBTTagCompound compound);

    /**
     * Creates a new instance of the Layer and handles the initial right-click
     *
     * @param stack The ItemStack used to create the Layer. Will be null when loading layers from NBT data!
     * @return Returns a new layer.
     */
    public Layer newInstance(ItemStack stack);
}
