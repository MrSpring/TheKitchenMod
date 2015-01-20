package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.TheKitchenMod;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 01-12-2014 for TheKitchenMod.
 */
public class ItemBase extends Item
{
	String modelName = "default";
	
    public ItemBase(String unlocalizedName, String modelName, boolean useCreativeTab)
    {
        super();

        this.setModelName(modelName);
        this.setUnlocalizedName(unlocalizedName);
        if (useCreativeTab)
            this.setCreativeTab(TheKitchenMod.instance.baseTab);
    }
    
    public ItemBase(String unlocalizedName, boolean useCreativeTab)
    {
    	this(unlocalizedName, unlocalizedName, useCreativeTab);
    }
    
    public ItemBase(String unlocalizedName, String modelName)
    {
    	this(unlocalizedName, modelName, true);
    }
    
    public ItemBase(String unlocalizedName)
    {
    	this(unlocalizedName, unlocalizedName, true);
    }
    
    public ItemBase addVariants(String... variantNames)
    {
    	TheKitchenMod.proxy.addVariants(this, variantNames);
    	return this;
    }
    
    public ItemBase setModelName(String modelName)
    {
    	this.modelName = modelName;
    	return this;
    }
    
    public String getModelName()
    {
    	return this.modelName;
    }
}
