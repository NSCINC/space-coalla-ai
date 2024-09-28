Para adaptar o código para o protocolo NBH, podemos seguir o exemplo fornecido e realizar as modificações necessárias para integrar a autenticação de chaves NBH. Aqui está a versão adaptada em Go:

```go
package main

import (
	"context"
	"fmt"
	"math/big"

	"github.com/ethereum/go-ethereum/accounts/abi"
	"github.com/ethereum/go-ethereum/common"
	"github.com/ethereum/go-ethereum/crypto"
	"github.com/ethereum/go-ethereum/rpc"
	"github.com/nbhauth/nbhauth"
)

// TES representa o contrato TES (Token de Margem de 6 Meses)
type TES struct {
	Name                string
	Symbol              string
	AnnualInterestRate  *big.Int // 0.9% expresso em pontos-base
	SecondsInYear       *big.Int // Número de segundos em um ano
	TotalSupply         *big.Int
	ReleaseDate         *big.Int // Data de liberação dos fundos
	ContractAddress     common.Address
	Client              *rpc.Client
	Authenticator       nbhauth.Authenticator
}

// NewTES cria uma nova instância do contrato TES
func NewTES(client *rpc.Client, contractAddress common.Address, authenticator nbhauth.Authenticator) *TES {
	return &TES{
		Name:                "Token de Margem de 6 Meses",
		Symbol:              "TES",
		AnnualInterestRate:  big.NewInt(90),       // 0.9%
		SecondsInYear:       big.NewInt(31536000), // Número de segundos em um ano
		TotalSupply:         big.NewInt(0),
		ReleaseDate:         big.NewInt(0), // Data de liberação dos fundos
		ContractAddress:     contractAddress,
		Client:              client,
		Authenticator:       authenticator,
	}
}

// Deposit realiza um depósito no contrato TES
func (t *TES) Deposit(amount *big.Int) (*big.Int, error) {
	account, err := t.Authenticator.GetAccount()
	if err != nil {
		return nil, err
	}

	data, err := abi.JSON(strings.NewReader(tesABI)) // ABI do contrato TES
	if err != nil {
		return nil, err
	}

	packed, err := data.Pack("deposit", amount)
	if err != nil {
		return nil, err
	}

	// Construir e enviar a transação
	tx := rpc.NewTransaction(nonce, t.ContractAddress, amount, gasLimit, gasPrice, packed)
	err = rpc.SendTransaction(context.Background(), t.Client, tx)
	if err != nil {
		return nil, err
	}

	return nil, nil
}

// Withdraw realiza uma retirada do contrato TES
func (t *TES) Withdraw(amount *big.Int) (*big.Int, error) {
	// Implementação similar a Deposit
}

// GetBalance retorna o saldo de uma conta no contrato TES
func (t *TES) GetBalance(account common.Address) (*big.Int, error) {
	// Implementação similar a Deposit
}

// GetTokenBalance retorna o saldo de tokens de uma conta no contrato TES
func (t *TES) GetTokenBalance(account common.Address) (*big.Int, error) {
	// Implementação similar a Deposit
}

func main() {
	client, err := rpc.Dial("http://localhost:8545") // Conectar ao nó Ethereum
	if err != nil {
		fmt.Println("Erro ao conectar ao nó Ethereum:", err)
		return
	}

	authenticator := nbhauth.NewAuthenticator() // Inicializar o autenticador NBH

	// Endereço do contrato TES
	contractAddress := common.HexToAddress("0x123...")

	// Criar uma nova instância do contrato TES
	tes := NewTES(client, contractAddress, authenticator)

	// Realizar operações no contrato TES
	// Por exemplo, depositar, retirar, obter saldo, etc.
}
```

Neste exemplo:

- Foi adicionada uma estrutura `TES` para representar o contrato TES, incluindo métodos para depositar, retirar e obter saldos.
- Foi criado um método `Deposit` para realizar um depósito no contrato TES, integrando a autenticação de chaves NBH.
- Foi utilizado o pacote `nbhauth` para fornecer a implementação do autenticador NBH.
- Os métodos `Withdraw`, `GetBalance` e `GetTokenBalance` podem ser implementados de forma semelhante ao método `Deposit`.

Certifique-se de ajustar a implementação conforme necessário para atender às suas necessidades específicas e substituir os valores de placeholders como o endereço do contrato e as configurações de conexão RPC.