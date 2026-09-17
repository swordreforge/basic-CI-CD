import hashlib
import math
from pathlib import Path

import numpy as np

import config
from llm import ask, build_client

BASE_DIR = Path(__file__).resolve().parent
DOCS_DIR = BASE_DIR / "docs" / "sample_docs"

RAG_SYSTEM_PROMPT = (
    "你是一个基于给定资料的问答助手。请只根据下面的资料回答用户问题。"
    "如果资料中没有答案，直接说“资料中没有相关信息”，不要编造。\n\n资料:\n{context}"
)


def _hash_embed(text, dim=256):
    vec = [0.0] * dim
    lower = text.lower()
    for n in (2, 3):
        for i in range(len(lower) - n + 1):
            gram = lower[i:i + n]
            h = int(hashlib.md5(gram.encode("utf-8")).hexdigest(), 16) % dim
            vec[h] += 1.0
    norm = math.sqrt(sum(v * v for v in vec))
    if norm == 0:
        return vec
    return [v / norm for v in vec]


def _api_embed(text):
    client = build_client()
    response = client.embeddings.create(model=config.EMBEDDING_MODEL, input=text)
    return response.data[0].embedding


def embed(text):
    if config.EMBEDDING_MODE in ("api", "auto"):
        try:
            return _api_embed(text)
        except Exception:
            if config.EMBEDDING_MODE == "api":
                raise
    return _hash_embed(text)


def chunk_text(text, chunk_size=200, overlap=40):
    chunks = []
    start = 0
    while start < len(text):
        chunks.append(text[start:start + chunk_size])
        start += chunk_size - overlap
    return chunks


class RagEngine:
    def __init__(self, docs_dir=DOCS_DIR):
        self.chunks = []
        self.vectors = []
        for path in sorted(Path(docs_dir).glob("*.txt")):
            self._add_document(path.read_text(encoding="utf-8"))

    def _add_document(self, text):
        for chunk in chunk_text(text):
            if chunk.strip():
                self.chunks.append(chunk)
                self.vectors.append(embed(chunk))

    def retrieve(self, query, top_k=3):
        if not self.chunks:
            return []
        query_vec = np.array(embed(query))
        matrix = np.array(self.vectors)
        scores = matrix @ query_vec / (
            np.linalg.norm(matrix, axis=1) * np.linalg.norm(query_vec) + 1e-9
        )
        top_idx = np.argsort(scores)[::-1][:top_k]
        return [self.chunks[i] for i in top_idx]

    def answer(self, question):
        contexts = self.retrieve(question)
        context = "\n\n".join(f"- {c}" for c in contexts)
        return ask(question, system_prompt=RAG_SYSTEM_PROMPT.format(context=context))


if __name__ == "__main__":
    engine = RagEngine()
    print(engine.answer("实验一需要哪些技术栈？"))
