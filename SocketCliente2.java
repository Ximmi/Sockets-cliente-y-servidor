/*
	Programa correspondiente al cliente que envía un entero, un double, una cadena de cuatro caracteres
        y 10000 doubles con writeDouble. Recibe una cadena de cuatro caracteres.
	
        Tiempo de ejecución: 1629750483692 ms
        
	Arantxa Ximena Cervantes Patlán, creado el 23-08-2021
*/

//DECLARACIÓN DE PAQUETES

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;


public class SocketCliente2 {
    //MÉTODO PRINCIPAL    
    public static void main(String[] args) {
        //manejo de excepciones que pueda generar la creación del Socket
	try{
            System.out.println("Estableciendo conexión...");
            //Creamos la instancia de la clase Socket y mandamos petición al puerto 50000
            //Socket conexion = new Socket("desarrollosistemas.sytes.net", 50000);
            Socket conexion = new Socket("localhost", 50000);
            		
            //Flujo de información de salida 
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());

            //Flujo de entrada para la lectura de datos
            DataInputStream entrada = new DataInputStream(conexion.getInputStream());

            //Mandamos información al servidor
            salida.writeInt(123);
            salida.writeDouble(123553.6734);
            //manda un arreglo de 4 bytes correspondientes al String "hola"
            salida.write("hola".getBytes());

            //Recibimos la información del servidor
            byte[] buffer = new byte[4];
            //invocamos al método read de esta clase
            read(entrada,buffer,0,4);
            System.out.println(new String(buffer,"UTF-8"));	

            long tiempo_inicio = System.currentTimeMillis();
            
            //Se envían 10000 doubles sin empaquetar
            for(int j=0;j<10000;j++){
                salida.writeDouble(Double.valueOf(j));
            }
            
            //Se cierran los streams
            salida.close();
            entrada.close();

            //Se cierra la conexión
            conexion.close();
            
            long tiempo_fin = System.currentTimeMillis();
            System.out.println("Tiempo de ejecución total: " + (tiempo_fin-tiempo_inicio) + "ms");
            
	}catch(Exception e){
            System.out.println(e.getMessage());
	}
    }
    
//MÉTODO QUE LEE EL STREAM DE DATOS CON LONGITUD VARIABLE
    static void read(DataInputStream f, byte[] b, int posicion, int longitud) throws Exception{
		
	while(longitud > 0){
            int n = f.read(b,posicion,longitud);
            posicion += n;
            longitud -= n;
	}
    }
}
