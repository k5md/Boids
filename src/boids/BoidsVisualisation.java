package boids;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class BoidsVisualisation extends JFrame implements MouseListener {
    JFrame myJFrame;
    Field field;
    ControlPanel controlPanel;
    Timer timer = null;
    
    int controlWidth, fieldWidth, height;
    
    public static void main(String args[]) {
        BoidsVisualisation bv = new BoidsVisualisation();
    }
    
    public BoidsVisualisation() { 
        //Determine width and height of frame, field and control panel
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        height= gd.getDisplayMode().getHeight();
        fieldWidth = (int) Math.round(width * 0.75);
        controlWidth = width - fieldWidth;
               
        //create frame
        myJFrame = new JFrame("Boids Classic");
        myJFrame.setSize(width, height);
        myJFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //create field (simulation goes there)
        field= new Field();
        field.setPreferredSize(new Dimension(fieldWidth, height));
        field.setBorder(BorderFactory.createLineBorder(Color.black));
        
        //create control panel for parameters of simulation
        controlPanel = new ControlPanel();
        controlPanel.setPreferredSize(new Dimension(controlWidth, height));
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        //add components to frame 
        Container content = myJFrame.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(controlPanel, BorderLayout.EAST);
        content.add(field, BorderLayout.WEST);
        
        pack();
        
        myJFrame.setVisible(true);
        
        addMouseListener(this);
    }
    
    public void mouseClicked(MouseEvent me) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
    double cohesionCoefficient = 100.0;
    int alignmentCoefficient = 8;
    double separationCoefficient = 10.0;
    int N = 500;                                 //number of boids to simulate
    int distance = 50;                           //amount of neighbours to search for in kd-tree
    
    /**
     * Field --- implements visualisation of Boids.
     */
    class Field extends JPanel {
        Boids boids;
        
        public Field() {
            init(N, fieldWidth, height);
            timer = new Timer(30, new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    boids.move(distance, cohesionCoefficient, alignmentCoefficient, separationCoefficient);
                    myJFrame.repaint();
                }
            });
        }
        
        public void init(int N, int fieldWidth, int height) {
            boids = new Boids(N, fieldWidth, height);
        }
        
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(5));
            g2d.setPaint(Color.black);
            boids.draw(g2d);
        } 
    }
    
    /**
     * ControlPanel --- creates and registers GUI-control components.
     */
    class ControlPanel extends JPanel {
        JTextField numberBoids;
        JTextField fov;
        
        public ControlPanel() { 
            // Buttons  
            JButton startButton = new JButton("Start");
            startButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if (field.boids == null) {
                        N = Integer.parseInt(numberBoids.getText());
                        distance = Integer.parseInt(fov.getText());
                        if (N > 0 && distance - N <= 0)
                            field.init(Integer.parseInt(numberBoids.getText()), fieldWidth, height);
                    }
                    myJFrame.repaint();
                    field.repaint();
                    timer.start();   
                }
            }); 
            JButton stopButton = new JButton("Stop");
            stopButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    timer.stop();
                }
            });
            JButton initButton = new JButton("Initialize");
            initButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    N = Integer.parseInt(numberBoids.getText());
                    distance = Integer.parseInt(fov.getText());
                    if (N > 0 && distance - N <= 0)
                    {
                        timer.stop();
                        field.init(N, fieldWidth, height);
                        myJFrame.repaint();
                        field.repaint();  
                    }
                }
            });
            
            //Sliders
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
            
            //Labels
            JLabel cohesionLabel = new JLabel("Cohesion");
            cohesionLabel.setLabelFor(cohesionSlider);
            JLabel alignmentLabel = new JLabel("Alignment");
            alignmentLabel.setLabelFor(alignmentSlider);
            JLabel separationLabel = new JLabel("Separation");
            separationLabel.setLabelFor(separationSlider);
            
            //Behaviour coefficients panel
            JPanel textControlsPane = new JPanel();
            textControlsPane.setPreferredSize(new Dimension(controlWidth-10, height/4));
            textControlsPane.setBorder(BorderFactory.createTitledBorder("Behaviour coefficients"));
            textControlsPane.setLayout(new BoxLayout(textControlsPane, BoxLayout.Y_AXIS));
            
            //Add components to behaviour coefficients' panel
            textControlsPane.add(cohesionLabel);
            textControlsPane.add(cohesionSlider);
            textControlsPane.add(alignmentLabel);
            textControlsPane.add(alignmentSlider);
            textControlsPane.add(separationLabel);
            textControlsPane.add(separationSlider);
                
            add(textControlsPane);
 
            //General parameters(e.g. number of boids, of neighbours to search) panel
            JPanel textParametersPane = new JPanel();
            textParametersPane.setPreferredSize(new Dimension(controlWidth-10, height/4));
            textParametersPane.setBorder(BorderFactory.createTitledBorder("General parameters"));
   
            //Text field for N with label
            numberBoids = new JTextField("500", 10);
            numberBoids.setHorizontalAlignment(JTextField.LEFT);
            JLabel numberBoidsLabel = new JLabel("Number of boid objects:");
            numberBoidsLabel.setLabelFor(numberBoids);
            
            //Text field for amount of neighbours to search for (FOV) with label
            fov = new JTextField("50", 10);
            fov.setHorizontalAlignment(JTextField.LEFT);
            JLabel fovLabel = new JLabel("Number of neighbours to search for:");
            fovLabel.setLabelFor(fov);
            
            //Add components to general parameters' panel
            textParametersPane.add(numberBoidsLabel);
            textParametersPane.add(numberBoids);
            textParametersPane.add(fovLabel);
            textParametersPane.add(fov);
            
            add(textParametersPane);
                     
            add(startButton);
            add(stopButton);
            add(initButton);

            pack();
            
            setVisible(true); 
        }
    }
}