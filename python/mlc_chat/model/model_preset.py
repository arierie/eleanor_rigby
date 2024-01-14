"""A builtin set of models available in MLC LLM."""
from typing import Any, Dict

MODEL_PRESETS: Dict[str, Any] = {
    "llama2_7b": {
        "architectures": ["LlamaForCausalLM"],
        "bos_token_id": 1,
        "eos_token_id": 2,
        "hidden_act": "silu",
        "hidden_size": 4096,
        "initializer_range": 0.02,
        "intermediate_size": 11008,
        "max_position_embeddings": 2048,
        "model_type": "llama",
        "num_attention_heads": 32,
        "num_hidden_layers": 32,
        "num_key_value_heads": 32,
        "pad_token_id": 0,
        "pretraining_tp": 1,
        "rms_norm_eps": 1e-05,
        "rope_scaling": None,
        "tie_word_embeddings": False,
        "torch_dtype": "float16",
        "transformers_version": "4.31.0.dev0",
        "use_cache": True,
        "vocab_size": 32000,
        "context_window_size": 2048,
        "prefill_chunk_size": 2048,
    },
    "llama2_13b": {
        "_name_or_path": "meta-llama/Llama-2-13b-hf",
        "architectures": ["LlamaForCausalLM"],
        "bos_token_id": 1,
        "eos_token_id": 2,
        "hidden_act": "silu",
        "hidden_size": 5120,
        "initializer_range": 0.02,
        "intermediate_size": 13824,
        "max_position_embeddings": 2048,
        "model_type": "llama",
        "num_attention_heads": 40,
        "num_hidden_layers": 40,
        "num_key_value_heads": 40,
        "pad_token_id": 0,
        "pretraining_tp": 2,
        "rms_norm_eps": 1e-05,
        "rope_scaling": None,
        "tie_word_embeddings": False,
        "torch_dtype": "float16",
        "transformers_version": "4.31.0.dev0",
        "use_cache": True,
        "vocab_size": 32000,
        "context_window_size": 2048,
        "prefill_chunk_size": 2048,
    },
    "llama2_70b": {
        "architectures": ["LlamaForCausalLM"],
        "bos_token_id": 1,
        "eos_token_id": 2,
        "hidden_act": "silu",
        "hidden_size": 8192,
        "initializer_range": 0.02,
        "intermediate_size": 28672,
        "max_position_embeddings": 2048,
        "model_type": "llama",
        "num_attention_heads": 64,
        "num_hidden_layers": 80,
        "num_key_value_heads": 8,
        "pad_token_id": 0,
        "rms_norm_eps": 1e-05,
        "tie_word_embeddings": False,
        "torch_dtype": "float16",
        "transformers_version": "4.31.0.dev0",
        "use_cache": True,
        "vocab_size": 32000,
        "context_window_size": 2048,
        "prefill_chunk_size": 2048,
    },
    "codellama_7b": {
        "_name_or_path": "codellama/CodeLlama-7b-hf",
        "architectures": ["LlamaForCausalLM"],
        "bos_token_id": 1,
        "eos_token_id": 2,
        "hidden_act": "silu",
        "hidden_size": 4096,
        "initializer_range": 0.02,
        "intermediate_size": 11008,
        "max_position_embeddings": 16384,
        "model_type": "llama",
        "num_attention_heads": 32,
        "num_hidden_layers": 32,
        "num_key_value_heads": 32,
        "pretraining_tp": 1,
        "rms_norm_eps": 1e-05,
        "rope_scaling": None,
        "rope_theta": 1000000,
        "tie_word_embeddings": False,
        "torch_dtype": "bfloat16",
        "transformers_version": "4.33.0.dev0",
        "use_cache": True,
        "vocab_size": 32016,
        "context_window_size": 2048,
        "prefill_chunk_size": 2048,
    },
    "codellama_13b": {
        "architectures": ["LlamaForCausalLM"],
        "bos_token_id": 1,
        "eos_token_id": 2,
        "hidden_act": "silu",
        "hidden_size": 5120,
        "initializer_range": 0.02,
        "intermediate_size": 13824,
        "max_position_embeddings": 16384,
        "model_type": "llama",
        "num_attention_heads": 40,
        "num_hidden_layers": 40,
        "num_key_value_heads": 40,
        "pretraining_tp": 1,
        "rms_norm_eps": 1e-05,
        "rope_scaling": None,
        "rope_theta": 1000000,
        "tie_word_embeddings": False,
        "torch_dtype": "bfloat16",
        "transformers_version": "4.32.0.dev0",
        "use_cache": True,
        "vocab_size": 32016,
        "context_window_size": 2048,
        "prefill_chunk_size": 2048,
    },
    "codellama_34b": {
        "architectures": ["LlamaForCausalLM"],
        "bos_token_id": 1,
        "eos_token_id": 2,
        "hidden_act": "silu",
        "hidden_size": 8192,
        "initializer_range": 0.02,
        "intermediate_size": 22016,
        "max_position_embeddings": 16384,
        "model_type": "llama",
        "num_attention_heads": 64,
        "num_hidden_layers": 48,
        "num_key_value_heads": 8,
        "pretraining_tp": 1,
        "rms_norm_eps": 1e-05,
        "rope_scaling": None,
        "rope_theta": 1000000,
        "tie_word_embeddings": False,
        "torch_dtype": "bfloat16",
        "transformers_version": "4.32.0.dev0",
        "use_cache": True,
        "vocab_size": 32016,
        "context_window_size": 2048,
        "prefill_chunk_size": 2048,
    },
    "mistral_7b": {
        "architectures": ["MistralForCausalLM"],
        "bos_token_id": 1,
        "eos_token_id": 2,
        "hidden_act": "silu",
        "hidden_size": 4096,
        "initializer_range": 0.02,
        "intermediate_size": 14336,
        "max_position_embeddings": 32768,
        "model_type": "mistral",
        "num_attention_heads": 32,
        "num_hidden_layers": 32,
        "num_key_value_heads": 8,
        "rms_norm_eps": 1e-05,
        "rope_theta": 10000.0,
        "tie_word_embeddings": False,
        "torch_dtype": "bfloat16",
        "transformers_version": "4.34.0.dev0",
        "use_cache": True,
        "vocab_size": 32000,
        "sliding_window_size": 4096,
        "prefill_chunk_size": 128,
        "attention_sink_size": 4,
    },
    "gpt2": {
        "architectures": ["GPT2LMHeadModel"],
        "bos_token_id": 50256,
        "eos_token_id": 50256,
        "hidden_act": "gelu_new",
        "n_embd": 768,
        "initializer_range": 0.02,
        "n_positions": 1024,
        "model_type": "gpt2",
        "n_head": 12,
        "n_layer": 12,
        "layer_norm_epsilon": 1e-05,
        "transformers_version": "4.26.0.dev0",
        "use_cache": True,
        "vocab_size": 50257,
        "context_window_size": 2048,
        "prefill_chunk_size": 2048,
    },
    "redpajama_3b_v1": {
        "_name_or_path": "/root/fm/models/rp_3b_800b_real_fp16",
        "architectures": ["GPTNeoXForCausalLM"],
        "bos_token_id": 0,
        "eos_token_id": 0,
        "hidden_act": "gelu",
        "hidden_size": 2560,
        "initializer_range": 0.02,
        "intermediate_size": 10240,
        "layer_norm_eps": 1e-05,
        "max_position_embeddings": 2048,
        "model_type": "gpt_neox",
        "num_attention_heads": 32,
        "num_hidden_layers": 32,
        "rotary_emb_base": 10000,
        "rotary_pct": 1.0,
        "tie_word_embeddings": False,
        "torch_dtype": "float16",
        "transformers_version": "4.28.1",
        "use_cache": True,
        "use_parallel_residual": False,
        "vocab_size": 50432,
    },
    "phi-1_5": {
        "_name_or_path": "microsoft/phi-1_5",
        "activation_function": "gelu_new",
        "architectures": ["PhiForCausalLM"],
        "attn_pdrop": 0.0,
        "auto_map": {
            "AutoConfig": "configuration_phi.PhiConfig",
            "AutoModelForCausalLM": "modeling_phi.PhiForCausalLM",
        },
        "embd_pdrop": 0.0,
        "flash_attn": False,
        "flash_rotary": False,
        "fused_dense": False,
        "initializer_range": 0.02,
        "layer_norm_epsilon": 1e-05,
        "model_type": "phi-msft",
        "n_embd": 2048,
        "n_head": 32,
        "n_head_kv": None,
        "n_inner": None,
        "n_layer": 24,
        "n_positions": 2048,
        "resid_pdrop": 0.0,
        "rotary_dim": 32,
        "tie_word_embeddings": False,
        "torch_dtype": "float16",
        "transformers_version": "4.34.1",
        "vocab_size": 51200,
    },
    "phi-2": {
        "_name_or_path": "microsoft/phi-2",
        "activation_function": "gelu_new",
        "architectures": ["PhiForCausalLM"],
        "attn_pdrop": 0.0,
        "auto_map": {
            "AutoConfig": "configuration_phi.PhiConfig",
            "AutoModelForCausalLM": "modeling_phi.PhiForCausalLM",
        },
        "embd_pdrop": 0.0,
        "flash_attn": False,
        "flash_rotary": False,
        "fused_dense": False,
        "img_processor": None,
        "initializer_range": 0.02,
        "layer_norm_epsilon": 1e-05,
        "model_type": "phi-msft",
        "n_embd": 2560,
        "n_head": 32,
        "n_head_kv": None,
        "n_inner": None,
        "n_layer": 32,
        "n_positions": 2048,
        "resid_pdrop": 0.1,
        "rotary_dim": 32,
        "tie_word_embeddings": False,
        "torch_dtype": "float16",
        "transformers_version": "4.35.2",
        "vocab_size": 51200,
    },
}
