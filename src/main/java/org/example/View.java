package org.example;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * Deve poi implementare la IApplicationObserver per modificarsi 
 * e bisogna passargli il sender manager per inviare le sue azioni
 * @author INFODOC-2
 */
public class View extends JFrame implements IApplicationObserver {
    private Color mainColor = new Color(30, 30, 30);
    private Color foreColor = new Color(255, 165, 0);
    private Font bigFont = new Font("Seven Segment", Font.PLAIN, 24);
    private Font hugeFont = new Font("Seven Segment", Font.PLAIN, 48);
    private Font font = new Font("Seven Segment", Font.PLAIN, 16);
    private Font smallestFont = new Font("Seven Segment", Font.PLAIN, 10);
    private DefaultListModel<String> modelUsers;
    private JList<String> users;
    private DefaultListModel<String> modelMessages;
    private JList<String> events;
    private JButton inviteButton;
    private JButton leaveButton;
    private ISenderProtocol senderProtocol;
    private String username;
    private JFreeChart baseChart;
    private DefaultCategoryDataset dataset;
    private int minimum = 980;
    private int maximum = 1020;
    private JTextArea newsScren;
    private JTextArea chatbox;
    private JTextField chatInput;
    private JLabel clock;
    private JLabel price;
    private JLabel units;
    private JLabel balance;
    
    public View(ISenderProtocol senderProtocol) {
        this.senderProtocol = senderProtocol;
    }

    public void setup() {
        this.username = JOptionPane.showInputDialog(View.this, "Username");
        this.newsScren = this.createText("NO NEWS");
        this.clock = new JLabel("--:--");
        this.price = new JLabel("--");
        this.units = new JLabel("--");
        this.balance = new JLabel("--");

        this.setTitle("Earnings Day");
        this.getContentPane().setFont(font);
        this.getContentPane().setBackground(mainColor);
        this.setBackground(this.mainColor);
        this.setForeground(foreColor);

        this.dataset = new DefaultCategoryDataset();
        this.baseChart = ChartFactory.createLineChart(
                "MARKET",
                "TIME","PRICE",
                createDataset(),
                PlotOrientation.VERTICAL,
                false,false,false);

        StandardChartTheme ct = (StandardChartTheme) StandardChartTheme.createDarknessTheme();
        ct.setPlotBackgroundPaint(this.mainColor);
        ct.setChartBackgroundPaint(this.mainColor);
        ct.setTitlePaint(this.foreColor);
        ct.setLargeFont(this.bigFont);
        ct.setExtraLargeFont(this.bigFont);
        ct.setRegularFont(this.font);
        ct.setSmallFont(this.font);
        ct.setAxisLabelPaint(this.foreColor);
        ct.setTitlePaint(this.foreColor);
        ct.setThermometerPaint(this.foreColor);
        ct.apply(this.baseChart);

        this.baseChart.getCategoryPlot().getRenderer().setSeriesPaint(0, this.foreColor);
        ((LineAndShapeRenderer) this.baseChart.getCategoryPlot().getRenderer()).setAutoPopulateSeriesStroke(false);
        this.baseChart.getCategoryPlot().getRenderer().setDefaultStroke(new BasicStroke(5.0f));

        MagicPane magicCenter = new MagicPane(5, 2);

        ChartPanel chart = new ChartPanel(this.baseChart);
        JPanel control = this.createBorderedPanel("DASHBOARD");
        JPanel chat = this.createBorderedPanel("CHAT");
        JPanel news = this.createBorderedPanel("NEWS");

        magicCenter.setVisible(true);
        magicCenter.setBackground(mainColor);

        this.getContentPane().add(magicCenter);
        this.setVisible(true);

        chart.setMouseWheelEnabled(false);
        chart.setMouseZoomable(false);
        chart.setDomainZoomable(true);

        control.setBackground(mainColor);
        chat.setBackground(mainColor);
        news.setBackground(mainColor);

        chat.setLayout(new BorderLayout());
        this.chatbox = this.createText("");
        this.chatInput = new JTextField();
        this.chatInput.setBackground(this.mainColor);
        this.chatInput.setForeground(this.foreColor);
        this.chatInput.setFont(this.bigFont);
        chat.add(new JScrollPane(this.chatbox), BorderLayout.CENTER);

        JPanel chatControl = new JPanel();
        chatControl.setBorder(BorderFactory.createLineBorder(this.foreColor));
        chatControl.setBackground(this.mainColor);
        chatControl.setLayout(new BorderLayout());
        chatControl.add(this.chatInput, BorderLayout.CENTER);

        JButton send = new JButton("SEND");
        send.setFont(this.font);
        send.setForeground(this.foreColor);
        send.setBackground(this.mainColor);

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View.this.senderProtocol.sendChatMessage(View.this.chatInput.getText());
                View.this.chatInput.setText("");
            }
        });

        chatControl.add(send, BorderLayout.EAST);
        chat.add(chatControl, BorderLayout.SOUTH);

        news.setLayout(new BorderLayout());
        news.add(this.newsScren, BorderLayout.NORTH);

        magicCenter.addMagic(chart, 0, 0, 5, 1);
        magicCenter.addMagic(control, 0, 1, 1, 1);
        magicCenter.addMagic(chat, 1, 1, 2, 1);
        magicCenter.addMagic(news, 3, 1, 2, 1);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                magicCenter.refreshGrid();
            }
        });

        this.modelUsers = new DefaultListModel<>();
        this.users = this.createBorderedJList("USERS", this.modelUsers);
        this.users.setForeground(foreColor);
        this.users.setBackground(mainColor);
        this.users.setFont(font);

        this.users.setPreferredSize(new Dimension(200, 0));
        this.users.setMinimumSize(new Dimension(200, 0));
        this.users.setMaximumSize(new Dimension(200, 0));

        JScrollPane userScroll = new JScrollPane(this.users);
        userScroll.setBorder(null);
        this.getContentPane().add(userScroll, BorderLayout.EAST);

        this.modelMessages = new DefaultListModel<>();
        this.events = this.createBorderedJList("EVENTS", this.modelMessages);
        this.events.setBackground(mainColor);
        this.events.setFont(font);
        this.events.setForeground(foreColor);

        this.events.setPreferredSize(new Dimension(0, 100));
        this.events.setMinimumSize(new Dimension(0, 100));
        this.events.setMaximumSize(new Dimension(0, 100));

        JScrollPane messageScroll = new JScrollPane(this.events);
        messageScroll.setBorder(null);
        messageScroll.setPreferredSize(new Dimension(0, 100));
        messageScroll.setMinimumSize(new Dimension(0, 100));
        messageScroll.setMaximumSize(new Dimension(0, 100));
        this.getContentPane().add(messageScroll,BorderLayout.SOUTH);

        control.setLayout(new BorderLayout());
        JPanel dashNorth = new JPanel();
        dashNorth.setLayout(new GridLayout(1, 4, 8, 8));
        dashNorth.setBackground(this.mainColor);

        this.clock.setForeground(this.foreColor);
        this.clock.setFont(this.bigFont);
        this.clock.setBackground(this.mainColor);
        this.clock.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.price.setForeground(this.foreColor);
        this.price.setFont(this.bigFont);
        this.price.setBackground(this.mainColor);
        this.price.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.units.setForeground(this.foreColor);
        this.units.setFont(this.bigFont);
        this.units.setBackground(this.mainColor);
        this.units.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.balance.setForeground(this.foreColor);
        this.balance.setFont(this.bigFont);
        this.balance.setBackground(this.mainColor);
        this.balance.setAlignmentX(Component.CENTER_ALIGNMENT);

        dashNorth.add(this.clock);
        dashNorth.add(this.price);
        dashNorth.add(this.units);
        dashNorth.add(this.balance);
        control.add(dashNorth, BorderLayout.NORTH);

        JPanel dashCenter = new JPanel();
        dashCenter.setLayout(new GridLayout(4, 1, 8, 8));
        dashCenter.setBackground(this.mainColor);
        JButton sellButton = this.createButton("SELL 1");
        JButton buyButton = this.createButton("BUY 1");
        JButton sell10Button = this.createButton("SELL 10");
        JButton buy10Button = this.createButton("BUY 10");
        sellButton.setActionCommand("1");
        buyButton.setActionCommand("1");
        sell10Button.setActionCommand("10");
        buy10Button.setActionCommand("10");

        ActionListener sellHandler = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int qty = Integer.parseInt(e.getActionCommand());
                View.this.senderProtocol.sendSell(qty);
            }
        };

        ActionListener buyHandler = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int qty = Integer.parseInt(e.getActionCommand());
                View.this.senderProtocol.sendBuy(qty);
            }
        };

        sellButton.addActionListener(sellHandler);
        buyButton.addActionListener(buyHandler);
        sell10Button.addActionListener(sellHandler);
        buy10Button.addActionListener(buyHandler);

        dashCenter.add(sellButton);
        dashCenter.add(buyButton);
        dashCenter.add(sell10Button);
        dashCenter.add(buy10Button);
        control.add(dashCenter, BorderLayout.CENTER);

        this.inviteButton = new JButton ("INVITE");
        this.leaveButton = new JButton("LEAVE GAME");

        inviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = View.this.users.getSelectedValue();

                if (selected == null)
                    return;

                View.this.senderProtocol.sendInvite(selected);
                View.this.modelMessages.addElement("YOU INVITED " + selected.toUpperCase() + " TO PLAY");
            }
        });

        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View.this.senderProtocol.senddQuit();
            }
        });

        this.leaveButton.setEnabled(false);
        this.inviteButton.setOpaque(true);
        this.leaveButton.setOpaque(true);
        this.inviteButton.setBackground(mainColor);
        this.leaveButton.setBackground(mainColor);
        this.inviteButton.setForeground(foreColor);
        this.leaveButton.setForeground(foreColor);
        this.inviteButton.setFont(bigFont);
        this.leaveButton.setFont(bigFont);

        JPanel north = new JPanel();
        north.setBackground(this.mainColor);
        north.add(this.inviteButton);
        north.add(this.leaveButton);
        this.getContentPane().add(north, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    View.this.senderProtocol.sendLogout();
                } catch (Exception ignored) { }
            }
        });

        this.setResizable(false);

        if (!System.getProperties().getProperty("os.name").equals("Mac OS X")) {
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
        } else {
            this.setExtendedState(MAXIMIZED_BOTH);
            this.pack();
        }

        this.setLocationRelativeTo(null);
        this.baseChart.getCategoryPlot().getRangeAxis().setRange(this.minimum, this.maximum);

        this.senderProtocol.sendLogin(this.username);
        this.senderProtocol.requestUsers();
    }

    @Override
    public void showUsers(String[] users) {
        for (String user : users) {
            if (!user.equals(this.username)) {
                this.modelUsers.addElement(user);
            }
        }
    }

    @Override
    public void loginConfirm() {

    }

    private void endGame() {
        this.leaveButton.setEnabled(false);
        this.inviteButton.setEnabled(true);
    }

    @Override
    public void userJoined(String username) {
        this.modelUsers.addElement(username);
        this.modelMessages.addElement(username + " JOINED THE GAME");
    }

    @Override
    public void userLeft(String username) {
        this.modelUsers.removeElement(username);
        this.modelMessages.addElement(username + " LEFT THE GAME");
    }

    @Override
    public void showInvite(String from) {
        int ans = JOptionPane.showConfirmDialog (null, from + " invited you to play. Do you accept?","WARNING", JOptionPane.YES_NO_OPTION);
        this.modelMessages.addElement(from.toUpperCase() + " INVITED YOU TO PLAY");

        if (ans == 0) {
            this.senderProtocol.sendAccept();
            this.modelMessages.addElement("YOU ACCEPTED " + from.toUpperCase() + " REQUEST");
            return;
        }

        this.senderProtocol.sendReject();
        this.modelMessages.addElement("YOU REJECTED " + from.toUpperCase() + " REQUEST");
    }

    @Override
    public void startGame(String units, String balance) {
        this.inviteButton.setEnabled(false);
        this.leaveButton.setEnabled(true);
        this.maximum = 1020;
        this.minimum = 980;

        for (int i = 0; i < 480; i++) {
            this.dataset.setValue(null, "PRICE", Utils.makeTime(i));
        }

        this.newsScren.setText("NO NEWS");
        this.newsScren.setForeground(this.foreColor);
        this.baseChart.getCategoryPlot().getRangeAxis().setRange(this.minimum, this.maximum);
        this.clock.setText("--:--");
        this.price.setText("--");
        this.units.setText(units);
        this.balance.setText(balance);
    }

    @Override
    public void updateTime(String time, String price) {
        this.clock.setText(time);
        this.price.setText(price);
        int intPrice = Integer.parseInt(price);
        this.dataset.setValue(intPrice, "PRICE", time);

        if (intPrice < this.minimum) {
            this.minimum = intPrice;
        }

        if (intPrice > this.maximum) {
            this.maximum = intPrice;
        }

        this.baseChart.getCategoryPlot().getRangeAxis().setRange(this.minimum, this.maximum);
    }

    @Override
    public void showGameWon(String yourNetWorth, String otherNetWorth) {
        JOptionPane.showMessageDialog(this, "You won!\n\nYour NW: $" + yourNetWorth + "\nOpponent NW: $" + otherNetWorth);
        this.endGame();
    }

    @Override
    public void showGameLost(String yourNetWorth, String otherNetWorth) {
        JOptionPane.showMessageDialog(this, "You lost...\n\nYour NW: $" + yourNetWorth + "\nOpponent NW: $" + otherNetWorth);
        this.endGame();
    }

    @Override
    public void showGameEnded(String netWorth) {
        JOptionPane.showMessageDialog(this, "The game ended on a tie of $" + netWorth);
        this.endGame();
    }

    @Override
    public void displayNews(String tone, String content) {
        this.newsScren.setText(content);

        switch (tone) {
            case "GOOD" -> this.newsScren.setForeground(Color.green);
            case "BAD" -> this.newsScren.setForeground(Color.red);
            case "NEUTRAL" -> this.newsScren.setForeground(Color.gray);
        }
    }

    @Override
    public void displayChatMessage(String message) {
        this.chatbox.append(message + "\n");
    }

    @Override
    public void displayConfirmation(String balance, String units) {
        this.fixLabelText(this.balance, Integer.parseInt(balance));
        this.units.setText(units);
    }

    @Override
    public void error(String error) {
        JOptionPane.showMessageDialog(this, error);
    }

    private DefaultCategoryDataset createDataset() {
        for (int i = 0; i < 480; i++) {
            this.dataset.addValue(null, "PRICE", Utils.makeTime(i));
        }

        return this.dataset;
    }

    private BorderedPanel createBorderedPanel(String title) {
        return new BorderedPanel(title, this.foreColor, this.bigFont);
    }

    private JList<String> createBorderedJList(String title, DefaultListModel<String> model) {
        JList<String> list = new JList<>(model);

        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(this.foreColor);
        border.setTitleFont(this.bigFont);

        list.setBorder(border);

        return list;
    }

    private JTextArea createText(String content) {
        JTextArea label = new JTextArea(content);

        label.setVisible(true);
        label.setFont(this.hugeFont);
        label.setForeground(this.foreColor);
        label.setBackground(this.mainColor);
        label.setLineWrap(true);
        label.setWrapStyleWord(true);
        label.setEditable(false);

        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(this.mainColor);
        button.setForeground(this.foreColor);
        button.setFont(this.bigFont);
        button.setVisible(true);
        return button;
    }

    private void fixLabelText(JLabel label, int value) {
        DecimalFormat df = new DecimalFormat("#.#");

        if (Math.abs(value) >= 1000) {
            label.setFont(this.font);
            label.setText(df.format((float)value / 1000) + "K");
        }

        if (Math.abs(value) >= 100000) {
            label.setFont(this.smallestFont);
        }

        if (Math.abs(value) >= 1000000) {
            label.setFont(this.font);
            label.setText(df.format((float)value / 1000000) + "M");
        }

        if (Math.abs(value) >= 1000000000) {
            label.setFont(this.font);
            label.setText(df.format((float)value / 1000000000) + "B");
        }
    }

    @Override
    public Insets getInsets() {
        return new Insets(10, 10, 10, 10);
    }
}
