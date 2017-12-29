# Multi-Threaded Juggling-Project


JugglingBall class will implement Runnable, and follow these steps:
•	Ball is in the air – sleep a random amount of time between 1000ms and 1500ms
•	Ball enters catchable zone, is there for 150ms
o	Tries to get a hand until you have one, or the ball hits the ground.
•	If the ball hits the ground, end.
•	If the ball is caught, hold it (sleep) for between 100ms and 200ms
•	Go back to ball in air.
Hands should be (2) lock objects (ReentrantLock).
Every ball MUST be assigned a LETTER; Ball A is the first, Ball B the second, etc.
Must write a message to the console when any of the following things happen:
•	A ball is added (which ball?)
•	A ball enters the air (which ball?)
•	A ball enters catchable zone (which ball?)
•	A ball is caught by a hand (which ball? Which hand?)
•	A hand becomes free (which hand?)
•	A ball hits the ground (which ball)
•	successfully told a hand to stop catching balls because a ball hit the ground (which hand)?
