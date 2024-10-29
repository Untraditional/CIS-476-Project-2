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
        Bids request = new Bids(300.0);
        String result = suiteHandler.handleRequest(request);
        assertEquals("Bid accepted for Suite Room at $" + request.getBidAmount(), result);
    }

    // Testing if Deluxe handler is accepting bids
    @Test
    void testDeluxeRoomAccepted(){
        Bids request = new Bids(200.0);
        String result = deluxeHandler.handleRequest(request);
        assertEquals("Bid accepted for a Deluxe Room at $" + request.getBidAmount(), result);
    }

    // Testing if standard room handler is accepting bids
    @Test
    void testStandardRoomAccepted(){
        Bids request = new Bids(100.0);
        String result = standardHandler.handleRequest(request);
        assertEquals("Bid accepted for a Standard Room at $" + request.getBidAmount(), result);
    }
}
