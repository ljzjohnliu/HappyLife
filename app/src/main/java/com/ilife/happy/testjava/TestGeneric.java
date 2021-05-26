package com.ilife.happy.testjava;

public class TestGeneric {

    public static void main(String[] args) {
        /**
         * 对象strTest只能操作String类型,如果你要操作其他类型，只能额外去创建其他泛型对象intTest。这是个弊端！你的初衷可能未必如此
         */
        TestClass<String> strTest = new TestClass<>();
        strTest.show("I am String!");

        TestClass<Integer> intTest = new TestClass<>();
        intTest.show(1);

        /**
         * 把泛型定义在方法上，可以优雅解决问题以上的弊端
         */
        TestClass2 d = new TestClass2();
        d.show("java");
        d.show(5);
        d.sum("java", new Double(8));

        /**
         * 对于实现类类型不确定的类使用时候，可以指定泛型类型，也可以不指定。
         * 不指定的话，类中方法就是泛型了
         */
        ShowClass showClass = new ShowClass();
        showClass.show("test");

        ShowClass2<String> showClass2 = new ShowClass2<>();
        showClass2.show("test 2");

        ShowClass2<Integer> obj = new ShowClass2<>();
        obj.show(6);

        ShowClass2 showClass3 = new ShowClass2<>();
        showClass3.show("test 3");
        showClass3.show(123);
    }

}

/**
 * 泛型类定义的泛型，在整个类中有效。如果被方法使用，那么泛型类的对象明确要操作的具体类型后，所要操作的类型已经固定了。
 */
class TestClass<T> {
    public void show(T t) {
        System.out.println("TestClass, show t = " + t);
    }
}

/**
 * 泛型方法可以让不同方法操作不同类型，且类型还不确定。
 * 与泛型类不同，泛型方法的类型参数只能在它锁修饰的泛型方法中使用。
 */
class TestClass2 {
    public <T> void show(T t) {
        System.out.println("TestClass2, show t = " + t);
    }

    public <U, T> void sum(U u, T t) {
        System.out.println("TestClass2, sum u = " + u + " version is " + t);
    }
}

interface showInterface<T> {
    public void show(T t);

}

/**
 * 实现类确定了类型
 */
class ShowClass implements showInterface<String> {
    public void show(String t) {
        System.out.println("ShowClass, show:t = " + t);
    }
}

/**
 * 实现类类型不确定
 */
class ShowClass2<T> implements showInterface<T> {
    public void show(T t) {
        System.out.println("ShowClass2, show:t = " + t);
    }
}