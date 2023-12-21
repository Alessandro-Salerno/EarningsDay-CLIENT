package org.example;

import io.Sender;

public class SenderProtocolManager implements ISenderProtocol {
    private Sender sender;

    public SenderProtocolManager(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void requestUsers() {
        this.sender.send("GETUSERS");
    }

    @Override
    public void sendLogin(String username) {
        this.sender.send("LOGIN*" + username);
    }

    @Override
    public void sendLogout() {
        this.sender.send("LOGOUT");
    }

    @Override
    public void senddQuit() {
        this.sender.send("QUITGAME");
    }

    @Override
    public void sendAccept() {
        this.sender.send("ACCEPTGAME");
    }

    @Override
    public void sendReject() {
        this.sender.send("REJECTGAME");
    }

    @Override
    public void sendInvite(String to) {
        this.sender.send("INVITE*" + to);
    }

    @Override
    public void sendChatMessage(String message) {
        this.sender.send("CHAT*" + message);
    }

    @Override
    public void sendBuy(int units) {
        this.sender.send("BUY*" + units);
    }

    @Override
    public void sendSell(int units) {
        this.sender.send("SELL*" + units);
    }
}
