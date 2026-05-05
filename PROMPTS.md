Prompt 1 — Interview prompt
Prompt: 
I want to build a Breakout game in Java with Swing using MVC. Before we write any code, interview me. Ask me one question at a time about gameplay, controls, win and loss conditions, and what should be on the screen. After about 8 questions, summarize what I told you as a one-page spec organized by Model, View, and Controller.


What the AI produced:
It asked one question at a time and gathered the rules for my Breakout game before writing the spec.

What I changed and why:
I answered the questions and clarified specific rules like lives, controls, scoring, and the brick layout so the game would be fully defined before coding.


Prompt 2 — Spec dump prompt that started the initial build
Prompt:
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


What the AI produced:
It generated the starter MVC file shells for Main.java, BreakoutModel.java, BreakoutView.java, and BreakoutController.java based on the spec.

What I changed and why:
I kept the project as a 4-file MVC structure because that matched my assignment better than adding extra object classes.


Prompt 3 — The scoped edit fixing wall collision

Prompt:  
In BreakoutModel.java, update the brick layout values so the 8 x 8 brick grid stretches to the left and right boundary walls. Do not modify BreakoutView.java, BreakoutController.java, or Main.java. Show me only the changed lines — I will paste them in.

What the AI produced:
It returned the changed brick layout lines in BreakoutModel.java so the brick bounds would be created closer to a wall-to-wall layout.

What I changed and why:
I updated the brick width and starting x-position in BreakoutModel.java because that was the file actually creating the brick rectangles, so changing the model fixed both the drawn layout and the collision alignment.


Prompt 4 — The debugging prompt with model error

Prompt:  
I ran my Breakout game and the brick layout still looked wrong after I updated BreakoutModel.java. The bricks were not reaching the side boundaries the way I expected.

Here is the part of BreakoutModel.java I changed:
-BRICK_WIDTH
-startX in initializeBricks()

Please explain why the layout still has side space and what values I should change to make the 8 x 8 brick grid stretch closer to the left and right walls. Show me only the corrected code for that part.


What the AI produced:
It explained that the brick width, gap, and starting x-position together determine the total row width, and it gave corrected layout values for the brick initialization code in BreakoutModel.java.

What I changed and why:
I updated the brick width and starting x-position again because the first layout still left unused space on the sides, and I needed the brick grid to span the play area more closely.


Prompt 5 — Reflection on an AI answer that was incomplete

Prompt:  
Earlier, I used this prompt in BreakoutModel.java:
“In BreakoutModel.java, update the brick layout values so the 8 x 8 brick grid stretches to the left and right boundary walls. Do not modify BreakoutView.java, BreakoutController.java, or Main.java. Show me only the changed lines — I will paste them in.”

What the AI produced:
It returned changed brick layout lines in BreakoutModel.java that adjusted brick width and starting x, but the brick grid still had visible side space and did not stretch fully wall-to-wall.

What I changed and why:
I ran the game, saw that the layout still had unused space on the sides, and realized the first fix was incomplete. I recalculated the brick width and starting x based on the play area width and updated BreakoutModel.java again so the bricks spanned the full width.

What was wrong and how I corrected it:
The AI’s first brick values were close but not exact, so the grid still left gaps on the sides. I corrected this by adjusting the brick dimensions and starting position in the model until the grid reached the boundaries, which fixed both the drawn layout and the collision alignment.

Prompt 6 The refactor model — The model change for level two and multi-ball

Prompt:
In BreakoutModel.java, refactor the ball state so the model can support two balls at once instead of one. Add a method unlockLevelTwoMultiBall() that activates level two when the score reaches 100 and creates a second ball with its own position and direction. Do not modify BreakoutView.java, BreakoutController.java, or Main.java. Show me only the new and changed lines in BreakoutModel.java — I will paste them in.

What the AI produced:
It returned changes to BreakoutModel.java that replaced the single-ball setup with a structure that could store two balls and their movement values, and it added a method to unlock level two when the score reached 100.

What I changed and why:
I wanted a more noticeable gameplay change, so I used score-based level progression and added a second ball when the player reached level two. I checked the new ball state carefully because this feature required a bigger model change than the earlier scoped edits.

What was added:
This change added a second level and a multi-ball feature, so once the score reached 100 the game became faster and more diffuclt when there was level progression by putting two balls in play at the same time.

Prompt 7- 

































Prompt 10- A test-extension prompt for ModelTester
