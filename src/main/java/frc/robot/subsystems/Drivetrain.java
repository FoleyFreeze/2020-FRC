package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.cals.DriverCals;
import frc.robot.motors.Motor;
import frc.robot.util.DistanceSensors;
import frc.robot.util.Vector;
import frc.robot.util.DistanceSensors.DistData;

import com.kauailabs.navx.frc.AHRS;

public class Drivetrain extends SubsystemBase{
    public class Wheel{
        int idx;
        Motor driveMotor;
        Motor turnMotor;
        AnalogInput enc;
        Vector location;
        Vector rotVec;
        Vector wheelVec;
        double angleOffset;
        
        //create a wheel object to make assigning motor values easier
        public Wheel(DriverCals cals, int idx){
            driveMotor = Motor.initMotor(cals.driveMotors[idx]);
            turnMotor = Motor.initMotor(cals.turnMotors[idx]);
            enc = new AnalogInput(cals.turnEncoderIds[idx]);
            location = Vector.fromXY(cals.xPos[idx], cals.yPos[idx]);
            this.idx = idx;
            this.angleOffset = cals.angleOffset[cals.turnEncoderIds[idx]];
        }

        public double calcRotVec(double centX, double centY){
            Vector v = Vector.fromXY(centX, centY);
            v.inverse().add(location);
            v.theta -= Math.PI/2;
            rotVec = v;
            //SmartDashboard.putString("Location" + idx, 
            //    location.toString());
            //SmartDashboard.putString("InitRotate" + idx, v.toString());
            return rotVec.r;
        }

        public void drive(boolean parkMode){
            double encVoltage = enc.getVoltage();
            SmartDashboard.putNumber("RawEnc" + idx, encVoltage);
            double currentAngle = ((encVoltage - angleOffset) 
                *2*Math.PI/5);
            double angleDiff = wheelVec.theta - currentAngle;
            //SmartDashboard.putNumber("AngleRaw" + idx, 
            //    Math.toDegrees(angleDiff));

            angleDiff = ((angleDiff+Math.PI*2) % (2*Math.PI)) - Math.PI;
            //angleDiff = Math.IEEEremainder(angleDiff, 2*Math.PI) - Math.PI;

            //SmartDashboard.putNumber("AngleDiff" + idx, 
            //    Math.toDegrees(angleDiff));

            if(Math.abs(angleDiff) > Math.PI/2){
                wheelVec.r *= -1;
                if(angleDiff < 0) {
                    angleDiff += Math.PI;
                    wheelVec.theta += Math.PI;
                } else if(angleDiff > 0) {
                    angleDiff -= Math.PI;
                    wheelVec.theta -= Math.PI;
                }
            }
            //SmartDashboard.putNumber("CurrAngle" + idx, Math.toDegrees(currentAngle));
            //SmartDashboard.putNumber("AngleCorr" + idx, Math.toDegrees(angleDiff));
            SmartDashboard.putString("WheelCmd" + idx, wheelVec.toString());
            

            double deltaTicks = angleDiff / (2*Math.PI) * k.turnGearRatio;
            double targetTicks = turnMotor.getPosition();
            if((wheelVec.r != 0 || parkMode) && Math.abs(deltaTicks) > k.DRV_TURNDEADBND){ //don't turn unless we actually want to move
                targetTicks += deltaTicks;
            } 
            SmartDashboard.putNumber("TargetTicks" + idx, targetTicks);
            SmartDashboard.putNumber("DeltaTicks" + idx, deltaTicks);
            driveMotor.setPower(wheelVec.r);
            turnMotor.setPosition(targetTicks);
        }
    }

    Wheel[] wheels;
    public DriverCals k;
    public AHRS navX;
    private RobotContainer mSubsystem;

    public Drivetrain(DriverCals cals, RobotContainer subsystem){
        k = cals;
        if(cals.disabled) return;
        mSubsystem = subsystem;
        
        int size = k.driveMotors.length;
        wheels = new Wheel[size];
        for(int i=0; i<size; i++){
            wheels[i] = new Wheel(k, i);
        }

        navX = new AHRS(Port.kMXP);
    }

    public double parkTime = 0.0;
    public boolean parkMode = false;
    double currentTime = 0.0;
    public double prevRot;
    public boolean driveStraight = false;
    public double prevAng = 0.0;
    public double goalAng = 0.0;
    public double robotAng = 0;
    public DistanceSensors distSens = new DistanceSensors();

        //joystick x, joystick y, joystick rot, center of rotation x and y, field oriented
    public void drive(Vector strafe, double rot, double centX, double centY, boolean fieldOrient){
        if(k.disabled) return;
        currentTime = Timer.getFPGATimestamp();
        //SmartDashboard.putString("Strafe", String.format("%.2f, %.0f", 
        //    strafe.r, Math.toDegrees(strafe.theta)));
        //SmartDashboard.putNumber("prevAngle", prevAng);
        //SmartDashboard.putNumber("RobotAngle", navX.getAngle());
        if(fieldOrient){
            strafe.theta -= Math.toRadians(-navX.getAngle()) - Math.PI/2;
        }
        
        SmartDashboard.putBoolean("Driving Straigth", driveStraight);
        
        if(0 == rot && 0 == strafe.r){
            if(parkTime <= currentTime){
                parkMode = true;
            }
            else parkMode = false;
        }else{
            parkTime = currentTime + k.parkOffset;
            parkMode = false;
        }

        driveStraight = (rot == 0 && !parkMode);

        if(driveStraight){
            rot = outRot(k.driveStraightKp, goalAng);
        }

        if(!driveStraight) goalAng = prevAng;
        //SmartDashboard.putNumber("Goal Angle", goalAng);

        //SmartDashboard.putNumber("Rot", rot);
        //SmartDashboard.putNumber("parkTime", parkTime);
        SmartDashboard.putBoolean("parkMode", parkMode);
        
        double maxMag = 0;
        for(Wheel w : wheels){//calculate rotation
            double mag = w.calcRotVec(centX, centY);
            maxMag = Math.max(maxMag, Math.abs(mag));
        }
        //SmartDashboard.putNumber("MaxMag",maxMag);


        Wheel maxOut = wheels[0];
        for(Wheel w : wheels){//normalize the rotation
            w.rotVec.r /= maxMag;
            w.rotVec.r *= rot;
            w.wheelVec = Vector.add(w.rotVec, strafe); 
            if(Math.abs(w.wheelVec.r) > Math.abs(maxOut.wheelVec.r)){
                maxOut = w;
            }
            //SmartDashboard.putString("NormRotate" + w.idx, 
            //    w.rotVec.toString());
            //SmartDashboard.putString("RawWheelCmd" + w.idx, 
            //    w.wheelVec.toString());

            if(parkMode){
                w.wheelVec.theta = w.location.theta;
            }

        }
        
        double maxPower = 1;
        if(mSubsystem.m_input.pitMode()){
            maxPower = 0.2;
        }
        if(Math.abs(maxOut.wheelVec.r) > maxPower){
            double reducRatio = maxPower/maxOut.wheelVec.r;

            strafe.r *= reducRatio;

            for(Wheel w: wheels){
                w.rotVec.r *= reducRatio;
                w.wheelVec= Vector.add(strafe, w.rotVec);
            }
        }

        

        for(Wheel w : wheels){
            //SmartDashboard.putString("FinalWheel" + w.idx, 
            //    w.wheelVec.toString());
            w.drive(parkMode);
            SmartDashboard.putNumber("DrivePower "+w.idx, strafe.r);
            SmartDashboard.putNumber("Turn Pwr" + w.idx, w.rotVec.r);
        }

        prevAng = navX.getAngle();
    }

    public double outRot(double pCorrection, double targetAng){
        double error = targetAng - navX.getAngle();
        double kP = pCorrection;
        //SmartDashboard.putNumber("Straight Error", error);
        double output = kP * error;
        return output;
    }

    public double[] getDist(){
        double[] dists = new double[4];
        for(int i = 0 ; i < wheels.length ; i++){
            dists[i] = wheels[i].driveMotor.getPosition() * k.driveGearRatio;
        }
        return dists;
    }

    public DistData getRearDist(){
        return distSens.getRear();
    }

    public DistData getRightDist(){
        return distSens.getRight();
    }

    public void periodic(){
        if(k.disabled) return;
        Display.put("NavX Ang", navX.getAngle());
        for(Wheel w: wheels){
            Display.put("DMotorCurrent " + w.idx, wheels[w.idx].driveMotor.getCurrent());
            Display.put("TMotorCurrent " + w.idx, wheels[w.idx].turnMotor.getCurrent());
            Display.put("DMotorTemp " + w.idx, wheels[w.idx].driveMotor.getTemp());
            Display.put("TMotorTemp " + w.idx, wheels[w.idx].turnMotor.getTemp());
        }

        Display.put("DistSenseInfo Re", distSens.getRear().toString());
        Display.put("DistSenseInfo Ri", distSens.getRight().toString());
    }
}