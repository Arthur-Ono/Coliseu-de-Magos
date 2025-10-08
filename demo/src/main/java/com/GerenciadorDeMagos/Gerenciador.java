package com.GerenciadorDeMagos;

import java.util.ArrayList;
import java.util.List;
import com.personagem.Personagem; // A lista principal trabalha com Personagem

public class Gerenciador {

    // A lista principal guarda 'Personagem', o tipo mais genérico.
    private List<Personagem> listaDeMagos = new ArrayList<>();

    // O método 'adicionar' aceita qualquer coisa que seja um 'Personagem'.
    public void adicionar(Personagem mago) {
        listaDeMagos.add(mago);
    }

    // O método 'listarTodos' retorna a lista de 'Personagem'.
    public List<Personagem> listarTodos() {
        return listaDeMagos;
    }

    // O método 'buscarPorId' retorna um 'Personagem'.
    public Personagem buscarPorId(int id) {
        for (Personagem mago : listaDeMagos) {
            if (mago.getId() == id) {
                return mago;
            }
        }
        return null;
    }
    
    // O método 'setListaDeMagos' aceita uma lista de 'Personagem'.
    public void setListaDeMagos(List<Personagem> magos) {
        this.listaDeMagos = magos;
    }
}