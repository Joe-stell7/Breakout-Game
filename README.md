Breakout — Spec

Gameplay
A paddle hits a ball upward to break bricks. If the last active ball falls below the paddle, the player loses a life and the round resets. The player has 3 lives total. The goal is to destroy all bricks to win. The game starts on a title screen (SPACE to start), supports pause (P key), and has score-based levels with multiball at higher scores.

Model — BreakoutModel.java
paddle x, y, width, height
ball x, y, xVelocity, yVelocity, size
second ball and third ball for multiball (position, velocity, active flags)
lives (int, starts at 3)
score (int)
highScore (int, persistent from text file)
gameState: TITLE / WAITING / PLAYING / PAUSED / WON / LOST
current level, current ball speed, current paddle speed
bricks in an 8 x 8 grid
each brick has color, destroyed, and point value
rows 1–2 red = 7 points, rows 3–4 orange = 5 points, rows 5–6 green = 3 points, rows 7–8 blue = 1 point
ball rests on paddle before launch
paddle hit changes ball direction: left goes left, middle goes straight up, right goes right
level unlocks multiball and speed increases

View — BreakoutView.java
draws paddle, balls, bricks, and boundaries
draws score, high score, lives, level at the top
draws control hints ("SPACE = Start", "P = Pause", "R = Restart")
draws title screen, pause, win ("YOU WIN"), and game-over ("GAME OVER") messages

Controller — BreakoutController.java
A key moves paddle left
D key moves paddle right
SPACE starts game or launches ball
P toggles pause
R restarts the game
Swing Timer updates the game loop
on each tick: move balls, check collisions, update score, unlock levels/multiball, check win/loss
plays sound effects for brick hits, life loss, game over

"Done" for this week
The paddle moves with A and D. The ball launches with SPACE. Title screen, pause, restart, scoring, lives, level progression, multiball, sound effects, win/lose screens, and persistent high score all work. ModelTester verifies model logic.