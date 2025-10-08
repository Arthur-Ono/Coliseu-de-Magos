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

        System.out.print("Digite o ID do mago: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o Codinome do mago: ");
        String codinome = scanner.nextLine();

        System.out.print("Digite o Foco (Cajado, Varinha, etc): ");
        String foco = scanner.nextLine();

        System.out.print("Digite o Controlador (1 para Humano, 2 para IA): ");
        int controlador = scanner.nextInt();

        System.out.println("Agora você tem 10 pontos para distribuir nos seguintes atributos:");
        System.out.println("Vida, Mana, Poder base, Resistência e Velocidade.");

        int ptsAtributos = 10;
        int acumulador = 0;

        System.out.println("Pontos restantes: " + ptsAtributos);
        System.out.print("Vida máxima base é 10, 5 para cada ponto extra, digite a quantidade de pontos: ");
        acumulador = scanner.nextInt();
        int vidaMax = 10 + 5 * acumulador;
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
        int manaMax = 10 + 5 * acumulador;
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
        int poderBase = 10 + 5 * acumulador;
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
        int resistencia = 10 + 5 * acumulador;
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

        int velocidade = 10 + 5 * acumulador;
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