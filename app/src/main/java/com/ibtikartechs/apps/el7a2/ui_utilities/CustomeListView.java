package com.ibtikartechs.apps.el7a2.ui_utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by ahmedyehya on 5/14/18.
 */

public class CustomeListView extends ListView {

    private OnScrollListener onScrollListener;
    private OnDetectScrollListenerListView onDetectScrollListener;

    public CustomeListView(Context context) {
        super(context);
        onCreate(context, null, null);
    }

    public CustomeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate(context, attrs, null);
    }

    public CustomeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onCreate(context, attrs, defStyle);
    }

    @SuppressWarnings("UnusedParameters")
    private void onCreate(Context context, AttributeSet attrs, Integer defStyle) {
        setListeners();
    }

    private void setListeners() {
        super.setOnScrollListener(new OnScrollListener() {

            private int oldTop;
            private int oldFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

                if (onDetectScrollListener != null) {
                    onDetectedListScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }

            private void onDetectedListScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View view = absListView.getChildAt(0);
                int top = (view == null) ? 0 : view.getTop();

              /*   */
                if (firstVisibleItem == oldFirstVisibleItem) {
                    if (top > oldTop ) {
                        onDetectScrollListener.onUpScrolling();
                    } else if (top < oldTop) {
                        onDetectScrollListener.onDownScrolling();
                    }

                }
                else {
                    if (firstVisibleItem < oldFirstVisibleItem ) {
                        onDetectScrollListener.onUpScrolling();
                    }
                    else {
                        onDetectScrollListener.onDownScrolling();
                    }
                }


                oldTop = top;
                oldFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void setOnDetectScrollListener(OnDetectScrollListenerListView onDetectScrollListener) {
        this.onDetectScrollListener = onDetectScrollListener;
    }
}
