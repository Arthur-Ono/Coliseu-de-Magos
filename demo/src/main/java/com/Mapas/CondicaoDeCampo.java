
package com.Mapas;
public class CondicaoDeCampo {
    private String nome;           
    private String tipoDeEfeito;  

    public CondicaoDeCampo(String nome, String tipoDeEfeito) {
        this.nome = nome;
        this.tipoDeEfeito = tipoDeEfeito;
    }

    public String getNome() {
        return nome;
    }

    public String getTipoDeEfeito() {
        return tipoDeEfeito;
    }
}