import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class BreakoutView extends JPanel {
    private BreakoutModel model;

    public BreakoutView(BreakoutModel model) {
        this.model = model;
        setPreferredSize(new Dimension(BreakoutModel.WINDOW_WIDTH, BreakoutModel.WINDOW_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawHud(g2);
        drawPaddle(g2);
        drawBall(g2);
        drawBricks(g2);
        drawEndMessage(g2);
    }

    private void drawHud(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 18));

        g2.drawString("Score: " + model.getScore(), 20, 30);
        g2.drawString("High Score: " + model.getHighScore(), 170, 30);
        g2.drawString("Lives: " + model.getLives(), 380, 30);
        g2.drawString("Space = Launch", 520, 30);
        g2.drawString("R = Restart", 520, 50);
    }

    private void drawPaddle(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fill(model.getPaddle());
    }

    private void drawBall(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillOval(model.getBall().x, model.getBall().y, model.getBall().width, model.getBall().height);
    }

    private void drawBricks(Graphics2D g2) {
        BreakoutModel.Brick[][] bricks = model.getBricks();

        for (int row = 0; row < BreakoutModel.BRICK_ROWS; row++) {
            for (int col = 0; col < BreakoutModel.BRICK_COLS; col++) {
                BreakoutModel.Brick brick = bricks[row][col];

                if (!brick.destroyed) {
                    g2.setColor(brick.color);
                    g2.fill(brick.bounds);
                    g2.setColor(Color.WHITE);
                    g2.draw(brick.bounds);
                }
            }
        }
    }

    private void drawEndMessage(Graphics2D g2) {
        if (model.getGameState() == BreakoutModel.GameState.WON ||
            model.getGameState() == BreakoutModel.GameState.LOST) {

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 32));

            String message = model.getGameState() == BreakoutModel.GameState.WON
                    ? "YOU WIN!"
                    : "GAME OVER";

            g2.drawString(message, 300, 300);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawString("Press R to Restart", 300, 340);
        }
    }
}