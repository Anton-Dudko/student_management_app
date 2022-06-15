import java.sql.*;
import java.util.LinkedList;

public class DataBaseHandler {
    private Connection connection;
    private Statement statement;

    public DataBaseHandler(){
        connectionToDB();
        createTable(connection, statement);
    }

    public void connectionToDB(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(Constant.HOSTNAME_DB + Constant.NAME_DB,
                    Constant.USERNAME_DB, Constant.PASSWORD_DB);
            statement = connection.createStatement();
            System.out.println("База данных подключена !");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(Connection connection, Statement statement){
        new Tables(connection, statement);
    }

    public String authUser(String email,String password) {
        String currentUser="";
        try {
            String query = "SELECT " + Constant.EMAIL + "," + Constant.PASSWORD+","+Constant.ID+","+Constant.ROLL + " FROM " + Constant.USERS_TABLE +
                    " WHERE " + Constant.EMAIL + " = " + "'" + email + "'" + " AND " + Constant.PASSWORD + " = " + "'" + password + "'";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                if (!rs.getString(Constant.EMAIL).equals("") &&
                        !rs.getString(Constant.PASSWORD).equals("")) {
                    currentUser+=rs.getString(Constant.ID)+" ";
                    currentUser+=rs.getString(Constant.ROLL);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(currentUser.equals("")) {
            return "false";
        }
        else {
            return currentUser;
        }
    }

    public LinkedList<String> showAllUsers() {

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Constant.ID+" , " + Constant.EMAIL+" , "+Constant.NAME_USER+" , "+Constant.SURNAME_USER+" , "+Constant.LASTNAME_USER+" , "+Constant.PASSWORD+" , "
                +Constant.ROLL+ " FROM " + Constant.USERS_TABLE ;
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getString(Constant.ID)+" ";
                contribution+=rs.getString(Constant.NAME_USER)+" ";
                contribution+=rs.getString(Constant.SURNAME_USER)+" ";
                contribution+=rs.getString(Constant.LASTNAME_USER)+" ";
                contribution+=rs.getString(Constant.EMAIL)+" ";
                contribution+=rs.getString(Constant.PASSWORD)+" ";
                contribution+=rs.getString(Constant.ROLL)+" ";
                list.add(contribution);
                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    public LinkedList<String> showStudents() {

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Constant.STUDENT_ID+" , " + Constant.STUDENT_NAME+" , "+Constant.STUDENT_SURNAME+" , " +
                ""+Constant.STUDENT_FAC+" , "+Constant.STUDENT_SPEC+" , "+Constant.STUDENT_NUMBER +" FROM " + Constant.STUDENTS_TABLE ;
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getInt(Constant.STUDENT_ID)+" ";
                contribution+=rs.getString(Constant.STUDENT_NAME)+" ";
                contribution+=rs.getString(Constant.STUDENT_SURNAME)+" ";
                contribution+=rs.getString(Constant.STUDENT_FAC)+" ";
                contribution+=rs.getString(Constant.STUDENT_SPEC)+" ";
                contribution+=rs.getString(Constant.STUDENT_NUMBER)+" ";
                list.add(contribution);
                System.out.println(list);
                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

    public void redactionUser(int idUser, String name, String surname, String lastname, String email,String password,int roll) {
        String query = "update "+Constant.USERS_TABLE+" SET   name =?, surname =?, lastname =?, login =?, password =?, roll=? WHERE " + Constant.ID + " = " + "'" + idUser + "'";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString(1, name);
            preparedStmt.setString(2, surname);
            preparedStmt.setString(3, lastname);
            preparedStmt.setString(4, email);
            preparedStmt.setString(5, password);
            preparedStmt.setInt   (6, roll);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void redactionMark(int id, String name, String surname, String group, String subject,int mark) {
        String query = "update "+Constant.MARK_TABLE+" SET   name =?, surname =?, group_number =?, subject =?, mark =? WHERE " + Constant.ID + " = " + "'" + id + "'";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString(1, name);
            preparedStmt.setString(2, surname);
            preparedStmt.setString(3, group);
            preparedStmt.setString(4, subject);
            preparedStmt.setInt(5, mark);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteUser(int id)  {

        String selectSQL = "DELETE FROM "+Constant.USERS_TABLE +  " WHERE id = ?";
        try {
            connection.prepareStatement(selectSQL);
            PreparedStatement preparedStmt = connection.prepareStatement(selectSQL);
            preparedStmt.setInt(1, id);
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean addUserInDb(String name, String surname, String lastname, String login, String password, String roll){

        try {
            String query = " insert into users (name, surname, lastname, login, password, roll)"
                    + " values ( ?, ?, ?, ?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, name);
            preparedStmt.setString (2, surname);
            preparedStmt.setString (3, lastname);
            preparedStmt.setString (4, login);
            preparedStmt.setString (5, password);
            preparedStmt.setString (6, roll);


            preparedStmt.executeUpdate();

            System.out.println("Данные в "+Constant.FACULTY_TABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean addMark(String name, String surname, String group, String subject, int mark){

        try {
            String query = " insert into marks (name, surname, group_number, subject, mark)"
                    + " values ( ?, ?, ?, ?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, name);
            preparedStmt.setString (2, surname);
            preparedStmt.setString (3, group);
            preparedStmt.setString (4, subject);
            preparedStmt.setInt (5, mark);

            preparedStmt.executeUpdate();

            System.out.println("Данные в "+Constant.FACULTY_TABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean addStudent(String name, String surname, String fac, String spec, String group){

        try {
            String query = " insert into students (name, surname, faculty, specialty, group_number)"
                    + " values ( ?, ?, ?, ?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, name);
            preparedStmt.setString (2, surname);
            preparedStmt.setString (3, fac);
            preparedStmt.setString (4, spec);
            preparedStmt.setString (5, group);

            preparedStmt.executeUpdate();

            System.out.println("Данные в "+Constant.FACULTY_TABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public boolean addSpec(String fac, String spec){

        try {
            String query = " insert into specialties (title, faculty)"
                    + " values ( ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);

            preparedStmt.setString (1, fac);
            preparedStmt.setString (2, spec);

            preparedStmt.executeUpdate();

            System.out.println("Данные в "+Constant.FACULTY_TABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public LinkedList<String> showMarksByGroup(String surname,String group){
        LinkedList<String> list = new LinkedList<>();
                String query = "SELECT "+Constant.MARK_ID+" , "+Constant.MARK_NAME+" , " + Constant.MARK_SURNAME+" , "+Constant.MARK_NUMBER+" , "+Constant.MARK_SUBJECT+" , "+Constant.MARK
                + " FROM " + Constant.MARK_TABLE +
                " WHERE " + Constant.MARK_SURNAME + " = " + "'" + surname + "'" +" AND "+ Constant.MARK_NUMBER + " = " + "'" + group + "'" ;
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getString(Constant.MARK_ID)+" ";
                contribution+=rs.getString(Constant.MARK_NAME)+" ";
                contribution+=rs.getString(Constant.MARK_SURNAME)+" ";
                contribution+=rs.getString(Constant.MARK_NUMBER)+" ";
                contribution+=rs.getString(Constant.MARK_SUBJECT)+" ";
                contribution+=rs.getString(Constant.MARK)+" ";
                list.add(contribution);
                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public LinkedList<String> showSpecByFac(String name){
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Constant.SPEC_TITLE+" , "+ Constant.SPEC_FAC
                + " FROM " + Constant.SPECIALTY_TABLE +
                " WHERE " + Constant.SPEC_FAC + " = " + "'" + name + "'";
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getString(Constant.SPEC_TITLE)+" ";
                contribution+=rs.getString(Constant.SPEC_FAC)+" ";
                list.add(contribution);
                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public LinkedList<String> showAllTasks() {
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT " + Constant.FAC_TITLE  + " FROM "
                + Constant.FACULTY_TABLE ;
        ResultSet rs = null;
        String task="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                task+=rs.getString(Constant.FAC_TITLE)+" ";
                list.add(task);
                task="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public LinkedList<String> showAllSpec() {
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT " + Constant.SPEC_TITLE+" , "+ Constant.SPEC_FAC + " FROM "
                + Constant.SPECIALTY_TABLE ;
        ResultSet rs = null;
        String task="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                task+=rs.getString(Constant.SPEC_TITLE)+" ";
                task+=rs.getString(Constant.SPEC_FAC)+" ";
                list.add(task);
                task="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void deleteMark(int id)  {

        String selectSQL = "DELETE FROM "+Constant.MARK_TABLE +  " WHERE id = ?";
        try {
            connection.prepareStatement(selectSQL);
            PreparedStatement preparedStmt = connection.prepareStatement(selectSQL);
            preparedStmt.setInt(1, id);
            preparedStmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public LinkedList<String> showMarks() {

        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT "+Constant.MARK_ID+" , "+Constant.MARK_NAME+" , " + Constant.MARK_SURNAME+" , "+Constant.MARK_NUMBER+" , "+Constant.MARK_SUBJECT+" , "+Constant.MARK
                + " FROM " + Constant.MARK_TABLE ;
        ResultSet rs = null;
        String contribution="";
        try {
            rs = statement.executeQuery(query);

            while (rs.next()) {

                contribution+=rs.getString(Constant.MARK_ID)+" ";
                contribution+=rs.getString(Constant.MARK_NAME)+" ";
                contribution+=rs.getString(Constant.MARK_SURNAME)+" ";
                contribution+=rs.getString(Constant.MARK_NUMBER)+" ";
                contribution+=rs.getString(Constant.MARK_SUBJECT)+" ";
                contribution+=rs.getString(Constant.MARK)+" ";
                list.add(contribution);
                contribution="";
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }

}
