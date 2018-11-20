package uff.simonalzheimer.app;


class RuleValues {

    public static final String HEART_BEAT = "Heart Beat";
    public static final String TEMPERATURE = "Body Temperature";
    public static final String BLOOD_PRESSURE = "Blood Preassure";
    public static final String ACTIVITY = "Activity";
    public static final String LOCATION = "Location";

    public static String LOW = "Low";
    public static String NORMAL = "Normal";
    public static String HIGH = "High";

    public static String ON = "On";
    public static String OFF = "Off";

    public static String OPEN = "Open";
    public static String CLOSED = "Closed";

    public static String STANDING = "Standing";
    public static String WALKING = "Walking";
    public static String LYING = "Lying";
    public static String SITING = "Siting";
    public static String RUNNING = "Running";

    public static String LIVING_ROOM = "Living Room";
    public static String KITCHEN = "Kitchen";
    public static String BATHROOM = "Bathroom";
    public static String BEDROOM = "Bedroom";
    public static String DINING_ROOM = "Dining Room";
    public static String OUTDOOR = "Outdoor";
    public static String BALCONY = "Balcony";
    public static String GARAGE = "Garage";
    public static String SERVICE_AREA = "Service Area";

    //ACTIONS
    public static String ALERT_CAREGIVER = "Alert Caregiver";
    public static String ALERT_FAMILIAR = "Alert Familiar";
    public static String ALERT_BOTH = "Alert Caregiver and Familiar";
    public static String ALERT_PATIENT = "Alert Patient";
    public static String CALL_EMERGENCY = "Call Emergency Service";

    public static PossibleCondition possibleConditionFactory(String type){
        PossibleCondition pc = null;
        switch (type){
            case HEART_BEAT:
                pc = new PossibleCondition(HEART_BEAT);
                pc.addValue(LOW);
                pc.addValue(NORMAL);
                pc.addValue(HIGH);
                break;
            case BLOOD_PRESSURE:
                pc = new PossibleCondition(BLOOD_PRESSURE);
                pc.addValue(LOW);
                pc.addValue(NORMAL);
                pc.addValue(HIGH);
                break;
            case TEMPERATURE:
                pc = new PossibleCondition(TEMPERATURE);
                pc.addValue(LOW);
                pc.addValue(NORMAL);
                pc.addValue(HIGH);
                break;
            case ACTIVITY:
                pc = new PossibleCondition(ACTIVITY);
                pc.addValue(STANDING);
                pc.addValue(WALKING);
                pc.addValue(LYING);
                pc.addValue(SITING);
                pc.addValue(RUNNING);
                break;
            case LOCATION:
                pc = new PossibleCondition(LOCATION);
                pc.addValue(LIVING_ROOM);
                pc.addValue(KITCHEN);
                pc.addValue(BATHROOM);
                pc.addValue(BEDROOM);
                pc.addValue(DINING_ROOM);
                pc.addValue(OUTDOOR);
                pc.addValue(BALCONY);
                pc.addValue(GARAGE);
                pc.addValue(SERVICE_AREA);
                break;
        }
        return pc;
    }
}
