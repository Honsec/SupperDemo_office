package serra.demo_hongsec_tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongsec.frame.tab.view.indicator.Indicator;
import com.hongsec.frame.tab.view.viewpager.SViewPager;
import com.hongsec.frame.tab.view.viewpager.adapter.IndicatorViewPager;

import serra.demo_hongsec_tab.tabmain.MainTabLayerFrag;

/**
 * Created by Hongsec on 2016-03-22.
 */
public class MainTabActivity  extends AppCompatActivity {

    private IndicatorViewPager indicatorViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tabmain);
        Indicator indicator = (Indicator) findViewById(R.id.tabmain_indicator);
        SViewPager viewPager = (SViewPager) findViewById(R.id.tabmain_viewpager);

        indicatorViewPager = new IndicatorViewPager(indicator,viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(4);

    }


    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter{

        private String[] tabNames = {"主页","消息","发现","我"};

        private int[] tabIcons = {R.drawable.maintab_1_selector,R.drawable.maintab_2_selector,R.drawable.maintab_3_selector,R.drawable.maintab_4_selector};

        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }


        //Indicator  图片及内容
        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {

            if(convertView == null){
                convertView = inflater.inflate(R.layout.tab_main,container,false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            textView.setCompoundDrawables(null, MainTabActivity.this.getResources().getDrawable(tabIcons[position]), null, null);

            return textView;
        }


        //内容页
        @Override
        public Fragment getFragmentForPage(int position) {

            MainTabLayerFrag mainTabLayerFrag = new MainTabLayerFrag();
            Bundle bundle =new Bundle();
            bundle.putString(MainTabLayerFrag.INTENT_STRING_TABNAME,tabNames[position]);
            bundle.putInt(MainTabLayerFrag.INTENT_INT_INDEX,position);
            mainTabLayerFrag.setArguments(bundle);

            return mainTabLayerFrag;
        }
    }



}
