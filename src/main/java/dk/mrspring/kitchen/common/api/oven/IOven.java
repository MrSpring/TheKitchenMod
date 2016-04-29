package dk.mrspring.kitchen.common.api.oven;

import dk.mrspring.kitchen.common.api.ISoundPlayer;
import dk.mrspring.kitchen.common.api.ISpawner;
import dk.mrspring.kitchen.common.api.IUpdatable;
import dk.mrspring.kitchen.common.api.oven.item.OvenItemStack;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
public interface IOven extends ISpawner, ISoundPlayer, IUpdatable
{
    int[] getConcurrentEmptySlots(int slot);

    void fillSlots(OvenItemStack stack, int... slots);

    void clearSlots(OvenItemStack stack, int... slots);

    int getSlotCount();
}
