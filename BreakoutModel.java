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

    private Rectangle secondBall;
    private int secondBallDX;
    private int secondBallDY;
    private boolean secondBallActive;
    private boolean levelTwoUnlocked;

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
        levelTwoUnlocked = false;
        secondBallActive = false;

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

        secondBall = new Rectangle(0, 0, BALL_SIZE, BALL_SIZE);
        secondBallDX = -BALL_SPEED;
        secondBallDY = -BALL_SPEED;

        initializeBricks();
    }

    public void resetAfterLifeLost() {
        paddle.x = (WINDOW_WIDTH - PADDLE_WIDTH) / 2;
        paddle.y = WINDOW_HEIGHT - 80;

        attachBallToPaddle();

        ballDX = BALL_SPEED;
        ballDY = -BALL_SPEED;

        if (secondBallActive) {
            attachSecondBallToPaddle();
            secondBallDX = -BALL_SPEED;
            secondBallDY = -BALL_SPEED;
        }

        gameState = GameState.WAITING;
    }

    public void attachBallToPaddle() {
        ball.x = paddle.x + (paddle.width / 2) - (BALL_SIZE / 2);
        ball.y = paddle.y - BALL_SIZE;
    }

    public void attachSecondBallToPaddle() {
        secondBall.x = paddle.x + (paddle.width / 2) - (BALL_SIZE / 2) + 30;
        secondBall.y = paddle.y - BALL_SIZE - 20;
    }

    public void initializeBricks() {
        bricks = new Brick[BRICK_ROWS][BRICK_COLS];

        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICK_COLS; col++) {
                bricks[row][col] = createBrick(row, col);
            }
        }
    }

    private Brick createBrick(int row, int col) {
        int x = getBrickX(col);
        int y = getBrickY(row);
        Color color = getBrickColor(row);
        int points = getBrickPointValue(row);

        return new Brick(x, y, BRICK_WIDTH, BRICK_HEIGHT, color, points);
    }

    private int getBrickX(int col) {
        int startX = 0;
        return startX + col * (BRICK_WIDTH + BRICK_GAP);
    }

    private int getBrickY(int row) {
        int startY = TOP_HUD_HEIGHT + 20;
        return startY + row * (BRICK_HEIGHT + BRICK_GAP);
    }

    private Color getBrickColor(int row) {
        if (row <= 1) {
            return Color.RED;
        } else if (row <= 3) {
            return Color.ORANGE;
        } else if (row <= 5) {
            return Color.GREEN;
        } else {
            return Color.BLUE;
        }
    }

    private int getBrickPointValue(int row) {
        if (row <= 1) {
            return 7;
        } else if (row <= 3) {
            return 5;
        } else if (row <= 5) {
            return 3;
        } else {
            return 1;
        }
    }

    public void unlockLevelTwoMultiBall() {
        if (!levelTwoUnlocked && score >= 100) {
            levelTwoUnlocked = true;
            secondBallActive = true;

            secondBall.width = BALL_SIZE;
            secondBall.height = BALL_SIZE;
            secondBall.x = ball.x + 20;
            secondBall.y = ball.y + 20;

            secondBallDX = -BALL_SPEED;
            secondBallDY = -BALL_SPEED;
        }
    }

    public Rectangle getPaddle() {
        return paddle;
    }

    public Rectangle getBall() {
        return ball;
    }

    public Rectangle getSecondBall() {
        return secondBall;
    }

    public boolean isSecondBallActive() {
        return secondBallActive;
    }

    public boolean isLevelTwoUnlocked() {
        return levelTwoUnlocked;
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

    public int getSecondBallDX() {
        return secondBallDX;
    }

    public int getSecondBallDY() {
        return secondBallDY;
    }

    public void setSecondBallDX(int secondBallDX) {
        this.secondBallDX = secondBallDX;
    }

    public void setSecondBallDY(int secondBallDY) {
        this.secondBallDY = secondBallDY;
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

        unlockLevelTwoMultiBall();
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