package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.api.ingredient.Ingredient;
import dk.mrspring.kitchen.api.ingredient.IngredientRegistry;
import dk.mrspring.kitchen.pan.Jam;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public class TileEntityPan extends TileEntityTimeable
{
    Ingredient ingredient = IngredientRegistry.getInstance().getIngredient("empty");
    int cookTime = 0;
    boolean isFunctional = true, firstRun = true;

    /**
     * Handles the block being right-clicked. Adds the item if the pan is
     * empty, finishes the current ingredient if it's ready and clicked
     * is null.
     *
     * @param clicked The ItemStack the block is being right-clicked with.
     * @return Returns true if clicked's stack-size should be subtracted.
     */
    public boolean rightClicked(ItemStack clicked, EntityPlayer player)
    {
        if (clicked != null)
        {
            if (this.cookTime >= getDoneTime() && clicked.getItem() == KitchenItems.jam_jar && this.ingredient.isJam() && clicked.getItemDamage() == 0)
            {
                this.finishItem(clicked, player);
                return true;
            } else if (this.ingredient == IngredientRegistry.getInstance().getIngredient("empty"))
            {
                return this.setIngredient(clicked);
            }
        } else
        {
            if (this.cookTime >= getDoneTime())
            {
                this.finishItem(null, player);
                return false;
            }
        }

        return false;
    }

    private void finishItem(ItemStack clicked, EntityPlayer player)
    {
        if (this.cookTime >= getDoneTime())
        {
            ItemStack result = null;
            if (clicked != null && clicked.getItem() == KitchenItems.jam_jar && this.ingredient.isJam())
            {
                Jam resultJam = this.ingredient.getJamResult();
                result = Kitchen.getJamJarItemStack(resultJam, 6);
                clicked.stackSize--;
            } else if (!this.ingredient.isJam())
            {
                result = this.ingredient.getItemResult();
            }
            if (result != null)
            {
                this.cookTime = 0;
                this.ingredient.onIngredientFinished(result, player);
                this.ingredient = IngredientRegistry.getInstance().getIngredient("empty");

                worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, result));
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    /**
     * Sets the pan's ingredient to whatever the item links to.
     *
     * @param clicked The item the pan is being clicked with.
     * @return Returns true if the ingredient was set successfully.
     */
    private boolean setIngredient(ItemStack clicked)
    {
        if (this.ingredient == IngredientRegistry.getInstance().getIngredient("empty") && this.cookTime == 0)
        {
            Ingredient ingredientFromItem = IngredientRegistry.getInstance().getOutput(clicked);
            if (ingredientFromItem != IngredientRegistry.getInstance().getIngredient("empty") && ingredientFromItem.canAdd(clicked))
            {
                this.ingredient = ingredientFromItem;
                ingredientFromItem.onAdded(clicked);
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (this.firstRun)
        {
            this.checkIsFunctional();
            this.firstRun = false;
        }

        if (this.getIngredient() != IngredientRegistry.getInstance().getIngredient("empty") && isFunctional)
        {
            if (this.cookTime < getDoneTime() + 10)
                this.cookTime++;
        } else this.cookTime = 0;
    }

    public void checkIsFunctional()
    {
        if (this.worldObj.getBlock(xCoord, yCoord - 1, zCoord) == KitchenBlocks.oven)
            this.makeFunctional();
        else
            this.makeNonFunctional();
    }

    public void makeNonFunctional()
    {
        this.isFunctional = false;
    }

    public void makeFunctional()
    {
        this.isFunctional = true;
    }

    public int getCookTime()
    {
        return cookTime;
    }

    public Ingredient getIngredient()
    {
        return ingredient;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setString("Ingredient", this.getIngredient().getName());
        compound.setInteger("CookTime", this.getCookTime());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        String string = compound.getString("Ingredient");
        try
        {
            this.ingredient = IngredientRegistry.getInstance().getIngredient(string);
        } catch (IllegalArgumentException e)
        {
            ModLogger.print(ModLogger.WARNING, "There was a problem loading pan @ X:" + this.xCoord + ", Y:" + this.yCoord + ", Z:" + this.zCoord + ", the ingredient " + string + " could not be found!");
            e.printStackTrace();
        }
        this.cookTime = compound.getInteger("CookTime");
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 2, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    @Override
    public int getTime()
    {
        return this.cookTime;
    }

    @Override
    public int getDoneTime()
    {
        return 250;
    }
}
