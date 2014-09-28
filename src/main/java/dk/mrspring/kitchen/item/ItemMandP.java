package dk.mrspring.kitchen.item;

import net.minecraft.item.ItemStack;

public class ItemMandP extends ItemBase
{
	public ItemMandP()
	{
		super("mortar_and_pestle", true);
		
		this.setContainerItem(this);
	}

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_)
    {
        return false;
    }
}
