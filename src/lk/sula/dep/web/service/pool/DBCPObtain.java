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

@WebServlet(urlPatterns = "/dbcp-obtain")
public class DBCPObtain extends HttpServlet {

    private int connectionId = 0;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBCP dbcp = (DBCP)getServletContext().getAttribute("pool");
        Connection connection = dbcp.obtainConnection();
        String connectionName = "c" + connectionId;
        connectionId++;
        getServletContext().setAttribute(connectionName, connection);
        try (PrintWriter out = resp.getWriter()){
            out.println("<h1>Connection has obtain successfully</h1>");
            out.println("<hr>");
            out.println("<a href=\"http://localhost:8080/pool/dbcp-release?cname=" + connectionName +"\">Click to release the connection</a>");
        }
    }
}
