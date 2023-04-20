package com.duling.demo;

public class MethodDemo {

    class MethodInnerDemo{
        private String inner;
    }
    public void setInner(String s){
        new MethodInnerDemo().inner = s;
    }
    public String getInner(){
        return new MethodInnerDemo().inner;
    }
}
