import streamlit as st
import subprocess
from transformers import AutoTokenizer, AutoModelForCausalLM
import torch

st.title("Eleanor Rigby")
user_prompt = st.text_area("Enter your prompt that can be song lyrics", "E.g. Yesterday, I saw you in my dream")
output = ""

hf_token = st.secrets["hf_token"]
inference_model = AutoModelForCausalLM.from_pretrained("microsoft/phi-2", trust_remote_code=True, torch_dtype=torch.float32)

user_prompt = st.text_area("Enter your prompt that can be song lyrics", "E.g. Yesterday, I saw you in my dream")
