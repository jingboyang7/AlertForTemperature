public class TemperatureException extends Exception {

    public TemperatureException(String reason) {
        System.out.println(reason);
    }

    public TemperatureException (String reason, Throwable e) {
        this(reason);
        e.printStackTrace();
    }
}
