package com.ddhen.method.Demo2;


import java.io.*;

/*
演示FileInputStream

 */
public class Demo1 {
    public static void main(String[] args) throws Exception {
        //1创建文件字符输入流,输出流
        FileReader fr = new FileReader("src/com/ddhen/method/Demo2/text/a.txt");
        FileWriter fw=new FileWriter("src/com/ddhen/method/Demo2/text/b.txt",true);
        //2传输
        int count=0;
        char[] buffer=new char[1024];
        while ((count=fr.read(buffer))!=-1){
            fw.write(buffer,0,count);
        }
        //3关闭
        fr.close();
        fw.close();
    }
}
