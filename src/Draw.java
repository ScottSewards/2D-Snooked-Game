
import java.awt.*;
import javax.swing.*;

public class Draw {
    public void drawBall(Graphics2D g, Ball ball) { //THIS METHOD DRAWS A BALL
        int radius = ball.radius; //GET BALL RADIUS
        g.setColor(ball.colour); //SET COLOUR FOR GRAPHIC FROM BALL COLOUR
        g.fillOval((int)(ball.position.x - radius), (int)(ball.position.y - radius), (int)(2 * radius), (int)(2 * radius)); //FILL BALL
        g.setStroke(new BasicStroke(2)); //SET THICKNESS OF OUTLINE
        g.setColor(Color.black); //SET COLOUR OF OUTLINE TO BLACK
        g.drawOval((int)(ball.position.x - radius), (int)(ball.position.y - radius), (int)(2 * radius), (int)(2 * radius)); //DRAW OUTLINE
    }
    
    public void drawLine(Graphics2D g, Vector2 from, Vector2 to) { //THIS METHOD DRAWS THE CUE
        g.setColor(Color.black); //SET COLOUR OF OUTLINE
        g.setStroke(new BasicStroke(4)); //SET THICKNESS OF OUTLINE
        g.drawLine((int)from.x, (int)from.y, (int)to.x, (int)to.y); //DRAW OUTLINE
        g.setColor(Color.white); //SET COLOUR OF CUE
        g.setStroke(new BasicStroke(2)); //SET THICKNESS OF CUE
        g.drawLine((int)from.x, (int)from.y, (int)to.x, (int)to.y); //DRAW CUE        
    }
    
    public void drawTable(Graphics2D g, JFrame frame, int pocket) { //THIS METHOD DRAWS THE TABLE
        g.setColor(Color.white); //SET COLOUR OF LINES
        g.setStroke(new BasicStroke(2)); //SET THICKNESS OF LINES
        g.drawLine((int)frame.getWidth() / 5 * 4, 0, (int)frame.getWidth() / 5 * 4, (int)frame.getHeight()); //DRAW LINE
        g.drawArc((int)frame.getWidth() / 5 * 4 - ((int)frame.getHeight() / 3 / 2), (int)frame.getHeight() / 3, (int)frame.getHeight() / 3, (int)frame.getHeight() / 3, -90, 180); //DRAW SEMI-CIRCLE
        g.setColor(Color.black); //SET COLOUR OF POCKETS
        g.fillOval(0, 0, pocket, pocket); //DRAW TOP-LEFT POCKET
        g.fillOval((frame.getWidth() / 2) - (pocket / 2), 0, pocket, pocket); //DRAW TOP-MIDDLE POCKET
        g.fillOval(1200 - 46, 0, pocket, pocket); //DRAW TOP-RIGHT POCKET
        g.fillOval(0, 600 - 70, pocket, pocket); //DRAW BOTTOM-LEFT POCKET
        g.fillOval((frame.getWidth() / 2) - (pocket / 2), 600 - 70, pocket, pocket); //DRAW BOTTOM-MIDDLE POCKET
        g.fillOval(1200 - 46, 600 - 70, pocket, pocket); //DRAW BOTTOM-RIGHT POCKET
    }
}



