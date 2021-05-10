package org;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.clocks.Clock;
import org.clocks.ClockUI;
import org.clocks.DefaultTime;
import org.clocks.IClock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML
    public GridPane gridPane;

    @FXML
    public TextField menu_hour;

    @FXML
    public TextField menu_min;

    @FXML
    public TextField menu_sec;

    @FXML
    public void add_clock() {
        if (number_of_clocks == 4) throw new IndexOutOfBoundsException("App supports only 4 clocks at the same time");

        clockUIS[number_of_clocks] = new ClockUI(new DefaultTime(Long.parseLong(menu_hour.getText()),
                Long.parseLong(menu_min.getText()),
                Long.parseLong(menu_sec.getText())), number_of_clocks + 1);

        gridPane.add(clockUIS[number_of_clocks].get_vbox(), number_of_clocks + 1, 0);

        number_of_clocks++;
    }

    public ClockUI[] clockUIS         = new ClockUI[4];
    public int       number_of_clocks = 0;
};

