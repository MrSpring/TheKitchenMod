package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityCraftingCabinet;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created on 20-09-2015 for TheKitchenMod.
 */
public class BlockCraftingCabinet extends BlockContainerBase
{
    public BlockCraftingCabinet()
    {
        super("crafting_cabinet", true, TileEntityCraftingCabinet.class);

        this.setStepSound(Block.soundTypeWood);
        this.setHardness(2.0F);
        this.setResistance(5.0F);

        this.rotationAngles = 4;
        this.enableTimer = false;
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer clicker, int side, float clickX, float clickY, float clickZ)
    {
        if (!world.isRemote)
            clicker.openGui(Kitchen.instance, 1, world, x, y, z);
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityCraftingCabinet();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }
}
