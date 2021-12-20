package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Denis
 * @version 0.2b
 * Class for creating the main program window
 */

public class MainWindow {
    private final JLabel fieldCity;
    private final JLabel fieldTempC;
    private final JTextField fieldForCity;
    private final JLabel timeStamp;

    /**
     * Method for creating the main window
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
        fieldCity = new JLabel("Temperature is loading: ");
        fieldCity.setBounds(2, 50, 200, 24);
        fieldTempC = new JLabel(" -- \u2103");
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
        frame.getContentPane().setBackground(new Color(135, 206, 235));
        frame.setSize(350, 200);
        frame.setVisible(true);
    }

    /**
     * Method for changing the city on the main window
     */

    public void showCity(final String city) {
        fieldCity.setText("Temperature in the " + city + ": ");
        timeStamp.setText(" ");
    }

    public void showTemp(final String Temp) {
        fieldTempC.setText(Temp);
        timeStamp.setText(" ");
    }

    public void setTimeStamp(final String lastTimeUpdate) {
        timeStamp.setText(lastTimeUpdate);
    }
}