import streamlit as st
import subprocess
from transformers import AutoTokenizer, AutoModelForCausalLM
import torch

st.title("Eleanor Rigby")
user_prompt = st.text_area("Enter your prompt that can be song lyrics", "E.g. Yesterday, I saw you in my dream")
