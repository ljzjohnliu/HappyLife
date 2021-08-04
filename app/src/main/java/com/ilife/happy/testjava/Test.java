package com.ilife.happy.testjava;

public class Test {

    public static void main(String[] args) {
        /**
         * 1.子类通过super调用父类构造器
         *
         * 子类通过super()调用父类无参构造器；通过super(参数)调用父类有参构造器；如果不写super，子类默认调用父类无参构造器
         *
         * 2.子类创建对象时，父类构造器会先执行。
         *
         * 因为在构造器中super必须放在第一个执行，否则会报错
         */
//        ChildClassB childB1 = new ChildClassB(1, "test");
//        System.out.println("childB1: " + childB1.toString());
//        System.out.println("childB1: " + ((BaseClassA)childB1).toString());

        try {
            String msg = testBack(-1);
            System.out.println("testBack is : " + msg);
        } catch (Exception e) {
            System.out.println("testBack e : " + e);
        }
    }

    public static String testBack(int a) {
        System.out.println("testBack --------1111------- a = " + a);
        if (a > 0) {
            System.out.println("testBack --------2222------- a = " + a);
            return String.valueOf(a);
        } else if (a < 0){
            System.out.println("testBack --------3333------- a = " + a);
            throw new UnsupportedOperationException("Unsupported Api class Type");
        } else {
            System.out.println("testBack --------5555------- a = " + a);
            return "";
        }
    }
}
