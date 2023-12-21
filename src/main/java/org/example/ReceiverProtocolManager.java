package org.example;

import io.IMessageConsumer;

public class ReceiverProtocolManager implements IMessageConsumer {
    private ICommandConsumer commandConsumer;

    public ReceiverProtocolManager(ICommandConsumer commandConsumer) {
        this.commandConsumer = commandConsumer;
    }

    @Override
    public void consumeMessage(String s) {
        String[] segments = s.split("\\*");

        switch (segments[0]) {
            case "LOGINCONFIRM" -> this.commandConsumer.loginConfirm();
            case "USERS" -> this.commandConsumer.users(this.parseList(segments[1]));
            case "JOINED" -> this.commandConsumer.userJoined(segments[1]);
            case "LEFT" -> this.commandConsumer.userLeft(segments[1]);
            case "TIME" -> this.commandConsumer.updateTime(segments[1], segments[2]);
            case "REQUEST" -> this.commandConsumer.invited(segments[1]);
            case "START" -> this.commandConsumer.startGame(segments[1], segments[2]);
            case "WON" -> this.commandConsumer.gameWon(segments[1], segments[2]);
            case "LOST" -> this.commandConsumer.gameLost(segments[1], segments[2]);
            case "END" -> this.commandConsumer.gameEnded(segments[1]);
            case "NEWS" -> this.commandConsumer.news(segments[1], segments[2]);
            case "CHAT" -> this.commandConsumer.chat(segments[1]);
            case "CONFIRM" -> this.commandConsumer.receipt(segments[1], segments[2]);
            case "ERR" -> this.commandConsumer.error(segments[1]);
        }
    }

    private String[] parseList(String listString) {
        return listString.split(",");
    }
}
