package com.ilife.happy.testjava;

public class TestSuper {

    public static void main(String[] args) {
        /**
         * 子类可以赋值给超类，称之为向上转型，这个是自动的。
         * 超类不可以赋值给子类，这个是向下转型，需要我们手动实现。
         * 赋值给超类的子类引用在运行期间将表现出不同的特性，这就是多态。
         *
         * 小类型 可转换为 大类型
         * 大类型 转小类型需要 强制转换
         * 对于存在继承关系的强制类型转换：
         * 子类转换为父类属于向上塑型，可以直接转换
         * 父类转换为子类属于向下塑型，需要强制类型转换，但是不一定成功。成功的条件是这个父类是经过子类向上塑型转换来的
         * 即 ：Father father=new Son(); Son son=(Son)father;
         * 对于不存在继承关系的强制类型转换，一般都是失败的（如果不写转换方法的话）
         * 即：
         *  子类可转为父类，父类不可以转为子类（如果不用强制类型转换）。
         *
         * 1.  子类和父类含有相同的成员变量的时候，访问的是父类的成员变量
         *
         * 2.  子类和父类含有相同的成员方法是，访问的是子类的成员方法
         *
         * 3.  子类和父类含有相同的静态函数和静态方法时，访问的是父类的。
         *
         * 4.  父类不能访问子类特有成员和方法（强制类型转换除外）
         *
         * 也就是说，只有在访问成员方法的时候，才会表现出多态。
         *
         */

        A a = new B();
        //子类和父类含有相同的成员变量
        System.out.println("111 a.str = " + a.str);
        System.out.println("222 a.str = " + ((B) a).str);
        //子类和父类含有相同的静态变量
        System.out.println("111 a.name = " + a.name);
        System.out.println("222 a.name = " + ((B) a).name);
        //子类和父类含有相同的成员方法
        a.fun();
        ((B) a).fun();
        //子类和父类含有相同的静态方法
        a.testFun();
        ((B) a).testFun();
        //子类特有成员变量
//        System.out.println("111 a.inB = " + a.inB);
        System.out.println("222 a.inB = " + ((B) a).inB);
        //子类特有方法
//        a.onlyBFun();
        ((B) a).onlyBFun();

        /**
         * 总结 对象多态时：
         *
         * 1.成员变量：(不涉及覆盖)
         * 编译时: 参考引用变量所属的类中是否有调用的成员变量， 有， 编译通过，没有，编译失败。
         * 运行时: 参考引用变量所属的类中是否有调用的成员变量， 并运行该类所属中的成员变量。
         * 简单的说：编译和运行都参考等号的左边。
         * 2.成员函数（非静态）：
         * 编译时：参考引用变量所属的类中是否有调用的成员变量， 有， 编译通过， 没有，编译失败：
         * 运行时：参考的是对象所属的类中是否有调用的函数。
         * 简单的说：编译看左边， 运行看右边。
         * 3.静态函数， 变量：
         *    编译和运行都是参考左边参数类型！
         *    其实静态方法不存在多态， 静态方法是属于类的，我们说的是对象的多态！静态方法直接用类名调用就好了，
         *    没必要创建对象！
         *    静态的方法只能被静态的方法所覆盖！
         */
    }

}

class A {
    static String name = "name A";
    String str = "A str";

    public void fun() {
        System.out.println("Class A fun is called!");
    }

    public static void testFun() {
        System.out.println("Class A testFun is called!");
    }
}

class B extends A {
    static String name = "name B";
    String str = "B str";
    String inB = "Only in B";

    public void fun() {
        System.out.println("Class B fun is called!");
    }

    public static void testFun() {
        System.out.println("Class B testFun is called!");
    }

    public void onlyBFun() {
        System.out.println("Class B onlyBFun is called!");
    }
}
