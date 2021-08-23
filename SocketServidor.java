/*
	Programa correspondiente al servidor que recibe un entero, un double, una cadena de cuatro caracteres
        y 10000 doubles empaquetados en un ByteBuffer. Envía una cadena de cuatro caracteres.

        Tiempo de ejecución: 1629749537693 ms
        
	Arantxa Ximena Cervantes Patlán, creado el 17-08-2021
*/

//DECLARACIÓN DE PAQUETES
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class SocketServidor {
    //MÉTODO PRINCIPAL
    public static void main(String[] args) throws IOException, Exception{
        
        System.out.println("Inicia el servidor...");
        //Creamos el socket en el puerto 50000
        ServerSocket servidor = new ServerSocket(50000);
        //Establecemos una conexión bloqueante
        Socket conexion = servidor.accept();
        
        //Creamos un stream de entrada y uno de salida
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        
        //recibimos el entero de 32 bits
        int n = entrada.readInt();
        System.out.println(n);
        //recibimos el número flotante
        double x = entrada.readDouble();
        System.out.println(x);
        //Recibe una cadena de 4 caracteres
        byte buffer[] = new byte[4];
        //invocamos al método read de esta clase
        read(entrada,buffer,0,4);
        System.out.println(new String(buffer,"UTF-8"));
        
        //Envía una cadena de cuatro caracteres
        salida.write("HOLA".getBytes());
        
        long tiempo_inicio = System.currentTimeMillis();
        
        //recibimos los cinco números de punto flotante
        byte[] a = new byte[10000*8];
        read(entrada,a,0,10000*8);
        //se convierte a objeto ByteBuffer
        ByteBuffer b = ByteBuffer.wrap(a);
        //imprimimos uno por uno
        for(int i=0;i<10000;i++)
            System.out.println(b.getDouble());
        
        //Cerramos los streams y las conexiones
        
        entrada.close();
        salida.close();
        conexion.close();
        
        long tiempo_fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución total: " + (tiempo_fin-tiempo_inicio) + "ms");
        
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
