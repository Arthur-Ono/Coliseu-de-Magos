package com.example;


import com.Menu.ColiseuGUI;
import com.GerenciadorDeMagos.Gerenciador;
import com.Menu.Menu;
import com.personagem.MagoElemental;

public class Main {
    public static void main(String[] args) {
        Gerenciador gerenciador = new Gerenciador();
        Menu menu = new Menu();
        // Personagem p1 = new Personagem(12,"1","Elemental",2,2,"Cajado",1,2,2,2);
        // Personagem p2 = new Personagem(13,"1","2",25,2,"2",2,2,2,2);
        
        MagoElemental m1 = new MagoElemental(2,"fogo",60,50,"Varinha",5,2,2,2);
        // MagoElemental m2 = new MagoElemental(3,"lava","seilameu",2,2,"Varinha",5,2,2,2,2,2,2,2,2,2,2);

        gerenciador.adicionar(m1);
        // m1.adicionar(m1);
        // m2.adicionar(m2);

        // m1.imprimirRanking();
        
        // m1.causarDano(m2);
        
        // m1.incrementarRanking(m2);
        

        // m1.imprimirRanking();
        // m2.imprimirRanking();
        
        menu.menuPrincipal(gerenciador);
        //ColiseuGUI.main(args);
    }
}