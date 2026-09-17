# 《团队规范项目实训》个人技能实训示例

按 `requirement.txt` 第二部分「个人技能实训」拆分为 5 个独立示例项目，每个目录均可独立运行并配有说明。

| 实验 | 目录 | 内容 |
| --- | --- | --- |
| 实验一 | `exp1/` | Spring Boot + MyBatis + MySQL 用户 CRUD |
| 实验二 | `exp2/` | 前后端分离：RESTful API + Vue3/Axios + CORS + OpenAPI |
| 实验三 | `exp3/` | Git 协作规范 + GitHub Actions CI |
| 实验四 | `exp4/` | Spring Security + JWT + RBAC + 提示注入/脱敏防护 |
| 实验五 | `exp5/` | LLM 调用 + 提示工程 + RAG 智能问答原型（Python） |

## 公共准备

- JDK 17、Maven、MySQL 8.x、Node.js 20+、Python 3.10+
- MySQL 创建数据库：`CREATE DATABASE selflearn DEFAULT CHARACTER SET utf8mb4;`
- 各实验需按自身 `README.md` 修改数据库账号密码或环境变量

## 说明

- 实验五采用 Python（`openai` SDK 兼容 OpenAI/通义/文心/Ollama）。
- AI 生成代码需标注 `// AI-assisted` 并人工理解复核；安全相关逻辑必须人工拍板。
