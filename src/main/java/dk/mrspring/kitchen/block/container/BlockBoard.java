package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockBoard extends BlockContainerBase
{
    public BlockBoard()
    {
        super("board", "minecraft:planks_oak");

        this.setStepSound(soundTypeWood);
        this.setHardness(2.0F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        TileEntityBoard entity = (TileEntityBoard) world.getTileEntity(x, y, z);

        if (!world.isRemote)
        {
            if (!activator.isSneaking())
            {
                if (activator.getCurrentEquippedItem() != null)
                {
                    if (entity.addLayer(activator.getCurrentEquippedItem()))
                    {
                        --activator.getCurrentEquippedItem().stackSize;
                        world.markBlockForUpdate(x, y, z);
                        return true;
                    }
                } else
                {
                    ItemStack removedItemStack = entity.removeTopItem();
                    if (removedItemStack != null)
                    {
                        world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, removedItemStack));
                        world.markBlockForUpdate(x, y, z);
                        return true;
                    }
                }
            } else
            {
                ItemStack sandwich = entity.finishSandwich();
                if (sandwich != null)
                {
                    world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, sandwich));
                    world.markBlockForUpdate(x, y, z);
                    return true;
                }
            }
        }

        return false;
    }



		/*if (!world.isRemote)
        {
			if (!activator.isSneaking())
			{
				if (activator.getCurrentEquippedItem() != null)
				{
					if (activator.getCurrentEquippedItem().getItem() instanceof ItemSandwichable)
					{
						if (entity.addLayer((ItemSandwichable) activator.getCurrentEquippedItem().getItem()))
						{
							--activator.getCurrentEquippedItem().stackSize;
							world.markBlockForUpdate(x, y, z);
							return true;
						}
						else return false;
					}
					else return false;
				}
				else
				{
					if (entity.removeTopLayer())
					{
						world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, entity.getLastRemoved()));
						world.markBlockForUpdate(x, y, z);
						return true;
					}
					else return false;
				}
			}
			else
			{
				if (activator.getCurrentEquippedItem() == null)
				{
					if (entity.isAcceptableSandwich())
					{
						ItemStack[] itemsFromEntity = entity.getLayers();
						
						ItemStack item = GameRegistry.findItemStack(ModInfo.modid, "sandwich", 1);
						
						NBTTagList layersList = new NBTTagList();
						
						for (int i = 0; i < itemsFromEntity.length && itemsFromEntity[i] != null; ++i)
						{
							NBTTagCompound layerCompound = new NBTTagCompound();
							itemsFromEntity[i].writeToNBT(layerCompound);
							layersList.appendTag(layerCompound);
						}
						
						item.setTagInfo("SandwichLayers", layersList);
						
						
						NBTTagCompound comboCompound = new NBTTagCompound();
						byte combo = 0;

						for (int i = 1; i < SandwichCombo.combos.length && SandwichCombo.combos[i] != null; ++i)
							if (SandwichCombo.combos[i].matches(item))
								combo = (byte) i;
						
						comboCompound.setByte("Id", combo);
						item.setTagInfo("Combo", comboCompound);
						
						world.spawnEntityInWorld(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, item));
						entity.resetLayers();
						
						world.markBlockForUpdate(x, y, z);
						
						return true;
					}
					else return false;
				}
				else return false;
			}
		}
		else return true;*/

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_)
    {
        TileEntityBoard tileEntityBoard = (TileEntityBoard) world.getTileEntity(x, y, z);

        if (tileEntityBoard != null)
        {
            List<ItemStack> stacks = tileEntityBoard.getLayers();

            for (ItemStack item : stacks)
            {
                if (item != null)
                {
                    Random random = new Random();

                    float xRandPos = random.nextFloat() * 0.8F + 0.1F;
                    float yRandPos = random.nextFloat() * 0.8F + 0.1F;
                    float zRandPos = random.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, x + xRandPos, y + yRandPos, z + zRandPos, item);

                    entityItem.motionX = random.nextGaussian() * 0.05F;
                    entityItem.motionY = random.nextGaussian() * 0.05F + 0.2F;
                    entityItem.motionZ = random.nextGaussian() * 0.05F;

                    world.spawnEntityInWorld(entityItem);
                }
            }
        }

        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata == 0)
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F * 3, 1.0F - 0.0625F, 0.0625F * 2, 1.0F - (0.0625F * 3));
        else if (metadata == 1)
            this.setBlockBounds(0.0625F * 3, 0.0F, 0.0625F, 1.0F - (0.0625F * 3), 0.0625F * 2, 1.0F - 0.0625F);
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F * 3, 1.0F - 0.0625F, 0.0625F * 2, 1.0F - (0.0625F * 3));
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack placed)
    {
        int direction = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (direction == 0 || direction == 2)
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);

        else if (direction == 1 || direction == 3)
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);

        super.onBlockPlacedBy(world, x, y, z, placer, placed);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileEntityBoard();
    }
}
