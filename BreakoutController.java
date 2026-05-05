import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BreakoutController implements KeyListener, ActionListener {
    private BreakoutModel model;
    private BreakoutView view;
    private JFrame frame;
    private Timer timer;

    private boolean leftPressed;
    private boolean rightPressed;

    public BreakoutController() {
        model = new BreakoutModel();
        view = new BreakoutView(model);

        frame = new JFrame("Breakout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(BreakoutModel.WINDOW_WIDTH, BreakoutModel.WINDOW_HEIGHT);
        frame.add(view);
        frame.setResizable(false);

        view.addKeyListener(this);
        view.setFocusable(true);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        view.requestFocusInWindow();

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (model.getGameState() == BreakoutModel.GameState.PLAYING) {
            updatePaddle();
            updateBall(model.getBall(), true);

            if (model.isSecondBallActive()) {
                updateBall(model.getSecondBall(), false);
            }

            checkWin();
        } else if (model.getGameState() == BreakoutModel.GameState.WAITING) {
            updatePaddle();
            model.attachBallToPaddle();

            if (model.isSecondBallActive()) {
                model.attachSecondBallToPaddle();
            }
        }

        view.repaint();
    }

    private void updatePaddle() {
        Rectangle paddle = model.getPaddle();

        if (leftPressed) {
            paddle.x -= BreakoutModel.PADDLE_SPEED;
        }
        if (rightPressed) {
            paddle.x += BreakoutModel.PADDLE_SPEED;
        }

        if (paddle.x < 0) {
            paddle.x = 0;
        }
        if (paddle.x + paddle.width > BreakoutModel.WINDOW_WIDTH) {
            paddle.x = BreakoutModel.WINDOW_WIDTH - paddle.width;
        }
    }

    private void updateBall(Rectangle currentBall, boolean firstBall) {
        int dx = firstBall ? model.getBallDX() : model.getSecondBallDX();
        int dy = firstBall ? model.getBallDY() : model.getSecondBallDY();

        currentBall.x += dx;
        currentBall.y += dy;

        if (currentBall.x <= 0 || currentBall.x + currentBall.width >= BreakoutModel.WINDOW_WIDTH) {
            dx = -dx;
        }

        if (currentBall.y <= BreakoutModel.TOP_HUD_HEIGHT) {
            dy = -dy;
        }

        if (currentBall.intersects(model.getPaddle())) {
            dy = -Math.abs(dy);
        }

        BreakoutModel.Brick[][] bricks = model.getBricks();
        boolean brickHit = false;

        for (int row = 0; row < BreakoutModel.BRICK_ROWS && !brickHit; row++) {
            for (int col = 0; col < BreakoutModel.BRICK_COLS && !brickHit; col++) {
                BreakoutModel.Brick brick = bricks[row][col];

                if (!brick.destroyed && currentBall.intersects(brick.bounds)) {
                    brick.destroyed = true;
                    model.addScore(brick.pointValue);
                    dy = -dy;
                    brickHit = true;
                }
            }
        }

        if (currentBall.y > BreakoutModel.WINDOW_HEIGHT) {
            if (firstBall || !model.isSecondBallActive()) {
                model.loseLife();

                if (model.getLives() <= 0) {
                    model.setGameState(BreakoutModel.GameState.LOST);
                } else {
                    model.resetAfterLifeLost();
                }
                return;
            } else {
                currentBall.x = -100;
                currentBall.y = -100;
                model.setSecondBallDX(0);
                model.setSecondBallDY(0);
                return;
            }
        }

        if (firstBall) {
            model.setBallDX(dx);
            model.setBallDY(dy);
        } else {
            model.setSecondBallDX(dx);
            model.setSecondBallDY(dy);
        }
    }

    private void checkWin() {
        BreakoutModel.Brick[][] bricks = model.getBricks();

        for (int row = 0; row < BreakoutModel.BRICK_ROWS; row++) {
            for (int col = 0; col < BreakoutModel.BRICK_COLS; col++) {
                if (!bricks[row][col].destroyed) {
                    return;
                }
            }
        }

        model.setGameState(BreakoutModel.GameState.WON);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (key == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (key == KeyEvent.VK_SPACE && model.getGameState() == BreakoutModel.GameState.WAITING) {
            model.setGameState(BreakoutModel.GameState.PLAYING);
        }

        if (key == KeyEvent.VK_R) {
            model.resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}