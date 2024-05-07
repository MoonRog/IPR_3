package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.sql.DriverManager.getConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {

        String rand = "10";
        String pattern = "]";
        String id;
        String data;
        String body;
        String variable;
        String result;

        String userName = "postgres";
        String password = "post";
        String connectionUrl = "jdbc:postgresql://localhost:5432/postgres";
        Class.forName("org.postgresql.Driver");
        Connection connection = getConnection(connectionUrl, userName, password);
        Statement statement = connection.createStatement();

        Pattern p = Pattern.compile(pattern);

        while (true) {

            int a = 0;

            ResultSet resultSet = statement.executeQuery("select * from intable");

            resultSet.next();

            while (a == 0) {

                resultSet.next();

                try {

                    id = resultSet.getString(1);
                    data = resultSet.getString(2);
                    body = resultSet.getString(3);

                    try {
                        Matcher matcher = p.matcher(body);

                        matcher.find();
                        variable = matcher.group();
                        result = body.replace(variable, rand);
                        statement.executeUpdate("delete from intable where id = '" + id + "'");
                        statement.executeUpdate("insert into outtable (id, dtcreate, body) values ('" + id + "','" + data + "','" + result + "')");
                        System.out.println("true");
                        a++;
                    }
                    catch (Exception e){
                    }
                } catch (Exception e){
                    System.out.println("false");
                    a++;}

            }

            Thread.sleep(10000);
        }

    }
}