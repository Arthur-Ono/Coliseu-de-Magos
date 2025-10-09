package com.servicos;

import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.personagem.MagoArcano;
import com.personagem.MagoElemental;
import com.personagem.MagoSombrio;
import com.personagem.Ranqueados;

public class CriadorDeMagos extends Servicos {

    // O construtor deste especialista. Ele recebe as ferramentas (scanner, gerenciador)
    // e as passa para a classe mãe 'Servicos' guardar.
    public CriadorDeMagos(Scanner scanner, Gerenciador gerenciador) {
        super(scanner, gerenciador);
    }

    @Override
    // Este é o método principal que será executado quando o usuário escolher "Criar Mago".
    public void executar() {

        System.out.println("\n--- CRIAÇÃO DE NOVO MAGO ---");
        System.out.println("Escolha a escola do Mago:");
        System.out.println("1. Mago Elemental");
        System.out.println("2. Mago Arcano");
        System.out.println("3. Mago Sombrio");
        System.out.print("Opção: ");
        // Lê a escolha do tipo de mago.
        int tipo = scanner.nextInt();
        scanner.nextLine();

        // Valida se a opção é uma das existentes.
        if (tipo < 1 || tipo > 3) {
            System.out.println("Opção inválida. Retornando ao menu principal.");
            return; // Encerra o método.
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
        // Define um valor padrão para a hora de entrada, já que não pedimos isso ao usuário.
        int horaEntrada = 1;

        // O 'switch' usa o 'tipo' escolhido pelo jogador para decidir qual construtor chamar.
        switch (tipo) {
            case 1:
                // Se escolheu 1, cria um MagoElemental usando o construtor de "nascimento".
                // Este construtor já sabe que os atributos de ranking (abates, etc.) começam em 0.
                novoMago = new MagoElemental(id, codinome, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada, velocidade);
                break;
            case 2:
                // Se escolheu 2, cria um MagoArcano.
                novoMago = new MagoArcano(id, codinome, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada, velocidade);
                break;
            case 3:
                // Se escolheu 3, cria um MagoSombrio.
                novoMago = new MagoSombrio(id, codinome, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada, velocidade);
                break;
        }

        // Se a criação foi bem-sucedida (novoMago não é nulo)...
        if (novoMago != null) {
            // ...adiciona o mago recém-criado à lista principal do jogo.
            this.gerenciador.adicionar(novoMago);
            System.out.println("\n" + novoMago.getClass().getSimpleName() + " criado com sucesso!");
            // Imprime os dados do mago usando o método toString() da classe Personagem.
            System.out.println(novoMago);
        }

        System.out.println("\nPressione Enter para voltar ao menu...");
        scanner.nextLine();
    }

    /**
     * Método "ajudante" privado para tornar a distribuição de pontos mais limpa e segura.
     * Ele lida com o processo de pedir os pontos para um único atributo.
     * @param nomeAtributo O nome do atributo (ex: "Vida") para mostrar na mensagem.
     * @param pontosRestantes O total de pontos que o jogador ainda tem.
     * @return A quantidade de pontos que o jogador decidiu gastar neste atributo.
     */
    /* 
     * 
     private int distribuirPontos(String nomeAtributo, int pontosRestantes) {
        // Se não há mais pontos, nem pergunta, só retorna 0.
        if (pontosRestantes <= 0) {
            System.out.println("Sem pontos restantes para " + nomeAtributo + ".");
            return 0;
        }
        
        System.out.println("\nPontos restantes: " + pontosRestantes);
        System.out.print("Quantos pontos para " + nomeAtributo + "? ");
        int pontosGastos = scanner.nextInt();
        
        // Validação para não deixar o usuário gastar mais pontos do que tem.
        if (pontosGastos > pontosRestantes) {
            System.out.println("Você não tem pontos suficientes! Gastando o máximo possível: " + pontosRestantes);
            pontosGastos = pontosRestantes;
        }
        // Validação para não deixar o usuário digitar um número negativo.
        if (pontosGastos < 0) {
            pontosGastos = 0;
        }
        
        // Retorna o número de pontos (válido) que o jogador gastou.
        return pontosGastos;
    }
    */
}