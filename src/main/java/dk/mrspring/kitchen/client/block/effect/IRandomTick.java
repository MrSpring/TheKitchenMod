package dk.mrspring.kitchen.client.block.effect;

import net.minecraft.world.World;

import java.util.Random;

/**
 * Created on 24-03-2016 for TheKitchenMod.
 */
public interface IRandomTick
{
    void onTick(World world, int x, int y, int z, Random random);
}
