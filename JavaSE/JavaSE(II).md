# 一、Stream

**Stream流的使用步骤：**

- 先得到一条Stream流，把数据放上去
- 使用==中间方法==对Stream上的数据进行操作
- 使用==终结方法==对Stream上的数据进行操作

| 获取方式     | 方法名                                        | 说明                     |
| ------------ | --------------------------------------------- | ------------------------ |
| 单列集合     | default Stream\<E> stream()                   | Collection中的默认方法   |
| 双列集合     | 无                                            | 只能先提取set            |
| 数组         | public static\<T> Stream<T> stream(T[] array) | Arrays工具类中的静态方法 |
| 一堆零散数据 | public static\<T> Stream<T> of(T...values)    | Stream接口中的静态方法   |

## 获取Stream

```java
'''
    单列集合
'''
list.stream().forEach(s->System.out.println(s));
    
'''
    双列集合
'''
map.keySet().stream().forEach(s->System.out.println(s));
map.entrySet().stream().forEach(s->System.out.println(s));

'''
    数组类型
'''
Arrays.stream(arr).forEach(s->System.out.println(s));

'''
    一堆零散数据
'''
Stream.of(...).forEach()
```

## 中间方法

| 名称                                     | 说明               |
| ---------------------------------------- | ------------------ |
| Stream<T> filter(Predicate<T> predicate) | 过滤               |
| Stream<T>map(Function<T,R> mapper)       | 转换流中的数据类型 |

# 二、方法引用

把已经实现的方法逻辑直接复用，当作函数式接口中抽象方法的方法体

- 引用处应该是函数式接口

- 被引用方法需要已存在(自己写或者第三方工具类)
- 被引用方法功能需要满足当前要求

```java
Arrays.sort(arr,FunctionDemo1::subtraction)
//::是方法引用标志
```

## 引用静态方法

``` java
ArrayList<String> list = new ArrayList<>();
Collections.addAll(list,"1","2","3")

//Lambda写法
list.stream().map(s->Integer.parseInt(s));
//方法引用写法
list.stream().map(Integer::pareInt)；
```

## 引用类的成员方法

- 对象::成员方法（成员方法属于对象实例）
- this::成员方法
- super::成员方法

```java
//其他类成员方法
list.stream().filter(new StringOperation()::judge)
```

## 引用构造方法

格式： 类名::new

# 三、异常

# 四、File

# 二、JavaIO(java.io)

**流**：内存与存储设备之间传输数据的通道，双向

**输入流**：将存储设备的内容读入到内存中

**输出流**：将内存的内容读入到存储设备

**字节流**：以字节为单位，可以读写所有的数据

**字符流**：以字符为单位，只能读写文本

## 字节流

**抽象类**

输入：InputStream

输出：OutputStream

### FileInputStream

**1、逐步读取每个字符**

此时`read()`返回字节的ASCII码

```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
        //1创建FileInputStream,参数指明文件路径
        FileInputStream fileInputStream=new FileInputStream("src/com/ddhen/method/Demo2/text/a.txt");
        //2读取文件
        //fileInputStream.read();
        int data=0;
        //单个字节读取,read()返回int类型的ASCII码
        while((data=fileInputStream.read())!=-1){
            System.out.println((char)data);
        }
        String encoding = System.getProperty("file.encoding");
        System.out.println("当前平台的默认编码是: " + encoding);
        //UTF-8编码，每个字母占1字节
    }
}
```

**2、一次读取多个**

多个字符，`read(byte[])`返回`int`即读取到的字符个数，如果读取个数为0，返回-1

```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
        //1创建FileInputStream,参数指明文件路径
        FileInputStream fileInputStream=new FileInputStream("src/com/ddhen/method/Demo2/text/a.txt");
        //2读取文件
        //fileInputStream.read();
        //一次读取多个字节，使用byte[]数组存入，8位
       byte[] buff=new byte[3];
       int count=0;
       while ((count=fileInputStream.read(buff))!=-1){
           System.out.println(new String(buff,0,count));
       }
    }
}
/*
faw
fsf
wqx
wqw
x
*/
```

### FileOutputStream

输出流对象创建时，构造函数需要传入两个参数

`name`:文件路径

`append`:默认为覆盖原文件，true在原文件末尾添加

`write()`有以下可选参数`int` `byte[]`

```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
        //1创建文件字节输出流对象
        FileOutputStream fileOutputStream=new FileOutputStream("src/com/ddhen/method/Demo2/text/b.txt",true);
        //2写入文件,write参数为byte[]数组
       String s="hello";
       fileOutputStream.write(s.getBytes());
        //3关闭输出流
        fileOutputStream.close();
    }
}
```

### 练习：复制图片

```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
        //1、创建输入流对象
        FileInputStream fis=new FileInputStream("src/com/ddhen/method/Demo2/text/软考证书.pdf");
        //2、创建输出流对象
        FileOutputStream fos=new FileOutputStream("src/com/ddhen/method/Demo2/text/软考证书(副本).pdf");
        //3、创建缓存
        byte[] buffer=new byte[1024];
        int count=0;
        while((count=fis.read(buffer))!=-1){
            fos.write(buffer);
        }
        //4关闭
        fis.close();
        fos.close();
    }
}
```

## 字节缓冲流

缓冲流

在上述的操作中，我们每次read()就要有相应的write()操作，访问磁盘次数多，效率低下，字节缓冲流解决了这一问题

- 提高IO效率，减少访问磁盘的次数
- 数据存储在缓冲区中，调用flush将缓冲区内容写入文件中，也可以直接close

### BufferdInputStream

此时的`read()`操作先将8k字节读入缓冲区，之后如果缓冲区未被读完，则不会与磁盘进行交互

```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
        //1创建缓冲流
        FileInputStream fis=new FileInputStream("src/com/ddhen/method/Demo2/text/a.txt");
        BufferedInputStream bis=new BufferedInputStream(fis);
        //2读入
        int count=0;
        while((count=bis.read())!=-1){
            System.out.print((char)count);
        }
        System.out.println();
        byte[] buff=new byte[1024];
        while((count=bis.read(buff))!=-1){
            System.out.println(new String(buff,0,count));
        }
        //关闭
        bis.close();
    }
}
```

### BufferdOutputStream

注意，此时的`write`只是将字节写入8kb的缓存区，注意flush调用刷新到磁盘

```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
        //1创建缓冲流
       FileOutputStream fos=new FileOutputStream("src/com/ddhen/method/Demo2/text/d.txt");
        BufferedOutputStream bos=new BufferedOutputStream(fos);
        //2读入
        String s="asdas";
        for (int i = 0; i < 10; i++) {
            bos.write(s.getBytes());
        }
        bos.flush();
        //关闭
        //bos.close();close会自动调用flush
    }
}
```

## 对象流

实现内存中的对象写入硬盘，或者从硬盘读取数据转化为对象

`readObject()`从流中读取一个对象

`writeObject()`将指定对象写入到流中

**序列化**：将数据结构或对象转换成一种可存储或可传输格式的过程

**反序列化**：序列化的逆过程，即将序列化后的数据重新还原成原始的数据结构或对象

### ObjectOutputStream

所有需要序列化的类都必须`implements`接口`Serializable`

```java
public class Student implements Serializable {}
```

```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
       //1创建对象流
        FileOutputStream fos=new FileOutputStream("src/com/ddhen/method/Demo2/text/stu.bin");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        //2实现序列化
        Student zsy=new Student("ddhen",20);
        oos.writeObject(zsy);
       //3关闭
       oos.close();
    }
}
```

### ObjectInputStream

`readObject()`的返回值为`Objetct`类型，需要强制转换

```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
       //1创建对象流
        FileInputStream fis=new FileInputStream("src/com/ddhen/method/Demo2/text/stu.bin");
        ObjectInputStream ois=new ObjectInputStream(fis);
        //2读取对象
        Student stu=(Student) ois.readObject();
        System.out.println(stu);
        //3关闭
        ois.close();
    }
}
```

### 序列化与反序列化注意事项

- 序列化类必须实现Serializable接口
- 序列化类中的对象属性要求实现Serializable接口
- 序列化版本号ID`long`,保证序列化的类和反序列化的类是同一个类

## 字符流

`ISO-8859-1`:收录ASCII外，还包括西欧、泰语等

`UTF-8`:针对Unicode码表的可变长度字符编码，汉字一个字三个字节

`GB2312`:简体中文

`GBK`:简体中文，扩充

**抽象类**

输入：Reader

输出：Writer

### FileReader

用来读取字符文件的便捷类，此类构造方法假定默认字符编码和默认字节缓冲区大小都是适当的

```java
public class Demo1 {
    public static void main(String[] args) throws Exception{
       //1创建文件字符输入流
        FileReader fr=new FileReader("src/com/ddhen/method/Demo2/text/a.txt");
        //2读取,一次读3个字节
        int data=0;
        data=fr.read();
        System.out.println((char)data);
        char[] buffer=new char[6];
        fr.read(buffer);
        System.out.println(new String(buffer));
    }
}
//四
//川大学周苏洋    
```

### FileWriter

文档复制练习

```java
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
```

==注意==：

FileReader和FileWriter复制文本文件，不能复制图片或二进制文件

复制图片或者pdf类型的文件只能使用字节流

## 字符缓冲流

### BufferdReader

### BufferdWriter

## 转换流

# 三、多线程

## 基础概念

**进程**是程序的一次==执行过程==，系统资源分配的单位

一个进程包含多个**线程**，线程是CPU调度和执行的单位

## 线程创建

### 继承Thread类

方法1：继承Thread类，重写run()方法，调用start开启线程

```java
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
//主线程与run线程交替打印
//
```

### 实现Runable接口（推荐）

实现Runable接口启动线程需要用线程对象进行代理

==相较于直接继承避免了单继承局限性，灵活方便，方便一个资源被多个线程使用==

**注意**：只有`Thread`对象才是真正的线程对象

```java
public class TestThread2 implements Runnable{
    @Override
    public void run() {
        //run方法线程体
        for (int i = 0; i < 20; i++) {
            System.out.println("这是run方法线程--"+i);
        }
    }

    public static void main(String[] args) {
        //创建Runnable接口的实现类对象
        TestThread2 testThread2=new TestThread2();
        //创建线程对象，通过线程对象代理开启线程
        new Thread(testThread2).start();

        //调用start()开启线程
        for (int i = 0; i < 20; i++) {
            System.out.println("这是主线程--"+i);
        }
    }
}
```

### 并发问题

当多个线程同时调用同一个资源时，可能造成冲突，产生不一致性

### 龟兔赛跑(练习)

```java
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
```

### 实现Callable接口

## Lamda表达式

### **函数式接口**

只包含唯一一个抽象方法，即为函数式接口

通过lambda表达式来创建该接口对象

```java
public interface Runnable{
    public abstract void run()
}
```

### 一个案例

```java
public class LamdaTest {
    static void send(message ms){
        String status=ms.sendmessage("顺丰","周苏洋");
        System.out.println(status);
    }

    public static void main(String[] args) {
        //()->{},()中填写唯一抽象方法的参数，{}中填写具体的函数实现方法
        //()->
        send(((type, name) -> {
            System.out.println(type+"寄快递"+name);
            return "success";
        }));
    }
}

interface message{
    abstract String sendmessage(String type,String name);
}
```

## 静态代理

真实对象和代理对象都要实现同一个接口

代理对象代理真实角色

**优势**

- 代理对象可以做很多真实对象无法完成的事情
- 真实对象专注做自己的事情

## 线程状态

### 线程停止

设置flag标志位去控制线程的中断

```java
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
```

### 线程休眠

- sleep