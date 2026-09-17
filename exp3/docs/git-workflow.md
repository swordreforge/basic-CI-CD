# 团队 Git 协作规范

## 分支模型

- `main`：稳定可发布分支，只接受来自 `develop` 或 `feature/*` 的合并请求。
- `develop`：开发主线，集成各功能分支。
- `feature/<功能名>`：每个功能/实验一个分支，例如 `feature/user-crud`。
- `hotfix/<问题>`：线上紧急修复分支。

```bash
# 开始一个新功能
git checkout develop
git pull origin develop
git checkout -b feature/user-crud

# 完成后提交并推送
git add .
git commit -m "feat: add user CRUD endpoints"
git push origin feature/user-crud
```

## 提交信息规范（Conventional Commits）

| 类型 | 用途 | 示例 |
| --- | --- | --- |
| feat | 新功能 | `feat: add user list api` |
| fix | 修复缺陷 | `fix: handle null email` |
| docs | 文档 | `docs: update README` |
| refactor | 重构 | `refactor: extract UserService` |
| test | 测试 | `test: cover delete endpoint` |
| chore | 杂项 | `chore: add ci workflow` |

## 代码评审（Code Review）

- 每个 Pull Request 至少由 1 名其他成员评审通过后才能合并。
- 评审关注：功能是否满足需求、命名与分层是否清晰、是否有安全/边界问题、测试是否充分。
- 评审意见需在合并前全部解决；禁止直接提交到 `main` 与 `develop`。

## 提交前自检

1. `mvn test` 或 `npm run build` 通过。
2. 不提交密钥、`.env`、构建产物（遵循 `.gitignore`）。
3. AI 生成代码已标注 `// AI-assisted` 且已人工理解复核。
