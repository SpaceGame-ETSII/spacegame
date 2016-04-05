package com.tfg.spacegame.utils.appwarp.listeners;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.*;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;
import com.tfg.spacegame.utils.appwarp.WarpController;

public class ZoneListener implements ZoneRequestListener{

    @Override
    public void onDeleteRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetAllRoomsDone(AllRoomsEvent allRoomsEvent) {
    }

    @Override
    public void onCreateRoomDone(RoomEvent roomEvent) {
        if(roomEvent.getResult()== WarpResponseResultCode.SUCCESS){
            WarpController.roomId = roomEvent.getData().getId();
            WarpController.warpClient.joinRoom(WarpController.roomId);
        }else{
            System.out.println("Error al crear room");
        }
    }

    @Override
    public void onGetOnlineUsersDone(AllUsersEvent allUsersEvent) {

    }

    @Override
    public void onGetLiveUserInfoDone(LiveUserInfoEvent liveUserInfoEvent) {

    }

    @Override
    public void onSetCustomUserDataDone(LiveUserInfoEvent liveUserInfoEvent) {

    }

    @Override
    public void onGetMatchedRoomsDone(MatchedRoomsEvent matchedRoomsEvent) {
        if(matchedRoomsEvent.getResult() == WarpResponseResultCode.SUCCESS)
            WarpController.getRoomInRangeDone(matchedRoomsEvent.getRoomsData());
        else
            System.out.println("Error en onGetMatchedRoomsDone : "+matchedRoomsEvent.getResult());
    }

    @Override
    public void onGetAllRoomsCountDone(AllRoomsEvent allRoomsEvent) {
        System.out.println();
    }

    @Override
    public void onGetOnlineUsersCountDone(AllUsersEvent allUsersEvent) {
        System.out.println("Users Online: "+allUsersEvent.getUsersCount());
    }

    @Override
    public void onGetUserStatusDone(LiveUserInfoEvent liveUserInfoEvent) {

    }
}
