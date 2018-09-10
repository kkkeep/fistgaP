package com.jy.fistga.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jy.fistga.R;
import com.jy.fistga.data.News;

/*
 * created by taofu on 2018/9/6
 **/
public class NewItemBottomInfoView extends android.support.v7.widget.AppCompatTextView{
    public NewItemBottomInfoView(Context context) {
        super(context);
    }

    public NewItemBottomInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NewItemBottomInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void setNews(News news){

       /* Drawable right = ContextCompat.getDrawable(getContext(), R.drawable.icon_news_close);
        right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());*/

        Drawable [] drawables = getCompoundDrawables();

        Drawable left = null;
        if(news.getIsTop() == 1){
            if(drawables[0] != null){
                left = drawables[0];
            }else{
                left = ContextCompat.getDrawable(getContext(), R.drawable.icon_news_top);
                left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
            }

        }

        setCompoundDrawables(left,null ,drawables[2] ,null);

        StringBuilder builder  = new StringBuilder();

        builder.append(TextUtils.isEmpty(news.getOrigin()) ? getResources().getString(R.string.app_name) : news.getOrigin());
        builder.append("    " +news.getPageviews() + "阅读量");
        builder.append("    " + news.getPublishTime());
        setText(String.format(builder.toString()));
    }
}
