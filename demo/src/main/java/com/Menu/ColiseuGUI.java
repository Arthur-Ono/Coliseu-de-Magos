package com.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

import com.personagem.Ranqueados;
import com.GerenciadorDeMagos.Gerenciador;
import com.csv.GerenciadorCSV;
import com.servicos.*;
import com.Agenda.*;

public class ColiseuGUI extends JFrame {

    private Gerenciador gerenciador = new Gerenciador();
    private GerenciadorCSV gerenciadorCSV = new GerenciadorCSV();
    private final String NOME_ARQUIVO = "magos.csv";
    private ControladorDeTurno relogio = new ControladorDeTurno();
    private GerenciadorDeAgendamentos gerenciadorAgendamentos = new GerenciadorDeAgendamentos();

    private CriadorDeMagos criadorDeMagos = new CriadorDeMagos(new Scanner(System.in), gerenciador);
    private ListadorDeMagos listadorDeMagos = new ListadorDeMagos(gerenciador, new Scanner(System.in));
    private OrganizadorDeDuelos organizadorDeDuelos = new OrganizadorDeDuelos(new Scanner(System.in), gerenciador);
    private BuscadorDeMagos buscadorDeMagos = new BuscadorDeMagos(gerenciador, new Scanner(System.in));
    private AgendadorDeDuelo agendadorDeDuelo = new AgendadorDeDuelo(new Scanner(System.in), gerenciador, gerenciadorAgendamentos, relogio);
    private CriadorDeMagias criadorDeMagias = new CriadorDeMagias(gerenciador, new Scanner(System.in));

    public ColiseuGUI() {
        setTitle("Coliseu de Magos");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        JButton btnCriarMago = new JButton("Criar Mago");
        JButton btnListarMagos = new JButton("Listar Magos");
        JButton btnAgendarDuelo = new JButton("Agendar Duelo");
        JButton btnDueloImediato = new JButton("Iniciar Duelo Imediato");
        JButton btnSalvarCSV = new JButton("Salvar Magos em CSV");
        JButton btnCarregarCSV = new JButton("Carregar Magos de CSV");
        JButton btnBuscarMago = new JButton("Buscar Mago por ID");
        JButton btnGrimorio = new JButton("Gerenciar Grimório de um Mago");
        JButton btnSair = new JButton("Sair");

        panel.add(btnCriarMago);
        panel.add(btnListarMagos);
        panel.add(btnAgendarDuelo);
        panel.add(btnDueloImediato);
        panel.add(btnSalvarCSV);
        panel.add(btnCarregarCSV);
        panel.add(btnBuscarMago);
        panel.add(btnGrimorio);
        panel.add(btnSair);

        add(panel);

        // Adaptação dos botões para chamar os serviços do sistema
        btnCriarMago.addActionListener(e -> criadorDeMagos.executar());
        btnListarMagos.addActionListener(e -> listadorDeMagos.executar());
        btnAgendarDuelo.addActionListener(e -> agendadorDeDuelo.executar());
        btnDueloImediato.addActionListener(e -> organizadorDeDuelos.executar());
        btnSalvarCSV.addActionListener(e -> {
            gerenciadorCSV.salvar(gerenciador.listarTodos(), NOME_ARQUIVO);
            JOptionPane.showMessageDialog(this, "Magos salvos com sucesso em " + NOME_ARQUIVO);
        });
        btnCarregarCSV.addActionListener(e -> {
            java.util.List<Ranqueados> magosCarregados = gerenciadorCSV.carregar(NOME_ARQUIVO);
            gerenciador.setListaDeMagos(magosCarregados);
            JOptionPane.showMessageDialog(this, "Magos carregados com sucesso de " + NOME_ARQUIVO +
                    "\n" + magosCarregados.size() + " magos foram carregados.");
        });
        btnBuscarMago.addActionListener(e -> buscadorDeMagos.executar());
        btnGrimorio.addActionListener(e -> criadorDeMagias.executar());
        btnSair.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ColiseuGUI::new);
    }
}