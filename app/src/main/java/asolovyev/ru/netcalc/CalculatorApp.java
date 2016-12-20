package asolovyev.ru.netcalc;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class CalculatorApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.roboto_regular))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
