# 实验一：搭建 Web 基础项目（Spring Boot + MyBatis + MySQL）

实现用户表 `t_user` 的完整 CRUD，采用 `Entity / Mapper / Service / Controller` 四层结构。

## 技术栈

- JDK 17
- Spring Boot 3.3.x
- MyBatis（`mybatis-spring-boot-starter`）
- MySQL 8.x
- Maven

## 目录结构

```
exp1/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/example/exp1/
    │   ├── Exp1Application.java
    │   ├── entity/User.java
    │   ├── mapper/UserMapper.java
    │   ├── service/UserService.java
    │   └── controller/UserController.java
    └── resources/
        ├── application.yml
        ├── schema.sql
        └── data.sql
```

## 运行步骤

1. 创建数据库：`CREATE DATABASE selflearn DEFAULT CHARACTER SET utf8mb4;`
2. 修改 `src/main/resources/application.yml` 中的数据库账号密码。
3. 启动：

```bash
mvn spring-boot:run
```

4. 打包部署：

```bash
mvn clean package
java -jar target/exp1-web-crud-1.0.0.jar
```

## 接口验证

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/users` | 查询全部用户 |
| GET | `/api/users/{id}` | 查询单个用户 |
| POST | `/api/users` | 新增用户 |
| PUT | `/api/users/{id}` | 修改用户 |
| DELETE | `/api/users/{id}` | 删除用户 |

```bash
curl http://localhost:8080/api/users
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"carol","password":"123456","email":"carol@example.com"}'
```

> 说明：本实验为演示 CRUD，密码仍为明文，实验四将引入 BCrypt 加密。
