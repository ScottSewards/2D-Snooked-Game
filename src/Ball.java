
import java.awt.*;

public class Ball {
    Vector2 position = new Vector2(0, 0); 
    Vector2 velocity = new Vector2(0, 0);
    int radius = 12; //RADIUS OF BALL
    int value = 1; //NUMBER OF POINTS FOR POCKETING THIS BALL
    Color colour; //COLOUR OF BALL BASED ON value
    static float mass = 1; //MASS OF BALL
    
    public Ball(Vector2 position, int value) { //CONSTRUCTOR
        this.position = position; //SET STARTING POSITION
        this.value = value; //SET VALUE
        this.colour = setColour(); //SET COLOUR BASED ON value
    }    

    private Color setColour() { //THIS METHOD SETS THE BALL COLOUR RELATIVE TO VALUE
        switch (this.value) { //CHECK VALUE OF BALL
            case 0: return new Color(255, 255, 255); //RETURN WHITE
            case 1: return new Color(255, 0, 0); //RETURN RED
            case 2: return new Color(255, 255, 0); //RETURN YELLOW
            case 3: return new Color(0, 192, 0); //RETURN GREEN
            case 4: return new Color(153, 102, 51); //RETURN BROWN
            case 5: return new Color(0, 0, 255); //RETURN BLUE
            case 6: return new Color(251, 95, 135); //RETURN PINK
            case 7: return new Color(0, 0, 0); //RETURN BLACK
            default: return null;
        }
    }
    
    static float minimumVelocity = 0.1f; //MINIMUM VELOCITY FOR THE BALL
    static float slowdown = 0.98f; //VALUE TO SLOWDOWN velocity BY
    static float maximumVelocity = 50; //MAXIMUM VELOCITY THE BALL CAN HAVE

    public void move() { //THIS CLASS APPLIES MOVEMENT TO THE BALL
        if (velocity.x < -minimumVelocity || velocity.x > minimumVelocity) { //IF velocity IS GREATER THAN minimumVelocity
            if (velocity.x > 0) { //IF velocity IS POSITIVE
                velocity.x = new Custom().clamp(velocity.x, 0, maximumVelocity); //CLAMP velocity BETWEEN minimumVelocity and maximumVelocity
                velocity.x *= slowdown; //MULTIPLE velocity BY LESS THAN 1 TO DECREASE velocity EVERY CALL
            }
            else if (velocity.x < 0) { //IF velocity IS NEGATIVE
                velocity.x = new Custom().clamp(velocity.x, 0, -maximumVelocity);            
                velocity.x *= slowdown;
            }
            position.x += velocity.x; //MOVE BALL BY ADDING velocity
        }
        if (velocity.y < -minimumVelocity || velocity.y > minimumVelocity) { //SIMILAR TO THE ABOVE IF CONDITION BUT APPLIES TO THE Y-AXIS
            if (velocity.y > 0) {
                velocity.y = new Custom().clamp(velocity.y, 0, maximumVelocity);
                velocity.y *= slowdown;
            }
            else if (velocity.y < 0) {
                velocity.y = new Custom().clamp(velocity.y, 0, -maximumVelocity);            
                velocity.y *= slowdown;
            }
            position.y += velocity.y;
        }
    }
    
    public boolean collide(Ball other) { //THIS CLASS CHECK IF BALLS COLLIDE
        Vector2 delta = (position.subtract(other.position)); //GET CHANGE OF POSITION

        if (delta.dotProduct(delta) > Math.pow(this.radius * 2, 2)) //CHECK IF BALLS ARE NOT COLLIDING BY GETTING DOT PRODUCT OF DELTA
            return false; //RETURN TO STOP EXECUTION OF PROCEEDING CODE
        
        float deltaLength = delta.length(); //GET LENGTH OF DELTA
        Vector2 minimumTranslationDistance = delta.multiply(((this.radius * 2) - deltaLength) / deltaLength); //MTD TO DEFLECT BALLS
        position = position.add(minimumTranslationDistance.multiply(0.5f)); //PUSH THIS BALL AWAY FROM OTHER BALL
        other.position = other.position.subtract(minimumTranslationDistance.multiply(0.5f)); //PUSH OTHER BALL AWAY FROM THIS BALL
        float impactSpeed = this.velocity.subtract(other.velocity).dotProduct(minimumTranslationDistance.normalize()); //GET IMPACT SPEED BY NORMALISING DOT PRODUCT OF VELOCITY SUBTRACTED BY OTHER VELOCITY

        if (impactSpeed > 0.0f) //CHECK IF ARE ALREADY BALLS MOVING AWAY
            return true; //RETURN TO STOP EXECUTION OF PROCEEDING CODE

        Vector2 impulse = minimumTranslationDistance.multiply((-1.85f * impactSpeed) / (mass * 2)); //SET HOW BALLS REACT TO COLLISION
        this.velocity = this.velocity.add(impulse.multiply(mass)); //SWITCH MOMENTUM OF THIS BALL
        other.velocity = other.velocity.subtract(impulse.multiply(mass)); //SWITCH MOMENTUM OF OTHER BALL
        return true; //RETURN TRUE BECAUSE BALL HAS COLLIDED WITH OTHER
    }
}




        
