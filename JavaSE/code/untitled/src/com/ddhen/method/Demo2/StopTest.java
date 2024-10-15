package com.ddhen.method.Demo2;

public class StopTest implements Runnable{
    private boolean flag=true;
    @Override
    public void run() {
        int i=0;
        while (flag){
            System.out.println("线程进行中--"+i++);
        }
        System.out.println("线程停止了");
    }

    void stop(){
        this.flag=false;
    }
    public static void main(String[] args) {
        StopTest stopTest=new StopTest();
        new Thread(stopTest).start();
        for (int i = 0; i < 1000; i++) {
            if(i==900){
                stopTest.stop();
            }
            System.out.println("主线程进行到--"+i);
        }
    }
}
