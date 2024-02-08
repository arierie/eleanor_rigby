import streamlit as sl
from transformers import AutoTokenizer, AutoModelForCausalLM
import torch

inference_model = AutoModelForCausalLM.from_pretrained("arieridwans/phi-2-finetuned-lyrics", trust_remote_code=True, torch_dtype=torch.float32)
inference_tokenizer = AutoTokenizer.from_pretrained("microsoft/phi-2", use_fast=True)
inference_tokenizer.pad_token=inference_tokenizer.eos_token

sl.title("Eleanor Rigby")
user_prompt = sl.text_area("Enter your prompt that can be song lyrics", "E.g. Yesterday, I saw you in my dream")

if sl.button("Generate Output"):
  instruct_prompt = "Instruct:You are a song writer and your main reference is The Beatles. Write a song lyrics by completing these words:"
  output_prompt = " Output:"
  input = inference_tokenizer(""" {0}{1}\n{2} """.format(instruct_prompt, user_prompt, output_prompt),
                     return_tensors="pt",
                     return_attention_mask=False,
                     padding=True,
                     truncation=True)
  result = inference_model.generate(**input, repetition_penalty=1.2, max_length=1024)
  output = inference_tokenizer.batch_decode(result, skip_special_tokens=True)[0]
  sl.text("Generated Output:")
  sl.write(output)
