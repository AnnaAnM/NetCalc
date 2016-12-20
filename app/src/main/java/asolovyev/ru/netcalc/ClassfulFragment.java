package asolovyev.ru.netcalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClassfulFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int pageNumber;

    static ClassfulFragment newInstance(){
        ClassfulFragment classfulFragment = new ClassfulFragment();
        return classfulFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.classful, container, false);
    }
}
