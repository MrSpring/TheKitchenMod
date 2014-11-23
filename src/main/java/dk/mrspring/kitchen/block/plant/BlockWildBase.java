package dk.mrspring.kitchen.block.plant;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.ModInfo;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockWildBase extends BlockBush
{
    //	Item drops;
    String drops;

    public BlockWildBase(String nameSuffix, String dropped)
    {
        super(Material.plants);

        this.drops = dropped;

        this.setBlockName("wild_" + nameSuffix);
        this.setBlockTextureName(ModInfo.modid + ":wild_" + nameSuffix);
        this.setTickRandomly(true);
        this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.8F, 0.2F * 3.0F, 0.8F);
        this.setStepSound(soundTypeGrass);
        this.setCreativeTab(null);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return GameRegistry.findItem(drops.split(":")[0], drops.split(":")[1]);
    }

    @Override
    public int quantityDropped(Random p_149745_1_)
    {
        return 2;
    }
}
