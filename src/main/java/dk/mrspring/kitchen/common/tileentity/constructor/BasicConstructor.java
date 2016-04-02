package dk.mrspring.kitchen.common.tileentity.constructor;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created on 31-03-2016 for TheKitchenMod.
 */
public class BasicConstructor extends TileEntityConstructor
{
    Class<? extends TileEntity> tileEntityClass;

    public BasicConstructor(Class<? extends TileEntity> tileEntityClass)
    {
        this.tileEntityClass = tileEntityClass;
    }

    @Override
    public TileEntity construct(World world, int metadata)
    {
        try
        {
            return tileEntityClass.newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void register(String name)
    {
        if (tileEntityClass != null) GameRegistry.registerTileEntity(tileEntityClass, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void bindRenderer(TileEntitySpecialRenderer renderer)
    {
        if (tileEntityClass != null)
            ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }
}
