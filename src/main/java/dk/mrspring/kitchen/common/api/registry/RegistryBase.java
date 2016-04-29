package dk.mrspring.kitchen.common.api.registry;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 03-04-2016 for TheKitchenMod.
 */
public class RegistryBase<K, V>
{
    Map<K, V> registered = Maps.newHashMap();
    V valueFallback = null;
    K keyFallback = null;

    public V get(K key)
    {
        if (key == null) return getValueFallback(null);
        return registered.getOrDefault(key, getValueFallback(key));
    }

    public K reverseGet(V value)
    {
        for (Map.Entry<K, V> entry : registered.entrySet())
            if (entry.getValue() == value) return entry.getKey();
        return getKeyFallback(value);
    }

    public boolean register(K key, V registering)
    {
        if (!registered.containsKey(key))
        {
            registered.put(key, registering);
            return true;
        } else
        {
            System.err.println(
                    "Tried to register: \"" +
                            key +
                            "\", in: \"" +
                            getClass().getName() +
                            "\", but there is already an item registered with that key!");
            return false;
        }
    }

    public Collection<V> getRegisteredItems()
    {
        return registered.values();
    }

    public V getValueFallback(K key)
    {
        return valueFallback;
    }

    public K getKeyFallback(V value)
    {
        return keyFallback;
    }
}
