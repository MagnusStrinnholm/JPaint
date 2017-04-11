import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

public class Rak extends JPanel implements Serializable{
                                           	
	private int s1 = 0, s2 = 0, s3 = 0, s4 = 0, senast1, senast2;
	private static int count, count1;

	private boolean bool;
	private static boolean spara;

	public BufferedImage buffer = null;
	private Graphics2D g3, gc;
	public Graphics2D g2;
 
 	public void counting(boolean a){
		bool = a;
}

	public void counting(int a, int b){
		count = a;
		count1 = b;
}

	public Rak(){
    }

	public void paintComponent(Graphics g){
		super.paintComponent(g);
        
		g2 = (Graphics2D)g;
		
		if (buffer == null){
		
			buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			gc = buffer.createGraphics();
			gc.setColor(Color.white);
			gc.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		g2.drawImage(buffer, null, 0, 0);
        
		draw(g2);
    }

	public void draw(Graphics2D g2){
 		
 		if(bool == true){
 			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 		}
 		else{
 			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
 		}
 		
 		g2.setColor(Ritprog.valdknapp.getBackground());
		if(count == 1 && Ritprog.sudd == true){
		g2.setStroke(new BasicStroke(15));
	}
		else{
		g2.setStroke(new BasicStroke(Ritprog.pensel));
	}
        
		switch (count){
            case 1:
            	
            	g2.draw(new Line2D.Double(s1, s2, s3, s4));
            	
					break;
                     
            case 2:
            	g2.draw(new Ellipse2D.Double(s1, s2, s3 - s1, s4 - s2));
					break;
                     
            case 3:
				g2.draw(new Rectangle2D.Double(s1, s2, s3 - s1, s4 - s2));
					break;
        }
    }
    
	public MouseListener mouse = new MouseAdapter(){
		public void mousePressed(MouseEvent e){
			if(SwingUtilities.isLeftMouseButton(e)){
			
			Ritprog.händer(spara = true);
      
			switch(count1){
      			case 1:
      				senast1 = e.getX();
      				senast2 = e.getY();
      					break;
      	
      			case 2:
        			s1 = e.getX();
       				s2 = e.getY();
       				s3   = s1;
        			s4   = s2;
        				break;
		}
	}
}	

	public void mouseReleased(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e)){
        
        switch(count1){
			case 1:
      			senast1 = 0;	
      			senast2 = 0;
      			repaint(); 
       				break;
      
      		case 2:
        		s3 = e.getX();
       			s4 = e.getY();
        	
      			g3 = buffer.createGraphics();
        		draw(g3);
        
        		repaint();
       				break;
       		}
        }
    }
};
   
	public MouseMotionListener	mo = new MouseMotionAdapter(){
		public void mouseDragged(MouseEvent e){
         	if(SwingUtilities.isLeftMouseButton(e)){

         	switch(count1){
      			case 1:
      			s1 = e.getX(); 
      			s2 = e.getY();	
      			s3 = senast1;
      			s4 = senast2;
      	   	 	
      	   		g3 = buffer.createGraphics();
      			draw(g3);
  			
  				senast1 = s1;
  				senast2 = s2;
  			
  				repaint(); 
					break;
      		
      			case 2:
				s3 = e.getX();
       			s4 = e.getY();
       			repaint(); 
      				break;
				}
			}        
		}
	};
}