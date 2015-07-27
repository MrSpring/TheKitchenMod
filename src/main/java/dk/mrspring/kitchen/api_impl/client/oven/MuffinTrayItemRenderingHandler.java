package dk.mrspring.kitchen.api_impl.client.oven;

import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.api.oven.IOven;
import dk.mrspring.kitchen.api.oven.IOvenItem;
import dk.mrspring.kitchen.api.oven.IOvenItemRenderingHandler;
import dk.mrspring.kitchen.api_impl.common.oven.MuffinTrayItem;
import dk.mrspring.kitchen.model.ModelMuffinTray;
import net.minecraft.client.Minecraft;

/**
 * Created by Konrad on 28-07-2015.
 */
public class MuffinTrayItemRenderingHandler implements IOvenItemRenderingHandler
{
    ModelMuffinTray model = new ModelMuffinTray();

    @Override
    public boolean shouldBeUsed(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
        return item instanceof MuffinTrayItem;
    }

    @Override
    public void renderPreTranslate(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
        if (firstSlot)
        {
            Minecraft.getMinecraft().getTextureManager().bindTexture(ModInfo.toResource("textures/models/muffin_tray.png"));
            model.render(null, 0, 0, 0, 0, 0, 0.0625F, false);
        }
    }

    @Override
    public void render(IOven oven, IOvenItem item, int slot, boolean firstSlot)
    {
//        if (firstSlot)
//        {
//            super.render(oven, item, slot, true);
//        }
    }
}
