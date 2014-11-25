package dk.mrspring.kitchen.item;

import com.google.common.collect.Lists;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.config.ComboConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
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
                String comboName = comboCompound.getString("ComboName");

                if (!comboName.equals("none"))
                {
                    ComboConfig.SandwichCombo combo = ModConfig.getComboConfig().getCombo(comboName);
                    if (combo != null)
                        return combo.getRarity();
                }
            }
        }
        return EnumRarity.common;
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
                        healAmount += ModConfig.getSandwichConfig().getHealAmount(ItemStack.loadItemStackFromNBT(layerCompound));
                    }

                    String comboName = item.getTagCompound().getCompoundTag("Combo").getString("ComboName");

                    if (!comboName.equals("none"))
                    {
                        ComboConfig.SandwichCombo combo = ModConfig.getComboConfig().getCombo(comboName);
                        if (combo != null)
                            healAmount += combo.getExtraHealth();
                    }
                }
            }

        System.out.println("Heal Amount: " + healAmount);
        return healAmount;
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        List information = new ArrayList();
        if (par1ItemStack.getTagCompound() != null)
        {
            if (par1ItemStack.getTagCompound().hasKey("SandwichLayers"))
            {
                NBTTagList layersList = par1ItemStack.getTagCompound().getTagList("SandwichLayers", 10);

                if (layersList != null)
                {
                    for (int i = 0; i < layersList.tagCount(); ++i)
                    {
                        NBTTagCompound layerCompound = layersList.getCompoundTagAt(i);
                        information.add(StatCollector.translateToLocal(ItemStack.loadItemStackFromNBT(layerCompound).getDisplayName()));
                    }

                    information = Lists.reverse(information);
                    par3List.addAll(information);

                    String comboName = par1ItemStack.getTagCompound().getCompoundTag("Combo").getString("ComboName");

                    if (!comboName.equals("none"))
                    {
                        ComboConfig.SandwichCombo combo = ModConfig.getComboConfig().getCombo(comboName);
                        if (combo != null)
                        {
                            par3List.add("");
                            par3List.add(combo.getLocalizedName());
                        }
                    }
                }
            } else
                super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        } else super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    }
}
