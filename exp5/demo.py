from prompt_engineering import classify, summarize
from rag import RagEngine


def main():
    sample = "今天用 Spring Boot 搭建了用户增删改查接口，并配置了 MySQL 数据源。"

    print("提示工程 - 分类:", classify(sample))
    print("提示工程 - 摘要:", summarize(sample))

    engine = RagEngine()
    print("RAG 问答:", engine.answer("实验一需要哪些技术栈？"))


if __name__ == "__main__":
    main()
