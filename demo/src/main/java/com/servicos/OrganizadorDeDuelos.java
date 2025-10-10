package com.servicos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.Mapas.Arena;
import com.Mapas.CondicaoDeCampo;
import com.Mapas.GerenciadorDeArenas;
import com.personagem.Personagem;
import com.personagem.Ranqueados;
import com.feitico.Magia;

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

    // O método agora é público e recebe a Arena para funcionar com o Agendador.
    public void iniciarDuelo(List<Ranqueados> time1, List<Ranqueados> time2, Arena arenaEscolhida) {
        if (time1.stream().anyMatch(time2::contains)) {
            System.out.println("Um mago não pode estar em ambos os times. Duelo Cancelado.");
            return;
        }

        List<Ranqueados> ordemTurno = new ArrayList<>();
        ordemTurno.addAll(time1);
        ordemTurno.addAll(time2);
        ordemTurno.sort((a, b) -> Integer.compare(b.getVelocidade(), a.getVelocidade()));

        System.out.println("\nO DUELO EM " + arenaEscolhida.getNome().toUpperCase() + " COMECOU!");

        Map<Ranqueados, Integer> magosEmDefesa = new HashMap<>();
        Map<Ranqueados, Ranqueados> ultimoAlvoAtacado = new HashMap<>();

        CondicaoDeCampo condicaoAtiva = arenaEscolhida.sortearCondicao();
        System.out.println("Condição Inicial: " + condicaoAtiva.getNome());

        int turno = 1;
        while (timeEstaVivo(time1) && timeEstaVivo(time2)) {
            System.out.println("\n--- Turno " + turno + " ---");

            // NOVA LÓGICA: Mudança da condição de campo a cada 3 turnos.
            // A verificação (turno - 1) % 3 == 0 faz com que a mudança ocorra no início do turno 4, 7, 10, etc.
            if (turno > 1 && (turno - 1) % 3 == 0) {
                condicaoAtiva = arenaEscolhida.sortearCondicao();
                System.out.println("A CONDICAO DO CAMPO MUDOU!");
            }
            System.out.println("Condição Ativa: " + condicaoAtiva.getNome());

            for (Ranqueados atacante : ordemTurno) {
                // Se um dos times já foi derrotado no meio do turno, para o loop de ataques.
                if (!timeEstaVivo(time1) || !timeEstaVivo(time2)) {
                    break;
                }
                // Se o atacante da vez já foi derrotado, pula para o próximo.
                if (atacante.getVidaAtual() <= 0) {
                    continue;
                }

                // Lógica de defesa: se o mago estava defendendo, sua resistência volta ao normal.
                if (magosEmDefesa.containsKey(atacante)) {
                    atacante.setResistencia(magosEmDefesa.get(atacante));
                    magosEmDefesa.remove(atacante);
                    System.out.println(atacante.getCodinome() + " parou de se defender.");
                }

                // Lógica do turno do jogador (sem IA)
                System.out.println("\nTurno de: " + atacante.getCodinome() + " (Vida: " + atacante.getVidaAtual() + " | Mana: " + atacante.getManaAtual() + ")");
                System.out.println("Escolha sua ação:\n (1) Atacar\n (2) Defender");
                int acao = scanner.nextInt();
                scanner.nextLine();

                if (acao == 1) {
                    List<Ranqueados> adversarios = time1.contains(atacante) ? time2 : time1;
                    Ranqueados alvo = escolherAlvo(adversarios);
                    
                    if (alvo != null) {
                        Magia magiaSelecionada = escolherMagia(atacante);
                        atacante.causarDano(alvo, magiaSelecionada);
                        ultimoAlvoAtacado.put(atacante, alvo); // Registra qual foi o último alvo do atacante
                    }
                } else if (acao == 2) {
                    // Guarda o valor original da resistência antes de aumentá-la
                    magosEmDefesa.put(atacante, atacante.getResistencia());
                    atacante.setResistencia(atacante.getResistencia() + (atacante.getResistencia() / 2));
                    System.out.println(atacante.getCodinome() + " está em modo de defesa!");
                }
            }

            // Impressão da vida de todos no final do turno
            System.out.println("\n-- Status dos Times --");
            System.out.print("Time 1: ");
            time1.forEach(p -> System.out.print(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            System.out.print("\nTime 2: ");
            time2.forEach(p -> System.out.print(p.getCodinome() + "(" + p.getVidaAtual() + ") "));
            System.out.println();

            turno++;
        }

        System.out.println("\n--- FIM DO DUELO ---");
        if (timeEstaVivo(time1)) {
            System.out.println("O VENCEDOR E: TIME 1!");
            for (Ranqueados mago : time1) {
                mago.setCapturas(mago.getCapturas() + 1);
            }
        } else if (timeEstaVivo(time2)) {
            System.out.println("O VENCEDOR E: TIME 2!");
            for (Ranqueados mago : time2) {
                mago.setCapturas(mago.getCapturas() + 1);
            }
        } else {
            System.out.println("O duelo terminou em empate!");
        }

        // Lógica de contabilizar os rankings (mantida do seu código)
        ArrayList<Ranqueados> todosMagos = new ArrayList<>();
        todosMagos.addAll(time1);
        todosMagos.addAll(time2);
        for (Ranqueados mago : todosMagos) {
            Ranqueados alvo = ultimoAlvoAtacado.get(mago);
            if (alvo != null) {
                mago.incrementarRanking(alvo, todosMagos);
            }
        }

        System.out.println("\nPressione Enter para continuar...");
        this.scanner.nextLine();
    }

    private List<Ranqueados> montarTime(int numeroDoTime, int tamanhoDoTime) {
        List<Ranqueados> time = new ArrayList<>();
        System.out.println("\n--- Montando Time " + numeroDoTime + " ---");
        for (int i = 1; i <= tamanhoDoTime; i++) {
            System.out.print("Digite o ID do " + i + "º mago do Time " + numeroDoTime + ": ");
            int idMago = this.scanner.nextInt();
            this.scanner.nextLine();

            // O método buscarPorId retorna um Personagem, então fazemos o "cast".
            Personagem magoEncontrado = this.gerenciador.buscarPorId(idMago);

            if (magoEncontrado == null || !(magoEncontrado instanceof Ranqueados)) {
                System.out.println("ERRO: Mago com ID " + idMago + " não encontrado ou não é um combatente.");
                return null;
            }
            
            Ranqueados magoSelecionado = (Ranqueados) magoEncontrado;

            if (time.contains(magoSelecionado)) {
                System.out.println("ERRO: Este mago já foi adicionado a este time.");
                return null;
            }

            time.add(magoSelecionado);
        }
        return time;
    }

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
            System.out.println("Não há alvos vivos para atacar!");
            return null;
        }
        System.out.print("Opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        if (escolha < 1 || escolha > alvosVivos.size()) {
            System.out.println("Escolha inválida!");
            return escolherAlvo(adversarios);
        }
        return alvosVivos.get(escolha - 1);
    }

    private Magia escolherMagia(Ranqueados atacante) {
        List<Magia> grimorio = atacante.getGrimorio();
        if (grimorio.isEmpty()) {
            return null; // Retorna nulo para indicar ataque básico
        }

        System.out.println("Escolha o tipo de ataque:");
        System.out.println("(0) Ataque básico | Poder: " + atacante.getPoderBase());
        for (int i = 0; i < grimorio.size(); i++) {
            Magia m = grimorio.get(i);
            System.out.println("(" + (i + 1) + ") " + m.getNome() + " | Custo: " + m.getCustoMana());
        }
        
        System.out.print("Sua escolha: ");
        int escolhaAtaque = scanner.nextInt();
        scanner.nextLine();

        if (escolhaAtaque == 0) {
            return null; // Jogador escolheu ataque básico
        }

        if (escolhaAtaque > 0 && escolhaAtaque <= grimorio.size()) {
            Magia magiaSelecionada = grimorio.get(escolhaAtaque - 1);
            if (atacante.getManaAtual() >= magiaSelecionada.getCustoMana()) {
                atacante.setManaAtual(atacante.getManaAtual() - magiaSelecionada.getCustoMana());
                return magiaSelecionada;
            } else {
                System.out.println("Mana insuficiente! Usando ataque básico no lugar.");
                return null;
            }
        } else {
            System.out.println("Escolha inválida! Usando ataque básico.");
            return null;
        }
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