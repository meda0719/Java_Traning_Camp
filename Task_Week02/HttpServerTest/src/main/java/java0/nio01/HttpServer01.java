package java0.nio01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class HttpServer01 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8801);
        while (true){
            try {
                Socket socket = serverSocket.accept();
                service(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void service(Socket socket){
        try {

            InputStreamReader inputStreamReader= new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            String header = null;
            //获取post参数name
            String name = null;
            while (true) {
                header = br.readLine();
                System.out.println(header);
                if (header.contains("GET")){
                    //获取client端post参数name
                    name = getClientName(header);
                }
                if (header == null || header.equals("") || header == "\r\n" || header == "\n") {
                    break;
                }
            }
            //响应client端，返回字符串
            responseWithName(socket, name);
            inputStreamReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getClientName(String str){
        int index;
        String[] astr = str.split(" ");
        for (String ss : astr
             ) {
            if ((index = ss.indexOf("=")) != -1){

                return ss.substring(index+1, ss.length());
            }
        }
        return "";

    }

    private static void responseWithName(Socket socket, String name) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Type:text/html;charset=utf-8");
        String body = "hello, " + name;
        printWriter.println("Content-Length:" + body.getBytes().length);
        printWriter.println();
        printWriter.write(body);
        printWriter.close();
    }
}
