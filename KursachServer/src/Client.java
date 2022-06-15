import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Client extends Thread{
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private DataBaseHandler dataBaseHandler;
    private int currentCount = ServerStart.countClient++;
    private Boolean checkAuth =false;

    public Client(Socket socket, DataBaseHandler dataBaseHandler) throws IOException {
        this.socket = socket;
        this.dataBaseHandler = dataBaseHandler;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        start();
    }

    @Override
    public void run(){
        try {
            workWithPersonal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void workWithPersonal() throws IOException{
        while(true){
            switch (in.readLine()){
                case "auth":
                    checkAuth();
                    break;
                case "showAllUser":
                    showAllUsers();
                    break;
                case "redactionUser":
                    redactionUser();
                    break;
                case "deleteUser":
                    deleteUser();
                    break;
                case "showMarksByGroup":
                    showMarksByGroup();
                    break;
                case "addUser":
                    addUserInDb();
                    break;
                case "showFaculties":
                    showAllFacs();
                    break;
                case "deleteMark":
                    deleteMark();
                    break;
                case "addMark":
                    addMark();
                    break;
                case "redactionMark":
                    redactionMark();
                    break;
                case "showMarks":
                    showMarks();
                    break;
                case "showSpec":
                    showAllSpec();
                    break;
                case "addSpec":
                    addSpec();
                    break;
                case "showSpecByFac":
                    showSpecByFac();
                    break;
                case "showStudents":
                    showStudents();
                    break;
                case "addStudent":
                    addStudent();
                    break;

            }
        }
    }

    private void checkAuth(){
        try {
            String[] subStr = in.readLine().split(" ");
            String check= dataBaseHandler.authUser(subStr[0], subStr[1]);
            if (!check.equals("false")) {
                checkAuth = true;
                ServerStart.clientList.get(currentCount).send("1");
                ServerStart.clientList.get(currentCount).send(check);
            } else {
                ServerStart.clientList.get(currentCount).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllUsers(){
        LinkedList<String> list = dataBaseHandler.showAllUsers();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }

    private void showStudents(){
        LinkedList<String> list = dataBaseHandler.showStudents();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }

    private void redactionUser(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            dataBaseHandler.redactionUser(Integer.parseInt(subStr[0]),subStr[1],subStr[2],subStr[3],subStr[4],subStr[5], Integer.parseInt(subStr[6  ]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(){
        try {
            dataBaseHandler.deleteUser(Integer.parseInt(in.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addUserInDb(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            System.out.println(subStr);
            if(validateInput(subStr[0],subStr[1],subStr[2],subStr[3],subStr[4])) {
                if (dataBaseHandler.addUserInDb(subStr[0], subStr[1], subStr[2], subStr[3], subStr[4], "0")) {
                    ServerStart.clientList.get(currentCount).send("1");
                } else {
                    ServerStart.clientList.get(currentCount).send("0");
                }
            }
            else{
                ServerStart.clientList.get(currentCount).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMark(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            System.out.println(subStr);
            if(validateInput(subStr[0],subStr[1],subStr[2],subStr[3],subStr[4])) {
                if (dataBaseHandler.addMark(subStr[0], subStr[1], subStr[2], subStr[3], Integer.parseInt(subStr[4]))) {
                    ServerStart.clientList.get(currentCount).send("1");
                } else {
                    ServerStart.clientList.get(currentCount).send("0");
                }
            }
            else{
                ServerStart.clientList.get(currentCount).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addStudent(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            System.out.println(subStr);
            if(validateInput(subStr[0],subStr[1],subStr[2],subStr[3],subStr[4])) {
                if (dataBaseHandler.addStudent(subStr[0], subStr[1], subStr[2], subStr[3], subStr[4])) {
                    ServerStart.clientList.get(currentCount).send("1");
                } else {
                    ServerStart.clientList.get(currentCount).send("0");
                }
            }
            else{
                ServerStart.clientList.get(currentCount).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSpec(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            System.out.println(subStr);
            if(validateInput1(subStr[0],subStr[1])) {
                if (dataBaseHandler.addSpec(subStr[0], subStr[1])) {
                    ServerStart.clientList.get(currentCount).send("1");
                } else {
                    ServerStart.clientList.get(currentCount).send("0");
                }
            }
            else{
                ServerStart.clientList.get(currentCount).send("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redactionMark(){
        String[] subStr;
        try {
            subStr = in.readLine().split(" ");
            dataBaseHandler.redactionMark(Integer.parseInt(subStr[0]),subStr[1],subStr[2],subStr[3],subStr[4],Integer.parseInt(subStr[5]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllFacs(){
        LinkedList<String> list = dataBaseHandler.showAllTasks();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){

            ServerStart.clientList.get(currentCount).send(s);

        }
    }

    private void showAllSpec(){
        LinkedList<String> list = dataBaseHandler.showAllSpec();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){

            ServerStart.clientList.get(currentCount).send(s);

        }
    }

    private void deleteMark(){
        try {
            dataBaseHandler.deleteMark(Integer.parseInt(in.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMarks() throws IOException {
        LinkedList<String> list = dataBaseHandler.showMarks();
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }

    private void showMarksByGroup() throws IOException {
        String[] str;
        str=in.readLine().split(" ");
        LinkedList<String> list = dataBaseHandler.showMarksByGroup(str[0], str[1]);
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }

    private void showSpecByFac() throws IOException {
        String[] str;
        str=in.readLine().split(" ");
        LinkedList<String> list = dataBaseHandler.showSpecByFac(str[0]);
        ServerStart.clientList.get(currentCount).send(String.valueOf(list.size()));
        for(String s:list){
            ServerStart.clientList.get(currentCount).send(s);
        }
    }

    private boolean validateInput(String name, String surname, String lastname, String login, String password){

        if(!name.equals("") || !surname.equals("") || !lastname.equals("")  || !login.equals("")  || !password.equals("")) {
            return true;
        }else {
            return false;
        }

    }

    private boolean validateInput1(String name, String surname){

        if(!name.equals("") || !surname.equals("")) {
            return true;
        }else {
            return false;
        }

    }

    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }

}
