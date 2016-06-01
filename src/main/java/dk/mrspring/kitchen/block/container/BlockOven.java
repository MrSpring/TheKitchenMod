package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.tileentity.TileEntityOven;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOven extends BlockContainerBase
{
    public BlockOven()
    {
        super(Material.iron, "oven", TileEntityOven.class);

        this.setTickRandomly(true);
        this.setHardness(4.0F);
        this.setStepSound(Block.soundTypePiston);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_)
    {
        TileEntityOven oven = (TileEntityOven) world.getTileEntity(x, y, z);

        if (oven != null)
        {
            ItemStack[] stacks = oven.getOvenItems();
            oven.spawn(stacks);
        }

        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        TileEntityOven oven = (TileEntityOven) world.getTileEntity(x, y, z);
        if (!world.isRemote)
            return oven.rightClicked(player.getCurrentEquippedItem(), player.isSneaking());
        else return oven.isOpen() || player.isSneaking();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack)
    {
        int rotation = (MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        world.setBlockMetadataWithNotify(x, y, z, rotation, 0);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        TileEntityOven tileEntityOven = (TileEntityOven) world.getTileEntity(x, y, z);

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
        }
    }

    public void updateBlockState(World world, int x, int y, int z)
    {
        TileEntityOven tileEntityOven = (TileEntityOven) world.getTileEntity(x, y, z);

        if (tileEntityOven.isOpen())
            tileEntityOven.setClosed();
        else
            tileEntityOven.setOpen();
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
