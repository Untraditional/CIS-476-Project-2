package com.parsons.handler;

import com.parsons.model.Bids;
import com.parsons.model.HotelRooms;

import static com.parsons.model.HotelRooms.*;

public class StandardHandler extends abstractRoomHandler{

    @Override
    public String handleRequest(Bids request) {
        if (!HotelRooms.isSoldOut(STANDARD_ROOM_INDEX) &&
                ((request.getBidAmount() >= HotelRooms.STANDARD_ROOM_MIN_BID && request.getBidAmount() < HotelRooms.DELUXE_ROOM_MIN_BID) ||
                (request.getBidAmount() >= DELUXE_ROOM_MIN_BID && HotelRooms.isSoldOut(DELUXE_ROOM_INDEX) && HotelRooms.isSoldOut(SUITE_ROOM_INDEX)))
        ) {
            HotelRooms.decrementRoom(STANDARD_ROOM_INDEX);
            return String.format("Bid accepted for a Standard Room at $%.2f", request.getBidAmount());
        } else if (request.getBidAmount() < STANDARD_ROOM_MIN_BID){
            return String.format("Bid too low for a Standard Room at $%.2f", request.getBidAmount());
        } else {
            return "All rooms are sold out";
        }
    }
}
