import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.event.*;
 import javax.swing.border.EmptyBorder;

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
         
        field= new Field();
        field.setPreferredSize(new Dimension(fieldWidth, height));
        field.setBorder(BorderFactory.createLineBorder(Color.black));
        
        controlPanel = new ControlPanel();
        controlPanel.setPreferredSize(new Dimension(controlWidth, height));
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        Container content = myJFrame.getContentPane();
        content.setLayout(new BorderLayout());
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
    
    double cohesionCoefficient = 100.0;
    int alignmentCoefficient = 8;
    double separationCoefficient = 10.0;
    int N = 500;
        
    class Field extends JPanel
    {
        Boids boids;
        
        public Field()
        {
            init(N, fieldWidth, height);
            
            timer = new Timer(30, new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    boids.move(cohesionCoefficient, alignmentCoefficient, separationCoefficient);
                    myJFrame.repaint();
                }
            });
        }
        public void init(int N, int fieldWidth, int height)
        {
            boids = new Boids(N, fieldWidth, height);
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(5));
            g2d.setPaint(Color.black);
            boids.draw(g2d);
        } 
    }
    
    class ControlPanel extends JPanel
    {
        JTextField numberBoids;
        
        public ControlPanel()
        { 
            JButton button = new JButton("Run");
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    field.init(Integer.parseInt(numberBoids.getText()), fieldWidth, height);
                    timer.start();
                    
                }
            });
            
            JSlider cohesionSlider= new JSlider(JSlider.HORIZONTAL,1,100,100); 
            cohesionSlider.addChangeListener(new ChangeListener(){
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider)e.getSource();
                    cohesionCoefficient = (int)source.getValue()*1.0;
                }
            });
            
            JSlider alignmentSlider= new JSlider(JSlider.HORIZONTAL,1,100,8); 
            alignmentSlider.addChangeListener(new ChangeListener(){
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider)e.getSource();
                    alignmentCoefficient = (int)source.getValue();
                }
            });
            
            JSlider separationSlider= new JSlider(JSlider.HORIZONTAL,1,100,10); 
            separationSlider.addChangeListener(new ChangeListener(){
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider)e.getSource();
                    separationCoefficient = (int)source.getValue()*1.0;
                }
            });
            
            
            
            cohesionSlider.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            alignmentSlider.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            separationSlider.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            
            JLabel cohesionLabel = new JLabel("Cohesion");
            cohesionLabel.setLabelFor(cohesionSlider);
            JLabel alignmentLabel = new JLabel("Alignment");
            alignmentLabel.setLabelFor(alignmentSlider);
            JLabel separationLabel = new JLabel("Separation");
            separationLabel.setLabelFor(separationSlider);
            
            JPanel textControlsPane = new JPanel();
            textControlsPane.setPreferredSize(new Dimension(controlWidth-10, height/4));
            textControlsPane.setBorder(BorderFactory.createTitledBorder("Behaviour coefficients"));
            textControlsPane.setLayout(new BoxLayout(textControlsPane, BoxLayout.Y_AXIS));
            
            textControlsPane.add(cohesionLabel);
            textControlsPane.add(cohesionSlider);
            textControlsPane.add(alignmentLabel);
            textControlsPane.add(alignmentSlider);
            textControlsPane.add(separationLabel);
            textControlsPane.add(separationSlider);
                
            add(textControlsPane);
 
            JPanel textParametersPane = new JPanel();
            textParametersPane.setPreferredSize(new Dimension(controlWidth-10, height/4));
            textParametersPane.setBorder(BorderFactory.createTitledBorder("General parameters"));
   
            numberBoids = new JTextField("500", 10);
            numberBoids.setHorizontalAlignment(JTextField.LEFT);
            
            JLabel numberBoidsLabel = new JLabel("Number of b-oid objects:");
            numberBoidsLabel.setLabelFor(numberBoids);
            
            textParametersPane.add(numberBoidsLabel);
            textParametersPane.add(numberBoids);
            
            add(textParametersPane);
                     
            add(button);

            pack();
            
            setVisible(true); 
        }
    }
}