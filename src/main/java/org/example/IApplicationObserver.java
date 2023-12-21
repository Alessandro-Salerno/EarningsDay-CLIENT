package org.example;

public interface IApplicationObserver {
    void showUsers(String[] users);
    void loginConfirm();
    void userJoined(String username);
    void userLeft(String username);
    void showInvite(String from);
    void startGame(String units, String balance);
    void updateTime(String time, String price);
    void showGameWon(String yourNetWorth, String otherNetWorth);
    void showGameLost(String yourNetWorth, String otherNetWorth);
    void showGameEnded(String netWorth);
    void displayNews(String tone, String content);
    void displayChatMessage(String message);
    void displayConfirmation(String balance, String units);
    void error(String error);
}
