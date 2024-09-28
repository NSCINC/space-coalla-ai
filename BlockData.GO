Para adaptar este código para Go e realizar uma solicitação HTTP POST para um servidor local, você pode usar a biblioteca padrão `net/http`. Aqui está como você pode fazer isso:

```go
package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
)

// BlockData representa os dados de um bloco
type BlockData struct {
	Number       int              `json:"number"`
	ParentHash   string           `json:"parentHash"`
	Coinbase     string           `json:"coinbase"`
	ExtraData    string           `json:"extraData"`
	Transactions []TransactionData `json:"transactions"`
}

// TransactionData representa os dados de uma transação
type TransactionData struct {
	From string `json:"from"`
	To   string `json:"to"`
	Data string `json:"data"`
}

func main() {
	// Define a URL para fazer a solicitação POST
	url := "http://localhost:8080/import-blocks"

	// Define os dados do bloco que deseja importar
	blockData := BlockData{
		Number:     123,
		ParentHash: "0xabc...",
		Coinbase:   "0x123...",
		ExtraData:  "0xabc123...",
		Transactions: []TransactionData{
			{From: "0xabc...", To: "0xdef...", Data: "0x123..."},
			{From: "0xdef...", To: "0xghi...", Data: "0x456..."},
		},
	}

	// Codifica os dados em JSON
	requestBody, err := json.Marshal(blockData)
	if err != nil {
		fmt.Println("Erro ao codificar os dados do bloco:", err)
		return
	}

	// Faz a solicitação POST para o servidor
	response, err := http.Post(url, "application/json", bytes.NewBuffer(requestBody))
	if err != nil {
		fmt.Println("Erro ao fazer a solicitação POST:", err)
		return
	}
	defer response.Body.Close()

	// Verifica o código de status da resposta
	if response.StatusCode == http.StatusOK {
		fmt.Println("Blocos importados com sucesso")
	} else {
		fmt.Println("Falha ao importar blocos:", response.Status)
	}
}
```

Certifique-se de ajustar a URL para corresponder ao seu servidor e aos dados do bloco conforme necessário. Este código faz uma solicitação HTTP POST para o servidor local na porta 8080, enviando os dados do bloco como JSON no corpo da solicitação. Ele imprime uma mensagem de sucesso se a solicitação for bem-sucedida ou uma mensagem de falha caso contrário.