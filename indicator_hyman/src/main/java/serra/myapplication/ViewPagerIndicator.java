package serra.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hongsec on 2016-03-22.
 */
public class ViewPagerIndicator extends LinearLayout {


    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 用来画三角形
     */
    private Path mPath;


    /**
     * 三角形的宽度
     */
    private int mTriangleWidth;

    /**
     * 三角形的高度
     */
    private int mTriangleHeight;

    /**
     * 三角形的宽度为单个tab的1/6
     */
    private static final float RADIO_TRIANGEL = 1.0f/6;

    /**
     * 三角形的最大宽度， 最大为假设有3个tab 且，小于等于 radio——triaangle 比率
     */
    private final int DIMENsION_TRIANGEL_WIDTH = (int )(getScreenWidth()/3*RADIO_TRIANGEL);


    /**
     * 初始时，三角形的偏移量
     */
    private int mInitTranslationX;

    /**
     * 手指滑动时的偏移量
     */
    private float mTranslationX;

    /**
     * 默认的tab 数量
     */
    private static final int COUNT_DEFAULT_TAB = 4;

    /**
     * tab上的title 内容
     */
    private List<String> mTitles;


    /**
     * 绑定的viewpager
     */
    public ViewPager mViewpager;


    /**
     * 默认颜色
     */
    private static final int COLOR_TEXT_NORMAL = 0x77FFFFFF;
    /**
     * 标题选中时的颜色
     */
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR= 0xFFFFFFFF;


    private int mTabVisibleCount =3;


    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttribute(context, attrs);

        initPaint();

    }


    /**
     * 画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        //锯齿
        mPaint.setAntiAlias(true);
        //颜色
        mPaint.setColor(Color.parseColor("#ffffffff"));
        //填充
        mPaint.setStyle(Paint.Style.FILL);
        // ？？
        mPaint.setPathEffect(new CornerPathEffect(3));

    }


    /**
     * 初始化 属性
     * @param context
     * @param attrs
     */
    private void initAttribute(Context context, AttributeSet attrs) {

        TypedArray typedArray =context.obtainStyledAttributes(attrs,R.styleable.ViewPagerIndicator);

        for(int i=0;i<typedArray.getIndexCount();i++){
            int id = typedArray.getIndex(i);
            switch (id){

                case R.styleable.ViewPagerIndicator_item_count:
                    //默认可见tab大小
                    mTabVisibleCount = typedArray.getInt(id,mTabVisibleCount);

                break;

            }



        }

        typedArray.recycle();

    }


    /**
     * 获取屏幕宽度
     * @return
     */
    public int getScreenWidth(){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.widthPixels;


    }

    /**
     * 绘制指示器
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {

        canvas.save();

        canvas.translate(mInitTranslationX+mInitTranslationX,getHeight()+1);
        canvas.drawPath(mPath,mPaint);

        canvas.restore();


        super.dispatchDraw(canvas);
    }


    /**
     * 初始化三角形的宽度
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriangleWidth = (int)(w/mTabVisibleCount*mTriangleWidth);

        mTriangleWidth = Math.min(DIMENsION_TRIANGEL_WIDTH,mTriangleWidth);

        //初始化 三角形
        initTriangle();

        //出事偏移量
        mInitTranslationX = getWidth()/mTabVisibleCount/2 -  mTriangleWidth/2;

    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {

        mPath = new Path();

        mTriangleHeight = (int)(mTriangleWidth/2 /Math.sqrt(2));
        mPath.moveTo(0,0);
        mPath.lineTo(mTriangleWidth,0);
        mPath.lineTo(mTriangleWidth, -mTriangleHeight);
        mPath.close();

    }

    /**
     * tab 可见数量
     * @param count
     */
    public void setVisibleCount(int count ){
        this.mTabVisibleCount = count;
    }


    /**
     * 绘制title views
     * @param datas
     */
    public void setTabItemTitles(List<String> datas){

        if(datas!=null && datas.size()>0){

            this.removeAllViews();
            this.mTitles = datas;

            for(String title :mTitles){
                //添加view
                addView(generateTextView(title));
            }

            setItemClickEvent();

        }

    }


    /**
     * 设置点击事件
     */
    private void setItemClickEvent() {

        int count = getChildCount();
        for(int i=0;i<count;i++){
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewpager.setCurrentItem(j);
                }
            });
        }

    }




    public interface  PageChangeListener{

        public void onPageScrolled(int position,float positionoffset,int positionoffsetPixels);

        public void onpageSelected(int position);

        public void onPageScrollStateChanged(int state);

    }


    private PageChangeListener pageChangeListener;

    public void setOnPageChangeListener(PageChangeListener onPageChangeListener){
        this.pageChangeListener = onPageChangeListener;
    }


    public void setViewPager(ViewPager mViewpager,int pos){

        this.mViewpager = mViewpager;
        this.mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                scroll(position, positionOffset);

                if (pageChangeListener != null) {

                    pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);

                }

            }

            @Override
            public void onPageSelected(int position) {

                resetTextViewColor();

                highLightTextView(position);

                if (pageChangeListener != null) {
                    pageChangeListener.onpageSelected(position);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (pageChangeListener != null) {
                    pageChangeListener.onPageScrollStateChanged(state);
                }

            }
        });
        mViewpager.setCurrentItem(pos);

        highLightTextView(pos);

    }

    private void scroll(int position, float positionOffset) {

        mTranslationX = getWidth() /mTabVisibleCount * (position + positionOffset);

        int tabWidth = getScreenWidth() / mTabVisibleCount;

        if(positionOffset> 0 && position >=(mTabVisibleCount - 2) && getChildCount() >mTabVisibleCount){

            if(mTabVisibleCount != 1){

                this.scrollTo((position - (mTabVisibleCount - 2))*tabWidth +(int)(tabWidth * positionOffset),0);

            }else{
                this.scrollTo(position*tabWidth +(int)(tabWidth*positionOffset),0);
            }

        }

        invalidate();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int cCount = getChildCount();

        if(cCount == 0 ) return;

        for(int i=0;i<cCount;i++){

            View view = getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.weight=0;
            layoutParams.width = getScreenWidth()/ mTabVisibleCount;

            view.setLayoutParams(layoutParams);

        }


        setItemClickEvent();

    }

    /**
     * 创建一般的textView
     * @param title
     * @return
     */
    private TextView generateTextView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width= getScreenWidth()/mTabVisibleCount;
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setLayoutParams(lp);

        return tv;
    }


    /**
     * 重置颜色
     */
    private void resetTextViewColor(){

        for(int i=0;i<getChildCount();i++){
            View view = getChildAt(i);
            if(view instanceof  TextView){

                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }

        }

    }


    /**
     * 加深被选颜色
     * @param position
     */
    private void highLightTextView(int position){
        View v = getChildAt(position);
        if(v instanceof  TextView){

            ((TextView) v).setTextColor(COLOR_TEXT_HIGHLIGHTCOLOR);
        }

    }



}
