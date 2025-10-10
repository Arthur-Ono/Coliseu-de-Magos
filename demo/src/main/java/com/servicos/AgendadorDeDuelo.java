package com.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.Mapas.Arena;
import com.personagem.Personagem; // Import necessário para o cast
import com.personagem.Ranqueados;
import com.Agenda.Agendamento;
import com.Agenda.GerenciadorDeAgendamentos;
import com.Mapas.GerenciadorDeArenas;

// Esta classe é um "especialista" com a única tarefa de guiar o usuário
// no processo de agendar um duelo para um turno futuro.
public class AgendadorDeDuelo extends Servicos {

    // Atributos específicos deste especialista, para ele poder conversar
    // com a "secretária" (GerenciadorDeAgendamentos) e o "relógio" do jogo (ControladorDeTurno).
    private GerenciadorDeAgendamentos gerenciadorAgendamentos;
    private ControladorDeTurno controladorDeTurno;

    // O construtor que o Menu usa para criar este especialista.
    // Ele recebe todas as ferramentas de que precisa para trabalhar.
    public AgendadorDeDuelo(Scanner scanner, Gerenciador gerenciador, GerenciadorDeAgendamentos ga, ControladorDeTurno ct) {
        // 'super' passa o scanner e o gerenciador para a classe mãe 'Servicos' guardar.
        super(scanner, gerenciador);
        // Guarda as outras ferramentas nos atributos desta classe.
        this.gerenciadorAgendamentos = ga;
        this.controladorDeTurno = ct;
    }

    @Override
    // O método principal que é chamado pelo Menu.
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

        // Chama o método "ajudante" para criar os dois times.
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
        
        // Calcula em qual turno do jogo o duelo vai de fato acontecer.
        int turnoFinal = this.controladorDeTurno.getTurnoAtual() + turnosNoFuturo;

        // Antes de criar o agendamento, pergunta para a "secretária" se já não tem algo marcado
        // para aquela arena e aquele turno.
        if (this.gerenciadorAgendamentos.isArenaOcupada(arenaEscolhida, turnoFinal)) {
            System.out.println("ERRO: A arena '" + arenaEscolhida.getNome() + "' já está reservada para o turno " + turnoFinal + ".");
            System.out.println("Agendamento cancelado.");
            return;
        }

        // Se a arena estiver livre, cria o objeto de agendamento.
        Agendamento novoAgendamento = new Agendamento(time1, time2, arenaEscolhida, turnoFinal);
        // Envia o "lembrete" para a "secretária" guardar.
        this.gerenciadorAgendamentos.adicionarAgendamento(novoAgendamento);
        System.out.println("Duelo agendado com sucesso para o turno " + turnoFinal + "!");
    }
    
    // Método ajudante para montar um time. Fica 'private' porque só é usado aqui dentro.
    private List<Ranqueados> montarTime(int numeroDoTime, int tamanhoDoTime) {
        List<Ranqueados> time = new ArrayList<>();
        System.out.println("\n--- Montando Time " + numeroDoTime + " ---");
        // Loop para adicionar a quantidade certa de magos no time.
        for (int i = 1; i <= tamanhoDoTime; i++) {
            System.out.print("Digite o ID do " + i + "º mago do Time " + numeroDoTime + ": ");
            int idMago = this.scanner.nextInt();
            this.scanner.nextLine();
            
            // Busca o mago pelo ID. O 'buscarPorId' retorna um Personagem,
            // então precisamos fazer o "cast" (conversão) para Ranqueados.
            Ranqueados magoSelecionado = (Ranqueados) this.gerenciador.buscarPorId(idMago);

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