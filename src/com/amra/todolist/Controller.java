package com.amra.todolist;

import com.amra.todolist.datamodel.TodoData;
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
import java.util.Date;
import java.util.List;

public class Controller {
    private List<TodoItem> todoItems;
    @FXML
    private ListView<TodoItem> todoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    public void initialize(){


        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observable, TodoItem oldValue, TodoItem newValue) {
                if(newValue !=null){
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d,yyyy");
                    deadlineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }

//    @FXML
//    public void handleClickListView(){
//          TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//          itemDetailsTextArea.setText(item.getDetails());
//          deadlineLabel.setText(item.getDeadline().toString());
//    }

}


//        System.out.println("The selected item is "+ item);
//          StringBuilder sb = new StringBuilder();
//          sb.append(item.getShortDescription());
//          sb.append("\n\n\n\n");
//          sb.append("Due: ");
//          sb.append(item.getDeadline().toString());
//          itemDetailsTextArea.setText(sb.toString());

//        TodoItem item1 = new TodoItem("Mail birthday card","Buy a 40th birthday card for John", LocalDate.of(2016, Month.APRIL,25));
//        TodoItem item2 = new TodoItem("Doctor's Appointment","See Dr.Smith at 123 Main Street",LocalDate.of(2016,Month.APRIL,22));
//        TodoItem item3 = new TodoItem("Finish design proposal for client","I promised Mike i'd email him",LocalDate.of(2019,Month.APRIL,26));
//
//        todoItems = new ArrayList<TodoItem>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);

//TodoData.getInstance().setTodoItems(todoItems);