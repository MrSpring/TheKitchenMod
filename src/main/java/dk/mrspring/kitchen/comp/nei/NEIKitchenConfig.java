package dk.mrspring.kitchen.comp.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import dk.mrspring.kitchen.ModInfo;

/**
 * Created by Konrad on 11-03-2015.
 */
public class NEIKitchenConfig implements IConfigureNEI
{
    @Override
    public void loadConfig()
    {
//        API.;
    }

    @Override
    public String getName()
    {
        return ModInfo.name;
    }

    @Override
    public String getVersion()
    {
        return ModInfo.version;
    }
}
