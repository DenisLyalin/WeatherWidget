import manager.WeatherWidget;

/**
 * @author Denis
 * @version 1.0
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