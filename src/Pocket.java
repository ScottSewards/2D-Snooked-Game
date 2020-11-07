
import java.awt.*;

public class Pocket {
    Vector2 position = new Vector2(0, 0);
    int radius = 12; //RADIUS OF POCKET

    public Pocket(Vector2 position) { //CONSTRUCTOR
        this.position = position; //SET POSITION
    }    

    private Color setColour() { //THIS METHOD SETS THE POCKET COLOUR
        return new Color(0, 0, 0);
    }

    public boolean pot(Ball other) { //THIS CLASS CHECK IF BALLS COLLIDE
        return false;
        /*
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
        */
    }
}



        
