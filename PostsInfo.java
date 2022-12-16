import Servlet.HttpServlet;
import Servlet.HttpServletRequest;
import Servlet.HttpServletResponse;
import Usage.Dao;
import Usage.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostsInfo extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Dao dao = new Dao();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getPathInfo();
        PrintWriter out = response.getWriter();

        Pattern p = Pattern.compile("(\\w+)");
        Matcher match = p.matcher(path);
        ArrayList<String> arr = new ArrayList<>();
        while (match.find()) {
            arr.add(match.group());
        }

        switch (arr.size()) {
            case 1:
                sendAllPost(out, request);
            case 2:
                showPostById(arr.get(0), out, request);
            case 3:
                showCommentsForPost(arr.get(0), request, response);
            default:
                sendError(out);
        }
    }

    private void sendAllPost(PrintWriter out, HttpServletRequest request) {
        List<Post> eL = dao.getAllPosts();
        String jsonInString = gson.toJson(eL);
        out.println(jsonInString);
        out.flush();
    }

    private void showCommentsForPost(String idString, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(idString);
        //RequestDispatcher dispatcher = request.getRequestDispatcher("/comments?postId=" + id);
        //dispatcher.forward(request, response);
    }

    private void showPostById(String idString, PrintWriter out, HttpServletRequest request) {
        int id = Integer.parseInt(idString);
        Post e = dao.getPostById(id);
        String jsonInString = gson.toJson(e);
        out.println(jsonInString);
        out.flush();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!getToken(request)) {
            //logger.error("No such user");
            return;
        }

        PrintWriter out = response.getWriter();
        //Dao d = new Dao();
        StringBuilder sb1 = new StringBuilder();
        String js = request.getReader().readLine();
        while (js != null) {
            sb1.append(js);
            js = request.getReader().readLine();
        }

        //Post post = gson.fromJson(sb1.toString(), Post.class);
        //d.insertPost(post);
        //out.println(gson.toJson(post.id));
        //HttpSession session = request.getSession(false);
        //logger.info((String) session.getAttribute("username") + " POST " + request.getPathInfo());
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!getToken(request)) {
            //logger.error("No such user");
            return;
        }

        PrintWriter out = response.getWriter();
        //Dao d = new Dao();
        String path = request.getPathInfo();


        if (path == null) {
            //String jsonInString = gson.toJson(null);
            //out.println(jsonInString);
            return;
        }
        Pattern pattern = Pattern.compile("([a-zA-z1-9]+)*");
        Matcher match = pattern.matcher(path);
        int id = Integer.parseInt(match.group());

        StringBuilder sb1 = new StringBuilder();
        String js = request.getReader().readLine();
        while (js != null) {
            sb1.append(js);
            js = request.getReader().readLine();
        }

        //Post post = gson.fromJson(sb1.toString(), Post.class);
        //post.id = id;
        //d.updatePost(post, id);
        //out.println(gson.toJson(post));

        //HttpSession session = request.getSession(false);
        //logger.info((String) session.getAttribute("username") + " PUT " + request.getPathInfo());
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!getToken(request)) {
            //logger.error("No such user");
            return;
        }

        String path = request.getPathInfo();
        //Dao ed = new Dao();
        PrintWriter out = response.getWriter();

        if (path == null) {
            sendError(out);
            return;
        }

        Pattern pattern = Pattern.compile("([a-zA-z1-9]+)*");
        Matcher match = pattern.matcher(path);
        int id = Integer.parseInt(match.group());

        //int n = ed.deletePost(id);
        //String jsonInString = gson.toJson(n);
        //out.println(jsonInString);

        //HttpSession session = request.getSession(false);
        //logger.info((String) session.getAttribute("username") + " DELETE " + request.getPathInfo());
    }

    private void sendJson(HttpServletResponse response, String message) throws IOException {
        PrintWriter out = response.getWriter();
        //String jsonInString = gson.toJson(message);
        //out.println(jsonInString);

    }

    private void sendError(PrintWriter out) {
        //String jsonInString = gson.toJson(null);
        //out.println(jsonInString);
    }

    private boolean getToken(HttpServletRequest request) {
        String reqToken = request.getHeader("Authorization");
        //String token = dao.getToken(reqToken).token;
        //if (token == null) {
        //return false;
        //}
        return true;
    }
}
