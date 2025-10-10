package com.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.Mapas.Arena;
import com.personagem.Ranqueados;
import com.Agenda.Agendamento;
import com.Agenda.GerenciadorDeAgendamentos;
import com.Mapas.GerenciadorDeArenas;

public class AgendadorDeDuelo extends Servicos {

    // Atributos para guardar as ferramentas que este especialista precisa.
    private GerenciadorDeAgendamentos gerenciadorAgendamentos;
    private ControladorDeTurno controladorDeTurno;

    // Construtor que recebe as ferramentas e as guarda nos atributos.
    public AgendadorDeDuelo(Scanner scanner, Gerenciador gerenciador, GerenciadorDeAgendamentos ga, ControladorDeTurno ct) {
        super(scanner, gerenciador);
        this.gerenciadorAgendamentos = ga;
        this.controladorDeTurno = ct;
    }

    @Override
    public void executar() {
        System.out.println("\n--- AGENDAMENTO DE DUELO ---");
        
        // Cria um gerenciador de arenas para poder listar os mapas disponíveis.
        GerenciadorDeArenas gerenciadorArenas = new GerenciadorDeArenas();
        List<Arena> arenasDisponiveis = gerenciadorArenas.getArenas();
        System.out.println("Selecione a Arena para o combate:");
        // Mostra a lista de arenas para o usuário.
        for (int i = 0; i < arenasDisponiveis.size(); i++) {
            System.out.println((i + 1) + ". " + arenasDisponiveis.get(i).getNome());
        }
        System.out.print("Escolha uma opção de Arena: ");
        int escolhaArena = scanner.nextInt() - 1;
        scanner.nextLine();
        
        // Valida se a escolha da arena é um número válido.
        if (escolhaArena < 0 || escolhaArena >= arenasDisponiveis.size()) {
            System.out.println("Arena inválida. Agendamento cancelado.");
            return;
        }
        Arena arenaEscolhida = arenasDisponiveis.get(escolhaArena);

        // Pergunta e lê o tamanho dos times.
        System.out.print("Digite o tamanho dos times (1, 2 ou 3): ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        // Valida o tamanho do time.
        if (tamanho <= 0 || tamanho > 3) {
            System.out.println("Tamanho de time inválido. Agendamento cancelado.");
            return;
        }

        // Chama o método 'montarTime' para criar os dois times.
        List<Ranqueados> time1 = montarTime(1, tamanho);
        List<Ranqueados> time2 = montarTime(2, tamanho);

        // Se a montagem de qualquer um dos times falhou (retornou null), cancela o agendamento.
        if (time1 == null || time2 == null) {
            System.out.println("Falha na montagem dos times. Agendamento cancelado.");
            return;
        }

        // Pergunta para quando o duelo será agendado.
        System.out.print("Agendar duelo para daqui a quantos turnos? ");
        int turnosNoFuturo = scanner.nextInt();
        scanner.nextLine();

        if (turnosNoFuturo < 0) {
            System.out.println("Não é possível agendar para um turno no passado. Agendamento cancelado.");
            return;
        }
        
        // Calcula em qual turno o duelo vai acontecer.
        int turnoFinal = this.controladorDeTurno.getTurnoAtual() + turnosNoFuturo;

        // Cria o objeto de agendamento com todas as informações.
        Agendamento novoAgendamento = new Agendamento(time1, time2, arenaEscolhida, turnoFinal);
        // Adiciona o novo agendamento na lista de agendamentos pendentes.
        this.gerenciadorAgendamentos.adicionarAgendamento(novoAgendamento);
        System.out.println("Duelo agendado com sucesso para o turno " + turnoFinal + "!");
    }
    
    // Método ajudante para montar um time.
    private List<Ranqueados> montarTime(int numeroDoTime, int tamanhoDoTime) {
        List<Ranqueados> time = new ArrayList<>();
        System.out.println("\n--- Montando Time " + numeroDoTime + " ---");
        // Loop para adicionar a quantidade certa de magos no time.
        for (int i = 1; i <= tamanhoDoTime; i++) {
            System.out.print("Digite o ID do " + i + "º mago do Time " + numeroDoTime + ": ");
            int idMago = this.scanner.nextInt();
            this.scanner.nextLine();
            // Busca o mago pelo ID fornecido.
            Ranqueados magoSelecionado = this.gerenciador.buscarPorId(idMago);
            // Valida se o mago existe.
            if (magoSelecionado == null) {
                System.out.println("ERRO: Mago com ID " + idMago + " não encontrado. Montagem de time cancelada.");
                return null;
            }
            // Valida se o mago já não foi adicionado a este time.
            if (time.contains(magoSelecionado)) {
                System.out.println("ERRO: Mago " + magoSelecionado.getCodinome() + " já está neste time. Montagem de time cancelada.");
                return null;
            }
            // Adiciona o mago ao time.
            time.add(magoSelecionado);
            System.out.println(" -> " + magoSelecionado.getCodinome() + " adicionado ao Time " + numeroDoTime);
        }
        // Retorna a lista com os magos do time.
        return time;
    }
}