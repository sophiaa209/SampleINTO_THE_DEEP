package org.firstinspires.ftc.teamcode.drive.writtenCode.controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.writtenCode.RobotMap;

@Config
public class PivotController {

    public enum pivotStatus {
        INIT,
        SCORE;
    }

    public pivotStatus currentStatus=pivotStatus.INIT;
    public pivotStatus previousStatus=null;

    public Servo pivot1 = null;
    public Servo pivot2 = null;

    public static double init_position=0;
    public static double extend_position=1;


    public PivotController(RobotMap robot) {
        this.pivot1=robot.pivot1;
        this.pivot2=robot.pivot2;
    }


    public void update() {

        if (currentStatus != previousStatus) {
            previousStatus = currentStatus;


            switch (currentStatus) {

                case INIT: {
                    this.pivot1.setPosition(init_position);
                    this.pivot2.setPosition(init_position);
                    break;
                }

                case SCORE: {
                    this.pivot1.setPosition(extend_position);
                    this.pivot2.setPosition(extend_position);
                    break;
                }

            }
        }
    }
}
