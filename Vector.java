public class Vector
{
    public int[] data;
    public int N;
    public Vector(int... args) 
    {
        N = args.length;
        data = new int[N];
        for (int i = 0; i < N; i++)
            data[i] = args[i];
    }
    public Vector div(int that)
    {
        int[] temp = new int[N];
        for (int i=0; i < N; i++)
            temp[i] = data[i] / that;
        return new Vector(temp);
    }   
    public Vector add(Vector that) 
    {
        int[] temp = new int[N];
        for (int i = 0; i < N; i++)
            temp[i] = data[i] + that.data[i];
        return new Vector(temp);
    }
    public Vector sub(Vector that) 
    {
        int[] temp = new int[N];
        for (int i = 0; i < N; i++)
            temp[i] = data[i] - that.data[i];
        return new Vector(temp);
    }
    public Vector abs(Vector that) 
    {
        int[] temp = new int[N];
        for (int i = 0; i < N ; i++)
            temp[i] = Math.abs(data[i] - that.data[i]);
        return new Vector(temp);
    }
    public Vector mult(int that) 
    {
        int[] temp = new int[N];
        for (int i = 0; i < N; i++)
            temp[i] = data[i] * that;
        return new Vector(temp);
    }
    public boolean equ(Vector that) 
    {
        for (int i=0; i < N; i++)
            if (data[i] - that.data[i] != 0) return false;
        return true;
    }
    public double length()
    {
        double temp = 0.0;
        for (int i=0; i < N; i++)
            temp += Math.pow(data[i], 2);
        return Math.sqrt(temp);
    }
    public String toString()
    {
        String temp = "( ";
        for (int i= 0; i < N; i++)
            temp += new String(data[i] + " ");
        return temp + ")";
    }
}