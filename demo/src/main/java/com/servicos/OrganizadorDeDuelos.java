package com.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.Mapas.*;
import com.Mapas.GerenciadorDeArenas;

import com.personagem.Ranqueados;


public class OrganizadorDeDuelos extends Servicos {

    public OrganizadorDeDuelos(Scanner scanner, Gerenciador gerenciador) {
        super(scanner, gerenciador);
    }

    @Override
    public void executar() {
        System.out.println("\n----- DUELO IMEDIATO -----");
        
        // 1. Lógica para escolher a arena
        GerenciadorDeArenas gerenciadorArenas = new GerenciadorDeArenas();
        List<Arena> arenasDisponiveis = gerenciadorArenas.getArenas();
        System.out.println("Selecione a Arena para o combate:");
        for (int i = 0; i < arenasDisponiveis.size(); i++) {
            System.out.println((i + 1) + ". " + arenasDisponiveis.get(i).getNome());
        }
        System.out.print("Escolha uma opção de Arena: ");
        int escolhaArena = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (escolhaArena < 0 || escolhaArena >= arenasDisponiveis.size()) {
            System.out.println("Arena inválida. Duelo cancelado.");
            return;
        }
        Arena arenaEscolhida = arenasDisponiveis.get(escolhaArena);

        // 2. Lógica para escolher o tipo de duelo
        System.out.println("SELECIONE O TIPO DE DUELO:");
        System.out.println("1. 1v1 | 2. 2v2 | 3. 3v3");
        System.out.print("Opção: ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        if (tamanho < 1 || tamanho > 3) {
            System.out.println("Opção inválida.");
            return;
        }

        // 3. Monta os times
        List<Ranqueados> time1 = montarTime(1, tamanho);
        List<Ranqueados> time2 = montarTime(2, tamanho);

        // 4. Inicia o duelo se os times foram montados com sucesso
        if (time1 != null && time2 != null) {
            iniciarDuelo(time1, time2, arenaEscolhida);
        }
    }

    // Este método é público para poder ser chamado pelo Agendador
    public void iniciarDuelo(List<Ranqueados> time1, List<Ranqueados> time2, Arena arenaEscolhida) {
        if (time1.stream().anyMatch(time2::contains)) {
            System.out.println("Um mago não pode estar em ambos os times. Duelo Cancelado.");
            return;
        }
        
        List<Ranqueados> ordemTurno = new ArrayList<>();
        ordemTurno.addAll(time1);
        ordemTurno.addAll(time2);
        ordemTurno.sort((a, b) -> Integer.compare(b.getVelocidade(), a.getVelocidade()));
        
        System.out.println("\n💥 O DUELO EM " + arenaEscolhida.getNome().toUpperCase() + " COMEÇOU! 💥");
        
        CondicaoDeCampo condicaoAtiva = arenaEscolhida.sortearCondicao();
        System.out.println("Condição Inicial: " + condicaoAtiva.getNome());

        int turno = 1;
        while (timeEstaVivo(time1) && timeEstaVivo(time2)) {
            System.out.println("\n--- Turno " + turno + " ---");
            System.out.println("Condição Ativa: " + condicaoAtiva.getNome());

            for (Ranqueados atacante : ordemTurno) {
                if (atacante.getVidaAtual() > 0) {
                    System.out.println("\nTurno de: " + atacante.getCodinome() + " (Vida: " + atacante.getVidaAtual() + ")");
                    System.out.println("Escolha sua ação:\n (1) Ataque Básico\n (2) Usar Magia\n (3) Defender");
                    int acao = scanner.nextInt();
                    scanner.nextLine();

                    if (acao == 1) { // Ataque Básico
                        List<Ranqueados> adversarios = time1.contains(atacante) ? time2 : time1;
                        Ranqueados alvo = escolherAlvo(adversarios);
                        if (alvo != null) {
                            atacante.causarDano(alvo, null); // Passa null para a magia
                        }
                    } else if (acao == 2) { // Usar Magia
                        // Lógica para escolher magia e alvo
                    } else if (acao == 3) { // Defender
                        // Lógica de defesa
                    }
                }
            }
            
            // Lógica de final de turno (veneno, etc)
            
            turno++;
            break; // Break temporário
        }

        System.out.println("\n--- FIM DO DUELO ---");
        if (timeEstaVivo(time1)) {
            System.out.println("🏆 O VENCEDOR É: TIME 1!");
        } else if (timeEstaVivo(time2)) {
            System.out.println("🏆 O VENCEDOR É: TIME 2!");
        } else {
            System.out.println("O duelo terminou em empate!");
        }

        System.out.println("\nPressione Enter para continuar...");
        this.scanner.nextLine();
    }
    
    // Método privado para escolher um alvo
    private Ranqueados escolherAlvo(List<Ranqueados> adversarios) {
        List<Ranqueados> alvosVivos = new ArrayList<>();
        System.out.println("Escolha um alvo:");
        for (Ranqueados p : adversarios) {
            if (p.getVidaAtual() > 0) {
                alvosVivos.add(p);
                System.out.println((alvosVivos.size()) + " - " + p.getCodinome() + " (Vida: " + p.getVidaAtual() + ")");
            }
        }

        if (alvosVivos.isEmpty()) {
            return null; // Não há mais alvos
        }

        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > alvosVivos.size()) {
            System.out.println("Escolha inválida!");
            return escolherAlvo(adversarios); // Pede para escolher de novo
        }
        return alvosVivos.get(escolha - 1);
    }

    private List<Ranqueados> montarTime(int numeroDoTime, int tamanhoDoTime) {
        List<Ranqueados> time = new ArrayList<>();
        System.out.println("\n--- Montando Time " + numeroDoTime + " ---");
        for (int i = 1; i <= tamanhoDoTime; i++) {
            System.out.print("Digite o ID do " + i + "º mago do Time " + numeroDoTime + ": ");
            int idMago = this.scanner.nextInt();
            this.scanner.nextLine();
            
            Ranqueados magoSelecionado = this.gerenciador.buscarPorId(idMago);

            if (magoSelecionado == null) {
                System.out.println("ERRO: Mago com ID " + idMago + " não encontrado.");
                return null;
            }
            
            if (!(magoSelecionado instanceof Ranqueados)) {
                System.out.println("ERRO: O personagem selecionado não é um combatente ranqueado.");
                return null;
            }

            time.add((Ranqueados) magoSelecionado);
        }
        return time;
    }
    
    private boolean timeEstaVivo(List<Ranqueados> time) {
        for (Ranqueados p : time) {
            if (p.getVidaAtual() > 0) {
                return true;
            }
        }
        return false;
    }
}