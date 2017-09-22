package com.pet.hotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pet.hotel.R;
import com.pet.hotel.adaptadores.HotelAdapter;
import com.pet.hotel.controladoras.HotelController;
import com.pet.hotel.dados.Hotel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    TabLayout tab_host_main;
    ViewPager viewPager;

    List<Hotel> hoteis;
    HotelAdapter hotelAdapter;
    ListView hoteisListView;
    private HotelController hotelController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // cria toolbar de suporte
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hoteisListView = (ListView) findViewById(R.id.hoteisListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adicionar Hotel", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); /*Implementação posterior*/
            }
        });

        //TODO revisar fonte de dados
        hoteis = getHotelController().geHoteisCadastrados();

        // seta os parametros para o adapter
        hotelAdapter = new HotelAdapter(this, hoteis);
        hoteisListView.setAdapter(hotelAdapter);

        // implementa o click listener
        hoteisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Hotel hotel = (Hotel) adapterView.getItemAtPosition(position);
                Intent it = new Intent(getBaseContext(), HotelDetalheActivity.class);
                // poe o hotel no parametro da intent
                it.putExtra(HotelDetalheActivity.EXTRA_HOTEL, hotel);
                startActivityForResult(it, 0);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tab_host_main = (TabLayout) findViewById(R.id.tabs);
        tab_host_main.setupWithViewPager(viewPager);
    }

    // padrao Singleton
    private HotelController getHotelController() {
        if (hotelController == null)
            return new HotelController();
        else
            return hotelController;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)
                searchItem.getActionView();
        // implementacao dos listeners de busca
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hint_busca));
        // implementacao manual da interface de limpeza do campo de busca
        setExpandListener(searchItem);
        return true;

    }

    private void setExpandListener(MenuItem searchItem) {
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true; // para expandir a view
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                limparBusca();
                return true; // para voltar ao normal
            }
        });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        buscar(newText);
        return false;
    }


    public void buscar(String s) {
        if (s == null || s.trim().equals("")) {
            limparBusca();
            return;
        }

        List<Hotel> hoteisEncontrados = new ArrayList<Hotel>(hoteis);

        for (int i = hoteisEncontrados.size() - 1; i >= 0; i--) {
            Hotel hotel = hoteisEncontrados.get(i);
            // se contem hotel ignorando case
            if (!hotel.nome.toUpperCase().contains(s.toUpperCase())) {
                hoteisEncontrados.remove(hotel);
            }
        }
        hotelAdapter.setItens(hoteisEncontrados);
        // notifica que mudou os itens da lista
        hotelAdapter.notifyDataSetChanged();
    }

    // reseta a busca com os hoteis iniciais
    public void limparBusca() {
        hotelAdapter.setItens(hoteis);
        hotelAdapter.notifyDataSetChanged();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
