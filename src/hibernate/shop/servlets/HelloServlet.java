package hibernate.shop.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet{
    @Override
    //  HttpServletRequest zawiera dane od klienta (przegladarki)
    //  np parametry, dane z formularza, ciastka,
    //  HttpServletResponse zawiera dane ktore odeslemy do klienta (pregladarki)
    //  mozemy wyslac kod strony html, ciastka, zrobic przekierowanie
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write("<html><head></head><body><h1>hello 44</h1></body></html>");

    }
}
