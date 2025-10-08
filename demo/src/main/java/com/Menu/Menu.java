package com.Menu;

import java.util.List;
import java.util.Scanner;

import com.personagem.Ranqueados;
import com.GerenciadorDeMagos.Gerenciador;
import com.csv.GerenciadorCSV;
import com.servicos.BuscadorDeMagos;
import com.servicos.CriadorDeMagos;
import com.servicos.ListadorDeMagos;
import com.servicos.OrganizadorDeDuelos;
public class Menu {

    Scanner scan = new Scanner(System.in);
    private GerenciadorCSV gerenciadorCSV = new GerenciadorCSV(); 
    private final String NOME_ARQUIVO = "magos.csv";
    public void menuPrincipal(Gerenciador gerenciador) {
        boolean running = true;
        while (running) {
            System.out.println("--- MENU PRINCIPAL ---");
            System.out.println("1. Criar Mago");
            System.out.println("2. Listar Magos");
            System.out.println("3. Agendar Duelo 1v1");
            System.out.println("4. Iniciar Duelo");
            System.out.println("5. Salvar Magos em CSV");
            System.out.println("6. Carregar Magos de CSV");
            System.out.println("7. Buscar Mago por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    CriadorDeMagos criador = new CriadorDeMagos(scan, gerenciador);
                    criador.executar();
                    break;
                case 2:
                    ListadorDeMagos listador = new ListadorDeMagos(gerenciador, scan);
                    listador.executar();
                    break;
                case 3:
                    System.out.println("Opção 3 selecionada: Agendar Duelo 1v1");
                    break;
                case 4:
                    OrganizadorDeDuelos organizador = new OrganizadorDeDuelos(scan, gerenciador);
                    organizador.executar();
                    break;
                case 5: 
                    gerenciadorCSV.salvar(gerenciador.listarTodos(), NOME_ARQUIVO);
                    System.out.println("Magos salvos com sucesso em " + NOME_ARQUIVO);
                    break;

                case 6: 
                    List<Ranqueados> magosCarregados = gerenciadorCSV.carregar(NOME_ARQUIVO);
                    gerenciador.setListaDeMagos(magosCarregados);
                    System.out.println("Magos carregados com sucesso de " + NOME_ARQUIVO);
                    System.out.println(magosCarregados.size() + " magos foram carregados.");
                    break;
                case 7:
                    BuscadorDeMagos buscador = new BuscadorDeMagos(gerenciador, scan);
                    buscador.executar();
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

    }


    
}