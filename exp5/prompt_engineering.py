from llm import ask

SYSTEM_CLASSIFIER = (
    "你是一个文本分类助手。请只输出以下分类之一：技术、生活、学习。"
    "不要输出任何解释或其他内容。"
)

SYSTEM_SUMMARIZER = (
    "你是一个摘要助手。请用不超过 50 个字概括用户输入的核心内容，只输出摘要本身。"
)


def classify(text):
    return ask(text, system_prompt=SYSTEM_CLASSIFIER, temperature=0)


def summarize(text):
    return ask(text, system_prompt=SYSTEM_SUMMARIZER, temperature=0.2)


if __name__ == "__main__":
    sample = "今天用 Spring Boot 搭建了用户增删改查接口，并配置了 MySQL 数据源。"
    print("分类结果:", classify(sample))
    print("摘要结果:", summarize(sample))
