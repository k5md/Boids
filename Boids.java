import java.util.Random;
import java.awt.Graphics;

class Boids 
{
    int N;
    Bird[] birds; 
    int xRes;
    int yRes;
     
    public Boids(int N, int xRes, int yRes)
    {
        this.N = N;
        this.xRes = xRes;
        this.yRes = yRes;
        birds = new Bird[N];
        System.out.println("Initializing positions of " + N + " boids");
        Random rand = new Random();
        
        for (int i = 0; i < birds.length - 1; i++)   
        {
            birds[i] = new Bird(new Vector(rand.nextInt(xRes),rand.nextInt(yRes),0),new Vector(0,0,0)); 
            System.out.println(birds[i]);
        }
        System.out.println("Done.");
    }
    public void draw(Graphics g)
    {
        for (int i = 0; i < birds.length - 1; i++)   
            g.drawLine(birds[i].position.x, birds[i].position.y, birds[i].position.x, birds[i].position.y);
    }
    public void move() 
    {
        for (int i = 0; i < birds.length - 1; i++)  
        {
            birds[i].velocity = birds[i].velocity.add(cohesion(birds[i])).add(alignment(birds[i])).add(separation(birds[i])).add(boundPosition(birds[i]));
            int vlim = 100;
            if (birds[i].velocity.length() > vlim)
                birds[i].velocity = (birds[i].velocity.div((int)birds[i].velocity.length())).mult(vlim);
            birds[i].position = birds[i].position.add(birds[i].velocity);
        }
    }  
    public void limit_velocity(Bird b)
    {
        int vlim = 100;
        if (b.velocity.length() > vlim)
            b.velocity = (b.velocity.div((int)b.velocity.length())).mult(vlim);
    }
   /*************************************************************************
    *  Rules that determinine flock behaviour.
    *************************************************************************/  
    public Vector cohesion(Bird bj) 
    {
        Vector pcJ = new Vector(0,0,0);
        for (int i = 0; i < birds.length - 1; i++)   
            if (!birds[i].equals(bj)) 
            pcJ = pcJ.add(birds[i].position);
        pcJ = pcJ.div(N-1);
        return (pcJ.sub(bj.position)).div(100);
    }  
    public Vector alignment(Bird bj) 
    {
        Vector pvJ = new Vector(0,0,0);   
        for (int i = 0; i < birds.length - 1; i++)  
            if (!birds[i].equals(bj)) 
            pvJ = pvJ.add(birds[i].velocity);
        pvJ = pvJ.div(N-1);
        return pvJ.sub(bj.velocity).div(8);
    }  
    public Vector separation(Bird bj) 
    {
        Vector c = new Vector(0,0,0);
        for (int i = 0; i < birds.length - 1; i++)  
            if (!birds[i].equals(bj) && ((birds[i].position.sub(bj.position)).length() < 10))
            c = c.sub(birds[i].position).add(bj.position);
        return c;
    }  
    public Vector boundPosition(Bird b)
    {
        int Xmin = 0; 
        int Xmax = xRes;
        int Ymin = 0; 
        int Ymax = yRes; 
        Vector v = new Vector(0,0,0);
        if (b.position.x < Xmin)
            v.x = 10;
        else if (b.position.x > Xmax) 
            v.x = -10;
        if (b.position.y < Ymin) 
            v.y = 10;
        else if (b.position.y > Ymax) 
            v.y = -10;
        return v;
    }
}
