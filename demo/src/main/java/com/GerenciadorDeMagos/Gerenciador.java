package com.GerenciadorDeMagos;

import java.util.List;
import java.util.ArrayList;
import com.personagem.Personagem;

public class Gerenciador{
    List<Personagem> listaDeMagos;

    public Gerenciador() {
        this.listaDeMagos = new ArrayList<>();
    }

    public Personagem buscarPorId(int id) {
        for (Personagem mago : this.listaDeMagos) {
            if (mago.getId() == id) {
                return mago;
            }
        }
        return null;
    }
    public void adicionar(Personagem mago){
        this.listaDeMagos.add(mago);
    }

    public List<Personagem> listarTodos() {
        return this.listaDeMagos;
    }
    public void setListaDeMagos(List<Personagem> novaLista) {
        this.listaDeMagos = novaLista;
    }
}
