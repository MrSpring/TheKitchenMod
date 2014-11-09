package dk.mrspring.kitchen.item;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemKnife extends ItemSword
{
    public ItemKnife()
    {
        super(ToolMaterial.WOOD);

        this.setTextureName(ModInfo.modid + ":knife");
        this.setUnlocalizedName("knife");

        this.setCreativeTab(Kitchen.instance.tab);

        this.setContainerItem(this);
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_)
    {
        return false;
    }
}
