import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class BreakoutController extends KeyAdapter implements ActionListener {
    private BreakoutModel model;
    private BreakoutView view;
    private Timer timer;

    private boolean movingLeft;
    private boolean movingRight;

    public BreakoutController(BreakoutModel model, BreakoutView view) {
        this.model = model;
        this.view = view;

        view.addKeyListener(this);

        timer = new Timer(16, this);
    }

    public void startGameLoop() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        view.repaint();
    }

    private void updateGame() {
        movePaddle();

        if (model.getGameState() == BreakoutModel.GameState.WAITING) {
            model.attachBallToPaddle();
            return;
        }

        if (model.getGameState() != BreakoutModel.GameState.PLAYING) {
            return;
        }

        moveBall();
        checkWallCollisions();
        checkPaddleCollision();
        checkBrickCollisions();
        checkWinOrLoss();
    }

    private void movePaddle() {
        if (movingLeft) {
            model.getPaddle().x -= BreakoutModel.PADDLE_SPEED;
        }
        if (movingRight) {
            model.getPaddle().x += BreakoutModel.PADDLE_SPEED;
        }

        if (model.getPaddle().x < 0) {
            model.getPaddle().x = 0;
        }

        if (model.getPaddle().x + model.getPaddle().width > BreakoutModel.WINDOW_WIDTH) {
            model.getPaddle().x = BreakoutModel.WINDOW_WIDTH - model.getPaddle().width;
        }
    }

    private void moveBall() {
        model.getBall().x += model.getBallDX();
        model.getBall().y += model.getBallDY();
    }

    private void checkWallCollisions() {
        if (model.getBall().x <= 0 || model.getBall().x + model.getBall().width >= BreakoutModel.WINDOW_WIDTH) {
            model.setBallDX(-model.getBallDX());
        }

        if (model.getBall().y <= BreakoutModel.TOP_HUD_HEIGHT) {
            model.setBallDY(-model.getBallDY());
        }

        if (model.getBall().y > BreakoutModel.WINDOW_HEIGHT) {
            model.loseLife();

            if (model.getLives() <= 0) {
                model.setGameState(BreakoutModel.GameState.LOST);
            } else {
                model.resetAfterLifeLost();
            }
        }
    }

    private void checkPaddleCollision() {
        if (model.getBall().intersects(model.getPaddle())) {
            int paddleLeft = model.getPaddle().x;
            int paddleWidth = model.getPaddle().width;
            int ballCenter = model.getBall().x + model.getBall().width / 2;

            int relativeHit = ballCenter - paddleLeft;

            if (relativeHit < paddleWidth / 3) {
                model.setBallDX(-Math.abs(BreakoutModel.BALL_SPEED));
            } else if (relativeHit < 2 * paddleWidth / 3) {
                model.setBallDX(0);
            } else {
                model.setBallDX(Math.abs(BreakoutModel.BALL_SPEED));
            }

            model.setBallDY(-Math.abs(BreakoutModel.BALL_SPEED));
            model.getBall().y = model.getPaddle().y - model.getBall().height;
        }
    }

    private void checkBrickCollisions() {
        BreakoutModel.Brick[][] bricks = model.getBricks();

        for (int row = 0; row < BreakoutModel.BRICK_ROWS; row++) {
            for (int col = 0; col < BreakoutModel.BRICK_COLS; col++) {
                BreakoutModel.Brick brick = bricks[row][col];

                if (!brick.destroyed && model.getBall().intersects(brick.bounds)) {
                    brick.destroyed = true;
                    model.addScore(brick.pointValue);
                    model.setBallDY(-model.getBallDY());
                    return;
                }
            }
        }
    }

    private void checkWinOrLoss() {
        boolean allDestroyed = true;

        for (int row = 0; row < BreakoutModel.BRICK_ROWS; row++) {
            for (int col = 0; col < BreakoutModel.BRICK_COLS; col++) {
                if (!model.getBricks()[row][col].destroyed) {
                    allDestroyed = false;
                    break;
                }
            }
        }

        if (allDestroyed) {
            model.setGameState(BreakoutModel.GameState.WON);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            movingLeft = true;
        } else if (key == KeyEvent.VK_D) {
            movingRight = true;
        } else if (key == KeyEvent.VK_SPACE) {
            if (model.getGameState() == BreakoutModel.GameState.WAITING) {
                model.setGameState(BreakoutModel.GameState.PLAYING);
            }
        } else if (key == KeyEvent.VK_R) {
            model.resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            movingLeft = false;
        } else if (key == KeyEvent.VK_D) {
            movingRight = false;
        }
    }
}