package org.firstinspires.ftc.teamcode.drive.writtenCode.controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.writtenCode.RobotMap;
@Config
public class ClawController {
//this controls whether the claw is open or closed
    public enum clawStatus{
        OPEN,
        CLOSED;
    }

    public clawStatus currentStatus= clawStatus.OPEN;
    public clawStatus previousStatus=null;

    public Servo clawLeft = null;
    public Servo clawRight = null;

    public static double open_position=0;
    public static double closed_position=0.4;

    public ClawController(RobotMap robot) {
        this.clawLeft=robot.clawLeft;
        this.clawRight=robot.clawRight;
    }

    public void update()
    {
        if(currentStatus!=previousStatus)
        {
            previousStatus=currentStatus;


            switch(currentStatus)
            {
                case OPEN:
                {
                    this.clawLeft.setPosition(open_position);
                    this.clawRight.setPosition(closed_position);

                    break;
                }

                case CLOSED:
                {
                    this.clawRight.setPosition(open_position);
                    this.clawLeft.setPosition(closed_position);
                    break;
                }

            }
        }

    }
}
