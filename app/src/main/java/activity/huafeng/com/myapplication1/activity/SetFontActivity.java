package activity.huafeng.com.myapplication1.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.norbsoft.typefacehelper.ActionBarHelper;
import com.norbsoft.typefacehelper.TypefaceCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.util.MyApplication;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;


public class SetFontActivity extends AppCompatActivity {

    private static final String STATE_SELECTED_FONT = "STATE_SELECTED_FONT";

    private static final String TYPEFACE_DEFAULT = "System default";
    private static final String TYPEFACE_ACTIONMAN = "Action man";
    private static final String TYPEFACE_ARCHRIVAL = "Arch Rival";
    private static final String TYPEFACE_JUICE = "Juice";
    private static final String TYPEFACE_UBUNTU = "Ubuntu";

    private Map<String, TypefaceCollection> mTypefaceMap;
    private Spinner spinner;
    private com.zcw.togglebutton.ToggleButton toggleBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_set_font );

        // Retrieve custom typefaces from Application subclass
        MyApplication myApp = (MyApplication) getApplication();
        mTypefaceMap = new HashMap<String, TypefaceCollection>(5);
        mTypefaceMap.put(TYPEFACE_DEFAULT, myApp.getSystemDefaultTypeface());
        mTypefaceMap.put(TYPEFACE_ACTIONMAN, myApp.getActionManTypeface());
        mTypefaceMap.put(TYPEFACE_ARCHRIVAL, myApp.getArchRivalTypeface());
        mTypefaceMap.put(TYPEFACE_JUICE, myApp.getJuiceTypeface());
        mTypefaceMap.put(TYPEFACE_UBUNTU, myApp.getUbuntuTypeface());

        final List<String> fontList = new ArrayList<String>(mTypefaceMap.keySet().size());
        fontList.addAll(mTypefaceMap.keySet());


        spinner = (Spinner) findViewById( R.id.spinner);
        spinner.setAdapter(new BaseAdapter() {

            //String[] items = getResources().getStringArray( R.array.adapter_values);

            @Override public int getCount() {
                //return items.length;
                return fontList.size();
            }
            @Override public Object getItem(int position) {
                //return items[position];
                return fontList.get(position);
            }
            @Override public long getItemId(int position) {
                //return items[position].hashCode();
                return fontList.get(position).hashCode();
            }
            @Override public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(SetFontActivity.this)
                            .inflate(android.R.layout.simple_list_item_1, parent, false);
                    typeface(convertView);
                }

                //((TextView) convertView).setText(items[position]);

                typeface(convertView, mTypefaceMap.get(fontList.get(position)));
                ((TextView) convertView).setText(fontList.get(position));
                return convertView;
            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        if (savedInstanceState != null) {
            spinner.setSelection(savedInstanceState.getInt(STATE_SELECTED_FONT));
        }

        toggleBtn =(com.zcw.togglebutton.ToggleButton) findViewById(R.id.btn_toggle) ;
        //切换开关
        toggleBtn.toggle();
      

    }


    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_FONT, spinner.getSelectedItemPosition());
    }




}
