package com.sociallibrary.crud;

import com.sociallibrary.connection.ConnectionProvider;
import com.sociallibrary.entity.*;
//import com.sociallibrary.icrud.*;
import org.apache.log4j.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Time;
//import java.sql.Timestamp;

public class CatalogCRUD implements ICatalogCRUD {

    private Connection connection;
    public static final Logger log = Logger.getLogger(CatalogCRUD.class);
    private static final String selectQuery = "SELECT * FROM catalog WHERE id=?";
    private static final String deleteQuery = "DELETE FROM catalog WHERE id =?";
    private static final String insertCatalogQuery = "INSERT INTO catalog VALUES(catalog_id.nextval, ?,?,?,?)";
    private static final String updateCatalogQuery = "UPDATE catalog SET users=?, book=?, event_time=?, status=?, WHERE id=?";

     public  CatalogCRUD()
    {
        connection = ConnectionProvider.getConnection();
    }
     
    public void createCatalog(Catalog catalog) {
        BasicConfigurator.configure();
        try
        {
            PreparedStatement pstmt = connection.prepareStatement(insertCatalogQuery);

            pstmt.setLong(1, catalog.getUser().getId());
            pstmt.setLong(2, catalog.getBook().getId());
            pstmt.setTimestamp(3, catalog.getEventTime());
            pstmt.setInt(4, catalog.getStatus().getId());

            pstmt.executeUpdate();

            pstmt.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public Catalog readCatalog(int id) {
        BasicConfigurator.configure();
        Catalog catalog = new Catalog();
        try {
            PreparedStatement stmt = connection.prepareStatement(selectQuery);

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                catalog.setId(rs.getLong(1));
                catalog.setUser(new UserCRUD().readUser(rs.getInt(2)));
                catalog.setBook(new LibraryCRUD().readLibrary(rs.getInt(3)));
                catalog.setEventTime(rs.getTimestamp(4));
                catalog.setStatus(new BookStatusCRUD().readBookStatus(rs.getInt(5)));
            }
            rs.close();
            stmt.close();

        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
        return catalog;
    }

    public void updateCatalog(Catalog catalogOld, Catalog catalogNew) {
        BasicConfigurator.configure();
        try {
            PreparedStatement pstmt = connection.prepareStatement(updateCatalogQuery);
            pstmt.setLong(1, catalogOld.getUser().getId());
            pstmt.setLong(2, catalogOld.getBook().getId());
            //pstmt.setShort(3, catalogOld.getStatus().getId());
            pstmt.setLong(4, catalogOld.getId());

            pstmt.executeUpdate();

            pstmt.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }

    public void deleteCatalog(Catalog catalog) {
        BasicConfigurator.configure();
        try {
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);

            stmt.setLong(1, catalog.getId());

            stmt.executeUpdate();

            stmt.close();
        }
         catch (SQLException e)
        {
                e.printStackTrace();
                log.error("SQLException:" + e);
        }
        
    }
}

