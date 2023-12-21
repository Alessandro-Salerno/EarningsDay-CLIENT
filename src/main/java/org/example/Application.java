package org.example;

import io.IMessageConsumer;
import io.Sender;

import javax.swing.*;

public class Application implements ICommandConsumer {
    private ISenderProtocol senderProtocolManager;
    private IApplicationObserver appObserver;

    public Application(ISenderProtocol senderProtocolManager) {
        this.senderProtocolManager = senderProtocolManager;
    }

    public void setAppObserver(IApplicationObserver appObserver) {
        this.appObserver = appObserver;
    }

    @Override
    public void users(String[] users) {
        this.appObserver.showUsers(users);
    }

    @Override
    public void loginConfirm() {
        this.appObserver.loginConfirm();
    }

    @Override
    public void userJoined(String username) {
        this.appObserver.userJoined(username);
    }

    @Override
    public void userLeft(String username) {
        this.appObserver.userLeft(username);
    }

    @Override
    public void invited(String from) {
        this.appObserver.showInvite(from);
    }

    @Override
    public void startGame(String units, String balance) {
        this.appObserver.startGame(units, balance);
    }

    @Override
    public void updateTime(String time, String price) {
        this.appObserver.updateTime(time, price);
    }

    @Override
    public void gameWon(String yourNetWorth, String otherNetWorth) {
        this.appObserver.showGameWon(yourNetWorth, otherNetWorth);
    }

    @Override
    public void gameLost(String yourNetWorth, String otherNetWorth) {
        this.appObserver.showGameLost(yourNetWorth, otherNetWorth);
    }

    @Override
    public void gameEnded(String netWorth) {
        this.appObserver.showGameEnded(netWorth);
    }

    @Override
    public void news(String tone, String content) {
        this.appObserver.displayNews(tone, content);
    }

    @Override
    public void chat(String message) {
        this.appObserver.displayChatMessage(message);
    }

    @Override
    public void receipt(String balance, String units) {
        this.appObserver.displayConfirmation(balance, units);
    }

    @Override
    public void error(String error) {
        this.appObserver.error(error);
    }
}
