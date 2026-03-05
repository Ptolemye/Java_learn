# Maven

## 1. Maven基础

==Maven有三大作用==

- 依赖管理
- 项目构建
- 同一项目结构

### 1.1  依赖管理

Maven可以管理项目所需要的jar包

**传统方法**

一个project有一个单独的lib仓库，程序员从网上下载好jar包存入lib目录

**项目结构**：

```
common_project
	> lib
	  hutool-all-5.8.27.jar
	  ......
	> src
```



**Maven方法**

maven项目利用==pom.xml==管理项目所需要的jar包

所谓pom，即项目对象模型（Project Object Model），将项目抽象成一个对象，项目拥有自己的坐标



```
maven_project
	> src
	pom.xml
```



```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.8.27</version>
</dependency>
```



### 1.2  项目构建

### 1.3  同一项目结构



## 2. Maven仓库

