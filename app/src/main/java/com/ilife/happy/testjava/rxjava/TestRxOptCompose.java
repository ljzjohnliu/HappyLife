package com.ilife.happy.testjava.rxjava;

import android.util.Log;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Rxjava 的操作符:组合操作符
 * 这个页面展示的操作符可用于组合多个Observables。
 *
 * startWith( ) — 在数据序列的开头增加一项数据
 * merge( ) — 将多个Observable合并为一个
 * mergeDelayError( ) — 合并多个Observables，让没有错误的Observable都完成后再发射错误通知
 * zip( ) — 使用一个函数组合多个Observable发射的数据集合，然后再发射这个结果
 * and( ), then( ), and when( ) — (rxjava-joins) 通过模式和计划组合多个Observables发射的数据集合
 * combineLatest( ) — 当两个Observables中的任何一个发射了一个数据时，通过一个指定的函数组合每个Observable发射的最新数据（一共两个数据），然后发射这个函数的结果
 * join( ) and groupJoin( ) — 无论何时，如果一个Observable发射了一个数据项，只要在另一个Observable发射的数据项定义的时间窗口内，就将两个Observable发射的数据合并发射
 * switchOnNext( ) — 将一个发射Observables的Observable转换成另一个Observable，后者发射这些Observables最近发射的数据
 * (rxjava-joins) — 表示这个操作符当前是可选的rxjava-joins包的一部分，还没有包含在标准的RxJava操作符集合里
 */
public class TestRxOptCompose {

    /**
     * startWith()
     * 可作用于Flowable、Observable。将指定数据源合并在另外数据源的开头。
     */
    public static void startWithUse() {
        Observable<String> name = Observable.just("My", "name");
        Observable<String> name2 = Observable.just("is", "Lucas","!");
        name2.startWith(name).subscribe(item -> System.out.print(" " + item));
    }

    /**
     * merge / mergeWith
     * 可作用所有数据源类型，用于合并多个数据源到一个数据源。
     * merge在合并数据源时，如果一个合并发生异常后会立即调用观察者的onError方法，并停止合并。可通过mergeDelayError操作符，将发生的异常留到最后处理。
     */
    public static void mergeUse() {
        Observable<String> name = Observable.just("My", "name");
        Observable<String> name2 = Observable.just("is", "Lucas", "!");

//        Observable.merge(name, name2).subscribe(v -> System.out.print(" " + v));
//        name.mergeWith(name2).subscribe(v -> System.out.print(" " + v));

        Observable<String> error = Observable.error(new NullPointerException("Error!"));

        Observable.mergeDelayError(name, error, name2).subscribe(
                v -> System.out.print(" " + v),
                e -> System.out.print(" " + e.getMessage()));
    }

    /**
     * 可作用于Flowable、Observable、Maybe、Single。
     * 将多个数据源的数据一个一个的合并在一起哇。当其中一个数据源发射完事件之后，若其他数据源还有数据未发射完毕，也会停止。
     */
    public static void zipUse() {
        Observable<String> name = Observable.just("My", "name");
        Observable<String> name2 = Observable.just("is", "Lucas", "!", "haha!");

        name.zipWith(name2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String first, String last) throws Exception {
                return first + last;
            }
        })
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                System.out.print(" " + s);
            }
        });
    }

    /**
     * 可作用于Flowable, Observable。在结合不同数据源时，发射速度快的数据源最新item与较慢的相结合。
     * 如下时间线，Observable-1发射速率快，发射了65，Observable-2才发射了C, 那么两者结合就是C5。
     */
    public static void combineLatestUse() {
        String[] str = {"A", "B", "C", "D", "E"};

        Observable<String> just1 = Observable.interval(1, TimeUnit.SECONDS).map(new Function<Long, String>() {
            @Override
            public String apply(Long aLong) throws Exception {
                return str[(int) (aLong % 5)];
            }
        });

        Observable<Long> just2 = Observable.interval(1, TimeUnit.SECONDS);

        Observable.combineLatest(just1, just2, new BiFunction<String, Long, String>() {
            @Override
            public String apply(String s, Long l) throws Exception {
                return s + l;
            }
        }).blockingSubscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("onNext=" + s);
            }
        });
    }

    public static void switchOnNextUse() {

    }

    public static void main(String[] args) {
//        startWithUse();
//        mergeUse();
//        zipUse();
        combineLatestUse();
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
