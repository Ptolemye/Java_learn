package com.ddhen.method.Demo2;

public class LamdaTest {
    static void send(message ms){
        String status=ms.sendmessage("顺丰","周苏洋");
        System.out.println(status);
    }

    public static void main(String[] args) {
        //()->{}
        send(((type, name) -> {
            System.out.println(type+"寄快递"+name);
            return "success";
        }));
    }
}

interface message{
    abstract String sendmessage(String type,String name);
}
