package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.item.ItemJamJar;
import dk.mrspring.kitchen.pan.Ingredient;
import dk.mrspring.kitchen.pan.Jam;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrSpring on 25-11-2014 for TheKitchenMod.
 */
public class PanRecipeHandler extends TemplateRecipeHandler
{
    public class PanPair extends CachedRecipe
    {
        public PositionedStack input;
        public PositionedStack output;

        public PanPair(ItemStack input, ItemStack output)
        {
            this.input = new PositionedStack(input, 51, 24);
            this.output = new PositionedStack(output, 111, 24);
        }

        @Override
        public PositionedStack getIngredient()
        {
            return input;
        }

        @Override
        public PositionedStack getResult()
        {
            return output;
        }
    }

    @Override
    public String getGuiTexture()
    {
        return "textures/gui/pan.png";
    }

    @Override
    public String getRecipeName()
    {
        return "Frying Pan";
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "cooking"));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiFurnace.class;
    }

    @Override
    public void loadUsageRecipes(ItemStack inputIngredient)
    {
        Ingredient ingredient = KitchenItems.valueOf(inputIngredient.getItem());
        if (ingredient != null)
        {
            ItemStack output;
            if (ingredient.isJam())
                output = Kitchen.getJamJarItemStack(ingredient.getJamResult(), 6);
            else output = ingredient.getItemResult();
            arecipes.add(new PanPair(inputIngredient, output));
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack outputResult)
    {
        if (outputResult.getItem() instanceof ItemJamJar)
        {
            NBTTagCompound jamInfo = outputResult.getTagCompound().getCompoundTag("JamInfo");
            if (jamInfo != null)
            {
                Jam jam = Jam.getJam(jamInfo.getString("JamName"));
                if (jam != Jam.getJam("empty"))
                {
                    HashMap<String, String> ingredientAndItemRelations = KitchenItems.getIngredientRelations();
                    for (Map.Entry<String, String> entry : ingredientAndItemRelations.entrySet())
                    {
                        Ingredient ingredient = Ingredient.getIngredient(entry.getValue());
                        if (ingredient.isJam())
                            if (ingredient.getJamResult() == jam)
                                arecipes.add(new PanPair(GameRegistry.findItemStack(entry.getKey().split(":")[0], entry.getKey().split(":")[0], 1), outputResult));
                    }
                }
            }
        } else
        {
            String itemName=GameRegistry.findUniqueIdentifierFor(outputResult.getItem()).toString();
            HashMap<String, String> ingredientAndItemRelations = KitchenItems.getIngredientRelations();
            for (Map.Entry<String, String> entry : ingredientAndItemRelations.entrySet())
            {
                Ingredient ingredient = Ingredient.getIngredient(entry.getValue());
                if (!ingredient.isJam())
                {
                    String[] splieItemName=itemName.split(":");
                    if (GameRegistry.findUniqueIdentifierFor(Ingredient.getIngredient(entry.getValue()).getItemResult().getItem()).toString().equals(itemName))
                        arecipes.add(new PanPair(GameRegistry.findItemStack(entry.getKey().split(":")[0], entry.getKey().split(":")[0], 1), outputResult));
                }
            }
        }
    }
}
