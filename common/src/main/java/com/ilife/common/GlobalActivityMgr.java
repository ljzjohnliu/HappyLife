package com.ilife.common;

import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GlobalActivityMgr {
    private GlobalActivityMgr() {
    }

    private enum MgrInstance {
        INSTANCE;
        private GlobalActivityMgr instance;

        MgrInstance() {
            instance = new GlobalActivityMgr();
        }

        public GlobalActivityMgr getInstance() {
            return instance;
        }
    }

    public static GlobalActivityMgr getInstance() {
        return MgrInstance.INSTANCE.getInstance();
    }

    private LinkedList<FragmentActivity> mRunningActivities = new LinkedList<>();

    private List<OnApplicationExitListener> mExitListeners = new ArrayList<>();

    public void addActivity(FragmentActivity activity) {
        Log.d("GlobalActivityMgr" , "new Activity added: " + activity.getClass().getCanonicalName());
        if (activity == null) return;

        mRunningActivities.add(activity);
    }

    public void removeActivity(FragmentActivity activity) {
        Log.d("GlobalActivityMgr" , "new Activity removed: " + activity.getClass().getCanonicalName());

        if (activity != null) {
            mRunningActivities.remove(activity);
        }

        if (mRunningActivities.size() == 0) {
            notifyExit();
        }
    }

    public void clearActivities() {
        if (mRunningActivities != null && !mRunningActivities.isEmpty()) {
            for (FragmentActivity activity : mRunningActivities) {
                if (activity != null) {
                    activity.finish();
                }
            }
            mRunningActivities.clear();
        }
        if (mRunningActivities.size() == 0) {
            notifyExit();
        }
    }

    public void finishActivityByClassName(String name) {
        if (mRunningActivities != null && !mRunningActivities.isEmpty() && !TextUtils.isEmpty(name)) {
            for (FragmentActivity activity : mRunningActivities) {
                if (activity != null
                        && activity.getClass().getSimpleName().equals(name)) {
                    activity.finish();
                }
            }
        }
    }

    public FragmentActivity getTopActivity() {
        return mRunningActivities.getLast();
    }

    public boolean hasRunningActivity() {
        return !CollectionUtils.isEmpty(mRunningActivities);
    }

    public void registerExitListener(OnApplicationExitListener listener) {
        mExitListeners.add(listener);
    }

    public void unregisterExitListener(OnApplicationExitListener listener) {
        mExitListeners.remove(listener);
    }

    private void notifyExit() {
        if (CollectionUtils.isEmpty(mExitListeners)) return;

        for (OnApplicationExitListener listener : mExitListeners) {
            listener.onExit();
        }

        mExitListeners.clear();
    }

    public interface OnApplicationExitListener {
        void onExit();
    }
}



