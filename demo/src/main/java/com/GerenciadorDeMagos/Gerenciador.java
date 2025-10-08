package com.GerenciadorDeMagos;

import java.util.ArrayList;
import java.util.List;
import com.personagem.Ranqueados; // A lista principal trabalha com Ranqueados

public class Gerenciador {

    // A lista principal guarda 'Ranqueados', o tipo mais genérico.
    private List<Ranqueados> listaDeMagos = new ArrayList<>();

    // O método 'adicionar' aceita qualquer coisa que seja um 'Ranqueados'.
    public void adicionar(Ranqueados mago) {
        listaDeMagos.add(mago);
    }

    // O método 'listarTodos' retorna a lista de 'Ranqueados'.
    public List<Ranqueados> listarTodos() {
        return listaDeMagos;
    }

    // O método 'buscarPorId' retorna um 'Ranqueados'.
    public Ranqueados buscarPorId(int id) {
        for (Ranqueados mago : listaDeMagos) {
            if (mago.getId() == id) {
                return mago;
            }
        }
        return null;
    }
    
    // O método 'setListaDeMagos' aceita uma lista de 'Ranqueados'.
    public void setListaDeMagos(List<Ranqueados> magos) {
        this.listaDeMagos = magos;
    }
}