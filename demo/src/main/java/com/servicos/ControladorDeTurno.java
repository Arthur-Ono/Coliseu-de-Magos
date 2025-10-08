package com.servicos;

public class ControladorDeTurno {
    private int turnoAtual = 1;

    public int getTurnoAtual() {
        return this.turnoAtual;
    }

    public void avancarTurno() {
        this.turnoAtual++;
    }
}