package com.pet.hotel.dados;

import android.content.Context;

/**
 * Created by hugo_ on 22/09/2017.
 */
public class HotelDB {

    private static HotelController controller;

    private HotelDB() {}

    public static void initialize(Context ctx) {
        controller = new HotelController(ctx);
    }

    public static HotelController getController() {
        return controller;
    }

}
