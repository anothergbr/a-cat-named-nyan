package com.gbr.nyan.support;

import java.util.ArrayList;
import java.util.List;

public class Iterables {
    public static <T> List<T> toList(Iterable<T> items) {
        List<T> listToReturn = new ArrayList<>();
        items.forEach(listToReturn::add);
        return listToReturn;
    }
}
