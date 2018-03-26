package com.gbr.nyan.support;

import org.hamcrest.Matchers;

class SafeMatchers {
    @SafeVarargs
    @SuppressWarnings("varargs")
    static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(org.hamcrest.Matcher<? super T>... itemMatchers) {
        return Matchers.hasItems(itemMatchers);
    }
}
