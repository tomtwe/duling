package com.duling.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * synthetic关键字
 */
public class Main {
    public static void main(String[] args) {
//        fileDemoTest();
//        methodDemoTest();
        constructorDemoTest();
    }

    public static void fileDemoTest(){
        Field[] fields = FieldDemo.FieldDemoInner.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName()+" "+field.isSynthetic());//this$0 true
//            为什么会出现这个结果，因为在内部类中可以直接调用外部类的实例方法，这是违反Java语法的
//            但是实际上是不出现错误的，因为Java 编译器在编译时为自动给内部类添加一个field,指向外部类

        }

    }

    public static void methodDemoTest(){
        Method[] declaredMethods = MethodDemo.MethodInnerDemo.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName()+" "+declaredMethod.isSynthetic());
//            access$000 true
//            access$002 true
//            外部类访问内部类的private方法，Java编译器会默认生成get set方法
        }
    }

    public static void constructorDemoTest(){
        Constructor<?>[] declaredConstructors = ConstructorDemo.ConstructorInnerDemo.class.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            System.out.println(declaredConstructor.getName()+" "+declaredConstructor.isSynthetic());
            System.out.println("==================");
            System.out.println(declaredConstructor.getModifiers());//输出修饰符
            /**
             * com.duling.demo.ConstructorDemo$ConstructorInnerDemo false
             * ==================
             * 2    指的时private
             * com.duling.demo.ConstructorDemo$ConstructorInnerDemo true
             * ==================
             * 4096  指的synthetic
             */

        }
    }

}
