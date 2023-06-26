package com.example.ratingcommentservice.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PreDestroy;

@CrossOrigin(origins = "*")
@Component
@Log4j2
public class SocketIOConfig {
//    @Value("${socket.host}")
//    private String SOCKET_HOST;
//    @Value("${socket.port}")
//    private int SOCKET_PORT;
//    private SocketIOServer server;
//
//    @Bean
//    public SocketIOServer socketIOServer() {
//        Configuration config = new Configuration();
//        config.setHostname(SOCKET_HOST);
//        config.setPort(SOCKET_PORT);
//        server = new SocketIOServer(config);
//        server.start();
//        server.addConnectListener(new ConnectListener() {
//            @Override
//            public void onConnect(SocketIOClient client) {
//
//                log.info("new user connected with socket " + client.getSessionId());
//            }
//        });
//
//        server.addDisconnectListener(new DisconnectListener() {
//            @Override
//            public void onDisconnect(SocketIOClient client) {
//                client.getNamespace().getAllClients().stream().forEach(data-> {
//                    log.info("user disconnected "+data.getSessionId().toString());});
//            }
//        });
//        return server;
//    }
//
//    @PreDestroy
//    public void stopSocketIOServer() {
//        this.server.stop();
//    }

}
