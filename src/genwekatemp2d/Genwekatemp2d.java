/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genwekatemp2d;
import java.io.*;
import java.sql.*;
/**
 *
 * @author Herwin
 */
public class Genwekatemp2d {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 Connection conexion = null;
        try {
            // Cargar el driver
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/weewx", "weewx", "7s3r");
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select from_unixtime(dateTime) as fecha,date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60 as hora,round(((outTemp-32)/1.8),2) as temp, outHumidity as humedad, round((windSpeed/0.62137),2) as viento, rain as lluvia, uv as uv_actual,"+
                "(select round(((outTemp-32)/1.8),2) from archive as tt "+
                "where from_unixtime(dateTime)=DATE_ADD(fecha,INTERVAL 2 day) "+
                "order by from_unixtime(dateTime) limit 1 "+
                ") as temp_predict "+
                "from archive "+
                " where  from_unixtime(dateTime)>=date_sub(now(),INTERVAL 4 day) "+
                "order by fecha");

                FileWriter miArchivo = null;
                 PrintWriter escribirArchivo;

                    try
                    {
                        //miArchivo = new FileWriter("d:\\arffs\\climandtemp48h.arff");
                        miArchivo = new FileWriter("/home/hv/public_html/climandtemp48h.arff");
                        escribirArchivo = new PrintWriter(miArchivo);
                        //escribirArchivo.println("\""+rs.getString("fecha")+"\""+","+rs.getString("mes")+","+rs.getString("d_anio")+","+rs.getString("hormin")+"," +rs.getString("hora")+","+rs.getString("minuto")+","+rs.getString("temp")+","+rs.getString("humedad")+","+rs.getString("viento")+","+rs.getString("lluvia")+","+rs.getString("uv")+","+rs.getString("UV_M_1D")+","+rs.getString("UV_M_1H"));
                        escribirArchivo.println("@relation climandtemp48h");
                        escribirArchivo.println("@attribute hora_minuto REAL");
                        escribirArchivo.println("@attribute temperatura REAL");
                        escribirArchivo.println("@attribute humedad REAL");
                        escribirArchivo.println("@attribute viento REAL");
                        escribirArchivo.println("@attribute lluvia REAL");
                        escribirArchivo.println("@attribute uv_actual REAL");
                        escribirArchivo.println("@attribute temp_predecida REAL");
                        escribirArchivo.println("@data");
                        
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                    finally
                    {
                        try
                        {
                            if (null != miArchivo)
                            {
                                miArchivo.close();
                            }
                        }
                        catch (Exception ex1)
                        {
                            System.out.println(ex1.getMessage());
                        }
                    }
             
            
            
            
            while (rs.next()) {
                //System.out.println(rs.getString("fecha") + " " + rs.getString("hora")+" "+rs.getString("temp")+" "+rs.getString("humedad")+" "+rs.getString("viento")+" "+rs.getString("uv"));
                 //FileWriter miArchivo = null;
                 //PrintWriter escribirArchivo;

                    try
                    {
                        
                        //miArchivo = new FileWriter("d:\\arffs\\climandtemp48h.arff",true);
                        miArchivo = new FileWriter("/home/hv/public_html/climandtemp48h.arff",true);
                        escribirArchivo = new PrintWriter(miArchivo);
                        //escribirArchivo.println("\""+rs.getString("fecha")+"\""+","+rs.getString("mes")+","+rs.getString("d_anio")+","+rs.getString("hormin")+"," +rs.getString("hora")+","+rs.getString("minuto")+","+rs.getString("temp")+","+rs.getString("humedad")+","+rs.getString("viento")+","+rs.getString("lluvia")+","+rs.getString("uv")+","+rs.getString("UV_M_1D")+","+rs.getString("UV_M_1H"));
                        if ((rs.getString("temp").equalsIgnoreCase("null"))||(rs.getString("temp_predict").equalsIgnoreCase("null")) )
                        {
                            rs.next();
                        }
                        else
                        {
                        escribirArchivo.println(rs.getString("hora")+","+rs.getString("temp")+","+rs.getString("humedad")+","+rs.getString("viento")+","+rs.getString("lluvia")+","+rs.getString("uv_actual")+","+rs.getString("temp_predict"));
                    }   }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                    finally
                    {
                        try
                        {
                            if (null != miArchivo)
                            {
                                miArchivo.close();
                            }
                        }
                        catch (Exception ex1)
                        {
                            System.out.println(ex1.getMessage());
                        }
                    }
                
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally { // Se cierra la conexión con la base de datos.
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }        
    }

/////////////////comienza creacion de las instancias para el clasificador  
 
////////////////comienza interaccion con weka    
                        try{
                            //clsModelo modelo = new clsModelo("d:\\arffs\\climandtemp48h.arff");
                            clsModelo modelo = new clsModelo("/home/hv/public_html/climandtemp48h.arff");
 
                            //modelo.generarModelo("d:\\arffs\\climandtemp48h.model");
                            modelo.generarModelo("/home/hv/public_html/climandtemp48h.model");
                            
                            
                            //System.out.println("prediccion: "+resultado );
                        }
                        catch (Exception ex){
                           System.out.println(ex.getMessage()); 
                        }

/////////////termina intercaccion weka           
                        
            
                        
        try {
   
                FileWriter miArchivo = null;
                 PrintWriter escribirArchivo;

                    try
                    {
                        //miArchivo = new FileWriter("d:\\arffs\\predicttemp2d.txt");
                        miArchivo = new FileWriter("/home/hv/public_html/predicttemp2d.txt");
                        escribirArchivo = new PrintWriter(miArchivo);
                        //escribirArchivo.println("\""+rs.getString("fecha")+"\""+","+rs.getString("mes")+","+rs.getString("d_anio")+","+rs.getString("hormin")+"," +rs.getString("hora")+","+rs.getString("minuto")+","+rs.getString("temp")+","+rs.getString("humedad")+","+rs.getString("viento")+","+rs.getString("lluvia")+","+rs.getString("uv")+","+rs.getString("UV_M_1D")+","+rs.getString("UV_M_1H"));
                        escribirArchivo.println("INICIO");
   
                        
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                    finally
                    {
                        try
                        {
                            if (null != miArchivo)
                            {
                                miArchivo.close();
                            }
                        }
                        catch (Exception ex1)
                        {
                            System.out.println(ex1.getMessage());
                        }
                    }            
            
            // Cargar el driver
    
            
            
            
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/weewx", "weewx", "7s3r");
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select from_unixtime(dateTime) as fecha, date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60 as hora, round(((outTemp-32)/1.8),2) as temp, outHumidity as humedad, round((windSpeed/0.62137),2) as viento, rain as lluvia, uv as uv_actual "+
                        "from archive "+
                        "where  from_unixtime(dateTime)>=date_format(date_sub(now(),INTERVAL 1 day),'%Y-%m+%d') and "+
                        "from_unixtime(dateTime)<date_format(now(),'%Y-%m+%d') "+
                        "and  (date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='0.5000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='1.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='2.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='3.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='4.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='5.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='6.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='7.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='8.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='9.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='10.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='11.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='12.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='13.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='14.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='15.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='16.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='17.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='18.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='19.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='20.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='21.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='22.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='23.0000' "+
                        "or date_format(from_unixtime(dateTime),'%k')+(date_format(from_unixtime(dateTime),'%i'))/60='23.5000') "+
                        "order by fecha");
            
            
            
            while (rs.next()) {
                //System.out.println(rs.getString("fecha") + " " + rs.getString("hora")+" "+rs.getString("temp")+" "+rs.getString("humedad")+" "+rs.getString("viento")+" "+rs.getString("uv"));
                
//                    FileWriter miArchivo = null;
//                    PrintWriter escribirArchivo;

                    try
                    {
                       
                        
                        // miArchivo = new FileWriter("d:\\arffs\\predicttemp2d.txt",true);
                        miArchivo = new FileWriter("/home/hv/public_html/predicttemp2d.txt",true);
                        escribirArchivo = new PrintWriter(miArchivo);
                        System.out.println(rs.getString("temp"));
                        //if ((rs.getString("hora").equalsIgnoreCase("null"))||(rs.getString("temp").equalsIgnoreCase("null"))||(rs.getString("humedad").equalsIgnoreCase("null"))||(rs.getString("viento").equalsIgnoreCase("null"))||(rs.getString("lluvia").equalsIgnoreCase("null"))||(rs.getString("uv_actual").equalsIgnoreCase("null"))){
                        if ((rs.getString("hora").equalsIgnoreCase("NULL"))){
                            escribirArchivo.println("0"); 
                            System.out.println("hay un null");
                        }
                        else
                        {
                        clsClasificacion clasificacion = new clsClasificacion("/home/hv/public_html/climandtemp48h.arff", "/home/hv/public_html/climandtemp48h.model");
                        //clsClasificacion clasificacion = new clsClasificacion("d:\\arffs\\climandtemp48h.arff", "d:\\arffs\\climandtemp48h.model");
                        String resultado = clasificacion.clasificar(Float.parseFloat(rs.getString("hora")),Float.parseFloat(rs.getString("temp")),Float.parseFloat(rs.getString("humedad")),Float.parseFloat(rs.getString("viento")),Float.parseFloat(rs.getString("lluvia")),Float.parseFloat(rs.getString("uv_actual")));     
                                                
                        escribirArchivo.println(resultado);                         
                        }

                        
                        
                        
                        
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                    finally
                    {
                        try
                        {
                            if (null != miArchivo)
                            {
                                miArchivo.close();
                            }
                        }
                        catch (Exception ex1)
                        {
                            System.out.println(ex1.getMessage());
                        }
                    }
                
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally { // Se cierra la conexión con la base de datos.
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }        
    }       
        
        
////////////////comienza interaccion con weka    
//        try{
//            clsModelo modelo = new clsModelo("d:\\arffs\\climand13.arff");
//            modelo.generarModelo("d:\\arffs\\climand13.model");
//            System.out.println("modelo generado..." );
//            clsClasificacion clasificacion = new clsClasificacion("d:\\arffs\\climand13.arff", "d:\\arffs\\climand13.model");
//            String resultado = clasificacion.clasificar(Float.parseFloat("13.3333"),Float.parseFloat("17.89"),Float.parseFloat("63"),Float.parseFloat("16.09"),Float.parseFloat("0"),Float.parseFloat("5.6"));
//            System.out.println("prediccion: "+resultado );
//        }
//        catch (Exception ex){
//           System.out.println(ex.getMessage()); 
//        }
//        
/////////////termina intercaccion weka        
        
        
    }
}
