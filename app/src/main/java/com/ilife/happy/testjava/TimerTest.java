package com.ilife.happy.testjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    public static void main(String[] args) {
        List<Student> originList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            originList.add(new Student("学生"+i, i, i%2));
        }
        System.out.println("------------originList.size() = " + originList.size());

        List<Student> waitDealList = new ArrayList<>();
        for (int i = 91; i <= 120; i++) {
            waitDealList.add(new Student("学生"+i, i, i%2));
        }
        System.out.println("------------waitDealList.size() = " + waitDealList.size());

        //todo 现在需要做的是：把waitDealList中的学生加入到originList学生中，但是要去重，期望得到的最终列表应该是学生1～学生120

    }

    public static void testTimer() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {// 任务A
            @Override
            public void run() {
                System.out.println("Timer正在调度 线程：" + Thread.currentThread().getName() + " 执行任务 A 中。。。");
            }
        }, 0, 1000);

        timer.schedule(new TimerTask() {// 任务B
            @Override
            public void run() {
                System.out.println("Timer正在调度 线程：" + Thread.currentThread().getName() + " 执行任务 B 中。。。");
                try {
                    System.out.println("任务B很慢，它让Timer单例线程睡眠3s");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5000, 500);

        try {
            System.out.println("主线程睡眠10s");
            Thread.sleep(10000);
            timer.cancel();
            System.out.println("主线程关闭了Timer的所有任务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
