package com.servicos;

import java.util.List;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.personagem.Ranqueados;
public class ListadorDeMagos extends Servicos {

    
    public ListadorDeMagos(Gerenciador gerenciador, Scanner scanner) {
        super(scanner, gerenciador);
    }

    
    @Override
    public void executar() {
        System.out.println("\n--- LISTA DE MAGOS ---");
        // Os atributos "gerenciador" e "scanner" são herdados da classe Servicos
        List<Ranqueados> magos = this.gerenciador.listarTodos();

        if (magos.isEmpty()) {
            System.out.println("Nenhum mago cadastrado no momento.");
        } else {
            for (Ranqueados mago : magos) {
                System.out.println(mago);
            }
        }
        
        System.out.println("\nPressione Enter para voltar ao menu...");
        scanner.nextLine();
    }
}