import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class BreakoutView extends JPanel {
    private final BreakoutModel model;

    public BreakoutView(BreakoutModel model) {
        this.model = model;
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(BreakoutModel.WINDOW_WIDTH, BreakoutModel.WINDOW_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawHud(g);
        drawBricks(g);
        drawPaddle(g);
        drawBall(g);
        drawSecondBall(g);
        drawGameStateMessage(g);
    }

    private void drawHud(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + model.getScore(), 20, 30);
        g.drawString("High Score: " + model.getHighScore(), 160, 30);
        g.drawString("Lives: " + model.getLives(), 360, 30);

        if (model.isLevelTwoUnlocked()) {
            g.drawString("Level: 2", 480, 30);
        } else {
            g.drawString("Level: 1", 480, 30);
        }

        g.drawString("Space = Start", 580, 30);
        g.drawString("R = Restart", 580, 50);
        g.drawString("P = Pause", 580, 70);
    }

    private void drawBricks(Graphics g) {
        if (model.getGameState() == BreakoutModel.GameState.TITLE) {
            return;
        }

        BreakoutModel.Brick[][] bricks = model.getBricks();

        for (int row = 0; row < BreakoutModel.BRICK_ROWS; row++) {
            for (int col = 0; col < BreakoutModel.BRICK_COLS; col++) {
                BreakoutModel.Brick brick = bricks[row][col];

                if (!brick.destroyed) {
                    g.setColor(brick.color);
                    g.fillRect(brick.bounds.x, brick.bounds.y, brick.bounds.width, brick.bounds.height);

                    g.setColor(Color.BLACK);
                    g.drawRect(brick.bounds.x, brick.bounds.y, brick.bounds.width, brick.bounds.height);
                }
            }
        }
    }

    private void drawPaddle(Graphics g) {
        if (model.getGameState() == BreakoutModel.GameState.TITLE) {
            return;
        }

        g.setColor(Color.WHITE);
        g.fillRect(model.getPaddle().x, model.getPaddle().y, model.getPaddle().width, model.getPaddle().height);
    }

    private void drawBall(Graphics g) {
        if (model.getGameState() == BreakoutModel.GameState.TITLE) {
            return;
        }

        g.setColor(Color.YELLOW);
        g.fillOval(model.getBall().x, model.getBall().y, model.getBall().width, model.getBall().height);
    }

    private void drawSecondBall(Graphics g) {
        if (model.getGameState() == BreakoutModel.GameState.TITLE) {
            return;
        }

        if (model.isSecondBallActive()) {
            g.setColor(Color.CYAN);
            g.fillOval(model.getSecondBall().x, model.getSecondBall().y,
                    model.getSecondBall().width, model.getSecondBall().height);
        }
    }

    private void drawCenteredString(Graphics g, String text, int y, Font font) {
        g.setFont(font);
        int textWidth = g.getFontMetrics().stringWidth(text);
        int x = (BreakoutModel.WINDOW_WIDTH - textWidth) / 2;
        g.drawString(text, x, y);
    }

    private void drawGameStateMessage(Graphics g) {
        g.setColor(Color.WHITE);

        if (model.getGameState() == BreakoutModel.GameState.TITLE) {
            drawCenteredString(g, "BREAKOUT", 220, new Font("Arial", Font.BOLD, 42));
            drawCenteredString(g, "Press SPACE to Start", 300, new Font("Arial", Font.BOLD, 28));
            drawCenteredString(g, "A / D = Move   P = Pause   R = Restart", 360, new Font("Arial", Font.PLAIN, 20));
        } else if (model.getGameState() == BreakoutModel.GameState.WAITING) {
            drawCenteredString(g, "Press SPACE to Launch", 350, new Font("Arial", Font.BOLD, 28));
        } else if (model.getGameState() == BreakoutModel.GameState.PAUSED) {
            drawCenteredString(g, "PAUSED", 320, new Font("Arial", Font.BOLD, 32));
            drawCenteredString(g, "Press P to Resume", 370, new Font("Arial", Font.BOLD, 24));
        } else if (model.getGameState() == BreakoutModel.GameState.WON) {
            drawCenteredString(g, "You Win! Press R to Restart", 350, new Font("Arial", Font.BOLD, 28));
        } else if (model.getGameState() == BreakoutModel.GameState.LOST) {
            drawCenteredString(g, "Game Over! Press R to Restart", 350, new Font("Arial", Font.BOLD, 28));
        }
    }
}