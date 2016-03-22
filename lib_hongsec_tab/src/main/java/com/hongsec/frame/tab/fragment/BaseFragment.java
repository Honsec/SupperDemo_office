package com.hongsec.frame.tab.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * 这是底层 fragment ，所有fragment 都要继承自这个
 * Created by Hongsec on 2016-03-22.
 */
public abstract class BaseFragment extends Fragment{

    /**
     * 应用context
     */
    private Context mApplicationContext;
    private  LayoutInflater inflater;
    private ViewGroup container;

    /**
     * 内容ContentView
     */
    private View contentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplicationContext = getActivity().getApplicationContext();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);

        if(contentView == null){
            return super.onCreateView(inflater,container,savedInstanceState);
        }

        return contentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
        container = null;
        inflater = null;
    }


    /**
     * 处理在onCreateView 中得到的 bundle
     * @param savedInstanceState
     */
    protected void onCreateView(Bundle savedInstanceState) {
            //可能需要在 下级 fragment 里 实现这个函数
        //TODO  有待研究
    }


    public Context getApplicationContext() {
        return mApplicationContext;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public ViewGroup getContainer() {
        return container;
    }

    public View getContentView() {
        return contentView;
    }


    /**
     * 设置内容页
     * @param contentView
     */
    public void setContentView(View contentView) {
        this.contentView = contentView;
    }


    /**
     * 设置内容页
     * @param layoutResID
     */
    public void setContentView(int layoutResID) {
        setContentView(inflater.inflate(layoutResID,container,false));
    }



    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
        }
    }


    public View findViewById(int id){

        if(contentView !=null){
            return  contentView.findViewById(id);
        }

        return null;

    }

}
