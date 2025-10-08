package com.Agenda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorDeAgendamentos {
    private List<Agendamento> agendamentos = new ArrayList<>();

    public void adicionarAgendamento(Agendamento novoAgendamento) {
        this.agendamentos.add(novoAgendamento);
        System.out.println("✅ Duelo agendado com sucesso para o turno " + novoAgendamento.getTurnoAgendado() + "!");
    }

    public List<Agendamento> verificarEObterAgendamentosParaTurno(int turnoAtual) {
        List<Agendamento> duelosDeHoje = agendamentos.stream()
                .filter(ag -> ag.getTurnoAgendado() == turnoAtual)
                .collect(Collectors.toList());
        
        agendamentos.removeAll(duelosDeHoje);

        return duelosDeHoje;
    }
}
