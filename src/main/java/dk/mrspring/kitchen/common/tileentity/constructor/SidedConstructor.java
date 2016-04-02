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
public class SidedConstructor extends TileEntityConstructor
{
    Class<? extends TileEntity> remoteClass, clientClass;

    public SidedConstructor(Class<? extends TileEntity> remote, Class<? extends TileEntity> client)
    {
        this.remoteClass = remote;
        this.clientClass = client;
    }

    @Override
    public TileEntity construct(World world, int metadata)
    {
        try
        {
            if (world.isRemote) System.out.println("Remote!");
            else System.out.println("Not remote!");
            return world.isRemote ? clientClass.newInstance() : remoteClass.newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void register(String name)
    {
        if (remoteClass != null) GameRegistry.registerTileEntity(remoteClass, name);
        if (clientClass != null) GameRegistry.registerTileEntity(clientClass, name + "-c");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void bindRenderer(TileEntitySpecialRenderer renderer)
    {
        System.out.println("Binding client renderer!");
        if (clientClass != null) ClientRegistry.bindTileEntitySpecialRenderer(clientClass, renderer);
    }
}
