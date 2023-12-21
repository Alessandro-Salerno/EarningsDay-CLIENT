package io;

import java.io.PrintWriter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MULTI01
 */
public class Sender {
    PrintWriter out;

    public Sender(PrintWriter out) {
        this.out = out;
    }
    
    public void send(String message){
        out.println(message);
        out.flush();
    }
    
    public void close(){
        out.close();
    }
    
    
    
}
