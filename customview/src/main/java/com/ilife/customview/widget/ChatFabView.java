package com.ilife.customview.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ilife.common.utils.SystemUIUtils;
import com.ilife.customview.R;

public class ChatFabView extends ConstraintLayout implements View.OnClickListener {

    private static final int DEFAULT_TRIGGER_MARGIN = 5;
    private static final int DEFAULT_TRIGGER_SLIDE = 10;

    private Context mContext;
    private ConstraintLayout root;
    private LinearLayout buttonContainer;

    private TextView rankingTv;
    private TextView onlineContactsTv;
    private TextView eventTv;
    private TextView setTv;

    private LinearLayout rightSideLayout;
    private ImageView rightSideEntrance;
    private ImageView rightSideBtn;

    private LinearLayout leftSideLayout;
    private ImageView leftSideEntrance;
    private ImageView leftSideBtn;

    private boolean isEnableDrag = false;
    private boolean isExpand = false;
    private boolean isStayAtLeft = false;
    private boolean isDragging = false;
    private boolean automaticAttach = true;//是否需要自动吸

    private float mLastRawX;
    private float mLastRawY;
    private int mRootMeasuredWidth = 0;
    private int mRootMeasuredHeight = 0;
    private int mRootTopY = 0;

    private View rootView;
    private Handler handler;

    private ChatFabActionListener listener;

    public ChatFabView(@NonNull Context context) {
        this(context, null);
    }

    public ChatFabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatFabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setListener(ChatFabActionListener listener) {
        this.listener = listener;
    }

    public void showEntrance(boolean isShow) {
        rightSideEntrance.setVisibility(isShow ? View.VISIBLE : View.GONE);
        leftSideEntrance.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void expand() {
        isExpand = true;

        leftSideLayout.setVisibility(View.GONE);
        rightSideLayout.setVisibility(View.GONE);
        buttonContainer.setVisibility(View.VISIBLE);
        root.setBackground(getContext().getDrawable(R.drawable.round_20dp_white_bg));

        handler.post(() -> {
            if (isStayAtLeft && rootView != null) {
                rootView.setX(0);
                invalidate();
            }
        });
    }

    public void foldToNearestBorder() {
        if (!isExpand) return;
        int screenWidth = SystemUIUtils.getScreenWidth(getContext());
        float left = getX();
        float right = getX() + getWidth();
        boolean isCloseToLeft = left < screenWidth - right;
        if (isCloseToLeft) {
            setX(0);
        } else {
            setX(screenWidth - getWidth());
        }

        foldViewIfCloseToBorder();
    }


    public void fold(boolean isFoldLeft) {
        Log.v("fab", "fold invoked");
        isExpand = false;
        isDragging = false;
        buttonContainer.setVisibility(View.GONE);
        root.setBackgroundColor(getContext().getColor(R.color.transparent));

        if (isFoldLeft) {
            leftSideLayout.setVisibility(VISIBLE);
            rightSideLayout.setVisibility(GONE);
        } else {
            leftSideLayout.setVisibility(GONE);
            rightSideLayout.setVisibility(VISIBLE);
        }
        handler.post(() -> {
            if (isStayAtLeft && rootView != null) {
                rootView.setX(0);
                invalidate();
            }
        });
    }

    private void init() {
        rootView = inflate(getContext(), R.layout.view_fab, this);
        handler = new Handler(Looper.getMainLooper());

        root = rootView.findViewById(R.id.root);
        buttonContainer = rootView.findViewById(R.id.button_container);

        rankingTv = rootView.findViewById(R.id.ranking_tv);
        rankingTv.setOnClickListener(this);
        onlineContactsTv = rootView.findViewById(R.id.online_contacts_tv);
        onlineContactsTv.setOnClickListener(this);
        eventTv = rootView.findViewById(R.id.event_tv);
        eventTv.setOnClickListener(this);
        setTv = rootView.findViewById(R.id.set_tv);
        setTv.setOnClickListener(this);

        rightSideLayout = rootView.findViewById(R.id.right_side_layout);
        rightSideEntrance = rootView.findViewById(R.id.right_side_entrance);
        rightSideEntrance.setOnClickListener(this);
        rightSideBtn = rootView.findViewById(R.id.right_side_btn);

        leftSideLayout = rootView.findViewById(R.id.left_side_layout);
        leftSideEntrance = rootView.findViewById(R.id.left_side_entrance);
        leftSideEntrance.setOnClickListener(this);
        leftSideBtn = rootView.findViewById(R.id.left_side_btn);


        fold(false);

        leftSideBtn.setOnClickListener(v -> expand());
        rightSideBtn.setOnClickListener(v -> expand());
    }

    /**
     * 解决问题：resource IDS cannot be used in a switch statement in Android library
     * android项目的library module里不能使用资源ID作为switch语句的case值。
     * 为什么呢？因为switch里的case值必须是常数，而在library module的R文件里ID的值不是final类型的，但是主module的R文件里的ID值是final类型的
     * 所以主module里可以用资源ID作为case值而library module却不能。
     * <p>
     * # 解决方案
     * 用if-else替换switch
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ranking_tv) {
            goToRankList();
        } else if (id == R.id.online_contacts_tv) {
            goToOnlineContacts();
        } else if (id == R.id.event_tv) {
            goToBigEventList();
            //case R.id.game_tv:
            //    goToGamesPage();
            //    break;
        } else if (id == R.id.set_tv) {
            gotoSetPage();
        } else if (id == R.id.left_side_entrance || id == R.id.right_side_entrance) {
            gotoManagerChat();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
//        Log.v("fab", "dispatchTouchEvent,  current  X = " + x + ", currentY = " + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isEnableDrag = false;
                mLastRawX = x;
                mLastRawY = y;
                ViewGroup mViewGroup = (ViewGroup) getParent();
                if (mViewGroup != null) {
                    int[] location = new int[2];
                    mViewGroup.getLocationInWindow(location);
                    //获取父布局的高度
                    mRootMeasuredHeight = mViewGroup.getMeasuredHeight();
                    mRootMeasuredWidth = mViewGroup.getMeasuredWidth();
                    //获取父布局顶点的坐标
                    mRootTopY = location[1];
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (x >= 0 && x <= mRootMeasuredWidth && y >= mRootTopY && y <= (mRootMeasuredHeight + mRootTopY)) {
                    float deltaX = x - mLastRawX;
                    float deltaY = y - mLastRawY;

                    if (!isDragging) {
                        double slideDistance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                        isDragging = slideDistance > DEFAULT_TRIGGER_SLIDE;
                        Log.v("fab", String.format("action move, dragging distance is %s", slideDistance));
                    }

                    float endX = getX() + deltaX;
                    float endY = getY() + deltaY;
                    // Max draggable distance on X axis
                    float maxX = mRootMeasuredWidth - getWidth();
                    // Max draggable distance on Y axis
                    float maxY = mRootMeasuredHeight - getHeight();
                    // check border
                    endX = endX < 0 ? 0 : Math.min(endX, maxX);
                    endY = endY < 0 ? 0 : Math.min(endY, maxY);

                    setX(endX);
                    setY(endY);

                    mLastRawX = x;
                    mLastRawY = y;

                    updateView(true);

                    isEnableDrag = true;
                }

                Log.v("fab", String.format("action move, isDragging = %s", isDragging));


                break;
            case MotionEvent.ACTION_UP:
                Log.v("fab", "action up");
                isEnableDrag = false;
                if (listener != null) {
                    listener.dragStateChanged(isEnableDrag);
                }

                if (automaticAttach) {
                    Log.v("fab", String.format("action up, isDragging = %s", isDragging));
                    //Intercept the up event to prevent triggering a click operation while dragging
                    if (isDragging) {
                        if (!isExpand) {
                            dragFoldedView();
                        } else {
                            foldViewIfCloseToBorder();
                        }

                        isDragging = false;
                        updateView(false);
                        return true;
                    } else {
                        isDragging = false;
                        updateView(false);
                    }
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private void updateView(boolean isMoving) {
        if (isMoving) {
            leftSideLayout.setBackground(getContext().getDrawable(R.drawable.fab_moving));
            rightSideLayout.setBackground(getContext().getDrawable(R.drawable.fab_moving));
        } else {
            leftSideLayout.setBackground(getContext().getDrawable(R.drawable.fab_left));
            rightSideLayout.setBackground(getContext().getDrawable(R.drawable.fab_right));
        }
    }

    private void foldViewIfCloseToBorder() {
        int rootViewX = (int) rootView.getX();
        int rootViewWidth = rootView.getWidth();

        if (isCloseToLeftBorder(rootViewX)) {
            isStayAtLeft = true;
            fold(isStayAtLeft);
        }

        if (isCloseToRightBorder(rootViewX, rootViewWidth)) {
            isStayAtLeft = false;
            fold(isStayAtLeft);
        }

        leftSideLayout.setBackground(getContext().getDrawable(R.drawable.fab_left));
        rightSideLayout.setBackground(getContext().getDrawable(R.drawable.fab_right));
    }

    private boolean isCloseToRightBorder(int rootViewX, int rootViewWidth) {
        return rootViewX + rootViewWidth > mRootMeasuredWidth - DEFAULT_TRIGGER_MARGIN;
    }

    private boolean isCloseToLeftBorder(int rootViewX) {
        return rootViewX < DEFAULT_TRIGGER_MARGIN;
    }

    private void dragFoldedView() {
        if (!isExpand) {
            float center = (mRootMeasuredWidth >> 1);
            //自动贴边
            if (mLastRawX <= center) {
                mLastRawX = 0;
                //向左贴边
                this.animate()
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .setDuration(50)
                        .x(mLastRawX)
                        .start();
                if (!isStayAtLeft) {
                    isStayAtLeft = true;
                }

            } else {
                mLastRawX = mRootMeasuredWidth - getWidth();
                //向右贴边
                this.animate()
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .setDuration(50)
                        .x(mLastRawX)
                        .start();
                if (isStayAtLeft) {
                    isStayAtLeft = false;
                }
            }

            fold(isStayAtLeft);
        }
    }

    private void goToBigEventList() {
        if (listener != null) {
            listener.goToBigEventList();
        }
    }

    private void goToOnlineContacts() {
        if (listener != null) {
            listener.goToOnlineContacts();
        }
    }

    private void goToRankList() {
        if (listener != null) {
            listener.goToRankList();
        }
    }

    private void goToGamesPage() {
        if (listener != null) {
            listener.goToGamesPage();
        }
    }

    private void gotoSetPage() {
        if (listener != null) {
            listener.gotoSetPage();
        }
    }

    private void gotoManagerChat() {
        if (listener != null) {
            listener.gotoManagerChat();
        }
    }

    public interface ChatFabActionListener {
        void dragStateChanged(boolean isDragEnable);

        void goToBigEventList();

        void goToOnlineContacts();

        void goToRankList();

        void goToGamesPage();

        void gotoSetPage();

        void gotoManagerChat();
    }

}
