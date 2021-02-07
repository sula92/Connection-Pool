package lk.ijse.dep.web.service.pool;

import lk.ijse.dep.web.service.DBCP;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(urlPatterns = "/dbcp-release")
public class DBCPRelease extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cname = req.getParameter("cname");
        Connection connection = (Connection) getServletContext().getAttribute("cname");
        DBCP dbcp = (DBCP) getServletContext().getAttribute("pool");
        dbcp.releaseConnection(connection);
        getServletContext().removeAttribute(cname);
        try (PrintWriter out = resp.getWriter()){
            out.println("<h1>Connection has obtain successfully</h1>");
            out.println("<hr>");
            out.println("<a href=\"http://localhost:8080/pool/\">Go to home page</a>");
        }
    }
}
