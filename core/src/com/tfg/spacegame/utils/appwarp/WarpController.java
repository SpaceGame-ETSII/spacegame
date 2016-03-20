package com.tfg.spacegame.utils.appwarp;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.tfg.spacegame.utils.appwarp.listeners.*;

import java.util.HashMap;

public class WarpController {

    public enum MultiplayerOptions {
        QUICK_GAME, CREATE_GAME, JOIN_GAME;
    }

    // Vamos a usar esta clase siempre como un SINGLETON
    private static WarpController instance;

    // Los estados por los que va a ir pasando toda la estructura multijugador
    private WarpListener warpListener;

    // Clase encargada de realizar la conexión al servidor y obtener las respuestas
    private WarpClient warpClient;

    private MultiplayerOptions option;

    // Nuestra clave de la aplicación para realizar la conexión
    private final String apiKey = "b37a939e659fef7201e30bf7e032ebd9207e4f711de3764142505602b2b75907";
    // Nuestra clave secreta para aceptar la entrada y salid de datos al realizar la conexión
    private final String secretKey = "eb9e252e012326bc0bc716a0fa2e09d24bbe040d5d307282e3927e70331ad0a9";
    // Número de usuarios por habitación como máximo
    private final int MAX_NUMBER_OF_PLAYERS = 2;
    // Número de usuarios por habitación como mínimo
    private final int MIN_NUMBER_OF_PLAYERS = 1;

    private final String DEFAULT_ROOM_NAME = "quickPlay";

    // Número de la habitación, este número será único y sólo tendra capacidad para 2 personas
    private String roomName;

    private String roomId;


    // Nombre del jugador a mostrar en pantalla
    private String userName;

    public WarpController(MultiplayerOptions option){
        // Al llamar al constructor lo que hacemos es inicializar el cliente y obtener la instancia
        // al singleton
        try {
            WarpClient.initialize(apiKey, secretKey);
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.option = option;
        // Añadimos todos los listeners necesarios
        warpClient.addConnectionRequestListener(new ConnectionListener(this));
        warpClient.addLobbyRequestListener(new LobbyListener());
        warpClient.addChatRequestListener(new ChatListener());
        warpClient.addNotificationListener(new NotificationListener(this));
        warpClient.addRoomRequestListener(new RoomListener(this));
        warpClient.addUpdateRequestListener(new UpdateListener());
        warpClient.addZoneRequestListener(new ZoneListener(this));
    }

    /**
     * Método usado para devolver el singleton de esta clase. Si no estaba creado, lo creamos
     * @return El Singleton de esta clase
     */
    public static void createInstance(MultiplayerOptions options){
        instance = new WarpController(options);
    }

    /**
     * Método usado para devolver el singleton de esta clase. Si no estaba creado, lo creamos
     * @return El Singleton de esta clase
     */
    public static WarpController getInstance(){
        return instance;
    }

    public void setListener(WarpListener listener){
        warpListener = listener;
    }

    /**
     * Método usado para iniciar la conexión al servidor
     * @param userName - Nombre de usuario con el que se conectará al servidor
     */
    public void startConnection(String userName, String roomName){
        this.roomName = roomName;
        this.userName = userName;
        warpClient.connectWithUserName(userName);
    }

    /**
     * Método usado para cuando hemos realizado con éxito la conexión con el servidor
     * En este método iniciamos el protocolo UDP para el intercambio de la información
     * además de unirnos a alguna habitación.
     * @param status - Estado del establecimiento de conexión, true si ha ido bien, false en caso contrario
     */
    public void onConnectDone(boolean status){
        if(status){
            warpListener.onConnectedWithServer("Connected to the server");
            warpClient.initUDP();
            if(option.equals(MultiplayerOptions.QUICK_GAME)) {
                warpClient.getRoomInRange(MIN_NUMBER_OF_PLAYERS,MAX_NUMBER_OF_PLAYERS);
            }else if (option.equals(MultiplayerOptions.CREATE_GAME))
                warpClient.createRoom(roomName,userName,MAX_NUMBER_OF_PLAYERS,new HashMap<String, Object>());
            else if (option.equals(MultiplayerOptions.JOIN_GAME)){
                warpClient.getRoomInRange(MIN_NUMBER_OF_PLAYERS,MAX_NUMBER_OF_PLAYERS);
            }

        }else{
            warpListener.onError("Failed to connect to the server");
        }
    }

    /**
     * Método usado para cuando nos acabamos de unir a una habitación. Lo que haremos será suscribirnos a esa habitación
     * para finalizar la unión. En caso de que no hayamos podido unirnos a alguna habitación,
     * lo que haremos será crearnos una nosotros y esperar a que alguien se conecte.
     * @param event
     */
    public void onJoinRoomDone(RoomEvent event){
        if(event.getResult() == WarpResponseResultCode.SUCCESS){
            warpClient.subscribeRoom(event.getData().getId());
        }else if (event.getResult() == WarpResponseResultCode.RESOURCE_NOT_FOUND){
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("result", "");
            warpClient.createRoom(DEFAULT_ROOM_NAME,userName, MAX_NUMBER_OF_PLAYERS,data);
        }else{
            handleError();
        }
    }

    /**
     * Método usado para cuando se ha creado una habitación. Lo que haremos aquí será realizar petición para unirnos
     * a esa habitación que hemos creado.
     * @param id - El ID con el que vamos a identificar esa nueva habitación a partir de ahora.
     */
    public void onRoomCreated(String id){
        if(id!=null){
            roomId = id;
            warpClient.joinRoom(roomId);
        }else{
            handleError();
        }
    }

    /**
     * Método usado para cuando nos hemos suscrito a una habitación y obtenemos toda la información de ella.
     * @param roomId - El ID de la habitación la cual nos hemos conectado.
     */
    public void onRoomSubscribed(String roomId){
        if(roomId!=null){
            warpListener.onJoinedToRoom("Joined to the Room: "+roomId);
            warpClient.getLiveRoomInfo(roomId);
        }else{
            warpListener.onError("Failed to join to the Room");
            disconnect();
        }
    }

    /**
     * Método usado cuando un nuevo usuario se ha conectado a nuestra habitación. Se realizará la lógica
     * que sea necesaria
     * @param roomId - El ID de la habtiación
     * @param userName - El nombre del usuario que se acaba de añadir a la habitación.
     */
    public void onUserJoinedRoom(String roomId, String userName){
        warpListener.onUserJoinedRoom("The player ("+userName+") has joined to the room");
        warpClient.getLiveRoomInfo(this.roomId);

    }


    /**
     * Método usado para obtener la información de una habitación en la que estemos conectados.
     * En caso de que no hayamos podido obtener dicha información.
     *
     * Si el número de usuarios en la habitación es ya el máximo número de jugadores, empezamos
     * el juego.
     * @param liveUsers - Usuarios activos en la habitación.
     */
    public void onGetLiveRoomInfo(String[] liveUsers) {
        if(liveUsers!=null){
            String message = "Number of players in the Room: "+liveUsers.length+"\nPlayers are: \n";
            for(int i= 0; i<=liveUsers.length-1;i++){
                message+=liveUsers[i]+"\n";
            }
            warpListener.onGetLiveRoomInfoDone(message);
            // Si el número de usuarios activos es ya el máximo de la habitación, empezamos la partida
            if(liveUsers.length == MAX_NUMBER_OF_PLAYERS){
                warpListener.onGameStarted("Start Game");
            }
        }else{
            warpListener.onError("Failed to get live room info");
            warpClient.disconnect();
        }
    }

    public void onGetMatchedRoomsDone(RoomData[] roomsData){
        boolean roomFound = false;
        for(int i=0; i< roomsData.length; i++){
            if(!roomName.equals("") && roomName.equals(roomsData[i].getName())){
                roomId = roomsData[i].getId();
                warpClient.joinRoom(roomId);
                roomFound = true;
                break;
            }else if(option.equals(MultiplayerOptions.QUICK_GAME) && roomsData[i].getName().equals(DEFAULT_ROOM_NAME)){
                roomId = roomsData[i].getId();
                warpClient.joinRoom(roomId);
                roomFound = true;
            }
        }
        if(!roomFound)
            warpClient.createRoom(DEFAULT_ROOM_NAME,userName, MAX_NUMBER_OF_PLAYERS,new HashMap<String, Object>());
    }


    /**
     * Método para actuar en caso de error -> Elimina la habitación y se desconecta del servidor.
     */
    private void handleError(){
        if(roomName !=null && roomName.length()>0){
            warpClient.deleteRoom(roomId);
        }
        disconnect();
    }

    /**
     * Envía un mensaje por UDP a la habitación en la que está conectado a través del servidor
     * @param message
     */
    public void sendGameUpdate(String message){
        warpClient.sendUpdatePeers((userName+":"+message).getBytes());
    }

    /**
     * Método para recibir (si ha habido) un mensaje nuevo por parte de otro jugador de la habitación
     * @param message
     */
    public void onGameUpdateReceived(String message){
        String userName = message.substring(0, message.indexOf(":"));
        String data = message.substring(message.indexOf(":")+1, message.length());
        if(!this.userName.equals(userName)){
            warpListener.onGameUpdateReceived(data);
        }
    }

    /**
     * Método para desconectarse del servidor correctamente
     */
    public void disconnect(){
        warpClient.removeConnectionRequestListener(new ConnectionListener(this));
        warpClient.removeChatRequestListener(new ChatListener());
        warpClient.removeZoneRequestListener(new ZoneListener(this));
        warpClient.removeRoomRequestListener(new RoomListener(this));
        warpClient.removeNotificationListener(new NotificationListener(this));
        warpClient.disconnect();
    }
}
