package com.example.chatApp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextArea txtChat;

    @FXML
    private Button btnSend;

    @FXML
    private TextField txtMessage;

    String message = "";
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public void initialize(){
        new Thread(()->{
            try {
                socket = new Socket("localhost",4002);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (!message.equals("bye")){
                    message = dataInputStream.readUTF();
                    txtChat.appendText("\nServer: "+message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void btnSendOnAction(javafx.event.ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtMessage.getText().trim());
        dataOutputStream.flush();
        String message = txtMessage.getText();
        txtChat.appendText("\nClient: "+message);
    }
}
