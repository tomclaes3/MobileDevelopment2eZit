package com.example.tom.mineclicker;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HighscoresFragment.SendUser{


    private static final String TAG = "MainActivity";

    private FragmentAdapter sectionStatePagerAdapter;
    private ViewPager viewPagerContent;
    private ViewPager viewPagerNavbar;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionStatePagerAdapter = new FragmentAdapter(getSupportFragmentManager());

        viewPagerContent = (ViewPager) findViewById(R.id.container);
        viewPagerNavbar = (ViewPager) findViewById(R.id.navbar);

        //setup pager
        setupViewPager(viewPagerContent);
        setupViewPagerNavbar(viewPagerNavbar);

        if (viewPagerContent.getAdapter() != null) {
            for (int i = 0; i < viewPagerContent.getAdapter().getCount(); i++) {
                Fragment f = sectionStatePagerAdapter.getItem(i);
                fragmentList.add(f);
            }
        }
    }


    private void setupViewPagerNavbar(ViewPager viewPager) {
        //adventurefragment staat als eerste zodat deze als eerst word getoont de orde maakt uit hiervoor
        // navbar = 0
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.AddFragment(new NavbarFragment(), "NavbarFragment");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPager(ViewPager viewPager) {
        //adventurefragment staat als eerste zodat deze als eerst word getoont de orde maakt uit hiervoor
        // adventure = 0 , minders = 1 , upgrades = 2 , highscore = 3 , highscore detail = 4
        sectionStatePagerAdapter.AddFragment(new AdventureFragment(), "AdventureFragment");
        sectionStatePagerAdapter.AddFragment(new MinerFragment(), "MinerFragment");
        sectionStatePagerAdapter.AddFragment(new UpgreadeFragment(), "UpgreadeFragment");
        sectionStatePagerAdapter.AddFragment(new HighscoresFragment(), "HighscoresFragment");
        sectionStatePagerAdapter.AddFragment(new HighscoreDetailFragment(), "HighscoreDetailFragment");

        viewPager.setAdapter(sectionStatePagerAdapter);
    }

    public void setViewPagerContent(int fragmentnumber) {
        viewPagerContent.setCurrentItem(fragmentnumber);
        Fragment fragment ;

        fragment = fragmentList.get(fragmentnumber);

        if (fragment instanceof ReloadTextFields) {
            ((ReloadTextFields) fragment).loadData();
        }
    }

    @Override
    public void sendData(UserModel user, int possision) {
        HighscoreDetailFragment detailFragment = (HighscoreDetailFragment) fragmentList.get(4);
        detailFragment.displayUser(user, possision);
    }
}