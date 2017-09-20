package com.pet.hotel.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pet.hotel.R;
import com.pet.hotel.dados.Hotel;

import java.util.List;

/**
 * Created by Camargo on 19/09/2017.
 */

public class HotelAdapter extends BaseAdapter {

    Context ctx;
    List<Hotel> hoteis;

    public HotelAdapter(Context ctx, List<Hotel> hoteis) {
        this.ctx = ctx;
        this.hoteis = hoteis;
    }

    @Override
    public int getCount() {
        return hoteis.size();
    }

    @Override
    public Object getItem(int position) {
        return hoteis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hotel hotel = hoteis.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx)
                    .inflate(R.layout.hotel_list_adapter, null);
            holder = new ViewHolder();
            holder.txtNome = convertView.findViewById(R.id.txt_nome_hotel_adapter);
            holder.txtEndereco = convertView.findViewById(R.id.txt_endereco_hotel_adapter);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.txtNome.setText(hotel.nome);
        holder.txtEndereco.setText(hotel.endereco);

        return convertView;
    }

    public void setItens(List lista) {
        this.hoteis = lista;
    }

    static class ViewHolder {
        TextView txtNome;
        TextView txtEndereco;
    }
}
