import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import javax.swing.*;

class BoidsVisualisation extends JFrame implements MouseListener
{
    JFrame myJFrame;
    Field field;
    ControlPanel controlPanel;
    Timer timer = null;
    
    int controlWidth, fieldWidth, height;
    
    public static void main(String args[])
    {
        BoidsVisualisation bv = new BoidsVisualisation();
    }
    
    public BoidsVisualisation()
    { 
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        height= gd.getDisplayMode().getHeight();
        
        fieldWidth = (int) Math.round(width * 0.75);
        controlWidth = width - fieldWidth;
        
        System.out.println(fieldWidth +" " +  height);
               
        myJFrame = new JFrame("Boids Classic");
        
        myJFrame.setSize(width, height);
        myJFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Container content = myJFrame.getContentPane();
        
        field= new Field();
        controlPanel = new ControlPanel();
        field.setPreferredSize(new Dimension(fieldWidth, height));
        controlPanel.setPreferredSize(new Dimension(controlWidth, height));
        field.setBorder(BorderFactory.createLineBorder(Color.black));
        
        content.add(controlPanel, BorderLayout.EAST);
        content.add(field, BorderLayout.WEST);
        
        pack();
        
        myJFrame.setVisible(true);
        
        addMouseListener(this);
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
    
    class Field extends JPanel
    {
        Boids boids;
        
        public Field()
        {
            boids = new Boids(500, fieldWidth, height);
            
            
            timer = new Timer(30, new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    boids.move();
                    myJFrame.repaint();
                }
            });
            
            setVisible(true); 
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(5));
            g2d.setPaint(Color.black);
            //for (int i = 0; i < boids.birds.length - 1; i++)   
            //g.drawLine((int)boids.birds[i].position.data[0],(int)boids.birds[i].position.data[1], (int)boids.birds[i].position.data[0], (int)boids.birds[i].position.data[1]); 
            boids.draw(g2d);
        } 
    }
    
    
    class ControlPanel extends JPanel
    {
        public ControlPanel()
        { 
            setBackground(Color.red);

            JButton button = new JButton("Run");
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    timer.start();
                }
            });
            
            add(button, BorderLayout.WEST);
            
            setVisible(true); 
        }
    }
}