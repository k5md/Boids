import java.util.Random;
import java.awt.Graphics;
import java.util.List;
import java.util.Iterator;
import java.util.Date;
import java.util.concurrent.atomic.*;
import edu.wlu.cs.levy.CG.KDTree;
class Boids 
{
    KDTree kd = new KDTree(2);
    int N;
    Bird[] birds; 
    int fullness = 0; //determines rate at which KD-tree is rebuild 
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
        long temp = getTimeMillis();
        for (int i = 0; i < birds.length - 1; i++)   
        {
            birds[i] = new Bird(new Vector(rand.nextInt(xRes),rand.nextInt(yRes)),new Vector(0,0)); 
            try
            {
            kd.insert(birds[i].position.data, birds[i]);
            }
            catch (Exception e)
            {
             System.out.println("Exception caught: " + e);   
            }
        }
        System.out.println("Done in: " + (getTimeMillis() - temp) +"ms.");
    }    
    public void move(int distance, double cohesionCoefficient, int alignmentCoefficient, double separationCoefficient) 
    {
        //long temp = getTimeMillis();
        
        for (int i = 0; i < birds.length - 1; i++)  
        {
            try
            {
                double[] coords = birds[i].position.data;
                List<Bird> nbrs = kd.nearest(coords, distance);
                kd.delete(coords);
                birds[i].velocity(nbrs, xRes, yRes, cohesionCoefficient, alignmentCoefficient, separationCoefficient);
                birds[i].position();
                kd.insert(birds[i].position.data, birds[i]);
                fullness++;
            }
            catch (Exception e) {
                System.out.println("Exception caught: " + e);   
            }         
        }
        if (fullness > 3)
        {
            kd = new KDTree(2);
            for (int i = 0; i < birds.length - 1; i++)  
            {
                try{
                    kd.insert(birds[i].position.data, birds[i]);
                } catch (Exception e) {
                    System.out.println("Exception caught: " + e);   
                }  
            } 
            fullness = 0;
        }
        
        //System.out.println(" Move in: " + (getTimeMillis() - temp) +"ms.");
    }
    public void draw(Graphics g)
    {
            for (int i = 0; i < birds.length - 1; i++)   
            g.drawLine((int)birds[i].position.data[0],(int)birds[i].position.data[1], (int)birds[i].position.data[0], (int)birds[i].position.data[1]);   
    }
    
    public long getTimeMillis() {
        Date d = new Date();
        return d.getTime();
    }
}
