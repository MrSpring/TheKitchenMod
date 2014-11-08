package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.pan.Ingredient;
import dk.mrspring.kitchen.pan.Jam;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public class TileEntityPan extends TileEntity
{
    Ingredient ingredient = Ingredient.getIngredient("empty");
    int cookTime = 0;

    public boolean rightClicked(ItemStack clicked)
    {
        System.out.println(this.getCookTime());
        if (this.getCookTime() >= 400 && this.ingredient != null)
        {
            if (clicked != null)
            {
                if (clicked.getItem() == KitchenItems.jam_jar && clicked.getItemDamage() == 0 && this.ingredient.isJam())
                {
                    Jam result = this.ingredient.getJamResult();
                    ItemStack jar = Kitchen.getJamJarItemStack(result, 6);
                    worldObj.spawnEntityInWorld(new EntityItem(worldObj, this.xCoord, this.yCoord, this.zCoord, jar));
                    this.ingredient = Ingredient.getIngredient("empty");
                    this.cookTime = 0;
                    worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                    return true;
                }
            } else if (!this.ingredient.isJam())
            {
                ItemStack result = this.ingredient.getItemResult();
                worldObj.spawnEntityInWorld(new EntityItem(worldObj, this.xCoord, this.yCoord, this.zCoord, result));
                this.ingredient = Ingredient.getIngredient("empty");
                this.cookTime = 0;
                worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                return false;
            }
        } else if (this.getCookTime() == 0 && clicked != null)
        {
            Ingredient ingredientFromItem = KitchenItems.valueOf(clicked.getItem());
            if (ingredientFromItem != null)
            {
                this.ingredient = ingredientFromItem;
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateEntity()
    {
        if (this.getIngredient() != null && this.worldObj.getBlock(xCoord, yCoord - 1, zCoord) == KitchenBlocks.oven)
            if (this.getIngredient() != Ingredient.getIngredient("empty"))
                if (this.cookTime < 410)
                    this.cookTime++;
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
            this.ingredient = Ingredient.getIngredient(string);
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
}
