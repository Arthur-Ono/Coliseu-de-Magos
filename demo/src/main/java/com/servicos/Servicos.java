package com.servicos;

import java.util.Scanner;
import com.GerenciadorDeMagos.Gerenciador;
abstract class Servicos {
    protected Scanner scanner;
    protected Gerenciador gerenciador;
    
    public Servicos(Scanner scanner, Gerenciador gerenciador) {
        this.scanner = scanner;
        this.gerenciador = gerenciador;
    }
    public abstract void executar();

}
