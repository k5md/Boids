import java.util.List;
import java.util.Iterator;

class Bird 
{
    Vector position;
    Vector velocity;
    
    public Bird(Vector position, Vector velocity)
    {
        this.position = position;
        this.velocity = velocity;
    }  
    public void velocity(List<Bird> birds, int xMax, int yMax)
    {
        velocity = velocity.plus(cohesion(birds, 100.0)).plus(alignment(birds, 8)).plus(separation(birds, 10.0)).plus(boundPosition(xMax, yMax));
        limit_velocity();
    }
    public void position()
    {
        position = position.plus(velocity);
    }  
    public Vector cohesion(List<Bird> birds, double cohesionCoefficient) 
    {    
        Vector pcJ = new Vector(0,0);
        for (Bird b: birds)   
            pcJ = pcJ.plus(b.position);
        pcJ = pcJ.div(birds.size());
        return pcJ.minus(position).div(cohesionCoefficient);
    }  
    public Vector alignment(List<Bird> birds, int alignmentCoefficient) 
    {
        Vector pvJ = new Vector(0,0);  
        for (Bird b: birds)  
            pvJ = pvJ.plus(b.velocity);
        pvJ = pvJ.div(birds.size());
        return pvJ.minus(velocity).div(alignmentCoefficient);
    }  
    public Vector separation(List<Bird> birds, double separationCoefficient) 
    {
        Vector c = new Vector(0,0);
        for (Bird b: birds)  
            if ((b.position.minus(position).magnitude()) < separationCoefficient)
            c = c.minus(b.position.minus(position));
        return c;
    }  
    public Vector boundPosition(int xMax, int yMax)
    {
        int x = 0;
        int y = 0;
        if (this.position.data[0] < 0)
            x = 10;
        else if (this.position.data[0]  > xMax) 
            x = -10;
        if (this.position.data[1]  < 0) 
            y = 10;
        else if (this.position.data[1]  > yMax) 
            y = -10;
        return new Vector(x,y);
    }
    public void limit_velocity()
    {
        int vlim = 100;
        if (this.velocity.magnitude() > vlim)
        {
            this.velocity = this.velocity.div((int)this.velocity.magnitude());
            this.velocity = this.velocity.times(vlim);
        }
    }
    public String toString()
    {
        return new String("Position: " + this.position + " Velocity: " + this.velocity);
    }
}  