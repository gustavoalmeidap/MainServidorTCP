/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gustavoalmeidap.servidortcp.servidor;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Gustavo
 */
class ServidorThread extends Thread {
    private Socket socket;
    private InputStream inputStream;
    private PrintStream out;
    private static final String MENSAGEM_CONEXAO = "Conexão realizada no servidor";

    public ServidorThread(Socket socketCliente) {
        this.socket = socketCliente;
        try {
            inputStream = socket.getInputStream();
            out = new PrintStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run(){
        try {
            /* Ao conectar, apenas envia uma mensagem para o servidor */
            sendMessage(MENSAGEM_CONEXAO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String message){
        try {
            /* Enviando a mensagem para o cliente */
            out.print(message);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public void close() {
        try {
            if(socket!=null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
