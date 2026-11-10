// [ Java - HY252 - A2 - Assignment 3 - RIP - csd5748 ] //
// [!] Please note: most of this code was copied from the instructions (.pdf) file and pasted on the provided template ...
// [!] Please note: this exercise runs based on exercise 2 by default. to run using exercise 4 please read the line 76 ...

package A23;

import A21.SensorsAssignment.Sensor;
import A21.SensorsAssignment.TemperatureSensor;
import A21.SensorsAssignment.LaserSensor;
import A21.SensorsAssignment.SensorLine;
import A22.HomeSecurityAssignment.HomeSecurityImpl;
import A24.AlarmThreadsAssignment.ThreadedAlarmExtension;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Toolkit;


public class AlarmGUIAssignment
{
    // Main GUI window for the app:
    static class SensorsGUIapp
    {
        public static void main(String[] args)
        {
            System.out.println("[*] GUI with 3 windows (by csd5748) ...");

            // A. CREATION OF SENSORS:
            // [*] Number of sensor lines to be created & Number of sensors per line to be created ...
            int K = 3, M = 4;
            SensorLine[] internallines = new SensorLine[K];
            SensorLine[] externallines = new SensorLine[K];

            // [TODO] Creation of internal and external sensor lines:
            // [!] I am creating KxM sensors of all types that are initially not violated and on ...
            for (int i = 0; i < K; i++)
            {
                internallines[i] = new SensorLine("Internal Line " + i);
                externallines[i] = new SensorLine("External Line " + i);

                for (int j = 0; j < M; j++)
                {
                    if ((j % 3) == 0)
                    {
                        internallines[i].add(new Sensor("I-NS-" + i + j, false, true));
                        externallines[i].add(new Sensor("E-NS-" + i + j, false, true));
                    }
                    else if ((j % 3) == 1)
                    {
                        internallines[i].add(new TemperatureSensor("I-TS-" + i + j, false, true));
                        externallines[i].add(new TemperatureSensor("E-TS-" + i + j, false, true));
                    }
                    else
                    {
                        internallines[i].add(new LaserSensor("I-LS-" + i + j, false, true, i + j - 1));
                        externallines[i].add(new LaserSensor("E-LS-" + i + j, false, true, i * j + 1));
                    }
                }
            }

            // B. PASSES THE ARRAY OF SENSORS TO TWO SensorConsoles:
            SensorsConsole internalSensorsConsole = new SensorsConsole("- All Internal Sensor Lines (csd5748)", internallines);
            SensorsConsole externalSensorsConsole = new SensorsConsole("- All External Sensor Lines (csd5748)", externallines);

            // Ci. CREATION OF HomeSecurityController (from exercise 2):
            HomeSecurityImpl homeSecurityController = new HomeSecurityImpl(internallines, externallines);
            // Cii. CREATION OF HomeSecurityController (from exercise 4): Comment out lines #75 & #107 and Un-comment lines #77 & #108 ....................................................................................................
            /* ThreadedAlarmExtension homeSecurityController = new ThreadedAlarmExtension(internallines, externallines); */

            // D. PASSES HomeSecurityController TO AlarmConsole:
            AlarmConsole alarmConsole = new AlarmConsole(homeSecurityController);

            // [*] Extra: custom window spawning (for easier testing) ...
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = screenSize.width; int screenHeight = screenSize.height;
            alarmConsole.setLocation((screenWidth - alarmConsole.getWidth()) / 2 + 50, (screenHeight - alarmConsole.getHeight()) / 2 - 50);
            internalSensorsConsole.setLocation(25, 25);
            externalSensorsConsole.setLocation(25, screenHeight - externalSensorsConsole.getHeight() - 75);
        }
    }


    // Window for the alarm console:
    static class AlarmConsole extends JFrame
    {
        // Initialization:
        HomeSecurityImpl homeSecurityController;
        JLabel labelStatus;
        JLabel labelLog;
        JButton buttonArm;
        JButton buttonStay;
        JButton buttonDisarm;
        JButton buttonChangeCode;
        JTextField inputField;

        // [TODO] Create the required buttons and make sure that when they are clicked, they call the appropriate methods in the homeSecurityController (implemented in exercise 2).
        // Constructors: first for exercise 2 and second for exercise 4 ...
        AlarmConsole(HomeSecurityImpl homeSecurityController)
        /* AlarmConsole(ThreadedAlarmExtension homeSecurityController) */
        {
            // Components:
            labelStatus = new JLabel(" System Status: " + homeSecurityController.GetState());
            labelLog = new JLabel(" Output: The system is (initially) armed!");
            inputField = new JTextField(10); inputField.setText(" Input..."); inputField.setForeground(Color.GRAY);
            buttonArm = new JButton("Arm");
            buttonStay = new JButton("Stay");
            buttonDisarm = new JButton("Disarm");
            buttonChangeCode = new JButton("Change Passcode");

            // Layout:
            setLayout(new GridLayout(0, 1, 5, 5));
            add(labelStatus);
            add(buttonArm);
            add(buttonStay);
            add(buttonDisarm);
            add(buttonChangeCode);
            add(inputField);
            add(labelLog);

            // ActionListeners:
            buttonArm.addActionListener
            (
                new ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent e)
                    {
                        try
                        {
                            homeSecurityController.Arm();
                            labelStatus.setText(" System Status: " + homeSecurityController.GetState());
                            labelLog.setText(" Output: System armed.");
                        }
                        catch (Exception ex)
                        {
                            labelLog.setText(" Error: " + ex.getMessage());
                        }
                    }
                }
            );
            buttonStay.addActionListener
            (
                new ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent e)
                    {
                        try
                        {
                            homeSecurityController.Stay();
                            labelStatus.setText(" System Status: " + homeSecurityController.GetState());
                            labelLog.setText(" Output: Stay mode activated.");
                        }
                        catch (Exception ex)
                        {
                            labelLog.setText(" Error: " + ex.getMessage());
                        }
                    }
                }
            );
            buttonDisarm.addActionListener
            (
                new ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent e)
                    {
                        String code = inputField.getText();
                        try
                        {
                            homeSecurityController.Disarm(code);
                            labelStatus.setText(" System Status: " + homeSecurityController.GetState());
                            labelLog.setText(" Output: System disarmed.");
                            inputField.setForeground(Color.GRAY);
                            inputField.setText(" Input...");
                        }
                        catch (Exception ex)
                        {
                            labelLog.setText(" Error: " + ex.getMessage());
                        }
                    }
                }
            );
            buttonChangeCode.addActionListener
            (
                new ActionListener()
                {
                    public void actionPerformed(java.awt.event.ActionEvent e)
                    {
                        String text = inputField.getText().trim();
                        if (text.equals("") || text.equals(" Input..."))
                        {
                            labelLog.setText(" Error: Please enter passwords format: <old>-<new>");
                            return;
                        }

                        String[] parts = text.split("-", 2);
                        if (parts.length != 2)
                        {
                            labelLog.setText(" Error: Please enter passwords format: <old>-<new>");
                            return;
                        }

                        String oldPwd = parts[0];
                        String newPwd = parts[1];

                        try
                        {
                            homeSecurityController.ChangePassword(oldPwd, newPwd);
                            labelLog.setText(" Output: Password changed.");
                            inputField.setForeground(Color.GRAY);
                            inputField.setText(" Input...");
                        }
                        catch (Exception ex)
                        {
                            labelLog.setText(" Error: " + ex.getMessage());
                        }
                    }
                }
            );
            inputField.addFocusListener
            (
                new java.awt.event.FocusAdapter()
                {
                    public void focusGained(java.awt.event.FocusEvent e)
                    {
                        if (inputField.getText().equals(" Input..."))
                        {
                            inputField.setText("");
                            inputField.setForeground(Color.BLACK);
                        }
                    }
                    public void focusLost(java.awt.event.FocusEvent e)
                    {
                        if (inputField.getText().isEmpty())
                        {
                            inputField.setForeground(Color.GRAY);
                            inputField.setText(" Input...");
                        }
                    }
                }
            );

            // * Other JFrame Options:
            setTitle("Alarm Console (csd5748)");
            setSize(325, 375);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

    }


    // Window for the sensors console:
    static class SensorsConsole extends JFrame
    {
        SensorLine[] sensorLines;
        void fill()
        {
            for (int i = 0; i < sensorLines.length; i++)
            {
                final SensorLine sl = sensorLines[i];
                JPanel sensorlinePanel = new JPanel(new GridLayout(0,4,5,5));
                sensorlinePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), sl.getName()));

                for (final Sensor s: sl.getSensors())
                {
                    final JButton t = new JButton(s.getId());
                    t.setToolTipText(s.toString());
                    t.setOpaque(true);
                    t.setBackground(s.getViolation() ? Color.RED : Color.GREEN);
                    t.addActionListener
                    (
                                new ActionListener()
                                {
                                    public void actionPerformed(java.awt.event.ActionEvent e)
                                    {
                                        s.setOn(true);
                                        s.setViolation(!s.getViolation());
                                        t.setLabel(s.getId());
                                        t.setBackground(s.getViolation() ? Color.RED : Color.GREEN);
                                        t.setToolTipText(s.toString());
                                    }
                                }
                    );
                    sensorlinePanel.add(t);
                    add(sensorlinePanel);
                }
            }
            setVisible(true);
        }

        // * Window Options:
        SensorsConsole(String title, SensorLine[] sensorLines)
        {
            this.sensorLines = sensorLines;
            this.setTitle(title);
            setBounds(200,100,550,350);
            setLayout(new GridLayout(0,1));
            setVisible(true);
            fill();
        }
    }

}

// The End :)