package dk.mrspring.kitchen.api.board;

import dk.mrspring.kitchen.api.ISpawner;
import dk.mrspring.kitchen.api.ISpecialTagInfo;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Created by Konrad on 14-05-2015.
 */
public interface ICuttingBoard extends ISpecialTagInfo, ISpawner
{
    boolean rightClicked(ItemStack clicked, EntityPlayer player);

    boolean addLayer(ItemStack layer);

    void clearBoard();

    void resetLayers();

    ItemStack getTopItem();

    ItemStack getBottomItem();

    ItemStack removeTopItem(EntityPlayer player);

    List<ItemStack> getLayers();

    int getLayerCount();

    ItemStack finishBoard();
}
