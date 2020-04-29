package com.amra.todolist;


import com.amra.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<TodoItem> todoItems;

    @FXML
    private ListView<TodoItem> toDoListView;

    @FXML
    private TextArea itemDetailTextArea;
    @FXML
    private Label deadLineLabel;


    public void initialize(){
        TodoItem item1 = new TodoItem("Mail birthday card","Buy a 30th birthday card", LocalDate.of(2016, Month.APRIL,25));
        TodoItem item2 = new TodoItem("Doctor's appointment","See Dr.Smith at 1223Main Street", LocalDate.of(2016, Month.MAY,03));
        TodoItem item3 = new TodoItem("Finish design proposal","Respond with an email.", LocalDate.of(2016, Month.APRIL,22));
        TodoItem item4 = new TodoItem("Pickup Bri at the train station","Bry arriving on March 23", LocalDate.of(2016, Month.MARCH,23));
        TodoItem item5 = new TodoItem("Pickup dry cleaning","Clothes ready by Wednesday", LocalDate.of(2016, Month.APRIL,25));


        todoItems = new ArrayList<TodoItem>();
        todoItems.add(item1);
        todoItems.add(item2);
        todoItems.add(item3);
        todoItems.add(item4);
        todoItems.add(item5);


        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
                if(newValue != null){
                    TodoItem item = toDoListView.getSelectionModel().getSelectedItem();
                    itemDetailTextArea.setText(item.getDetails());
//                    deadLineLabel.setText(item.getDeadLine().toString());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    deadLineLabel.setText(df.format(item.getDeadLine()));
                }
            }
        });

        toDoListView.getItems().setAll(todoItems);
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleClickListView(){
        TodoItem item = toDoListView.getSelectionModel().getSelectedItem();
//        System.out.println("The selected item is " + item);
//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Due:");
//        sb.append(item.getDeadLine().toString());
//        itemDetailTextArea.setText(sb.toString());
        itemDetailTextArea.setText(item.getDetails());
        deadLineLabel.setText(item.getDeadLine().toString());
    }
}
