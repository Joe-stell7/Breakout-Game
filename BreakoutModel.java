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
        TITLE, WAITING, PLAYING, PAUSED, WON, LOST
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

    private Rectangle thirdBall;
    private int thirdBallDX;
    private int thirdBallDY;
    private boolean thirdBallActive;
    private boolean levelThreeUnlocked;

    private Brick[][] bricks;

    private int lives;
    private int score;
    private int highScore;

    private int currentLevel;
    private int currentBallSpeed;
    private int currentPaddleSpeed;

    private GameState gameState;

    public BreakoutModel() {
        resetGame();
    }

    public void resetGame() {
        lives = 3;
        score = 0;
        levelTwoUnlocked = false;
        levelThreeUnlocked = false;
        secondBallActive = false;
        thirdBallActive = false;
        currentLevel = 1;
        currentBallSpeed = BALL_SPEED;
        currentPaddleSpeed = PADDLE_SPEED;
        gameState = GameState.TITLE;

        paddle = new Rectangle(
                (WINDOW_WIDTH - PADDLE_WIDTH) / 2,
                WINDOW_HEIGHT - 80,
                PADDLE_WIDTH,
                PADDLE_HEIGHT
        );

        ball = new Rectangle(0, 0, BALL_SIZE, BALL_SIZE);
        attachBallToPaddle();

        ballDX = 0;
        ballDY = -currentBallSpeed;

        secondBall = new Rectangle(-100, -100, BALL_SIZE, BALL_SIZE);
        secondBallDX = 0;
        secondBallDY = 0;

        thirdBall = new Rectangle(-100, -100, BALL_SIZE, BALL_SIZE);
        thirdBallDX = 0;
        thirdBallDY = 0;

        initializeBricks();
    }

    public void startNewRound() {
        paddle.x = (WINDOW_WIDTH - PADDLE_WIDTH) / 2;
        paddle.y = WINDOW_HEIGHT - 80;

        attachBallToPaddle();
        ballDX = 0;
        ballDY = -currentBallSpeed;

        secondBallActive = false;
        secondBall.x = -100;
        secondBall.y = -100;
        secondBallDX = 0;
        secondBallDY = 0;

        thirdBallActive = false;
        thirdBall.x = -100;
        thirdBall.y = -100;
        thirdBallDX = 0;
        thirdBallDY = 0;

        gameState = GameState.WAITING;
    }

    public void resetAfterLifeLost() {
        startNewRound();
    }

    public void attachBallToPaddle() {
        ball.x = paddle.x + (paddle.width / 2) - (BALL_SIZE / 2);
        ball.y = paddle.y - BALL_SIZE;
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

    public void updateLevelAndSpeeds() {
        if (score >= 150) {
            currentLevel = 3;
            currentBallSpeed = 6;
            currentPaddleSpeed = 12;
        } else if (score >= 50) {
            currentLevel = 2;
            currentBallSpeed = 4;
            currentPaddleSpeed = 10;
        } else {
            currentLevel = 1;
            currentBallSpeed = 4;
            currentPaddleSpeed = 8;
        }
    }

    public void unlockLevelTwoMultiBall() {
        if (!levelTwoUnlocked && score >= 50) {
            levelTwoUnlocked = true;
            secondBallActive = true;

            secondBall.width = BALL_SIZE;
            secondBall.height = BALL_SIZE;
            secondBall.x = ball.x + 20;
            secondBall.y = ball.y + 20;

            secondBallDX = currentBallSpeed;
            secondBallDY = -currentBallSpeed;
        }
    }

    public void unlockLevelThreeMultiBall() {
        if (!levelThreeUnlocked && score >= 150) {
            levelThreeUnlocked = true;
            thirdBallActive = true;

            thirdBall.width = BALL_SIZE;
            thirdBall.height = BALL_SIZE;
            thirdBall.x = ball.x - 20;
            thirdBall.y = ball.y + 20;

            thirdBallDX = -currentBallSpeed;
            thirdBallDY = -currentBallSpeed;
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

    public Rectangle getThirdBall() {
        return thirdBall;
    }

    public boolean isSecondBallActive() {
        return secondBallActive;
    }

    public void setSecondBallActive(boolean secondBallActive) {
        this.secondBallActive = secondBallActive;
    }

    public boolean isThirdBallActive() {
        return thirdBallActive;
    }

    public void setThirdBallActive(boolean thirdBallActive) {
        this.thirdBallActive = thirdBallActive;
    }

    public boolean isLevelTwoUnlocked() {
        return levelTwoUnlocked;
    }

    public boolean isLevelThreeUnlocked() {
        return levelThreeUnlocked;
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

    public int getThirdBallDX() {
        return thirdBallDX;
    }

    public int getThirdBallDY() {
        return thirdBallDY;
    }

    public void setThirdBallDX(int thirdBallDX) {
        this.thirdBallDX = thirdBallDX;
    }

    public void setThirdBallDY(int thirdBallDY) {
        this.thirdBallDY = thirdBallDY;
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

        updateLevelAndSpeeds();
        unlockLevelTwoMultiBall();
        unlockLevelThreeMultiBall();
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentBallSpeed() {
        return currentBallSpeed;
    }

    public int getCurrentPaddleSpeed() {
        return currentPaddleSpeed;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}