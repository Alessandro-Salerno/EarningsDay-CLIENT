package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class BorderedPanel extends JPanel {
    public BorderedPanel(String title, Color titleColor, Font titleFont) {
        super();
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleColor(titleColor);
        border.setTitleFont(titleFont);
        this.setBorder(border);
    }
}
