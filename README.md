
# Hotel Room Bidding System

A Java application that simulates a hotel room bidding system using the **Chain of Responsibility** design pattern. Users can place bids for hotel rooms, and based on the bid amount and room availability, they may receive a Standard, Deluxe, or Suite room. The application includes a graphical user interface (GUI) for easy bid entry and result display.

## Features

- **GUI for User Input**: Users can enter their bid amount, submit it, and see the result in an intuitive graphical interface.
- **Room Types**: Three room types available — Standard, Deluxe, and Suite.
- **Chain of Responsibility Pattern**: Handlers determine if a room is available and bid criteria are met, passing the request along if not.
- **JUnit Testing**: Automated unit tests for all primary components to ensure reliable functionality.

## Installation and Setup

### Prerequisites

- **Java Development Kit (JDK) 17** or higher
- **Gradle** (for building and testing)
- **IntelliJ IDEA** (recommended IDE for Java and GUI development)

## Usage

1. Launch the application via Main.java.
2. Enter a bid amount in the input field.
3. Click **Submit Bid**.
4. The result of your bid will display below the button, indicating the room type assigned based on availability and bid criteria.

### Example Bid Criteria

- **Suite**: Accepts bids of $280 and above.
- **Deluxe**: Accepts bids of $150 - $280 or above $280 when Suites are sold out.
- **Standard**: Accepts bids of $80 - $150 or above $150 when Deluxe and Suite rooms are sold out.

### Test Coverage

- **Room Type Handlers**: Ensures each room handler processes bids correctly and passes requests down the chain when appropriate.
- **Bid Validation**: Checks that only valid bid amounts are accepted.
- **Room Availability Logic**: Tests to verify that rooms are correctly assigned based on availability and bid amount.

## Project Structure

- **src/main/java**: Contains main source code files, including handlers and the GUI.
- **src/test/java**: Contains JUnit test files.
- **.github/workflows**: GitHub Actions workflow for continuous integration and automated testing.

## Design Patterns Used

### Chain of Responsibility

The hotel room bidding system employs the Chain of Responsibility design pattern, where each room type (Suite, Deluxe, Standard) acts as a handler in the chain, processing bids or passing them to the next handler based on room availability and bid amount.
