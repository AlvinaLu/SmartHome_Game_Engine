package smarthome;

public class SmartHome {
    public static String name = "0";
    public static void main(String[] args) {

        switch (args[0]) {
            case "Scenario1" :
                name = "Scenario1";
                Scenario1.start();
                break;
            case "Scenario2" :
                name = "Scenario2";
                Scenario2.start();
                break;
        }
    }
}
