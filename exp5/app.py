from pathlib import Path

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import RedirectResponse
from fastapi.staticfiles import StaticFiles
from pydantic import BaseModel

from llm import ask
from rag import RagEngine

BASE_DIR = Path(__file__).resolve().parent

app = FastAPI(title="学习搭子智能问答原型")
engine = RagEngine()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

app.mount("/static", StaticFiles(directory=str(BASE_DIR / "static")), name="static")


class AskRequest(BaseModel):
    question: str


@app.get("/")
def root():
    return RedirectResponse(url="/static/index.html")


@app.post("/ask")
def plain_ask(request: AskRequest):
    return {"answer": ask(request.question)}


@app.post("/rag/ask")
def rag_ask(request: AskRequest):
    return {"answer": engine.answer(request.question)}
