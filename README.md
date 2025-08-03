# Carteira Bancária (Wallet App)

Este sistema simula um ambiente bancário simplificado, com funcionalidades de criação de contas, transações financeiras, controle de saldo e investimentos.

## 🚀 Algumas Funcionalidades

- Criar contas bancárias com saldo inicial e chaves PIX
- Realizar saques, depósitos e transferências entre contas
- Listar contas criadas
- Realizar investimentos
- Histórico de transações financeiras

## 🧱 Estrutura do Projeto

O sistema é dividido em:

- `AccountWallet`: representa uma carteira bancária comum (conta corrente)
- `InvestmentWallet`: representa uma carteira de investimento
- `Money`: representa uma unidade de valor (centavo)
- `MoneyAudit`: registra o histórico de cada centavo movimentado
- `Wallet`: classe abstrata base para carteiras
- `BankService`: enum que identifica o tipo de serviço (ACCOUNT ou INVESTMENT)

## ✍️ Créditos
Projeto criado a partir de um modelo fornecido pelo professor no bootcamp **NTT DATA - Java e IA para Iniciantes**, da plataforma DIO.
Modificado e documentado por Rodrigo Macedo.

---

**Modificado e documentado por Rodrigo Macedo**



