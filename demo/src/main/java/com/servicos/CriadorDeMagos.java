package com.servicos;

import java.util.Scanner;

import com.GerenciadorDeMagos.Gerenciador;
import com.personagem.MagoElemental;
import com.personagem.Ranqueados;
import com.servicos.Servicos;
public class CriadorDeMagos extends Servicos {

    
    public CriadorDeMagos(Scanner scanner, Gerenciador gerenciador) {
        super(scanner, gerenciador);
    }

    @Override
    public void executar() {
        System.out.println("\n--- CRIAÇÃO DE NOVO MAGO ---");
        System.out.println("Escolha o tipo de Mago:");
        System.out.println("1. Mago Elemental");
        System.out.println("2. Mago Arcano");
        System.out.println("3. Mago Sombrio");
        System.out.print("Opção: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo < 1 || tipo > 3) {
            System.out.println("Opção inválida. Retornando ao menu principal.");
            return;
        }

        System.out.print("Digite o ID do mago: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o Codinome do mago: ");
        String codinome = scanner.nextLine();

        System.out.print("Digite a Vida Máxima: ");
        int vidaMax = scanner.nextInt();

        System.out.print("Digite a Mana Máxima: ");
        int manaMax = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o Foco (Cajado, Varinha, etc): ");
        String foco = scanner.nextLine();

        System.out.print("Digite o Poder Base: ");
        int poderBase = scanner.nextInt();

        System.out.print("Digite a Resistência Mágica: ");
        int resistencia = scanner.nextInt();

        System.out.print("Digite o Controlador (1 para Humano, 2 para IA): ");
        int controlador = scanner.nextInt();
        scanner.nextLine();

        Ranqueados novoMago = null;

        switch (tipo) {
            case 1:
                System.out.print("Digite o valor para 'alto': ");
                int alto = scanner.nextInt();
                scanner.nextLine();
                novoMago = new MagoElemental(id, codinome, vidaMax, manaMax, foco, poderBase, resistencia, controlador, alto);
                break;
            case 2:
                System.out.println("Criação de Mago Arcano ainda não implementada.");
                break;
            case 3:
                System.out.println("Criação de Mago Sombrio ainda não implementada.");
                break;
        }

        if (novoMago != null) {
            this.gerenciador.adicionar(novoMago);
            System.out.println(novoMago.getClass().getSimpleName() + " criado com sucesso!");
            System.out.println(novoMago); 
        }

        System.out.println("\nPressione Enter para voltar ao menu...");
        scanner.nextLine();
    }
}