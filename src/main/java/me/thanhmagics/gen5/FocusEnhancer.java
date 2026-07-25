package me.thanhmagics.gen5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.atomic.AtomicLong;

public class FocusEnhancer {

    private static final AtomicLong time = new AtomicLong(System.currentTimeMillis());
    private static JWindow warningWindow;
    private static boolean isWarningVisible = false;
    private static final long fDeadline = 40000;
    private static long deadline = fDeadline;
    private static boolean initiated = false;
    private static int coefficient = 0;

    public static void refresh() {
        if (!initiated) initiate();
        time.set(System.currentTimeMillis());
        coefficient = 0;
        deadline = fDeadline;
    }

    public static void initiate() {
        if (initiated) return;
        new Timer(1000, e -> checkTimeAndWarn()).start();
        initiated = true;
    }

    private static void checkTimeAndWarn() {
        long currentTime = System.currentTimeMillis();
        boolean shouldWarn = currentTime - time.get() > deadline;
        if (shouldWarn && !isWarningVisible) {
            SwingUtilities.invokeLater(FocusEnhancer::showWarning);
        }
    }

    private static void showWarning() {
        if (isWarningVisible) return;
        isWarningVisible = true;

        warningWindow = new JWindow();
        warningWindow.setAlwaysOnTop(true);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        warningWindow.setSize(screen);
        warningWindow.setLocation(0, 0);

        JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(0, 0, 0, 160));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                int cardW = 520, cardH = 260;
                int cardX = (getWidth() - cardW) / 2;
                int cardY = (getHeight() - cardH) / 2;

                g2d.setColor(new Color(0, 0, 0, 80));
                g2d.fill(new RoundRectangle2D.Float(cardX + 6, cardY + 6, cardW, cardH, 20, 20));

                g2d.setColor(new Color(30, 30, 40));
                g2d.fill(new RoundRectangle2D.Float(cardX, cardY, cardW, cardH, 20, 20));

                g2d.setColor(new Color(255, 80, 60));
                g2d.setStroke(new BasicStroke(2.5f));
                g2d.draw(new RoundRectangle2D.Float(cardX, cardY, cardW, cardH, 20, 20));

                int iconCX = getWidth() / 2;
                int iconY = cardY + 40;
                int[] xPoints = {iconCX, iconCX - 28, iconCX + 28};
                int[] yPoints = {iconY, iconY + 48, iconY + 48};
                g2d.setColor(new Color(255, 200, 40));
                g2d.fillPolygon(xPoints, yPoints, 3);
                g2d.setColor(new Color(30, 30, 40));
                g2d.setFont(new Font("SansSerif", Font.BOLD, 22));
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString("!", iconCX - fm.stringWidth("!") / 2, iconY + 40);

                g2d.setColor(new Color(255, 80, 60));
                g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
                fm = g2d.getFontMetrics();
                String title = "<!> Time Warning <!>";
                g2d.drawString(title, iconCX - fm.stringWidth(title) / 2, cardY + 125);

                g2d.setColor(new Color(210, 210, 220));
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 15));
                fm = g2d.getFontMetrics();
                String msg = "Stay Focused On Your Deadline!";
                g2d.drawString(msg, iconCX - fm.stringWidth(msg) / 2, cardY + 165);


                g2d.dispose();
            }
        };

        overlay.setOpaque(false);
        overlay.setLayout(null);

        overlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dismissWarning();
            }
        });

        warningWindow.setContentPane(overlay);

        try {
            warningWindow.setBackground(new Color(0, 0, 0, 0));
        } catch (Exception ignored) {}

        warningWindow.setVisible(true);

        Timer pulseTimer = new Timer(600, null);
        pulseTimer.addActionListener(e -> overlay.repaint());
        pulseTimer.start();
    }

    private static void dismissWarning() {
        if (warningWindow != null) {
            warningWindow.dispose();
            warningWindow = null;
        }
        isWarningVisible = false;
        time.set(System.currentTimeMillis());
        if (coefficient < 1)
            coefficient++;
        deadline = fDeadline / coefficient;
    }

}