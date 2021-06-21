package com.ilife.happy.testjava.rxjava;

import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Rxjava 的操作符:过滤操作符
 * 过滤操作符主要是指对数据源进行选择和过滤的常用操作符。
 * 注意观察操作符返回的数据类型!!!
 *
 * bounce()	事件流只发射规定范围时间内的数据项
 * distinct()	事件流只发射不重复的数据项
 * elementAt()	事件流只发射第N个数据项
 * filter()	事件流只发射符合规定函数的数据项
 * first()	事件流只发射第一个数据项
 * last()	事件流只发射最后一项数据项
 * ignoreElements()	忽略事件流的发射，只发射事件流的终止事件
 * sample()	事件流对指定的时间间隔进行数据项的采样
 * skip()	事件流忽略前N个数据项
 * skipLast()	事件流忽略后N个数据项
 * take()	事件流只发射前N个数据项
 * takeLast()	事件流只发射后N个数据项
 */
public class TestRxOptFilter {

    public static void skipUse() {
        Observable.range(10, 6)
                .skip(1)//表示源发射数据前，跳过多少个。
                .skipLast(1)//skipLast(n)操作表示从流的尾部跳过n个元素
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        System.out.println("skipUse, accept --------------integer = " + integer);
                    }
                });
    }

    /**
     * debounce（去抖动）
     * 在Android开发，通常为了防止用户重复点击而设置标记位，而通过RxJava的debounce操作符可以有效达到该效果。在规定时间内，用户重复点击只有最后一次有效，
     * 代码中，数据源以一定的时间间隔发送A,B,C,D,E。操作符debounce的时间设为1秒，发送A后1.5秒并没有发射其他数据，所以A能成功发射。
     * 发射B后，在1秒之内，又发射了C和D,在D之后的2秒才发射E,所有B、C都失效，只有D有效；而E之后已经没有其他数据流了，所有E有效。
     */
    public static void debounceUse() {
        Observable<String> source = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("A");

                Thread.sleep(1500);
                emitter.onNext("B");

                Thread.sleep(500);
                emitter.onNext("C");

                Thread.sleep(250);
                emitter.onNext("D");

                Thread.sleep(2000);
                emitter.onNext("E");
                emitter.onComplete();
            }
        });

        /**
         * subscribe与blockingSubscribe区别：
         * blockingSubscribe阻塞当前线程并处理那里的incoming事件。
         */
        source.subscribeOn(Schedulers.io())
                .debounce(1, TimeUnit.SECONDS)
                .blockingSubscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        System.out.println("debounceUse, accept --------------s = " + s);
                    }
                });

//        source.subscribeOn(Schedulers.io())
//                .debounce(1, TimeUnit.SECONDS)
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Throwable {
//                        System.out.println("debounceUse, accept --------------s = " + s);
//                    }
//                });
    }

    /**
     * 抑制（过滤掉）重复的数据项
     * Distinct的过滤规则是：只允许还没有发射过的数据项通过。
     * 在某些实现中，有一些变体允许你调整判定两个数据不同(distinct)的标准。还有一些实现只比较一项数据和它的直接前驱，因此只会从序列中过滤掉连续重复的数据。
     */
    public static void distinctUse() {
        Observable.just(2, 3, 4, 4, 2, 1)
//                .distinct()//去掉数据源重复的数据。
                .distinctUntilChanged()//去掉相邻重复数据。
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        System.out.println("distinctUse, accept--------integer = " + integer);
                    }
                });

        /**
         * 这个操作符有一个变体接受一个函数。这个函数根据原始Observable发射的数据项产生一个Key，然后，比较这些Key。如果两个数据的key相同，则只保留最先到达的数据。
         */
        Observable.just(1, 2, 3, true, 4, 10.0f, false, 6.1f)
                .distinct(new Function<Object, String>() {
                    @Override
                    public String apply(Object item) throws Throwable {
//                        System.out.println("distinctUse, apply--------item = " + item);
                        if (item instanceof Integer) {
                            return "I";
                        } else if (item instanceof Boolean) {
                            return "B";
                        } else {
                            return "F";
                        }
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Throwable {
                        System.out.println("distinctUse, accept--------o = " + o);
                    }
                });

        /**
         * 跟distinct(Func1)操作类似，但是判定的key是否和前驱重复。
         */
        Observable.just(1, 2, 3, true, 4, 10.0f, false, 6.1f)
                .distinctUntilChanged(
                item -> {
                    if (item instanceof Integer) {
                        return "I";
                    } else if (item instanceof Boolean) {
                        return "B";
                    } else {
                        return "F";
                    }
                }
        )
                .subscribe(obj -> System.out.println("distinctUse, accept--------obj = " + obj));
    }

    /**
     * 从数据源获取指定位置的元素，从0开始。
     * elementAtOrError：指定元素的位置超过数据长度，则发射异常。
     */
    public static void elementAtUse() {
        Observable.just(2, 4, 3, 1, 5, 8)
                .elementAt(1)
                .subscribe(integer -> System.out.println("elementAtUse accept-------------- integer = " + integer));
    }

    /**
     * filter 过滤
     * 可作用于 Flowable,Observable,Maybe,Single。在filter中返回表示发射该元素，返回false表示过滤该数据。
     */
    public static void filterUse() {
        Observable.range(1, 6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Throwable {
                        if (integer % 2 == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        System.out.println("filterUse accept-------------- integer = " + integer);
                    }
                });

    }

    /**
     * first(第一个)
     * 作用于 Flowable,Observable。发射数据源第一个数据，如果没有则发送默认值。
     * 和firstElement的区别是first返回的是Single，而firstElement返回Maybe。firstOrError在没有数据会返回异常。
     */
    public static void firstUse() {
//        Observable.just("A", "B", "C")
        Observable.empty()
                .first("D")
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object s) throws Throwable {
                        System.out.println("firstUse accept-------------- s = " + s);
                    }
                });

        Observable.empty()
                .firstOrError()
                .subscribe(element -> System.out.println("onSuccess will not be printed!"),
                        error -> System.out.println("onError: " + error));

        Observable.empty()
                .firstOrError()
                .subscribe(new SingleObserver<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("firstUse accept-------------- d = " + d);
                    }

                    @Override
                    public void onSuccess(@NonNull Object o) {
                        System.out.println("firstUse accept-------------- o = " + o);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("firstUse onError-------------- e = " + e);
                    }
                });

        Observable.just("A", 1, true)
                .firstElement()
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(Serializable serializable) throws Throwable {
                        System.out.println("firstUse accept-------------- serializable = " + serializable);
                    }
                });
    }

    /**
     * last、lastElement、lastOrError与fist、firstElement、firstOrError相对应。
     */
    public static void lastUse() {
        Observable.empty()
                .last("D")
                .subscribe(s -> System.out.println("firstUse accept----11---------- s = " + s));

//        Observable.just(1, false, "A")
        Observable.empty()
                .lastOrError()
                .subscribe(element -> System.out.println("firstUse accept-----22--------- element = " + element),
                        error -> System.out.println("firstUse onError----22---------- error = " + error));

        Observable.empty()
                .lastElement()
                .subscribe(element -> System.out.println("firstUse accept---33----------- element = " + element),
                        error -> System.out.println("firstUse onError----33---------- error = " + error));
    }

    /**
     * ignoreElements & ignoreElement（忽略元素）
     * ignoreElements 作用于Flowable、Observable。
     * ignoreElement作用于Maybe、Single。两者都是忽略掉数据，不发射任何数据，返回完成或者错误时间。
     * 这里关注下intervalRange的用法，以下面这个例子说明：从1开始输出5个数据，延迟1秒执行，每隔1秒执行一次：
     */
    public static void ignoreUse() {
        Single<Long> source = Single.just(1).timer(1, TimeUnit.SECONDS);
        Completable completable = source.ignoreElement();
        completable.doOnComplete(() -> System.out.println("Done!"))
                .blockingAwait();
        // 1秒后打印：Donde!

        Observable<Long> source1 = Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS);
        Completable completable1 = source1.ignoreElements();
        completable1.doOnComplete(() -> System.out.println("Done!"))
                .blockingAwait();
        // 六秒后打印：Done!
    }

    /**
     * ofType（过滤类型）
     * 作用于Flowable、Observable、Maybe，过滤选择类型。
     */
    public static void ofTypeUse() {
        Observable<Number> numbers = Observable.just(1, 4.0, 3, 2.71, 2f, 7);
        Observable<Integer> integers = numbers.ofType(Integer.class);
        integers.subscribe((Integer x) -> System.out.print(x+" "));
        //打印:1 3 7
    }

    /**
     * sample
     * 作用于Flowable、Observable，在一个周期内发射最新的数据。
     * sample操作符会在指定的事件内从数据项中采集所需要的数据。
     * 与debounce的区别是，sample是以时间为周期的发射，一秒又一秒内的最新数据。而debounce是最后一个有效数据开始。
     */
    public static void sampleUSe() {
        Observable<String> source = Observable.create(emitter -> {
            emitter.onNext("A");

            Thread.sleep(500);
            emitter.onNext("B");

            Thread.sleep(200);
            emitter.onNext("C");

            Thread.sleep(800);
            emitter.onNext("D");

            Thread.sleep(600);
            emitter.onNext("E");
            emitter.onComplete();
        });

        source.subscribeOn(Schedulers.io())
                .sample(2, TimeUnit.SECONDS)
                .blockingSubscribe(
                        item -> System.out.print(item + " "),
                        Throwable::printStackTrace,
                        () -> System.out.print("onComplete"));
        // 打印： C D onComplete
    }

    /**
     * throttleFirst & throttleLast & throttleWithTimeout & throttleLatest
     * 作用于Flowable、Observable。
     * throttleFirst是指定周期内第一个数据，throttleLast与sample一致。throttleWithTimeout与debounce一致。
     */
    public static void throttleUse() {
        Observable<String> source = Observable.create(emitter -> {
            emitter.onNext("A");

            Thread.sleep(500);
            emitter.onNext("B");

            Thread.sleep(200);
            emitter.onNext("C");

            Thread.sleep(800);
            emitter.onNext("D");

            Thread.sleep(600);
            emitter.onNext("E");
            emitter.onComplete();
        });

        source.subscribeOn(Schedulers.io())
                .throttleFirst(1, TimeUnit.SECONDS)
                .blockingSubscribe(
                        item -> System.out.print(item + " "),
                        Throwable::printStackTrace,
                        () -> System.out.println(" onComplete"));
        //打印:A D onComplete

        source.subscribeOn(Schedulers.io())
                .throttleLast(1, TimeUnit.SECONDS)
                .blockingSubscribe(
                        item -> System.out.print(item + " "),
                        Throwable::printStackTrace,
                        () -> System.out.println(" onComplete"));
        // 打印:C D onComplete

        Observable<String> source2 = Observable.create(emitter -> {
            emitter.onNext("A");

            Thread.sleep(500);
            emitter.onNext("B");

            Thread.sleep(200);
            emitter.onNext("C");

            Thread.sleep(200);
            emitter.onNext("D");

            Thread.sleep(400);
            emitter.onNext("E");

            Thread.sleep(400);
            emitter.onNext("F");

            Thread.sleep(400);
            emitter.onNext("G");

            Thread.sleep(2000);
            emitter.onComplete();
        });
        source2.subscribeOn(Schedulers.io())
                .throttleLatest(1, TimeUnit.SECONDS)
                .blockingSubscribe(
                        item -> Log.e("RxJava", item),
                        Throwable::printStackTrace,
                        () -> Log.e("RxJava", "finished"));
        //打印 A D F G RxJava","finished
    }

    /**
     * take & takeLast
     * 作用于Flowable、Observable。take发射前n个元素。takeLast发射后n个元素。
     */
    public static void takeUse() {
        Observable<Integer> source = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        source.take(4)
                .subscribe(System.out::print);
        //打印:1 2 3 4

        source.takeLast(4)
                .subscribe(System.out::println);
        //打印:7 8 9 10
    }

    /**
     * timeout（超时）
     * 作用于Flowable、Observable、Maybe、Single、Completable。
     * 后一个数据发射未在前一个元素发射后规定时间内发射则返回超时异常。
     */
    public static void timeoutUse() {
        Observable<String> source = Observable.create(emitter -> {
            emitter.onNext("A");

            Thread.sleep(800);
            emitter.onNext("B");

            Thread.sleep(400);
            emitter.onNext("C");

            Thread.sleep(1200);
            emitter.onNext("D");
            emitter.onComplete();
        });

        source.timeout(1, TimeUnit.SECONDS)
                .subscribe(
                        item -> System.out.println("onNext: " + item),
                        error -> System.out.println("onError: " + error),
                        () -> System.out.println("onComplete will be printed!"));
        // 打印:
        // onNext: A
        // onNext: B
        // onNext: C
        // onError: java.util.concurrent.TimeoutException: The source did not signal an event for 1 seconds and has been terminated.
    }

    /**
     * merge/concat
     * merge操作符可以合并两个事件流，如果在merge操作符上增加延时发送的操作，那么就会导致其发射的数据项是无序的，会跟着发射的时间点进行合并。
     * 虽然是将两个事件流合并成一个事件流进行发射，但在最终的一个事件流中，发射出来的却是两次数据流。
     * merge和concat的区别:merge():合并后发射的数据项是无序的​​​​​​，concat():合并后发射的数据项是有序的。
     */
    public static void mergeUse() {
        Observable<String> just1 = Observable.just("A", "B", "C");
        Observable<String> just2 = Observable.just("1", "2", "3");

        Observable
                .concat(just1, just2)
//                .merge(just1, just2)
                .subscribe(new Consumer<Serializable>() {
            @Override
            public void accept(Serializable serializable) throws Exception {
                System.out.println("mergeUse, accept: serializable = " + serializable);
            }
        });
    }

    /**
     * zip操作符是将两个数据流进行指定的函数规则合并。
     */
    public static void zipUse() {
        Observable<String> just1 = Observable.just("A", "B", "C");
        Observable<String> just2 = Observable.just("1", "2", "3");

        Observable.zip(just1, just2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) throws Exception {
                return s + s2;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("zipUse, accept: s = " + s);
            }
        });
    }

    /**
     * startWith()
     * startWith操作符是将另一个数据流合并到原数据流的开头。
     */
    public static void startWithUSe() {
        Observable<String> just1 = Observable.just("A", "B", "C");
        Observable<String> just2 = Observable.just("1", "2", "3");

        just1.startWith(just2).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("startWithUSe, accept: s = " + s);
            }
        });
    }

    /**
     * join操作符是有时间期限的合并操作符。
     * join操作符有三个函数需要设置
     *
     * 第一个函数：规定just2的过期期限
     * 第二个函数：规定just1的过期期限
     * 第三个函数：规定just1和just2的合并规则
     * 由于just2的期限只有3秒的时间，而just2延时1秒发送一次，所以just2只发射了2次，其输出的结果就只能和just2输出的两次进行合并，其输出格式有点类似我们的排列组合。
     */
    public static void joinUse() {
        Observable<String> just1 = Observable.just("A", "B", "C");
        Observable<Long> just2 = Observable.interval(1, TimeUnit.SECONDS);

        just1.join(just2, new Function<String, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(String s) throws Exception {
                return Observable.timer(3, TimeUnit.SECONDS);
            }
        }, new Function<Long, ObservableSource<Long>>() {
            @Override
            public ObservableSource<Long> apply(Long l) throws Exception {
                return Observable.timer(8, TimeUnit.SECONDS);
            }
        }, new BiFunction<String, Long, String>() {
            @Override
            public String apply(String s, Long l) throws Exception {
                return s + l;
            }
        }).blockingSubscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("joinUse, accept: s = " + s);
            }
        });
    }

    public static void main(String[] args) {
//        skipUse();
//        debounceUse();
//        distinctUse();
//        elementAtUse();
//        filterUse();
//        firstUse();
//        lastUse();
//        ignoreUse();
//        ofTypeUse();
//        sampleUSe();
//        throttleUse();
//        takeUse();
//        timeoutUse();
//        mergeUse();
//        zipUse();
//        startWithUSe();
        joinUse();
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
