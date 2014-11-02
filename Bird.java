class Bird
{
    Vector position;
    Vector velocity;
    public Bird(Vector position, Vector velocity)
    {
        this.position = position;
        this.velocity = velocity;
    }
    public String toString()
    {
        return new String("Position: " + this.position + " Velocity: " + this.velocity);
    }
}  