package sample;

import sample.model.*;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ConnectionToServer {
    private BufferedReader in;
    private BufferedWriter out;

    public ConnectionToServer(Socket socket){
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String message) {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserInDb() {
        try {
            if(in.readLine().equals("1")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkAddUserInDb(){
        try {
            if(in.readLine().equals("1")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getIdAndRoll(String email){
        try {

            String[] subStr = in.readLine().split(" ");
            User.currentUser= new User(email,Integer.parseInt(subStr[0]),Integer.parseInt(subStr[1]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<User> showAllUsers() throws IOException {
        LinkedList<User> lisUsers = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStringUsers(in.readLine(),lisUsers);
        }
        return lisUsers;
    }

    private void parseStringUsers(String readLine, LinkedList<User> lisUsers) {
        String[] subStr;
        subStr = readLine.split(" ");
        lisUsers.add(new User(Integer.parseInt(subStr[0]),subStr[1],subStr[2],subStr[3],subStr[4], subStr[5],Integer.parseInt(subStr[6])));
    }


    public LinkedList<Mark> showMarks() throws IOException {
        LinkedList<Mark> listTask = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseMark(in.readLine(), listTask);
        }
        return listTask;
    }

    private void parseMark(String str, LinkedList<Mark> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add(new Mark(Integer.parseInt(subStr[0]), subStr[1], subStr[2], subStr[3], subStr[4], subStr[5]));
    }

    public LinkedList<Faculty> showAllFaculties() throws IOException {
        LinkedList<Faculty> listTask = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseFaculty(in.readLine(), listTask);
        }
        return listTask;
    }

    private void parseFaculty(String str, LinkedList<Faculty> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add(new Faculty(subStr[0]));
    }

    public LinkedList<Specialty> showAllSpec() throws IOException {
        LinkedList<Specialty> listTask = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseSpec(in.readLine(), listTask);
        }
        return listTask;
    }

    private void parseSpec(String str, LinkedList<Specialty> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add(new Specialty(subStr[0], subStr[1]));
    }

    public LinkedList<Student> showStudents() throws IOException {
        LinkedList<Student> listTask = new LinkedList<>();

        int sizeList = Integer.parseInt(in.readLine());
        for(int i=0;i<sizeList;i++){
            parseStudent(in.readLine(), listTask);
        }
        return listTask;
    }

    private void parseStudent(String str, LinkedList<Student> list){
        String[] subStr;
        subStr = str.split(" ");
        list.add(new Student(Integer.parseInt(subStr[0]), subStr[1], subStr[2], subStr[3], subStr[4], subStr[5]));
    }
}
