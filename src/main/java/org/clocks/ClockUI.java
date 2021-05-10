package org.clocks;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ClockUI {

    public ClockUI(IClockTime time, int index) {

        clock_ = new ClockClient();
        clock_.set_time(time);

        time_  = new Label(time.get_time());
        index_ = index;

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), ae -> {

            time_.setText(clock_.get_time());

            String alarm = clock_.get_message();
            if (!alarm.equals("\n")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setHeaderText(null);
                alert.setTitle("Alarm");
                alert.setContentText(alarm);

                alert.show();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Label header = new Label("Clock " + index_);
        header.setStyle("-fx-font-size:16; -fx-font-family: 'Helvetica';");
        time_.setStyle("-fx-font-size:14; -fx-font-family: 'Helvetica';");

        alarm_button_.setOnAction(ae -> clock_.set_alarm(Long.parseLong(hour_.getText()),
                Long.parseLong(minute_.getText()),
                Long.parseLong(second_.getText()),
                message_.getText()));

        pause_button_.setOnAction(ae -> {
            clock_.pause();
        });

        hour_.setPromptText("Hour");
        minute_.setPromptText("Minute");
        second_.setPromptText("Second");

        vbox_ = new VBox();
        vbox_.setSpacing(30);
        vbox_.setStyle("-fx-border-color:black;-fx-border-width: 0.25 1 0.25 0;");
        vbox_.getChildren().add(header);
        vbox_.getChildren().add(new Label("Time:"));
        vbox_.getChildren().add(time_);
        vbox_.getChildren().add(pause_button_);
        vbox_.getChildren().add(new Label("Add Alarm:"));
        vbox_.getChildren().add(new HBox(hour_, minute_, second_));
        vbox_.getChildren().add(message_);
        vbox_.getChildren().add(alarm_button_);
    }

    public VBox get_vbox() {
        return vbox_;
    }

    private VBox      vbox_;
    private Label     time_;
    private int       index_;
    private Button    alarm_button_ = new Button("Add");
    private Button    pause_button_ = new Button("Pause/Run");
    private TextField hour_         = new TextField();
    private TextField minute_       = new TextField();
    private TextField second_       = new TextField();
    private TextArea  message_      = new TextArea("Alarm Message");

    private ClockClient clock_;
}
