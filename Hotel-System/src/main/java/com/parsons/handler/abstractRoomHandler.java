package com.parsons.handler;

import com.parsons.model.Bids;

public abstract class abstractRoomHandler {
    protected abstractRoomHandler nextHandler;

    public void setNextHandler(abstractRoomHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract String handleRequest(Bids request);
}
