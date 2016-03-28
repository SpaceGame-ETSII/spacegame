package com.tfg.spacegame.utils.appwarp.listeners;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import com.tfg.spacegame.utils.appwarp.WarpController;


public class RoomListener implements RoomRequestListener {

    WarpController callback;

    public RoomListener(WarpController warpController){
        callback = warpController;
    }

    @Override
    public void onSubscribeRoomDone(RoomEvent roomEvent) {
        if(roomEvent.getResult()== WarpResponseResultCode.SUCCESS){
            callback.onRoomSubscribed(roomEvent.getData().getId());
        }else{
            callback.onRoomSubscribed(null);
        }
    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent roomEvent) {
        System.out.println("UnsubscrimeRoom");
        callback.onUnSubscribeRoomDone();
    }

    @Override
    public void onJoinRoomDone(RoomEvent roomEvent) {
        callback.onJoinRoomDone(roomEvent);
    }

    @Override
    public void onLeaveRoomDone(RoomEvent roomEvent) {
        System.out.println("Leave the room");
        callback.onLeaveRoomDone();
    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        if(liveRoomInfoEvent.getResult()==WarpResponseResultCode.SUCCESS){
            callback.onGetLiveRoomInfo(liveRoomInfoEvent.getJoinedUsers());
        }else{
            callback.onGetLiveRoomInfo(null);
        }
    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent liveRoomInfoEvent) {

    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent liveRoomInfoEvent) {
    }

    @Override
    public void onLockPropertiesDone(byte b) {

    }

    @Override
    public void onUnlockPropertiesDone(byte b) {

    }

    @Override
    public void onJoinAndSubscribeRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onLeaveAndUnsubscribeRoomDone(RoomEvent roomEvent) {

    }
}
