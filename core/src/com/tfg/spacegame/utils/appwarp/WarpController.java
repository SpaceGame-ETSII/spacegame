package com.tfg.spacegame.utils.appwarp;


import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.tfg.spacegame.utils.appwarp.listeners.*;

import java.util.HashMap;

public class WarpController {

    public enum MultiplayerOptions {
        QUICK_GAME, CREATE_GAME, JOIN_GAME;
    }

    // Los estados por los que va a ir pasando toda la estructura multijugador
    private static WarpListener warpListener;

    // Clase encargada de realizar la conexión al servidor y obtener las respuestas
    public static WarpClient warpClient;

    private static MultiplayerOptions option;

    // Nuestra clave de la aplicación para realizar la conexión
    private static final String apiKey = "b37a939e659fef7201e30bf7e032ebd9207e4f711de3764142505602b2b75907";
    // Nuestra clave secreta para aceptar la entrada y salid de datos al realizar la conexión
    private static final String secretKey = "eb9e252e012326bc0bc716a0fa2e09d24bbe040d5d307282e3927e70331ad0a9";
    // Número de usuarios por habitación como máximo
    private static final int MAX_NUMBER_OF_PLAYERS = 2;
    // Número de usuarios por habitación como mínimo
    private static final int MIN_NUMBER_OF_PLAYERS = 1;

    private static final String DEFAULT_ROOM_NAME = "quickPlay";

    // Número de la habitación, este número será único y sólo tendra capacidad para 2 personas
    private static String roomName;

    public static String roomId;

    // Nombre del jugador a mostrar en pantalla
    private static String userName;

    public static void load(MultiplayerOptions option){
        // Al llamar al constructor lo que hacemos es inicializar el cliente y obtener la instancia
        // al singleton
        try {
            WarpClient.initialize(apiKey, secretKey);
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        WarpController.option = option;
        // Añadimos todos los listeners necesarios
        warpClient.addConnectionRequestListener(new ConnectionListener());
        warpClient.addNotificationListener(new NotificationListener());
        warpClient.addRoomRequestListener(new RoomListener());
        warpClient.addZoneRequestListener(new ZoneListener());

        roomName = "";
        userName = "";
    }

    public static void start(String usrName, String rumName){
        roomName = rumName;
        userName = usrName;
        warpClient.connectWithUserName(userName);
    }

    public static void setWarpListener(WarpListener l){
        warpListener = l;
    }

    public static void connectDone(boolean status){
        if(status){
            warpListener.onConnectedWithServer("Connected to the server");

            warpClient.initUDP();

            if(option.equals(MultiplayerOptions.QUICK_GAME)) {
                warpClient.joinRoomInRange(MIN_NUMBER_OF_PLAYERS,MAX_NUMBER_OF_PLAYERS,false);
            }
        }else{
            warpListener.onError("Failed to connect to the server");
        }
    }

    public static void joinedToRoom(String id){
        roomId = id;
        warpClient.subscribeRoom(roomId);
    }

    public static void createNewRoom(){
        warpClient.createRoom(DEFAULT_ROOM_NAME,userName, MAX_NUMBER_OF_PLAYERS,new HashMap<String, Object>());
    }

    public static void getRoomInRangeDone(RoomData[] roomsData){
        boolean roomFound = false;
        for (RoomData aRoomsData : roomsData) {
            if (option.equals(MultiplayerOptions.QUICK_GAME) && aRoomsData.getName().equals(DEFAULT_ROOM_NAME)) {
                roomId = aRoomsData.getId();
                warpClient.joinRoom(roomId);
                roomFound = true;
                break;
            }
        }
        if(!roomFound){
            warpClient.createRoom(DEFAULT_ROOM_NAME,userName, MAX_NUMBER_OF_PLAYERS,new HashMap<String, Object>());
        }
    }

    public static void liveRoomInfoDone(String[] liveUsers){
        if(liveUsers!=null){
            if(liveUsers.length == MAX_NUMBER_OF_PLAYERS){
                warpListener.onGameStarted();
            }else{
                warpListener.onWaitOtherPlayer();
            }
        }
    }

    public static void sendGameUpdate(String message){
        warpClient.sendUpdatePeers((userName+":"+message).getBytes());
    }

    public static void messageReceived(String message){
        String userName = message.substring(0, message.indexOf(":"));
        String data = message.substring(message.indexOf(":")+1, message.length());

        if(!WarpController.userName.equals(userName)){
            warpListener.onGameUpdateReceived(data);
        }
    }

    public static void userLeftRoom(String id, String s) {
        if(roomId.equals(id) && !userName.equals(s)){
            warpListener.wonTheGame();
            leaveRoom();
        }
    }

    public static void leaveRoom(){
        warpClient.leaveRoom(roomId);
    }

    public static void disconnectDone(){
        warpListener.onDisconnectedWithServer();
    }

    private static void disposeListeners(){
        warpClient.removeConnectionRequestListener(new ConnectionListener());
        warpClient.removeZoneRequestListener(new ZoneListener());
        warpClient.removeRoomRequestListener(new RoomListener());
        warpClient.removeNotificationListener(new NotificationListener());
    }

}
