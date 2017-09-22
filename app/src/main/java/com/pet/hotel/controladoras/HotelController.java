package com.pet.hotel.controladoras;

import com.pet.hotel.dados.Hotel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rickh on 17/09/2017.
 */

public class HotelController {

    public List<Hotel> geHoteisCadastrados(){
        ArrayList arrayList = new ArrayList();

        Hotel h1 = new Hotel("Teste 1", "Rua Teste 1", 3.0f);
        Hotel h2 = new Hotel("Teste 2", "Rua Teste 2", 2.0f);
        Hotel h3 = new Hotel("Teste 3", "Rua Teste 3", 1.0f);

        arrayList.add(h1);
        arrayList.add(h2);
        arrayList.add(h3);

        return arrayList;
    }
}
