package br.com.dio;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.model.AccountWallet;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.stream.Collectors.toList;

public class Main {

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("BOAS VINDAS AO DIO BANK! QUE BOM VER VOCE AQUI! ");
        displayMainMenu();



    }
    public static void displayMainMenu() {
        int option;
        do {
            System.out.println("=======================================");
            System.out.println("           MENU PRINCIPAL");
            System.out.println("=======================================");
            System.out.println("1 - Menu de Contas");
            System.out.println("2 - Menu de Investimentos");
            System.out.println("3 - Historico de Transacoes\n");
            System.out.println("=======================================");
            System.out.println("0 - Sair");
            System.out.println("=======================================\n");
            System.out.print(">> ");
            option = scanner.nextInt();
            switch (option) {
                case 1 -> displayAccountsMenu();
                case 2 -> displayInvestmentsMenu();
                case 3 -> checkHistory();
                case 0 -> {
                    System.out.println("Encerrando o programa...");
                    return;
                }
                default -> System.out.println("Opcao invalida");

            }
        } while (true);
    }

    public static void displayAccountsMenu() {
        int accountOption;
        while (true) {
            System.out.println("=======================================");
            System.out.println("               CONTA");
            System.out.println("=======================================");
            System.out.println("1 - Abrir uma conta");
            System.out.println("2 - Realizar um deposito");
            System.out.println("3 - Realizar um saque");
            System.out.println("4 - Transferir entre contas");
            System.out.println("5 - Listar contas");
            System.out.println("=======================================");
            System.out.println("0 - Retornar ao Menu Principal");
            System.out.println("=======================================\n");
            System.out.print(">> ");
            accountOption = scanner.nextInt();
            switch(accountOption) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> transferToAccount();
                case 5 -> accountRepository.list().forEach(System.out::println);
                case 0 -> {
                    System.out.println("Retornando ao menu principal...");
                    return;
                }
                default -> System.out.println("Insira uma opção válida.");
            }
        }
    }

    public static void displayInvestmentsMenu() {
        int investmentOption;
        while (true) {
            System.out.println("=======================================");
            System.out.println("            INVESTIMENTOS");
            System.out.println("=======================================");
            System.out.println("1 - Criar um investimento");
            System.out.println("2 - Fazer um investimento");
            System.out.println("3 - Investir");
            System.out.println("4 - Resgatar investimento");
            System.out.println("5 - Listar investimentos");
            System.out.println("6 - Listar carteiras de investimento");
            System.out.println("7 - Atualizar investimentos");
            System.out.println("=======================================");
            System.out.println("0 - Retornar ao Menu Principal");
            System.out.println("=======================================\n");
            System.out.print(">> ");
            investmentOption = scanner.nextInt();
            switch (investmentOption) {
                case 1 -> createInvestment();
                case 2 -> createWalletInvestment();
                case 3 -> incInvestment();
                case 4 -> rescueInvestment();
                case 5 -> investmentRepository.list().forEach(System.out::println);
                case 6 -> investmentRepository.listWallets().forEach(System.out::println);
                case 7 -> {
                    investmentRepository.listWallets().forEach(System.out::println);
                    System.out.println("Investimentos reajustados");
                }
                case 0 -> {
                    System.out.println("Retornando ao menu principal...");
                    return;
                }
                default -> System.out.println("Digite uma opção válida.");
            }
        }

    }

    private static void createAccount() {
        System.out.println("A abertura da sua conta e segura e rapida. Basta cadastrar as suas chaves PIX.");
        System.out.println("Importante: Separe as chaves PIX com ';' (ponto e virgula). ");
        System.out.print(">> ");
        var pix = Arrays.stream(scanner.next().split(";")).toList();
        System.out.println("Agora, insira o valor do deposito inicial da conta: ");
        System.out.print(">> ");
        var amount = scanner.nextLong();
        var wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada: " + wallet);
    }

    private static void createInvestment() {
        System.out.println("Informe a taxa do investimento: ");
        System.out.print(">> ");
        var tax = Arrays.stream(scanner.next().split(";")).toList();
        System.out.println("Agora, informe o valor a ser depositado: ");
        System.out.print(">> ");
        var initialFunds = scanner.nextLong();
        var investment = accountRepository.create(tax, initialFunds);
        System.out.println("Investimento criado: " + investment);
    }
    // 2
    private static void deposit() {
        System.out.println("Para realizar um depósito, informe a chave PIX da conta: ");
        var pix = scanner.next();
        System.out.println("Informe o valor a ser depositado: ");
        System.out.print(">> ");
        var amount = scanner.nextLong();
        try {
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    // 3
    private static void withdraw() {
        System.out.println("Para realizar um saque, informe a chave PIX da conta: ");
        var pix = scanner.next();
        System.out.println("Informe o valor a ser sacado: ");
        System.out.print(">> ");
        var amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void transferToAccount() {
        System.out.println("Insira a chave PIx de origem: ");
        System.out.print(">> ");
        var source = scanner.next();
        System.out.println("Para quem você quer pagar? Insira a chave PIX do destinatário: ");
        System.out.print(">> ");
        var target = scanner.next();
        System.out.println("Informe o valor da transferência: ");
        System.out.print(">> ");
        var amount = scanner.nextLong();
        try {
            accountRepository.transferMoney(source, target, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());

        }
    }

    private static void createWalletInvestment() {
        System.out.println("Informe a chave PIX da conta: ");
        System.out.print(">> ");
        var pix = scanner.next();
        var account = accountRepository.findByPix(pix);
        System.out.println("Informe o identificador do investimento: ");
        System.out.print(">> ");
        var investmentId = scanner.nextInt();
        var investmentWallet = investmentRepository.initInvestment(account, investmentId);
        System.out.println("Conta de investimento criada: "+ investmentWallet);
    }

    private static void incInvestment() {
        System.out.println("Para investir, informe a chave PIX da conta: ");
        var pix = scanner.next();
        System.out.println("Informe o valor a ser investido: ");
        System.out.print(">> ");
        var amount = scanner.nextLong();
        try {
            investmentRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void rescueInvestment() {
        System.out.println("Para resgatar um investimento, informe a chave PIX da conta: ");
        System.out.print(">> ");
        var pix = scanner.next();
        System.out.println("Informe o valor a ser resgatado: ");
        System.out.print(">> ");
        var amount = scanner.nextLong();
        try {
            investmentRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void checkHistory() {
        System.out.println("Para consultar o extrato, informe a chave PIX da conta: ");
        System.out.print(">> ");
        var pix = scanner.next();
        AccountWallet wallet;
        try {
            wallet = accountRepository.findByPix(pix);
            var audit = wallet.getFinancialTransactions();
            var group = audit.stream()
                    .collect(Collectors.groupingBy(t -> t.createdAt().truncatedTo(SECONDS)));
            group.forEach((time, transactions) -> {
                System.out.println("Transações em: " + time);
                transactions.forEach(System.out::println);
            });
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
