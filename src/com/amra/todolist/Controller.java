package com.amra.todolist;

import com.amra.todolist.datamodel.TodoData;
import com.amra.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    private List<TodoItem>  todoItems;

    @FXML
    private ListView<TodoItem> todoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPain;

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
        todoListView.setItems(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPain.getScene().getWindow());
        dialog.setTitle("Add New Todo Item");
        dialog.setHeaderText("Use this dialog to create a new todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoitemDialog.fxml"));
        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());
//            Parent root = FXMLLoader.load(getClass().getResource("todoitemDialog.fxml"));
//            dialog.getDialogPane().setContent(root);
        }
        catch (Exception e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController controller = fxmlLoader.getController();
            TodoItem newItem = controller.processResults();
         // todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
            todoListView.getSelectionModel().select(newItem);
        }
//        else{
//            System.out.println("Cancel pressed");
//        }
    }

    @FXML
    public void handleClickListView(){
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());

    }

}


// System.out.println("Selected item is " + item);
//itemDetailsTextArea.setText(item.getDetails());
//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n\n");
//        sb.append("Due:");
//        sb.append(item.getDeadline().toString());
//        itemDetailsTextArea.setText(sb.toString());



//        TodoItem item1 = new TodoItem("Mail birthday card","Buy a 40th birthday card for John", LocalDate.of(2015, Month.APRIL,25));
//        TodoItem item2 = new TodoItem("Doctor's Appointment","See Dr.Smith at 123 Main Street", LocalDate.of(2015, Month.MAY,22));
//        TodoItem item3 = new TodoItem("Finish design proposal for client","I promised Mike i'd email him.", LocalDate.of(2015, Month.APRIL,23));
//        TodoItem item4 = new TodoItem("Pickup Bri at the train station","Arriving on March 23", LocalDate.of(2015, Month.MARCH,22));
//        TodoItem item5 = new TodoItem("Pick up dry cleaning","The clothes should be ready by Wednesday.", LocalDate.of(2015, Month.APRIL,20));
//
//        todoItems = new ArrayList<>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);
//        todoItems.add(item4);
//        todoItems.add(item5);

//       TodoData.getInstance().setTodoItems(todoItems);
