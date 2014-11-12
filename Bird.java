import java.util.List;

/**
 * Bird ---   class to represent an bird-like object in 
 *            simulation of the flocking behaviour of birds.
 * @author    fr05td3su
 */
class Bird 
{
    Vector position;
    Vector velocity;
    
    public Bird(Vector position, Vector velocity)
    {
        this.position = position;
        this.velocity = velocity;
    }  
    
    /**
     * Calculate new velocity vector based on current velocity,
     * cohesion, alignment and separation coefficients and bound position. 
     *
     * @param  birds                  list of birds, which positions and velocities are used to calculate 
     *                                corresponding vectors of cohesion, alignment, separation
     *         xMax                   maximum value of x-coordinate of position
     *         yMax                   maximum value of y-coordinate of position
     *         cohesionCoefficient    value affects speed at which bird moves towards the perceived centre of mass
     *                                e.g 100 means that in each iteration bird moves 1% to the perceived centre 
     *         alignmentCoefficient   value affects velocity increase of bird with respect to the perceived centre 
     *                                of mass 
     *         separationCoefficient  if bird is within this distance from other birds, it will move away
     * @return No return value.
     */
    public void updateVelocity(List<Bird> birds, int xMax, int yMax, double cohesionCoefficient, int alignmentCoefficient, double separationCoefficient)
    {
        velocity = velocity.plus(cohesion(birds,  cohesionCoefficient))
                           .plus(alignment(birds, alignmentCoefficient))
                           .plus(separation(birds, separationCoefficient))
                           .plus(boundPosition(xMax, yMax));
        limit_velocity();
    }
    
    /**
     * Update current position using its velocity.
     * @param  No parameters.
     * @return No return value.
     */    
    public void updatePosition()
    {
        position = position.plus(velocity);
    }    
    //rules that determine flock's behaviour
    //are all to apply on bird's velocity
    
    //cohesion - steer towards the center of mass of local flockmates
    public Vector cohesion(List<Bird> birds, double cohesionCoefficient) 
    {    
        Vector pcJ = new Vector(0,0);
        for (Bird b: birds)   
            pcJ = pcJ.plus(b.position);
        pcJ = pcJ.div(birds.size());
        return pcJ.minus(position).div(cohesionCoefficient);
    }  
    
    //alignment - steer towards the average heading of local flockmates
    public Vector alignment(List<Bird> birds, int alignmentCoefficient) 
    {
        Vector pvJ = new Vector(0,0);  
        for (Bird b: birds)  
            pvJ = pvJ.plus(b.velocity);
        pvJ = pvJ.div(birds.size());
        return pvJ.minus(velocity).div(alignmentCoefficient);
    }  
    
    //separation - steer to avoid crowding local flockmates
    public Vector separation(List<Bird> birds, double separationCoefficient) 
    {
        Vector c = new Vector(0,0);
        for (Bird b: birds)  
            if ((b.position.minus(position).magnitude()) < separationCoefficient)
            c = c.minus(b.position.minus(position));
        return c;
    }  
    
    //keep birds within a certain area
    public Vector boundPosition(int xMax, int yMax)
    {
        int x = 0;
        int y = 0;
        if (this.position.data[0] < 0)                x = 10;
        else if (this.position.data[0]  > xMax)       x = -10;
        if (this.position.data[1]  < 0)               y = 10;
        else if (this.position.data[1]  > yMax)       y = -10;
        return new Vector(x,y);
    }
    
    //limit the magnitude of the boids' velocities
    public void limit_velocity()
    {
        int vlim = 100;
        if (this.velocity.magnitude() > vlim)
        {
            this.velocity = this.velocity.div(this.velocity.magnitude());
            this.velocity = this.velocity.times(vlim);
        }
    }
    public String toString()
    {
        return new String("Position: " + this.position + " Velocity: " + this.velocity);
    }
}  