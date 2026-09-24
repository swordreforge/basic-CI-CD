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

## 简易前端

启动后浏览器访问 http://localhost:8878/ 即可看到用户管理页面（`src/main/resources/static/index.html`），无需单独构建前端。编辑用户时密码框留空即保留原密码，只有填写新密码时才会更新（BCrypt 重新加密）。

## 接口验证

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/users` | 查询全部用户 |
| GET | `/api/users/{id}` | 查询单个用户 |
| POST | `/api/users` | 新增用户 |
| PUT | `/api/users/{id}` | 修改用户 |
| DELETE | `/api/users/{id}` | 删除用户 |

```bash
curl http://localhost:8878/api/users
curl -X POST http://localhost:8878/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"carol","password":"123456","email":"carol@example.com"}'
```

> 说明：新建与更新接口均用 BCrypt 加密后入库（`PasswordEncoder`，见 `UserService`），更新时密码留空则保留原哈希；种子数据 `data.sql` 中的 alice/bob 本就存放哈希。查库可见 `$2a$` 开头哈希，而非明文。
