package com.personagem;

import java.util.ArrayList;
import java.util.List;

import com.feitico.Magia;


public abstract class Ranqueados extends Personagem {


    // onde guardar as magias :p
    private List<Magia> grimorio = new ArrayList<Magia>();
    public void adicionarMagia(Magia magia){
        grimorio.add(magia);
    }
    public List<Magia> getGrimorio(){
        return grimorio;
    }

    // atributos de ranking
    private int abates;
    private int assistencias;
    private int danoCausado;
    private int danoMitigado;
    private int rupturas;
    private int capturas;

    // contadores
    private int contadorAbate;
    private int contadorDano;
    private int contadorMitigado;
    private int contadorDanoRecebido;
    private int contadorUltimoAtacante;

    

    public int getContadorAbate() {
        return contadorAbate;
    }

    public void setContadorAbate(int contador) {
        this.contadorAbate = contador;
    }

    public int getAbates() {
        return abates;
    }

    public void setAbates(int abates) {
        this.abates = abates;
    }

    public int getAssistencias() {
        return assistencias;
    }

    public void setAssistencias(int assistencias) {
        this.assistencias = assistencias;
    }

    public int getDanoCausado() {
        return danoCausado;
    }

    public void setDanoCausado(int danoCausado) {
        this.danoCausado = danoCausado;
    }

    public int getDanoMitigado() {
        return danoMitigado;
    }

    public void setDanoMitigado(int danoMitigado) {
        this.danoMitigado = danoMitigado;
    }

    public int getRupturas() {
        return rupturas;
    }

    public void setRupturas(int rupturas) {
        this.rupturas = rupturas;
    }

    public int getCapturas() {
        return capturas;
    }

    public void setCapturas(int capturas) {
        this.capturas = capturas;
    }

    public int getContadorDano() {
        return contadorDano;
    }

    public void setContadorDano(int contadorDano) {
        this.contadorDano = contadorDano;
    }

    public int getContadorMitigado() {
        return contadorMitigado;
    }

    public void setContadorMitigado(int contadorMitigado) {
        this.contadorMitigado = contadorMitigado;
    }

    public int getContadorDanoRecebido() {
        return contadorDanoRecebido;
    }

    public void setContadorDanoRecebido(int contadorDanoRecebido) {
        this.contadorDanoRecebido = contadorDanoRecebido;
    }

    public int getContadorUltimoAtacante() {
        return contadorUltimoAtacante;
    }

    public void setContadorUltimoAtacante(int contadorUltimoAtacante) {
        this.contadorUltimoAtacante = contadorUltimoAtacante;
    }

    public Ranqueados(int id, String codinome, String escola, int vidaMax, int manaMax, String foco, int poderBase,
                      int resistencia, int controlador, int horaEntrada, int velocidade ,int abates, int assistencias, int danoCausado,
                      int danoMitigado, int rupturas, int capturas) {
        super(id, codinome, escola, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada, velocidade);
        this.abates = abates;
        this.assistencias = assistencias;
        this.danoCausado = danoCausado;
        this.danoMitigado = danoMitigado;
        this.rupturas = rupturas;
        this.capturas = capturas;
    }

    public void receberDano(int poderBase) {
        int x = 0;
        x = poderBase - super.getResistencia();
        if (x < 0) {
            x = 0;
        }

        System.out.println("----------------------------");

        System.out.println("Mago " + super.getCodinome() + " leva " + x + " de dano!");
        setVidaAtual(super.getVidaAtual() - x);
        System.out.println("Vida atual do " + super.getCodinome() + ": " + super.getVidaAtual());

        if (super.getVidaAtual() < 0) {
            setVidaAtual(0);
        }

        if (super.getVidaAtual() == 0) {
            System.out.println("Mago " + super.getCodinome() + " está fora de combate!");
            setContadorAbate(1);
        }

        // contadores abaixo
        // lembrar que são referentes QUEM ESTÁ ATACANDO

        setContadorDano(getContadorDano() + x);
        System.out.println("Dano causado total :" + contadorDano);

    }

    public void causarDano(Ranqueados alvo, Magia magiaseleciona) {
        System.out.println("Mago " + super.getCodinome() + " ataca o mago " + alvo.getCodinome());
        // registra o ultimo atacante
        alvo.setContadorUltimoAtacante(this.getId());

        // calcula o dano referente a magia, e se nao tiver/selecionar magia, usa ataque básico 
        int dano;
        if (magiaseleciona !=null) {
            dano = magiaseleciona.calcularDano(this.getEscola());
        }
        else{
            dano = this.getPoderBase();
        }

        // ataca ne
        alvo.receberDano(dano);
        if (alvo.getContadorAbate() == 1) {
            setAbates(getAbates() + 1);
            alvo.contadorAbate = 0;
        }
    }

    public void incrementarRanking(Ranqueados alvo, ArrayList<Ranqueados> todosMagos) {
        // incremento de abate
        if (alvo.getContadorAbate() == 1) {
            setAbates(getAbates() + 1);
            int idAssistente = alvo.getContadorUltimoAtacante();
            if (idAssistente != -1 && idAssistente != this.getId()) {
                for (Ranqueados mago : todosMagos) {
                    if (mago.getId() == idAssistente) {
                        mago.setAssistencias(mago.getAssistencias() + 1);
                        break;
                    }
                }
            }

            alvo.contadorAbate = 0;
            alvo.setContadorUltimoAtacante(-1);
        }
        // incrementa o dano causado
        setDanoCausado(getDanoCausado() + getContadorDano());
        setContadorDano(0);

        // incrementa dano mitigado
        int poder = this.getPoderBase();
        int resistencia = alvo.getResistencia();
        int mitigado = Math.min(poder, resistencia);
        if (mitigado < 0) {
            mitigado = 0;
        }
        alvo.setDanoMitigado(alvo.getDanoMitigado() + mitigado);

    }

    public void imprimirRanking() {
        System.out.println("------------");
        System.out.println("Codinome: " + super.getCodinome());
        System.out.println("Abates: " + abates);
        System.out.println("Assistências: " + assistencias);
        System.out.println("Dano causado: " + danoCausado);
        System.out.println("Dano mitigado: " + danoMitigado);
        System.out.println("Capturas de objetivo: " + capturas);
        System.out.println("Rupturas de canalização: " + rupturas);
    }
}