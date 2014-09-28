package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.ModInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemSandwichable extends ItemBase
{
    private int healOnEaten = 1;
    public boolean hasCustomModel;
    private ModelBase bottomModel;
    private ModelBase topModel;
    public int modelBottomHeight = 1;
    public int modelTopHeight = 1;
    public boolean showInformation = true;

    public ItemSandwichable(String name, String textureName, boolean useCreativeTab, int healAmount)
    {
        super(name, textureName, useCreativeTab);

        this.healOnEaten = healAmount;
    }

    public ItemSandwichable(String name, boolean useCreativeTab, int healAmount)
    {
        this(name, ModInfo.modid + ":" + name, useCreativeTab, healAmount);
    }

    public ItemSandwichable hideInformation()
    {
        this.showInformation = false;
        return this;
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List infoList, boolean p_77624_4_)
    {
        if (this.showInformation)
            infoList.add(StatCollector.translateToLocal("item.sandwichable.sandwichable_msg"));
    }

    public int getHealAmount()
    {
        return this.healOnEaten;
    }

    public ModelBase getBottomModel()
    {
        return this.bottomModel;
    }

    public ModelBase getTopModel()
    {
        return this.topModel;
    }

    public ItemSandwichable setCustomModel(ModelBase top, ModelBase bottom, int modelTopHeight, int modelBottomHeight)
    {
        this.hasCustomModel = true;
        this.topModel = top;
        this.bottomModel = bottom;
        this.modelBottomHeight = modelBottomHeight;
        this.modelTopHeight = modelTopHeight;

        return this;
    }
}
