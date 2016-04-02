package dk.mrspring.kitchen.client.block.effect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created on 24-03-2016 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public interface IRandomTick
{
    void onTick(World world, int x, int y, int z, Random random);
}
