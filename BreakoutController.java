import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BreakoutController implements KeyListener, ActionListener {
    private final BreakoutModel model;
    private final BreakoutView view;
    private final JFrame frame;
    private final Timer timer;

    private boolean leftPressed;
    private boolean rightPressed;

    public BreakoutController() {
        model = new BreakoutModel();
        view = new BreakoutView(model);

        frame = new JFrame("Breakout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(view);
        frame.pack();

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

        if (currentBall.x <= 0) {
            currentBall.x = 0;
            dx = Math.abs(dx);
        } else if (currentBall.x + currentBall.width >= BreakoutModel.WINDOW_WIDTH) {
            currentBall.x = BreakoutModel.WINDOW_WIDTH - currentBall.width;
            dx = -Math.abs(dx);
        }

        if (currentBall.y <= BreakoutModel.TOP_HUD_HEIGHT) {
            currentBall.y = BreakoutModel.TOP_HUD_HEIGHT;
            dy = Math.abs(dy);
        }

        Rectangle paddle = model.getPaddle();

        if (currentBall.intersects(paddle) && dy > 0) {
            currentBall.y = paddle.y - currentBall.height - 1;

            int paddleCenter = paddle.x + paddle.width / 2;
            int ballCenter = currentBall.x + currentBall.width / 2;

            double relativeIntersect =
                    (double) (ballCenter - paddleCenter) / (paddle.width / 2.0);

            if (relativeIntersect < -1.0) {
                relativeIntersect = -1.0;
            }
            if (relativeIntersect > 1.0) {
                relativeIntersect = 1.0;
            }

            int speed = BreakoutModel.BALL_SPEED;

            dx = (int) Math.round(relativeIntersect * speed);

            if (dx == 0 && relativeIntersect != 0) {
                dx = relativeIntersect < 0 ? -1 : 1;
            }

            dy = -Math.max(2, speed - Math.abs(dx));
        }

        BreakoutModel.Brick[][] bricks = model.getBricks();
        boolean brickHit = false;

        for (int row = 0; row < BreakoutModel.BRICK_ROWS && !brickHit; row++) {
            for (int col = 0; col < BreakoutModel.BRICK_COLS && !brickHit; col++) {
                BreakoutModel.Brick brick = bricks[row][col];

                if (!brick.destroyed && currentBall.intersects(brick.bounds)) {
                    brick.destroyed = true;
                    model.addScore(brick.pointValue);

                    int overlapLeft = (currentBall.x + currentBall.width) - brick.bounds.x;
                    int overlapRight = (brick.bounds.x + brick.bounds.width) - currentBall.x;
                    int overlapTop = (currentBall.y + currentBall.height) - brick.bounds.y;
                    int overlapBottom = (brick.bounds.y + brick.bounds.height) - currentBall.y;

                    int minOverlapX = Math.min(overlapLeft, overlapRight);
                    int minOverlapY = Math.min(overlapTop, overlapBottom);

                    if (minOverlapX < minOverlapY) {
                        if (overlapLeft < overlapRight) {
                            currentBall.x = brick.bounds.x - currentBall.width;
                            dx = -Math.abs(dx);
                        } else {
                            currentBall.x = brick.bounds.x + brick.bounds.width;
                            dx = Math.abs(dx);
                        }
                    } else {
                        if (overlapTop < overlapBottom) {
                            currentBall.y = brick.bounds.y - currentBall.height;
                            dy = -Math.abs(dy);
                        } else {
                            currentBall.y = brick.bounds.y + brick.bounds.height;
                            dy = Math.abs(dy);
                        }
                    }

                    brickHit = true;
                }
            }
        }

        if (currentBall.y > BreakoutModel.WINDOW_HEIGHT) {
            removeBall(firstBall);
            return;
        }

        if (firstBall) {
            model.setBallDX(dx);
            model.setBallDY(dy);
        } else {
            model.setSecondBallDX(dx);
            model.setSecondBallDY(dy);
        }
    }

    private void removeBall(boolean firstBall) {
        if (firstBall) {
            if (model.isSecondBallActive()) {
                Rectangle mainBall = model.getBall();
                Rectangle secondBall = model.getSecondBall();

                mainBall.x = secondBall.x;
                mainBall.y = secondBall.y;
                model.setBallDX(model.getSecondBallDX());
                model.setBallDY(model.getSecondBallDY());

                secondBall.x = -100;
                secondBall.y = -100;
                model.setSecondBallDX(0);
                model.setSecondBallDY(0);
                model.setSecondBallActive(false);
            } else {
                model.loseLife();

                if (model.getLives() <= 0) {
                    model.setGameState(BreakoutModel.GameState.LOST);
                } else {
                    model.resetAfterLifeLost();
                }
            }
        } else {
            model.getSecondBall().x = -100;
            model.getSecondBall().y = -100;
            model.setSecondBallDX(0);
            model.setSecondBallDY(0);
            model.setSecondBallActive(false);
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

        if (key == KeyEvent.VK_SPACE) {
            if (model.getGameState() == BreakoutModel.GameState.TITLE) {
                model.startNewRound();
            } else if (model.getGameState() == BreakoutModel.GameState.WAITING) {
                model.setGameState(BreakoutModel.GameState.PLAYING);
            }
        }

        if (key == KeyEvent.VK_P) {
            if (model.getGameState() == BreakoutModel.GameState.PLAYING) {
                model.setGameState(BreakoutModel.GameState.PAUSED);
            } else if (model.getGameState() == BreakoutModel.GameState.PAUSED) {
                model.setGameState(BreakoutModel.GameState.PLAYING);
            }
        }

        if (key == KeyEvent.VK_R) {
            model.resetGame();
            leftPressed = false;
            rightPressed = false;
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