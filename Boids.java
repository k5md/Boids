import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import java.util.Random;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JButton;

class Boids extends JPanel implements MouseListener 
{
    Timer timer = null;
    JFrame myJFrame;
    int xRes = 1024;
    int yRes = 768;
    int N = 50;
    Bird[] birds = new Bird[N];
    boolean flag = false;                                             
     
    public void init() 
    {
        setBackground(Color.white);
        setForeground(Color.white);
        System.out.println("Initialisation...");
        Random rand = new Random();
        for (int i = 0; i < birds.length - 1; i++)   
            birds[i] = new Bird(new Vector(rand.nextInt(xRes-500),rand.nextInt(yRes-300),0),new Vector(0,0,0)); 
        flag = true;
        System.out.println("Done.");
    }   
    public void paint(Graphics g) 
    {  
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(5));
        g2d.setPaint(Color.black);
        if (flag)
            for (int i = 0; i < birds.length - 1; i++)   
            g2d.drawLine(birds[i].position.x, birds[i].position.y, birds[i].position.x, birds[i].position.y);
    }   
    public void move() 
    {
        for (int i = 0; i < birds.length - 1; i++)  
        {
            birds[i].velocity = (((birds[i].velocity.add(cohesion(birds[i]))).add(alignment(birds[i]))).add(separation(birds[i]))).add(boundPosition(birds[i]));
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
    /*************************************************************************/  
    
    public Boids()
    {
        System.out.println("created new instance of Boids");
        myJFrame = new JFrame("Boids Classic");
        myJFrame.setSize(xRes, yRes);
        JButton button = new JButton("Run");
        myJFrame.add(button, BorderLayout.SOUTH);
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJFrame.getContentPane().add(this);
        myJFrame.setVisible(true);
        init();  
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        this.addMouseListener(this);
        this.timer = new Timer(30, new ActionListener(){     
            public void actionPerformed(ActionEvent e) 
            {
                move();                        
                myJFrame.revalidate();  
                myJFrame.repaint();
            }
        });
    }
    public void mouseClicked(MouseEvent me) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public static void main(String args[]) 
    {
        Boids boids = new Boids();
    } 
}
