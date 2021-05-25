package com.ilife.happy.testjava;

public class ChildClassB extends BaseClassA {
    String name;

    public ChildClassB() {
        this(0);
        System.out.println("ChildClassB, ----Constructor--- 1");
    }

    public ChildClassB(int type) {
        this(type, "defaultB");
        System.out.println("ChildClassB, ----Constructor--- 2");
    }

    public ChildClassB(int type, String name) {
        super(type);
        System.out.println("ChildClassB, ----Constructor--- 3");
        this.type = type;
        this.name = name;
        init();
    }

    private void init() {
        System.out.println("ChildClassB, ----init--- type = " + type + ", name = " + name);
    }

    @Override
    public String toString() {
        System.out.println("BaseClassB, toString is called! 111 ");
        super.toString();
        System.out.println("BaseClassB, toString is called! 222 ");
        return "BaseClassB is type = " + type + ", name = " + name;
    }
}
