import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

class GraphicalInterface extends JFrame {

    public GraphicalInterface(int[] scores, int imageH, int imageW) {
        trackPanel = new TrackPanel(scores, imageH, imageW);
        trackPanel.setBackground(new Color(255, 255, 255));
        trackPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setContentPane(trackPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private JPanel trackPanel;

    class TrackPanel extends JPanel {

        int[] scores;
        int imageH;
        int imageW;

        TrackPanel(int[] scores, int imageH, int imageW) {
            this.scores = scores;
            this.imageH = imageH;
            this.imageW = imageW;
            setPreferredSize(new Dimension(420,420));
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);


            int origin = 50;
            for (int hh = 0; hh < imageH; hh++) {
                for (int ww = 0; ww < imageW; ww++) {
                    g.setColor(Color.black);
                    g.drawRect(origin + ww*20, origin + hh * 20, 20, 20);
                    g.drawString(String.valueOf(scores[hh * imageW + ww]), origin + ww * 20 + 10, origin + hh * 20 + 10);
                }
            }
        }
    }
}