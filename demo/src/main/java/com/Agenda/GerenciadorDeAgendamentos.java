package com.Agenda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Mapas.Arena; // Import necessário para a nova função

// Esta classe é a "secretária" do jogo. Ela é responsável por
// guardar e gerenciar todos os duelos que foram agendados para o futuro.
public class GerenciadorDeAgendamentos {
    
    // A "caderneta" da secretária, onde todos os lembretes (Agendamentos) são guardados.
    private List<Agendamento> agendamentos = new ArrayList<>();

    // Método para adicionar um novo "lembrete" de duelo na caderneta.
    public void adicionarAgendamento(Agendamento novoAgendamento) {
        this.agendamentos.add(novoAgendamento);
        System.out.println("✅ Duelo agendado com sucesso para o turno " + novoAgendamento.getTurnoAgendado() + "!");
    }

    // Novo método para verificar se uma arena está ocupada em um turno específico.
    // Retorna 'true' se a arena estiver ocupada, 'false' se estiver livre.
    public boolean isArenaOcupada(Arena arenaParaVerificar, int turnoParaVerificar) {
        // Percorre cada agendamento na "caderneta".
        for (Agendamento ag : this.agendamentos) {
            // Compara se o turno e a arena do agendamento são os mesmos que estamos tentando marcar.
            if (ag.getTurnoAgendado() == turnoParaVerificar && ag.getArena().equals(arenaParaVerificar)) {
                // Se encontrou um agendamento conflitante, retorna 'true' imediatamente.
                return true;
            }
        }
        // Se o loop terminar e não encontrar nenhum conflito, a arena está livre.
        return false;
    }

    // Este método é chamado pelo Menu a cada novo turno.
    // Ele verifica na "caderneta" quais duelos devem acontecer AGORA.
    public List<Agendamento> verificarEObterAgendamentosParaTurno(int turnoAtual) {
        // Usando a API de Streams do Java, que é um jeito moderno de trabalhar com listas.
        List<Agendamento> duelosDeHoje = agendamentos.stream()
                // 'filter' funciona como um filtro: só deixa passar os agendamentos
                // cujo turno é igual ao turno atual do jogo.
                .filter(ag -> ag.getTurnoAgendado() == turnoAtual)
                // 'collect' junta todos os agendamentos que passaram pelo filtro em uma nova lista.
                .collect(Collectors.toList());
        
        // Remove os duelos que vão acontecer hoje da lista principal de agendamentos.
        // Assim, eles não serão executados de novo no futuro.
        agendamentos.removeAll(duelosDeHoje);

        // Retorna a lista apenas com os duelos que devem acontecer neste turno.
        return duelosDeHoje;
    }
}