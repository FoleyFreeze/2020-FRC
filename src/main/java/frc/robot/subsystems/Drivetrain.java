package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.DriverCals;
import frc.robot.motors.Motor;
import frc.util.Vector;
import com.kauailabs.navx.frc.*;

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
            this.angleOffset = cals.angleOffset[idx];
        }

        public double calcRotVec(double centX, double centY){
            Vector v = Vector.fromXY(centX, centY);
            v.inverse().add(location);
            v.theta -= Math.PI/2;
            rotVec = v;
            SmartDashboard.putString("Location" + idx, location.toString());
            SmartDashboard.putString("InitRotate" + idx, v.toString());
            return rotVec.r;
        }

        public void drive(){
            double encVoltage = enc.getVoltage();
            SmartDashboard.putNumber("RawEnc" + idx, encVoltage);
            double currentAngle = ((encVoltage - angleOffset) *2*Math.PI/5);
            double angleDiff = wheelVec.theta - currentAngle;
            SmartDashboard.putNumber("AngleRaw" + idx, Math.toDegrees(angleDiff));
            angleDiff = ((angleDiff+Math.PI*2) % (2*Math.PI)) - Math.PI;
            //angleDiff = Math.IEEEremainder(angleDiff, 2*Math.PI) - Math.PI;
            SmartDashboard.putNumber("AngleDiff" + idx, Math.toDegrees(angleDiff));
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
            SmartDashboard.putNumber("CurrAngle" + idx, Math.toDegrees(currentAngle));
            SmartDashboard.putNumber("AngleCorr" + idx, Math.toDegrees(angleDiff));
            SmartDashboard.putString("WheelCmd" + idx, wheelVec.toString());
            

            double deltaTicks = angleDiff / (2*Math.PI) / k.turnGearRatio * 4096;
            double targetTicks = turnMotor.getPosition();
            if(wheelVec.r != 0){ //don't turn unless we actually want to move
                targetTicks += deltaTicks;
            } 
            SmartDashboard.putNumber("TargetTicks" + idx, targetTicks);
            driveMotor.setPower(wheelVec.r);
            turnMotor.setPosition(targetTicks);
        }
    }

    Wheel[] wheels;
    DriverCals k;
    AHRS navX;
    
    public Drivetrain(DriverCals cals){
        if(cals.disabled) return;
        k = cals;
        int size = cals.driveMotors.length;
        wheels = new Wheel[size];
        for(int i=0; i<size; i++){
            wheels[i] = new Wheel(cals, i);
        }

        navX = new AHRS(Port.kMXP);
    }

        //joystick x, joystick y, joystick rot, center of rotation x and y, arbitration ratio 0-1
    public void drive(Vector strafe, double rot, double centX, double centY, double arbRatio, boolean fieldOrient){
        if(k.disabled) return;
        SmartDashboard.putString("Strafe", String.format("%.2f, %.0f", strafe.r, Math.toDegrees(strafe.theta)));

        SmartDashboard.putNumber("RobotAngle", navX.getAngle());
        if(fieldOrient){
            strafe.theta -= Math.toRadians(-navX.getAngle()) - Math.PI/2;
        }
        
        double maxMag = 0;
        for(Wheel w : wheels){//calculate rotation
            double mag = w.calcRotVec(centX, centY);
            maxMag = Math.max(maxMag, Math.abs(mag));
        }
        SmartDashboard.putNumber("MaxMag",maxMag);

        Wheel maxOut = wheels[0];
        for(Wheel w : wheels){//normalize the rotation
            w.rotVec.r /= maxMag;
            w.rotVec.r *= rot;
            w.wheelVec = Vector.add(w.rotVec, strafe); 
            if(Math.abs(w.wheelVec.r) > Math.abs(maxOut.wheelVec.r)){
                maxOut = w;
            }
            SmartDashboard.putString("NormRotate" + w.idx, w.rotVec.toString());
            SmartDashboard.putString("RawWheelCmd" + w.idx, w.wheelVec.toString());
        }
        
        if(Math.abs(maxOut.wheelVec.r) > 1){
            /*double R = (1-arbRatio)/arbRatio;
            double r1 = strafe.r;
            double r2 = maxOut.rotVec.r;
            double a = 1 + (R*R * r2*r2 /r1/r1);
            double b = 2 * r2 * Math.cos(maxOut.rotVec.theta - strafe.theta);
            double ans = (-b + Math.sqrt(b*b + 4*a))/2/a;//c is ALWAYS -1
            double ans2 = (-b - Math.sqrt(b*b + 4*a))/2/a;//c is ALWAYS -1

            SmartDashboard.putString("QuadraticZeros", String.format("%.2f, %.2f", ans, ans2));
            if(ans2 > ans) ans = ans2;
            
            double strafeReduc = ans/r1;
            double rotReduc = R*strafeReduc;
            SmartDashboard.putString("ArbitrationFactors", String.format("%.2f, %.2f", strafeReduc, rotReduc));
            */
            
            double R, r1, r2;
            double strafeReduc;
            if(arbRatio > 0.5){
                R = (1-arbRatio)/arbRatio;
                r1 = maxOut.rotVec.r;
                r2 = strafe.r;
            } else {
                R = arbRatio/(1-arbRatio);
                r1 = strafe.r;
                r2 = maxOut.rotVec.r;
            }
            double rotReduc = Math.sqrt(1/(R*R * r1*r1 + r2*r2 +2*R*r1*r2*Math.cos(maxOut.rotVec.theta - strafe.theta)));
            if(R != 0){
                strafeReduc = rotReduc/R;
            }else strafeReduc = (0||1);//later

            strafe.r *= strafeReduc;

            for(Wheel w: wheels){
                w.rotVec.r *= rotReduc;
                w.wheelVec= Vector.add(strafe, w.rotVec);
            }
        }

        for(Wheel w : wheels){
            SmartDashboard.putString("FinalWheel" + w.idx, w.wheelVec.toString());
            w.drive();
        }
    }

}