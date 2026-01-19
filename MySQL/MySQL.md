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

数据定义语言，用来定义数据库对象（数据库，表，字段）

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

### 查询创建

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

`desc`实际上就是describe

```sql
desc 表名 ;
```

```
mysql> desc users;
+------------+------------------------+------+-----+-------------------+-------------------+
| Field      | Type                   | Null | Key | Default           | Extra             |
+------------+------------------------+------+-----+-------------------+-------------------+
| id         | int                    | NO   | PRI | NULL              | auto_increment    |
| username   | varchar(50)            | NO   |     | NULL              |                   |
| email      | varchar(100)           | YES  | UNI | NULL              |                   |
| age        | tinyint unsigned       | YES  |     | 18                |                   |
| gender     | enum('男','女','未知') | YES  |     | 未知              |                   |
| created_at | timestamp              | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
+------------+------------------------+------+-----+-------------------+-------------------+
```

（3）**查询指定表的建表语句**

通过这条指令，主要是用来查看建表语句的

```sql
show create table 表名 ;
```

```
 users | CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `email` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `age` tinyint unsigned DEFAULT '18' COMMENT '年龄',
  `gender` enum('男','女','未知') COLLATE utf8mb4_general_ci DEFAULT '未知' COMMENT '性别',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
```

（4）**创建表结构**

```sql
CREATE TABLE 表名(
字段1 字段1类型 [ COMMENT 字段1注释 ],
字段2 字段2类型 [COMMENT 字段2注释 ],
字段3 字段3类型 [COMMENT 字段3注释 ],
......
字段n 字段n类型 [COMMENT 字段n注释 ]
) [ COMMENT 表注释 ] ;
```



### 修改

（1）**添加字段**

```sql
ALTER TABLE 表名 ADD 字段名 类型 (长度) [ COMMENT 注释 ] [ 约束 ];
```

例

```sql
ALTER TABLE emp ADD nickname varchar(20) COMMENT '昵称';
```

（2）**修改数据类型**

```sql
ALTER TABLE 表名 MODIFY 字段名 新数据类型 (长度);
```

（3） **修改字段名和字段类型**

```sql
ALTER TABLE 表名 CHANGE 旧字段名 新字段名 类型 (长度) [ COMMENT 注释 ] [ 约束 ];
```

（4）**删除字段**

```sql
ALTER TABLE 表名 DROP 字段名;
```

（5）**修改表名**

```sql
ALTER TABLE 表名 RENAME TO 新表名;
```



### 删除

（1）**删除表**

```sql
DROP TABLE [ IF EXISTS ] 表名;
```

（2）**删除指定表, 并重新创建表**

```sql
TRUNCATE TABLE 表名;
```



# DML

Data Manipulation Language（数据操作语言），用来对数据库中表的数据记录进行增、删、改操作

- 添加数据（INSERT）
- 修改数据（UPDATE）
- 删除数据（DELETE）

## 添加数据

（1）**给指定字段添加数据**

```sql
INSERT INTO 表名 (字段名1, 字段名2, ...) VALUES (值1, 值2, ...);
```

（2）**给全部字段添加数据**

```sql
INSERT INTO 表名 VALUES (值1, 值2, ...);
```

（3） **批量添加数据**

```sql
INSERT INTO 表名 (字段名1, 字段名2, ...) VALUES (值1, 值2, ...), (值1, 值2, ...), (值
1, 值2, ...) ;
```

## 修改数据

```sql
UPDATE 表名 SET 字段名1 = 值1 , 字段名2 = 值2 , .... [ WHERE 条件 ] ;
```

## 删除数据

```sql
DELETE FROM 表名 WHERE 条件;
```

`DELETE` 会根据 `WHERE` 条件扫描数据，然后将符合条件的记录逐行删除。

可以不写条件但会==整张表删除==

# DQL

**Data Query Language(数据查询语言)**，用来查询库中表的记录。在一个正常的业务系统中，查询操作的频次是要远高于增删改的

## 基本语法

语法结构如下：

```sql
SELECT
	字段列表
FROM
	表名列表
WHERE
	条件列表
GROUP BY
    分组字段列表
HAVING
	分组后条件列表
ORDER BY
	排序字段列表
LIMIT
	分页参数
```



## 基础查询

基础查询不带任何查询条件

（1）查询多个字段

```sql
select * from 表名;
```

```sql
SELECT 字段1, 字段2, 字段3 ... FROM 表名 ;
```

```
mysql> select name from emp;
+--------+
| name   |
+--------+
| 张三   |
| 李美   |
| 王志强 |
| 赵敏   |
| 孙磊   |
| 周芳   |
| 吴军   |
| 郑静   |
| 冯杰   |
| 陈莹   |
+--------+
```

（2）字段设置别名

```sql
SELECT 字段1 [ AS 别名1 ] , 字段2 [ AS 别名2 ] ... FROM 表名;
SELECT 字段1 [ 别名1 ] , 字段2 [ 别名2 ] ... FROM 表名;
```

（3）去除重复记录

```sql
SELECT DISTINCT 字段列表 FROM 表名;
```

`DISTINCT`字段后面可以跟多个字段（==作用范围是之后的所有字段==），只有一行中所有指定字段值与另一行完全相同时，才被视为重复数据



## 条件查询

```sql
SELECT 字段列表 FROM 表名 WHERE 条件列表 ;
```

**常用运算符**：

| **比较运算符**        | **功能**                                         |
| --------------------- | ------------------------------------------------ |
| `>`                   | 大于                                             |
| `>=`                  | 大于等于                                         |
| `<`                   | 小于                                             |
| `<=`                  | 小于等于                                         |
| `=`                   | 等于                                             |
| `<>` 或 `!=`          | 不等于                                           |
| `BETWEEN ... AND ...` | 在某个范围之内（包含最小值和最大值）             |
| `IN (...)`            | 在 `IN` 之后的列表中的值，多选一                 |
| `LIKE 占位符`         | 模糊匹配（`_` 匹配单个字符，`%` 匹配任意个字符） |
| `IS NULL`             | 是 `NULL`                                        |

**常用逻辑运算符**：

| **逻辑运算符** | **功能**                     |
| -------------- | ---------------------------- |
| `AND` 或 `&&`  | 并且（多个条件同时成立）     |
| `OR` 或 `||`   | 或者（多个条件任意一个成立） |
| `NOT` 或 `!`   | 非，不是                     |

**显然，任意组合都是可以的**

```sql
select * from emp where name like '__';
```

```
mysql> select * from emp where name like '__';
+------+--------+------+--------+------+--------------------+--------------+------------+
| id   | workno | name | gender | age  | idcard             | workaddress  | entrydate  |
+------+--------+------+--------+------+--------------------+--------------+------------+
| NULL | 00001  | 张三 | 男     |   28 | 110101199501011234 | 北京朝阳区   | 2022-01-15 |
| NULL | 00002  | 李美 | 女     |   24 | 310101199905204321 | 上海浦东新区 | 2023-03-10 |
| NULL | 00004  | 赵敏 | 女     |   30 | 510101199308159876 | 四川成都武侯 | 2022-06-01 |
| NULL | 00005  | 孙磊 | 男     |   22 | 33010120011212001X | 浙江杭州西湖 | 2024-07-15 |
| NULL | 00006  | 周芳 | 女     |   27 | 420101199602283344 | 湖北武汉洪山 | 2023-09-12 |
| NULL | 00007  | 吴军 | 男     |   42 | 210101198104057788 | 辽宁沈阳沈北 | 2019-12-05 |
| NULL | 00008  | 郑静 | 女     |   31 | 320101199210106655 | 江苏南京玄武 | 2022-02-28 |
| NULL | 00009  | 冯杰 | 男     |   29 | 610101199407074433 | 陕西西安雁塔 | 2023-05-20 |
| NULL | 00010  | 陈莹 | 女     |   26 | 350101199712252211 | 福建福州仓山 | 2024-01-10 |
| NULL | 00015  | 刘洋 | 男     |   23 | 330101200107078899 | 浙江省杭州市 | 2024-07-12 |
| NULL | 00016  | 赵静 | 女     |   34 | 420101199002283344 | 湖北省武汉市 | 2021-09-30 |
+------+--------+------+--------+------+--------------------+--------------+------------+
```

```sql
select * from emp where idcard like '%X';
```

```
mysql> select * from emp where idcard like '%X';
+------+--------+------+--------+------+--------------------+--------------+------------+
| id   | workno | name | gender | age  | idcard             | workaddress  | entrydate  |
+------+--------+------+--------+------+--------------------+--------------+------------+
| NULL | 00005  | 孙磊 | 男     |   22 | 33010120011212001X | 浙江杭州西湖 | 2024-07-15 |
+------+--------+------+--------+------+--------------------+--------------+------------+
```



## 聚合函数

```sql
SELECT 聚合函数(字段列表) FROM 表名 ;
```

常见的聚合函数

| **函数** | **功能** |
| -------- | -------- |
| `count`  | 统计数量 |
| `max`    | 最大值   |
| `min`    | 最小值   |
| `avg`    | 平均值   |
| `sum`    | 求和     |

```sql
select count(*) from emp; #统计总行数，包含字段全为NULL的行
select count(age) from emp; #统计age字段不为NULL的行数
select avg(age) from emp; #只计算非NULL的行
```

后面可以接条件查询`where`



## 分组查询

```sql
SELECT 字段列表 FROM 表名 [ WHERE 条件 ] GROUP BY 分组字段名 [ HAVING 分组 后过滤条件 ];
```

分组之后，查询的字段列表一般为**聚合函数**和**目标分组字段**，其他查询字段没有任何意义

**关键字执行顺序**：`where`>`聚合函数`>`having`

`where`:对分组前的的table进行筛选

`having`：对分组后的结果进行过滤，后接聚合函数

```sql
SELECT workaddress AS 工作城市, COUNT(*) AS 员工人数 FROM emp WHERE age > 25 GROUP BY workaddress HAVING COUNT(*) > 2;
# 查询年龄大于 25 岁的员工，并根据工作城市分组，获取员工人数大于2的工作城市
```

```sql
select gender,workplace,count(*) from emp group by gender, workplace;
# (gender,workplace)作为一个主键
```

```
mysql> select gender,workplace,count(*) from emp group by gender, workplace;
+--------+-----------+----------+
| gender | workplace | count(*) |
+--------+-----------+----------+
| 男     | 北京      |        2 |
| 女     | 上海      |        1 |
| 男     | 上海      |        1 |
| 女     | 成都      |        1 |
+--------+-----------+----------+
```



## 排序查询

```sql
SELECT 字段列表 FROM 表名 ORDER BY 字段1 排序方式1 , 字段2 排序方式2 ;
```

**ASC：升序**

**DESC：降序**



## 分页查询

```sql
SELECT 字段列表 FROM 表名 LIMIT 起始索引, 查询记录数 ;
```

起始索引为0

起始索引 = （查询页码-1）* 每页显示记录数

查询第一页数据，可以省略起始索引



## 执行顺序

DQL执行顺序为：

```
from ... where ... group by ... having ... select ... order by ... limit ...
```

# DCL

**Data Control Language**(数据控制语言)



