package com.servicos;

import java.util.Scanner;

import com.GerenciadorDeMagos.Gerenciador;
import com.personagem.MagoArcano;
import com.personagem.MagoElemental;
import com.personagem.MagoSombrio;
import com.personagem.Ranqueados;

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

        int idExistente = 0;
        int id = -1;
        while (idExistente == 0) {

            System.out.print("Digite o ID do mago: ");
            id = scanner.nextInt();
            if (gerenciador.buscarPorId(id) != null || id == 0) {
                System.out.println("id já existente, tente outro!");
            } else if (id <= 0) {
                System.out.println("Por favor, apenas números acima de zero....");
            } else {
                idExistente = 1;
            }

            scanner.nextLine();
        }

        System.out.print("Digite o Codinome do mago: ");
        String codinome = scanner.nextLine();

        System.out.print("Digite o Foco (Cajado, Varinha, etc): ");
        String foco = scanner.nextLine();

        System.out.print("Digite o Controlador (1 para Humano, 2 para IA): ");
        int controlador = scanner.nextInt();
        while (controlador < 1 || controlador > 2) {
            System.out.println("Valor inválido, digite novamente: ");
            controlador = scanner.nextInt();
        }

        System.out.println("\nAgora você tem 10 pontos para distribuir nos seguintes atributos:");
        System.out.println("Vida, Mana, Poder base, Resistência e Velocidade.\n");

        int ptsAtributos = 10;
        int acumulador = 0;

        System.out.println("Pontos restantes: " + ptsAtributos);
        System.out.print("Vida máxima base é 10, 5 para cada ponto extra, digite a quantidade de pontos: ");

        acumulador = scanner.nextInt();
        while (acumulador < 0 || acumulador > ptsAtributos) {
            System.out.println("Apenas valores de zero até " + ptsAtributos);
            System.out.println("Digite novamente: ");
            acumulador = scanner.nextInt();
        }
        int vidaMax = 10 + 5 * acumulador;
        System.out.println("Vida máxima: " + vidaMax + "\n");
        ptsAtributos = ptsAtributos - acumulador;
        acumulador = 0;

        if (ptsAtributos == 0) {
            System.out.println("Sem pontos restantes!");
        }

        if (ptsAtributos != 0) {
            System.out.println("Pontos restantes: " + ptsAtributos);
            System.out.print("Mana máxima base é 10, 5 para cada ponto extra, digite a quantidade de pontos: ");
            acumulador = scanner.nextInt();
        }
        if (ptsAtributos == 0) {
            System.out.println("Sem pontos restantes!");
        }
        while (acumulador < 0 || acumulador > ptsAtributos) {
            System.out.println("Apenas valores de zero até " + ptsAtributos);
            System.out.println("Digite novamente: ");
            acumulador = scanner.nextInt();
        }
        int manaMax = 10 + 5 * acumulador;
        System.out.println("Mana máxima: " + manaMax + "\n");
        ptsAtributos = ptsAtributos - acumulador;
        acumulador = 0;

        if (ptsAtributos != 0) {
            System.out.println("Pontos restantes: " + ptsAtributos);
            System.out.print("Poder base é 10, 5 para cada ponto extra, digite a quantidade de pontos: ");
            acumulador = scanner.nextInt();
        }
        if (ptsAtributos == 0) {
            System.out.println("Sem pontos restantes!");
        }
        while (acumulador < 0 || acumulador > ptsAtributos) {
            System.out.println("Apenas valores de zero até " + ptsAtributos);
            System.out.println("Digite novamente: ");
            acumulador = scanner.nextInt();
        }
        int poderBase = 10 + 5 * acumulador;
        System.out.println("Pode base: " + poderBase + "\n");
        ptsAtributos = ptsAtributos - acumulador;
        acumulador = 0;

        if (ptsAtributos != 0) {
            System.out.println("Pontos restantes: " + ptsAtributos);
            System.out.print("Resistência Mágica base é 10, 5 para cada ponto extra, digite a quantidade de pontos: ");
            acumulador = scanner.nextInt();
        }
        if (ptsAtributos == 0) {
            System.out.println("Sem pontos restantes!");
        }
        while (acumulador < 0 || acumulador > ptsAtributos) {
            System.out.println("Apenas valores de zero até " + ptsAtributos);
            System.out.println("Digite novamente: ");
            acumulador = scanner.nextInt();
        }
        int resistencia = 10 + 5 * acumulador;
        System.out.println("Resistência base: " + resistencia + "\n");
        ptsAtributos = ptsAtributos - acumulador;
        acumulador = 0;

        if (ptsAtributos != 0) {
            System.out.println("Pontos restantes: " + ptsAtributos);
            System.out.print("Digite a Velocidade: ");
            acumulador = scanner.nextInt();
        }
        if (ptsAtributos == 0) {
            System.out.println("Sem pontos restantes!");
        }
        while (acumulador < 0 || acumulador > ptsAtributos) {
            System.out.println("Apenas valores de zero até " + ptsAtributos);
            System.out.println("Digite novamente: ");
            acumulador = scanner.nextInt();
        }
        int velocidade = 1 + acumulador;
        System.out.println("Velocidade: " + velocidade + "\n");
        ptsAtributos = ptsAtributos - acumulador;
        acumulador = 0;

        scanner.nextLine();

        Ranqueados novoMago = null;

        switch (tipo) {
            case 1:
                novoMago = new MagoElemental(id, codinome, vidaMax, manaMax, foco, poderBase, resistencia, controlador,
                        velocidade);
                break;
            case 2:
                novoMago = new MagoArcano(id, codinome, vidaMax, manaMax, foco, poderBase, resistencia, controlador,
                        velocidade);
                break;
            case 3:
                novoMago = new MagoSombrio(id, codinome, vidaMax, manaMax, foco, poderBase, resistencia, controlador,
                        velocidade);
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