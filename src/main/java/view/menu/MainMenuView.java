package view.menu;

import java.net.URL;
import java.util.ResourceBundle;


import controller.menu.SceneLoader;
import controller.sound.SoundController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.utilities.PersonalFonts;
import view.utilities.PersonalImages;
import view.utilities.PersonalSounds;
import view.utilities.PersonalStyle;
import view.utilities.PersonalViews;

public class MainMenuView implements Initializable {

    @FXML
    private AnchorPane window;

    @FXML
    private BorderPane panel;

    @FXML
    private HBox titleContainer;

    @FXML
    private HBox coinContainer;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblCoins;

    @FXML
    private VBox buttonContainer;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnTutorial;

    @FXML
    private Button btnRanking;

    private static final int SIZEFONT = 42;
    private static final int SIZEFONTCOIN = 24;
    private static final int SIZEWIDTH = 40;
    private static final int SIZEHEIGHT = 40;
    private static final int CENTER_POSITION = 2;


    /**
     *
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.resizable();
        this.loadFont();
        this.loadButtonImage();
        this.loadAnimation();
        this.loadListener();
        this.loadMusic();
    }

    private void loadListener() {
        //Play Listener
        this.btnPlay.setOnAction(event -> {
            SceneLoader.switchScene((Stage) ((Node) event.getSource()).getScene().getWindow(), 
                                     PersonalViews.SCENE_CHARACTER_MENU.getURL(), 
                                     PersonalViews.SCENE_CHARACTER_MENU.getTitleScene(), 
                                     this.window.getWidth(), 
                                     this.window.getHeight(),
                                     PersonalStyle.DEFAULT_STYLE.getStylePath());
            //Play Button CLick Sound
            SoundController.playSoundFx(PersonalSounds.TICK_BUTTON.getURL().getPath());
        });

        // Settings Listener
        this.btnSettings.setOnAction(event -> {
            SceneLoader.switchScene((Stage) ((Node) event.getSource()).getScene().getWindow(), 
                                     PersonalViews.SCENE_SETTINGS.getURL(), 
                                     PersonalViews.SCENE_SETTINGS.getTitleScene(), 
                                     this.window.getWidth(), 
                                     this.window.getHeight(),
                                     PersonalStyle.DEFAULT_STYLE.getStylePath());
            //Play Button CLick Sound
            SoundController.playSoundFx(PersonalSounds.TICK_BUTTON.getURL().getPath());
        });

        // Tutorial Listener
        this.btnTutorial.setOnAction(event -> {
            SceneLoader.switchScene((Stage) ((Node) event.getSource()).getScene().getWindow(), 
                                    PersonalViews.SCENE_TUTORIAL.getURL(), 
                                    PersonalViews.SCENE_TUTORIAL.getTitleScene(), 
                                    this.window.getWidth(), 
                                    this.window.getHeight(),
                                    PersonalStyle.DEFAULT_STYLE.getStylePath());
            //Play Button CLick Sound
            SoundController.playSoundFx(PersonalSounds.TICK_BUTTON.getURL().getPath());
        });

        // Ranking Listener
        this.btnRanking.setOnAction(event -> {
            SceneLoader.switchScene((Stage) ((Node) event.getSource()).getScene().getWindow(), 
                                    PersonalViews.SCENE_RANKING.getURL(), 
                                    PersonalViews.SCENE_RANKING.getTitleScene(), 
                                    this.window.getWidth(), 
                                    this.window.getHeight(),
                                    PersonalStyle.DEFAULT_STYLE.getStylePath());
            //Play Button CLick Sound
            SoundController.playSoundFx(PersonalSounds.TICK_BUTTON.getURL().getPath());
        });
    }

    private void loadAnimation() {
        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.00), evt -> this.lblCoins.setVisible(false)),
                new KeyFrame(Duration.seconds(0.50), evt -> this.lblCoins.setVisible(true)));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
    }

    private void loadFont() {
        this.lblTitle
                .setFont(Font.loadFont(PersonalFonts.FONT_TITLE.getResourceAsStream(), SIZEFONT));
        this.btnPlay
                .setFont(Font.loadFont(PersonalFonts.FONT_BUTTON.getResourceAsStream(), SIZEFONT));
        this.btnSettings
                .setFont(Font.loadFont(PersonalFonts.FONT_BUTTON.getResourceAsStream(), SIZEFONT));
        this.btnTutorial
                .setFont(Font.loadFont(PersonalFonts.FONT_BUTTON.getResourceAsStream(), SIZEFONT));
        this.btnRanking
                .setFont(Font.loadFont(PersonalFonts.FONT_BUTTON.getResourceAsStream(), SIZEFONT));
        this.lblCoins
                .setFont(Font.loadFont(PersonalFonts.FONT_BUTTON.getResourceAsStream(), SIZEFONTCOIN));
    }

    private void loadButtonImage() {

        // ButtonPlay
        final ImageView imgPlay = new ImageView(
                new Image(PersonalImages.PLAY_IMG.getResourceAsStream()));
        imgPlay.setFitWidth(SIZEWIDTH);
        imgPlay.setFitHeight(SIZEHEIGHT);
        this.btnPlay.setGraphic(imgPlay);

        // ButtonSettings
        final ImageView imgSettings = new ImageView(
                new Image(PersonalImages.SETTINGS_IMG.getResourceAsStream()));
        imgSettings.setFitWidth(SIZEWIDTH);
        imgSettings.setFitHeight(SIZEHEIGHT);
        this.btnSettings.setGraphic(imgSettings);

        // ButtonTutorial
        final ImageView imgTutorial = new ImageView(
                new Image(PersonalImages.TUTORIAL_IMG.getResourceAsStream()));
        imgTutorial.setFitWidth(SIZEWIDTH);
        imgTutorial.setFitHeight(SIZEHEIGHT);
        this.btnTutorial.setGraphic(imgTutorial);

        // ButtonRanking
        final ImageView imgRanking = new ImageView(
                new Image(PersonalImages.RANKING_IMG.getResourceAsStream()));
        imgRanking.setFitWidth(SIZEWIDTH);
        imgRanking.setFitHeight(SIZEHEIGHT);
        this.btnRanking.setGraphic(imgRanking);
    }

    private void resizable() {

        this.btnPlay.prefWidthProperty().bind(this.buttonContainer.widthProperty().divide(CENTER_POSITION));
        this.btnSettings.prefWidthProperty().bind(this.buttonContainer.widthProperty().divide(CENTER_POSITION));
        this.btnTutorial.prefWidthProperty().bind(this.buttonContainer.widthProperty().divide(CENTER_POSITION));
        this.btnRanking.prefWidthProperty().bind(this.buttonContainer.widthProperty().divide(CENTER_POSITION));

        // Title
        this.lblTitle.setWrapText(true);
    }

    private void loadMusic() {
        //Play Button CLick Sound
        SoundController.playMusic(PersonalSounds.TETRIS_THEME.getURL().getPath());
    }
}
