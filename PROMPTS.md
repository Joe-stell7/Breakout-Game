Prompt 1 - Interview prompt
Prompt: 
I want to build a Breakout game in Java with Swing using MVC. Before we write any code, interview me. Ask me one question at a time about gameplay, controls, win and loss conditions, and what should be on the screen. After about 8 questions, summarize what I told you as a one-page spec organized by Model, View, and Controller.


What the AI produced:
It asked one question at a time and gathered the rules for my Breakout game before writing the spec.

What I changed and why:
I answered the questions and clarified specific rules like lives, controls, scoring, and the brick layout so the game would be fully defined before coding.


Prompt 2 - Spec dump prompt that started the initial build
Prompt:
I am building Breakout in Java with Swing using MVC. Here is my spec:

Breakout - Spec

Gameplay  
A paddle hits a ball upward to break bricks. If the ball falls below the paddle, the player loses a life and the paddle and ball reset. The player has 3 lives total. The goal is to destroy all bricks to win. There is no start screen. The ball starts on the paddle, and the player presses Space to launch it.

Model - BreakoutModel.java  
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

View - BreakoutView.java  
draws paddle, ball, bricks, and boundaries  
draws score, high score, and lives at the top  
draws “Space = Launch” and “R = Restart”  
draws “YOU WIN” when all bricks are destroyed  
draws “GAME OVER” when all lives are lost

Controller - BreakoutController.java  
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


Prompt 3 - The scoped edit fixing wall collision

Prompt:  
In BreakoutModel.java, update the brick layout values so the 8 x 8 brick grid stretches to the left and right boundary walls. Do not modify BreakoutView.java, BreakoutController.java, or Main.java. Show me only the changed lines — I will paste them in.

What the AI produced:
It returned the changed brick layout lines in BreakoutModel.java so the brick bounds would be created closer to a wall-to-wall layout.

What I changed and why:
I updated the brick width and starting x-position in BreakoutModel.java because that was the file actually creating the brick rectangles, so changing the model fixed both the drawn layout and the collision alignment.


Prompt 4 - The debugging prompt with model error

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


Prompt 5 - Reflection on an AI answer that was incomplete

Prompt:  
Earlier, I used this prompt in BreakoutModel.java:
“In BreakoutModel.java, update the brick layout values so the 8 x 8 brick grid stretches to the left and right boundary walls. Do not modify BreakoutView.java, BreakoutController.java, or Main.java. Show me only the changed lines — I will paste them in.”

What the AI produced:
It returned changed brick layout lines in BreakoutModel.java that adjusted brick width and starting x, but the brick grid still had visible side space and did not stretch fully wall-to-wall.

What I changed and why:
I ran the game, saw that the layout still had unused space on the sides, and realized the first fix was incomplete. I recalculated the brick width and starting x based on the play area width and updated BreakoutModel.java again so the bricks spanned the full width.

What was wrong and how I corrected it:
The AI’s first brick values were close but not exact, so the grid still left gaps on the sides. I corrected this by adjusting the brick dimensions and starting position in the model until the grid reached the boundaries, which fixed both the drawn layout and the collision alignment.

Prompt 6 - The refactor model - The model change for level two and multi-ball

Prompt:
In BreakoutModel.java, refactor the ball state so the model can support two balls at once instead of one. Add a method unlockLevelTwoMultiBall() that activates level two when the score reaches 100 and creates a second ball with its own position and direction. Do not modify BreakoutView.java, BreakoutController.java, or Main.java. Show me only the new and changed lines in BreakoutModel.java — I will paste them in.

What the AI produced:
It returned changes to BreakoutModel.java that replaced the single-ball setup with a structure that could store two balls and their movement values, and it added a method to unlock level two when the score reached 100.

What I changed and why:
I wanted a more noticeable gameplay change, so I used score-based level progression and added a second ball when the player reached level two. I checked the new ball state carefully because this feature required a bigger model change than the earlier scoped edits.

What was added:
This change added a second level and a multi-ball feature, so once the score reached 100 the game became faster and more diffuclt when there was level progression by putting two balls in play at the same time.

Prompt 7 - Title screen, pause, and clean restart cycle

Prompt:
In my Java Swing Breakout game, add a polished game-state flow using my existing MVC structure. Update the code so the game starts on a title screen that shows the name BREAKOUT and the text "Press SPACE to start". Add a pause feature so pressing P pauses the game and pressing P again resumes it. Also add a clean restart cycle so pressing R fully resets the game with no leftover state.

What the AI should produce
It should return updated versions of the files that need state-flow changes, mainly BreakoutModel.java, BreakoutView.java, and BreakoutController.java. The update should introduce TITLE and PAUSED into the game state system, display a centered title screen message in the view, and handle SPACE, P, and R correctly in the controller.

What I changed and why
I wanted the game to feel more polished and complete instead of starting immediately in a gameplay-ready state. Adding a title screen, a pause toggle, and a clean restart flow makes the game feel more like a finished arcade game and also keeps the state transitions cleaner inside the MVC setup.

What was added
This change added a proper title screen, pause and resume controls, and a full restart cycle that resets the game cleanly back to the title screen. It also improved the game-state structure by separating title, waiting, playing, paused, win, and lose states more clearly.

Prompt 8 - Level progression for speed 
Prompt:
In BreakoutModel.java and BreakoutController.java, update the level progression so level two starts when the score reaches 50 instead of 100, and add a level three that begins at score 150. Add model fields for current level, current ball speed, and current paddle speed so movement can scale by level instead of always using the fixed constants. Make both balls always use the same speed values so the second ball does not feel faster than the first one. At level three, increase both the ball speed and the paddle speed. Do not modify Main.java. Show me only the new and changed lines in BreakoutModel.java and BreakoutController.java — I will paste them in.

What the AI produced:
It returned updates that added score-based level tracking, moved level two to start at 50 points, added a third level at 150 points, and introduced current movement-speed variables for the ball and paddle. It also changed the controller so paddle movement and bounce calculations used the new current speed values instead of relying only on the fixed constants.

What I changed and why:
I wanted level progression to happen earlier so the game would feel more active before the player got too far into the round. I also wanted both balls to feel fair during multiball, because the second ball sometimes looked faster than the first one, and the paddle felt too slow once two balls were in play. Adding level-based speed values made the difficulty curve more noticeable and gave me a cleaner way to tune gameplay.

What was added:
This change moved level two to score 50, added a level three at score 150, and increased both ball speed and paddle speed at level three. It also made both balls use the same speed rules so multiball felt more consistent and the paddle could better keep up once the game became faster.

Prompt 9 - Sound Effects in Breakout game

Prompt:
In BreakoutController.java, add sound effects for key game events without changing the gameplay rules. I already created a sounds folder inside my BreakoutGame project with these files: brick.wav, life_lost.wav, and game_over.wav. Add a new SoundManager.java file that can play short WAV files from that sounds folder. Play brick.wav whenever a brick is destroyed, play life_lost.wav whenever the player loses a life, and play game_over.wav when the player reaches 0 lives and the game ends. Keep the code simple and beginner-friendly, keep the MVC structure, and do not modify Main.java or BreakoutView.java unless absolutely necessary. Show me the full new SoundManager.java file and the full updated BreakoutController.java file so I can paste them in.

What the AI produced:
It added a small sound manager class for loading and playing short WAV sound effects and updated the controller so sounds trigger during brick breaks, life loss, and game over. The gameplay logic stayed the same, but the controller now gives audio feedback at the exact moments those events happen.

What I changed and why:
I wanted the game to feel more polished and more like an arcade game without changing how it plays. Adding sound to brick hits, life loss, and game over makes important events easier to notice and gives the game more feedback for the player.

What was added:
This change added a SoundManager.java file and connected three WAV files from the sounds folder to existing game events. The brick hit sound plays when a brick is destroyed, the life-lost sound plays when the last active ball is lost and a life is removed, and the game-over sound plays when the player runs out of lives.


Prompt 10- A test-extension prompt for ModelTester

Prompt:
In ModelTester.java, add three new test methods for important BreakoutModel behavior without changing the existing tests. Use the same check(name, condition) helper. Add tests that verify losing the last active ball decrements lives, reaching the score threshold unlocks the next level or multiball behavior, and resetting the game restores the starting values for score, lives, and game state. Show me the full updated ModelTester.java file so I can paste it in.

What the AI produced:
It added three new test methods to ModelTester.java that checked life loss, score-based level or multiball progression, and reset behavior using the same simple check() pattern as the rest of the tester.

What I changed and why:
I wanted a simple way to test important model rules without needing to launch the whole Swing game every time. Adding these tests made it easier to verify that the core game logic was working correctly after the new features were added.

What was added:
This change added three new tests to ModelTester.java for losing a life, unlocking level progression or multiball from score thresholds, and resetting the game back to its starting values. The tester now checks more of the important BreakoutModel behavior directly from the console.
