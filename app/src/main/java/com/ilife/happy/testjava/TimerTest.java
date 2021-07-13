package com.ilife.happy.testjava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class TimerTest {

    public static void main(String[] args) {
        List<Student> originList = new ArrayList<>();
        for (int i = 1; i <= 100000; i++) {
            originList.add(new Student("学生" + i, i, i % 2));
        }
        System.out.println("------------originList.size() = " + originList.size());

        List<Student> waitDealList = new ArrayList<>();
        for (int i = 60001; i <= 120000; i++) {
            waitDealList.add(new Student("学生" + i, i, i % 2));
        }
        System.out.println("------------waitDealList.size() = " + waitDealList.size());

        //todo 现在需要做的是：把waitDealList中的学生加入到originList学生中，但是要去重，期望得到的最终列表应该是学生1～学生12000，能实现肯定没问题，主要考察时间复杂度问题
//        insertStudents1(originList, waitDealList);
//        insertStudents2(originList, waitDealList);
//        insertStudents3(originList, waitDealList);
//        insertStudents4(originList, waitDealList);
//        insertStudents5(originList, waitDealList);

        iterateList0(originList);
        iterateList1(originList);
        iterateList2(originList);
        iterateList3(originList);
    }

    public static void insertStudents1(List<Student> originList, List<Student> waitJoinList) {
        long startTime = System.currentTimeMillis();
        for (int i = 1; i < waitJoinList.size(); i++) {
            boolean isInOrigin = false;
            for (int j = 1; j < originList.size(); j++) {
                if (waitJoinList.get(i).getName().equals(originList.get(j).getName())) {
                    isInOrigin = true;
                }
            }
            if (!isInOrigin) {
                originList.add(waitJoinList.get(i));
            }
        }
        System.out.println("insertStudents0, cost time : " + (System.currentTimeMillis() - startTime) + ", originList size = " + originList.size());
    }

    public static void insertStudents2(List<Student> originList, List<Student> waitJoinList) {
        List<Student> combineList = originList;
        long startTime = System.currentTimeMillis();
        for (Student wStudent : waitJoinList) {
            boolean isInOrigin = false;
            for (Student oStudent : originList) {
                if (wStudent.getName().equals(oStudent.getName())) {
                    isInOrigin = true;
                }
            }
            if (!isInOrigin) {
                combineList.add(wStudent);
            }
        }
        System.out.println("insertStudents2, cost time : " + (System.currentTimeMillis() - startTime) + ", combineList size = " + combineList.size());
    }

    public static void insertStudents3(List<Student> originList, List<Student> waitJoinList) {
        long startTime = System.currentTimeMillis();
        Set<String> set = new HashSet<String>();
        for (Student oStudent : originList) {
            set.add(oStudent.getName());
        }

        for (Student wStudent : waitJoinList) {
            if (set.add(wStudent.getName())) {
                originList.add(wStudent);
            }
        }
        System.out.println("insertStudents3, cost time : " + (System.currentTimeMillis() - startTime) + ", originList size = " + originList.size());
    }

    public static void insertStudents4(List<Student> originList, List<Student> waitJoinList) {
        long startTime = System.currentTimeMillis();
        Set<String> set = new TreeSet<>();
        for (Student oStudent : originList) {
            set.add(oStudent.getName());
        }

        for (Student wStudent : waitJoinList) {
            if (set.add(wStudent.getName())) {
                originList.add(wStudent);
            }
        }
        System.out.println("insertStudents4, cost time : " + (System.currentTimeMillis() - startTime) + ", originList size = " + originList.size());
    }

    public static void insertStudents5(List<Student> originList, List<Student> waitJoinList) {
        long startTime = System.currentTimeMillis();
        Set<String> set = new LinkedHashSet<>();
        for (Student oStudent : originList) {
            set.add(oStudent.getName());
        }

        for (Student wStudent : waitJoinList) {
            if (set.add(wStudent.getName())) {
                originList.add(wStudent);
            }
        }
        System.out.println("insertStudents5, cost time : " + (System.currentTimeMillis() - startTime) + ", originList size = " + originList.size());
    }

    /**
     * https://blog.csdn.net/dengnanhua/article/details/64692191
     * @param list
     */
    public static void iterateList0(List<Student> list) {
        long startTime = System.currentTimeMillis();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.get(i).setCity("shanghai");
        }
        System.out.println("iterateList1, cost time : " + (System.currentTimeMillis() - startTime));
    }

    public static void iterateList1(List<Student> list) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCity("shanghai");
        }
        System.out.println("iterateList1, cost time : " + (System.currentTimeMillis() - startTime));
    }

    public static void iterateList2(List<Student> list) {
        long startTime = System.currentTimeMillis();
        for (Student student : list) {
            student.setCity("beijing");
        }
        System.out.println("iterateList2, cost time : " + (System.currentTimeMillis() - startTime));
    }

    public static void iterateList3(List<Student> list) {
        long startTime = System.currentTimeMillis();
        Iterator<Student> iter = list.iterator();
        while (iter.hasNext()) {
            ((Student) iter.next()).setCity("shijiazhuang");
        }
        System.out.println("iterateList3, cost time : " + (System.currentTimeMillis() - startTime));
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
