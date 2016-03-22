package serra.demo_hongsec_tab;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongsec.frame.tab.view.indicator.Indicator;
import com.hongsec.frame.tab.view.viewpager.adapter.IndicatorViewPager;

/**
 * Created by Hongsec on 2016-03-22.
 */
public class GuideActivity extends AppCompatActivity {


    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide);

        ViewPager viewpager = (ViewPager) findViewById(R.id.guide_viewpager);
        Indicator indicator = (Indicator) findViewById(R.id.guide_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator,viewpager);

        inflate = LayoutInflater.from(getApplicationContext());

        indicatorViewPager.setAdapter(adapter);

    }


    //可以用来做 新闻导航页 。 简单加入图片的那种类型
    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter(){

        private int[] images = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4};


        @Override
        public int getCount() {
            return images.length;
        }

        //Indicator 的 图片
        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {

            if(convertView ==null){
                convertView = inflate.inflate(R.layout.tab_guide,container,false);
            }

            return convertView;
        }

        //内容图片
        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {

            if(convertView == null){
                convertView = new View(getApplicationContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            convertView.setBackgroundResource(images[position]);

            return convertView;
        }
    };



}
