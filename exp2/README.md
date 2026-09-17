# 实验二：前后端分离与 RESTful API

后端使用 Spring Boot 暴露 RESTful JSON 接口，前端使用 Vue 3 + Vite + Axios 调用，并解决跨域与自动生成接口文档。

## 目录结构

```
exp2/
├── backend/   # Spring Boot 后端（RESTful + CORS + OpenAPI）
└── frontend/  # Vue 3 前端（Axios 交互）
```

## 后端

1. 创建数据库：`CREATE DATABASE selflearn DEFAULT CHARACTER SET utf8mb4;`
2. 修改 `backend/src/main/resources/application.yml` 数据库账号密码。
3. 启动：

```bash
cd backend
mvn spring-boot:run
```

- 接口文档（OpenAPI / Swagger UI）：http://localhost:8080/swagger-ui.html
- OpenAPI JSON：http://localhost:8080/v3/api-docs

## 前端

```bash
cd frontend
npm install
npm run dev
```

访问 http://localhost:5173 。开发环境通过 Vite 的 `/api` 代理转发到 `http://localhost:8080`，避免跨域；生产环境也可由后端 CORS 配置直接放行。

## 跨域说明

后端 `WebConfig` 允许 `http://localhost:5173` 访问 `/api/**`；前端 `vite.config.js` 提供开发代理，二选一或配合使用均可。
