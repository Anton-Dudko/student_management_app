import java.sql.*;

public class Tables {
    private Connection connection;
    private Statement statement;

    public Tables(Connection connection, Statement statement){
        this.connection = connection;
        this.statement = statement;

        createTableUsers();
        createTableFaculty();
        createTableSpecialty();
        createTableStudents();
        createTableMarks();
    }

    public void createTableUsers(){
        if(tableExist(Constant.USERS_TABLE)){
            try {
                String sql = "CREATE TABLE " + Constant.USERS_TABLE + "( "
                        + " id SERIAL PRIMARY KEY, "
                        + " name VARCHAR (30), "
                        + " surname VARCHAR (30), "
                        + " lastname VARCHAR (30), "
                        + " login VARCHAR (30), "
                        + " password VARCHAR (30), "
                        + " roll VARCHAR (30)"
                        + ")";
                statement.executeUpdate(sql);
                System.out.println("Таблица " + Constant.USERS_TABLE + " создана !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTableFaculty(){

        if(tableExist(Constant.FACULTY_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+Constant.FACULTY_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " title VARCHAR (100)" +
                        ")";

                statement.executeUpdate(SQL);
                System.out.println("Таблица  была создана ! " +Constant.FACULTY_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTableSpecialty(){

        if(tableExist(Constant.SPECIALTY_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+Constant.SPECIALTY_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " title VARCHAR (30)," +
                        " faculty VARCHAR (30)" +
                        ")";

                statement.executeUpdate(SQL);
                System.out.println("Таблица  была создана ! " +Constant.SPECIALTY_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTableStudents(){
        if(tableExist(Constant.STUDENTS_TABLE)){
            try {
                String sql = "CREATE TABLE " + Constant.STUDENTS_TABLE + "( "
                        + " id SERIAL PRIMARY KEY, "
                        + " name VARCHAR (30), "
                        + " surname VARCHAR (30), "
                        + " faculty VARCHAR (30), "
                        + " specialty VARCHAR (30), "
                        + " group_number VARCHAR (30)"
                        + ")";
                statement.executeUpdate(sql);
                System.out.println("Таблица " + Constant.STUDENTS_TABLE + " создана !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTableMarks(){
        if(tableExist(Constant.MARK_TABLE)){
            try {
                String sql = "CREATE TABLE " + Constant.MARK_TABLE + "( "
                        + " id SERIAL PRIMARY KEY, "
                        + " name VARCHAR (30), "
                        + " surname VARCHAR (30), "
                        + " group_number VARCHAR (30), "
                        + " subject VARCHAR (30), "
                        + " mark INTEGER"
                        + ")";
                statement.executeUpdate(sql);
                System.out.println("Таблица " + Constant.MARK_TABLE + " создана !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean tableExist(String name){
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, name, null);
            resultSet.last();
            return resultSet.getRow() <= 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
