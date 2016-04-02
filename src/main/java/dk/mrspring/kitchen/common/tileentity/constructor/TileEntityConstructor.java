package dk.mrspring.kitchen.common.tileentity.constructor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created on 28-03-2016 for TheKitchenMod.
 */
public abstract class TileEntityConstructor
{
    public abstract TileEntity construct(World world, int metadata);

    public abstract void register(String name);

    @SideOnly(Side.CLIENT)
    public void bindRenderer(TileEntitySpecialRenderer renderer)
    {
    }

    public void register(String name, Class<? extends TileEntity> clazz)
    {
        GameRegistry.registerTileEntity(clazz, name);
    }
}
