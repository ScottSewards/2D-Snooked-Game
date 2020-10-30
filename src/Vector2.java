
public class Vector2 {
    float x = 0;
    float y = 0;

    public Vector2() { //THIS CONSTRUCTOR IS THE DEFAULT
        this.x = 0;
        this.y = 0;
    }
    
    public Vector2(float x, float y) { //THIS CONSTRUCTOR SETS THE VECTOR VALUE
        set(x, y);
    }
    
    public void set(float x, float y) { //THIS METHOD SETS THE VALUE OF X AND Y
        this.x = x;
        this.y = y;
    }
 
    public Vector2 add(Vector2 value) { //THIS METHOD ADDS A VECTOR TO ANOTHER
        return new Vector2(this.x + value.x, this.y + value.y); //CALCULATE AND RETURN ADDITION
    }

    public Vector2 subtract(Vector2 value) { //THIS METHOD SUBTRACTS A VECTOR FROM ANOTHER
        return new Vector2(this.x - value.x, this.y - value.y); //CALCULATE AND RETURN SUBTRACTION
    }

    public Vector2 multiply(float value) { //THIS METHOD MULTIPLIES A VECTOR BY ANOTHER
        return new Vector2(this.x * value, this.y * value); //CALCULATE AND RETURN MULTIPLICATION
    }
    
    public int direction(Vector2 value) { //THIS METHOD GETS THE DIRECTION BETWEEN TWO VECTORS
        return (int)-Math.toDegrees(Math.atan2(value.x - this.x, value.y - this.y)) + 180; //CALCULATE AND RETURN DIRECTION      
    }
    
    public int distance(Vector2 value) {  //THIS METHOD GETS THE DISTANCE BETWEEN TWO VECTORS
        return (int)Math.hypot(this.x - value.x, this.y - value.y); //CALCULATE AND RETURN DISTANCE
    }

    public float dotProduct(Vector2 value) { //THIS METHOD GETS THE DOT PRODUCT USING TWO VECTORS
        return this.x * value.x + this.y * value.y; //CALCULATE AND RETURN DOT PRODUCT
    }
    
    public float length() { //THIS METHOD GETS THE LENGTH OF THIS VECTOR
        return (float)Math.sqrt(x * x + y * y); //CALCULATE AND RETURN LENGTH
    }

    public Vector2 normalize() { //THIS METHOD NORMALIZES THIS VECTOR
        float length = length(); //CALL LENGTH TO GET LENGTH OF THIS VECTOR

        if (length != 0.0f) { //IF LENGTH IS NOT EQUAL TO ZERO
            this.x = this.x / length; //CALCULATE AND ASSIGN NORMALIZED X
            this.y = this.y / length; //CALCULATE AND ASSIGN NORMALIZED Y
        } else { 
            this.x = 0; //SET X TO ZERO
            this.y = 0; //SET Y TO ZERO
        }

        return new Vector2(x, y); //RETURN X AND Y AS A VECTOR
    }
}

