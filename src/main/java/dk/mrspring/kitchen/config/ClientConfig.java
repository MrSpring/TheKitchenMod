package dk.mrspring.kitchen.config;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.IItemRenderer;

import java.io.File;

/**
 * Created on 07-12-2015 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ClientConfig extends BaseConfig
{
    public RenderConfig oven_rendering = new RenderConfig();
    public RenderConfig pan_rendering = new RenderConfig();
    public RenderConfig waffle_iron_rendering = new RenderConfig();
    public RenderConfig toaster_rendering = new RenderConfig();
    public RenderConfig plate_rendering = new RenderConfig();
    public RenderConfig kitchen_cabinet_rendering = new RenderConfig();
    public RenderConfig crafting_cabinet_rendering = new RenderConfig();
    public RenderConfig muffin_tray_rendering = new RenderConfig();
    public RenderConfig grinder_rendering = new RenderConfig();

    public ClientConfig()
    {
        oven_rendering = new RenderConfig();
        pan_rendering = new RenderConfig();
        waffle_iron_rendering = new RenderConfig();
        toaster_rendering = new RenderConfig();
        plate_rendering = new RenderConfig();
        kitchen_cabinet_rendering = new RenderConfig();
        crafting_cabinet_rendering = new RenderConfig();
        muffin_tray_rendering = new RenderConfig();
        grinder_rendering = new RenderConfig();
    }

    public ClientConfig(File file, String name)
    {
        super(file, name);
    }

    public static class RenderConfig
    {
        public boolean inventory;
        public boolean entity;
        public boolean first_person;
        public boolean third_person;

        public RenderConfig()
        {
            inventory = entity = first_person = third_person = true;
        }

        public boolean handleType(IItemRenderer.ItemRenderType type)
        {
            switch (type)
            {
                case EQUIPPED:
                    return third_person;
                case EQUIPPED_FIRST_PERSON:
                    return first_person;
                case INVENTORY:
                    return inventory;
                case ENTITY:
                    return entity;
                default:
                    return false;
            }
        }
    }
}
