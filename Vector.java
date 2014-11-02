public class Vector
{
    public int x = 0;
    public int y = 0;
    public int z = 0;
    
    public Vector(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;   
    }
    public Vector div(int that)
    {
        return new Vector(this.x / that, 
                          this.y / that, 
                          this.z / that);
    }   
    public Vector add(Vector that) 
    {
        return new Vector(this.x + that.x, 
                          this.y + that.y, 
                          this.z + that.z);
    }
    public Vector sub(Vector that) 
    {
        return new Vector(this.x - that.x, 
                          this.y - that.y, 
                          this.z - that.z);
    }
    public Vector abs(Vector that) 
    {
        return new Vector(Math.abs(this.x - that.x), 
                          Math.abs(this.y - that.y), 
                          Math.abs(this.z - that.z));
    }
        public Vector mult(int that) 
    {
        return new Vector(this.x * that, 
                          this.y * that, 
                          this.z * that);
    }
    public boolean equ(Vector that) 
    {
        if (this.x - that.x != 0) return false; 
        if (this.y - that.y != 0) return false; 
        if (this.z - that.z != 0) return false; 
        return true;
    }
    public double length()
    {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }
    public String toString()
    {
        return new String("(" + this.x + "," + this.y + "," + this.z + ")");
    }
}