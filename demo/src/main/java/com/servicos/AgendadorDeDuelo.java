package com.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.Mapas.Arena;
import com.personagem.Personagem;
import com.Agenda.Agendamento;
import com.Agenda.GerenciadorDeAgendamentos;
import com.Mapas.GerenciadorDeArenas;

public class AgendadorDeDuelo extends Servicos {

    // --- CORREÇÃO AQUI: Atributos para guardar as novas ferramentas ---
    private GerenciadorDeAgendamentos gerenciadorAgendamentos;
    private ControladorDeTurno controladorDeTurno;

    // --- CORREÇÃO AQUI: O construtor de 4 parâmetros que o Menu está tentando chamar ---
    public AgendadorDeDuelo(Scanner scanner, Gerenciador gerenciador, GerenciadorDeAgendamentos ga, ControladorDeTurno ct) {
        super(scanner, gerenciador);
        this.gerenciadorAgendamentos = ga;
        this.controladorDeTurno = ct;
    }

    @Override
    public void executar() {
        System.out.println("\n--- AGENDAMENTO DE DUELO ---");
        
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
            System.out.println("Arena inválida. Agendamento cancelado.");
            return;
        }
        Arena arenaEscolhida = arenasDisponiveis.get(escolhaArena);

        System.out.print("Digite o tamanho dos times (1, 2 ou 3): ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        if (tamanho <= 0 || tamanho > 3) {
            System.out.println("Tamanho de time inválido. Agendamento cancelado.");
            return;
        }

        List<Personagem> time1 = montarTime(1, tamanho);
        List<Personagem> time2 = montarTime(2, tamanho);

        if (time1 == null || time2 == null) {
            System.out.println("Falha na montagem dos times. Agendamento cancelado.");
            return;
        }

        System.out.print("Agendar duelo para daqui a quantos turnos? ");
        int turnosNoFuturo = scanner.nextInt();
        scanner.nextLine();

        if (turnosNoFuturo < 0) {
            System.out.println("Não é possível agendar para um turno no passado. Agendamento cancelado.");
            return;
        }
        
        int turnoFinal = this.controladorDeTurno.getTurnoAtual() + turnosNoFuturo;

        Agendamento novoAgendamento = new Agendamento(time1, time2, arenaEscolhida, turnoFinal);
        this.gerenciadorAgendamentos.adicionarAgendamento(novoAgendamento);
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
}