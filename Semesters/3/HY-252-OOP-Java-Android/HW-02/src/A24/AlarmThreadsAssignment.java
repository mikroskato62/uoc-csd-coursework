// [ Java - HY252 - A2 - Assignment 4 - RIP - csd5748 ] //

package A24;
import A21.SensorsAssignment;
import A22.HomeSecurityAssignment;
import A23.AlarmGUIAssignment;

public class AlarmThreadsAssignment
{
    // Alarm extension with check every 1 second:
    public static class ThreadedAlarmExtension implements HomeSecurityAssignment.HomeSecurityADT
    {
        // (*) Variables; Constructor; Getters; Methods; Threads;
        private HomeSecurityAssignment.HomeSecurityADT.AlarmState state;
        private String password;
        private final SensorsAssignment.SensorLine[] internals;
        private final SensorsAssignment.SensorLine[] externals;
        private Thread pollingThread;
        private Thread buzzerThread;

        public ThreadedAlarmExtension(SensorsAssignment.SensorLine[] inside, SensorsAssignment.SensorLine[] outside)
        {
            this.internals = inside; this.externals = outside;
            this.state = HomeSecurityAssignment.HomeSecurityADT.AlarmState.ARMED;
            this.password = "1111";
        }

        @Override public HomeSecurityAssignment.HomeSecurityADT.AlarmState GetState() { return state; }
        @Override public String GetPassword() { return password; }

        @Override public void Arm() throws HomeSecurityAssignment.InternalSensorViolationException, HomeSecurityAssignment.ExternalSensorViolationException
        {
            for (SensorsAssignment.SensorLine s : internals) if (s.isViolated()) throw new HomeSecurityAssignment.InternalSensorViolationException("At least one internal sensor violated!");
            for (SensorsAssignment.SensorLine s : externals) if (s.isViolated()) throw new HomeSecurityAssignment.ExternalSensorViolationException("At least one external sensor violated!");
            state = HomeSecurityAssignment.HomeSecurityADT.AlarmState.ARMED;
            HomeSecurityAssignment.Sound.playArm();
            startPolling();
        }
        @Override public void Stay() throws HomeSecurityAssignment.ExternalSensorViolationException, HomeSecurityAssignment.NotDisarmedException
        {
            if (state != HomeSecurityAssignment.HomeSecurityADT.AlarmState.DISARMED) throw new HomeSecurityAssignment.NotDisarmedException("System must be disarmed to enter stay mode!");
            for (SensorsAssignment.SensorLine s : externals) if (s.isViolated()) throw new HomeSecurityAssignment.ExternalSensorViolationException("At least one external sensor is violated!");
            state = HomeSecurityAssignment.HomeSecurityADT.AlarmState.STAY;
            HomeSecurityAssignment.Sound.playStay();
            startPolling();
        }
        @Override public void Disarm(String code) throws HomeSecurityAssignment.WrongPasswordException
        {
            if (!password.equals(code)) throw new HomeSecurityAssignment.WrongPasswordException("The password is wrong!");
            state = HomeSecurityAssignment.HomeSecurityADT.AlarmState.DISARMED;
            HomeSecurityAssignment.Sound.playDisarm();
            stopPolling();
            stopBuzzing();
        }
        @Override public void ChangePassword(String oldcode, String newcode) throws HomeSecurityAssignment.WrongPasswordException, HomeSecurityAssignment.NotDisarmedException
        {
            if (state != HomeSecurityAssignment.HomeSecurityADT.AlarmState.DISARMED) throw new HomeSecurityAssignment.NotDisarmedException("System must be disarmed to change password!");
            if (!password.equals(oldcode)) throw new HomeSecurityAssignment.WrongPasswordException("The old password is incorrect!");
            password = newcode;
        }

        private void startPolling()
        {
            if (pollingThread == null || !pollingThread.isAlive())
            {
                pollingThread = new Thread(new AlarmPollingTask(this));
                pollingThread.start();
            }
        }
        private void stopPolling()
        {
            if (pollingThread != null)
            {
                pollingThread.interrupt();
                pollingThread = null;
            }
        }
        private void startBuzzing()
        {
            if (buzzerThread == null || !buzzerThread.isAlive())
            {
                buzzerThread = new Thread(new AlarmBuzzer());
                buzzerThread.start();
            }
        }
        private void stopBuzzing()
        {
            if (buzzerThread != null)
            {
                buzzerThread.interrupt();
                buzzerThread = null;
            }
        }
    }

    // Thread that performs periodic violation checks:
    static class AlarmPollingTask implements Runnable
    {
        // (*) Variable; Constructor; Run:
        private ThreadedAlarmExtension alarm;

        public AlarmPollingTask(ThreadedAlarmExtension alarm) { this.alarm = alarm; }

        @Override public void run()
        {
            while (true)
            {
                try { Thread.sleep(1000); }
                catch (InterruptedException e) { return; }

                boolean violation = false;
                if (alarm.GetState() == HomeSecurityAssignment.HomeSecurityADT.AlarmState.ARMED)
                {
                    for (SensorsAssignment.SensorLine s : alarm.internals) if (s.isViolated()) { violation = true; break; }
                    if (!violation) for (SensorsAssignment.SensorLine s : alarm.externals) if (s.isViolated()) { violation = true; break; }
                }
                else if (alarm.GetState() == HomeSecurityAssignment.HomeSecurityADT.AlarmState.STAY)
                {
                    for (SensorsAssignment.SensorLine s : alarm.externals) if (s.isViolated()) { violation = true; break; }
                }
                else
                {
                    return;
                }
                if (violation) { alarm.startBuzzing(); }
            }
        }
    }

    // Support for alarm "ringing":
    static class AlarmBuzzer implements Runnable
    {
        // (*) Run:
        @Override public void run()
        {
            try
            {
                while (true)
                {
                    HomeSecurityAssignment.Sound.beep();
                    Thread.sleep(999);
                }
            }
            catch (InterruptedException e)
            {
                return;
            }
        }
    }

    // Test scenario: Arm -> after 3s violation -> after 4s Disarm:
    static class AlarmThreadTest
    {
        public static void main(String[] args) throws InterruptedException
        {
            System.out.println("[*] Starting Arm Test ...");

            SensorsAssignment.SensorLine[] internals = { new SensorsAssignment.SensorLine("Internal") };
            SensorsAssignment.SensorLine[] externals = { new SensorsAssignment.SensorLine("External") };
            SensorsAssignment.Sensor sensor = new SensorsAssignment.Sensor("Door", false, true);
            externals[0].add(sensor);
            ThreadedAlarmExtension alarm = new ThreadedAlarmExtension(internals, externals);

            try { alarm.Disarm("1111"); }
            catch (Exception e) { System.out.println("[Error] Initial Disarm: " + e.getMessage() + " ..."); }

            try
            {
                alarm.Arm();
                System.out.println("System Armed.");
                Thread.sleep(3000);
                sensor.setViolation(true);
                System.out.println("Violation Triggered.");
                Thread.sleep(4000);
                alarm.Disarm("1111");
                System.out.println("System Disarmed.");
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e.getMessage() + " ...");
            }

            System.out.println("[*] End of Arm Test ...");
        }
    }

    // Test scenario: Stay -> after 3s external violation -> after 4s Disarm:
    static class StayThreadTest
    {
        public static void main(String[] args) throws InterruptedException
        {
            System.out.println("[*] Starting Stay Test ...");

            SensorsAssignment.SensorLine[] internals = { new SensorsAssignment.SensorLine("Internal") };
            SensorsAssignment.SensorLine[] externals = { new SensorsAssignment.SensorLine("External") };
            SensorsAssignment.Sensor sensor = new SensorsAssignment.Sensor("Window", false, true);
            externals[0].add(sensor);
            ThreadedAlarmExtension alarm = new ThreadedAlarmExtension(internals, externals);

            try { alarm.Disarm("1111"); }
            catch (Exception e) { System.out.println("[Error] Initial Disarm: " + e.getMessage() + " ..."); }

            try
            {
                alarm.Stay();
                System.out.println("System in Stay Mode.");
                Thread.sleep(3000);
                sensor.setViolation(true);
                System.out.println("External Violation Triggered.");
                Thread.sleep(4000);
                alarm.Disarm("1111");
                System.out.println("System Disarmed.");
            }
            catch (Exception e)
            {
                System.out.println("Error: " + e.getMessage() + " ...");
            }

            System.out.println("[*] End of Stay Test ...");
        }
    }
}

// The End :)