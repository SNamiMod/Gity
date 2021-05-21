package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Gity !
 * in this class we handle server for Gity
 *
 * Network Project
 *
 *
 * @author Seyed Nami Modarressi
 * @version 1.0
 *
 * Spring 2021
 */
public class Server {

    private ServerSocket serversocket;
    private BufferedReader input;
    private PrintWriter output;

    public static void main(String[] args){
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * start server
     * @throws IOException cant read files
     */
    public void start() throws IOException {
        serversocket = new ServerSocket(1235);
        System.out.println("Connection Starting on port:" + serversocket.getLocalPort());
        while (true) {
            Socket client = serversocket.accept();
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            try {
                handle(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * handle request
     * @return result
     * @throws IOException cant read files
     */

    public int handle(Socket server) throws IOException {

        String code = input.readLine();

        if (code.equals("1")){
            System.out.println("Login request ...");
            String user = input.readLine();
            String pass = input.readLine();
            FileHandler p = new FileHandler();
            int x = p.login(user,pass);
            output.println(x);
        }
        if (code.equals("2")){
            System.out.println("Register request ...");
            String user = input.readLine();
            String pass = input.readLine();
            FileHandler p = new FileHandler();
            int x = p.register(user,pass);
            output.println(x);
        }
        if (code.equals("3")){
            System.out.println("Get users request ...");
            FileHandler p = new FileHandler();
            String x = p.getUsers();
            output.println(x);
        }
        if (code.equals("4")){
            String name = input.readLine();
            System.out.println("Get Repositories request ...");
            FileHandler p = new FileHandler();
            String x = p.getRepos(name);
            output.println(x);
        }
        if (code.equals("5")){
            String username = input.readLine();
            String name = input.readLine();
            String repoCode = input.readLine();
            System.out.println("Make new Repository request ...");
            FileHandler p = new FileHandler();
            int x = p.makeRepo(username,name,Integer.parseInt(repoCode));
            output.println(x);
        }
        if (code.equals("6")){
            String username = input.readLine();
            String repoName = input.readLine();
            String name = input.readLine();
            System.out.println("Add new contributor request ...");
            FileHandler p = new FileHandler();
            int x = p.addContributor(username,repoName,name);
            output.println(x);
        }
        if (code.equals("7")){
            String username = input.readLine();
            String repoName = input.readLine();
            int mode = Integer.parseInt(input.readLine());
            System.out.println("Change Repository mode request ...");
            FileHandler p = new FileHandler();
            int x = p.changeMode(username,repoName,mode);
            output.println(x);
        }
        if (code.equals("8")){
            String username = input.readLine();
            String repoName = input.readLine();
            System.out.println("get information of Repository request ...");
            FileHandler p = new FileHandler();
            output.println(p.getInfo(username,repoName));

        }
        if (code.equals("9")){
            String username = input.readLine();
            String repoName = input.readLine();
            String name = input.readLine();
            System.out.println("remove contributor request ...");
            FileHandler p = new FileHandler();
            int x = p.removeContributor(username,repoName,name);
            output.println(x);
        }
        if (code.equals("10")){
            String username = input.readLine();
            String repoName = input.readLine();
            String name = input.readLine();
            System.out.println("new directory request ...");
            FileHandler p = new FileHandler();
            int x = p.addDir(username,repoName,name);
            output.println(x);
        }
        if (code.equals("11")){
            String username = input.readLine();
            String repoName = input.readLine();
            String user = input.readLine();
            System.out.println("get Commits request ...");
            FileHandler p = new FileHandler();
            String x = p.getCommits(username,repoName,user);
            output.println(x);
        }
        if (code.equals("12")) {
            String username = input.readLine();
            String repoName = input.readLine();
            String user = input.readLine();
            System.out.println("can you commit ? request ...");
            FileHandler p = new FileHandler();
            int x = p.possibleCommit(username,repoName,user);
            output.println(x);
        }
        if (code.equals("13")){
            String username = input.readLine();
            String message = input.readLine();
            String repoAddress = input.readLine();
            String user = input.readLine();
            String fileAddress = input.readLine();
            String fileName = input.readLine();
            System.out.println("push request ...");
            FileHandler p = new FileHandler();
            p.addCommit(username,message,repoAddress,user,fileAddress,fileName);
            BufferedInputStream bis = new BufferedInputStream(server.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("Data/Server/"+user+"/"+repoAddress+"/"+fileName));
            byte[] b = new byte[1024 * 8];
            int len;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            bos.close();
            bis.close();
            System.out.println("Upload succeeded");
        }

        output.flush();
        output.close();
        return 0;

    }



}