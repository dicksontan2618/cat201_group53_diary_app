package home;


import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.event.ActionEvent;

public class DiaryController {

    MenuBar bar;

    Menu file;
    Menu edit;
    Menu view;
    Menu javaM;
    Menu help;
    Menu shortcuts;

    MenuItem f1New;
    MenuItem f2Open;
    MenuItem f3Save;
    MenuItem f4Exit;
    MenuItem e1Undo;
    MenuItem e2Redo;
    MenuItem e3Cut;
    MenuItem e4Copy;
    MenuItem e5Paste;
    MenuItem e6Delete;
    MenuItem e7SelectAll;
    MenuItem h1About;
    MenuItem h2FontSize;
    MenuItem h3FontType;
    @FXML
    Menu h4DateFormat;
    Menu h5TimeFormat;
    MenuItem j1Compile;
    MenuItem j2Run;
    MenuItem c1Date;
    MenuItem c2Time;
    Menu c3Emotion;
    @FXML
    MenuItem m1emotion;
    @FXML
    MenuItem m2emotion;
    @FXML
    MenuItem m3emotion;
    @FXML
    MenuItem m4emotion;
    @FXML
    MenuItem m5emotion;
    CheckMenuItem terminal;
    @FXML
    MenuItem a1ddMMyyyy;
    @FXML
    MenuItem a2MMddyyyy;
    @FXML
    MenuItem a3EEEMMMdyyyy;
    @FXML
    MenuItem a4EEEdMMMyyyy;
    @FXML
    MenuItem a5yyyyddMM;
    @FXML
    MenuItem a6yyyyMMdd;

    MenuItem t1HHmmss;
    MenuItem t2hmma;
    MenuItem t3hhmm;
    MenuItem t4hhmmss;

    @FXML
    TextArea txt;
    TextArea output;
    Label copyRight;
    SeparatorMenuItem sep1, sep2, sep3, sep4;
    BorderPane pane;
    @FXML
    Scene s;
    String myClipBoard;
    FileChooser fileChooser;
    Dialog<ButtonType> dialog;

    ButtonType save;
    ButtonType dontSave;
    ButtonType cancel;
    FileReader fr;
    BufferedReader br;
    FileWriter fw;
    BufferedWriter bw;
    boolean isSaved;
    String fileName = "Untitled";
    String path = "";
    Thread clip;
    String oldTxt = "";
    String newTxt = "";

    public void init() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("diary.fxml"));
        Parent root = loader.load();
        // thread to get the string clipboard
        clip = new Thread(new Runnable(){
            @Override
            public void run() {
                while(true){
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    Clipboard clipboard = toolkit.getSystemClipboard();
                    String result = "";
                    try {
                        result = (String) clipboard.getData(DataFlavor.stringFlavor);
                    } catch (Exception ex) {}
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {}
                    myClipBoard = result;
                }
            }
        });
        clip.start();

        isSaved = true;
        bar = new MenuBar();    // menu bar
        // menus to be set in menu bar
        file = new Menu("File");
        edit = new Menu("Edit");
        view = new Menu("View");
        javaM = new Menu("Java");
        help = new Menu("Help and Settings");
        shortcuts= new Menu("Shortcuts");
        // items in menu file
        f1New = new MenuItem("New");
        f2Open = new MenuItem("Open");
        f3Save = new MenuItem("Save");
        f4Exit = new MenuItem("Exit");
        // items in menu edit
        e1Undo = new MenuItem("Undo");
        e2Redo = new MenuItem("Redo");
        e3Cut = new MenuItem("Cut");
        e4Copy = new MenuItem("Copy");
        e5Paste = new MenuItem("Paste");
        e6Delete = new MenuItem("Delete");
        e7SelectAll = new MenuItem("Select All");
        // items in menu help
        h1About = new MenuItem("About");
        h2FontSize = new MenuItem("Font Size");
        h3FontType = new MenuItem("Font Type");
        h4DateFormat = new Menu ("Date Format");
        h5TimeFormat= new Menu ("Time Format");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String str = formatter.format(date);
        a1ddMMyyyy= new MenuItem(str);
        formatter=new SimpleDateFormat("MM/dd/yyyy");
        str = formatter.format(date);
        a2MMddyyyy= new MenuItem(str);
        formatter=new SimpleDateFormat("EEEE, MMM d yyyy");
        str = formatter.format(date);
        a3EEEMMMdyyyy= new MenuItem(str);
        formatter=new SimpleDateFormat("EEEE, d MMM yyyy");
        str = formatter.format(date);
        a4EEEdMMMyyyy= new MenuItem(str);
        formatter=new SimpleDateFormat("yyyy/d/MMM");
        str = formatter.format(date);
        a5yyyyddMM= new MenuItem(str);
        formatter=new SimpleDateFormat("yyyy/MMM/d");
        str = formatter.format(date);
        System.out.println(str);
        a6yyyyMMdd = new MenuItem();
        Date time = new Date();
        formatter = new SimpleDateFormat("HH:mm:ss");
        str = formatter.format(date);
        t1HHmmss=new MenuItem(str);
        formatter = new SimpleDateFormat("h:mm a");
        str = formatter.format(date);
        t2hmma=new MenuItem(str);
        formatter = new SimpleDateFormat("HHmm");
        str = formatter.format(date);
        t3hhmm=new MenuItem(str+" hours");
        formatter = new SimpleDateFormat("hh:mm:ss a");
        str = formatter.format(date);
        t4hhmmss=new MenuItem(str);
        // items in menu java
        j1Compile = new MenuItem("Compile");
        j2Run = new MenuItem("Run");
        // items in menu shortcuts
        c1Date = new MenuItem("Current Date");
        c2Time = new MenuItem("Current Time");
        c3Emotion = new Menu("Today's Emotion");
        m1emotion= new MenuItem("Excellent");
        m2emotion= new MenuItem("Good");
        m3emotion= new MenuItem("Average");
        m4emotion= new MenuItem("Poor");
        m5emotion= new MenuItem("Terrible");

        // view
        terminal = new CheckMenuItem("Show Terminal");
        // separator menu item
        sep1 = new SeparatorMenuItem();
        sep2 = new SeparatorMenuItem();
        sep3 = new SeparatorMenuItem();
        sep4 = new SeparatorMenuItem();
        // text area
        txt = new TextArea();
        // copyright label
        copyRight = new Label("Release Date: 14 DEC 2021    ©YSSH   Revised Date: 18 JAN 2023   ©CAT201");
        // pane type --> border
        pane = new BorderPane();
        // adding menus items

        // adding menus to menu bar
        bar.getMenus().addAll(file, edit, view, javaM, help, shortcuts);
        // adding bar, text area, label to borderpane
        pane.setTop(bar);
        pane.setCenter(txt);

        // shortcut for menu items
        f1New.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        f2Open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        f3Save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        f4Exit.setAccelerator(KeyCombination.keyCombination("Alt+Shift+F4"));

        e1Undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Z"));
        e2Redo.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Y"));
        e3Cut.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+X"));
        e4Copy.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+C"));
        e5Paste.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+V"));
        e6Delete.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+D"));
        e7SelectAll.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+A"));

        h1About.setAccelerator(KeyCombination.keyCombination("Ctrl+F1"));
        j1Compile.setAccelerator(KeyCombination.keyCombination("Ctrl+F9"));
        j2Run.setAccelerator(KeyCombination.keyCombination("Ctrl+F10"));

        c1Date.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
        c2Time.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
        terminal.setAccelerator(KeyCombination.keyCombination("Ctrl+t"));

        // file chooser
        fileChooser = new FileChooser();
        save = new ButtonType("Save");
        dontSave = new ButtonType("Don't Save");
        cancel = new ButtonType("Cancel");



    }

    public void start(Stage primaryStage) throws Exception {
        // just load fxml file and display it in the stage:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("diary.fxml"));
        Parent root = loader.load();
        AnchorPane anchorPane = new AnchorPane(root);
        s = new Scene(anchorPane);
        primaryStage.setScene(s);
        primaryStage.show();
        e1Undo.setDisable(true);
        e2Redo.setDisable(true);
        e3Cut.setDisable(true);
        e4Copy.setDisable(true);
        e5Paste.setDisable(true);
        e6Delete.setDisable(true);
        e7SelectAll.setDisable(true);


        // add css file
        File f = new File("src/Style.css");
        s.getStylesheets().clear();
        s.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

        // reset the terminal height ratio whenever the scene height is changed
        s.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(output.isVisible())
                    output.setPrefHeight(s.getHeight()/5);
                else
                    output.setPrefHeight(0);
            }
        });

        // reset the terminal height ratio whenever the terminal visibility is changed
        output.visibleProperty().addListener( new InvalidationListener(){

            @Override
            public void invalidated(Observable observable) {
                if(output.isVisible())
                    output.setPrefHeight(s.getHeight()/5);
                else
                    output.setPrefHeight(0);
            }
        });

        primaryStage.setOnCloseRequest(evt -> {
            // prevent window from closing before executing the exit routine
            evt.consume();
            f4Exit.fire();
        });
        // save dialoge
        dialog = new Dialog<ButtonType>();
        dialog.setTitle("Save");
        dialog.setContentText("Do you want to save last changes?");
        dialog.getDialogPane().getButtonTypes().addAll(save, dontSave, cancel);

        txt.setOnMouseMoved(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                setEn();                // in edit menu --> enable and disable menu items
            }
        });
        txt.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                // check if change happened in the text area --> not saved
                oldTxt = newTxt;
                newTxt = txt.getText();
                if(!newTxt.equals(oldTxt)){
                    isSaved = false;
                    primaryStage.setTitle("*" + fileName + " - FX NotePad"); // add "*" before file name if not saved
                }
                setEn();                // in edit menu --> enable and disable menu items
            }
        });

        primaryStage.setTitle(fileName + " - FX NotePad");              // set title
        primaryStage.setScene(s);                                       // set scene
        primaryStage.show();                                            // show stage
    }

    void setEn(){
        // set edit menu items as enabled or disabled
        if(!txt.isUndoable())
            e1Undo.setDisable(true);
        else
            e1Undo.setDisable(false);

        if(!txt.isRedoable())
            e2Redo.setDisable(true);
        else
            e2Redo.setDisable(false);
        if(myClipBoard.length() == 0)
            e5Paste.setDisable(true);
        else
            e5Paste.setDisable(false);

        if(txt.getSelectedText().length() == 0){
            e3Cut.setDisable(true);
            e4Copy.setDisable(true);
            e6Delete.setDisable(true);
        }
        else{
            e3Cut.setDisable(false);
            e4Copy.setDisable(false);
            e6Delete.setDisable(false);
        }

        if(txt.getText().length() == 0)
            e7SelectAll.setDisable(true);
        else
            e7SelectAll.setDisable(false);
    }

    @FXML
    public void handlem1EmotionClick() {
        txt.insertText(txt.getCaretPosition(),"Today's Mood- :-D");
        isSaved = false;

    }
    @FXML
    public void handlem2EmotionClick() {
        txt.insertText(txt.getCaretPosition(),"Today's Mood- :-)");
        isSaved = false;

    }
    @FXML
    public void handlem3EmotionClick() {
        txt.insertText(txt.getCaretPosition(),"Today's Mood- :-|");
        isSaved = false;

    }
    @FXML
    public void handlem4EmotionClick() {
        txt.insertText(txt.getCaretPosition(),"Today's Mood- :-/");
        isSaved = false;

    }
    @FXML
    public void handlem5EmotionClick() {
        txt.insertText(txt.getCaretPosition(),"Today's Mood- :-(");
        isSaved = false;
    }

    final String[] dateFormat = {"dd/MM/yy"};
    final String[] timeFormat={"HH:mm"};
    Date date = new Date();
    SimpleDateFormat dateformatter = new SimpleDateFormat(dateFormat[0]);
    final String[] strdate = {dateformatter.format(date)};
    Date time = new Date();
    SimpleDateFormat timeformatter = new SimpleDateFormat(timeFormat[0]);
    final String[] strtime={timeformatter.format(time)};
    @FXML
    public void handlec1DateClick() {
        txt.insertText(txt.getCaretPosition(), strdate[0]);         // paste
        isSaved = false;
    }
    @FXML
    public void handlec2TimeClick() {
        txt.insertText(txt.getCaretPosition(), strtime[0]);         // paste
        isSaved = false;
    }
    @FXML
    public void handlea1DFormatClick() {
        dateFormat[0] ="MM/dd/yyyy";
        SimpleDateFormat dateformatter = new SimpleDateFormat(dateFormat[0]);
        strdate[0] = dateformatter.format(date);
    }
    @FXML
    public void handlea2DFormatClick() {
        dateFormat[0] ="MM/dd/yyyy";
        SimpleDateFormat dateformatter = new SimpleDateFormat(dateFormat[0]);
        strdate[0] = dateformatter.format(date);
    }
    @FXML
    public void handlea3DFormatClick() {
        dateFormat[0] ="EEEE, MMM d yyyy";
        SimpleDateFormat dateformatter = new SimpleDateFormat(dateFormat[0]);
        strdate[0] = dateformatter.format(date);
    }
    @FXML
    public void handlea4DFormatClick() {
        dateFormat[0] ="EEEE, d MMM yyyy";
        SimpleDateFormat dateformatter = new SimpleDateFormat(dateFormat[0]);
        strdate[0] = dateformatter.format(date);
    }
    @FXML
    public void handlea5DFormatClick() {
        dateFormat[0] ="yyyy/d/MMM";
        SimpleDateFormat dateformatter = new SimpleDateFormat(dateFormat[0]);
        strdate[0] = dateformatter.format(date);
    }
    @FXML
    public void handlea6DFormatClick() {
        dateFormat[0] ="yyyy/MMM/d";
        SimpleDateFormat dateformatter = new SimpleDateFormat(dateFormat[0]);
        strdate[0] = dateformatter.format(date);
    }

    @FXML
    public void handlet1TFormatClick() {
        timeFormat[0] ="HH:mm:ss";
        SimpleDateFormat timeformatter = new SimpleDateFormat(timeFormat[0]);
        strtime[0] = timeformatter.format(time);
    }
    @FXML
    public void handlet2TFormatClick() {
        timeFormat[0] ="h:mm a";
        SimpleDateFormat timeformatter = new SimpleDateFormat(timeFormat[0]);
        strtime[0] = timeformatter.format(time);
    }
    @FXML
    public void handlet3TFormatClick() {
        timeFormat[0] ="HHmm";
        SimpleDateFormat timeformatter = new SimpleDateFormat(timeFormat[0]);
        strtime[0] = timeformatter.format(time);
    }
    @FXML
    public void handlet4TFormatClick() {
        timeFormat[0] ="hh:mm:ss a";
        SimpleDateFormat timeformatter = new SimpleDateFormat(timeFormat[0]);
        strtime[0] = timeformatter.format(time);
    }
    @FXML
    public void handlef1NewClick() {
        fileChooser = new FileChooser();
        save = new ButtonType("Save");
        dontSave = new ButtonType("Don't Save");
        cancel = new ButtonType("Cancel");
        dialog = new Dialog<ButtonType>();
        dialog.setTitle("Save");
        dialog.setContentText("Do you want to save last changes?");
        dialog.getDialogPane().getButtonTypes().addAll(save, dontSave, cancel);
        if(!isSaved){
            // if not saved --> show ask to save dialog
            Optional<ButtonType> result = dialog.showAndWait();
            if(!result.isPresent()){
                // do nothing
            }
            else if(result.get() == save){
                // if save button save is clicked --> save then initialize to a new document
                handlef3SaveClick();
                if(isSaved){
                    txt.clear();
                    fileName = "Untitled";
                    path = "";
                }
            }
            else if(result.get() == dontSave){
                // if don't save is clicked --> re initialize to a new document without saving
                txt.clear();
                isSaved = true;
                oldTxt = "";
                newTxt = "";
                fileName = "Untitled";
                path = "";
            }
            else if(result.get() == cancel){
                // if cancel is clicked --> just close the dialog and return
                dialog.close();
                return;
            }
        }
        else{
            // if saved --> reinitialize to a new document
            fileName = "Untitled";
            path = "";
            oldTxt= newTxt = "";
            txt.clear();
        }
    }
    @FXML
    public void handlef2OpenClick() {
        if(!isSaved)
            handlef1NewClick();
        // if saved show open dialog
        if(isSaved){
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
            try{
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                File f = fileChooser.showOpenDialog(null);     // choose the file to open
                fileName = f.getName();                                 // get file name
                txt.clear();                                            // clear the text area
                path = f.getParent();                                   // get directory path
                fr = new FileReader(f);                                 // file reader
                br = new BufferedReader(fr);                            // buffer reader to read the file
                String line;
                do{
                    line = br.readLine();                               // read lines
                    if(line != null)
                        txt.appendText(line+"\n");                      // set on text area

                }while(line != null);
                br.close();                                             // close the buffer reader
                fr.close();                                             // close the file reader
                isSaved = true;                                         // issaved flag is true
                // initialize old and new txt
                oldTxt = txt.getText();
                newTxt = txt.getText();

            }
            catch(Exception e){}
        }
    }
    @FXML
    public void handlef3SaveClick() {
        fileChooser = new FileChooser();
        try {
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
            fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Text Files", "*.txt"));
            File f = fileChooser.showSaveDialog(null);
            if(f == null) {
                // Show an error message to the user telling them to select a valid file name and directory path
                return;
            }
            if(!f.canWrite()) {
                // Show an error message to the user telling them that they do not have write access to the selected directory
                return;
            }
            if(f.exists()){
                // Show a confirmation dialog asking the user if they want to overwrite the file
                // If the user selects "No", return and don't save the file
            }
            fileName = f.getName();
            path = f.getParent();
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);
            bw.write(txt.getText());
            bw.close();
            fw.close();
            isSaved = true;
            // initialize old and new txt
            oldTxt = txt.getText();
            newTxt = txt.getText();
        } catch(Exception e) {
            System.err.println("Error while saving the file: "+e);
        }
    }

    @FXML
    public void handlef4ExitClick() {
        if(!isSaved){
            // if not saved --> show ask to save dialog
            Optional<ButtonType> result = dialog.showAndWait();
            if(!result.isPresent()){
            }
            else if(result.get() == save){
                // if saved is clicked --> fire save menu item
                handlef3SaveClick();
                if(isSaved)
                    Platform.exit();        // exit if saved
            }
            else if(result.get() == dontSave){
                Platform.exit();            // if don't save is clicked --> just exit!
            }
            else if(result.get() == cancel){
                dialog.close();             // cancel is clicked --> just lcose the dialog!
            }
        }
        else
            Platform.exit();
    }
    @FXML
    public void handlee1UndoClick() {
        if(txt.isUndoable()){
            txt.undo();         // undo
            isSaved = false;
        }
    }
    @FXML
    public void handlee2RedoClick() {
        if(txt.isRedoable()){
            txt.redo();         // redo
            isSaved = false;
        }
    }
    @FXML
    public void handlee3CutClick() {
        txt.cut();              // cut
        isSaved = false;
    }

    @FXML
    public void handlee4CopyClick() {
        txt.copy();
    }
    @FXML
    public void handlee5PasteClick() {
        txt.paste();            // paste
        isSaved = false;
    }
    @FXML
    public void handlee6DeleteClick() {
        IndexRange r = txt.getSelection();
        txt.deleteText(r);      // delete
        isSaved = false;
    }
    @FXML
    public void handlee7SelectAllClick() {

        txt.selectAll();        // select all
    }


    @FXML
    public void handleh1AboutClick() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("FX NotePad/ Student Starter Kit App");
        alert.setContentText("Developed by YSSH, Enhanced by CAT201 Group 53 22/23");
        alert.showAndWait();
    }

    Double size[] = { 8.0,9.0, 10.0,11.0,12.0,14.0,16.0,18.0,20.0,22.0,24.0,28.0, 32.0};
    ComboBox combo_box =
            new ComboBox(FXCollections.observableArrayList(size));

    // Create a combo box
    ComboBox combo_box1 =
            new ComboBox(FXCollections.observableArrayList(Font.getFontNames()));
    final Double[] fontsize = {11.0};
    final String[] fonttype = {"Roboto"};
    @FXML
    public void handleh2FontSizeClick(){
        Stage stage = new Stage();
        TextFlow text_flow = new TextFlow();
        stage.setTitle("Font Size");
        HBox hbox = new HBox(combo_box);
        // create VBox
        VBox vbox = new VBox(hbox, text_flow);
        // set spacing
        vbox.setSpacing(50.0);

        // set alignment of vbox
        vbox.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);
        // create a scene
        Button button = new Button();
        //Setting text to the button
        button.setText("OK");
        //Setting the location of the button
        button.setTranslateX(150);
        button.setTranslateY(0);
        Group root = new Group(button,vbox);
        Scene scene = new Scene(root, 200, 100);

        //Setting the stage


        stage.setScene(scene);

        stage.show();
        // set the scene


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                fontsize[0] =(Double)combo_box.getSelectionModel().getSelectedItem();

                System.out.println(fontsize[0]+fonttype[0]);
                txt.setFont(Font.font(fonttype[0],fontsize[0]));
                stage.close();
            }
        });

    }
    @FXML
    public void handleh3FontTypeClick(){
        Stage stage = new Stage();
        stage.setTitle("Font Type");
        HBox hbox = new HBox(combo_box1);

        // create VBox
        VBox vbox = new VBox(hbox);

        // set spacing
        vbox.setSpacing(30.0);

        // set alignment of vbox
        vbox.setAlignment(Pos.CENTER);
        Button button = new Button();
        //Setting text to the button
        button.setText("OK");
        //Setting the location of the button
        button.setTranslateX(150);
        button.setTranslateY(60);
        Group root = new Group(button,vbox);
        Scene scene = new Scene(root, 300, 50);

        //Setting the stage


        stage.setScene(scene);

        stage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                fonttype[0] = (String) combo_box1.getSelectionModel().getSelectedItem();
                System.out.println(fontsize[0]+fonttype[0]);
                txt.setFont(Font.font(fonttype[0],fontsize[0]));
                stage.close();
            }
        });
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToHome (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        root = loader.load();

        DashboardController DashboardController = loader.getController();
        try {
            File file = new File("username.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            DashboardController.displayName(bufferedReader.readLine());
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DashboardController.displayDate(String.valueOf(java.time.LocalDate.now()));
        DashboardController.randomQuote();

        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();

        if (hour >= 6 && hour < 18) {
            DashboardController.hideNight();
        } else {
            DashboardController.hideDay();
        }

        stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Starter Kit App");
        scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.show();
    }
}

