package frc.util;

public class Vector{
    public double r;
    public double theta;

    public Vector(double r, double theta){
        this.r = r;
        this.theta = theta;
    }

    public Vector(Vector vec){
        this.r = vec.r;
        this.theta = vec.theta;
    }

    public static Vector fromXY(double x, double y){
        double r = Math.sqrt(x*x + y*y);
        double theta = Math.atan2(y, x);
        return new Vector(r, theta);
    }

    public void scaleNorm(){
        double h = 1/Math.cos(theta);
        r = r/h;
    }

    public void threshNorm(){
        if(r > 1) r = 1;
        else if(r < -1) r = -1;
    }

    public Vector inverse(){
        r = -r;
        return this;
    }

    public Vector add(Vector v){
        r = Math.sqrt(r*r + v.r*v.r + 2*r*v.r * Math.cos(v.theta - theta));
        theta = theta + Math.atan2(v.r*Math.sin(v.theta - theta), 
            r + v.r*Math.cos(v.theta - theta));

        return this;
    }

    public static Vector add(Vector v1, Vector v2){
        Vector output = new Vector(v1);
        return output.add(v2);
    }
}