
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class Table extends JPanel implements MouseListener, MouseMotionListener {
    //JCOMPONENTS
    final JFrame frame = new JFrame("Snooker"); //CREATE NEW JFRAME
    Dimension table = new Dimension(1200, 600); //SET TABLE SIZE
    
    //STATES/STATS
    String time = "00:00"; //PLAYTIME TO DISPLAY
    static int fps = 24; //FRAMES PER SECOND
    static int ballsCount = 22; //NUMBER OF BALLS TO CREATE
    static int radius = 24; //BALL RADIUS
    static int pocketSize = 40; //SIZE OF POCKETS
    int score = 0;

    //COMPONENTS
    Ball[] balls; //STORES ALL BALLS
    Draw draw = new Draw(); //TO CALL DRAW GRAPHICS
    
    public Table() { //THIS IS THE CLASS CONSTRUCTOR
        addMouseListener(this); //ADD MOUSE LISTENER TO THIS
        addMouseMotionListener(this); //ADD MOUSE MOTION LISTENER TO THIS
        setBackground(new Color(0, 170, 0)); //SET BACKGROUND TO GREEN
        new Timer(25, (ActionEvent e) -> { //TIMER IS EXECUTED EVERY 0.1 SECONDS
            repaint(); //CALL repaint TO REPAINT GRAPHICS
        }).start(); //CALL start TO START TIMER
        new Timer(1000, (ActionEvent e) -> { //TIMER IS EXECUTED EVERY 1 SECONDS
            updateTimer(); //CALL updateTimer TO COUNT PLAYTIME
        }).start();
    }
    
    public void initiate(Table program) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //SET frame TO CLOSE WHEN CLOSE BUTTON IS CLICKED            
        frame.setSize((int)table.width, (int)table.height); //SET frame SIZE
        frame.setResizable(false); //STOP frame RESIZING
        frame.getContentPane().add(program); //ADD TABLE TO CONTENT PANEL
        frame.setVisible(true); //SET WINDOW AS VISIBLE
        startMatch(); //CALL startMatch TO CREATE BALLS
    }
    
    public static void main(String[] args) {
        Table program = new Table(); //CREATE AN INSTANCE OF THIS CLASS
        program.initiate(program); //INITIATE GUI AND GAME
    }
                
    public void paintComponent(Graphics og) { //THIS METHOD PAINT GRAPHICS
        super.paintComponent(og); 
        Graphics2D g = (Graphics2D)og; //CONVERT Graphics TO Graphics2D VIA CASTING
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //APPLY ANTI-ALIASING
        g.setRenderingHints(hints);
        draw.drawTable(g, frame, pocketSize); 
                
        for (int i = 0; i < ballsCount; i++) { //LOOP THROUGH BALLS
            try { 
                draw.drawBall(g, balls[i]); //DRAW EACH BALL BY PASSING THE GRAPHIC AND BALL TO DRAW           
            } catch (Exception e) { //CATCH ERROR BEFORE ANY BALLS ARE ADDED TO BALLS
                new Custom().print(e + "");
            }
        }

        g.setColor(Color.white); //SET COLOUR OF STRINGS
        g.drawString("Time : " + time, 15, 25); //DRAW TIMER
        g.drawString("Score : " + score, 15, 50); //DRAW SCORE
        
        if (isPressing && isPressingBall) //DRAW CUE IF PLAYER HAS PRESSED MOUSE BUTTON OVER THE WHITE BALL AND IS HOLDING THE MOUST BUTTON
            draw.drawLine(g, balls[0].position, mousePosition); //DRAW CUE
        
        checkCollisions(); //CHECK FOR COLLISIONS
    }

    Vector2 mousePosition; //STORE MOUSE POSITION
    LinkedList<Vector2> pressed = new LinkedList<Vector2>(); //STORE ALL MOUSE PRESSED POSITIONS
    LinkedList<Vector2> released = new LinkedList<Vector2>(); //STORE ALL MOUSE RELEASED POSITIONS
    boolean isPressing = false; //STATE IF MOUSE IS PRESSED
    boolean isPressingBall = false; //STATE IS MOUSE IS PRESSED WHITE BALL
    boolean areBallsMoving = false; //STATE IS ANY BALLS ARE MOVING
    
    public void mousePressed(MouseEvent e) { //MOUSE BUTTON IS PRESSED DOWN
        if (areBallsMoving) //IF BALLS ARE MOVING
            return; //RETURN TO STOP INPUT WHILE BALLS ARE MOVING
        
        isPressing = true; //MOUSE PRESSED IS TRUE WHEN THIS METHOD IS CALLED
        pressed.add(new Vector2(e.getX(), e.getY())); //ADD MOUSE PRESSED TO pressed

        if (pressed.getLast().distance(balls[0].position) < (radius / 2)) //IF DISTANCE BETWEEN MOUSE AND WHITE BALL IS WITHIN RADIUS 
            isPressingBall = true; //MOUSE PRESSED WHITE BALL IS TRUE
    }

    public void mouseClicked(MouseEvent e) { //CALLED WHEN MOUSE BUTTON IS RELEASED
        //new Custom().print("Clicked");
    }

    public void mouseReleased(MouseEvent e) { //CALLED WHEN MOUSE BUTTON IS RELEASED
        if (areBallsMoving) //IF BALLS ARE MOVING
            return; //RETURN TO STOP INPUT WHILE BALLS ARE MOVING
        
        if (isPressingBall) //IF MOUSE PRESSED WHITE BALL WHEN MOUSE BUTTON RELEASED
            balls[0].velocity.set( //SET BALL velocity BY DISTANCE BETWEEN WHITE BALL AND MOUSE POSITION
                (balls[0].position.x - mousePosition.x) / 10,
                (balls[0].position.y - mousePosition.y) / 10            
            );

        isPressing = false; //RESET MOUSE PRESSED TO FALSE
        isPressingBall = false; //RESET MOUSE PRESSED WHITE BALL TO FALSE
    }

    public void mouseEntered(MouseEvent e) { //CALLED WHEN MOUSE ENTERS WINDOW
        //new Custom().print("Entered");
    }

    public void mouseExited(MouseEvent e) { //CALLED WHEN MOUSE EXITS WINDOW
        //new Custom().print("Exited");
    }
    
    public void mouseDragged(MouseEvent e) { //CALLED WHEN MOUSE DRAGGED (MOUSE BUTTON HELD)
        mousePosition = new Vector2(e.getX(), e.getY()); //GET CURRENT MOUSE POSITION
    }
    
    public void mouseMoved(MouseEvent e) { //CALLED WHEN MOUSE DRAGGED (MOUSE BUTTON NOT HELD)
        mousePosition = new Vector2(e.getX(), e.getY()); //GET CURRENT MOUSE POSITION
    }
    
    public void checkCollisions() { //THIS METHOD CHECKS IF ANY BALLS COLLIDE WITH ANOTHER BALL OR A WALL
        for (int i = 0; i < balls.length; i++) //LOOP THROUGH BALLS
            balls[i].move(); //CALL MOVE IN BALL CLASS TO MOVE BALL

        for (int i = 0; i < balls.length; i++) { //LOOP THROUGH ALL BALLS
            for(int j = i + 1; j < balls.length; j++) //LOOP THROUGH ALL OTHER BALLS
                balls[i].collide(balls[j]);            
            
            if (balls[i].position.x - balls[i].radius < 0) { //IF COLLIDE WITH LEFT WALL
                balls[i].position.x = balls[i].radius; //PLACE BALL ON WALL
                balls[i].velocity.x = -(balls[i].velocity.x); //INVERT VELOCITY ON PARRALEL AXIS OF WALL
            } else if (balls[i].position.x + balls[i].radius > getWidth()) { //IF COLLIDE WITH RIGHT WALL
                balls[i].position.x = getWidth() - balls[i].radius;
                balls[i].velocity.x = -(balls[i].velocity.x); 
            } else if (balls[i].position.y - balls[i].radius < 0) { //IF COLLIDE WITH TOP WALL
                balls[i].position.y = balls[i].radius;
                balls[i].velocity.y = -(balls[i].velocity.y); 
            } else if (balls[i].position.y + balls[i].radius > getHeight()) { //IF COLLIDE WITH BOTTOM WALL
                balls[i].position.y = getHeight() - balls[i].radius;
                balls[i].velocity.y = -(balls[i].velocity.y);
            }
	}
        
        areBallsMoving = checkBallsMoving(); //CHECK IF BALLS ARE MOVING AFTER EVERY COLLISION
    }
    
    public boolean checkBallsMoving() { //THIS METHOD CHECKS IF ANY BALL IS MOVING
        for (int i = 0; i < balls.length; i++) { //LOOP THROUGH ALL BALLS
            if (balls[i].velocity.x < -Ball.minimumVelocity) //IF velocity IS GREATER THAN minimumVelocity
                return true; //THEN BALL IS MOVING SO RETURN TRUE
            else if (balls[i].velocity.x > Ball.minimumVelocity)
                return true;
            else if (balls[i].velocity.y < -Ball.minimumVelocity)
                return true;
            else if (balls[i].velocity.y > Ball.minimumVelocity)
                return true;
        }
        
        return false; //RETURN FALSE BECAISE NO BALLS ARE MOVING
    }
    
    public boolean checkPot() {
        //FOR EACH BALL COMPARE TO 
        /*
        for(int i = 0; i < pockets.length; i++) {
            for(int j = 0; j < balls.length; i++) {
                if(pockets[i].) {
                    //DISTANCE BETWEEN BALLS IS RADIUS OF BALL
                }
            }
        }
        */

        return false;
    }
    
    public void startMatch() { //THIS METHOD SPAWNS ALL BALLS
        this.balls = new Ball[ballsCount]; //ASSIGN NEW ARRAY OF BALLS WITH A LENGTH EQUAL TO BALL COUNT
        
        for (int i = 0; i < ballsCount; i++) { //LOOP THROUGH ALL BALLS
            int value = i < 8 ? i : 1; //SET VALUE FROM 1-7 DEPENDING ON INDEX
            balls[i] = new Ball(spawnBall(value), value); //ADD BALL TO BALLS
        }
        //MANUALLY SET POSITION OF EACH RED BALL
        balls[1].position = new Vector2(table.width / 4 - radius, table.height / 2);
        balls[8].position = new Vector2(table.width / 4 - (radius * 3), table.height / 2);
        balls[9].position = new Vector2(table.width / 4 - (radius * 5), table.height / 2);
        balls[10].position = new Vector2(table.width / 4 - (radius * 2), table.height / 2 - (radius / 2));
        balls[11].position = new Vector2(table.width / 4 - (radius * 2), table.height / 2 + (radius / 2));
        balls[12].position = new Vector2(table.width / 4 - (radius * 3), table.height / 2 - radius);
        balls[13].position = new Vector2(table.width / 4 - (radius * 3), table.height / 2 + radius);
        balls[14].position = new Vector2(table.width / 4 - (radius * 4), table.height / 2 - (radius / 2));
        balls[15].position = new Vector2(table.width / 4 - (radius * 4), table.height / 2 + (radius / 2));
        balls[16].position = new Vector2(table.width / 4 - (radius * 4), table.height / 2 - (radius / 2 * 3));
        balls[17].position = new Vector2(table.width / 4 - (radius * 4), table.height / 2 + (radius / 2 * 3));
        balls[18].position = new Vector2(table.width / 4 - (radius * 5), table.height / 2 - radius);
        balls[19].position = new Vector2(table.width / 4 - (radius * 5), table.height / 2 + radius);
        balls[20].position = new Vector2(table.width / 4 - (radius * 5), table.height / 2 - (radius * 2));
        balls[21].position = new Vector2(table.width / 4 - (radius * 5), table.height / 2 + (radius * 2));
    }
    
    private Vector2 spawnBall(int value) { //THIS METHOD SETS THE POSITION OF BALLS OTHER THAN RED
        switch (value) { //SELECT BALL BASED ON VALUE
            case 0: return new Vector2(table.width / 24 * 19 + 10, table.height / 8 * 3 + 20); //WHITE
            case 2: return new Vector2(table.width / 24 * 19 + 10, table.height / 3); //YELLOW
            case 3: return new Vector2(table.width / 24 * 19 + 10, table.height / 3 * 2); //GREEN
            case 4: return new Vector2(table.width / 24 * 19 + 10, table.height / 6 * 3); //BROWN
            case 5: return new Vector2(table.width / 2, table.height / 2); //BLUE
            case 6: return new Vector2(table.width / 4, table.height / 2); //PINK
            case 7: return new Vector2(table.width / 11, table.height / 2); //BLACK
            default: return new Vector2(0, 0); //DEFAULT POSITION
        }
    }
    
    private void updateTimer() { //THIS METHOD INCREMENTS time
        String[] splitTime = time.split(":"); //time IS SPLIT USING : DELIMITER
        int tempMinutes = Integer.valueOf(splitTime[0]); //FIRST INDEX OF SPLIT REPRESENTS MINUTES
        int tempSeconds = Integer.valueOf(splitTime[1]); //SECOND INDEX OF SPLIT REPRESENTS SECONDS
        
        if (tempSeconds >= 59) { //CHECK IF A MINUTE HAS PASSED BY DETERMINING IF tempSeconds HAS EXCEEDED 59 SECONDS
            tempMinutes++; //INCREMENT tempMinutes
            tempSeconds = 0; //RESET SECONDS TO 0
        } else //IF tempSeconds HAS NOT EXCEEDED 59 SECONDS
            tempSeconds++; //INCREMENT tempSeconds
        
        String[] newTime = { //CREATE A NEW STRING TO COMBINE time AGAIN 
            tempMinutes < 10 ? "0" + tempMinutes : Integer.toString(tempMinutes),
            tempSeconds < 10 ? "0" + tempSeconds : Integer.toString(tempSeconds) 
        };
        
        time = newTime[0] + ":" + newTime[1]; //CONCANTENATE ARRAY INTO STRING
    }    
}