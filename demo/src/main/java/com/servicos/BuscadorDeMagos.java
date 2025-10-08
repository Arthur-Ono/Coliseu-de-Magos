package com.servicos;

import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.personagem.Personagem;

public class BuscadorDeMagos extends Servicos {

    public BuscadorDeMagos(Gerenciador gerenciador, Scanner scanner) {
        super(scanner, gerenciador);
    }

    @Override
    public void executar() {
        System.out.println("\n--- BUSCAR MAGO POR ID ---");
        System.out.print("Digite o ID do mago que deseja buscar: ");
        int idParaBuscar = this.scanner.nextInt();
        this.scanner.nextLine();

        Personagem magoEncontrado = this.gerenciador.buscarPorId(idParaBuscar);

        if (magoEncontrado != null) {
            System.out.println("Mago encontrado:");
            System.out.println(magoEncontrado);
        } else {
            System.out.println("Nenhum mago encontrado com o ID: " + idParaBuscar);
        }

        System.out.println("\nPressione Enter para voltar ao menu...");
        this.scanner.nextLine();
    }
}