// very simple hello world app
package org.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class DbSample extends HttpServlet {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Context initContext = new InitialContext();

            DataSource ds = (DataSource) initContext.lookup("jdbc/mydbDS");
            if (ds == null) {
                throw new Exception("Data source not found!");
            }
            Connection conn = ds.getConnection();
            String selectSQL = "SELECT ID, NAME FROM mytable";
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            resp.getWriter().print("<b>Userid: Username</b><br>");
            while (rs.next()) {
                String userid = rs.getString("ID");
                String username = rs.getString("NAME");
                resp.getWriter().print(userid + ":" + username + "<br>");
            }
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
