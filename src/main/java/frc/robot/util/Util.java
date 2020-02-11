package frc.robot.util;

public class Util{
    public static double interpolate(double[] table, double[] axis, double value){
        if(value <= axis[0]) return table[0];
        if(value >= axis[axis.length - 1]) return table[table.length - 1];

        int i = axis.length-1;
        while(axis[i--] > value);

        double slope = (table[i+1]-table[i])/(axis[i+1]-axis[i]);
        double x = value - axis[i];
        return slope*x + table[i];
    }
}