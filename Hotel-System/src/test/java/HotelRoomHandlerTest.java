import com.parsons.handler.DeluxeHandler;
import com.parsons.handler.StandardHandler;
import com.parsons.handler.SuiteHandler;
import com.parsons.model.Bids;
import com.parsons.model.HotelRooms;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class HotelRoomHandlerTest {
    private SuiteHandler suiteHandler;
    private DeluxeHandler deluxeHandler;
    private StandardHandler standardHandler;

    @BeforeEach
    void setUp() {
        int[] roomsAvailable = HotelRooms.getRoomsAvailable();
        roomsAvailable[HotelRooms.SUITE_ROOM_INDEX] = 10;
        roomsAvailable[HotelRooms.DELUXE_ROOM_INDEX] = 15;
        roomsAvailable[HotelRooms.STANDARD_ROOM_INDEX] = 45;

        // Setting up chain of responsibility
        suiteHandler = new SuiteHandler();
        deluxeHandler = new DeluxeHandler();
        standardHandler = new StandardHandler();

        suiteHandler.setNextHandler(deluxeHandler);
        deluxeHandler.setNextHandler(standardHandler);
    }

    // Testing if Suite handler is accepting bids
    @Test
    void testSuiteRoomAccepted(){
        Bids request = new Bids(300.00);
        String result = suiteHandler.handleRequest(request);
        assertEquals(String.format("Bid accepted for Suite Room at $%.2f", request.getBidAmount()), result);
    }

    // Testing if Deluxe handler is accepting bids
    @Test
    void testDeluxeRoomAccepted(){
        Bids request = new Bids(200.00);
        String result = suiteHandler.handleRequest(request);
        assertEquals(String.format("Bid accepted for a Deluxe Room at $%.2f", request.getBidAmount()), result);
    }

    // Testing if standard room handler is accepting bids
    @Test
    void testStandardRoomAccepted(){
        Bids request = new Bids(100.00);
        String result = suiteHandler.handleRequest(request);
        assertEquals(String.format("Bid accepted for a Standard Room at $%.2f", request.getBidAmount()), result);
    }

    // Test for all rooms are sold out
    @Test
    void testAllRoomsSoldOut(){
        HotelRooms.getRoomsAvailable()[HotelRooms.SUITE_ROOM_INDEX] = 0;
        HotelRooms.getRoomsAvailable()[HotelRooms.DELUXE_ROOM_INDEX] = 0;
        HotelRooms.getRoomsAvailable()[HotelRooms.STANDARD_ROOM_INDEX] = 0;

        Bids request = new Bids(300.00);
        String result = suiteHandler.handleRequest(request);
        assertEquals("All rooms are sold out" , result);
    }

    // Test for not meeting the minimum cost of a standard room of $80.0
    @Test
    void testBidRejected(){
        Bids request = new Bids(50.00);
        String result = suiteHandler.handleRequest(request);
        assertEquals(String.format("Bid too low for a Standard Room at $%.2f", request.getBidAmount()), result);
    }

    // Need a test for when Suites are sold out and the user ends up with a deluxe room for the price of a suite
    @Test
    void testSuitesSoldOutBookingDeluxeAtSuitePrice(){
        HotelRooms.getRoomsAvailable()[HotelRooms.SUITE_ROOM_INDEX] = 0;

        Bids request = new Bids(300.00);
        String result = suiteHandler.handleRequest(request);
        assertEquals(String.format("Bid accepted for a Deluxe Room at $%.2f", request.getBidAmount()), result);
    }

    // Need a test for when the suite is sold out and the deluxe rooms are sold out, the user ends up with a standard room for suite price
    @Test
    void testSuitesSoldOutAndDeluxeSoldOutBookingStandardAtSuitePrice(){
        HotelRooms.getRoomsAvailable()[HotelRooms.SUITE_ROOM_INDEX] = 0;
        HotelRooms.getRoomsAvailable()[HotelRooms.DELUXE_ROOM_INDEX] = 0;

        Bids request = new Bids(300.00);
        String result = suiteHandler.handleRequest(request);
        assertEquals(String.format("Bid accepted for a Standard Room at $%.2f", request.getBidAmount()), result);
    }

    // Testing if Deluxe is sold out, but suite isn't yet. So in theory they shouldn't be able to buy one for $200.00
    // In the Word document: Standard room accepts price ranges $80 - $150 or above $150 when Deluxe and Suite are both sold out
    @Test
    void testDeluxeSoldOutButSuiteIsNotSoldOutBuyingStandardRoomAtDeluxePrice(){
        HotelRooms.getRoomsAvailable()[HotelRooms.DELUXE_ROOM_INDEX] = 0;

        Bids request = new Bids(200.00);
        String result = suiteHandler.handleRequest(request);
        assertEquals("All rooms are sold out", result);
    }

    // Testing for purchasing 11 suites at $300.0 each, where the 11th purchase will be a Deluxe room
    @Test
    void testEleventhSuiteBidGoesToDeluxeRoom(){
        Bids request = new Bids(300.00);
        String result = "";

        // loop for buying 10 suites
        for (int i = 0; i < 10; i++){
            result = suiteHandler.handleRequest(request);
            assertEquals(String.format("Bid accepted for Suite Room at $%.2f", request.getBidAmount()), result);
        }

        // 11th bid should be a deluxe room as suites are sold out
        result = suiteHandler.handleRequest(request);
        assertEquals(String.format("Bid accepted for a Deluxe Room at $%.2f", request.getBidAmount()), result);
    }
}
