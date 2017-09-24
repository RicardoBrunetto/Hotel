package com.pet.hotel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.pet.hotel.R;
import com.pet.hotel.adaptadores.HotelAdapter;
import com.pet.hotel.dados.Hotel;
import com.pet.hotel.dados.HotelDB;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static int RESULT_UPDATE_LIST = 1;

    TabLayout tab_host_main;
    ViewPager viewPager;

    List<Hotel> hoteis;
    HotelAdapter hotelAdapter;
    ListView hoteisListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hoteisListView = (ListView) findViewById(R.id.hoteisListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ADICIONAR HOTEL
                Intent it = new Intent(getBaseContext(), HotelNovoActivity.class);
                startActivityForResult(it, 1);
            }
        });

        //deleteDatabase("dbHotel");

        // ADICIONAR HOTEL
        HotelDB.initialize(this);

        //TODO revisar fonte de dados
        hoteis = getHoteis();

        hotelAdapter = new HotelAdapter(this, hoteis);
        hoteisListView.setAdapter(hotelAdapter);
        hoteisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Hotel hotel = (Hotel) adapterView.getItemAtPosition(position);
                Intent it = new Intent(getBaseContext(), HotelDetalheActivity.class);
                it.putExtra(HotelDetalheActivity.EXTRA_HOTEL, hotel);
                startActivityForResult(it, 0);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tab_host_main = (TabLayout) findViewById(R.id.tabs);
        tab_host_main.setupWithViewPager(viewPager);
    }

    //TODO reescrever este método
    private List getHoteisTeste() {
        ArrayList arrayList = new ArrayList();

        Hotel h1 = new Hotel("Teste 1", "Rua Teste 1", 3.0f);
        Hotel h2 = new Hotel("Teste 2", "Rua Teste 2", 2.0f);
        Hotel h3 = new Hotel("Teste 3", "Rua Teste 3", 1.0f);

        arrayList.add(h1);
        arrayList.add(h2);
        arrayList.add(h3);

        return arrayList;
    }

    // ADICIONAR HOTEL
    private List<Hotel> getHoteis() {
        return HotelDB.getController().buscarHotel();
    }

    // ADICIONAR HOTEL
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_UPDATE_LIST) {
            hoteis = getHoteis();
            hotelAdapter.setItens(hoteis);
            hotelAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Novo hotel adicionado", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)
                searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hint_busca));
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
            if (!hotel.nome.toUpperCase().contains(s.toUpperCase())) {
                hoteisEncontrados.remove(hotel);
            }
        }
        hotelAdapter.setItens(hoteisEncontrados);
        hotelAdapter.notifyDataSetChanged();
    }

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
