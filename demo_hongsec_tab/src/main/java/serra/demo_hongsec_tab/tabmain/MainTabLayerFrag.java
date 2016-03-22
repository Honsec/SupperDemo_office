package serra.demo_hongsec_tab.tabmain;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongsec.frame.tab.fragment.LazyFragment;
import com.hongsec.frame.tab.view.indicator.Indicator;
import com.hongsec.frame.tab.view.indicator.slidebar.ColorBar;
import com.hongsec.frame.tab.view.indicator.slidebar.LayoutBar;
import com.hongsec.frame.tab.view.indicator.slidebar.ScrollBar;
import com.hongsec.frame.tab.view.indicator.transition.OnTransitionTextListener;
import com.hongsec.frame.tab.view.viewpager.adapter.IndicatorViewPager;

import serra.demo_hongsec_tab.R;

/**
 * Created by Hongsec on 2016-03-22.
 */
public class MainTabLayerFrag extends LazyFragment {

    public static final String INTENT_STRING_TABNAME = "intent_string_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    private String tabName = "";
    private int index = 0;
    private IndicatorViewPager indicatorViewpager;


    @Override
    public void onFragmentStartLazy() {

    }

    @Override
    public void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.frag_tabmain);
        Resources resources = getResources();

        Bundle bundle = getArguments();
        tabName = bundle.getString(INTENT_STRING_TABNAME);
        index = bundle.getInt(INTENT_INT_INDEX);

        if (getContentView() == null) {
            return;
        }

        ViewPager viewpager = (ViewPager) findViewById(R.id.fragment_tabmain_viewpager);
        Indicator indicator = (Indicator) findViewById(R.id.fragment_tabmain_indicator);

        switch (index) {

            case 0:
                indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.RED, 5));
                break;

            case 1:
                indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.RED, 0, ScrollBar.Gravity.CENTENT_BACKGROUND));
                break;

            case 2:
                indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.RED, 5, ScrollBar.Gravity.TOP));
                break;
            case 3:
                indicator.setScrollBar(new LayoutBar(getApplicationContext(), R.layout.tab_main, ScrollBar.Gravity.CENTENT_BACKGROUND));

        }


        float unSelectSize = 16;
        float selectSize = unSelectSize*1.2f;

        int selectColor= resources.getColor(R.color.tab_top2_text_2);
        int unselectColor= resources.getColor(R.color.tab_top2_text_1);
        //变化监听
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unselectColor).setSize(selectSize, unSelectSize));

        viewpager.setOffscreenPageLimit(4);

        indicatorViewpager = new IndicatorViewPager(indicator,viewpager);

        indicatorViewpager.setAdapter(new MyAdapter(getChildFragmentManager()));
    }



    private class MyAdapter extends  IndicatorViewPager.IndicatorFragmentPagerAdapter{


        private LayoutInflater inflater = null;
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if(convertView == null){
                convertView = inflater.inflate(R.layout.tab_main,container,false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabName+ " "+ position);

            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            SecondLayerFragment secondLayerFragment = new SecondLayerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SecondLayerFragment.INTENT_STRING_TITLE,position+"");
            secondLayerFragment.setArguments(bundle);
            return secondLayerFragment;
        }
    }

}
