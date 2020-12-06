import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
/**
 * @Description: //TODO  简单的HTTP服务器， 其实就是接受HTTP请求，并解析请求，
 * @Author: xiaolin
 * @Date: 2020/12/6 16:35
 * @Param:
 * @Return:
 */
class server_http {
 
    public static void main(String[] args) {
        try {
 
            /*监听端口号，只要是8888就能接收到*/
            ServerSocket ss = new ServerSocket(8888);
 
            while (true) {
                /*实例化客户端，固定套路，通过服务端接受的对象，生成相应的客户端实例*/
                Socket socket = ss.accept();
                /*获取客户端输入流，就是请求过来的基本信息：请求头，换行符，请求体*/
                BufferedReader bd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
                /**
                 * 接受HTTP请求，并解析数据
                 */
                String requestHeader;
                int contentLength = 0;
                String text = "服务器已经启动了BY小林";
                //Scanner sc = new Scanner(new FileReader("html.txt"));//读取

                while ((requestHeader = bd.readLine()) != null && !requestHeader.isEmpty()) {
                    System.out.println(requestHeader);
                    /**
                     * 获得GET参数
                     */
                    //get_user get = new get_user();
                    if (requestHeader.startsWith("GET")) {//请求方法
                        int begin = requestHeader.indexOf("/?") + 2;//去掉本身的然后开始寻找参数返回一个int
                        int end = requestHeader.indexOf("HTTP/");//目录模型构建
                        String condition = requestHeader.substring(begin, end);//返回可能的参数值为
                        /**
                         * if（begin=一个参数时执行）{写逻辑代码}
                         */
                        System.out.println("GET参数是：" + condition);
                    }
                    /**
                     * 获得POST参数
                     * 1.获取请求内容长度
                     */
                    if (requestHeader.startsWith("Content-Length")) {
                        int begin = requestHeader.indexOf("Content-Lengh:") + "Content-Length:".length();
                        String postParamterLength = requestHeader.substring(begin).trim();
                        contentLength = Integer.parseInt(postParamterLength);
                        System.out.println("POST参数长度是：" + Integer.parseInt(postParamterLength));
                    }
                }
                StringBuffer sb = new StringBuffer();
                if (contentLength > 0) {
                    for (int i = 0; i < contentLength; i++) {
                        sb.append((char) bd.read());
                    }
                    System.out.println("POST参数是：" + sb.toString());
                }
                /*发送回执*/
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
 
                pw.println("HTTP/1.1 200 OK");
                pw.println("Content-type:text/html");
                pw.println("Server-type:xiaolin");//报文
                pw.println();
                pw.println(text);//返回的参数
                System.out.println(text);
 
                pw.flush();
                socket.close();//socket关闭连接
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //class post_usere{//构造post方法

    //}
    class get_user{//构造请求方法
        private String requestHeader;
        private int contentLength;
        int begin = requestHeader.indexOf("/?") + 2;
        private int end = requestHeader.indexOf("HTTP/");
        private String condition = requestHeader.substring(begin, end);
    }
}