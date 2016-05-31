package dk.mrspring.kitchen.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class ModelKitchenCabinet extends ModelBase<ModelKitchenCabinet.Parameters>
{
    ModelPart corner;
    ModelPart leftDoor, rightDoor;

    public ModelKitchenCabinet()
    {
        super("kitchen_cabinet", 64, 64);

        corner = addPart().addBox(15, 0, -6F, 11F, -8F, 14, 13, 2);
        leftDoor = addPart(-6.5F, 12F, -6.5F)
                .addBox(48, 0, -0.5F, 0F, -0.5F, 6, 11, 1)
                .addBox(8, 29, 2.5F, 3F, -1.5F, 2, 2, 1);
        rightDoor = addPart(6.5F, 12F, -6.5F)
                .addBox(0, 0, -5.5F, 0F, -0.5F, 6, 11, 1)
                .addBox(1, 29, -4.5F, 3F, -1.5F, 2, 2, 1);
        addBox(1, 0, -8F, 11F, -6F, 16, 13, 14);
        addBox(0, 28, -8F, 8F, -8F, 16, 3, 16);
    }

    @Override
    public void preRender(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, RenderContext context)
    {
        corner.isHidden = !context.parameters.corner;
        leftDoor.isHidden = rightDoor.isHidden = context.parameters.corner;
    }

    @Override
    public Parameters makeDefaultParameter()
    {
        return new Parameters(false);
    }

    public static class Parameters implements IRenderParameter
    {
        boolean corner = false;

        public Parameters(TileEntity entity)
        {
            this(entity.getBlockMetadata() % 2 > 0);
        }

        public Parameters(boolean corner)
        {
            this.corner = corner;
        }
    }
}