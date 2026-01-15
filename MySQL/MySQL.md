# MySQL

# 数据模型

![image-20260115200810883](./image/image-20260115200810883.png)

**客户端(Cilent)：**用户界面或应用程序，是用户和数据库系统交互的地方（Navicat）

**数据库管理系统(DBMS)：**==数据库服务器中==一套软件，负责接收客户端的请求，并对数据库进行操作，处理操作指令

**数据库（Database）：**==数据库服务器中==存储数据库的实体

一个管理系统可以对应多个独立的数据库，每个数据库中又存储了多个数据表（table）

# SQL

| **DDL** | **Data Definition Language**   | 数据定义语言，用来定义数据库对象（数据库，表，字段）   |
| ------- | ------------------------------ | ------------------------------------------------------ |
| **DML** | **Data Manipulation Language** | 数据操作语言，用来对数据库表中的数据进行增删改         |
| **DQL** | **Data Query Language**        | 数据查询语言，用来查询数据库中表的记录                 |
| **DCL** | **Data Control Language**      | 数据控制语言，用来创建数据库用户、控制数据库的访问权限 |

# DDL

## 数据库操作

1) 查询所有数据库

```sql
show databases;
```

```
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| learning_base      |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
```

2) 切换数据库

   一个服务器中有多个数据库，首先需要切换数据库选择需要进行后续操作的那一个

```sql
use sys;
```

```
mysql> use sys
Database changed
```

3. 查询当前数据库

```sql
select database();
```

```
mysql> select database();
+------------+
| database() |
+------------+
| sys        |
+------------+
```

4. 创建数据库

```sql
create database [ if not exists ] 数据库名 [ default charset 字符集 ] [ collate 排序
规则 ] ;
```

5. 删除数据库

```sql
drop database [ if exists ] 数据库名 ;
```



## 表操作

1. 查询创建

（1）**查询当前数据库所有表**

```sql
show tables;
```

```
mysql> show tables;
+-----------------------------------------------+
| Tables_in_sys                                 |
+-----------------------------------------------+
| host_summary                                  |
| host_summary_by_file_io                       |
| host_summary_by_file_io_type                  |
| host_summary_by_stages                        |
| host_summary_by_statement_latency             |
| host_summary_by_statement_type                |
| innodb_buffer_stats_by_schema                 |
| innodb_buffer_stats_by_table                  |
| innodb_lock_waits                             |
| io_by_thread_by_latency                       |
| io_global_by_file_by_bytes                    |
| io_global_by_file_by_latency                  |
|......                                         |
+-----------------------------------------------+
```

（2）**查看指定表结构**

查看到指定表的字段，字段的类型、是否可以为NULL，是否存在默认值等信息。

```sql
desc 表名 ;
```



