# Software Performance Tasks 2026

## 1 - Write a brief summary of the expectations for the Software Lead throughout the season
This task is meant to assess your ability to describe your role.
Please write a 1-2 paragraph description of your role as it relates to your personal responsibilities,
responsibilities towards your subteam, and responsibilities to the whole team. Details are encouraged, as
well as specific examples that you have performed/observed. Discuss the season as a whole, not just one
segment.
This should be saved as README.md in the repository from the second part of this performance task.

## 2 - Write code for a pick-and-place hypothetical robot

This task is meant to assess your ability to figure out things you may not have done before while
on a time crunch, like in the regular season.
Details about the robot:

- Elevator with grabber on a pivot

- Max height of elevator is 4.5 meters, and is powered by two Kraken X60s

- Pivot is attached to elevator and has π radians of motion, from -π/2 to π/2, and is
powered by one Kraken X44

- Grabber is a set of rollers, and is powered by two Kraken X44s, one on each side of the
grabber

### Details about the states the robot needs to reach:
- L1 scoring at 1 meter of elevator height, pivot at position π/6, and grabber running each
side at one third speed in opposite directions
- L2 scoring at 2 meters of elevator height, pivot at position -π/6, and grabber running
each side forward at one third speed
- L3 scoring at 3 meters of elevator height, pivot at position -π/6, and grabber running
each side forward at one third speed
- L4 scoring at 4 meters of elevator height, pivot at position -π/3, and grabber running
each side forward at one third speed
- Human player intake at 1.5 meters of elevator height, pivot at position π/3, grabber
running backward at half speed until a current spike of greater than 10 amps is detected
for more than 20 ms
- A throw where the elevator goes up from less than one meter of height to maximum
extension with the pivot at 2π/3 radians and then shooting with the grabber (full speed
forward) when the speed of the elevator is at least 2 m/s and the height is at least 3.5 m

### Details about the format of your code:
- Should use AdvantageKit
- Should have IO layers, subsystems, a Superstructure, and RobotContainer
- Should not be written by AI
- Should not use YAMS

### Things that are optional:
- Drivetrain code
- Simulations

### Submission Details:
- Code should be made in a new WPILib project
- Should be pushed to a public repository on your personal GitHub account named
2026-[Your Name]-Performance-Task
- Will be graded based upon your last commit to the main branch made before Friday at
11:59 PM