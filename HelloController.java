package com.example.lab2asmitttt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public TableView<UserAsmit> userTable;
    public TableColumn<UserAsmit,Integer> id;
    public TableColumn <UserAsmit,String> name;
    public TableColumn <UserAsmit,String> email;
    public TableColumn <UserAsmit,String> password;
    public TextField uid;
    public TextField uname;
    public TextField uemail;
    public TextField upassword;
    @FXML
    private Label welcomeText;

    ObservableList<UserAsmit> list = FXCollections.observableArrayList();

    @FXML
    protected void onHelloButtonClick() {
        fetchData();
    }

    private void fetchData() {
        list.clear();

        String jdbcUrl = "jdbc:mysql://localhost:3306/asssmitlab2";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM asmit";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int pid = resultSet.getInt("pid");
                String playername = resultSet.getString("playername");
                String playeremail = resultSet.getString("playeremail");
                String password = resultSet.getString("password");
                list.add(new UserAsmit(pid, playername, playeremail, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<UserAsmit,Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<UserAsmit,String>("name"));
        email.setCellValueFactory(new PropertyValueFactory<UserAsmit,String>("email"));
        password.setCellValueFactory(new PropertyValueFactory<UserAsmit,String>("password"));
        userTable.setItems(list);
    }

    public void InsertData(ActionEvent actionEvent) {
        String playername = uname.getText();
        String playeremail = uemail.getText();
        String password = upassword.getText();

        String jdbcUrl = "jdbc:mysql://localhost:3306/asssmitlab2";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO asmit (playername, playeremail, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, playername);
            statement.setString(2, playeremail);
            statement.setString(3, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateData(ActionEvent actionEvent) {
        int pid = Integer.parseInt(uid.getText());
        String playername = uname.getText();
        String playeremail = uemail.getText();
        String password = upassword.getText();

        String jdbcUrl = "jdbc:mysql://localhost:3306/asssmitlab2";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE asmit SET playername = ?, playeremail = ?, password = ? WHERE pid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, playername);
            statement.setString(2, playeremail);
            statement.setString(3, password);
            statement.setInt(4, pid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteData(ActionEvent actionEvent) {
        int pid = Integer.parseInt(uid.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/asssmitlab2";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM asmit WHERE pid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void LoadData(ActionEvent actionEvent) {
        int pid = Integer.parseInt(uid.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/asssmitlab2";
        String dbUser = "root";
        String dbPassword = "";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM asmit WHERE pid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pid);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String playername = resultSet.getString("playername");
                String playeremail = resultSet.getString("playeremail");
                String password = resultSet.getString("password");

                uname.setText(playername);
                uemail.setText(playeremail);
                upassword.setText(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
