package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;
import dk.mrspring.kitchen.KitchenBlocks;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.recipe.KnifeRecipes;
import dk.mrspring.kitchen.util.ItemUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created on 21-09-2015 for TheKitchenMod.
 */
public class KnifeCraftingHandler extends SimpleCraftingHandler
{
    int ticks = 0;
    final int TICKS_PER_FRAME = 2, FRAMES = 10;

    public KnifeCraftingHandler()
    {
        super("kitchen.knife", "tile.board.name", KnifeRecipes.instance().getRecipes(), new ItemStack(KitchenBlocks.board), false);

        this.drawOtherStack = false;
        inputX = 24 + 15;
        inputY = 10 + 14;
    }

    @Override
    public void loadUsageRecipes(ItemStack output)
    {
        System.out.println("Loading for: " + ItemUtils.name(output));
        if (ItemUtils.item(output, KitchenItems.knife)||ItemUtils.item(output, KitchenBlocks.board))
            this.loadAllRecipes();
        else super.loadUsageRecipes(output);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        ticks++;
        if (ticks >= TICKS_PER_FRAME * FRAMES) ticks = 0;
    }

    @Override
    public String getGuiTexture()
    {
        return ModInfo.toTexture("textures/gui/nei/knife.png");
    }

    @Override
    public void drawExtras(int recipe)
    {
        super.drawExtras(recipe);

        this.drawTexturedModalRect(48, 14, ticks / TICKS_PER_FRAME * 24, 166, 24, 24);
    }

    @Override
    public TemplateRecipeHandler newInstance()
    {
        return new KnifeCraftingHandler();
    }
}
