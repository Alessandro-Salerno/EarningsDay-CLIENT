package org.example;

public interface ICommandConsumer {
    void users(String[] users);
    void loginConfirm();
    void userJoined(String username);
    void userLeft(String username);
    void invited(String from);
    void startGame(String units, String balance);
    void updateTime(String time, String price);
    void gameWon(String yourNetWorth, String otherNetWorth);
    void gameLost(String yourNetWorth, String otherNetWorth);
    void gameEnded(String netWorth);
    void news(String tone, String content);
    void chat(String message);
    void receipt(String balance, String units);
    void error(String error);
}
