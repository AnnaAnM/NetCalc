package asolovyev.ru.netcalc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;


public class CidrFragment extends Fragment{
    private IpCalc ip;
    private EditText ipEditText;
    private TextView maskTextView;
    private NumberPicker maskPicker;
    private TextView cidrTextView;
    private TextView wcMaskTextView;
    private TextView addRangeTextView;

    static CidrFragment newInstance(){
        CidrFragment cidrFragment = new CidrFragment();
        return cidrFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.cidr, null);

        ip = new IpCalc("1.0.0.0", "128.0.0.0");
        ipEditText = (EditText) view.findViewById(R.id.ipEditText);
        maskPicker = (NumberPicker) view.findViewById(R.id.maskPicker);
        maskTextView = (TextView) view.findViewById(R.id.maskTextView);
        cidrTextView = (TextView) view.findViewById(R.id.cidrTextView);
        wcMaskTextView = (TextView) view.findViewById(R.id.wcMaskTextView);
        addRangeTextView = (TextView) view.findViewById(R.id.addRangeTextView);
        final SeekBar maskSeekBar = (SeekBar) view.findViewById(R.id.maskSeekBar);

        // TODO: дописать, когда будет не лень
        final String[] masks = {"0.0.0.0", "128.0.0.0", "192.0.0.0", "224.0.0.0", "240.0.0.0",
                "248.0.0.0", "252.0.0.0", "254.0.0.0", "255.0.0.0", "255.128.0.0",
                "255.192.0.0", "255.224.0.0", "255.240.0.0", "255.248.0.0",
                "255.252.0.0", "255.254.0.0", "255.255.0.0", "255.255.128.0",
                "255.255.192.0", "255.255.224.0", "255.255.240.0", "255.255.248.0",
                "255.255.252.0", "255.255.254.0", "255.255.255.0", "255.255.255.128",
                "255.255.255.192", "255.255.255.224", "255.255.255.240",
                "255.255.255.248", "255.255.255.252", "255.255.255.254", "255.255.255.255"};
        maskPicker.setMinValue(0);
        maskPicker.setMaxValue(masks.length - 1);
        maskPicker.setDisplayedValues(masks);


        maskSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                maskTextView.setText("/" + progress);
                maskPicker.setValue(progress);

                ip.SetMask(masks[progress]);
                cidrTextView.setText(ip.IpAddress() + "/" + progress);
                wcMaskTextView.setText(ip.WildcardMask());
                addRangeTextView.setText(ip.IpAddress() + " - " + ip.BroadcastAddress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        maskPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });

        ipEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ip.SetIp(ipEditText.getText().toString());
                cidrTextView.setText(ip.IpAddress() + "/" + maskSeekBar.getProgress());
                addRangeTextView.setText(ip.IpAddress() + " - " + ip.BroadcastAddress());
            }
        });

        return view;
    }
}
