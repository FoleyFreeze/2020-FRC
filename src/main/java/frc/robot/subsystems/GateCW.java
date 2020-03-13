package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.cals.CwTnCals;
import frc.robot.motors.Motor;

public class GateCW extends SubsystemBase{

    public Motor rotateMotor;
    public Motor loadMotor;
    public CwTnCals k;
    public ColorSensorV3 colorSensor;
    public Solenoid CWNotTransport;
    private double targetpos = 0;
    public boolean isIndexing;
    private RobotContainer mSubsystem;
    private RobotState mState;
    public ColorMatch colorMatch;
    public String colorString;
    public Color detectedColor;
    public Color lastColor;
    public String gameData;

    public GateCW(CwTnCals k, RobotContainer subsystem, RobotState state){
        this.k = k;
        mSubsystem = subsystem;
        mState = state;
        if(k.tnDisabled && k.cwDisabled) return;

        rotateMotor = Motor.initMotor(k.rotateMotor);
        loadMotor = Motor.initMotor(k.loadMotor);
        colorSensor = new ColorSensorV3(Port.kOnboard);
        CWNotTransport = new Solenoid(k.CWNotTransport);
        colorMatch = new ColorMatch();

        colorMatch.addColorMatch(k.Blue);
        colorMatch.addColorMatch(k.Green);
        colorMatch.addColorMatch(k.Red);
        colorMatch.addColorMatch(k.Yellow);
    }

    double jamTime;
    boolean prevJammed;
    public void periodic(){
        if(k.tnDisabled && k.cwDisabled) return;
        
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        if(!mSubsystem.m_input.cwActivate()){
            CWNotTransport.set(false);
        }

        rotateMotor.setPosition(targetpos);


        /*if(mSubsystem.m_intake.isOut() && !ballpositions[x%5]){
            gatePower(k.TN_LOADSPEED);
        }else if(mSubsystem.m_intake.isOut()) {
            gatePower(k.TN_STOPSPEED);
        }else*/ if(CWNotTransport.get()){
            if(mSubsystem.m_input.cwRotNotPos()){
                gatePower(k.rotSpeed);
            } else{
                gatePower(k.colSpeed);
            }
        } else gatePower(0.0);

        detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatch.matchClosestColor(detectedColor);
        if(match.color == k.Blue){
            colorString = "Blue";
        } else if(match.color == k.Green){
            colorString = "Green";
        } else if(match.color == k.Red){
            colorString = "Red";
        } else if(match.color == k.Yellow){
            colorString = "Yellow";
        } else colorString = "N/A";

        Display.put("Current Pos", rotateMotor.getPosition() / k.countsPerIndex);
        Display.put("Ball Number", mState.ballnumber);
        Display.put("TCMotorCurrent0", rotateMotor.getCurrent());
        Display.put("TCMotorCurrent1", loadMotor.getCurrent());
        Display.put("TC Motor Temp 0", rotateMotor.getTemp());
        Display.put("TC Motor Temp 1", loadMotor.getTemp());
        Display.put("Color Info", String.format("R: %d G: %d B: %d IR: %d Prox: %d", 
            colorSensor.getRed(), colorSensor.getGreen(), colorSensor.getBlue(), 
            colorSensor.getIR(), colorSensor.getProximity()));
        Display.put("Detected Color", String.format("Color Guess: " + colorString + 
            " Confidence: %f", match.confidence));
        lastColor = detectedColor;
    }

    public void spinGate(double pwr){
        loadMotor.setPower(pwr);
    }

    public boolean isIndexing(){
        if(k.tnDisabled) return false;
        double error = targetpos - rotateMotor.getPosition();
        return Math.abs(error) > k.allowedIndexError;
    }

    double restTime = 0;
    public void gatePower(double power){
        if(restTime > Timer.getFPGATimestamp()){
            loadMotor.setPower(k.TN_STOPSPEED);
        } else {
            loadMotor.setPower(power);
        }

        if(loadMotor.getCurrent() > k.maxGateCurr){
            restTime = Timer.getFPGATimestamp() + k.gateRestTime;
        }
    }

    public void deployCW(boolean activated){
        if(k.cwDisabled) return;
        CWNotTransport.set(activated);
    }
}