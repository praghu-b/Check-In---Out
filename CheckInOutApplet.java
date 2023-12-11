import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CheckInOutApplet extends JApplet {
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JButton checkInButton;
    private JButton checkOutButton;

    private Map<String, LocalDateTime> checkInRecords;

    public void init() {
        checkInRecords = new HashMap<>();

        setLayout(new FlowLayout());

        nameLabel = new JLabel("Name:");
        nameTextField = new JTextField(10);
        checkInButton = new JButton("Check-In");
        checkOutButton = new JButton("Check-Out");

        add(nameLabel);
        add(nameTextField);
        add(checkInButton);
        add(checkOutButton);

        checkInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkIn();
            }
        });

        checkOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkOut();
            } 
        });
    }

    private void checkIn() {
        String name = nameTextField.getText();
        LocalDateTime checkInTime = LocalDateTime.now();
        checkInRecords.put(name, checkInTime);
        JOptionPane.showMessageDialog(null, "Check-in successful at " + checkInTime);
    }

    private void checkOut() {
    String name = nameTextField.getText();
    LocalDateTime checkOutTime = LocalDateTime.now();
    LocalDateTime checkInTime = checkInRecords.get(name);

    if (checkInTime == null) {
        JOptionPane.showMessageDialog(null, "No check-in record found for " + name);
        return;
    }

    Duration duration = Duration.between(checkInTime, checkOutTime);
    long hours = duration.toHours();
    long minutes = duration.toMinutes() % 60;
    long seconds = duration.getSeconds() % 60;

    // Load the image from a file
    ImageIcon imageIcon = new ImageIcon("qrcode.png");

    // Create a panel to hold the image and text
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    // Add the image to the panel
    JLabel imageLabel = new JLabel(imageIcon);
    panel.add(imageLabel, BorderLayout.CENTER);

    // Create a text message
    String message = "Scan QR code to get E-ticket" + "\n" + "Check-out successful at " + checkOutTime + "\n" +
            "Total duration: " + hours + " hours " + minutes + " minutes " + seconds + " seconds";

    // Add the text message to the panel
    JTextArea textArea = new JTextArea(message);
    textArea.setEditable(false);
    panel.add(textArea, BorderLayout.SOUTH);

    // Display the panel in a dialog
    JOptionPane.showMessageDialog(null, panel);

    // Remove the check-in record
    checkInRecords.remove(name);
}


    public static void main(String[] args) {
        JFrame frame = new JFrame("Check-In/Check-Out Application");
        CheckInOutApplet applet = new CheckInOutApplet();

        applet.init();

        // Set the preferred size of the applet
        applet.setPreferredSize(new Dimension(300, 150));

        // Add the applet to the frame
        frame.getContentPane().add(applet);

        // Configure the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
