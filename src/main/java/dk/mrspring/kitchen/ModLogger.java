package dk.mrspring.kitchen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModLogger
{
	// Integers used to choose which type of message to Log
	public static final int INFO = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;
    public static final int DEBUG = 3;

	// Prints a message to the console, obj used if the message is an error and an exception has to be logged
	public static void print(int type, String message, Object... obj)
	{
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

		switch (type)
		{
			case INFO:
				System.out.println("[" + time + "] [TheKitchenMod/INFO]: " + message);
				break;
			case WARNING:
				System.out.println("[" + time + "] [TheKitchenMod/WARNING]: " + message);
				if (obj[0] != null && obj[0] instanceof Exception) ((Exception) obj[0]).printStackTrace();
				break;
			case ERROR:
				System.err.println("[" + time + "] [TheKitchenMod/ERROR]: " + message);
				if (obj[0] != null && obj[0] instanceof Exception) ((Exception) obj[0]).printStackTrace();
				break;
            case DEBUG:
                if (ModConfig.showDebug)
                    System.out.println("[" + time + "] [TheKitchenMod/DEBUG]: " + message);
                break;
            default:
                System.out.println("[" + time + "] [TheKitchenMod/INFO]: " + message);
                break;
		}
	}
}
