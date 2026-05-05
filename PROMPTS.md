Prompt 1 — Interview prompt
Prompt:
"I want to build a Breakout game in Java with Swing using MVC. Before we write any code, interview me. Ask me one question at a time about gameplay, controls, win and loss conditions, and what should be on the screen. After about 8 questions, summarize what I told you as a one-page spec organized by Model, View, and Controller."

What the AI produced:
It asked one question at a time and gathered the rules for my Breakout game.

What I changed and why:
I answered the questions and clarified specific rules like lives, controls, scoring, and the brick layout so the game would be fully defined before coding.


Prompt 2 — Spec‑dump prompt that started the initial build 

I am building Breakout in Java with Swing using MVC. Here is my spec:

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

Generate four class shells — Main.java, BreakoutModel.java, BreakoutView.java, and BreakoutController.java — with method stubs based on this design. BreakoutModel.java must not import any Swing classes. The program should compile and open a blank window.