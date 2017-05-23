package ru.asoloveva.netcalc.feature.calculation;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Pattern;

import ru.asoloveva.netcalc.R;
import ru.asoloveva.netcalc.feature.Navigable;
import ru.asoloveva.netcalc.feature.information.InformationFragment;

public class CidrFragment extends android.support.v4.app.Fragment {
    public static CidrFragment newInstance() {
        return new CidrFragment();
    }

    private final String KEY_IP = "key_ip";
    private final String KEY_MASK = "key_mask";

    private final String IP_ADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private IpCalc ip;
    private TextInputEditText ipTextInputEditText;
    private TextView maskTextView;
    private Spinner maskSpinner;
    private TextView cidrTextView;
    private TextView wcMaskTextView;
    private TextView addRangeTextView;
    private SeekBar maskSeekBar;
    private TextView maxAddTextView;

    private Navigable navigable;

    private boolean isValidIP;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        navigable = (Navigable) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cidr, container, false);

        ipTextInputEditText = (TextInputEditText) view.findViewById(R.id.ipTextInputEditText);
        maskSpinner = (Spinner) view.findViewById(R.id.maskSpinner);
        maskTextView = (TextView) view.findViewById(R.id.maskTextView);
        cidrTextView = (TextView) view.findViewById(R.id.cidrTextView);
        wcMaskTextView = (TextView) view.findViewById(R.id.wcMaskTextView);
        addRangeTextView = (TextView) view.findViewById(R.id.addRangeTextView);
        maskSeekBar = (SeekBar) view.findViewById(R.id.maskSeekBar);
        maxAddTextView = (TextView) view.findViewById(R.id.maxAddTextView);

        final String[] masks = {"0.0.0.0", "128.0.0.0", "192.0.0.0", "224.0.0.0", "240.0.0.0",
                "248.0.0.0", "252.0.0.0", "254.0.0.0", "255.0.0.0", "255.128.0.0",
                "255.192.0.0", "255.224.0.0", "255.240.0.0", "255.248.0.0",
                "255.252.0.0", "255.254.0.0", "255.255.0.0", "255.255.128.0",
                "255.255.192.0", "255.255.224.0", "255.255.240.0", "255.255.248.0",
                "255.255.252.0", "255.255.254.0", "255.255.255.0", "255.255.255.128",
                "255.255.255.192", "255.255.255.224", "255.255.255.240",
                "255.255.255.248", "255.255.255.252", "255.255.255.254", "255.255.255.255"};


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, masks);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maskSpinner.setAdapter(arrayAdapter);

        ip = new IpCalc("192.168.0.1", "255.255.255.0");

        if (savedInstanceState == null) {
            maskSpinner.setSelection(24);
        } else {
            String ipAddress = savedInstanceState.getString(KEY_IP);
            int mask = savedInstanceState.getInt(KEY_MASK);
            ipTextInputEditText.setText(ipAddress);
            maskSpinner.setSelection(mask);
        }

        maskSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maskTextView.setText("/" + progress);
                maskSpinner.setSelection(progress);
                ip.setMask(masks[progress]);
                cidrTextView.setText(ip.getIpAddress() + "/" + progress);
                wcMaskTextView.setText(ip.getWildcardMask());
                addRangeTextView.setText(ip.getSubnetAddress() + " - " + ip.getBroadcastAddress());
                maxAddTextView.setText(ip.getNumberOfAdds(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        maskSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maskSeekBar.setProgress(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ipTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String ipString = charSequence.toString();
                isValidIP = Pattern.matches(IP_ADDRESS_PATTERN, ipString);
                if (!isValidIP) {
                    ipTextInputEditText.setError("Wrong format of ip address");
                }
                if (isValidIP) {
                    ip.setIp(ipTextInputEditText.getText().toString());
                    cidrTextView.setText(ip.getIpAddress() + "/" + maskSeekBar.getProgress());
                    addRangeTextView.setText(ip.getSubnetAddress() + " - " + ip.getBroadcastAddress());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    @Override
    public void onDetach(){
        navigable = null;
        super.onDetach();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_information:
                navigable.addFragment(InformationFragment.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IP, ipTextInputEditText.getText().toString());
        outState.putInt(KEY_MASK, maskSpinner.getSelectedItemPosition());
    }
}
