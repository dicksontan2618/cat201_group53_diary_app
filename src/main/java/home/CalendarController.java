package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalTime;


public class CalendarController {

    @FXML private GridPane calendar_grid;
    @FXML private TextField year_input_field, month_input_field;
    @FXML private TextArea edit_area = new TextArea();
    @FXML public Button display_calendar, home, edit_notes = new Button("Edit");

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

    private Button [] day_slots;
    public void val_date()
    {
        if(!year_input_field.getText().equals("") && !month_input_field.getText().equals(""))
        {
            //This line of code will parse the integer which represents the year
            int year = Integer.parseInt(year_input_field.getText().replaceAll("\\s{1,}",""));

            //This line of code will parse the integer which represents the month
            int month = Integer.parseInt(month_input_field.getText().replaceAll("\\s{1,}",""));

            //THis conditional statement makes sure that the user input of year and month are within the valid range
            if(year > 1800 && month > 0 && month < 13)
            {
                //A user-defined class of calendar
                CalcDays current_calendar = new CalcDays();
                int totalDay_calendar = current_calendar.obtainNumOfDaysInMonth(year,month);

                //Determines the first day of certain month
                int startDay_calendar = current_calendar.dayBegin(year,month);

                //Define an empty array which is later filled with the number of days of the user input month
                day_slots = new Button [31];
                for(int i = 1; i < 32; i++)
                    day_slots[i-1] = new Button("" + i);

                for(int i = 0; i < 31; i++)
                {
                    day_slots[i].setBackground(null);
                    day_slots[i].setMinWidth(50);
                    day_slots[i].setMinHeight(50);
                }

                //Avoid duplication by clearing out all the buttons on the grid pane during each iteration of user input
                calendar_grid.getChildren().clear();

                int day_counter = 0;

                //Control the display of days on the first row of the calendar
                for(int i = startDay_calendar; i < 7; i++)
                {
                    calendar_grid.add(day_slots[day_counter], i,0);
                    day_counter++;
                }

                int row_ctrl = (int)(Math.ceil((totalDay_calendar - day_counter)/7));

                //Control the display of days for the other rows of the calendar
                for(int z = 1; z <= row_ctrl+1; z++)
                {
                    for(int y = 0; y < 7; y++)
                    {
                        if(day_counter < totalDay_calendar)
                        {
                            calendar_grid.add(day_slots[day_counter],y,z);
                            day_counter++;
                        }
                    }
                }

                //This section is involved in naming a unique file of notepad created by user on a certain date
                String counter;
                if(month < 10) {counter = year + "0" + month + "";}
                else {counter = year + "" + month + "";}

                for(int y = 0; y < 31; y++)
                    setCursor(y);

                for(int y = 0; y < 31; y++)
                {
                    String mess2;
                    if(y < 10)
                        mess2 = "0" + y;
                    else
                        mess2 = "" + y;
                    setAction(counter + mess2, y);
                }
            }
        }
    }

    private static final String idle = "-fx-background-color: transparent;";
    private static final String hover = "-fx-background-color: beige;";
    private static final String hoverMouse = "";

    //Change the style or colour of a day slot of calendar when the mouse is hovering over that slot
    private void setCursor(int i)
    {
        day_slots[i].setOnMouseEntered(e -> {
            day_slots[i].setCursor(Cursor.HAND);
            day_slots[i].setStyle(hover);
        });
        day_slots[i].setOnMouseExited(e -> day_slots[i].setStyle(idle));
    }

    /* Call the function "editable_area" to trigger a pop-up window of notepad when a
     corresponding slot on the calendar is clicked */
    private void setAction(String counter, int y)
    {
        day_slots[y].setOnAction(e -> editable_area(counter));
    }

    //This function creates and saves a new file of note for user input on a certain date
    private void editable_area(String file)
    {
        int count = 0;
        String sentence = "";
        try
        {
            //Reads text from a character-input stream, buffering characters
            BufferedReader reader = new BufferedReader(new FileReader("userNotes\\" + file + ".txt"));
            while(reader.readLine() != null)
            {
                count++;
            }

            //Prints out the number of lines in a file on a certain date
            System.out.println("The number of lines in file " + file + " is: " + count);
            String [] content = new String[count];
            try
            {
                BufferedReader reader_2 = new BufferedReader(new FileReader("userNotes\\" + file + ".txt"));
                for(int i=0; i < count; i++)
                {
                    content[i] = reader_2.readLine();
                }
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Failed to read file. (The file is not detected)");
            }
            catch(IOException o)
            {
                System.out.println("Failed to read file. (The file is corrupted)");
            }
            for(int i = 0; i < count; i++)
            {
                sentence += content[i] + "\n";
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Failed to read file. (The file is not detected)");
            System.out.print("A new file is being created now: ");
            createFile(file + ".txt");
        }
        catch(IOException o)
        {
            System.out.println("Failed to read file. (The file is corrupted)");
            System.out.print("A new file is being created now: ");
            createFile(file + ".txt");
        }

        //Call a function when it is clicked to update the content of the note file
        edit_notes.setOnAction(e -> updatePressed(file + ".txt"));
        edit_area.setText(sentence);

        VBox second2 = new VBox(15);
        second2.setAlignment(Pos.CENTER);
        second2.getChildren().addAll(edit_area, edit_notes);

        edit_notes.setStyle("-fx-background-color: #FFC680;");
        edit_notes.setMinWidth(100);
        edit_notes.setMinHeight(20);
        edit_notes.setOnMouseEntered(e -> edit_notes.setCursor(Cursor.HAND));
        second2.setStyle("-fx-background-color: #D0B49F;");
        edit_area.setStyle( "-fx-control-inner-background:  #E4D4C8; " +
                            "-fx-focus-color: -fx-control-inner-background; " +
                            "-fx-faint-focus-color: -fx-control-inner-background; " +
                            "-fx-font-size: 15");
        edit_area.setWrapText(true);
        Stage second = new Stage();
        Scene sc2 = new Scene(second2, 350, 300);
        second.setTitle("Notes");
        second.setScene(sc2);
        second.show();
    }

    //This function updates the content of the note file
    private void updatePressed(String path)
    {
        //Determines the number of rows in the note file
        int rows_in_textArea = edit_area.getText().split("\n").length;
        System.out.println(rows_in_textArea);
        try
        {
            File output = new File("userNotes\\" + path);
            FileOutputStream outputFile = new FileOutputStream(output);
            BufferedWriter buwr = new BufferedWriter(new OutputStreamWriter(outputFile));
            for (String line : edit_area.getText().split("\n"))
            {
                buwr.write(line);
                buwr.newLine();
            }
            buwr.close();
        }
        catch (Exception e) {
            System.out.println("Fail to write into file:" + e);
        }
    }

    //This function creates a new note file for user input
    private void createFile(String path)
    {
        // If the file cannot be found or causes IOException, it will be executed
        try
        {
            File new_file = new File("userNotes\\" + path);
            if(new_file.createNewFile())
            {
                System.out.println("A brand new file has been created.");
            }
            else
                System.out.println("A new file is not created.");
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    /* This class contains functions to check and validate the number of days that should be contained in a month
    according to the inputs of year and month */
    class CalcDays
    {
        //This is a constructor
        CalcDays() {}

        //This function checks whether the year input by user is a leap year
        public boolean validateLeapYear(int year)
        {
            return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
        }

        //This function obtains the total number of days since the year of 1800
        public int obtainTotalNumOfDays(int year, int month)
        {
            int total = 0;

            for(int i = 1800; i < year; i++)
            {
                if(validateLeapYear(i)) {total += 366;}
                else {total += 365;}
            }

            for(int i = 1; i < month; i++)
            {
                total += obtainNumOfDaysInMonth(year, i);
            }

            return total;
        }

        //This function determines the total number of days in a month of a particular year according to user input
        public int obtainNumOfDaysInMonth(int year, int month)
        {
            if(month == 1 ||month == 3 ||month == 5 ||month == 7 ||month == 8 ||month == 10 ||month == 12)
                return 31;
            if(month == 4 ||month == 6 ||month == 9 ||month == 11)
                return 30;
            else
            {
                if(validateLeapYear(year))
                    return 29;
                else
                    return 28;
            }
        }

        //This function determines the first day of a particular month according to user input
        public int dayBegin(int year, int month)
        {
            System.out.println("Start day:" + (obtainTotalNumOfDays(year,month) + 3)%7);
            return (obtainTotalNumOfDays(year,month) + 3)%7;
        }
    }





































}