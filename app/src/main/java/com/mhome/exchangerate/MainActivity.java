package com.mhome.exchangerate;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    private ViewPager viewPager;
    private TabTitlePager tabTitleAdapter;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private Fragment[] fragments = new Fragment[2];
    private String[] tabs = {"常用汇率", "汇率换算"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Exchange Rates");
        setActionBar(toolbar);

        fragments[0] = new RatesFragment();
        fragments[1] = new ConverterFragment();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabTitleAdapter = new TabTitlePager(getSupportFragmentManager());
        viewPager.setAdapter(tabTitleAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        queue = Volley.newRequestQueue(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class TabTitlePager extends FragmentPagerAdapter {

        public TabTitlePager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            if (fragments == null) {
                return 0;
            }
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}


