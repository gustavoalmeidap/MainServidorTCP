/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gustavoalmeidap.servidortcp.servidor;

import com.google.common.eventbus.EventBus;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo
 */
public class ServidorTCP implements Runnable {
    
    private int porta;
    private ServerSocket serverSocket;
    private Socket socket;
    private Thread threadServer;
    private List<ServidorThread> clientes = new ArrayList<>();
    private EventBus eventBus;


    public ServidorTCP(int porta, EventBus eventBus) {
        this.porta = porta;
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }
    
    public void start() {
        threadServer = new Thread((Runnable) this);
        threadServer.start();
    }
    
    public void stop() {
        try {
            threadServer.interrupt();
            serverSocket.close();
            for (ServidorThread cliente : clientes) {
                if (cliente.isAlive()) {
                    cliente.interrupt();
                    cliente.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(porta);            
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        while (!threadServer.isInterrupted()) {
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(5000);
                socket.setKeepAlive(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ServidorThread clienteThread = new ServidorThread(socket);
            clienteThread.start();
            clientes.add(clienteThread);
        }
    }    
}
