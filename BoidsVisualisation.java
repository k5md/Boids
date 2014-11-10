import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.Date;


class BoidsVisualisation extends JPanel implements MouseListener 
{
    Boids boids;
    Timer timer = null;  
    JFrame myJFrame = new JFrame("Boids Classic");
    JButton button = new JButton("Run");
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    
    public BoidsVisualisation()
    {
        boids = new Boids(500, width, height);
        myJFrame.setSize(width, height);
        myJFrame.add(button, BorderLayout.SOUTH);
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJFrame.getContentPane().add(this);
        myJFrame.setVisible(true);
        setBackground(Color.white);
        setForeground(Color.white);  
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        this.addMouseListener(this);
        timer = new Timer(30, new ActionListener(){     
            public void actionPerformed(ActionEvent e) 
            {
                boids.move(); 
                myJFrame.revalidate();  
                myJFrame.repaint();
            }
        });
    }
    
    
    public void paint(Graphics g) 
    {  
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));
        g2d.setPaint(Color.black);
        
        
        //for (int i = 0; i < birds.length - 1; i++)   
            //g.drawLine((int)birds[i].position.data[0],(int)birds[i].position.data[1], (int)birds[i].position.data[0], (int)birds[i].position.data[1]);   
        boids.draw(g2d);
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
    
    private long getTimeMillis() 
    {
        Date d = new Date();
        return d.getTime();
    }
    public static void main(String args[]) 
    {
        BoidsVisualisation bv = new BoidsVisualisation();
    } 
}