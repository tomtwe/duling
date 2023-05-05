package com.duling.demo;

import com.duling.entity.Bar;
import com.duling.entity.Foo;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Test {
    private static Object o  = new Object();
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
//        System.out.println(testDelegation());//使用ByteBuddy的DSL将所有调用sayHelloFoo()的请求都代理到sayHelloBar()上
        addElement();//给动态生成的类，添加方法和字段

    }

    private static String testDelegation() throws InstantiationException, IllegalAccessException {
        DynamicType.Unloaded<Foo> sayHelloFoo = new ByteBuddy()
                .subclass(Foo.class)
                .method(named("sayHelloFoo").and(isDeclaredBy(Foo.class)).and(returns(String.class)))
                .intercept(MethodDelegation.to(Bar.class))
                .make();

        return sayHelloFoo.load(Test.class.getClassLoader()).getLoaded().newInstance().sayHelloFoo();
        /**
         * 调用sayHelloFoo()方法实际上会调用sayHelloBar()方法
         *
         * ByteBuddy怎么知道该调用Bar.class中的哪个方法？ByteBuddy根据方法签名、返回值类型、方法名、注解的顺序来匹配方法（越后面的优先级越高）。
         *
         * sayHelloFoo()方法和sayHelloBar()方法的方法名不一样，但是它们有相同的方法签名和返回值类型。
         *
         * 如果在Bar.class中有超过一个可调用的方法的签名和返回类型一致，
         * 我们可以使用@BindingPriority来解决冲突。@BindingPriority有一个整型参数-这个值越大优先级越高。因此，在下面的代码片段中sayHelloBar()方法将会被调用：
         */
    }

    private static void addElement() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Class<?> type = new ByteBuddy()
                .subclass(Object.class)
                .name("MyClassName")
                .defineMethod("custom", String.class, Modifier.PUBLIC)
                .intercept(MethodDelegation.to(Bar.class))
                .defineField("x", String.class, Modifier.PUBLIC)
                .make()
                .load(
                        Test.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Method m = type.getDeclaredMethod("custom", null);
        System.out.println(m.invoke(type.newInstance()));
        System.out.println(type.getDeclaredField("x"));
    }
}
