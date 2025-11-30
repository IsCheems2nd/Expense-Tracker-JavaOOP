package backend.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import backend.db.Database;

/**
 * Generic Controller for CRUD operations on database entities.
 * This class provides common database operations that can be reused
 * for different entity types.
 * 
 * @param <T> The entity type this controller manages
 */
public abstract class GenericController<T> {
    
    protected final String tableName;
    
    /**
     * Constructor for GenericController
     * @param tableName The name of the database table this controller manages
     */
    public GenericController(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * Abstract method to map a ResultSet row to an entity object.
     * Subclasses must implement this to define how to convert database rows to objects.
     * 
     * @param rs The ResultSet containing the data
     * @return The entity object created from the ResultSet
     * @throws SQLException if there's an error accessing the ResultSet
     */
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
    
    /**
     * Abstract method to get the SQL INSERT statement for this entity.
     * 
     * @return SQL INSERT statement with placeholders
     */
    protected abstract String getInsertSQL();
    
    /**
     * Abstract method to set parameters for an INSERT statement.
     * 
     * @param stmt The PreparedStatement to set parameters on
     * @param entity The entity to insert
     * @throws SQLException if there's an error setting parameters
     */
    protected abstract void setInsertParameters(PreparedStatement stmt, T entity) throws SQLException;
    
    /**
     * Abstract method to get the SQL UPDATE statement for this entity.
     * 
     * @return SQL UPDATE statement with placeholders
     */
    protected abstract String getUpdateSQL();
    
    /**
     * Abstract method to set parameters for an UPDATE statement.
     * 
     * @param stmt The PreparedStatement to set parameters on
     * @param entity The entity to update
     * @param id The ID of the entity to update
     * @throws SQLException if there's an error setting parameters
     */
    protected abstract void setUpdateParameters(PreparedStatement stmt, T entity, int id) throws SQLException;
    
    /**
     * Retrieves all entities from the database.
     * 
     * @return List of all entities
     */
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
    
    /**
     * Adds a new entity to the database.
     * 
     * @param entity The entity to add
     * @return true if the entity was added successfully, false otherwise
     */
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
    
    /**
     * Deletes an entity from the database by ID.
     * 
     * @param id The ID of the entity to delete
     * @return true if the entity was deleted successfully, false otherwise
     */
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
    
    /**
     * Updates an existing entity in the database.
     * 
     * @param entity The entity with updated values
     * @param id The ID of the entity to update
     * @return true if the entity was updated successfully, false otherwise
     */
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
    
    /**
     * Finds an entity by its ID.
     * 
     * @param id The ID of the entity to find
     * @return The entity if found, null otherwise
     */
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
    
    /**
     * Counts the total number of entities in the database.
     * 
     * @return The total count of entities
     */
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
    
    /**
     * Checks if an entity with the given ID exists.
     * 
     * @param id The ID to check
     * @return true if an entity with this ID exists, false otherwise
     */
    public boolean exists(int id) {
        return findById(id) != null;
    }
}