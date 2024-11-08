package com.parsons.handler;

import com.parsons.model.Bids;
import com.parsons.model.HotelRooms;

import static com.parsons.model.HotelRooms.*;

public class DeluxeHandler extends abstractRoomHandler {

    @Override
    public String handleRequest(Bids request) {
        if (!HotelRooms.isSoldOut(DELUXE_ROOM_INDEX) &&
                ((request.getBidAmount() >= DELUXE_ROOM_MIN_BID && request.getBidAmount() < SUITE_ROOM_MIN_BID) ||
                (request.getBidAmount() >= SUITE_ROOM_MIN_BID && HotelRooms.isSoldOut(SUITE_ROOM_INDEX)))) {

            HotelRooms.decrementRoom(DELUXE_ROOM_INDEX);
            return String.format("Bid accepted for a Deluxe Room at $%.2f", request.getBidAmount());
        } else if (nextHandler != null) {
            return nextHandler.handleRequest(request);
        } else {
            return "Bid rejected. Please enter a higher bid.";
        }
    }
}
