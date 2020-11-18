package boids;
/**
 * Vector --- class to represent an N-dimentional vector.
 */
public class Vector { 
    int N;              
    double[] data;       

    public Vector(int d) {
        N = d;
        data = new double[N];
    }
      
    public Vector(double... a) {
        N = a.length;
        data = new double[N];
        for (int i = 0; i < N; i++)
            data[i] = a[i];
    }

    /**
     * Returns dot product of two vectors
     * @param  that        Second vector to find dot product.
     * @return Double value representing dot product of this and that .
     */    
    public double dot(Vector that) {
        double sum = 0.0;
        for (int i = 0; i < N; i++)
            sum += this.data[i] * that.data[i];
        return sum;
    }

    /**
     * Returns vector magnitude (the distance vector and the origin)
     * @param  No parameters.
     * @return Double value |this|.
     */    
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    /**
     * Returns Euclidean distance between two vectors.
     * @param  that       Vector to find the distance to.
     * @return Double value d(this, that).
     */    
    public double distanceTo(Vector that) {
        return this.minus(that).magnitude();
    }

    /**
     * Returns result of vector addition.
     * @param  that       Vector to add to initial vector.
     * @return New vector representing this + that
     */ 
    public Vector plus(Vector that) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] + that.data[i];
        return c;
    }

    /**
     * Returns result of vector subtraction.
     * @param  that       Vector to subtract from initial vector.
     * @return New vector representing this - that
     */   
    public Vector minus(Vector that) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] - that.data[i];
        return c;
    }

    /**
     * Returns result of vector multiplication by a scalar
     * @param  factor           Value to multiply vector by.
     * @return New vector representing this * value
     */    
    public Vector times(double factor) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = factor * data[i];
        return c;
    }
    
    /**
     * Returns result of vector division by a scalar
     * @param  factor           Value to divide vector by
     * @return New vector representing this / value
     */    
    public Vector div(double factor) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = data[i] / factor;
        return c;
    }

    /**
     * Returns string representation of the vector
     */    
    public String toString() {
        String s = "";
        for (int i = 0; i < N; i++)
            s += data[i] + " ";
        return s;
    }
}