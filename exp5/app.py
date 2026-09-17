from fastapi import FastAPI
from pydantic import BaseModel

from llm import ask
from rag import RagEngine

app = FastAPI(title="学习搭子智能问答原型")
engine = RagEngine()


class AskRequest(BaseModel):
    question: str


@app.get("/")
def root():
    return {"message": "学习搭子智能问答原型，试试 POST /ask 或 POST /rag/ask"}


@app.post("/ask")
def plain_ask(request: AskRequest):
    return {"answer": ask(request.question)}


@app.post("/rag/ask")
def rag_ask(request: AskRequest):
    return {"answer": engine.answer(request.question)}
