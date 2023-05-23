import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author : Gathsara
 * created : 5/20/2023 -- 10:43 AM
 **/

public class ServerController implements Initializable {
    public Button btnServer;
    public TextArea txtSArea;
    public TextField txtSField;

    ServerSocket serverSocket;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String message = "";

    public void sendOnAction(ActionEvent actionEvent) throws IOException {

        dataOutputStream.writeUTF(txtSField.getText().trim());
        dataOutputStream.flush();
        txtSField.clear();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(5000);
                txtSArea.appendText("server is started");
                socket = serverSocket.accept();
                txtSArea.appendText("\n Client Accepted");
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!message.equals("finish")) {
                    message = dataInputStream.readUTF();
                    txtSArea.appendText("\n Client : " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
