package org.example;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import io.IMessageConsumer;
import io.Reader;
import io.Sender;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 *
 * @author INFODOC-2
 */
public class EarningsDay {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(EarningsDay.class.getResourceAsStream("/Seven Segment.ttf"))));
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        LOOP: while (true) {
            try {
                String ip = JOptionPane.showInputDialog(null, "Server IP address");
                Socket socket = new Socket(ip, 60000);

                Sender sender = new Sender(new PrintWriter(socket.getOutputStream()));
                Reader reader = new Reader(new BufferedReader(new InputStreamReader(socket.getInputStream())), "CLOSEACK");

                ISenderProtocol senderProtocol = new SenderProtocolManager(sender);

                Application app = new Application(senderProtocol);
                IMessageConsumer receiverProtocol = new ReceiverProtocolManager(app);

                View v = new View(senderProtocol);
                app.setAppObserver(v);
                reader.setConsumer(receiverProtocol);

                v.setup();
                break;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Errore di connessione al server");
            } catch (Exception e) {
                return;
            }
        }
    }
    
}
