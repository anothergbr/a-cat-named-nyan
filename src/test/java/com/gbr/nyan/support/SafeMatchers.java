package com.gbr.nyan.support;

import org.hamcrest.Matchers;

public class SafeMatchers {
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(org.hamcrest.Matcher<? super T>... itemMatchers) {
        return Matchers.hasItems(itemMatchers);
    }
}
