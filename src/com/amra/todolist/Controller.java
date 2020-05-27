package com.amra.todolist;

import com.amra.todolist.datamodel.TodoData;
import com.amra.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {
    private List<TodoItem> todoItems;
    @FXML
    private ListView<TodoItem> todoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPain;

    @FXML
    private ContextMenu listContextMenu;

    public void initialize(){
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem();
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

        //add deleteMenuItem to Context menu
        listContextMenu.getItems().addAll(deleteMenuItem);
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
        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {
                ListCell<TodoItem> cell = new ListCell<TodoItem>(){
                    @Override
                    protected void updateItem(TodoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }
                        else {
                            setText(item.getShortDescription());
                            if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))){
                                setTextFill(Color.RED);
                            }
                            else if(item.getDeadline().equals(LocalDate.now().plusDays(1))){
                                setTextFill(Color.GREEN);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs,wasEmpty,isNowEmpty) ->{
                            if(isNowEmpty){
                                cell.setContextMenu(null);
                            }
                            else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }

                );
                return cell;
            }
        });
    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPain.getScene().getWindow());
        dialog.setTitle("Add new Todo Item");
        dialog.setHeaderText("Use this dialog to create a new todo item");

        FXMLLoader fxmlLoader =new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoitemDialog.fxml"));
        try{

            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK){
           DialogController controller = fxmlLoader.getController();
           TodoItem newItem = controller.processResults();
     //      todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
           todoListView.getSelectionModel().select(newItem);
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            if(keyEvent.getCode().equals(KeyCode.DELETE)){
                deleteItem(selectedItem);
            }
        }
    }

    public void deleteItem(TodoItem item){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete todo item");
        alert.setHeaderText("Delete item "+ item.getShortDescription());
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to go back.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            TodoData.getInstance().deleteTodoItem(item);
        }
    }
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


//    @FXML
//    public void handleClickListView(){
//          TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//          itemDetailsTextArea.setText(item.getDetails());
//          deadlineLabel.setText(item.getDeadline().toString());
//    }