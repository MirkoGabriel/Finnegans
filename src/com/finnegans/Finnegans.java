package com.finnegans;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Finnegans {
	
	/* MENU INICIO*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion;
		Scanner s = new Scanner(System.in);
		/* VERIFICA INICIO DE SEMANA*/
		comprobar();
		System.out.println("** MENU **");
		System.out.println("1-Loguearse");
		System.out.println("2-Crear Cuenta");
		System.out.println("3-Salir");
		System.out.println("Ingrese opcion:");;
		opcion=s.nextInt();
		 switch(opcion) {
		 	case 1:autenticarUsuario();
		 			break;
		 	case 2:cargarUsuario();
		 			break;
		 	case 3:System.out.println("Finalizando programa..");
 					break;
		 }			
		
	}
	
	/* INGRESO DE USUARIO NUEVO */
	public static void cargarUsuario(){
        try{
        	String nombre,contraseña,tipo = null;
    		Scanner s = new Scanner(System.in);
    		int opcion,tiempo=0;
    		boolean result;
    		System.out.println("Que usuario va a ingresar:");
    		System.out.println("1-Neonato");
    		System.out.println("2-Nerd");
    		System.out.println("3-Sensei");
    		opcion=s.nextInt();
    		
    		
    		System.out.println("Ingrese nombre:");
    		nombre=s.next();
    		System.out.println("Ingrese contraseña:");
    		contraseña=s.next();
    		/* SUGIERE QUE USUARIO DESEA INGRESAR */
    		switch(opcion) {
    			case 1: tipo="Neonato";
    					tiempo=100;
    					break;
    			case 2: tipo="Nerd";
    					tiempo=250;
    					break;
    			case 3: tipo="Sensei";
    					tiempo=175;
    					break;
    		}
                
                String query = "insert into user (nombre,password,tipo,tiempo) values ('" + nombre+  "', '" + contraseña +"', '" + tipo +"', '"+tiempo+ "')";
                Statement stmt=Conexion.getConexion().createStatement();
                stmt.executeUpdate(query);
                Conexion.getConexion().close();
                System.out.println("usuario ingresado");
                main(null);
            }catch(Exception e){
                System.out.println(e);
            }
    }
	
	/* AUTENTICACION USUARIO */
	public static void autenticarUsuario() {
		String nombre, contraseña,tipo = null;
		int tiempo,id;
		Scanner s = new Scanner(System.in);
		System.out.println("** INGRESO **");
		System.out.println("Ingrese nombre:");
		nombre=s.next();		
		System.out.println("Ingrese contraseña:");
		contraseña=s.next();
		
		/*CONSULTA VALORES INGRESADOS POR TECLADO SI ESTAN EN LA BASE DE DATOS*/
		try {
			String sql ="SELECT * FROM user WHERE nombre='"+nombre+"' and password='"+contraseña+"'";
            Statement stmt = Conexion.getConexion().createStatement();
            ResultSet rst=stmt.executeQuery(sql);
			
            	/* SI ESTOS VALORES ESTAN SE INSTANCIA UN OBJETO DEL TIPO PERFIL Y MUESTRA EL MENU */
            if(rst.next()) {
            	System.out.println("Bienvenido,"+nombre+". Según tu perfil de Neonato que Finnegans te asignó y tus horarios te proponemos las siguientes actividades para la semana");
            	tipo=rst.getString("tipo");
            	tiempo=rst.getInt("tiempo");
            	id=rst.getInt("id");
            	if(tipo.equals("Neonato")) {
            		Neonato n=new Neonato(nombre,contraseña,tiempo,id);
            		n.mostrarMenu();
            	}else if(tipo.equals("Nerd")){
            		Nerd ne=new Nerd(nombre,contraseña,tiempo,id);
            		ne.mostrarMenu();
            	}else if(tipo.equals("Sensei")) {
            		Sensei se =new Sensei(nombre,contraseña,tiempo,id);
            		se.mostrarMenu();
            	}
            }else {
            	System.out.println("usuario o contraseña no coinsiden");
            	main(null);
            }
            Conexion.getConexion().close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	/* VERIFICA SI EL EL DIA ES LUNES Y SI ES LA PRIMERA VEZ QUE SE EJECUTA EN EL DIA*/
	public static void comprobar() {
		java.util.Date fecha = new Date();
		String [] parts=fecha.toString().split(" ");
		String nomDia=parts[0];
		int flag=0;
		
		//String nomDia="Mon";
		try {
			String sql ="SELECT * FROM cont WHERE id='"+1+"'";
            Statement stmt = Conexion.getConexion().createStatement();
            ResultSet rst=stmt.executeQuery(sql);
			
            if(rst.next()) {
            	flag=rst.getInt("flag");
            }
            Conexion.getConexion().close();
		}catch(Exception e) {
			System.out.println(e);
		}
		/* SI ES EL COMIENZO DE SEMANA Y LA PRIMERA VEZ QUE SE EJECUTA EN EL DIA
		 * RESTABLECE LOS VALORES DE UNA SEMANA NUEVA
		 * */
		if(nomDia.equals("Mon")) {
			if(flag==0) {
				actualizarUsuario();
				actualizarActividad();
				borrarReservas();
			}
			flagActualizar(flag+1);
		}else {
			flagActualizar(0);
		}
	}
	/* ACTUALIZA LA 'FLAG' LE SUMA 1 PARA SABER CUANTAS VECES SE EJECUTA EL PROGRAMA*/
	public static void flagActualizar(int valor) {
		try {
			Statement stmt;
            String sql="UPDATE cont SET flag ='"+valor+"' WHERE id='"+1+"'";
            stmt = Conexion.getConexion().createStatement();
            stmt.executeUpdate(sql);
            
            Conexion.getConexion().close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	/* ACTUALIZA LOS VALORES DE TIEMPO DE TODOS LOS USUARIOS POR QUE INICIO UNA SEMANA NUEVA*/
	public static void actualizarUsuario() {
		String [] tipo = {"Nerd","Sensei","Neonato"};
		int [] tiempo= {250,175,100};
		for (int i = 0; i < tiempo.length; i++) {
			try {
				Statement stmt;
	            String sql="UPDATE user SET tiempo ='"+tiempo[i]+"' WHERE tipo='"+tipo[i]+"'";
	            stmt = Conexion.getConexion().createStatement();
	            stmt.executeUpdate(sql);
	            
	            Conexion.getConexion().close();
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	/* ACTUALIZA LOS VALORES DE CUPO DE TODAS LAS ACTIVIDADES POR QUE INICIO UNA SEMANA NUEVA*/
	public static void actualizarActividad() {
		String [] nombre = {"yoga","actuacion","ensayo musical","metegol","cyberjuegos-fifa","cyberjuegos-mathemats"};
		int [] cupo= {20,10,5,2,4,4};
		for (int i = 0; i < cupo.length; i++) {
			try {
				Statement stmt;
	            String sql="UPDATE actividades SET cupo ='"+cupo[i]+"' WHERE nombre='"+nombre[i]+"'";
	            stmt = Conexion.getConexion().createStatement();
	            stmt.executeUpdate(sql);
	            
	            Conexion.getConexion().close();
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	/* BORRA LAS RESERVAS DE LA SEMANA ANTERIOR PUESTO QUE INICIA UNA SEMANA NUEVA*/
	public static void borrarReservas() {
		try {
			Statement stmt;
            String sql="DELETE FROM reservas";
            stmt = Conexion.getConexion().createStatement();
            stmt.executeUpdate(sql);
            
            Conexion.getConexion().close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
