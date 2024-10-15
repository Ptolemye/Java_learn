package com.ddhen.method.Demo2;

public class TestThread1 extends  Thread{
    @Override
    public void run() {
        //run方法线程体
        for (int i = 0; i < 20; i++) {
            System.out.println("这是run方法线程--"+i);
        }
    }

    public static void main(String[] args) {
        //main线程，主线程

        //创建一个线程对象
        TestThread1 testThread1=new TestThread1();

        //调用start()开启线程
        testThread1.start();
        for (int i = 0; i < 20; i++) {
            System.out.println("这是主线程--"+i);
        }
    }
}
