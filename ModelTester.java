public class ModelTester {
    private static BreakoutModel model;

    public static void main(String[] args) {
        model = new BreakoutModel();

        System.out.println("=== BreakoutModel Test Suite ===");

        testInitialState();
        testLoseLife();
        testLevelUnlock();
        testResetGame();

        System.out.println("=== All tests complete ===");
    }

    private static void check(String name, boolean condition) {
        String result = condition ? "PASS" : "FAIL";
        System.out.println(name + ": " + result);
    }

    private static void testInitialState() {
        System.out.println("\n1. Initial State");
        check("Lives start at 3", model.getLives() == 3);
        check("Score starts at 0", model.getScore() == 0);
        check("High score starts at 0", model.getHighScore() == 0);
        check("Current level starts at 1", model.getCurrentLevel() == 1);
        check("Game state starts as TITLE",
                model.getGameState() == BreakoutModel.GameState.TITLE);
        check("Second ball inactive", !model.isSecondBallActive());
        check("Third ball inactive", !model.isThirdBallActive());
        check("Level 2 not unlocked", !model.isLevelTwoUnlocked());
        check("Level 3 not unlocked", !model.isLevelThreeUnlocked());
    }

    private static void testLoseLife() {
        System.out.println("\n2. Lose Last Active Ball Decrements Lives");

        model.resetGame();
        model.startNewRound();
        model.setGameState(BreakoutModel.GameState.PLAYING);

        int initialLives = model.getLives();

        model.loseLife();

        check("Lives decrement from " + initialLives + " to " + (initialLives - 1),
                model.getLives() == initialLives - 1);
        check("Lives never go below 0", model.getLives() >= 0);
    }

    private static void testLevelUnlock() {
        System.out.println("\n3. Score Threshold Unlocks Level/Multiball");

        model.resetGame();

        int initialLevel = model.getCurrentLevel();

        model.addScore(60);

        check("Score >= 50 unlocks level 2", model.getCurrentLevel() > initialLevel);
        check("Score >= 50 sets levelTwoUnlocked", model.isLevelTwoUnlocked());
        check("Score >= 50 activates second ball", model.isSecondBallActive());

        model.addScore(100);

        check("Score >= 150 unlocks level 3", model.getCurrentLevel() == 3);
        check("Level 3 unlocks third ball", model.isLevelThreeUnlocked());
        check("Level 3 activates third ball", model.isThirdBallActive());
        check("Level 3 increases ball speed", model.getCurrentBallSpeed() == 6);
        check("Level 3 increases paddle speed", model.getCurrentPaddleSpeed() == 12);
    }

    private static void testResetGame() {
        System.out.println("\n4. Reset Restores Starting Values");

        model.addScore(200);
        model.loseLife();
        model.loseLife();
        model.setGameState(BreakoutModel.GameState.PLAYING);

        int dirtyScore = model.getScore();
        int dirtyLives = model.getLives();
        BreakoutModel.GameState dirtyState = model.getGameState();

        model.resetGame();

        check("Reset clears score (was " + dirtyScore + ")", model.getScore() == 0);
        check("Reset restores lives to 3 (was " + dirtyLives + ")", model.getLives() == 3);
        check("Reset restores TITLE state (was " + dirtyState + ")",
                model.getGameState() == BreakoutModel.GameState.TITLE);
        check("Reset deactivates second ball", !model.isSecondBallActive());
        check("Reset deactivates third ball", !model.isThirdBallActive());
        check("Reset locks level 2", !model.isLevelTwoUnlocked());
        check("Reset locks level 3", !model.isLevelThreeUnlocked());
        check("Reset restores level 1", model.getCurrentLevel() == 1);
        check("Reset restores original speeds",
                model.getCurrentBallSpeed() == BreakoutModel.BALL_SPEED
                        && model.getCurrentPaddleSpeed() == BreakoutModel.PADDLE_SPEED);
    }
}