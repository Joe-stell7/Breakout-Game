Breakout — Spec
Gameplay
A paddle hits a ball upward to break bricks. If the ball falls below the paddle, the player loses a life and the paddle and ball reset. The player has 3 lives total. The goal is to destroy all bricks to win. There is no start screen. The ball starts on the paddle, and the player presses Space to launch it.



Model — BreakoutModel.java
paddle x, y, width, height

ball x, y, xVelocity, yVelocity, size

lives (int, starts at 3)

score (int)

highScore (int)

gameState: WAITING / PLAYING / WON / LOST

bricks in an 8 x 8 grid

each brick has color, destroyed, and point value

rows 1–2 red = 7 points, rows 3–4 orange = 5 points, rows 5–6 green = 3 points, rows 7–8 blue = 1 point

ball rests on paddle before launch

paddle hit changes ball direction: left goes left, middle goes straight up, right goes right



View — BreakoutView.java
draws paddle, ball, bricks, and boundaries

draws score, high score, and lives at the top

draws “Space = Launch” and “R = Restart”

draws “YOU WIN” when all bricks are destroyed

draws “GAME OVER” when all lives are lost



Controller — BreakoutController.java
A key moves paddle left

D key moves paddle right

Space launches ball

R restarts the game

Swing Timer updates the game loop

on each tick: move ball, check collisions, update score, check win/loss

"Done" for this week
The paddle moves with A and D. The ball launches with Space. The player has 3 lives. Bricks break and give points based on color. Win and lose screens appear. R restarts the game.