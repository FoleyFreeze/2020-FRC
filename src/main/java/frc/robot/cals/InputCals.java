package frc.robot.cals;

public class InputCals extends CalSet{

    //fly sky buttons and axes
    public final int FS_FINTAKE = 1;
    public final int FS_RINTAKE = 2;
    public final int FS_FIELDORIENT = 3;
    public final int FS_ANGRESET = 4;

    public final int FS_XAXIS = 0;
    public final int FS_YAXIS = 1;
    public final int FS_ROTAXIS = 4;

    public final double FS_LOWDEADBND = 0.05;
    public final double FS_HIGHDEADBND = 1.0;
    public final double FS_EXPONENT = 1.0;



    //xbox buttons and axes
    public final int XB_FINTAKE = 1;
    public final int XB_RINTAKE = 2;
    public final int XB_FIELDORIENT = 3;
    public final int XB_ANGRESET = 4;

    public final int XB_XAXIS = 0;
    public final int XB_YAXIS = 1;
    public final int XB_ROTAXIS = 4;

    public final double XB_LOWDEADBND = 0.1;
    public final double XB_HIGHDEADBND = 0.9;
    public final double XB_EXPONENT = 1.0;



    //driver station buttons and switches
    public final int DS_CLIMBUP = -1;
    public final int DS_CLIMBDN = -1;
    public final int DS_ENABLEBUDCLIMB = -1;

    public InputCals(){

    }
}