package com.Agenda;

import java.util.List;
import com.personagem.Ranqueados;
import com.Mapas.Arena;

// Esta classe funciona como um "post-it" ou um "lembrete" de um duelo futuro.
// Ela guarda todas as informações necessárias para que o duelo aconteça.
public class Agendamento {
    
    // Guarda a lista de magos do primeiro time.
    private List<Ranqueados> time1;
    // Guarda a lista de magos do segundo time.
    private List<Ranqueados> time2;
    // Guarda a arena onde o duelo vai acontecer.
    private Arena arena;
    // Guarda o número do turno exato em que o duelo deve começar.
    private int turnoAgendado;

    // O construtor, que recebe todas as informações e as armazena nos atributos.
    public Agendamento(List<Ranqueados> time1, List<Ranqueados> time2, Arena arena, int turnoAgendado) {
        this.time1 = time1;
        this.time2 = time2;
        this.arena = arena;
        this.turnoAgendado = turnoAgendado;
    }

    public List<Ranqueados> getTime1() { return time1; }
    public List<Ranqueados> getTime2() { return time2; }
    public Arena getArena() { return arena; }
    public int getTurnoAgendado() { return turnoAgendado; } // O nome correto do método.
}