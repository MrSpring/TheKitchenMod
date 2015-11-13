package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityMuffinTray;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created on 13-11-2015 for TheKitchenMod.
 */
public class BlockMuffinTray extends BlockContainerBase
{
    public BlockMuffinTray()
    {
        super("muffin_tray", TileEntityMuffinTray.class);

        this.rotationAngles = 4;
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer clicker, int side, float clickX, float clickY, float clickZ)
    {
        if (!world.isRemote)
        {
            TileEntityMuffinTray entity = (TileEntityMuffinTray) world.getTileEntity(x, y, z);
            if (entity.rightClick(clicker.getCurrentEquippedItem(), clicker, clickX, clickZ))
                world.markBlockForUpdate(x, y, z);
            else return false;
            return true;
        } else return false;
    }
}
