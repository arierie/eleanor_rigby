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
    max_token = st.sidebar.slider('max_token', min_value=128, max_value=1028, value=512, step=8)

# Store LLM generated responses
if "messages" not in st.session_state.keys():
    st.session_state.messages = [{"role": "assistant", "content": "How may I assist you today?"}]

# Display or clear chat messages
for message in st.session_state.messages:
    with st.chat_message(message["role"]):
        st.write(message["content"])

def clear_chat_history():
    st.session_state.messages = [{"role": "assistant", "content": "Input a prompt that can be song lyrics e.g. 'yesterday, i saw you in my dream'"}]
st.sidebar.button('Clear Chat History', on_click=clear_chat_history)

# Function for generating LLM response
def generate_llama2_response(prompt_input):
    output = replicate.run('arierie/phi_2-finetuned-lyrics:48a60dd8d863735436eed0a6ff3b108d84fd333321c75441c57ac6a7cd75dbeb', 
                           input={"user_prompt": prompt_input,
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
