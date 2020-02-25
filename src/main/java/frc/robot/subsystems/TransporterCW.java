package frc.robot.subsystems;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.cals.CWheelCals;
import frc.robot.cals.TransporterCals;
import frc.robot.motors.Motor;

public class TransporterCW extends SubsystemBase{

    public Motor rotateMotor;
    public Motor loadMotor;
    public TransporterCals tCals;
    public CWheelCals cCals;
    public DigitalInput ballSensor;
    public ColorSensorV3 colorSensor;
    public Solenoid launcher;
    public Solenoid CWNotTransport;
    private double targetpos = 0;
    private double timeAtIdx;
    private boolean[] ballpositions = {false, false, false, false, false};
    public boolean isIndexing;
    public int ballnumber = 0;
    private RobotContainer mSubsystem;
    public ColorMatch colorMatch;
    public String colorString;
    public Color detectedColor;
    public Color lastColor;
    public String gameData;

    public TransporterCW(TransporterCals tCals, CWheelCals cCals, RobotContainer subsystem){
        this.tCals = tCals;
        this.cCals = cCals;
        mSubsystem = subsystem;
        if(tCals.disabled && cCals.disabled) return;

        rotateMotor = Motor.initMotor(tCals.rotateMotor);
        loadMotor = Motor.initMotor(tCals.loadMotor);
        ballSensor = new DigitalInput(tCals.sensorValue);
        colorSensor = new ColorSensorV3(Port.kOnboard);
        launcher = new Solenoid(tCals.launcherValue);
        CWNotTransport = new Solenoid(tCals.CWNotTransport);
        colorMatch = new ColorMatch();

        colorMatch.addColorMatch(cCals.Blue);
        colorMatch.addColorMatch(cCals.Green);
        colorMatch.addColorMatch(cCals.Red);
        colorMatch.addColorMatch(cCals.Yellow);
    }

    public void periodic(){
        if(tCals.disabled && cCals.disabled) return;
        
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        if(!mSubsystem.m_input.cwActivate()){
            CWNotTransport.set(false);
        }
        rotateMotor.setPosition(targetpos);
        int x = (int) Math.round(rotateMotor.getPosition() / tCals.countsPerIndex);
        if(ballpositions[(x + 3) % 5]){
            ballnumber--;
            ballpositions[x % 5] = false;
        }
        if(mSubsystem.m_input.autoGather() && !ballpositions[x%5]){
            loadMotor.setPower(tCals.TN_LOADSPEED);
        }else if(mSubsystem.m_input.autoGather()) {
            loadMotor.setPower(tCals.TN_STOPSPEED);
        }else if(CWNotTransport.get()){
            if(mSubsystem.m_input.cwRotNotPos()){
                loadMotor.setPower(cCals.rotSpeed);
            } else{
                loadMotor.setPower(cCals.colSpeed);
            }
        } else loadMotor.setPower(0.0);

        detectedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatch.matchClosestColor(detectedColor);
        if(match.color == cCals.Blue){
            colorString = "Blue";
        } else if(match.color == cCals.Green){
            colorString = "Green";
        } else if(match.color == cCals.Red){
            colorString = "Red";
        } else if(match.color == cCals.Yellow){
            colorString = "Yellow";
        } else colorString = "N/A";

        Display.put("Ball Number", ballnumber);
        Display.put("TCMotorCurrent0", rotateMotor.getCurrent());
        Display.put("TCMotorCurrent1", loadMotor.getCurrent());
        Display.put("TC Motor Temp 0", rotateMotor.getTemp());
        Display.put("TC Motor Temp 1", loadMotor.getTemp());
        Display.put("Color Info", String.format("R: %f G: %f B: %f IR: %f Prox: %f", 
            colorSensor.getRed(), colorSensor.getGreen(), colorSensor.getBlue(), 
            colorSensor.getIR(), colorSensor.getProximity()));
        Display.put("Detected Color", String.format("Color Guess: " + colorString + 
            " Confidence: %f", match.confidence));
        lastColor = detectedColor;
        isIndexing = Timer.getFPGATimestamp() >= timeAtIdx + tCals.idxFinTime;
    }

    //increment the ball storage
    public void index(double positions){
        targetpos += positions * tCals.countsPerIndex;
        timeAtIdx = Timer.getFPGATimestamp();
    }

    //used for gathering
    public void gatherIndex(){
        double error = targetpos - rotateMotor.getPosition();
        if(ballSensor.get() && error < tCals.countsPerIndex / 2 && ballnumber < 5){ //only spin if not moving & we have an open spot
            ballnumber++;
            int x = (int) Math.round(targetpos/tCals.countsPerIndex);
            ballpositions[x % 5] = true;
            index(1);
        }
    }

    public void shootAll(){
        enablefire(ballnumber > 0);
        double error = targetpos - rotateMotor.getPosition();
        if(error < tCals.countsPerIndex/2 && ballnumber > 0){
            index(1);
        }
    }

    //stop shooting
    public void stoprot(){
        enablefire(false);
        double x = rotateMotor.getPosition() / tCals.countsPerIndex;
        x = Math.round(x);
        targetpos = x * tCals.countsPerIndex;
    }

    public void enablefire(boolean on){
        launcher.set(on);
    }

    public void deploy(boolean activated){
        CWNotTransport.set(activated);
    }
}