package com.ddhen.method.Demo2;
//龟兔赛跑
public class Race implements Runnable{
    @Override
    public void run() {
        if(Thread.currentThread().getName().equals("兔子")) {
            for (int i = 0; i < 100; i+=10) {
                System.out.println("兔子跑了" + i + "米");
                try {
                    Thread.sleep(1);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }else{
            for (int i = 0; i < 100; i++) {
                System.out.println("乌龟跑了" + i + "米");
            }
        }
        System.out.println(Thread.currentThread().getName()+"到达终点");
    }

    public static void main(String[] args) {
        Race race=new Race();
        new Thread(race,"兔子").start();
        new Thread(race,"乌龟").start();
    }
}
