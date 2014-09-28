package dk.mrspring.kitchen.item;

import net.minecraft.item.ItemStack;

public class ItemKnife extends ItemBase
{
	public ItemKnife()
	{
		super("knife", true);
		
		this.setContainerItem(this);
	}

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_)
    {
        return false;
    }
}
