package com.Menu;
import java.util.Scanner;
public class Menu {

    Scanner scan = new Scanner(System.in);
    public void menuPrincipal() {
        System.out.println("--- MENU PRINCIPAL ---");
        System.out.println("1. Criar Mago");
        System.out.println("2. Listar Magos");
        System.out.println("3. Agendar Duelo 1v1");
        System.out.println("4. Iniciar Duelo");
        System.out.println("5. Salvar Magos em CSV");
        System.out.println("6. Carregar Magos de CSV");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
        int opcao = scan.nextInt();
        switch (opcao) {
            case 1:
                System.out.println("Opção 1 selecionada: Criar Mago");
                break;
            case 2:
                System.out.println("Opção 2 selecionada: Listar Magos");
                break;
            case 3:
                System.out.println("Opção 3 selecionada: Agendar Duelo 1v1");
                break;
            case 4:
                System.out.println("Opção 4 selecionada: Iniciar Duelo");
                break;
            case 5:
                System.out.println("Opção 5 selecionada: Salvar Magos em CSV");
                break;
            case 6:
                System.out.println("Opção 6 selecionada: Carregar Magos de CSV");
                break;
            case 0:
                System.out.println("Saindo do programa...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                break;
        }
    
    }
}