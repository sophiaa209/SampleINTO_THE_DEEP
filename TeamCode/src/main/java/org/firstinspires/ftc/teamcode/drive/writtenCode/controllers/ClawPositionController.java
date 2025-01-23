package org.firstinspires.ftc.teamcode.drive.writtenCode.controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.writtenCode.RobotMap;

@Config

public class ClawPositionController {

    public enum clawPositionStatus{
        INIT,
        COLLECT,
        PLACESPECIMEN;
    }

    public clawPositionStatus currentStatus= clawPositionStatus.INIT;
    public clawPositionStatus previousStatus=null;

    public Servo clawPosition = null;
    public static double init_position=0.02;
    public static double collect_position=0.6;
    public static double specimen_position=1;


    public ClawPositionController(RobotMap robot) {
        this.clawPosition=robot.clawPosition;
    }

    public void update()
    {

        if(currentStatus!=previousStatus)
        {
            previousStatus=currentStatus;


            switch(currentStatus)
            {

                case INIT:
                {
                    this.clawPosition.setPosition(init_position);
                    break;
                }

                case COLLECT:
                {
                    this.clawPosition.setPosition(collect_position);
                    break;
                }

                case PLACESPECIMEN:
                {
                    this.clawPosition.setPosition(specimen_position);
                }

            }
        }

    }
}