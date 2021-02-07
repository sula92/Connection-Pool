package lk.ijse.dep.web.service.listner;

import lk.ijse.dep.web.service.DBCP;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //System.out.println("Context is being initialized");
        sce.getServletContext().setAttribute("pool", new DBCP());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       // System.out.println("COntext is being destroyed");
        DBCP dbcp = (DBCP) sce.getServletContext().getAttribute("pool");
        dbcp.releaseAllConnection();
    }
}
