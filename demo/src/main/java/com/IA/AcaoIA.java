package com.IA;

import com.personagem.Ranqueados;
import com.feitico.Magia;

// Esta classe é um "pacote de dados" que representa a decisão final da IA.
public class AcaoIA {
    private TipoAcao tipo;
    private Ranqueados alvo;
    private Magia magiaEscolhida; // Pode ser null se a ação não for usar magia

    public AcaoIA(TipoAcao tipo, Ranqueados alvo, Magia magia) {
        this.tipo = tipo;
        this.alvo = alvo;
        this.magiaEscolhida = magia;
    }

    public TipoAcao getTipo() { return tipo; }
    public Ranqueados getAlvo() { return alvo; }
    public Magia getMagiaEscolhida() { return magiaEscolhida; }
}