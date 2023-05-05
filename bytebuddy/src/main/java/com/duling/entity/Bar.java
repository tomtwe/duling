package com.duling.entity;

import net.bytebuddy.implementation.bind.annotation.BindingPriority;

public class Bar {
    @BindingPriority(3)
    public static String sayHelloBar() {
        return "Holla in Bar!";
    }

    @BindingPriority(2)
    public static String sayBar() {
        return "bar";
    }
}
