import streamlit as st
import replicate
import os

# App title
st.set_page_config(page_title="Eleanor Rigby")
st.title('Eleanor Rigby: The Beatles-inspired lyrics generation')

# Replicate Credentials
with st.sidebar:
    replicate_api = st.secrets['REPLICATE_API_TOKEN']
    st.subheader('Parameters')
    llm = 'https://replicate.com/arierie/phi_2-finetuned-lyrics:48a60dd8d863735436eed0a6ff3b108d84fd333321c75441c57ac6a7cd75dbeb'
    max_token = st.sidebar.slider('max_length', min_value=128, max_value=1028, value=512, step=8)

# Store LLM generated responses
if "messages" not in st.session_state.keys():
    st.session_state.messages = [{"role": "assistant", "content": "How may I assist you today?"}]

# Display or clear chat messages
for message in st.session_state.messages:
    with st.chat_message(message["role"]):
        st.write(message["content"])

def clear_chat_history():
    st.session_state.messages = [{"role": "assistant", "content": "How may I assist you today?"}]
st.sidebar.button('Clear Chat History', on_click=clear_chat_history)

# Function for generating LLaMA2 response. Refactored from https://github.com/a16z-infra/llama2-chatbot
def generate_llama2_response(prompt_input):
    string_dialogue = "You are a helpful assistant. You do not respond as 'User' or pretend to be 'User'. You only respond once as 'Assistant'."
    for dict_message in st.session_state.messages:
        if dict_message["role"] == "user":
            string_dialogue += "User: " + dict_message["content"] + "\n\n"
        else:
            string_dialogue += "Assistant: " + dict_message["content"] + "\n\n"
    output = replicate.run('arierie/phi_2-finetuned-lyrics:48a60dd8d863735436eed0a6ff3b108d84fd333321c75441c57ac6a7cd75dbeb', 
                           input={"user_prompt": "{prompt_input}",
                                  "max_new_tokens": max_token})
    return output

# User-provided prompt
if prompt := st.chat_input(disabled=not replicate_api):
    st.session_state.messages.append({"role": "user", "content": prompt})
    with st.chat_message("user"):
        st.write(prompt)

# Generate a new response if last message is not from assistant
if st.session_state.messages[-1]["role"] != "assistant":
    with st.chat_message("assistant"):
        with st.spinner("Thinking..."):
            response = generate_llama2_response(prompt)
            placeholder = st.empty()
            full_response = ''
            for item in response:
                full_response += item
                placeholder.markdown(full_response)
            placeholder.markdown(full_response)
    message = {"role": "assistant", "content": full_response}
    st.session_state.messages.append(message)
