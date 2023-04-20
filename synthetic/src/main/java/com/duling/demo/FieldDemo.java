package com.duling.demo;

public class FieldDemo {
    void say(){
        System.out.println("hello");
    }

    class FieldDemoInner{
//        编译器会自动添加一个字段指向外部类
//        FieldDemo this$0;
//        这里调用外部类的实例方法，没用对象 注：不调用外部类的实例方法也会存在这个this$0
        void sayHello(){
            say();
        }

    }
}
