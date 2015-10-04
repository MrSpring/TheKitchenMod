package dk.mrspring.kitchen.item.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.item.ItemMuffin;
import net.minecraft.item.ItemStack;

/**
 * Created by Konrad on 09-08-2015.
 */
@SideOnly(Side.CLIENT)
public class ItemRenderMuffin extends ColorHandler
{
    public static final ColorHandler COLOR_HANDLER = new ItemRenderMuffin();

    @Override
    String getIdentifierFromStack(ItemStack stack)
    {
        return ItemMuffin.getMuffinType(stack);
    }

    @Override
    public void loadDefaults()
    {
        this.registerColor("vanilla", 0xDFA037);
    }
}
