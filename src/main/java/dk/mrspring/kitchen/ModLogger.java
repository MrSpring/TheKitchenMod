package dk.mrspring.kitchen;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class ModLogger
{
    // Integers used to choose which type of message to Log
    public static final int INFO = 0;
    public static final int WARNING = 1;
    public static final int ERROR = 2;
    public static final int DEBUG = 3;

    private static Logger logger;

    public static void initializeLogger(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    public static void print(int type, String message)
    {
        print(type, message, null);
    }

    public static void print(int type, String message, Throwable error)
    {
        switch (type)
        {
            case INFO:
                info(message, error);
                break;
            case WARNING:
                warn(message, error);
                break;
            case ERROR:
                error(message, error);
                break;
            case DEBUG:
                if (ModConfig.getKitchenConfig().show_console_debug) logger.info(message, error);
                break;
        }
    }

    public static void info(String message)
    {
        info(message, null);
    }

    public static void info(String message, Throwable error)
    {
        logger.info(message, error);
    }

    public static void warn(String message)
    {
        warn(message, null);
    }

    public static void warn(String message, Throwable error)
    {
        logger.warn(message, error);
    }

    public static void error(String message)
    {
        error(message, null);
    }

    public static void error(String message, Throwable error)
    {
        logger.error(message, error);
    }
}