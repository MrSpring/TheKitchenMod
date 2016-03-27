package dk.mrspring.kitchen.client.model;

public class ModelPancake extends ModelBase
{
    public ModelPancake(boolean cooked)
    {
        super(cooked ? "pancake_cooked" : "pancake_raw", 16, 16);

        addBox(0, 6, -3F, 22.5F, -3F, 6, 1, 6);
    }
}
