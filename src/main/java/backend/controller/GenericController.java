package backend.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import backend.model.db.Database;


public abstract class GenericController<T> {
    
    protected final String tableName;
    
 GenericController(String tableName) {
        this.tableName = tableName;
    }
    

    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    protected abstract String getInsertSQL();
    
    protected abstract void setInsertParameters(PreparedStatement stmt, T entity) throws SQLException;
    
    protected abstract String getUpdateSQL();
    
    protected abstract void setUpdateParameters(PreparedStatement stmt, T entity, int id) throws SQLException;
    
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " ORDER BY id DESC";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                T entity = mapResultSetToEntity(rs);
                list.add(entity);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    

    public boolean add(T entity) {
        String sql = getInsertSQL();
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setInsertParameters(stmt, entity);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean delete(int id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean update(T entity, int id) {
        String sql = getUpdateSQL();
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            setUpdateParameters(stmt, entity, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public T findById(int id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    

    public boolean exists(int id) {
        return findById(id) != null;
    }
}