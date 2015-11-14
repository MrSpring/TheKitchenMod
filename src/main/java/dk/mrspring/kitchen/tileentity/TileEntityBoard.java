package dk.mrspring.kitchen.tileentity;

import dk.mrspring.kitchen.api.board.IBoardItemHandler;
import dk.mrspring.kitchen.api.board.ICuttingBoard;
import dk.mrspring.kitchen.api_impl.common.registry.BoardEventRegistry;
import dk.mrspring.kitchen.util.SandwichUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileEntityBoard extends TileEntityBase implements ICuttingBoard
{
    private List<ItemStack> layers = new ArrayList<ItemStack>();
    private NBTTagCompound specialInfo = new NBTTagCompound();

    /**
     * @param clicked The ItemStack trying to get added to the Board.
     * @param player  The player adding the ItemStack to the Cutting Board.
     * @return Returns true if the ItemStack was added, false if not.
     */
    @Override
    public boolean rightClicked(ItemStack clicked, EntityPlayer player)
    {
        if (clicked != null)
        {
            IBoardItemHandler itemHandler = BoardEventRegistry.instance().getHandlerFor(this, clicked, player);
            if (layers.size() > 0)
            {
                ItemStack topItem = getTopItem();
                IBoardItemHandler topHandler = BoardEventRegistry.instance().getHandlerFor(this, topItem, player);
                if (topHandler.onRightClicked(this, clicked, player))
                    return true;
            }

            if (itemHandler.canAdd(this, clicked, player))
            {
                this.resetSpecialInfo();
                this.addLayer/*layers.add*/(itemHandler.onAdded(this, clicked, player)); // TODO: Set maximum layers. Maybe 20? 15? 10? Do through SandwichableItemHandler
                return true;
            } else return false;
        } else if (player.isSneaking())
        {
            ItemStack finished = this.finishBoard();
            if (finished != null)
                spawnItemInWorld(finished);
            return true;
        } else if (this.getLayerCount() > 0)
        {
            ItemStack topItem = getTopItem();
            IBoardItemHandler topHandler = BoardEventRegistry.instance().getHandlerFor(this, topItem, player);
            if (topHandler.canBeRemoved(this, topItem, player))
            {
                ItemStack dropping = topHandler.onRemoved(this, topItem, player);
                getLayers().remove(getLayerCount() - 1);
                if (dropping != null)
                    this.spawnItemInWorld(dropping);
                return true;
            } else return false;
        } else return false;
    }

    @Override
    public boolean addLayer(ItemStack layer)
    {
        this.getLayers().add(layer);
        return true;
    }

    @Override
    public NBTTagCompound resetSpecialInfo()
    {
        return this.setSpecialInfo(new NBTTagCompound());
    }

    @Override
    public void clearBoard()
    {
        this.resetLayers();
        this.resetSpecialInfo();
    }

    @Override
    public ItemStack getTopItem()
    {
        if (this.getLayers().size() > 0)
            return this.getLayers().get(this.getLayers().size() - 1);
        else return null;
    }

    @Override
    public ItemStack getBottomItem()
    {
        if (getLayerCount() > 0)
            return getLayers().get(0);
        else return null;
    }

    @Override
    public EntityItem spawnItemInWorld(ItemStack stack)
    {
        Random random = new Random();

        float xRandPos = random.nextFloat() * 0.8F + 0.1F;
        float zRandPos = random.nextFloat() * 0.8F + 0.1F;

        System.out.println("Spawning: " + stack);

        EntityItem entityItem = new EntityItem(worldObj, xCoord + xRandPos, yCoord + 1, zCoord + zRandPos, stack);

        entityItem.motionX = random.nextGaussian() * 0.005F;
        entityItem.motionY = random.nextGaussian() * 0.005F + 0.2F;
        entityItem.motionZ = random.nextGaussian() * 0.005F;

        worldObj.spawnEntityInWorld(entityItem);
        return entityItem;
    }

    @Override
    public NBTTagCompound getSpecialInfo()
    {
        if (specialInfo == null)
            this.specialInfo = new NBTTagCompound();

        return specialInfo;
    }

    @Override
    public NBTTagCompound setSpecialInfo(NBTTagCompound specialInfo)
    {
        NBTTagCompound old = (NBTTagCompound) this.getSpecialInfo().copy();
        this.specialInfo = specialInfo;
        return old;
    }

    @Override
    public ItemStack removeTopItem(EntityPlayer player)
    {
        if (getLayerCount() > 0)
        {
            ItemStack topItem = getTopItem();
            IBoardItemHandler itemHandler = BoardEventRegistry.instance().getHandlerFor(this, topItem, player);
            if (itemHandler.canBeRemoved(this, topItem, player))
            {
                ItemStack removed = itemHandler.onRemoved(this, topItem, player);
                getLayers().remove(topItem);
                spawnItemInWorld(removed);
                return topItem;
            }
        }
        return null;
    }

    @Override
    public List<ItemStack> getLayers()
    {
        return layers;
    }

    @Override
    public ItemStack finishBoard() // TODO: Replace with call to Board handler
    {
        System.out.println("Finishing!");
        if (SandwichUtils.isSandwichReady(getLayers())/*isAllSandwichable(getLayers())*/)
        {
            ItemStack finishedSandwich = SandwichUtils.makeSandwich(getLayers().toArray(new ItemStack[getLayerCount()]));
            this.clearBoard();
            return finishedSandwich;
        }
        return null;
    }

    @Override
    public void resetLayers()
    {
        this.layers = new ArrayList<ItemStack>();
    }

    @Override
    public void readDataFromNBT(NBTTagCompound compound)
    {
        this.resetLayers();
        NBTTagList list = compound.getTagList("Items", 10);

        for (int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound layerCompound = list.getCompoundTagAt(i);
            ItemStack layer = ItemStack.loadItemStackFromNBT(layerCompound);

            if (layer != null)
                layers.add(layer);
        }

        this.setSpecialInfo(compound.getCompoundTag("SpecialInfo"));
    }

    @Override
    public void writeDataToNBT(NBTTagCompound compound)
    {
        NBTTagList list = new NBTTagList();
        for (ItemStack layer : this.layers)
        {
            if (layer != null)
            {
                NBTTagCompound layerCompound = new NBTTagCompound();
                layer.writeToNBT(layerCompound);
                list.appendTag(layerCompound);
            }
        }

        compound.setTag("Items", list);
        compound.setTag("SpecialInfo", this.getSpecialInfo());
    }

    @Override
    public int getLayerCount()
    {
        return getLayers().size();
    }
}
