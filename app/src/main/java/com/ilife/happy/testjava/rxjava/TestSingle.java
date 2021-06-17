package com.ilife.happy.testjava.rxjava;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TestSingle {

    public static void main(String[] args) {
//        baseUse();
        /**
         * Single高阶
         * 接下来我们会很深入的讲解Single的用法了。主要介绍Single的操作符，用一星~三星表示重要程度，三星表示很常用，一星表示了解就行。按首字母顺序介绍。
         * subscribeOn操作符[***]
         * 用于指定异步任务的线程，常见的有:
         * Schedulers.computation( )；// 计算线程
         * Schedulers.from(executor);// 自定义
         * Schedulers.immediate();// 当前线程
         * Schedulers.io();// io线程
         * Schedulers.newThread();// 创建新线程
         * Schedulers.trampoline();// 当前线程队列执行
         *
         * observeOn 指定回调所在线程
         * 常见的为，即Android UI线程 AndroidSchedulers.mainThread();
         *
         */
//        composeUse();
        concatUse();
//        createUse();
//        errorUse();
//        mapUse();
//        flatMapUse();
//        flatMapObservableUse();
//        onErrorReturnUse();
//        timeoutUse();
//        zipUse();
    }

    public static void baseUse() {
        // ps:不管是否subscribe()，只要使用just()，addValue()都会执行
        Single.just(addValue(1, 2)).subscribe();

        /**
         * 1.基础用法
         * 通过例子，我们学会了如何简单的传入参数，并且输出结果。但是这里我并不想误导大家，这里仅仅只是RxJava的开始，其中just()方法无论如何都只会在当前线程里执行。所以即使看上去有异步的过程，但其实这是个同步的过程！
         */
        Single.just(addValue(1, 2))
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe, d is : " + d);
                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        System.out.println("onSuccess, integer is : " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError, e is : " + e);
                    }
                });
    }

    /**
     * compose操作符[*]
     * 创建一个自定义的操作符，将某种范型的Single转换为另一种范型一般用不到，示例如下，将Integer的Single转为String Single。
     */
    public static void composeUse() {
        Single.just(addValue(1, 2))
                .compose(new SingleTransformer<Integer, String>() {

                    @Override
                    public @NonNull SingleSource<String> apply(@NonNull Single<Integer> integerSingle) {
                        return integerSingle.map(new Function<Integer, String>() {
                            @Override
                            public String apply(Integer integer) throws Throwable {
                                return String.valueOf(integer + 2);
                            }
                        });
                    }
                })
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe, d is : " + d);
                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        System.out.println("onSuccess, s is : " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError, e is : " + e);
                    }
                });
    }

    /**
     * concat操作符[*]
     * 用来连接多个Single和Observable发射的数据。
     * <p>
     * 仅仅用来连接Single顺序执行的，比如顺序执行检查网络，检查内存，执行任务，注意：如果某个Single调用了onError()会导致被中断。
     */
    public static void concatUse() {
        Single.concat(Single.just(addValue(1, 2)), Single.just(secondOpt()))
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {
                System.out.println("concatUse, accept -------------- integer = " + integer);
            }
        });
    }

    /**
     * create操作符[***]
     * 值得注意的是之前我们使用的just()是一种特殊的create()，它不能指定Schedulers。
     * 只能在当前线程中运行，而create()可以指定Schedulers实现异步处理。且just()不管是否被subscribe()订阅均会被调用，
     * 而create()如果不被订阅是不会被调用的。所以我们通常可以用just()传递简单参数，而用create()处理复杂异步逻辑。
     */
    public static void createUse() {
        // 作用同Single.just(addValue(1, 2));
        Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Integer> emitter) throws Throwable {
                emitter.onSuccess(addValue(1, 2));
            }
        });

        // 常见的示例，这是一个异步操作
        Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Integer> emitter) throws Throwable {
                emitter.onSuccess(addValue(1, 2));
            }
        })
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()) //Android程序才能使用这个
                .subscribe(new SingleObserver<Integer>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("createUse, onSubscribe --------------d = " + d);
                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        System.out.println("createUse, onSuccess --------------integer = " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("createUse, onError --------------e = " + e);
                    }
                });
    }

    /**
     * error操作符[*]
     * 返回一个立即给订阅者发射错误通知的Single，一般用于调试，不常用.
     */
    public static void errorUse() {
        // 如人为让concat中断:
        Single.concat(Single.just(addValue(1, 2)), Single.error(new Throwable("error")))
                .subscribe(new FlowableSubscriber<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        System.out.println("errorUse, onSubscribe --------------s = " + s);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("errorUse, onNext -------------- integer = " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("errorUse, onError --------------t = " + t);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("errorUse, onComplete -------------- ");
                    }
                });
    }

    /**
     * map操作符[***] 用于类型一对一转换，比较简单。
     */
    public static void mapUse() {
        Single.just(1).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Throwable {
                return "x" + integer;
            }
        })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        System.out.println("map, accept --------------s = " + s);
                    }
                });
    }

    /**
     * flatMap操作符[*]
     * flatMap<T,R>基本上等同于map()，唯一的区别在于flatMap的R一般用于返回Observable对象，这样随后的subscribe参数可以使用原始类型。详见代码
     */
    public static void flatMapUse() {
        Single.just(1).flatMap(new Function<Integer, SingleSource<String>>() {
            @Override
            public SingleSource<String> apply(Integer integer) throws Throwable {
                return Single.just(integer + "");
            }
        })
                .subscribe(new SingleObserver<String>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("flatMapUse, onSubscribe --------------d = " + d);
                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        System.out.println("flatMapUse, onSuccess --------------s = " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("flatMapUse, onError --------------e = " + e);
                    }
                });

        Single.just(1).map(new Function<Integer, Single<String>>() {
            @Override
            public Single<String> apply(Integer integer) throws Throwable {
                return Single.just(integer + "");
            }
        })
                .subscribe(new SingleObserver<Single<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Single<String> stringSingle) {
                        System.out.println("flatMapUse, onSuccess2 --------------stringSingle = " + stringSingle);
                        stringSingle.subscribe(new SingleObserver<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull String s) {
                                System.out.println("flatMapUse, onSuccess22 --------------s = " + s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("flatMapUse, onError2 --------------e = " + e);
                    }
                });
    }

    /**
     * flatMapObservable操作符[**]
     * flatMap()和map()类似，区别在于flatMap可以返回多个值，而map只能返回一个。
     * 但在Single中flatMap只能返回Single，几乎等同于map实用性不高。而flatMapObservable就不同了，它支持将Single转化为Observable对象，可以返回多个值。下面这个例子介绍如何将Single转化为Observable。
     */
    public static void flatMapObservableUse() {
        Single.just(1).flatMapObservable(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Throwable {
                return Observable.just("H", "3", "c");
            }
        })
                //subscribe这里使用什么取决与之前操作返回的是什么！！！
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("flatMapObservableUse, onSubscribe --------------d = " + d);
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        System.out.println("flatMapObservableUse, onNext --------------s = " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("flatMapObservableUse, onError --------------e = " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("flatMapObservableUse, onComplete --------------");
                    }
                });
    }

    /**
     * onErrorReturn操作符[***]
     * 相当于try catch中的return，具体意思就是当函数抛出错误的时候给出一个返回值，看代码
     */
    public static void onErrorReturnUse() {
        Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Integer> emitter) throws Throwable {
                emitter.onError(new Throwable("x"));
//                emitter.onSuccess(1);
            }
        })
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Throwable {
                        return 2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        System.out.println("onErrorReturnUse, accept -------------- integer = " + integer);
                    }
                });
    }

    /**
     * timeout操作符[***]
     * 超时操作操作，在指定时间内如果没有调用onSuccess()就判定为失败，且可支持失败的时候调用其他Single()
     */
    public static void timeoutUse() {
        Single.just(1).timeout(1, TimeUnit.MILLISECONDS)
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("timeoutUse, onSubscribe -------------- d = " + d);
                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        System.out.println("timeoutUse, onSuccess -------------- integer = " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("timeoutUse, onError -------------- e = " + e);
                    }
                });

    }

    /**
     * toSingle操作符[*]
     * 将传入一个参数的Observable转换为Single
     * Rxjava3中删除了该操作
     */
    public static void toSingleUse() {
//        Observable.just(1).toSingle();
    }

    /**
     * zip & zipWith操作符[**]
     * 如果说flatMap()是将一个Single变成多个的操作，那么zip刚刚相反，他可以将多个Single整合为一个
     */
    public static void zipUse() {
        Single.zip(Single.just(addValue(1, 2)), Single.just(addValue(2, 3)), new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) throws Throwable {
                System.out.println("zipUse, apply -------------- integer = " + integer + ", integer2 = " + integer2);
                return integer + "," + integer2;
            }
        })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Throwable {
//                        System.out.println("zipUse, accept -------------- s = " + s);
//                    }
//                });
                .subscribe(new BiConsumer<String, Throwable>() {
                    @Override
                    public void accept(String s, Throwable throwable) throws Throwable {
                        System.out.println("zipUse, accept -------------- s = " + s);
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
