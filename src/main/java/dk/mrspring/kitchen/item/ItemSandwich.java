package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.combo.SandwichCombo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemSandwich extends ItemFood
{
    public ItemSandwich()
    {
        super(0, false);
        this.setMaxStackSize(1);

        this.setUnlocalizedName("sandwich");
        this.setTextureName(ModInfo.modid + ":sandwich");

        this.setCreativeTab(null);
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        if (par1ItemStack.getTagCompound() != null)
        {
            NBTTagCompound comboCompound = par1ItemStack.getTagCompound().getCompoundTag("Combo");
            if (comboCompound != null)
            {
                byte combo = comboCompound.getByte("Id");

                if (combo != 0)
                    if (SandwichCombo.combos[(int) combo] != null)
                        return SandwichCombo.combos[(int) combo].getRarity();
                    else
                        return EnumRarity.common;
                else
                    return EnumRarity.common;
            } else
                return EnumRarity.common;
        } else
            return EnumRarity.common;
    }

    @Override
    protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par1ItemStack.getTagCompound() != null)
        {
            byte combo = par1ItemStack.getTagCompound().getCompoundTag("Combo").getByte("Id");

            if (combo != 0)
                if (SandwichCombo.combos[(int) combo] != null)
                    SandwichCombo.combos[(int) combo].onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
                else
                    super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        }
    }

    @Override
    public int func_150905_g(ItemStack item)
    {
        int healAmount = 0;

        if (item.getTagCompound() != null)
            if (item.getTagCompound().hasKey("SandwichLayers"))
            {
                NBTTagList layersList = item.getTagCompound().getTagList("SandwichLayers", 10);

                if (layersList != null)
                {
                    for (int i = 0; i < layersList.tagCount(); ++i)
                    {
                        NBTTagCompound layerCompound = layersList.getCompoundTagAt(i);
                        healAmount += ((ItemSandwichable) ItemStack.loadItemStackFromNBT(layerCompound).getItem()).getHealAmount();
                    }

                    byte combo = item.getTagCompound().getCompoundTag("Combo").getByte("Id");

                    if (SandwichCombo.combos[(int) combo] != null)
                        healAmount += SandwichCombo.combos[(int) combo].getExtraHeal();
                }
            }
        return healAmount;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (par1ItemStack.getTagCompound()!=null)
        {
            if (par1ItemStack.getTagCompound().hasKey("SandwichLayers"))
            {
                NBTTagList layersList = par1ItemStack.getTagCompound().getTagList("SandwichLayers", 10);

                if (layersList != null)
                {
                    for (int i = 0; i < layersList.tagCount(); ++i)
                    {
                        NBTTagCompound layerCompound = layersList.getCompoundTagAt(i);
                        par3List.add(StatCollector.translateToLocal(ItemStack.loadItemStackFromNBT(layerCompound).getDisplayName()));
                    }

                    byte combo = par1ItemStack.getTagCompound().getCompoundTag("Combo").getByte("Id");

                    if (combo != 0)
                        if (SandwichCombo.combos[(int) combo] != null)
                            SandwichCombo.combos[(int) combo].addCustomInfo(par3List);
                }
            } else
                super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        } else super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    }
}
