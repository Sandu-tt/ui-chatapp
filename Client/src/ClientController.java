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

public class ClientController implements Initializable {
    public Button btnClient;
    public TextArea txtCArea;
    public TextField txtCField;

    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String message = "";


    public void sendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtCField.getText().trim());
        dataOutputStream.flush();
        txtCField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new Thread(() -> {
            try {
                socket = new Socket("localhost", 5000);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                while (!message.equals("finished")) {
                    /*First message*/
                    message = dataInputStream.readUTF();
                    txtCArea.appendText("\n Server : " + message);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
