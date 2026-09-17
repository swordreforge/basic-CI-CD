# 实验三：Git 协作开发与持续集成

演示团队 Git 协作规范与 CI/CD 流水线，配套文档与 GitHub Actions 示例。

## 内容

- `.github/workflows/ci.yml`：CI 流水线，`push` / `pull_request` 到 `main` 或 `develop` 时自动构建并测试 `exp1`、`exp2/backend`（Maven）与 `exp2/frontend`（npm）。
- `docs/git-workflow.md`：分支模型、提交规范、代码评审要求。
- `docs/ai-usage-policy.md`：团队 AI 使用公约。
- `.gitignore`：忽略构建产物、IDE 配置、密钥与环境文件。

## 使用方式

将本目录放入 Git 仓库根目录（或把 `.github/` 放在仓库根），推送后即可在 GitHub Actions 页面查看构建结果：

```bash
git init
git add .
git commit -m "chore: add CI workflow and team docs"
git branch -M main
git remote add origin <你的仓库地址>
git push -u origin main
```

## 验收点

- CI 在每次推送与 PR 时自动执行构建/测试。
- 功能开发走 `feature/*` 分支，合并走 PR 且至少 1 人评审。
- 团队遵守 AI 使用公约，AI 生成代码已标注并人工复核。
