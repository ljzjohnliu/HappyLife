package com.ilife.happy.testjava.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TestObserver {

    public static void main(String[] args) {
        baseUse();
//        flowableUse();
//        singleUse();
//        completableUse();
//        maybeUse();
//        disposableUse();
    }

    /**
     * 这种观察者模型不支持背压：当被观察者快速发送大量数据时，下游不会做其他处理，即使数据大量堆积，调用链也不会报MissingBackpressureException,消耗内存过大只会OOM。
     * 所以，当我们使用Observable/Observer的时候，我们需要考虑的是，数据量是不是很大(官方给出以1000个事件为分界线作为参考)。
     */
    public static void baseUse() {

        Observable mObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        });

        Observer mObserver = new Observer<Integer>() {

            //这是新加入的方法，在订阅后发送数据之前，会首先调用这个方法，而Disposable可用于取消订阅
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("baseUse, onSubscribe --------------d isDisposed() = " + d.isDisposed());
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("baseUse, onNext --------------integer = " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("baseUse, onError --------------e = " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("baseUse, onComplete --------------");
            }
        };

        mObservable
                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())//在Android中才能使用
                .subscribe(mObserver);
    }

    /**
     * Flowable是支持背压的，也就是说，一般而言，上游的被观察者会响应下游观察者的数据请求，下游调用request(n)来告诉上游发送多少个数据。
     * 这样避免了大量数据堆积在调用链上，使内存一直处于较低水平。
     * <p>
     * 根据上面的代码的结果输出中可以看到，当我们调用subscription.request(n)方法的时候，不等onSubscribe()中后面的代码执行，就会立刻执行到onNext方法，(这个描述可能有误)
     * 因此，如果你在onNext方法中使用到需要初始化的类时，应当尽量在subscription.request(n)这个方法调用之前做好初始化的工作;
     * 当然，这也不是绝对的，我在测试的时候发现，通过create（）自定义Flowable的时候，即使调用了subscription.request(n)方法，也会等onSubscribe（）方法中后面的代码都执行完之后，
     * 才开始调用onNext。
     * IPS: 尽可能确保在request（）之前已经完成了所有的初始化工作，否则就有空指针的风险。
     */
    public static void flowableUse() {
        /*
        Flowable 创建方式一：Flowable.range
         */
        Flowable.range(0, 10)
                .subscribe(new Subscriber<Integer>() {
                    Subscription sub;

                    //当订阅后，会首先调用这个方法，其实就相当于onStart()，
                    //传入的Subscription s参数可以用于请求数据或者取消订阅
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("flowableUse, onSubscribe -----start-------");
                        sub = s;
                        sub.request(1);
                        System.out.println("flowableUse, onSubscribe ------end--------");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("flowableUse, onNext --------------integer = " + integer);
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("flowableUse, onError --------------t = " + t);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("flowableUse, onComplete -------------- ");
                    }
                });

        /*
        Flowable 创建方式二：Flowable.create
        Flowable虽然可以通过create()来创建，但是你必须指定背压的策略，以保证你创建的Flowable是支持背压的。
         */
        Flowable.create(new FlowableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Throwable {
                                emitter.onNext(1);
                                emitter.onNext(2);
                                emitter.onNext(3);
                                emitter.onNext(4);
                                emitter.onNext(5);
                                emitter.onComplete();
                            }
                        }//需要指定背压策略
                , BackpressureStrategy.BUFFER)
                .subscribe(new Subscriber<Integer>() {
                    Subscription sub;

                    //当订阅后，会首先调用这个方法，其实就相当于onStart()，
                    //传入的Subscription s参数可以用于请求数据或者取消订阅
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("flowableUse, onSubscribe -----start-------");
                        sub = s;
                        sub.request(1);
                        System.out.println("flowableUse, onSubscribe ------end--------");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("flowableUse, onNext --------------integer = " + integer);
                        sub.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("flowableUse, onError --------------t = " + t);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("flowableUse, onComplete -------------- ");
                    }
                });
    }

    /**
     * 要转换成其他类型的被观察者，也是可以使用toFlowable()、toObservable()等方法去转换。
     * single.toObservable();
     * single.toFlowable();
     */
    public static void singleUse() {
        Single single = Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Throwable {
                emitter.onSuccess("test!");
                emitter.onSuccess("error");//错误写法，重复调用也不会处理，因为只会调用一次
            }
        });

        single.subscribe(new SingleObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("singleUse, onSubscribe --------------d = " + d);
            }

            @Override
            public void onSuccess(@NonNull Object o) {
                System.out.println("singleUse, onSuccess --------------o = " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("singleUse, onError --------------e = " + e);
            }
        });
    }

    /**
     * 如果你的观察者连onNext事件都不关心，可以使用Completable，它只有onComplete和onError两个事件：
     * 要转换成其他类型的被观察者，也是可以使用toFlowable()、toObservable()等方法去转换。
     * completable.toObservable();
     * completable.toFlowable();
     */
    public static void completableUse() {
        Completable completable = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter emitter) throws Throwable {
                emitter.onComplete();
            }
        });

        completable.subscribe(new CompletableObserver() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("completableUse, onSubscribe --------------d = " + d);
            }

            @Override
            public void onComplete() {
                System.out.println("completableUse, onComplete -------------- ");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("completableUse, onError --------------e = " + e);
            }
        });
    }

    /**
     * 如果你有一个需求是可能发送一个数据或者不会发送任何数据，这时候你就需要Maybe，它类似于Single和Completable的混合体。
     *  Maybe可能会调用以下其中一种情况（也就是所谓的Maybe）：
     * onSuccess或者onError
     * onComplete或者onError
     * <p>
     * 要转换成其他类型的被观察者，也是可以使用toFlowable()、toObservable()等方法去转换
     * maybe.toObservable();
     * maybe.toFlowable();
     * <p>
     * 这种观察者模式并不用于发送大量数据，而是发送单个数据，也就是说，当你只想要某个事件的结果（true or false)的时候，你可以用这种观察者模式
     */
    public static void maybeUse() {
        Maybe maybe = Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull MaybeEmitter<String> emitter) throws Exception {
                emitter.onSuccess("I am success!");//发送一个数据的情况，或者onError，不需要再调用onComplete(调用了也不会触发onComplete回调方法)
                //e.onComplete();//不需要发送数据的情况，或者onError
            }
        });

//        使用create创建或者just创建
//        maybe = Maybe.just(addValue(1, 2));

        maybe.subscribe(new MaybeObserver() {
            @Override
            public void onSubscribe(@NonNull io.reactivex.disposables.Disposable d) {
                System.out.println("maybeUse, onSubscribe --------------d = " + d);
            }

            @Override
            public void onSuccess(@NonNull Object o) {
                //发送一个数据时，相当于onNext和onComplete，但不会触发另一个方法onComplete
                System.out.println("maybeUse, onSuccess --------------o = " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("maybeUse, onError --------------e = " + e);
            }

            @Override
            public void onComplete() {
                //无数据发送时候的onComplete事件
                System.out.println("maybeUse, onComplete ------------- ");
            }


        });
    }

    /**
     * 事件调度器释放事件
     * CompositeDisposable提供的方法中，都是对事件的管理
     *
     * dispose():释放所有事件
     * clear():释放所有事件，实现同dispose()
     * add():增加某个事件
     * addAll():增加所有事件
     * remove():移除某个事件并释放
     * delete():移除某个事件
     */
    public static void disposableUse() {
        CompositeDisposable mRxEvent = new CompositeDisposable();
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("Hello ljz");
                emitter.onNext("你值得拥有");
                emitter.onNext("取消关注");
                emitter.onNext("但还是要保持微笑");
                emitter.onComplete();
            }
        })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        //对应onNext()
                        System.out.println("disposableUse, 对应onNext() s = " + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        //对应onError()
                        System.out.println("disposableUse, 对应onError() throwable = " + throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Throwable {
                        //对应onComplete()
                        System.out.println("disposableUse, run 对应onComplete ");
                    }
                });
        mRxEvent.add(subscribe);
        mRxEvent.clear();
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
