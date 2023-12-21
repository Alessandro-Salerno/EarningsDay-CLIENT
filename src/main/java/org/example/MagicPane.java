package org.example;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.ArrayList;
import java.util.List;

public class MagicPane extends JPanel {
    private static final class MagicComponent {
        private Component component;
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        public MagicComponent(Component component, int x, int y, int width, int height) {
            this.component = component;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public Component getComponent() {
            return this.component;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }

    private List<MagicComponent> components;
    private final int width;
    private final int height;

    public MagicPane(int width, int height) {
        this.components = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.setLayout(null);
    }

    public int getGridWidth() {
        return this.width;
    }

    public int getGridHeight() {
        return this.height;
    }

    public Component addMagic(Component c, int x, int y, int width, int height) {
        c = updateComponent(c, x, y, width, height);
        c.setVisible(true);
        this.components.add(new MagicComponent(c, x, y, width, height));
        return this.add(c);
    }

    private Component updateComponent(Component c, int x, int y, int width, int height) {
        int remX = (this.getWidth() % this.getGridWidth()) / 2;
        int remY = (this.getHeight() % this.getGridHeight()) / 2;

        int pixelX = remX + this.getWidth() / this.getGridWidth() * x;
        int pixelY = remY + this.getHeight() / this.getGridHeight() * y;
        int pixelWidth = this.getWidth() / this.getGridWidth() * width;
        int pixelHeight = this.getHeight() / this.getGridHeight() * height;

        c.setSize(pixelWidth, pixelHeight);
        c.setLocation(pixelX, pixelY);

        return c;
    }

    public void refreshGrid() {
        for (MagicComponent c : components) {
            updateComponent(c.component, c.x, c.y, c.width, c.height);
        }
        this.updateUI();
    }
}
