package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

public class ProjectController {

    public void save(Project project) {
        String sql = "INSERT INTO project (name, "
                + "description, "
                + "createdAt, "
                + "updateAt) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.execute();
        }catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar o projeto " 
                    + ex.getMessage(), ex);
        }finally {
            ConnectionFactory.closeConnection(connection, statement);
        } 
    }
    
    public void update (Project project){
        String sql = "UPDATE project SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ?, "
                + "WHRE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();
        } catch (Exception ex) {
             throw new RuntimeException("Erro ao atualizar o projeto " 
                    + ex.getMessage(),ex);
        }
    }
    
    public void remove (int projectId) throws SQLException{
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar o projeto " 
                    + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
   
    public List<Project> getAll(int idProject){
        String sql = "SELECT * FROM projects WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        Resultset resultSet = null;
        
        List<Project> projects = new ArrayList<Project>();
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Project project = new Project();
                project.setId (resultSet.getInt("id"));
                project.setName (resultSet.getString("name"));
                project.setDescription (resultSet.getString("description"));
                project.setCreatedAt (resultSet.getDate("createdAt"));
                project.setUpdatedAt (resultSet.getDate("updatedAt"));
                projects.add(project);  
            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir  o projeto " 
                    + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return projects;
        }
    }