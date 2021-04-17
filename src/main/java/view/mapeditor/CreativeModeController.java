package view.mapeditor;

import controller.game.GameLoop;
import controller.game.GameStateImpl;
import controller.scene.FXMLMenuController;
import controller.utilities.CheckAlertController;
import controller.utilities.GUIController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.mapeditor.Level;
import model.mapeditor.LevelManager;
import model.settings.SettingLevel.SettingLevelBuilder;
import model.settings.SettingLevelManager;
import model.utilities.GameUtilities;
import resource.routing.PersonalStyle;
import resource.routing.PersonalViews;
import view.SceneLoader;

public class CreativeModeController implements GUIController {

    private Level currentLevel;

    @FXML
    private AnchorPane panel;

    @FXML
    private BorderPane borderPanel;

    @FXML
    private Button menuBtn;

    @FXML
    private Button builderBtn;

    @FXML
    private Button playBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox levelContainer;

    @FXML
    private Label levelSelected;



    /**
     * initializes the window by setting dimensions.
     */
    @FXML
    public void initialize() {
        //Anchor Pane main setting
        this.panel.setMinWidth(GameUtilities.SCREEN_WIDTH);
        this.panel.setMaxWidth(GameUtilities.SCREEN_WIDTH);
        this.panel.setMinHeight(GameUtilities.SCREEN_HEIGHT);
        this.panel.setMaxHeight(GameUtilities.SCREEN_HEIGHT);

        this.borderPanel.setMinWidth(GameUtilities.SCREEN_WIDTH);
        this.borderPanel.setMaxWidth(GameUtilities.SCREEN_WIDTH);
        this.borderPanel.setMinHeight(GameUtilities.SCREEN_HEIGHT);
        this.borderPanel.setMaxHeight(GameUtilities.SCREEN_HEIGHT);
        //ScrollPane setting
        this.scrollPane.setMinWidth(GameUtilities.SCREEN_WIDTH / 2);
        this.scrollPane.setMaxWidth(GameUtilities.SCREEN_WIDTH / 2);
        this.scrollPane.setMinHeight(GameUtilities.SCREEN_HEIGHT);
        this.scrollPane.setMaxHeight(GameUtilities.SCREEN_HEIGHT);
        this.scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        //Setting Vbox level container
        this.levelContainer.setMinWidth(GameUtilities.SCREEN_WIDTH / 2);
        this.levelContainer.setMaxWidth(GameUtilities.SCREEN_WIDTH / 2);
        this.levelContainer.setMinHeight(GameUtilities.SCREEN_HEIGHT);
        this.levelContainer.setMaxHeight(GameUtilities.SCREEN_HEIGHT);
        //Label setting
        this.levelSelected.setMinWidth(GameUtilities.SCREEN_WIDTH);
        this.levelSelected.setMaxWidth(GameUtilities.SCREEN_WIDTH);
        this.levelSelected.setMinHeight(GameUtilities.SCREEN_HEIGHT);
        this.levelSelected.setMaxHeight(GameUtilities.SCREEN_HEIGHT);

        levelSelected.setOpacity(0);
        this.update();
    }

    /**
     * implementation of pattern observer.
     * reads the new generated levels and creates a button as a reference
     * by copying the style from an existing one
     * {@inheritDoc}
     */
    public void update() {
        this.levelContainer.getChildren().clear();
        for (final Level lvl : LevelManager.loadLevels()) {
            final Button b = new Button(lvl.getLevelName());
            this.setButtonStyle(b, menuBtn);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    levelSelected.setOpacity(100);
                    currentLevel = lvl;
                    levelSelected.setText("Level name : " + lvl.getLevelName() + "\n"
                                          + "Background : " + lvl.getBackground().getTheme() + "\n"
                                          + "Music : " + lvl.getMusic().getName());
                }
            });
            this.levelContainer.getChildren().add(b);
        }
    }

    /**
     * @param event
     */
    @FXML
    void backToMenu(final MouseEvent event) {
        final var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneLoader.switchScene(stage, 
                PersonalViews.SCENE_MAIN_MENU.getURL(), 
                PersonalViews.SCENE_MAIN_MENU.getTitleScene(), 
                stage.getWidth(), 
                stage.getHeight(),
                PersonalStyle.DEFAULT_STYLE.getStylePath());
        stage.setResizable(false);
    }

    /**
     * @param event
     */
    @FXML
    void goToLevelBuilder(final MouseEvent event) {
        /*final var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneLoader.switchScene(stage, 
                PersonalViews.SCENE_EDITOR_MODE.getURL(), 
                PersonalViews.SCENE_EDITOR_MODE.getTitleScene(), 
                stage.getWidth() / 1.3, 
                stage.getHeight(),
                PersonalStyle.DEFAULT_STYLE.getStylePath());
        stage.setResizable(true);
        */
        this.builderBtn.setOnAction(this.switchPage(PersonalViews.SCENE_EDITOR_MODE, PersonalStyle.DEFAULT_STYLE, 
                GameUtilities.SCREEN_WIDTH / 1.3, GameUtilities.SCREEN_HEIGHT, false));
    }

    /**
     * loads the currently selected level and starts the gameloop cycle.
     */
    @FXML
    void playLevel(final MouseEvent event) {
        if (!levelSelected.getText().isBlank()) {
            GameStateImpl.setCreativeMode(true);

            //UserManager.saveUser(new User());
            //final Scene scene = playBtn.getScene();
            final SettingLevelBuilder levelBuilder = new SettingLevelBuilder();
            levelBuilder.fromSettings(SettingLevelManager.loadOption());
            levelBuilder.selectLevel(currentLevel);
            SettingLevelManager.saveOption(levelBuilder.build());

            this.playBtn.setOnAction(this.switchPage(PersonalViews.SCENE_CHARACTER_MENU, PersonalStyle.DEFAULT_STYLE, 
                    GameUtilities.SCREEN_WIDTH, GameUtilities.SCREEN_HEIGHT, true));

            //inal Thread engine = new Thread(new GameLoop(scene));
            //.setDaemon(true);
            //engine.start();
        } else {
            CheckAlertController.checkLevelSelected();
        }
    }

    /**
     * copy the style of a button to another button.
     * @param subject the button that takes the new style
     * @param reference the button that gives the new style
     */
    private void setButtonStyle(final Button subject, final Button reference) {
        subject.setStyle(reference.getStyle());
        subject.setEffect(reference.getEffect());
        subject.setFont(reference.getFont());
    }

    /**
     * This method allows to switch the current scene whit the next scene.
     * @param scene - use to set the next scene.
     * @param style - use to set the style for the next scene.
     * @param width - use to set the width for next scene.
     * @param height - use to set the height for next scene.
     * @param resizable - use to understand if the next scene will be resizable or not.
     * @return an ActionEvent that allow to change between the current scene and the next scene.
     */
    private EventHandler<ActionEvent> switchPage(final PersonalViews scene, final PersonalStyle style, 
                                                 final double width, final double height, final boolean resizable) {

        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent event) {
                final Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                //Switch Scene
                SceneLoader.switchScene(currentStage, 
                scene.getURL(), 
                scene.getTitleScene(), 
                width, 
                height,
                style.getStylePath());
                currentStage.setResizable(resizable);
            }

        };
    }

}
