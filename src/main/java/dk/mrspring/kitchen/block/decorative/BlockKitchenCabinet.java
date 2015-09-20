package dk.mrspring.kitchen.block.decorative;

import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityKitchenCabinet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class BlockKitchenCabinet extends BlockContainerBase
{
    public BlockKitchenCabinet()
    {
        super(Material.wood, "kitchen_cabinet", TileEntityKitchenCabinet.class);

        this.setStepSound(Block.soundTypeWood);
        this.setHardness(2.0F);
        this.setResistance(5.0F);

        this.rotationAngles = 8;
        this.enableTimer = false;
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
