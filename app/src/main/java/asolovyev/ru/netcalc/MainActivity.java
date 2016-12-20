package asolovyev.ru.netcalc;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity implements CidrFragment.Callbacks {

    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = CidrFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        //ViewPager не работает
        /*pager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        pager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            static final int PAGE_COUNT = 1;

            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return CidrFragment.newInstance();
                } else {
                    return ClassfulFragment.newInstance();
                }
                return CidrFragment.newInstance();
                //return new InformationFragment();
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }
        });*/
    }

    @Override
    public void onInfSelected() {
        Fragment fragment = new InformationFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
