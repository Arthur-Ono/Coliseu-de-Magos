package com.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
import com.Mapas.*; 
import com.Mapas.GerenciadorDeArenas; 
import com.personagem.Personagem;

public class OrganizadorDeDuelos extends Servicos {

    public OrganizadorDeDuelos(Scanner scanner, Gerenciador gerenciador) {
        super(scanner, gerenciador);
    }

    @Override
    public void executar() {
        System.out.println("\n----- DUELO IMEDIATO -----");
        
        // Lógica para escolher a arena
        GerenciadorDeArenas gerenciadorArenas = new GerenciadorDeArenas();
        System.out.println("Selecione a Arena:");
        // ... (código para listar e escolher a arena)
        Arena arenaEscolhida = gerenciadorArenas.getArenas().get(0); // Pega a primeira como padrão

        // Lógica para escolher o tipo de duelo
        System.out.println("SELECIONE O TIPO DE DUELO:");
        System.out.println("1. 1v1 | 2. 2v2 | 3. 3v3");
        System.out.print("Opção: ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();

        if (tamanho < 1 || tamanho > 3) {
            System.out.println("Opção inválida.");
            return;
        }

        List<Personagem> time1 = montarTime(1, tamanho);
        List<Personagem> time2 = montarTime(2, tamanho);

        if (time1 != null && time2 != null) {
            // O executar agora chama o nosso novo método público
            iniciarDuelo(time1, time2, arenaEscolhida);
        }
    }

    // ESTE MÉTODO AGORA É PÚBLICO E É O CORAÇÃO DA BATALHA
    public void iniciarDuelo(List<Personagem> time1, List<Personagem> time2, Arena arenaEscolhida) {
        System.out.println("\n💥 O DUELO EM " + arenaEscolhida.getNome().toUpperCase() + " COMEÇOU! 💥");
        
        CondicaoDeCampo condicaoAtiva = arenaEscolhida.sortearCondicao();
        System.out.println("Condição Inicial: " + condicaoAtiva.getNome());

        int turno = 1;
        while (timeEstaVivo(time1) && timeEstaVivo(time2)) {
            // ... (toda a sua lógica de batalha que já tínhamos) ...
            // (a lógica com o loop 'while', os 'for' de cada time, a checagem de turno % 3, etc.)
            System.out.println("\n--- Turno " + turno + " ---");
            System.out.println("Lógica de batalha a ser implementada...");
            break; // Break temporário
        }
        
        System.out.println("\n--- FIM DO DUELO ---");
        if (timeEstaVivo(time1)) {
            System.out.println("🏆 O VENCEDOR É: TIME 1!");
        } else {
            System.out.println("🏆 O VENCEDOR É: TIME 2!");
        }

        System.out.println("\nPressione Enter para continuar...");
        this.scanner.nextLine();
    }

    // O método montarTime continua privado, pois só é usado aqui dentro
    private List<Personagem> montarTime(int numeroDoTime, int tamanhoDoTime) {
        List<Personagem> time = new ArrayList<>();
        System.out.println("\n--- Montando Time " + numeroDoTime + " ---");
        for (int i = 1; i <= tamanhoDoTime; i++) {
            System.out.print("Digite o ID do " + i + "º mago do Time " + numeroDoTime + ": ");
            int idMago = this.scanner.nextInt();
            this.scanner.nextLine();
            Personagem magoSelecionado = this.gerenciador.buscarPorId(idMago);
            if (magoSelecionado == null) {
                System.out.println("ERRO: Mago com ID " + idMago + " não encontrado.");
                return null;
            }
            time.add(magoSelecionado);
        }
        return time;
    }
    
    // O método timeEstaVivo também continua privado
    private boolean timeEstaVivo(List<Personagem> time) {
        for (Personagem p : time) {
            if (p.getVidaAtual() > 0) return true;
        }
        return false;
    }
}