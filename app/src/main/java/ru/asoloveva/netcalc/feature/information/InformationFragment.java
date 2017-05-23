package ru.asoloveva.netcalc.feature.information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.asoloveva.netcalc.R;

public class InformationFragment extends Fragment {
    public static InformationFragment newInstance() {
        return new InformationFragment();
    }

    private TextView developerTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        developerTextView = (TextView) view.findViewById(R.id.developerTextView);
        developerTextView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(getString(R.string.icon_developer_prefix));
        ssb.append(" ");
        final int userStartIndex = ssb.length();
        ssb.append(getString(R.string.icon_developer_user));
        final int userEndIndex = ssb.length();
        ssb.append(" ");
        final int siteStartIndex = ssb.length();
        ssb.append(getString(R.string.icon_developer_site));
        final int siteEndIndex = ssb.length();
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.freepik.com/"));
                startActivity(browserIntent);
            }
        }, userStartIndex, userEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.flaticon.com"));
                startActivity(browserIntent);
            }
        }, siteStartIndex, siteEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        developerTextView.setText(ssb);

        return view;
    }
}
