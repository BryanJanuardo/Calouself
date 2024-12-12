package Models;

import java.io.Console;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import Utils.ConnectionDB;

public abstract class Model {
	
	public Model() {
		// TODO Auto-generated constructor stub
	}
	
	protected abstract String getTablename();
	
	protected <ToModel> ToModel hasOne(Class<ToModel> otherModel, String Totablename,String fromKey, String toKey){
		try {
			String query = "SELECT * FROM " + Totablename + " WHERE " + Totablename + "." + toKey + " = ?;";
			
			ConnectionDB con = ConnectionDB.getInstance();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, fromKey);
            ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				ToModel instance = otherModel.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName() == "Tablename") continue;
        	        field.setAccessible(true);
    	            Object value = rs.getObject(field.getName());
    	            field.set(instance, value);
        	    }		
				
				return instance;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}

	protected <ToModel> ArrayList<ToModel> hasMany(Class<ToModel> otherModel, String Totablename,String fromKey, String toKey){		
		try {
			ArrayList<ToModel> listToModels = new ArrayList<ToModel>();
			String query = "SELECT * FROM " + Totablename + " WHERE " + Totablename + "." + toKey + " = ?;";
			
			ConnectionDB con = ConnectionDB.getInstance();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, fromKey);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ToModel instance = otherModel.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName() == "Tablename") continue;
        	        field.setAccessible(true);
    	            Object value = rs.getObject(field.getName());
    	            field.set(instance, value);
        	    }
                listToModels.add(instance);
            }
            
            return listToModels;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}
	
	protected <FromModel> ArrayList<FromModel> all(Class<FromModel> model){	
		try {
			ArrayList<FromModel> listToModels = new ArrayList<FromModel>();
			String query = "SELECT * FROM " + getTablename();
			
			ConnectionDB con = ConnectionDB.getInstance();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                FromModel instance = model.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName() == "Tablename") continue;
        	        field.setAccessible(true);
    	            Object value = rs.getObject(field.getName());
    	            field.set(instance, value);
        	    }
                listToModels.add(instance);
            }
            
            return listToModels;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}

	protected <FromModel> ArrayList<FromModel> where(Class<FromModel> model, String columnName, String operator, String key){	
		try {
			ArrayList<FromModel> listToModels = new ArrayList<FromModel>();
			String query = "SELECT * FROM " + getTablename() + " WHERE " + columnName + " " + operator + " ?";
			
			ConnectionDB con = ConnectionDB.getInstance();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
            	System.out.println("dawdaw");
                FromModel instance = model.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName() == "Tablename") continue;
        	        field.setAccessible(true);
    	            Object value = rs.getObject(field.getName());
    	            field.set(instance, value);
        	    }
                listToModels.add(instance);
            }
            
            return listToModels;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}
	
//	private void mapResultSetToObject(Object instance, ResultSet rs) throws Exception {
//	    Field[] fields = instance.getClass().getDeclaredFields();
//	    for (Field field : fields) {
//	    	System.out.println(field);
//	        field.setAccessible(true);
//
//	        if (isPrimitiveOrWrapper(field.getType())) {
//	            Object value = rs.getObject(field.getName());
//	            field.set(instance, value);
//	        } else {
//	            Object objectInstances = field.getType().getDeclaredConstructor().newInstance();
//	            mapResultSetToObject(objectInstances, rs);
//	            field.set(instance, objectInstances);
//	        }
//	    }
//	}
//	
//	private boolean isPrimitiveOrWrapper(Class<?> type) {
//	    return type.isPrimitive() || 
//	           type == String.class || 
//	           type == Integer.class || 
//	           type == Long.class || 
//	           type == Double.class || 
//	           type == Float.class || 
//	           type == Boolean.class || 
//	           type == Byte.class || 
//	           type == Character.class || 
//	           type == Short.class;
//	}
	
}
