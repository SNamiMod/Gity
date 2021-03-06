package com.company;
import java.io.*;
import java.util.Scanner;

/**
 * Gity !
 * in this class we handle clients requests for Gity
 *
 * Network Project
 *
 *
 * @author Seyed Nami Modarressi
 * @version 1.0
 *
 * Spring 2021
 */
public class Client {

    private String name;
    private String command;

    /**
     * create new client
     * @param name name of client
     * @param command command to run
     * @throws IOException can not use files
     */
    public Client(String name, String command) throws IOException {
        this.name = name;
        this.command = command;
    }

    /**
     * run command
     * @return result of command
     * @throws IOException can not use file
     */
    public String run() throws IOException {

        if (command.contains("ls -u")) {
            Request req = new Request();
            String result = req.getUsers();
            String temp = "Gity Users : \n\n1 ) ";
            int i = 2;
            for (char c : result.toCharArray()) {
                if (c != ' ') {
                    temp = temp + c;
                } else {
                    temp = temp + "\n" + i + " ) ";
                    i++;
                }
            }
            return temp;

        }

        if (command.contains("ls -r")) {
            String[] data = command.split(" ", 3);
            Request req = new Request();
            String result = req.getRepos(data[2]);
            if (result.equals("")) {
                result = "Repositories : \n\n Nothing :(";
                return result;
            } else {
                String temp = "Repositories : \n\n1 ) ";
                int i = 2;
                for (char c : result.toCharArray()) {
                    if (c != ' ') {
                        temp = temp + c;
                    } else {
                        temp = temp + "\n" + i + " ) ";
                        i++;
                    }
                }
                return temp;

            }
        }

        if (command.contains("mkrepo")) {
            int code = 1;
            String[] data = command.split(" ", 3);
            if (data[1].equals("-public")) {
                code = 1;
            }
            if (data[1].equals("-private")) {
                code = 2;
            }
            if (data[2].contains(" ")) {
                return "Please don't use space in name of repository";
            } else {
                Request req = new Request();
                int result = req.makeRepo(name, data[2], code);
                if (result == 0) {
                    return "Can not create repository ! :(\nPlease Try again";
                } else {
                    return "Repository Created successfully ! :)";
                }
            }
        }

        if (command.contains("addc")) {
            String[] data = command.split(" ", 3);
            Request req = new Request();
            int result = req.addContributor(name, data[1], data[2]);
            if (result == 1) {
                return "Contributor added successfully ! :)";
            } else {
                return "Can not add contributor :( please try again";
            }
        }

        if (command.contains("chm")) {
            String[] data = command.split(" ", 3);
            Request req = new Request();
            int x = 0;
            if (data[2].equals("private")) {
                x = 2;
            }
            if (data[2].equals("public")) {
                x = 1;
            }
            int result = req.changeMode(name, data[1], x);
            if (result == 1) {
                return "Repository changed ! :)";
            } else {
                return "Can not change Repository :( please try again";
            }
        }
        if (command.contains("get -i")) {
            String[] data = command.split(" ", 3);
            Request req = new Request();
            String result = req.getRepoInfo(name, data[2]);
            if (result.equals("Can not get information :( Please try again")) {
                return "Can not get information :( Please try again";
            } else {
                String[] out = result.split(" ");
                String output = "Information of Repository :\n";
                output = output + "Mode : " + out[0] + "\n";
                output = output + "Contributor(s) : \n";
                for (int i = 1; i < out.length; i++) {
                    output = output + i + " ) " + out[i] + "\n";
                }
                return output;
            }
        }
        if (command.contains("removec")) {
            int result;
            String[] data = command.split(" ", 3);
            if (name.equals(data[2])) {
                result = 0;
            } else {
                Request req = new Request();
                result = req.removeContributor(name, data[1], data[2]);
            }
            if (result == 1) {
                return "Contributor Removed ! :)";
            } else {
                return "Can not remove contributor :( please try again";
            }
        }
        if (command.contains("mkdir")) {
            String[] data = command.split(" ", 3);
            Request req = new Request();
            int result = req.makeDir(name, data[1], data[2]);
            if (result == 1) {
                return "Directory created ! :)";
            } else {
                return "Can not create directory :( please try again";
            }

        }
        if (command.contains("get -c")) {
            String[] data = command.split(" ", 4);
            Request req = new Request();
            String result = req.getCommits(name, data[2], data[3]);
            if (result.equals("")) {
                return "Can not get commits of Repository ! :( Please Try again";
            } else {
                String[] out = result.split(" ");
                String output = "Commits : \n";
                int i = 1;
                for (String s : out) {
                    output = output + i + " ) " + s + "\n";
                    i++;
                }
                return output;
            }
        }
        if (command.contains("c&p")) {
            String[] data = command.split(" ", 6);
            String[] repo = data[2].split("/");
            Request req = new Request();
            int result = req.possibleCommit(name, repo[0], data[3]);
            if (result == 1) {
                req.sendFile(name, data[1], data[2], data[3], data[4], data[5]);
                return "Done ! :)";
            } else {
                return "can not commit ! :( Please try again ";
            }
        }
        if (command.contains("pull")) {
            String[] data = command.split(" ", 3);
            Request req = new Request();
            int result = req.possiblePull(name, data[1], data[2]);
            if (result == 1) {
                req.pull(name, data[1], data[2]);
                return "Done ! :)";
            } else {
                return "can not pull ! :( Please try again ";
            }
        }
        if (command.contains("download")) {
            String[] data = command.split(" ", 4);
            Request req = new Request();
            int result = req.possibleDownload(name, data[1], data[2], data[3]);
            if (result == 1) {
                req.download(name, data[1], data[2], data[3]);
                return "Done ! :)";
            } else {
                return "can not download file ! :( Please try again ";
            }
        }
        if (command.contains("update")) {
            String[] data = command.split(" ", 3);
            Request req = new Request();
            int result = req.possiblePull(name, data[1], data[2]);
            if (result == 1) {
                int x = req.needUpdate(name, data[1], data[2]);
                if (x == 1) {
                    req.pull(name, data[1], data[2]);
                    return "Done ! :)";
                } else if (x == 2) {
                    return "already updated !";
                }else {
                    return "can not get from server ! ...";
                }
            } else {
                return "can not update ! :( Please try again ";
            }
        }
        if (command.contains("get -m")){
            String result = "";
            String[] data = command.split(" ",3);
            FileReader fileReader = new FileReader("Data/Client/" + name + "/" + data[2] + "/RepoData.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNext()) {
                String code = getString.nextLine();
                String cNumber = getString.nextLine();
                for (int i = 0; i < Integer.parseInt(cNumber); i++) {
                    String cName = getString.nextLine();
                }
                String coNumber = getString.nextLine();
                if (Integer.parseInt(coNumber) != 0) {
                    for (int i = 0; i < Integer.parseInt(coNumber); i++) {
                        String coName = getString.nextLine();
                        if (coName.contains(name)){
                            result=result+coName+"\n";
                        }
                    }
                }
            }
            getString.close();
            fileReader.close();
            return result;
        }
        return "Command Not Found !  \nPlease use help button to see available commands";

    }

    /**
     * login user
     * @param username username
     * @param password password
     * @return possible or not
     * @throws IOException can not use files
     */
    public static int login(String username, String password) throws IOException {
        Request req = new Request();
        return req.login_Register(0,username,password);
    }
    /**
     * register new user
     * @param username username
     * @param password password
     * @return possible or not
     * @throws IOException can not use files
     */
    public static int register(String username, String password) throws IOException {
        Request req = new Request();
        return req.login_Register(1,username,password);
    }
}