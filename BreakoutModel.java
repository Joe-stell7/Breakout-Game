import java.awt.Color;
import java.awt.Rectangle;

public class BreakoutModel {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public static final int TOP_HUD_HEIGHT = 60;

    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 15;
    public static final int PADDLE_SPEED = 8;

    public static final int BALL_SIZE = 14;
    public static final int BALL_SPEED = 4;

    public static final int BRICK_ROWS = 8;
    public static final int BRICK_COLS = 8;
    public static final int BRICK_WIDTH = 95;
    public static final int BRICK_HEIGHT = 20;
    public static final int BRICK_GAP = 5;

    public enum GameState {
        WAITING, PLAYING, WON, LOST
    }

    public static class Brick {
        Rectangle bounds;
        Color color;
        int pointValue;
        boolean destroyed;

        public Brick(int x, int y, int width, int height, Color color, int pointValue) {
            this.bounds = new Rectangle(x, y, width, height);
            this.color = color;
            this.pointValue = pointValue;
            this.destroyed = false;
        }
    }

    private Rectangle paddle;
    private Rectangle ball;
    private int ballDX;
    private int ballDY;

    private Brick[][] bricks;

    private int lives;
    private int score;
    private int highScore;

    private GameState gameState;

    public BreakoutModel() {
        resetGame();
    }

    public void resetGame() {
        lives = 3;
        score = 0;
        gameState = GameState.WAITING;

        paddle = new Rectangle(
                (WINDOW_WIDTH - PADDLE_WIDTH) / 2,
                WINDOW_HEIGHT - 80,
                PADDLE_WIDTH,
                PADDLE_HEIGHT
        );

        ball = new Rectangle(0, 0, BALL_SIZE, BALL_SIZE);
        attachBallToPaddle();

        ballDX = BALL_SPEED;
        ballDY = -BALL_SPEED;

        initializeBricks();
    }

    public void resetAfterLifeLost() {
        paddle.x = (WINDOW_WIDTH - PADDLE_WIDTH) / 2;
        paddle.y = WINDOW_HEIGHT - 80;

        attachBallToPaddle();

        ballDX = BALL_SPEED;
        ballDY = -BALL_SPEED;

        gameState = GameState.WAITING;
    }

    public void attachBallToPaddle() {
        ball.x = paddle.x + (paddle.width / 2) - (BALL_SIZE / 2);
        ball.y = paddle.y - BALL_SIZE;
    }

    public void initializeBricks() {
        bricks = new Brick[BRICK_ROWS][BRICK_COLS];

        int startX = 0;
        int startY = TOP_HUD_HEIGHT + 20;

        for (int row = 0; row < BRICK_ROWS; row++) {
            Color color;
            int points;

            if (row <= 1) {
                color = Color.RED;
                points = 7;
            } else if (row <= 3) {
                color = Color.ORANGE;
                points = 5;
            } else if (row <= 5) {
                color = Color.GREEN;
                points = 3;
            } else {
                color = Color.BLUE;
                points = 1;
            }

            for (int col = 0; col < BRICK_COLS; col++) {
                int x = startX + col * (BRICK_WIDTH + BRICK_GAP);
                int y = startY + row * (BRICK_HEIGHT + BRICK_GAP);

                bricks[row][col] = new Brick(x, y, BRICK_WIDTH, BRICK_HEIGHT, color, points);
            }
        }
    }

    public Rectangle getPaddle() {
        return paddle;
    }

    public Rectangle getBall() {
        return ball;
    }

    public Brick[][] getBricks() {
        return bricks;
    }

    public int getBallDX() {
        return ballDX;
    }

    public int getBallDY() {
        return ballDY;
    }

    public void setBallDX(int ballDX) {
        this.ballDX = ballDX;
    }

    public void setBallDY(int ballDY) {
        this.ballDY = ballDY;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        lives--;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
        if (score > highScore) {
            highScore = score;
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}