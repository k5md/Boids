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
    public void move() 
    {
        /*
        int[] ridx = new int[birds.length];
        for (int i = 0; i < birds.length - 1; i++)
            ridx[i] = i;
        shuffle(ridx);
        */
        for (int i = 0; i < birds.length - 1; i++)  
        {
            try
            {
                //double[] coords = new double[]{birds[i].position.cartesian(0), birds[i].position.cartesian(1)};
                double[] coords = birds[i].position.data;
                List<Bird> nbrs = kd.nearest(coords, 50);
                kd.delete(coords);
                birds[i].velocity(nbrs, xRes, yRes);
                birds[i].position();
                kd.insert(birds[i].position.data, birds[i]);
            }
            catch (Exception e)
            {
                System.out.println("Exception caught: " + e);   
            }         
        }
        kd = new KDTree(2);
        for (int i = 0; i < birds.length - 1; i++)  
        {
            try
            {
                kd.insert(birds[i].position.data, birds[i]);
            }
            catch (Exception e)
            {
                System.out.println("Exception caught: " + e);   
            }  
        }  
    }
    public void draw(Graphics g)
    {
            for (int i = 0; i < birds.length - 1; i++)   
            g.drawLine((int)birds[i].position.data[0],(int)birds[i].position.data[1], (int)birds[i].position.data[0], (int)birds[i].position.data[1]);   
    }
    
    private long getTimeMillis() {
        Date d = new Date();
        return d.getTime();
    }
    public final void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N - i));
            int swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }
}
