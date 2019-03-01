# Robot-Maze-Navigation
This project involves working in a team of 2 to build an autonomous Robot that can navigate a maze towards an unknown but marked goal, and “learns” the maze to find the optimal way to go back to the starting point.

[**See full documentation.**](https://github.com/hu-zhiling-540/Robot-Maze-Navigation/blob/master/FinalProjectDesignDoc.pdf)

## Introduction:	The	Maze	Navigation	Problem	
The	problem	to	be	solved	is	**a	maze	navigation	problem**.
The only information we	are given	here:
- a	maze
- a	starting	point (on	the	maze)
- a	final	destination	point	

Our job was to come	up	with	a	strategy,	and	implement	the	corresponding	"algorithm",	so	that the robot	will	go	from	the	starting	point	to	the	goal/destination and	then	
make	its	way	directly	back	to	the	start	without	making	a	wrong	turn.

The implementation of our project is heavily influenced by the robotic architecture called **Subsumption architecture**, which the control is divided into layers corresponding to levels of behavior. 
The idea of subsumption is that not only do more complex layers depend on lower, more reactive levels, 
but that they could also influencetheir behavior. 
Within subsumption architecture, the controlling structure is an
arbitrator. The arbitrator looks through a list of behaviors, and depending on the
current conditions, will fire off a certain behavior.

## High Level Design Overview
![high_level](https://github.com/hu-zhiling-540/Robot-Maze-Navigation/blob/master/highLevelDesign.jpg)

## Low Level Design Overview

### Robot
- Sensors
  - TouchSensor
  - LightSensor
  - Ultrasonic Sensor

### Behaviors
- Explore Class
  - defines and initiates the default action to perform in different cases when walking through the maze
- Avoid Class
  - takes Touch Sensor and Ultrasonic Sensor
  - will be called on once an obstacle is detected
- ReachGoal Class
  - takes Light Sensor
  - will be called on once it gets light reflected from the white(goal) cell
  - will walk back to the starting cell with the optimal solution, and play a song

### Others
- Cell Class
  - holds information for coordinates regarding its location in the maze
- World Class
  - holds the grid and knowledge built from beginning or gained during exploration
  - and DFS algorithm for generating an optimal path
  
