package com.dot.networkloading;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Keeper {
    private static Keeper instance = new Keeper();

    public enum STATE {
        STARTED,
        CLICKED,
        REVEAL,
        FINISHED;

        @Override
        public String toString() {
            switch (this){
                case STARTED:
                    return "STARTED";
                case CLICKED:
                    return "CLICKED";
                case REVEAL:
                    return "REVEAL";
                case FINISHED:
                    return "FINISHED";
                default:
                    return "";
            }
        }
    }

    private List<KeeperHolder> currentState = null;

    private Keeper(){
        currentState = new ArrayList<>();
    }

    public static Keeper getInstance(){
        return instance;
    }

    public void setAttached(int uniqueIdentifier, boolean state){
        KeeperHolder holder = getHolder(uniqueIdentifier);

        if (holder != null) {
            holder.setAttached(state);
        }
    }

    public void set(int uniqueIdentifier, STATE state){
        KeeperHolder holder = getHolder(uniqueIdentifier);

        if (holder != null) {
            holder.set(state);
        }
    }

    public boolean isAttached(int uniqueIdentifier){
        KeeperHolder holder = getHolder(uniqueIdentifier);

        if (holder != null) {
            return holder.getAttached();
        }

        return false;
    }

    public STATE get(int uniqueIdentifier){
        KeeperHolder holder = getHolder(uniqueIdentifier);

        if (holder != null) {
            return holder.getState();
        }

        return null;
    }

    private KeeperHolder getHolder(int uniqueIdentifier){
        for (KeeperHolder holder : currentState){

            if (holder.getUniqueIdentifier() == uniqueIdentifier)
                return holder;
        }

        return null;
    }

    public void add(int uniqueIdentifier){
        if (!contains(uniqueIdentifier)) {
            currentState.add(new KeeperHolder(uniqueIdentifier));
        }
    }

    private boolean contains(int uniquedentifier){
        for (KeeperHolder holder : currentState){
            if (holder.getUniqueIdentifier() == uniquedentifier){
                return true;
            }
        }

        return false;
    }

    private class KeeperHolder {
        private STATE state = STATE.STARTED;
        private boolean attached = true;
        private int uniqueIdentifier = 0;

        public KeeperHolder(int uniqueIdentifier){
            this.uniqueIdentifier = uniqueIdentifier;
            this.state = STATE.STARTED;
        }

        public void setAttached(boolean attached){
            this.attached = attached;
        }

        public void set(STATE state){
            this.state = state;
        }

        public int getUniqueIdentifier() {
            return uniqueIdentifier;
        }

        public STATE getState() {
            return state;
        }

        public boolean getAttached(){
            return attached;
        }
    }
}
