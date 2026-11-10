/* [ Java - HY252 - A2 - Assignment 2 - RIP - csd5748 ] */
// [!] Save of the used command for "Javadoc" in this directory (copy-paste) & view "index.html" file ...
/*
 javadoc -d doc -author -version -private ^
 -tag pre:a:"Precondition:" -tag post:a:"Postcondition:" ^
 -classpath "C:\libs\junit-jupiter-api-5.10.2.jar; C:\libs\jfugue-5.0.9.jar;." ^
 A22/HomeSecurityAssignment.java
*/
// [*] (Instructions): Package; Imports; The Class:

package A22;
import A21.SensorsAssignment;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested; import org.junit.jupiter.api.Test;
import org.jfugue.Player;

/** <hr><b>The primary class for the second assignment ...</b><br><b>@author [RIP] csd5748</b><br><b>@version 6.2</b><hr> **/
public class HomeSecurityAssignment
{
    // (a.i) ADT Specifications:
    /**
     * <hr> The interface representing the abstract data type (ADT) of a home security system. <br><br>
     * Invariants: <br>
     * 1. {@code password != null && password.length() > 0}. <br>
     * 2. state ∈ {DISARMED, ARMED, STAY}. <br>
     * 3. if state == ARMED: all sensors must be non-violated. <br>
     * 4. if state == STAY: all external sensors must be non-violated. <br> <br>
     * Equational Axioms: <br>
     * 1. Arm() possible ⇔ all sensors not violated. <br>
     * 2. Stay() possible ⇔ external sensors not violated ^ state == DISARMED. <br>
     * 3. Disarm(pwd) possible ⇔ pwd == current password. <br>
     * 4. ChangePassword(old, new) possible ⇔ state == DISARMED ^ old == current password. <br> <hr>
    **/
    public interface HomeSecurityADT
    {
        /** <hr> The enumeration of all possible alarm states. <hr> **/
        enum AlarmState { DISARMED, ARMED, STAY }

        /** [ Accessor | Observer ] <br>
         * @return the current alarm state from the enumeration. **/
        AlarmState GetState();

        /** [ Accessor | Observer ] <br>
         * @return string of the current system password. **/
        String GetPassword();

        /** [ Transformer | Mutator ] <br> Arms the alarm system fully. <br>
         * @pre all internal and external sensor lines must not be violated.
         * @post system state becomes {@code ARMED}.
         * @throws InternalSensorViolationException if any internal sensor line is violated.
         * @throws ExternalSensorViolationException if any external sensor line is violated. **/
        void Arm() throws InternalSensorViolationException, ExternalSensorViolationException;

        /** [ Transformer | Mutator ] <br> Activates "STAY" mode (external sensors active, internal inactive). <br>
         * @pre system must be {@code DISARMED &&} all external sensor lines must not be violated.
         * @post system state becomes {@code STAY}.
         * @throws ExternalSensorViolationException if an external sensor line is violated.
         * @throws NotDisarmedException if system is not in DISARMED state. **/
        void Stay() throws ExternalSensorViolationException, NotDisarmedException;

        /** [ Transformer | Mutator ] <br> Disarms the system from ARMED or STAY state. <br>
         * @param password the password needed to disarm the system.
         * @pre current state is {@code ARMED} or {@code STAY}.
         * @post system state becomes {@code DISARMED}.
         * @throws WrongPasswordException if provided password does not match the current one. **/
        void Disarm(String password) throws WrongPasswordException;

        /** [ Transformer | Mutator ] <br> Changes the system password. <br>
         * @param oldpassword the current password.
         * @param newpassword the new password to set.
         * @pre system is DISARMED
         * @pre {@code oldpassword.equals(getPassword())}.
         * @post password becomes newpassword.
         * @throws WrongPasswordException if oldpassword is incorrect.
         * @throws NotDisarmedException if system is not DISARMED. **/
        void ChangePassword(String oldpassword, String newpassword) throws WrongPasswordException, NotDisarmedException;
    }

    // (a.ii) (Some) Exceptions Declarations:
    /** <hr> ET for when any internal sensor line is violated while attempting to arm the system. <hr> **/
    public static class InternalSensorViolationException extends Exception { public InternalSensorViolationException(String message) { super(message); } }

    /** <hr> ET for when any external sensor line is violated while trying to arm/stay system. <hr> **/
    public static class ExternalSensorViolationException extends Exception { public ExternalSensorViolationException(String message) { super(message); } }

    /** <hr> ET for when an incorrect password is entered for disarming or password change. <hr> **/
    public static class WrongPasswordException extends Exception { public WrongPasswordException(String message) { super(message); } }

    /** <hr> ET for when an operation requires the system to be DISARMED but it is not. <hr> **/
    public static class NotDisarmedException extends Exception { public NotDisarmedException(String message) { super(message); } }

    // (b) Example Implementation Class:
    /** <hr> The implementation of the (ADT) interface. <hr> **/
    public static class HomeSecurityImpl implements HomeSecurityADT
    {
        // (*) Variables; Constructor; Getters; Methods; Extra/Other:
        private AlarmState state;
        private String password;
        private SensorsAssignment.SensorLine[] internals;
        private SensorsAssignment.SensorLine[] externals;

        /** <hr> The constructor with two arguments. <hr> **/
        public HomeSecurityImpl(SensorsAssignment.SensorLine[] inside, SensorsAssignment.SensorLine[] outside)
        { this.internals = inside; this.externals = outside; this.state = AlarmState.ARMED; this.password = "1111"; }

        @Override public AlarmState GetState() { return state; }
        @Override public String GetPassword() { return password; }

        @Override
        public void Arm() throws InternalSensorViolationException, ExternalSensorViolationException
        {
            for (SensorsAssignment.SensorLine s : internals) if (s.isViolated()) throw new InternalSensorViolationException("At least one internal sensor violated!");
            for (SensorsAssignment.SensorLine s : externals) if (s.isViolated()) throw new ExternalSensorViolationException("At least one external sensor violated!");
            state = AlarmState.ARMED;
            Sound.playArm();
        }

        @Override
        public void Stay() throws ExternalSensorViolationException, NotDisarmedException
        {
            if (state != AlarmState.DISARMED) throw new NotDisarmedException("System must be disarmed to enter stay mode!");
            for (SensorsAssignment.SensorLine s : externals) if (s.isViolated()) throw new ExternalSensorViolationException("At least one external sensor is violated!");
            state = AlarmState.STAY;
            Sound.playStay();
        }

        @Override
        public void Disarm(String code) throws WrongPasswordException
        {
            if (!password.equals(code)) throw new WrongPasswordException("The password is wrong!");
            state = AlarmState.DISARMED;
            Sound.playDisarm();
        }

        @Override
        public void ChangePassword(String oldcode, String newcode) throws WrongPasswordException, NotDisarmedException
        {
            if (state != AlarmState.DISARMED) throw new NotDisarmedException("System must be disarmed to change password!");
            if (!password.equals(oldcode)) throw new WrongPasswordException("The old password is incorrect!");
            password = newcode;
        }
    }


    // (c) Client Programs - A Simple (whole and correct) Test Case:
    /** <hr> A manual test for the interface and its implementation. <hr> **/
    static class HomeSecurityMyTest
    {
        public static void main(String[] args)
        {
            try
            {
                System.out.println("[*] Starting The Manual Test ...");
                SensorsAssignment.SensorLine[] internal = { new SensorsAssignment.SensorLine("LivingRoom1"), new SensorsAssignment.SensorLine("Bedroom2") };
                SensorsAssignment.SensorLine[] external = { new SensorsAssignment.SensorLine("Door1"), new SensorsAssignment.SensorLine("Window1") };
                HomeSecurityImpl alarm = new HomeSecurityImpl(internal, external);
                System.out.println("System State After Starting: " + alarm.GetState() + ".");
                alarm.Arm();
                Thread.sleep(1000);
                System.out.println("System State After Arm: " + alarm.GetState() + ".");
                alarm.Disarm("1111");
                Thread.sleep(1000);
                System.out.println("System State After Disarm: " + alarm.GetState() + ".");
                alarm.ChangePassword("1111", "1234");
                alarm.Stay();
                Thread.sleep(1000);
                System.out.println("System State After Stay: " + alarm.GetState() + ".");
                alarm.Disarm("1234");
                Thread.sleep(1000);
                System.out.println("System State After Password Disarm: " + alarm.GetState() + ".");
                System.out.println("[*] The End Of The Manual Test ...");
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e.getMessage() + "...");
            }
        }
    }

    // (*c) Client Programs - A JUnit Test Case (with assertions):
    // [!] "JUnit" was imported and used inside this project as an external module ...
    /** <hr> A simple test for the alarm system using "JUnit". <hr> **/
    @Nested class HomeSecurityTest
    {
        @Test void normalScenario() throws Exception
        {
            SensorsAssignment.SensorLine[] internal = { new SensorsAssignment.SensorLine("L1"), new SensorsAssignment.SensorLine("B2") };
            SensorsAssignment.SensorLine[] external = { new SensorsAssignment.SensorLine("D1"), new SensorsAssignment.SensorLine("W1") };
            HomeSecurityImpl alarm = new HomeSecurityImpl(internal, external);
            assertEquals(HomeSecurityADT.AlarmState.ARMED, alarm.GetState());
            alarm.Disarm("1111");
            assertEquals(HomeSecurityADT.AlarmState.DISARMED, alarm.GetState());
            alarm.Arm();
            assertEquals(HomeSecurityADT.AlarmState.ARMED, alarm.GetState());
            alarm.Disarm("1111");
            assertEquals(HomeSecurityADT.AlarmState.DISARMED, alarm.GetState());
            alarm.ChangePassword("1111", "1234");
            alarm.Stay();
            assertEquals(HomeSecurityADT.AlarmState.STAY, alarm.GetState());
            alarm.Disarm("1234");
            assertEquals(HomeSecurityADT.AlarmState.DISARMED, alarm.GetState());

        }

        @Test void internalViolationTest() throws Exception
        {
            SensorsAssignment.SensorLine[] internal = { new SensorsAssignment.SensorLine("L1"), new SensorsAssignment.SensorLine("B2") };
            SensorsAssignment.SensorLine[] external = { new SensorsAssignment.SensorLine("D1"), new SensorsAssignment.SensorLine("D2") };
            HomeSecurityImpl alarm = new HomeSecurityImpl(internal, external);
            internal[0].add(new SensorsAssignment.Sensor("cam1", false, true));
            internal[0].getSensors()[0].setViolation(true);
            alarm.Disarm("1111");
            assertThrows(HomeSecurityAssignment.InternalSensorViolationException.class, alarm::Arm);
        }

        @Test void externalViolationTest() throws Exception
        {
            SensorsAssignment.SensorLine[] internal = { new SensorsAssignment.SensorLine("L1") };
            SensorsAssignment.SensorLine[] external = { new SensorsAssignment.SensorLine("D1") };
            HomeSecurityImpl alarm = new HomeSecurityImpl(internal, external);
            external[0].add(new SensorsAssignment.Sensor("cam2", false, true));
            external[0].getSensors()[0].setViolation(true);
            alarm.Disarm("1111");
            assertThrows(HomeSecurityAssignment.ExternalSensorViolationException.class, alarm::Stay);
        }
    }

    // (d) *Sounds for: Arm | Disarm | Stay:
    // [!] The "jfugue-4.0.3.jar" file was downloaded from "(uoc-csd-hy252) e-learn" and imported in the project as a module ...
    /** <hr> (*) A class that plays different sounds for every alarm state. <hr> **/
    public static class Sound
    {
        private static final Player player = new Player();
        public static void playArm() { player.play("[0] [127]"); }
        public static void playStay() { player.play("[62]"); }
        public static void playDisarm() { player.play("[127] [0]"); }
        public static void beep() { java.awt.Toolkit.getDefaultToolkit().beep(); }
    }

    // (*e) Optional: reading configuration file with sensors for setup ...
    static final class ReadConfigurationFileAndSetUpSensors { }
}

// The End :)