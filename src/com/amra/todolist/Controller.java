package com.amra.todolist;

import com.amra.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<TodoItem> todoItems;

    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;




    public void initialize() {
        TodoItem item1 = new TodoItem("Mail Birthday card", "Buy a 30 birthday card for John",
                LocalDate.of(2016, Month.APRIL, 25));
        TodoItem item2 = new TodoItem("Doctors Appointment", "See Doctor Smith ",
                LocalDate.of(2016, Month.MAY, 23));
        TodoItem item3 = new TodoItem("Finish design proposal", "Promised design proposal to Mike ",
                LocalDate.of(2016, Month.APRIL, 21));
        TodoItem item4 = new TodoItem("Pickup Sam at the airport", "Sam's arriving ",
                LocalDate.of(2016, Month.MARCH, 22));
        TodoItem item5 = new TodoItem("Pick up dry cleaning", "Clothes should be ready by Wednesday",
                LocalDate.of(2016, Month.APRIL, 20));


        this.todoItems = new ArrayList<TodoItem>();
        todoItems.add(item1);
        todoItems.add(item2);
        todoItems.add(item3);
        todoItems.add(item4);
        todoItems.add(item5);

        todoListView.getItems().setAll(todoItems);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void handleClickListView(){
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//        System.out.println("The selected item is " + item);
        StringBuilder sb = new StringBuilder(item.getDetails());
        sb.append("\n\n\n\n");
        sb.append("Due: ");
        sb.append(item.getDeadLine().toString());
        itemDetailsTextArea.setText(sb.toString());

    }
}
