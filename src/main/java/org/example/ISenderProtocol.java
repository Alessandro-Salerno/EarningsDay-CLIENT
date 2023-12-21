package org.example;

public interface ISenderProtocol {
    void requestUsers();
    void sendLogin(String username);
    void sendLogout();
    void senddQuit();
    void sendAccept();
    void sendReject();
    void sendInvite(String to);
    void sendChatMessage(String message);
    void sendBuy(int units);
    void sendSell(int units);
}
