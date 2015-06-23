package dk.mrspring.kitchen.api;

/**
 * Created by Konrad on 21-06-2015.
 */
public interface ICooking
{
    boolean isCooking();

    int getCookTime();

    int getDoneTime();

    boolean isFinished();
}
