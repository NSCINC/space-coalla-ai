#!/usr/bin/env bash

# Copyright (c) Meta Platforms, Inc. and affiliates.
# Licensed under the Llama 2 Community License Agreement.

set -e

echo ""
read -p "Enter the list of models to download without spaces (7B,13B,70B,7B-chat,13B-chat,70B-chat), or press Enter for all: " MODEL_SIZE
TARGET_FOLDER="./sea_models"  # Pasta de destino para os arquivos
mkdir -p ${TARGET_FOLDER}

# Se o usuário não inserir um tamanho de modelo, baixar todos
if [[ $MODEL_SIZE == "" ]]; then
    MODEL_SIZE="7B,13B,70B,7B-chat,13B-chat,70B-chat"
fi

echo "Iniciando o download dos arquivos de LICENSE e Acceptable Usage Policy"
wget --continue ${PRESIGNED_URL/'*'/"LICENSE"} -O ${TARGET_FOLDER}"/LICENSE"
wget --continue ${PRESIGNED_URL/'*'/"USE_POLICY.md"} -O ${TARGET_FOLDER}"/USE_POLICY.md"

echo "Iniciando o download do tokenizer"
wget --continue ${PRESIGNED_URL/'*'/"tokenizer.model"} -O ${TARGET_FOLDER}"/tokenizer.model"
wget --continue ${PRESIGNED_URL/'*'/"tokenizer_checklist.chk"} -O ${TARGET_FOLDER}"/tokenizer_checklist.chk"

# Verificação de integridade do tokenizer
(cd ${TARGET_FOLDER} && md5sum -c tokenizer_checklist.chk)

# Função para download de modelos paralelamente
download_model() {
    local MODEL_PATH=$1
    local SHARD=$2
    echo "Downloading ${MODEL_PATH}"

    mkdir -p ${TARGET_FOLDER}/${MODEL_PATH}

    for s in $(seq -f "0%g" 0 ${SHARD})
    do
        wget --continue ${PRESIGNED_URL/'*'/"${MODEL_PATH}/consolidated.${s}.pth"} -O ${TARGET_FOLDER}/${MODEL_PATH}/consolidated.${s}.pth
    done

    wget --continue ${PRESIGNED_URL/'*'/"${MODEL_PATH}/params.json"} -O ${TARGET_FOLDER}/${MODEL_PATH}/params.json
    wget --continue ${PRESIGNED_URL/'*'/"${MODEL_PATH}/checklist.chk"} -O ${TARGET_FOLDER}/${MODEL_PATH}/checklist.chk

    echo "Verificando integridade do modelo ${MODEL_PATH}"
    (cd ${TARGET_FOLDER}/${MODEL_PATH} && md5sum -c checklist.chk)
}

# Loop pelos modelos solicitados
for m in ${MODEL_SIZE//,/ }
do
    case $m in
        "7B") SHARD=0; MODEL_PATH="llama-2-7b";;
        "7B-chat") SHARD=0; MODEL_PATH="llama-2-7b-chat";;
        "13B") SHARD=1; MODEL_PATH="llama-2-13b";;
        "13B-chat") SHARD=1; MODEL_PATH="llama-2-13b-chat";;
        "70B") SHARD=7; MODEL_PATH="llama-2-70b";;
        "70B
