/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gustavoalmeidap.servidortcp.servidor;

import com.google.common.eventbus.EventBus;

/**
 *
 * @author Gustavo
 */
public class MainServer {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        ServidorTCP servidorTCP = new ServidorTCP(12345, eventBus);
        servidorTCP.start();
        System.out.println("Servidor iniciado!");
    }
}
