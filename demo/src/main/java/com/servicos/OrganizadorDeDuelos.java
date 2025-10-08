package com.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.personagem.Personagem;

public class OrganizadorDeDuelos extends Servicos {

    public OrganizadorDeDuelos(Scanner scanner, Gerenciador gerenciador) {
        super(scanner, gerenciador);
    }

    @Override
    public void executar() {
        System.out.println("\n----- DUELOS -----");
        System.out.println("SELECIONE O TIPO DE DUELO:");
        System.out.println("1. Duelo 1v1");
        System.out.println("2. Duelo 2v2");
        System.out.println("3. Duelo 3v3");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        List<Personagem> time1 = null;
        List<Personagem> time2 = null;

        switch (opcao) {
            case 1:
                time1 = montarTime(1, 1);
                time2 = montarTime(2, 1);
                break;
            case 2:
                time1 = montarTime(1, 2);
                time2 = montarTime(2, 2);
                break;
            case 3:
                time1 = montarTime(1, 3);
                time2 = montarTime(2, 3);
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                return; 
            default:
                System.out.println("Opção inválida.");
                return; 
        }

        // Se a montagem dos times foi bem-sucedida (não retornou null)
        if (time1 != null && time2 != null) {
            iniciarDuelo(time1, time2);
        }
    }

    private List<Personagem> montarTime(int numeroDoTime, int tamanhoDoTime) {
        List<Personagem> time = new ArrayList<>();
        System.out.println("\n--- Montando Time " + numeroDoTime + " ---");

        for (int i = 1; i <= tamanhoDoTime; i++) {
            System.out.print("Digite o ID do " + i + "º mago do Time " + numeroDoTime + ": ");
            int idMago = this.scanner.nextInt();
            this.scanner.nextLine();

            Personagem magoSelecionado = this.gerenciador.buscarPorId(idMago);

            if (magoSelecionado == null) {
                System.out.println("ERRO: Mago com ID " + idMago + " não encontrado. Montagem de time cancelada.");
                return null;
            }
            if (time.contains(magoSelecionado)) {
                System.out.println("ERRO: Mago " + magoSelecionado.getCodinome() + " já está neste time. Montagem de time cancelada.");
                return null;
            }
            
            time.add(magoSelecionado);
            System.out.println(" -> " + magoSelecionado.getCodinome() + " adicionado ao Time " + numeroDoTime);
        }
        return time;
    }

    private void iniciarDuelo(List<Personagem> time1, List<Personagem> time2) {
        // Validação básica para garantir que os times não são os mesmos
        if (time1.stream().anyMatch(time2::contains)) {
            System.out.println("Um mago não pode estar em ambos os times. Duelo Cancelado.");
            return;
        }
        
        System.out.println("\n💥 O DUELO ENTRE TIME 1 E TIME 2 COMEÇOU! 💥");
        
        int turno = 1;
        while (timeEstaVivo(time1) && timeEstaVivo(time2)) {
            System.out.println("\n--- Turno " + turno + " ---");

            System.out.println("Turno do Time 1:");
            for (Personagem atacante : time1) {
                if (atacante.getVidaAtual() > 0 && timeEstaVivo(time2)) {
                    // Bota aqui a lógica de dano, Mathematics.
                    // Exemplo: atacante.causarDano(time2.get(0)); // Ataca o primeiro vivo do time 2
                    System.out.println(atacante.getCodinome() + " ataca...");
                }
            }
            
            System.out.println("\nTurno do Time 2:");
            for (Personagem atacante : time2) {
                 if (atacante.getVidaAtual() > 0 && timeEstaVivo(time1)) {
                    
                    // Bota aqui a lógica de dano, Mathematics.
                    // Exemplo: atacante.causarDano(time1.get(0)); // Ataca o primeiro vivo do time 1
                    System.out.println(atacante.getCodinome() + " ataca...");
                }
            }

            // Impressão da vida de todos
            System.out.println("\n-- Status dos Times --");
            System.out.print("Time 1: ");
            time1.forEach(p -> System.out.print(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            System.out.print("\nTime 2: ");
            time2.forEach(p -> System.out.print(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            System.out.println();
            
            turno++;
            // Quando tu terminares o ngc de dano remove esse break aqui. ele serve só pro esqueleto não ficar em loop
            break;
        }

        System.out.println("\n--- FIM DO DUELO ---");
        if (timeEstaVivo(time1)) {
            System.out.println("🏆 O VENCEDOR É: TIME 1!");
        } else if (timeEstaVivo(time2)) {
            System.out.println("🏆 O VENCEDOR É: TIME 2!");
        } else {
            System.out.println("O duelo terminou em empate!");
        }

        System.out.println("\nPressione Enter para voltar ao menu...");
        this.scanner.nextLine();
    }

    private boolean timeEstaVivo(List<Personagem> time) {
        for (Personagem p : time) {
            if (p.getVidaAtual() > 0) {
                return true;
            }
        }
        return false;
    }
}