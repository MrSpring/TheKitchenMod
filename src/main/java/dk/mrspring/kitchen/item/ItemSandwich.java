package dk.mrspring.kitchen.item;

import com.google.common.collect.Lists;
import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
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

        this.setAlwaysEdible();
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
                }
            }

        return healAmount;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        List<String> information = new ArrayList<String>();
        if (stack.getTagCompound() != null)
        {
            if (stack.getTagCompound().hasKey("SandwichLayers"))
            {
                NBTTagList layersList = stack.getTagCompound().getTagList("SandwichLayers", 10);

                if (layersList != null)
                {
                    for (int i = 0; i < layersList.tagCount(); ++i)
                    {
                        NBTTagCompound layerCompound = layersList.getCompoundTagAt(i);
                        information.add(StatCollector.translateToLocal(ItemStack.loadItemStackFromNBT(layerCompound).getDisplayName()));
                    }

                    information = Lists.reverse(information);
                    list.addAll(information);
                }
            }
        }
        super.addInformation(stack, player, list, par4);
    }
}
