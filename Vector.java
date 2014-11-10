public class Vector { 

    private int N;              
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

    public double dot(Vector that) {
        if (this.N != that.N) throw new IllegalArgumentException("Dimensions don't agree");
        double sum = 0.0;
        for (int i = 0; i < N; i++)
            sum = sum + (this.data[i] * that.data[i]);
        return sum;
    }

    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    public double distanceTo(Vector that) {
        if (this.N != that.N) throw new IllegalArgumentException("Dimensions don't agree");
        return this.minus(that).magnitude();
    }

    public Vector plus(Vector that) {
        if (this.N != that.N) throw new IllegalArgumentException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] + that.data[i];
        return c;
    }

    public Vector minus(Vector that) {
        if (this.N != that.N) throw new IllegalArgumentException("Dimensions don't agree");
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = this.data[i] - that.data[i];
        return c;
    }

    public Vector times(double factor) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = factor * data[i];
        return c;
    }
    
    public Vector div(double factor) {
        Vector c = new Vector(N);
        for (int i = 0; i < N; i++)
            c.data[i] = data[i] / factor;
        return c;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < N; i++)
            s = s + data[i] + " ";
        return s;
    }
}