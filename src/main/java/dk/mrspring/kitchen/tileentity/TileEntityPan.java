package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.ModLogger;
import dk.mrspring.kitchen.api.pan.IFryingPan;
import dk.mrspring.kitchen.api.pan.IIngredient;
import dk.mrspring.kitchen.api_impl.common.IngredientRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import java.util.Random;

/**
 * Created by MrSpring on 09-10-2014 for TheKitchenMod.
 */
public class TileEntityPan extends TileEntityTimeable implements IFryingPan
{
    IIngredient ingredient = null;
    int cookTime = 0;
    boolean isOnOven = false;
    private NBTTagCompound specialInfo = new NBTTagCompound();

    @Override
    public boolean rightClicked(ItemStack clicked, EntityPlayer player) // TODO: Rewrite
    {
        if (getIngredient() != null)
            if (getIngredient().onRightClicked(this, clicked, player))
                return true;

        if (clicked != null)
        {
            IIngredient ingredient = IngredientRegistry.getInstance().getIngredientFor(this, clicked, player);
            if (getIngredient() == null && ingredient.canAdd(this, clicked, player))
            {
                this.ingredient = ingredient;
                this.cookTime = 0;
                getIngredient().onAdded(this, clicked, player);
                return true;
            }
        } else if (getIngredient() != null && this.isFinished())
        {
            System.out.println("Removing");
            if (ingredient.canBeRemoved(this, player))
            {
                System.out.println("Getting result");
                ItemStack result = ingredient.onRemoved(this, null, player);
                if (result != null)
                    spawnItemInWorld(result);
                this.replaceIngredient(null);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setIngredient(IIngredient ingredient)
    {
        if (this.ingredient == null)
            return false;
        this.ingredient = ingredient;
        return true;
    }

    @Override
    public IIngredient replaceIngredient(IIngredient newIngredient)
    {
        IIngredient old = this.getIngredient();
        this.ingredient = newIngredient;
        return old;
    }

    @Override
    public IIngredient getIngredient()
    {
        return ingredient;
    }

    @Override
    public NBTTagCompound getSpecialInfo()
    {
        if (specialInfo == null)
            this.specialInfo = new NBTTagCompound();
        return specialInfo;
    }

    @Override
    public NBTTagCompound resetSpecialInfo()
    {
        NBTTagCompound old = (NBTTagCompound) this.getSpecialInfo().copy();
        this.specialInfo = new NBTTagCompound();
        return old;
    }

    @Override
    public NBTTagCompound setSpecialInfo(NBTTagCompound specialInfo)
    {
        NBTTagCompound old = (NBTTagCompound) this.getSpecialInfo().copy();
        this.specialInfo = specialInfo;
        return old;
    }

    @Override
    public boolean isCooking()
    {
        return this.cookTime > 0;
    }

    @Override
    public int getCookTime()
    {
        return cookTime;
    }

    @Override
    public int getDoneTime()
    {
        return this.getIngredient() != null ? this.getIngredient().getCookTime(this) : 1;
    }

    @Override
    public boolean isFinished()
    {
        return getCookTime() >= getDoneTime();
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (ingredient != null && this.ingredient.readyToCook(this))
        {
            if (cookTime < getDoneTime())
                cookTime++;
        } else cookTime = 0;
    }

    @Override
    public boolean checkIsFunctional()
    {
        Block below = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
        return this.isOnOven = below == KitchenBlocks.oven;
    }

    @Override
    public EntityItem spawnItemInWorld(ItemStack stack)
    {
        if (stack != null)
        {
            Random random = new Random();

            float xRandPos = random.nextFloat() * 0.8F + 0.1F;
            float zRandPos = random.nextFloat() * 0.8F + 0.1F;

            EntityItem entityItem = new EntityItem(worldObj, xCoord + xRandPos, yCoord + 1, zCoord + zRandPos, stack);

            entityItem.motionX = random.nextGaussian() * 0.005F;
            entityItem.motionY = random.nextGaussian() * 0.005F + 0.2F;
            entityItem.motionZ = random.nextGaussian() * 0.005F;

            worldObj.spawnEntityInWorld(entityItem);
            return entityItem;
        }
        return null;
    }

    @Override
    public int getTime()
    {
        return this.getCookTime();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (getIngredient() != null)
            compound.setString("Ingredient", this.getIngredient().getName());
        compound.setInteger("CookTime", this.getCookTime());
        if (this.specialInfo != null)
            compound.setTag("SpecialInfo", this.specialInfo);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        String string = compound.getString("Ingredient");
        if (string != null && !string.isEmpty())
            try
            {
                this.ingredient = IngredientRegistry.getInstance().getIngredientFromName(string);
            } catch (Exception e)
            {
                ModLogger.print(ModLogger.WARNING, "There was a problem loading pan @ X:" + this.xCoord + ", Y:" + this.yCoord + ", Z:" + this.zCoord + ", the ingredient " + string + " could not be found!");
                e.printStackTrace();
            }
        else this.ingredient = null;
        this.cookTime = compound.getInteger("CookTime");
        if (compound.hasKey("SpecialInfo", 10))
            this.setSpecialInfo(compound.getCompoundTag("SpecialInfo"));
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
