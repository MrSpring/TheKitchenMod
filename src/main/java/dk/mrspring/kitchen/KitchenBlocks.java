package dk.mrspring.kitchen;

import dk.mrspring.kitchen.common.block.BlockContainerBase;
import dk.mrspring.kitchen.common.block.BlockNoLogic;
import net.minecraft.block.Block;

import static dk.mrspring.kitchen.common.util.RegistryUtils.registerBlock;

/**
 * Created on 07-03-2016 for TheKitchenMod.
 */
public class KitchenBlocks
{
    protected KitchenBlocks()
    {
    }

    public final Block tiles = new BlockNoLogic("tiles");
    public final Block oven = new BlockContainerBase("oven", "oven").setRotationAngles(4);

    public void register()
    {
        registerBlock(tiles);
        registerBlock(oven);
    }
}
