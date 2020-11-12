package com.finnegans;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Neonato extends Usuario{
	public Neonato(String nombreUsuario, String claveUsuario,int tiempo,int id) {
        super(nombreUsuario,claveUsuario,tiempo,id);
    }

	/* MUESTRA EL MENU DE RESERVAS DE ACTIVIDADES POR DIAS */
	@Override
	public void mostrarMenu() {
		// TODO Auto-generated method stub
		String opcion;
		String [] dias= {"lunes","martes","miercoles","jueves","viernes"};
		boolean resultado;
		Scanner s = new Scanner(System.in);
		System.out.println("**  MENU NEONATO **");
		
		int j=0,k=0;
		/* OBTENGO LA FECHA ACTUAL */
		java.util.Date fecha = new Date();
		String [] parts=fecha.toString().split(" ");
		String nomDia=parts[0];
		String nomMes=parts[1];
		int numDia=Integer.parseInt(parts[2]);
		/*COMPARO EN QUE DIA ESTOY Y CON L RESULADO ME PARO EN LA POSICION DEL ARREGLO DONDE ESTOY*/
		switch(nomDia) {
				case "Mon":j=0;
							break;
				case "Tue":j=1;
							break;
				case "Wed":j=2;
						break;
				case "Thu":j=3;
						break;
				case "Fri":j=4;
						break;
		}
		/*POR CADA DIA RECORRIDO CONSULTO EN LA BASE DE DATOS A QUE ACTIVIDADES CORRESPONDEN*/
		for (int i = j; i < dias.length; i++) {
					System.out.println(dias[i].toUpperCase()+" "+nomMes+" "+numDia);
					try {
						String sql ="SELECT * FROM actividades WHERE dias LIKE '%"+dias[i]+"%'";
			            Statement stmt = Conexion.getConexion().createStatement();
			            ResultSet rst=stmt.executeQuery(sql);
			            /* POR CADA DIA OBTENGO LAS ACTIVIDADES DE ESE DIA*/
			            while(rst.next()) {
			            	/* POR CADA ACTIVIDAD
		            		 * SI ELIJO "S" RESERVO LA ACTIVIDAD A NOMBRE DEL USUARIO
		            		 * SI ELIJO "N" VOY AL SIGUIENTE REGISTRO, ACTIVIDAD
		            		 * SI ELIJO P VOY AL SIGUIENTE DIA
		            		 *  
		            		 *  */
			            		System.out.println(rst.getString("nombre"));
				            	System.out.println(" Quieres reservar? (s)i / (n)o /(p)róximo día");
								opcion=s.next();
								if(opcion.equals("p")) {
									break;
								}else if(opcion.equals("n")) {
									k++;	
									continue;
								}else if(opcion.equals("s")) {
									/* VERIFICO SI PUEDO HACER LA RESERVA*/
									resultado=reserva(getNombreUsuario(),rst.getString("nombre"),getTiempo(),rst.getInt("tiempo"),rst.getInt("cupo"));
									if(resultado==true) {
										/* SI PUEDO REGISTRO LA RESERVA*/
										ingresarReserva(getId(),rst.getString("nombre"),dias[i]);
									}else if(resultado==false) {
										System.out.println("no se puede hacer la reserva");
									}
								}
			            }
			            Conexion.getConexion().close();
					}catch(Exception e) {
						System.out.println(e);
					}
					numDia++;
				}
		/* AL FINAL MUESTRO LAS RESERVAS DEL USUARIO */
		mostrarAgenda(getId(),getNombreUsuario());
	}
	/* VERIFICO SI PUEDO HACER LA RESERVA SI HAY CUPOS O SI TENGO TIEMPO DISPONIBLE
	 * DADO EL CASO POSITIVO RETORNO TRUE
	 * CASO NEGATIVO RETORNI FALSE
	 * */
	public boolean reserva(String nombre,String actividad,int tiempo,int tiempo2,int cupo) {
		try {
			if(tiempo>tiempo2) {
				if(cupo==0) {
					System.out.println("no hay cupo disponible");
					return false;
				}else {
					Statement stmt,stmt1;
		            String sql="UPDATE user SET tiempo ='"+(tiempo-tiempo2)+"' WHERE nombre='"+nombre+"'";
		            stmt = Conexion.getConexion().createStatement();
		            stmt.executeUpdate(sql);
		            
		            String sql1="UPDATE actividades SET cupo ='"+(cupo-1)+"' WHERE nombre='"+actividad+"'";
		            stmt1 = Conexion.getConexion().createStatement();
		            stmt1.executeUpdate(sql1);
		            Conexion.getConexion().close();
		            return true;
				}
			}else {
				System.out.println("no dispone tiempo para la actividad");
				return false;
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
	/* INGRESA NUEVA A LA BASE DE DATOS */
	public void ingresarReserva(int id, String actividad,String dia) {
		 try{
	                
	                String query = "insert into reservas (idUser,dia,actividad) values ('" + id+  "', '" + dia +"', '" + actividad + "')";
	                Statement stmt=Conexion.getConexion().createStatement();
	                stmt.executeUpdate(query);
	                Conexion.getConexion().close();
	                
	            }catch(Exception e){
	                System.out.println(e);
	            }
	}
	/* MUESTRA RESERVAS POR USUARIO */
	public void mostrarAgenda(int id,String nombre) {
		try {
			String sql ="SELECT * FROM reservas WHERE idUser='"+id+"'";
            Statement stmt = Conexion.getConexion().createStatement();
            ResultSet rst=stmt.executeQuery(sql);
			System.out.println(nombre+ ", tu agenda de recreación para esta semana es:");
            while(rst.next()) {
            	System.out.println(rst.getString("dia")+" "+rst.getString("actividad"));
            }
            System.out.println("Que tengas una buena semana! Adiós");
            Conexion.getConexion().close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
