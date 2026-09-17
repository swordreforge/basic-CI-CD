# 实验五：LLM 应用开发与智能功能原型

调用兼容 OpenAI 协议的大模型 API，完成提示工程、`ask(question)` 封装、RAG 入门与可演示原型（“学习搭子”问答入口）。

## 技术栈

- Python 3.10+
- `openai` SDK（兼容 OpenAI / 通义 / 文心 / 本地 Ollama）
- `numpy`（余弦相似度检索）
- `fastapi` + `uvicorn`（原型接口）

## 安装与配置

```bash
cd exp5
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
cp .env.example .env
```

编辑 `.env`，填入你的 API Key 与模型。示例：

- 通义：`LLM_BASE_URL=https://dashscope.aliyuncs.com/compatible-mode/v1`
- 本地 Ollama：`LLM_BASE_URL=http://localhost:11434/v1`，`LLM_API_KEY=ollama`，`LLM_MODEL=qwen2.5`

## 运行

命令行演示（提示工程 + RAG）：

```bash
python demo.py
```

启动原型接口：

```bash
uvicorn app:app --reload
```

```bash
# 直接问答
curl -X POST http://localhost:8000/ask \
  -H "Content-Type: application/json" \
  -d '{"question":"什么是 Spring Boot？"}'

# 基于资料问答（RAG）
curl -X POST http://localhost:8000/rag/ask \
  -H "Content-Type: application/json" \
  -d '{"question":"实验一需要哪些技术栈？"}'
```

## 模块说明

| 文件 | 作用 |
| --- | --- |
| `llm.py` | 封装 `ask(question)`，支持 System/User 提示词 |
| `prompt_engineering.py` | 分类、摘要两个“给定需求 → 稳定输出”的提示工程示例 |
| `rag.py` | 文档切块 → 向量化 → 检索 → 拼入提示 → 作答 |
| `app.py` | FastAPI 原型，`/ask` 与 `/rag/ask` |
| `demo.py` | 命令行演示 |
| `docs/sample_docs/` | 用于 RAG 的小型知识库 |
| `docs/reflection.md` | 幻觉案例与应对记录 |

## 关于向量化

`EMBEDDING_MODE` 支持三种模式：

- `api`：调用嵌入接口（`text-embedding-3-small` 等），语义最好。
- `hash`：离线字符 n-gram 哈希向量，无需 API，仅用于演示 RAG 流程。
- `auto`（默认）：优先 `api`，失败自动回退 `hash`。

## 接入实验一项目

将 `app.py` 的 `/rag/ask` 作为后端智能入口，由实验一/二的 Spring Boot 或 Vue 前端调用，即可形成“学习搭子”问答原型。用户输入进入模型前，请复用实验四的 `AiSecurityGuard` 做提示注入防护与敏感信息脱敏。
