package com.ilife.happy.testjava.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.internal.operators.observable.ObservableFromCallable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.jvm.functions.Function0;

/**
 * Rxjava 的操作符:创建操作
 * 用于创建Observable的操作符
 */
public class TestRxOptCreate {

    public static void main(String[] args) {
//        createUse();
//        fromUse();
//        justUse();
//        deferUse();
//        rangeUse();
        intervalUse();
//        repeatUse();
        timerUse();
    }

    /**
     * 建议你在传递给create方法的函数中检查观察者的isUnsubscribed状态，以便在没有观察者的时候，让你的Observable停止发射数据或者做昂贵的运算。
     */
    public static void createUse() {
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("step1");
                emitter.onNext("step2");
                emitter.onNext("step3");
                emitter.onComplete();
            }
        });

        //如果这里不指定String类型的话，onNext中就是Object！注意这个类型要跟Observable的数据流中类型一致不然会报错！
        Observer observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("createUse, onSubscribe --------------d = " + d);
            }

            @Override
            public void onNext(@NonNull String o) {
                System.out.println("createUse, onNext --------------o = " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("createUse, onError --------------e = " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("createUse, onComplete --------------");
            }
        };

        observable.subscribe(observer);
    }

    /**
     * fromIterable方法参数为实现Iterable接口的类，如List/Map/Set等集合类。
     */
    public static void fromUse() {
        String[] strings = {"a", "b", "c"};
        Observable observable1 = Observable.fromArray(strings);
        Observable observable2 = Observable.fromArray("a", "b", "c");
        Observable observable3 = Observable.fromArray(1, 2, 3);

        List<String> stringList = Arrays.asList(strings);
        Observable observable4 = Observable.fromIterable(stringList);

        //这里为了使用到以上几个被观察者上，这里就不指定数据类型了！
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("fromUse, onSubscribe --------------d = " + d);
            }

            @Override
            public void onNext(@NonNull Object o) {
                System.out.println("fromUse, onNext --------------o = " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("fromUse, onError --------------e = " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("fromUse, onComplete --------------");
            }
        };

        observable1.subscribe(observer);
        observable2.subscribe(observer);
        observable3.subscribe(observer);
        observable4.subscribe(observer);
    }

    /**
     * just重载了多个参数数量不同的方法，最大可带10个参数，just实际上同样是调用的fromArray方法；
     */
    public static void justUse() {
        //注意它的返回类型是Disposable
        Observable.just(1, 2, 3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {
                System.out.println("justUse, accept --------------integer = " + integer);
            }
        });

        //注意它的返回类型是void
        Observable.just(1).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("justUse, onSubscribe --------------d = " + d);
            }

            @Override
            public void onNext(@NonNull Integer o) {
                System.out.println("justUse, onNext --------------o = " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("justUse, onError --------------e = " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("justUse, onComplete --------------");
            }
        });
    }

    /**
     * defer 操作符与create、just、from等操作符一样，是创建类操作符，不过所有与该操作符相关的数据都是在订阅是才生效的。
     * Defer操作符会一直等待直到有观察者订阅它，然后它使用Observable工厂方法生成一个Observable。它对每个观察者都这样做，因此尽管每个订阅者都以为自己订阅的是同一个Observable，事实上每个订阅者获取的是它们自己的单独的数据序列。
     * <p>
     * 在某些情况下，等待直到最后一分钟（就是知道订阅发生时）才生成Observable可以确保Observable包含最新的数据。
     */
    public static void deferUse() {
        //以下create创建的数据在没有被订阅的时候 也没有触发
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                System.out.println("deferUse, subscribe --------start------");
                emitter.onNext(1);
                emitter.onComplete();
                System.out.println("deferUse, subscribe --------end------");
            }
        });

        Observable.defer(new Supplier<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> get() throws Throwable {
                System.out.println("deferUse, get --------------");
                return Observable.just(1);
            }
        })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("deferUse, onSubscribe --------------d = " + d);
                    }

                    @Override
                    public void onNext(@NonNull Integer o) {
                        System.out.println("deferUse, onNext --------------o = " + o);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("deferUse, onError --------------e = " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("deferUse, onComplete --------------");
                    }
                });
    }

    /**
     * Range操作符发射一个范围内的有序整数序列，你可以指定范围的起始和长度。
     * <p>
     * RxJava将这个操作符实现为range函数，它接受两个参数，一个是范围的起始值，一个是范围的数据的数目。如果你将第二个参数设为0，将导致Observable不发射任何数据（如果设置为负数，会抛异常）。
     * <p>
     * range默认不在任何特定的调度器上执行。有一个变体可以通过可选参数指定Scheduler。
     */
    public static void rangeUse() {
        //发送从10开始的整数，发送4个(发到13)
        Observable.range(10, 4)
                .subscribe(integer -> System.out.println("rangeUse, accept --------------integer = " + integer));

        //发送从10开始的长整型数，发送6个(发到15)
        Observable.rangeLong(10, 6)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Throwable {
                        System.out.println("rangeUse, accept --------------aLong = " + aLong);
                    }
                });
    }

    /**
     * 存疑：订阅者回调中并没有执行！！放到app中是按照预期执行的！
     * 创建一个按固定时间间隔发射整数序列的Observable
     * Interval操作符返回一个Observable，它按固定的时间间隔发射一个无限递增的整数序列。
     * RxJava将这个操作符实现为interval方法。它接受一个表示时间间隔的参数和一个表示时间单位的参数。
     */
    public static void intervalUse() {

        //每3秒发个自增整数
//        Observable.interval(3, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Throwable {
//                        System.out.println("intervalUse, accept --------------aLong = " + aLong);
//                    }
//                });

        //初始延时1秒，每3秒发一个自增整数
//        Observable.interval(1, 3, TimeUnit.MILLISECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Throwable {
//                        System.out.println("intervalUse, accept --------------aLong = " + aLong);
//                    }
//                });

        //初始延时2秒，后每1秒发一个从10开始的整数，发5个（发到14）停止
//        Observable.intervalRange(10, 5, 1, 1, TimeUnit.MILLISECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Throwable {
//                        System.out.println("intervalUse, accept --------------aLong = " + aLong);
//                    }
//                });

        Observable.intervalRange(10, 5, 500, 500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("intervalUse, onSubscribe --------------d = " + d);
                    }

                    @Override
                    public void onNext(@NonNull Long o) {
                        System.out.println("intervalUse, onNext --------------o = " + o);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("intervalUse, onError --------------e = " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("intervalUse, onComplete --------------");
                    }
                });
    }

    /**
     * 创建一个发射特定数据重复多次的Observable
     *
     * Repeat重复地发射数据。某些实现允许你重复的发射某个数据序列，还有一些允许你限制重复的次数。
     * RxJava将这个操作符实现为repeat方法。它不是创建一个Observable，而是重复发射原始Observable的数据序列，这个序列或者是无限的，或者通过repeat(n)指定重复次数。
     * repeat操作符默认在trampoline调度器上执行。有一个变体可以通过可选参数指定Scheduler。
     */
    public static void repeatUse() {
        //一直重复
        Observable.fromArray(1, 2, 3, 4).repeat();
        //重复发送5次
        Observable.fromArray(1, 2, 3, 4).repeat(5);
        //重复发送直到符合条件时停止重复
        Observable.fromArray(1, 2, 3, 4).repeatUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Throwable {
                return true;
            }
        })
                .subscribe(integer -> System.out.println("intervalUse, onNext --------------integer = " + integer));
    }

    /**
     * Timer操作符创建一个在给定的时间段之后返回一个特殊值的Observable。
     * RxJava将这个操作符实现为timer函数。
     * timer返回一个Observable，它在延迟一段给定的时间后发射一个简单的数字0。
     * timer操作符默认在computation调度器上执行。有一个变体可以通过可选参数指定Scheduler。
     */
    public static void timerUse() {
        Observable.timer(1, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Throwable {
                        System.out.println("timerUse, accept --------------aLong = " + aLong);
                    }
                });
    }

    public static int addValue(int a, int b) {
        System.out.println("addValue, a is : " + a + ", b is : " + b);
        return a + b;
    }

    public static int secondOpt() {
        System.out.println("secondOpt -------------- ");
        return 0;
    }
}
