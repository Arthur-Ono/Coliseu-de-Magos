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

        // --- COLETA DOS DADOS BÁSICOS ---
        System.out.print("Digite o ID do mago: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o Codinome do mago: ");
        String codinome = scanner.nextLine();

        System.out.print("Digite o Foco (Cajado, Varinha, etc): ");
        String foco = scanner.nextLine();

        System.out.print("Digite o Controlador (1 para Humano, 2 para IA): ");
        int controlador = scanner.nextInt();
        scanner.nextLine();

        // --- SISTEMA DE DISTRIBUIÇÃO DE PONTOS ---
        int pontos = 10;
        System.out.println("\nAgora você tem " + pontos + " pontos para distribuir nos atributos.");
        
        // Chama o método "ajudante" para distribuir pontos para Vida.
        int ptsVida = distribuirPontos("Vida", pontos);
        // Subtrai os pontos gastos do total.
        pontos -= ptsVida;
        // Calcula o valor final do atributo com base nos pontos.
        int vidaMax = 10 + (5 * ptsVida);

        // Repete o processo para cada atributo.
        int ptsMana = distribuirPontos("Mana", pontos);
        pontos -= ptsMana;
        int manaMax = 10 + (5 * ptsMana);

        int ptsPoder = distribuirPontos("Poder Base", pontos);
        pontos -= ptsPoder;
        int poderBase = 10 + (5 * ptsPoder);
        
        int ptsResistencia = distribuirPontos("Resistência", pontos);
        pontos -= ptsResistencia;
        int resistencia = 10 + (5 * ptsResistencia);

        int ptsVelocidade = distribuirPontos("Velocidade", pontos);
        pontos -= ptsVelocidade;
        int velocidade = 10 + (5 * ptsVelocidade);

        // Avisa ao jogador se ele não gastou todos os pontos.
        if (pontos > 0) {
            System.out.println("Atenção: " + pontos + " pontos não foram distribuídos.");
        }

        // Prepara uma variável para guardar o mago que será criado.
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
}