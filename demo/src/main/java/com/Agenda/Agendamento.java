package com.Agenda;

import java.util.List;
import com.personagem.Ranqueados;
import com.Mapas.Arena;

public class Agendamento {
    private List<Ranqueados> time1;
    private List<Ranqueados> time2;
    private Arena arena;
    private int turnoAgendado;

    public Agendamento(List<Ranqueados> time1, List<Ranqueados> time2, Arena arena, int turnoAgendado) {
        this.time1 = time1;
        this.time2 = time2;
        this.arena = arena;
        this.turnoAgendado = turnoAgendado;
    }

    // Getters para todas as informações
    public List<Ranqueados> getTime1() { return time1; }
    public List<Ranqueados> getTime2() { return time2; }
    public Arena getArena() { return arena; }
    public int getTurnoAgendado() { return turnoAgendado; }
}
