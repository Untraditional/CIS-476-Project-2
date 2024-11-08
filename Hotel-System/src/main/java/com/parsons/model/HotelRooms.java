package com.parsons.model;

public class HotelRooms {
    // Number of rooms available
    public static final int STANDARD_ROOM_INDEX = 2;
    public static final int DELUXE_ROOM_INDEX = 1;
    public static final int SUITE_ROOM_INDEX = 0;

    // Minimum bid amounts required for each room
    public static final double STANDARD_ROOM_MIN_BID = 80.00;
    public static final double DELUXE_ROOM_MIN_BID = 150.00;
    public static final double SUITE_ROOM_MIN_BID = 280.00;

    // room availability array [suite, deluxe, standard]
    private static int[] roomsAvailable = {10, 15, 45};

    public static int[] getRoomsAvailable() {
        return roomsAvailable;
    }

    public static boolean isSoldOut(int roomTypeIndex){
        return roomsAvailable[roomTypeIndex] <= 0;
    }

    public static void decrementRoom(int roomTypeIndex) {
        if (roomsAvailable[roomTypeIndex] > 0) {
            roomsAvailable[roomTypeIndex]--;
        }
    }
}
