package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.ModConfig;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.pan.Jam;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

/**
 * Created by MrSpring on 25-09-2014 for ModJam4.
 */
public class ItemJamJar extends ItemBase
{
    IIcon[] jamIcon = new IIcon[6];

    public ItemJamJar(String name)
    {
        super(name, true);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        if (metadata == 0)
            return 1;
        else return 2;
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        super.getSubItems(p_150895_1_, p_150895_2_, p_150895_3_);

        if (ModConfig.getKitchenConfig().show_different_jars_in_creative_tab)
            for (Map.Entry<String, Jam> entry : Jam.jams.entrySet())
            {
                if (!entry.getValue().getName().equals("empty"))
                {
                    ItemStack stack = new ItemStack(p_150895_1_, 1, 1);
                    NBTTagCompound jamInfo = new NBTTagCompound();
                    jamInfo.setString("JamType", entry.getValue().getName());
                    jamInfo.setInteger("UsesLeft", 6);
                    stack.setTagInfo("JamInfo", jamInfo);
                    p_150895_3_.add(stack);
                }
            }
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 1 || stack.getItemDamage() == 0)
            return super.getIcon(stack, pass);
        else
        {
            if (stack.stackTagCompound != null)
            {
                NBTTagCompound jamInfo = stack.stackTagCompound.getCompoundTag("JamInfo");
                if (jamInfo != null)
                {
                    int usesLeft = jamInfo.getInteger("UsesLeft");
                    if (usesLeft != 0)
                    {
                        return this.jamIcon[usesLeft - 1];
                    }
                }
            }
            return super.getIcon(stack, pass);
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (par1ItemStack.stackTagCompound != null && par1ItemStack.getItemDamage() != 0)
        {
            NBTTagCompound jamInfo = par1ItemStack.stackTagCompound.getCompoundTag("JamInfo");
            if (jamInfo != null)
            {
                int usesLeft = jamInfo.getInteger("UsesLeft");
                if (usesLeft != 0)
                {
                    par3List.add(StatCollector.translateToLocal("item.jam_jar.uses_left_msg") + ": " + usesLeft);
                }
            }
        }
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        if (par2 == 0 && par1ItemStack.getItemDamage() != 0)
        {
            Jam jam = Jam.getJam("empty");

            if (par1ItemStack.stackTagCompound != null)
            {
                NBTTagCompound jamInfo = par1ItemStack.stackTagCompound.getCompoundTag("JamInfo");
                if (jamInfo != null)
                {
                    jam = Jam.getJam(jamInfo.getString("JamType"));
                }
            }

            return jam.getColor();
        } else return super.getColorFromItemStack(par1ItemStack, par2);
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        for (int i = 0; i < this.jamIcon.length; i++)
            this.jamIcon[i] = par1IconRegister.registerIcon(ModInfo.modid + ":jam_jar_filling_" + (i + 1));
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack)
    {
        if (par1ItemStack.getItemDamage() != 0)
        {
            NBTTagCompound compound = par1ItemStack.getTagCompound();
            if (compound != null)
            {
                NBTTagCompound jamInfo = compound.getCompoundTag("JamInfo");
                if (jamInfo != null)
                {
                    Jam jam = Jam.getJam(jamInfo.getString("JamType"));
                    return StatCollector.translateToLocal("jam." + jam.getName().toLowerCase() + ".name") + " " + StatCollector.translateToLocal("item.jam_jar.filled.name");
                }
            }
        }
        return StatCollector.translateToLocal("item.jam_jar.empty.name");
    }

    @Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        return false;
    }
}