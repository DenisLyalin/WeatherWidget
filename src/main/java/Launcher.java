import manager.WeatherWidget;

/**
 * @author Denis
 * @version 0.1b
 * Main class-launcher
 * Launch WeatherWidget
 */

public class Launcher {

    /**
     * Main program method
     */
    public static void main(String[] args) {
        WeatherWidget weatherWidget = new WeatherWidget();
        weatherWidget.mainProcess();
    }
}