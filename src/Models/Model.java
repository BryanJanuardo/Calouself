package Models;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Utils.ConnectionDB;

public abstract class Model {
	
	public Model() {
		// TODO Auto-generated constructor stub
	}
	
	protected abstract String getTablename();
	protected abstract String getPrimarykey();
	
	protected <ToModel> ToModel hasOne(Class<ToModel> otherModel, String Totablename,String fromKey, String toKey){
		try {
			String query = "SELECT * FROM " + Totablename + " WHERE " + Totablename + "." + toKey + " = ?;";
			
			ConnectionDB con = ConnectionDB.getInstance();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, fromKey);
            ResultSet rs = con.execQuery(ps);
			
			if(rs.next()) {
				ToModel instance = otherModel.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName().equals("Tablename") || field.getName().equals("Primarykey")) continue;
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
            ResultSet rs = con.execQuery(ps);
            
            while (rs.next()) {
                ToModel instance = otherModel.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName().equals("Tablename") || field.getName().equals("Primarykey")) continue;
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
	
	protected <FromModel> FromModel update(Class<FromModel> model, String fromKey) {
		try {
            StringBuilder sets = new StringBuilder();
            ArrayList<Object> parameters = new ArrayList<Object>();
            
            Field[] fields = this.getClass().getDeclaredFields();
            
            if(this.getPrimarykey() == null) return null;
            
            for (Field field : fields) {
            	if(field.getName().equals("Tablename") || field.getName().equals("Primarykey")) continue;
				field.setAccessible(true);
				Object value = field.get(this);
				if(!field.getName().equals("id")) {
					if(sets.length() > 0) {
						sets.append(", ");
					}
					sets.append(field.getName()).append(" = ?");
					parameters.add(value == null ? null : value);
				}				
			}
            
            parameters.add(fromKey);
            
			ConnectionDB con = ConnectionDB.getInstance();
			String query = "UPDATE " + getTablename() + " SET " + sets + " WHERE " + getPrimarykey() + " = ?";
            PreparedStatement ps = con.prepareStatement(query);	
            
            for(int i = 0; i < parameters.size(); i++) {
            	if(parameters.get(i) == null) {
            		ps.setNull(i + 1, java.sql.Types.VARCHAR);
            	}else {
            		ps.setObject(i + 1, parameters.get(i));            		
            	}
            }
            
            con.execUpdate(ps);
            
            return (FromModel) this;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;	
	}
	
	protected <FromModel> FromModel insert(Class<FromModel> model){
		try {
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            ArrayList<Object> parameters = new ArrayList<Object>();
            
            Field[] fields = this.getClass().getDeclaredFields();
            
            for (Field field : fields) {
            	if(field.getName().equals("Tablename") || field.getName().equals("Primarykey")) continue;
				field.setAccessible(true);
				Object value = field.get(this);
				if(columns.length() > 0) {
					columns.append(", ");
					values.append(", ");
				}
				columns.append(field.getName());
				values.append("?");
				parameters.add(value == null ? null : value);
			}
            
			String query = "INSERT INTO " + getTablename() + " (" + columns + ") VALUES (" + values + ")";
			ConnectionDB con = ConnectionDB.getInstance();
            PreparedStatement ps = con.prepareStatement(query);	
            
            for(int i = 0; i < parameters.size(); i++) {
            	if(parameters.get(i) == null) {
            		ps.setNull(i + 1, java.sql.Types.VARCHAR);
            	}else {
            		ps.setObject(i + 1, parameters.get(i));            		
            	}
            }
            
            con.execUpdate(ps);
            return (FromModel) this;
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
            ResultSet rs = con.execQuery(ps);
            
            while (rs.next()) {
                FromModel instance = model.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName().equals("Tablename") || field.getName().equals("Primarykey")) continue;
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
	
	protected <FromModel> FromModel find(Class<FromModel> model, String fromKey){	
		try {
			String query = "SELECT * FROM " + getTablename() + " WHERE " + this.getPrimarykey() + " = ?";
			
			ConnectionDB con = ConnectionDB.getInstance();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, fromKey);
            ResultSet rs = con.execQuery(ps);
            
            FromModel instance = model.getDeclaredConstructor().newInstance();
            if (rs.next()) {
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName().equals("Tablename") || field.getName().equals("Primarykey")) continue;
        	        field.setAccessible(true);
    	            Object value = rs.getObject(field.getName());
    	            field.set(instance, value);
        	    }
            }
            
            return instance;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return null;
	}
	
	protected <FromModel> ArrayList<FromModel> whereIn(Class<FromModel> model, String columnName, ArrayList<String> ids) {
	    try {
	    	
	    	StringBuilder queryIds = new StringBuilder();
	    	
	    	for(int i = 0; i < ids.size(); i++) {
	    		if(queryIds.length() > 0) {
	    			queryIds.append(", ");
	    		}
	    		queryIds.append("?");	    		
	    	}

	        String query = "SELECT * FROM " + getTablename() + " WHERE " + columnName + " IN (" + queryIds + ")";
	        ConnectionDB con = ConnectionDB.getInstance();
	        PreparedStatement ps = con.prepareStatement(query);
	        
	        for(int i = 0; i < ids.size(); i++) {
	    		ps.setString(i + 1, ids.get(i));	    		
	    	}

	        ResultSet rs = con.execQuery(ps);
	        ArrayList<FromModel> listToModels = new ArrayList<>();
	        while (rs.next()) {
	            FromModel instance = model.getDeclaredConstructor().newInstance();
	            Field[] fields = instance.getClass().getDeclaredFields();
	            for (Field field : fields) {
	                if (field.getName().equals("Tablename") || field.getName().equals("Primarykey")) continue;
	                field.setAccessible(true);
	                Object value = rs.getObject(field.getName());
	                field.set(instance, value);
	            }
	            listToModels.add(instance);
	        }

	        return listToModels;
	    } catch (SQLException | ReflectiveOperationException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	protected <FromModel> Boolean delete(Class<FromModel> model, String fromKey){	
		try {
			String query = "DELETE FROM " + getTablename() + " WHERE " + this.getPrimarykey() + " = ?";
			
			ConnectionDB con = ConnectionDB.getInstance();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, fromKey);
            con.execUpdate(ps);
            
            return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		return false;
	}
	
	protected <FromModel> FromModel latest(Class<FromModel> model) {
		try {
			String query = "SELECT * FROM " + getTablename() + " ORDER BY " + getPrimarykey() + " DESC LIMIT 1";
			
			ConnectionDB con = ConnectionDB.getInstance();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = con.execQuery(ps);
            
            FromModel instance = model.getDeclaredConstructor().newInstance();
            if (rs.next()) {
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName().equals("Tablename") || field.getName().equals("Primarykey")) continue;
        	        field.setAccessible(true);
    	            Object value = rs.getObject(field.getName());
    	            field.set(instance, value);
        	    }
            }
            
            return instance;
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
            ResultSet rs = con.execQuery(ps);
            
            while (rs.next()) {
                FromModel instance = model.getDeclaredConstructor().newInstance();
                Field[] fields = instance.getClass().getDeclaredFields();
        	    for (Field field : fields) {
        	    	if(field.getName().equals("Tablename") || field.getName().equals("Primarykey")) continue;
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
