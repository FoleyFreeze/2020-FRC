package frc.robot.cals;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;

public class CwTnCals extends CalSet {

    //Color Wheel
    public boolean cwDisabled = true;
    public double rotSpeed = 0.75;
    public double colSpeed = 0.5;
    public double cwInitSpeed = 0.2;
    public double stopBuffer = 0.5;
    public final Color Red = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public final Color Blue = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public final Color Green = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public final Color Yellow = ColorMatch.makeColor(0.361, 0.524, 0.113);

    //Transporter
    public boolean tnDisabled = true;
    public MotorCal rotateMotor = MotorCal.spark(12).pid(0.001, 0, 0, 0).limit(0.1).ramp(0.3).brake();
    public MotorCal loadMotor = MotorCal.srx(-1); //this is the gate wheels and the CW motor
    public int CWNotTransport = -1;
    public int sensorValue = -1;
    public int launcherValue = -1;
    public double countsPerIndex = 24/40.0 * 52/36.0 * 64/20.0 * 64/13.0;
    public double allowedIndexError = 0.2 * countsPerIndex;
    public final double TN_LOADSPEED = 0.2;
    public final double TN_STOPSPEED = -0.0;
    public double jamRestTime;
    public double ballSenseDelay;
    public double maxGateCurr;
    public double gateRestTime;
    public double hasBallMinV;
    public double hasBallMaxV;

    public CwTnCals(){

        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:

            break;

            case LASTYEAR:

            break;
        }
    }
}