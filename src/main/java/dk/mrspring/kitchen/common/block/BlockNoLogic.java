package dk.mrspring.kitchen.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.Kitchen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class BlockNoLogic extends Block
{
    String[] sideNames = new String[6];
    IIcon[] sides = new IIcon[6];

    public BlockNoLogic(String name, String textureName, Material material, CreativeTabs tab)
    {
        super(material);

        this.setBlockName(name);
        this.setBlockTextureName(textureName);

        if (tab != null) this.setCreativeTab(tab);
    }

    public BlockNoLogic(String name, Material material, CreativeTabs tab)
    {
        this(name, String.format("kitchen:%s", name), material, tab);
    }

    public BlockNoLogic(String name, String textureName, CreativeTabs tab)
    {
        this(name, textureName, Material.rock, tab);
    }

    public BlockNoLogic(String name, Material material)
    {
        this(name, material, Kitchen.mainTab);
    }

    public BlockNoLogic(String name, CreativeTabs tab)
    {
        this(name, Material.rock, tab);
    }

    public BlockNoLogic(String name)
    {
        this(name, Material.rock, Kitchen.mainTab);
    }

    @SideOnly(Side.CLIENT)
    public BlockNoLogic setIconForSide(int side, String location)
    {
        if (side >= 0 && side < sideNames.length) sideNames[side] = location;
        return this;
    }

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        super.registerBlockIcons(register);

        for (int i = 0; i < sides.length; i++)
        {
            if (sideNames[i] != null) sides[i] = register.registerIcon(sideNames[i]);
            else sides[i] = blockIcon;
        }
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return sides[side];
    }
}
