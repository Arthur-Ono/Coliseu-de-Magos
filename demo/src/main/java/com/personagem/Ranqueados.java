package com.personagem;

import java.util.ArrayList;
import java.util.List;



import com.feitico.Magia;
import com.feitico.Projetil;


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
    private int tempoEmCombate;

    
    // contadores
    private int contadorAbate;
    private int contadorDano;
    private int contadorMitigado;
    private int contadorDanoRecebido;
    private int contadorUltimoAtacante;
    private int acertoChance;
    private int criticoChance;
    private int manaGasta;
    
    public Ranqueados(int id, String codinome, String escola, int vidaMax, int manaMax, String foco, int poderBase, int resistencia, int controlador, int horaEntrada, int velocidade, float acerto, float critico, int abates, int assistencias, int danoCausado, int danoMitigado, int rupturas, int capturas, int tempo) {
        super(id, codinome, escola, vidaMax, manaMax, foco, poderBase, resistencia, controlador, horaEntrada, velocidade, acerto, critico);
        this.abates = abates;
        this.assistencias = assistencias;
        this.danoCausado = danoCausado;
        this.danoMitigado = danoMitigado;
        this.rupturas = rupturas;
        this.capturas = capturas;
    }
    
    
    public int getAcertoChance() {
        return acertoChance;
    }
    public void setAcertoChance(int acertoChance) {
        this.acertoChance = acertoChance;
    }
    public int getCriticoChance() {
        return criticoChance;
    }
    public void setCriticoChance(int criticoChance) {
        this.criticoChance = criticoChance;
    }
    public int getManaGasta() {
        return manaGasta;
    }
    public void setManaGasta(int manaGasta) {
        this.manaGasta = manaGasta;
    }
    public int getTempoEmCombate() {
        return tempoEmCombate;
    }
    public void setTempoEmCombate(int tempoEmCombate) {
        this.tempoEmCombate = tempoEmCombate;
    }

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


    public int receberDano(int poderBase) {
        int x = poderBase - super.getResistencia();
        if (x < 0) {
            x = 0;
        }
        int mitigado = poderBase-x;
        setContadorMitigado(getContadorMitigado()+mitigado);
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

        return x;
        // contadores abaixo
        // lembrar que são referentes QUEM ESTÁ ATACANDO

        //setContadorDano(getContadorDano() + x);
        //System.out.println("Dano causado total :" + contadorDano);

    }

    public void causarDano(Ranqueados alvo, Magia magiaseleciona) {
        double chance = Math.random();
        boolean acertou = chance<=0.95;
        boolean critico = chance <=0.10;

        if (magiaseleciona instanceof Projetil && "Arcano".equalsIgnoreCase(this.getEscola())) {
            critico = Math.random() <0.3;
        }


        if (!acertou) {
            System.out.println("O ataque errou!!");
            return;
        }

        if (magiaseleciona==null) {
            
            System.out.println("Mago " + super.getCodinome() + " ataca o mago " + alvo.getCodinome());

        }
        else {
            System.out.println("Mago " + super.getCodinome() + " ataca o mago " + alvo.getCodinome()+" com a magia "+magiaseleciona.getNome() +"\n");
        }
            // registra o ultimo atacante
        alvo.setContadorUltimoAtacante(this.getId());

        // calcula o dano referente a magia, e se nao tiver/selecionar magia, usa ataque básico 
        int dano;
        if (magiaseleciona !=null) {
            dano = magiaseleciona.calcularDano(this.getEscola(),this.getPoderBase());
        }
        else{
            dano = this.getPoderBase();
        }

        if (critico) {
            dano*=2;
            setCritico(getCritico()+1);
            System.out.println("ACERTO CRÍTICO!!!!\n");
        }
        else{
            setAcerto(getAcerto()+1);
        }
        // ataca ne
        
        int danoEfetivo = alvo.receberDano(dano);
        setContadorDano(getContadorDano()+danoEfetivo);
        System.out.println("Dano causado total :" + getContadorDano());
    }

    public void incrementarRanking(Ranqueados alvo, ArrayList<Ranqueados> todosMagos) {
        // Abate e assistência
        if (alvo.getContadorAbate() == 1) {
            int idMatador = alvo.getContadorUltimoAtacante();
            // Matador recebe abate
            for (Ranqueados mago : todosMagos) {
                if (mago.getId() == idMatador) {
                    mago.setAbates(mago.getAbates() + 1);
                }
            }
           
            alvo.setContadorAbate(0);
            alvo.setContadorUltimoAtacante(-1);
        }

        // Dano causado
        setDanoCausado(getDanoCausado() + getContadorDano());
        setContadorDano(0);

        
        alvo.setDanoMitigado(alvo.getDanoMitigado() + alvo.getContadorMitigado());
        alvo.setContadorMitigado(0);
    }

    public void imprimirRanking() {
        System.out.println("------------");
        System.out.println("Codinome: " + getCodinome());
        System.out.println("Abates: " + getAbates());
        System.out.println("Assistências: " + getAssistencias());
        System.out.println("Dano causado: " + getDanoCausado());
        System.out.println("Dano mitigado: " + getDanoMitigado());
        System.out.println("Capturas de objetivo: " + getCapturas());
        System.out.println("Rupturas de canalização: " + getRupturas());
        System.out.println("Tempo em combate: " + getTempoEmCombate());
        System.out.println("Acertos: " + getAcerto());
        System.out.println("Críticos: " + getCritico());
        System.out.println("Eficiência de mana: " + ((getManaGasta() > 0) ? ((double)getDanoCausado() / getManaGasta()) : 0));
        System.out.println("------------");
    }
}