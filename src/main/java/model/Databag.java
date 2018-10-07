package model;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by dsanchez on 12/2/17.
 */
public class Databag {
    public static <T extends Map> Object get(T map, Object key) {
        KeyPath path = new KeyPath(key.toString(), map);

        return path.deepestValue();
    }

    public static <T extends Map> Object getOrDefault(T map, String path, Object defaultValue) {
        Object result = Databag.get(map, path);

        if ( result == null) {
            return defaultValue;
        }

        return result;
    }

    public static <T extends Map, R> R getTyped(T map, String path) {

        return (R)Databag.get(map, path);
    }

    public static <T extends Map, R> R getTypedOrDefault(T map, String path, R defaultValue) {
        R result = (R)Databag.get(map, path);

        if ( result == null) {
            return defaultValue;
        }

        return result;
    }

    public static <T extends Map> void pick(T src, T dst, Object ... keys) {

        Databag.copyKeys(src, dst, keys);
    }

    public static <T extends Map> void copyKeys(T src, T dst, Object ... keys) {
        for( Object k : keys) {
            dst.put(
                new KeyPath(k.toString(), src).deepestKey(),
                Databag.get(src, k)
            );
        }
    }

    public static <T extends Map> void renameKey(T map, String name, String newName) {
        if ( name.equals(newName)) {
            return;
        }
        Databag.putStatefull(map, newName, Databag.get(map, name));
        Databag.removeStatefull(map, name);
    }

    public static <T extends Map> void transformValue(T map, String path, Function transformation) {
        Databag.putStatefull(map, path, transformation.apply(Databag.get(map,path)));
    }

    public static <T extends Map> void put(T map, String keyName, Object value) {
        Databag.putStatefull(map, keyName, value);
    }

    public static <T extends Map> void removeStatefull(T map, String keyName) {
        KeyPath path = new KeyPath(keyName, map);

        path.removeKeyFrom();
    }

    public static <T extends Map> void putStatefull(T map, String keyName, Object value) {
        KeyPath path = new KeyPath(keyName, map);

        path.asurePathFrom();
        path.alterWith(value);
    }

    public static <T extends Map> void doOn(T map, String keyName, Consumer doSomething) {
        KeyPath path = new KeyPath(keyName, map);

        List<T> listToIterate;

        try {
            listToIterate = (List<T>)path.deepestValue();

            if ( listToIterate == null) {
                listToIterate = new ArrayList<>();
            }
        } catch (ClassCastException e) {
            listToIterate = new ArrayList<>();
        }

        listToIterate.stream().forEach(doSomething);
    }
}
