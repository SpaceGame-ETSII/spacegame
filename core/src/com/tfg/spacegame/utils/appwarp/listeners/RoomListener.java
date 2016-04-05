package com.tfg.spacegame.utils.appwarp.listeners;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import com.tfg.spacegame.utils.appwarp.WarpController;


public class RoomListener implements RoomRequestListener {

    @Override
    public void onSubscribeRoomDone(RoomEvent roomEvent) {
        if(roomEvent.getResult()== WarpResponseResultCode.SUCCESS){
            WarpController.warpClient.getLiveRoomInfo(WarpController.roomId);
        }else{
            System.out.println("Error en: onSubscribeRoomDone - " + roomEvent.getResult());
        }
    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent roomEvent) {
        if(roomEvent.getResult() == WarpResponseResultCode.SUCCESS)
            WarpController.warpClient.disconnect();
        else
            System.out.println("Error en: onJoinRoomDone - " + roomEvent.getResult());
    }

    @Override
    public void onJoinRoomDone(RoomEvent roomEvent) {
        if(roomEvent.getResult() == WarpResponseResultCode.SUCCESS)
            WarpController.joinedToRoom(roomEvent.getData().getId());
        else if(roomEvent.getResult() == WarpResponseResultCode.RESOURCE_NOT_FOUND){
            WarpController.createNewRoom();
        }
    }

    @Override
    public void onLeaveRoomDone(RoomEvent roomEvent) {
        if(roomEvent.getResult() == WarpResponseResultCode.SUCCESS)
            WarpController.warpClient.unsubscribeRoom(WarpController.roomId);
        else
            System.out.println("Error en: onLeaveRoomDone - "+roomEvent.getResult());
    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        if(liveRoomInfoEvent.getResult()==WarpResponseResultCode.SUCCESS){
            WarpController.liveRoomInfoDone(liveRoomInfoEvent.getJoinedUsers());
        }else{
            System.out.println("Error en: onJoinRoomDone - " + liveRoomInfoEvent.getResult());
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
