package com.servicos;

import java.util.List;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
 // Mudei o import de Ranqueados para Personagem
import com.personagem.Ranqueados;

public class ListadorDeMagos extends Servicos {

    // O construtor do especialista, que recebe as ferramentas (gerenciador, scanner)
    // e as passa para a classe mãe Servicos.
    public ListadorDeMagos(Gerenciador gerenciador, Scanner scanner) {
        super(scanner, gerenciador);
    }

    @Override
    // O método que será executado quando o usuário escolher "Listar Magos".
    public void executar() {
        System.out.println("\n--- LISTA DE MAGOS ---");
        // Os atributos "gerenciador" e "scanner" são herdados da classe Servicos.
        
        List<Ranqueados> magos = this.gerenciador.listarTodos();

        // Verifica se a lista de magos está vazia.
        if (magos.isEmpty()) {
            System.out.println("Nenhum mago cadastrado no momento.");
        } else {
            // Se não estiver vazia, usa um loop 'for-each' para passar por cada mago na lista.
            for (Ranqueados mago : magos) {
                // Para cada mago, imprime suas informações usando o método toString() que você definiu.
                System.out.println(mago);
            }
        }
        
        System.out.println("\nPressione Enter para voltar ao menu...");
        scanner.nextLine();
    }
}