package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Denis
 * @version 1.0
 * Class for creating the main program window
 */

public class MainWindow {
    private final JLabel fieldCity;
    private final JLabel fieldTempC;
    private final JTextField fieldForCity;
    private final JLabel timeStamp;

    /**
     * Constructor that creates the main window
     *
     * @param gui - class for changing data in the main window
     */
    public MainWindow(final Presenter gui) {
        JFrame frame = new JFrame();
        fieldForCity = new JTextField();
        fieldForCity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.setCityFromMainWindow(fieldForCity.getText());
                fieldForCity.setText(null);
            }
        });
        fieldForCity.setBounds(2, 2, 226, 24);
        fieldCity = new JLabel("Loading...");
        fieldCity.setBounds(2, 50, 200, 24);
        fieldTempC = new JLabel("\u2014 \u2103");
        fieldTempC.setBounds(202, 50, 100, 24);
        timeStamp = new JLabel();
        JButton changeCity = new JButton("Change City");
        changeCity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.setCityFromMainWindow(fieldForCity.getText());
                fieldForCity.setText(null);
            }
        });
        changeCity.setBounds(228, 2, 105, 23);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(fieldCity);
        frame.add(fieldTempC);
        frame.add(changeCity);
        frame.add(fieldForCity);
        frame.add(timeStamp);
        frame.getContentPane().add(BorderLayout.SOUTH, timeStamp);
        frame.getContentPane().setBackground(new Color(90, 221, 255));
        frame.setSize(350, 200);
        frame.setVisible(true);
    }

    /**
     * The method for displaying a city in the main window
     *
     * @param city - a city to display
     */
    public void showCity(final String city) {
        fieldCity.setText("Temperature in the " + city + ": ");
        timeStamp.setText(" ");
    }

    /**
     * The method for displaying a temperature in the main window
     *
     * @param Temp - a temperature to display
     */
    public void showTemp(final String Temp) {
        fieldTempC.setText(Temp + " \u2103");
        timeStamp.setText(" ");
    }

    /**
     * The method for displaying a time stamp in the main window
     *
     * @param lastTimeUpdate - a time stamp to display
     */
    public void setTimeStamp(final String lastTimeUpdate) {
        timeStamp.setText(lastTimeUpdate);
    }
}