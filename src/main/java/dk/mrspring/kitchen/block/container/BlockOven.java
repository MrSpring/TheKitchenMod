package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.block.BlockContainerBase;
import dk.mrspring.kitchen.tileentity.TileEntityOven;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOven extends BlockContainerBase
{
    public BlockOven()
    {
        super(Material.iron, "oven", TileEntityOven.class);

        this.setHardness(4.0F);
        this.setStepSound(Block.soundTypePiston);

        this.rotationAngles = 4;
    }

    @Override
    public void onBlockBroken(EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity instanceof TileEntityOven)
        {
            TileEntityOven oven = (TileEntityOven) entity;
            for (int i = 0; i < oven.getSlotCount(); i++)
            {
                IOvenItem item = oven.getItemAt(i);
                if (item != null && item.canBeRemoved(oven, null, player, i))
                {
                    ItemStack[] drops = item.onRemoved(oven,null,player,i);
                    oven.removeItemAt(i);
                    for (ItemStack stack : drops) spawnItem(stack, world, x, y, z);
                }
            }
        }
        super.onBlockBroken(player, world, x, y, z);
    }

    @Override
    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer activator, int side, float clickX, float clickY, float clickZ)
    {
        world.markBlockForUpdate(x, y, z);

        if (!world.isRemote)
        {
            TileEntityOven tileEntity = (TileEntityOven) world.getTileEntity(x, y, z);
            if (tileEntity.rightClicked(activator.getCurrentEquippedItem(), activator))
                world.markBlockForUpdate(x, y, z);
            else return false;
            return true;
        } else
            return true;
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        // TODO
        /*TileEntityOven tileEntityOven = (TileEntityOven) world.getTileEntity(x, y, z);

        boolean isOpen = tileEntityOven.isOpen();
        boolean hasCoal = tileEntityOven.hasCoal();
        int itemState = tileEntityOven.getItemState();

        int metadata = world.getBlockMetadata(x, y, z);

        if (hasCoal)
            world.spawnParticle("flame", x + 0.2 + (random.nextDouble() * 0.6), y + 0.5, z + 0.2 + (random.nextDouble() * 0.6), 0.0, 0.0, 0.0);

        if (!isOpen)
        {
            switch (itemState)
            {
                case TileEntityOven.RAW:
                    break;
                case TileEntityOven.COOKED:
                {
                    switch (metadata)
                    {
                        case 0:
                            world.spawnParticle("smoke", x + 0.1 + (random.nextDouble() * 0.8), y + 0.8125, z + 0.125, 0.0, 0.0, -0.025);
                            break;
                        case 1:
                            world.spawnParticle("smoke", x + 1.0 - 0.125, y + 0.8125, z + 0.1 + (random.nextDouble() * 0.8), 0.025, 0.0, 0.0);
                            break;
                        case 2:
                            world.spawnParticle("smoke", x + 0.1 + (random.nextDouble() * 0.8), y + 0.8125, z + 1.0 - 0.125, 0.0, 0.0, 0.025);
                            break;
                        case 3:
                            world.spawnParticle("smoke", x + 0.125, y + 0.8125, z + 0.1 + (random.nextDouble() * 0.8), -0.025, 0.0, 0.0);
                            break;
                    }

                }
                break;
                case TileEntityOven.BURNT:
                {
                    switch (metadata)
                    {
                        case 0:
                            world.spawnParticle("smoke", x + 0.1 + (random.nextDouble() * 0.8), y + 0.8125, z + 0.125, 0.0, 0.0, -0.025);
                            world.spawnParticle("flame", x + 0.1 + (random.nextDouble() * 0.8), y + 0.8125, z + 0.1, 0.0, 0.0, 0.0);
                            break;
                        case 1:
                            world.spawnParticle("smoke", x + 1.0 - 0.125, y + 0.8125, z + 0.1 + (random.nextDouble() * 0.8), 0.025, 0.0, 0.0);
                            world.spawnParticle("flame", x + 1.0 - 0.1, y + 0.8125, z + 0.1 + (random.nextDouble() * 0.8), 0.0, 0.0, 0.0);
                            break;
                        case 2:
                            world.spawnParticle("smoke", x + 0.1 + (random.nextDouble() * 0.8), y + 0.8125, z + 1.0 - 0.125, 0.0, 0.0, 0.025);
                            world.spawnParticle("flame", x + 0.1 + (random.nextDouble() * 0.8), y + 0.8125, z + 1.0 - 0.1, 0.0, 0.0, 0.0);
                            break;
                        case 3:
                            world.spawnParticle("smoke", x + 0.125, y + 0.8125, z + 0.1 + (random.nextDouble() * 0.8), -0.025, 0.0, 0.0);
                            world.spawnParticle("flame", x + 0.1, y + 0.8125, z + 0.1 + (random.nextDouble() * 0.8), -0.025, 0.0, 0.0);
                            break;
                    }
                }
                break;
            }
        }*/
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
