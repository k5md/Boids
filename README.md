Boids Classic
====================

What is it?
---------------------
This is an implementation of boids algorithm, used to simulate the flocking behaviour of birds. 
>As with most artificial life simulations, Boids is an example of emergent behavior; 
>that is, the complexity of Boids arises from the interaction of individual agents (the boids, in this case)
>adhering to a set of simple rules. The rules applied in the simplest Boids world are as follows:
>
>   - separation: steer to avoid crowding local flockmates
>   - alignment: steer towards the average heading of local flockmates
>   - cohesion: steer to move toward the average position (center of mass) of local flockmates

You can find more  information on Wikipedia: http://en.wikipedia.org/wiki/Boids

This implementation almost directly follows pseudocode on www.kfish.org/boids/pseudocode.html , but provides neat GUI 
to increase flexibility - in real-time one can change coefficients, affecting boids behaviour. The use of Kd-tree 
structure made it possible to support real-time simulation of numerous boids.

Contents
---------------------
The project includes following files:
- Bird.java - implements Bird class, which incorporates vectors of position and velocity, methods to simulate flocking behaviour. 
- Boids.java - represents flock of birds, contains methods to update each bird's position and velocity depending on it's neighbours.
- BoidsVisualisation.java - provides basic but flexible GUI.
- Vector.java - class to represent an N-dimentional vector.

How to run
---------------------
Simply compile project files with java compiler (with kd.jar included), then run BoidsVisualisation.
