package asolovyev.ru.netcalc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    static final int PAGE_COUNT = 2;

    public MyFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position){
        if (position == 0){
            return CidrFragment.newInstance();
        }
        else {
            return ClassfulFragment.newInstance();
        }
    }

    @Override
    public int getCount(){
        return PAGE_COUNT;
    }
}
