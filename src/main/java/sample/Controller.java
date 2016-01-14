package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML //  fx:id="myButton"
    private Button button1; // Value injected by FXMLLoader

    @FXML
    private ImageView imageView;

    @FXML
    private Button setImageButton;

    @FXML
    private TextField imageUrlTextField;

    @FXML
    private TextField appIdTextField;

    @FXML
    private TextField appCodeTextField;

    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader

    @FXML // fx:id="hideMessageButton"
    private Button hideMessageButton; // Value injected by FXMLLoader

    @FXML
    private void onSetImageButtonClick(ActionEvent ae) {
        setImage();
    }

    // Handler for Button[fx:id="hideMessageButton"] onAction
    @FXML
    void onHideMessageButtonClick(ActionEvent event) {
        messageLabel.setText("");
    }

    @FXML
    private void onLoadMapButtonClick(ActionEvent ae) {
        if (appIdTextField.getText().isEmpty()) {
            messageLabel.setText("No App ID. Please set App ID in Settings tab.");
            return;
        }
        if (appCodeTextField.getText().isEmpty()) {
            messageLabel.setText("No App Code. Please set App Code in Settings tab.");
            return;
        }
        String url = String.format("https://image.maps.cit.api.here.com/mia/1.6/mapview?app_id=%s&app_code=%s", appIdTextField.getText(), appCodeTextField.getText());
        imageUrlTextField.setText(url);
        setImage();
    }

    private void setImage() {
        if (imageUrlTextField.getText().isEmpty()) {
            messageLabel.setText("No Image URL. Please set Image URL.");
            return;
        }
        imageView.setImage(new Image(imageUrlTextField.getText()));
        System.out.println("url=" + imageUrlTextField.getText());
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert button1 != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";

        ObjectMapper om = new ObjectMapper();
        try {
            Config config = om.readValue(this.getClass().getResource("/here.config.json"), Config.class);
            appIdTextField.setText(config.appId);
            appCodeTextField.setText(config.appCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
