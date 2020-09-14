import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlertForTemperature {

    // Symbols
    private static final String FREEZING = "freezing";
    private static final String UNFREEZING = "unfreezing";
    private static final String BOILING = "boiling";
    private static final String UNBOILING = "unboiling";

    // Parameters
    private static double freezing__threshold;
    private static double boiling_threhold;
    private static double fluctuation_value;

    /**
     * Return temperature strings with symbols.
     * @param temperatures_double
     * @return
     */
    private String temperatureTransform (List<Double> temperatures_double) {
        StringBuilder res = new StringBuilder();

        // Freezing Symbol. If one temperature is according to the freezing definition, then the status is changed to Freezing until there is unFreezing temperature
        boolean isFreezing = false;

        // Boiling Symbol. If one temperature is according to the boiling definition, then the status is changed to Boiling until there is unBoiling temperature
        boolean isBoiling = false;

        for (double temperature : temperatures_double) {
            // Add blank field
            res.append(temperature).append(" ");

            // If the status is not already freezing and the temperature is no higher than freezing threshold, then the status will be changed to freezing.
            // And if isFreezing is true, the isBoiling should be false;
            if (!isFreezing && temperature <= freezing__threshold) {
                res.append(FREEZING).append(" ");
                isFreezing = true;
                isBoiling = false;
            }

            // If the status is already freezing and temperature is higher than freezing__threshold + fluctuation_value. Need to add unFreezing symbol
            if (isFreezing && temperature > freezing__threshold + fluctuation_value) {
                res.append(UNFREEZING).append(" ");
                isFreezing = false;
            }

            // If the status is not already boiling and the temperature is no lower than boiling threshold, then the status will be changed to boiling.
            // And if isBoiling is true, the isFreezing should be false;
            if (!isBoiling && temperature >= boiling_threhold) {
                res.append((BOILING)).append(" ");
                isBoiling = true;
                isFreezing = false;
            }

            // If the status is already boiling and temperature is lower than boiling_threhold - fluctuation_value. Need to add unBoiling symbol
            if (isBoiling && temperature < boiling_threhold - fluctuation_value) {
                res.append(UNFREEZING).append(" ");
                isBoiling = false;
            }

        }
        return res.toString();
    }


    /**
     * Input engine
     * @param alertForTemperature
     * @throws TemperatureException
     */
    public static void inputEngine(AlertForTemperature alertForTemperature) throws TemperatureException {

        Scanner sc = new Scanner(System.in);
        String str = "";
        try {

            System.out.println("Alerts for temperature values.");
            System.out.println();
            System.out.println("For examples: ");
            System.out.println("freezing threshold: 0");
            System.out.println("boiling threshold: 100");
            System.out.println("fluctuation value: 0.5");
            System.out.println("temperatures: 5.0 -0.5 0.5 -0.2 100 101");
            System.out.println("The output will be: 5.0 -0.5 freezing 0.5 -0.2 100 unfreezing boiling 101");
            System.out.println("Use exit the shutdown the app");
            System.out.println();

            // Read the parameters first
            System.out.print("Please input freezing threshold：");
            if((str=sc.nextLine()).equals("exit")){
                return;
            }
            freezing__threshold = Double.parseDouble(str);


            System.out.print("Please input boiling threshold：");
            if((str=sc.nextLine()).equals("exit")){
                return;
            }
            boiling_threhold = Double.parseDouble(str);


            // Boiling temperature should higher than freezing temperature
            if (freezing__threshold >= boiling_threhold) {
                throw new TemperatureException("Invalid Input. Freezing threshold should not higher than boiling threshold");
            }

            // Read the temperatures
            System.out.print("Please input fluctuation value：");
            if((str=sc.nextLine()).equals("exit")){
                return;
            }
            fluctuation_value = Double.parseDouble(str);


            System.out.println("Please input temperatures：");
            while(!(str=sc.nextLine()).equals("exit")){
                List<Double> temperatures_double = new ArrayList<>();
                String[] temperatures = str.trim().split(" ");
                for (String temperature : temperatures) {
                    temperatures_double.add(Double.parseDouble(temperature));
                }
                String res = alertForTemperature.temperatureTransform(temperatures_double);
                System.out.println(res);
            }

            System.out.println();
        } catch (NumberFormatException e) {
            throw new TemperatureException("Invalid Input. Please input digits here：", e);
        }

    }


    public static void main(String[] args) throws TemperatureException {
        inputEngine(new AlertForTemperature());
    }
}