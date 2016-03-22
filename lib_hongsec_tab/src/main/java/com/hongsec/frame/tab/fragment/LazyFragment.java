package com.hongsec.frame.tab.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * <h1>懒加载Fragment</h1> <br>
 * 虽然是懒加载但是可以  选择是否启动懒加载 默认懒加载 <br>
 *     只有创建并显示的时候才会调用onCreateViewLazy方法<br>
 * <br>
 *
 * 懒加载的原理onCreateView的时候Fragment有可能没有显示出来。<br>
 * 但是调用到setUserVisibleHint(boolean isVisibleToUser),isVisibleToUser =
 * true的时候就说明有显示出来<br>
 * 但是要考虑onCreateView和setUserVisibleHint的先后问题所以才有了下面的代码
 *
 * 注意：<br>
 * 《1》原先的Fragment的回调方法名字后面要加个Lazy，比如Fragment的onCreateView方法， 就写成onCreateViewLazy <br>
 * 《2》使用该LazyFragment会导致多一层布局深度
 * Created by Hongsec on 2016-03-22.
 */
public abstract  class LazyFragment extends  BaseFragment{


    /**
     * 判断页面是否可以加载
     */
    private boolean isInit = false;

    /**
     * 标记是否是 懒加载
     */
    private  boolean isLazyLoad = true;

    /**
     * 传递 懒加载 开关
     */
    public static final String INTENT_BOOLEAN_LAZYLOAD= "intent_boolean_lazyload";


    private Bundle savedInstanceState;
    private FrameLayout layout;


    /**
     * 判断页面开始 相当于 onStart 的呼出与否
     */
    private boolean isStart;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle!= null){ //判断 是否 开关 懒加载
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD,isLazyLoad);
        }

        if(isLazyLoad){
            //如果使用懒加载

            if(getUserVisibleHint() && !isInit){
                //如果已加载 则返回真实
                isInit = true;
                this.savedInstanceState = savedInstanceState;
                onCreateViewLazy(savedInstanceState);

            }else{
                //如果没有加载则返回 空Framelayout 先
                layout = new FrameLayout(getApplicationContext());
                layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                super.setContentView(layout);

            }

        }else{
          //如果不使用懒加载
            isInit = true;
            onCreateViewLazy(savedInstanceState);

        }


    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser && !isInit && getContentView() != null){
            isInit = true;
            onCreateViewLazy(savedInstanceState);
            onResumeLazy();
        }

        if(isInit&& getContentView()!=null){

            if(isVisibleToUser){
                isStart = true;
                onFragmentStartLazy();
            }else {
                isStart = false;
                onFragmentStopLazy();
            }



        }

    }


    @Override
    public void onStart() {
        super.onStart();

        if(isInit && !isStart && getUserVisibleHint()){
            isStart = true;
            onFragmentStartLazy();

        }

    }


    @Override
    public void onStop() {
        super.onStop();

        if (isInit && isStart && getUserVisibleHint()){
            isStart = false;
            onFragmentStopLazy();
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            View view = getInflater().inflate(layoutResID, layout, false);
            layout.addView(view);
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void setContentView(View view) {
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            layout.addView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    @Deprecated
    public final void onResume() {
        super.onResume();
        if (isInit) {
            onResumeLazy();
        }
    }

    @Override
    @Deprecated
    public final void onPause() {
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    @Override
    @Deprecated
    public final void onDestroyView() {
        super.onDestroyView();
        if (isInit) {
            onDestroyViewLazy();
        }
        isInit = false;
    }

    public  void onFragmentStartLazy(){

    } ;

    public  void onCreateViewLazy(Bundle savedInstanceState) {};

    public  void onResumeLazy(){};

    public  void onPauseLazy(){};

    public  void onFragmentStopLazy() {};

    public  void onDestroyViewLazy(){};

}
