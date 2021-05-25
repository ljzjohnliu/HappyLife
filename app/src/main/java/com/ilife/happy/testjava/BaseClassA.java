package com.ilife.happy.testjava;

public class BaseClassA {
    int type;

    public BaseClassA() {
        this(0);
        System.out.println("BaseClassA, ----Constructor--- 1");
    }

    public BaseClassA(int type) {
        System.out.println("BaseClassA, ----Constructor--- 2");
        this.type = type;
        init();
    }

    private void init() {
        System.out.println("BaseClassA, ----init--- type = " + type);
    }

    @Override
    public String toString() {
        System.out.println("BaseClassA, toString is called!");
        return "BaseClassA is type = " + type;
    }
}
