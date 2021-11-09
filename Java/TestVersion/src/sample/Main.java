package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /* SQL JDBC Test Script

        Connection con = DatabaseController.getConnection(DatabaseController.createBasicDataSource());
            String query = "select * from `registratie`;";
            PreparedStatement ps = con.prepareStatement(query);
            try {
            // Execute Query
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
            int arduinoID = resultSet.getInt("arduinoID");
            Date TijdDatum = resultSet.getDate("DatumTijd");
            double PHwaarde = resultSet.getDouble("PHwaarde");
            double GrondTemp = resultSet.getDouble("GrondTemp");
            double GrondVocht = resultSet.getDouble("GrondVocht");
            double LuchtTemp = resultSet.getDouble("LuchtTemp");
            double LuchtVocht = resultSet.getDouble("LuchtVocht");
            Registratie reg = new Registratie(arduinoID, TijdDatum, PHwaarde, GrondTemp, GrondVocht, LuchtTemp, LuchtVocht);

            System.out.println(reg.toString());
            }
            }
            catch(Exception ex){
            System.out.println(ex.getMessage());
            }
        */

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("KEK");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
