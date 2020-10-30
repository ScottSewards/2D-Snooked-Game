
public class Custom {
    public float clamp(float value, float min, float max) { //CLAMP ENFORCES A LIMITED RANGE FOR AN INTEGER
        if (min < max) { //CHECKS IF MIN IS LESS THAN MAX TO RETURN POSITIVE VALUE
            if (value < min) //VALUE CANNOT BE LESS THAN MINIMUM VALUE
                return min; //SET VALUE TO MINIMUM VALUE
            else if (value > max) //VALUE CANNOT BE MORE THAN MAXIMUM VALUE
                return max; //SET VALUE TO MAXIMUM VALUE
            else //VALUE IS BETWEEN MINIMUM VALUE AND MAXIMUM VALUE
                return value; //RETURN CURRENT VALUE
        } else { //CHECKS IF MIN IS GREATER THAN MAX TO RETURN NEGATIVE VALUE
            if (value > min)
                return min;
            else if (value < max)
                return max;
            else
                return value;      
        }
    }
    
    public void print(String text) { //SHORTHAND FOR PRINTING STRINGS
        System.out.println(text);
    }
}



