/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author MULTI01
 */
public class Reader {
    
   private BufferedReader in;
   private boolean stop;
   private String forStop;

    public Reader(BufferedReader in, String forStop) {
        this.in = in;
        this.forStop = forStop;
        this.stop = false;
    }
    
    public void setConsumer(IMessageConsumer consumer){
        InternalReader reader = new InternalReader(in, consumer);
        reader.start();
    }
    
    public void close(){
        stop = true;
       try {
           in.close();
       } catch (IOException ex) {
            throw new RuntimeException(ex);
       }
    }
    
    class InternalReader extends Thread {
        
        BufferedReader in;
        IMessageConsumer consumer;

        public InternalReader(BufferedReader in, IMessageConsumer consumer) {
            this.in = in;
            this.consumer = consumer;
        }
        
        @Override
        public void run(){
            while(!stop){
                try {
                    String message = in.readLine();
                    if(message != null && !message.isEmpty()){
                        consumer.consumeMessage(message);
                        if(message.equals(forStop)){
                            stop = true;
                        }                           
                    } 
                } catch (IOException ex) {
                       throw new RuntimeException(ex);
                }
                
            }
        }
    }
            
   
    
   
   
}
