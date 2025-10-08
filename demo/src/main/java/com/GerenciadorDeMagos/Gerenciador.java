package com.GerenciadorDeMagos;

import java.util.List;
import java.util.ArrayList;
import com.personagem.Ranqueados;

public class Gerenciador{
    List<Ranqueados> listaDeMagos;

    public Gerenciador() {
        this.listaDeMagos = new ArrayList<>();
    }

    public Ranqueados buscarPorId(int id) {
        for (Ranqueados mago : this.listaDeMagos) {
            if (mago.getId() == id) {
                return mago;
            }
        }
        return null;
    }
    public void adicionar(Ranqueados mago){
        this.listaDeMagos.add(mago);
    }

    public List<Ranqueados> listarTodos() {
        return this.listaDeMagos;
    }
    public void setListaDeMagos(List<Ranqueados> novaLista) {
        this.listaDeMagos = novaLista;
    }
}
