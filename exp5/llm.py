from openai import OpenAI

from config import LLM_API_KEY, LLM_BASE_URL, LLM_MODEL


def build_client():
    return OpenAI(api_key=LLM_API_KEY or "ollama", base_url=LLM_BASE_URL)


def ask(question, system_prompt=None, temperature=0.7):
    client = build_client()
    messages = []
    if system_prompt:
        messages.append({"role": "system", "content": system_prompt})
    messages.append({"role": "user", "content": question})

    response = client.chat.completions.create(
        model=LLM_MODEL,
        messages=messages,
        temperature=temperature,
    )
    return response.choices[0].message.content
