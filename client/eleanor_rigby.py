import streamlit as st
import subprocess
from transformers import AutoTokenizer, AutoModelForCausalLM
import torch

st.title("Eleanor Rigby")
user_prompt = st.text_area("Enter your prompt that can be song lyrics", "E.g. Yesterday, I saw you in my dream")
output = ""

hf_token = st.secrets["hf_token"]
inference_model = AutoModelForCausalLM.from_pretrained("microsoft/phi-2", trust_remote_code=True, torch_dtype=torch.float32, token=hf_token)
inference_tokenizer = AutoTokenizer.from_pretrained("microsoft/phi-2", use_fast=True)
inference_tokenizer.pad_token=inference_tokenizer.eos_token

user_prompt = st.text_area("Enter your prompt that can be song lyrics", "E.g. Yesterday, I saw you in my dream")

if st.button("Generate Output"):
  instruct_prompt = "Instruct:You are a song writer and your main reference is The Beatles. Write a song lyrics by completing these words:"
  output_prompt = " Output:"
  input = inference_tokenizer(""" {0}{1}\n{2} """.format(instruct_prompt, user_prompt, output_prompt),
                     return_tensors="pt",
                     return_attention_mask=False,
                     padding=True,
                     truncation=True)
  result = inference_model.generate(**input, repetition_penalty=1.2, max_length=1024)
  output = inference_tokenizer.batch_decode(result, skip_special_tokens=True)[0]
  st.text("Generated Output:")
  st.write(output)
  
