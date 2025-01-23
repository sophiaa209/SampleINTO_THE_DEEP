package org.firstinspires.ftc.teamcode.drive.writtenCode.controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;


import org.firstinspires.ftc.teamcode.drive.writtenCode.RobotMap;
@Config
public class LiftController {
    //this controls the position of the lift; it can be either in the init position or score position
    public enum liftStatus
    {
        INIT,
        SCORE;
    }

    public liftStatus currentStatus=liftStatus.INIT;
    public liftStatus previousStatus=null;

    public DcMotorEx lift1 = null;
    public DcMotorEx lift2 = null;

    public int  init_position=0;
    public int score_position=1;
    public int currentPosition = init_position;

    public LiftController(RobotMap robot) {
        this.lift1=robot.lift1;
        this.lift2=robot.lift2;
    }

    public void update(int target) {
        this.lift1.setTargetPosition(currentPosition);
        this.lift1.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        this.lift2.setTargetPosition(currentPosition);
        this.lift2.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);


        if(currentStatus!=liftStatus.INIT) {
            this.lift1.setPower(1);
            this.lift2.setPower(1);
        }

        double lift1_current_position = lift1.getCurrentPosition();
        double lift2_current_position = lift2.getCurrentPosition();

        if (currentStatus != previousStatus) {
            previousStatus = currentStatus;


            switch (currentStatus) {

                case INIT: {
                    this.lift1.setPower(0.5);
                    this.lift2.setPower(0.5);
                    currentPosition = init_position;
                    break;
                }

                case SCORE: {
                    currentPosition=score_position;
                    break;
                }

            }
        }
    }
}
