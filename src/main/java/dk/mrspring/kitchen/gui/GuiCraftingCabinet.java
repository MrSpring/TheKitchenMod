package dk.mrspring.kitchen.gui;

import dk.mrspring.kitchen.gui.container.ContainerCraftingCabinet;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created on 20-09-2015 for TheKitchenMod.
 */
public class GuiCraftingCabinet extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("textures/gui/container/crafting_table.png");

    public GuiCraftingCabinet(InventoryPlayer player, World world, int x, int y, int z)
    {
        super(new ContainerCraftingCabinet(player, world, x, y, z));
    }

    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString(I18n.format("container.crafting"), 28, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}
