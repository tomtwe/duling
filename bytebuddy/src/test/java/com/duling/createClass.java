package com.duling;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

//创建一个类
public class createClass {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Object o = new Object();
        /**
         * new ByteBuddy()很好理解，创建了ByteBuddy类型的一个实例
         * subClass(Object.class)含义是动态创建的类是继承Object类的
         * method(ElementMatchers.isToString())类似一个筛选器，这里选中的是Object类中的toString()方法
         * intercept(FixedValue.value("Hello World ByteBuddy!"))提供了了toString()的实现，这里的实现是返回一个固定的值"Hello World ByteBuddy!"
         * make()触发生成一个新的类
         */
        DynamicType.Unloaded unloaded = new ByteBuddy().subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(FixedValue.value("Hello word"))
                .make();
//        新的类已经被创建出来了，但是还没有被加载到JVM中。这个新的类的表现形式是DynamicType.Unloaded的一个实例，具体地说是DynamicType.Unloaded中包含了新的类的字节码。
        Class loaded = unloaded.load(o.getClass().getClassLoader()).getLoaded();
        System.out.println(loaded.newInstance().toString());


    }
}
