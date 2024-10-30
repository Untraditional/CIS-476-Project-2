package com.parsons;

import com.parsons.handler.*;
import com.parsons.model.Bids;

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JTextField bidInputField;
    private JLabel resultLabel;
    private abstractRoomHandler suiteHandler, deluxeHandler, standardHandler;

    public Main() {
        // setting up the chain of responsibility
        suiteHandler = new SuiteHandler();
        deluxeHandler = new DeluxeHandler();
        standardHandler = new StandardHandler();

        suiteHandler.setNextHandler(deluxeHandler);
        deluxeHandler.setNextHandler(standardHandler);

        // Setting up the GUI
        setTitle("Hotel Room Bidding System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Bid label
        JLabel bidLabel = new JLabel("Enter your bid:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        add(bidLabel, gbc);

        // Bid input field
        bidInputField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(bidInputField, gbc);

        // Submit button
        JButton submitButton = new JButton("Submit Bid");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

        // Result label
        resultLabel = new JLabel("Result: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(resultLabel, gbc);

        // Button click listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processBid();
            }
        });
    }

    private void processBid() {
        try {
            double bidAmount = Double.parseDouble(bidInputField.getText().trim());
            Bids request = new Bids(bidAmount);

            // Process the bid request
            String result = suiteHandler.handleRequest(request);
            resultLabel.setText("Result: " + result);
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Please enter a number");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}