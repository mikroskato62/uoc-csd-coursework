/* [ Java - HY252 - A2 - Assignment 1 - RIP - csd5748 ] */
// [!] Please note: Some classes/variables are public because they are used by the other assignments ...
// (* Instructions): Package; Imports; Class:

package A21;
import java.util.Arrays;
import java.util.ArrayList;

public class SensorsAssignment
{
    // (a.1): BaseSuperClass: Variables; Constructor; Setters; Getters; ToString.
    public static class Sensor
    {
        private String id; private boolean violation; private boolean on;

        public Sensor(String id, boolean violation, boolean on) { setId(id); setViolation(violation); setOn(on); }

        public void setId(String id) { this.id = id; }
        public void setViolation(boolean violation) { this.violation = violation; }
        public void setOn(boolean on) { this.on = on; }

        public String getId() { return id; }
        public boolean getViolation() { return violation; }
        public boolean getOn() { return on; }

        @Override
        public String toString()
        { return ("Sensor with: id = " + getId() + ", on state = " + getOn() + ", violation state = " + getViolation()); }
    }

    // (a.2.i): DerivedSubClass: Constructor; ToString.
    public static class TemperatureSensor extends Sensor
    {
        public TemperatureSensor(String id, boolean violation, boolean on) { super(id, violation, on); }

        @Override public String toString() { return (super.toString() + ", (temp sensor)"); }
    }

    // (a.2.ii): DerivedSubClass: Variables; Constructor; Setter; Getter; ToString.
    public static class LaserSensor extends Sensor
    {
        private float range;

        public LaserSensor(String id, boolean violation, boolean on, float range) { super(id, violation, on); setRange(range); }

        public void setRange(float range) { this.range = range; }
        public float getRange() { return this.range; }

        @Override public String toString() { return (super.toString() + ", range = " + getRange() + ", (laser sensor)"); }
    }

    // (b): NewClass: Variables; Constructors; Setter; Getter; Methods; ToString;
    public static class SensorLine
    {
        private String name;
        private final Sensor [] array = new Sensor[1000];
        private int activeCapacity = 0;

        public SensorLine() { this.name = "Anonymous SensorLine"; }
        public SensorLine(String name) { setName(name); }

        void setName(String name) { this.name = name; }
        public String getName() { return name; }

        public void add(Sensor s) { if (activeCapacity < array.length) { array[activeCapacity++] = s; } }
        void setOn(boolean v) { for(int i = 0; i < activeCapacity; i++) array[i].setOn(v); }
        public boolean isViolated() { for(int i=0; i<activeCapacity; i++) { if(array[i].getViolation()) return true; } return false; }
        public Sensor[] getSensors() { return (Arrays.copyOf(array, activeCapacity)); }

        @Override public String toString()
        {
            int violatedOn = 0;
            ArrayList<String> violatedIds = new ArrayList<>();
            for (int i = 0; i < activeCapacity; i++) { if (array[i].getViolation() && array[i].getOn()) { violatedOn++; violatedIds.add(array[i].getId()); } }
            return (name + " with: number of sensors = " + activeCapacity + ", violated = " + isViolated() + ", number of vailoated on = " + violatedOn + ", ids of violated on = " + violatedIds);
        }
    }

    // (c): The Main Class (Testing) - Client: SensorTester:
    public static void main(String[] args)
    {
        Sensor Sensor1 = new Sensor("S01", false, false);
        System.out.println("[a.1]: " + Sensor1 + ".");

        TemperatureSensor Sensor2 = new TemperatureSensor("S02", false, false);
        System.out.println("[a.2i]: " + Sensor2 + ".");

        LaserSensor Sensor3 = new LaserSensor("S03", false, false, 4F);
        System.out.println("[a.2ii]: " + Sensor3 + ".");

        SensorLine newline = new SensorLine("Sensor Line 0");
        newline.add(Sensor1); newline.add(Sensor2); newline.add(Sensor3);
        System.out.println("[b]: " + newline + ".");

        SensorLine SensorTester = new SensorLine();
        for (int i = 1; i <= 100; i++)
        {
            if ((i % 3) == 1) { SensorTester.add(new TemperatureSensor(("SID" + i), (i % 4 == 0), true)); }
            else if ((i % 3) == 2) { SensorTester.add(new LaserSensor(("SID" + i), (i % 4 == 0), true, (i))); }
            else { SensorTester.add(new Sensor(("SID" + i), (i % 4 == 0), true)); }
        }
        System.out.println("[c]: " + SensorTester + ".");
    }
}

// The End :)