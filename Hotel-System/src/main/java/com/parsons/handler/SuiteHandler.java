package com.parsons.handler;

import com.parsons.model.Bids;
import com.parsons.model.HotelRooms;

public class SuiteHandler extends abstractRoomHandler {

    @Override
    public String handleRequest(Bids request) {
        if (!HotelRooms.isSoldOut(HotelRooms.SUITE_ROOM_INDEX) && request.getBidAmount() >= HotelRooms.SUITE_ROOM_MIN_BID) {
            HotelRooms.decrementRoom(HotelRooms.SUITE_ROOM_INDEX);
            return String.format("Bid accepted for Suite Room at $%.2f", request.getBidAmount());
        } else if (nextHandler != null) {
            return nextHandler.handleRequest(request);
        }
        else {
            return "Bid rejected. Please enter a higher bid.";
        }
    }

}


