package serra.demo_hongsec_tab.tabmain;

import android.os.Bundle;
import android.widget.TextView;

import com.hongsec.frame.tab.fragment.LazyFragment;

import serra.demo_hongsec_tab.R;

/**
 * Created by Hongsec on 2016-03-22.
 */
public class SecondLayerFragment extends LazyFragment {


    public static final String INTENT_STRING_TITLE = "intent_string_title"  ;
    private String fdsf="dfs";

    @Override
    public void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.frag_tabmain_sub);

        Bundle arguments = getArguments();
        if(arguments!=null){
             fdsf = arguments.getString(INTENT_STRING_TITLE);
        }

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(fdsf);

    }
}
