import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import graphsDSESIUCLM.Edge;
import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.TreeMapGraph;
import graphsDSESIUCLM.Vertex;

public class Principal {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		StringTokenizer st;
		DecoratedElement<String> de1;
		DecoratedElement<String> de2;
		DecoratedElement<Integer> de3;
		Graph<DecoratedElement<String>, DecoratedElement<Integer>> grafo = new TreeMapGraph<DecoratedElement<String>, DecoratedElement<Integer>>();
		String line;
		Scanner sc;
		try {
			sc = new Scanner(new FileReader(new File("src//stormofswords.csv")));
			sc.nextLine();
			String aux;
			int i=1;
			while(sc.hasNext()){
				line = sc.nextLine();
				st=new StringTokenizer(line,",");
				aux=st.nextToken();
				de1= new DecoratedElement<String>(aux, aux);
				aux=st.nextToken();
				de2= new DecoratedElement<String>(aux, aux);
				aux=st.nextToken();
				de3=new DecoratedElement<Integer>(Integer.toString(i), Integer.parseInt(aux));
				i++;
				grafo.insertEdge(de1, de2, de3);
				
			}
			
			sc=new Scanner(System.in);
			//MOSTRAR EL NÚMERO DE PERSONAJES
			
			menu(grafo);
			
			//IMPRIMIR GRAFO
			 /*Vertex [] v;
			    Iterator<Edge> ite;
			    System.out.println("Graph");
			    ite = grafo.getEdges();
			    while (ite.hasNext()) {
			      v = grafo.endVertices(ite.next());
			      System.out.print(v[0].getElement().toString());
			      System.out.println("-" + v[1].getElement().toString() + "//");
			    } */
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
				

	}
	public static void menu (Graph<DecoratedElement<String>, DecoratedElement<Integer>> grafo) {

		Scanner sc = new Scanner (System.in);
		int opcion;

			System.out.println("Grafo Juego de Tronos");
			System.out.println("Elige opción: ");
			System.out.println("1. Número de personajes, relaciones, personaje con más relaciones y pareja con mayor relación");
			System.out.println("2. Cálculo de subconjuntos.");
			System.out.println("3. Enviar cuervo con sobre lacrado.");
			try {
			opcion=sc.nextInt();
			sc.nextLine();

			switch(opcion) {
			case 1:
				System.out.println("Número de personajes: "+grafo.getN());
				System.out.println("Número de relaciones: "+grafo.getM());
				System.out.println("Personaje con más relaciones: " +getVertexMayorRelacion(grafo).getID() );
				System.out.println("Mayor relación: "+getMayorRelacion(grafo).getElement());
				System.out.println("Pareja con mayor relacion: "+getParejaMayorRelacion(grafo));
				break;
			case 2:
				System.out.println(islaPersonajes(grafo));
				break;
			case 3:

				solitario(grafo);

				try {
				System.out.println("Emisor:");
				String leido = sc.nextLine();
				Vertex<DecoratedElement<String>> vstart = grafo.getVertex(leido);
				System.out.println("Receptor:");
				leido = sc.nextLine();
				
				Vertex<DecoratedElement<String>> vend = grafo.getVertex(leido);

					BFS(grafo,vstart,vend);
					if(vstart.getElement().getID().equals(vend.getElement().getID()))//Si emisor = receptor
						System.out.println("Los personajes son idénticos.");
					else
						sacarRuta(grafo, vend);			
				}
				catch (NullPointerException e){
					System.out.println("Error al introducir los personajes.");
				}
				
				break;
			
			}
			
			
			}
		
		catch (InputMismatchException e) {
			System.out.println("Error.");
		}
	}
		
	
	public static Edge<DecoratedElement<Integer>> getMayorRelacion(Graph<DecoratedElement<String>, DecoratedElement<Integer>> grafo){
		Edge<DecoratedElement<Integer>> relacion= null;
		Edge<DecoratedElement<Integer>> aux;
		Iterator<Edge<DecoratedElement<Integer>>> relaciones = grafo.getEdges();
		while(relaciones.hasNext()){
			if(relacion==null){
				relacion=relaciones.next();				
			}else{
				aux=relaciones.next();
				if(aux.getElement().getElement()>relacion.getElement().getElement()){
					relacion=aux;
				}
			}
		}
		return relacion;
		
	}
	
	public static Vertex<DecoratedElement<String>> getVertexMayorRelacion(Graph<DecoratedElement<String>, DecoratedElement<Integer>> grafo){
		Iterator <Vertex<DecoratedElement<String>>> vertices = grafo.getVertices();
		Iterator <Edge<DecoratedElement<Integer>>> relaciones;
		Vertex<DecoratedElement<String>> vertice = null;
		int aux=0;
		int mayor=0;
		Vertex<DecoratedElement<String>> mayorVertex=null;
		
		while(vertices.hasNext()) {
			vertice= vertices.next();
			relaciones= grafo.incidentEdges(vertice);
			while(relaciones.hasNext()) {
				aux++;
				relaciones.next();
			}
			if(aux> mayor) {
				mayorVertex=vertice;
				mayor=aux;
			}
			aux=0;
		}
		return mayorVertex;
	}
	
	public static String getParejaMayorRelacion(Graph<DecoratedElement<String>, DecoratedElement<Integer>> grafo) {
		/**********************************************************************  
		 * * * Method name: getParejaMayorRealacion 
		 * * * Description of the Method: Dado un grafo encuentra la pareja de vertices cuya arista tenga el mayor valor guardado 
		 * * * Calling arguments: A list of the calling arguments, their types, and * brief explanations of what they do.  
		 * * * Return value: un string con la pareja con mayor numero de relacion 
		 * * *********************************************************************/ 
		Iterator <Vertex<DecoratedElement<String>>> vertices = grafo.getVertices();  //se crea un iterador con todos lo vertices del grafo
		Iterator <Edge<DecoratedElement<Integer>>> relaciones; //un iterador vacio de aristas
		Vertex <DecoratedElement<String>>aux;
		Vertex <DecoratedElement<String>>husbando=null; //vertice donde se almacena el primer vertice de la pareja
		Vertex <DecoratedElement<String>>waifu=null; //vertice donde se almacena el segundo vertice la pareja
		Edge <DecoratedElement<Integer>>relacion;
		Edge <DecoratedElement<Integer>>compromiso=null;
		int amor=0; // utlizada para guardar el valor mas grande guardado entre las aristas
		while(vertices.hasNext()) {
			aux=vertices.next();
			relaciones = grafo.incidentEdges(aux);//guarda en el iterador todas las aristas que tiene ese vertice
			while(relaciones.hasNext()) { 
				//mientras exitan aristas se comprobaran el valor que guardan y se almacenara en amor el mas grande
				relacion=relaciones.next();
				if( amor < relacion.getElement().getElement()){
					amor= relacion.getElement().getElement();
					husbando= aux;
					compromiso=relacion;
				}
			}
			
		}
		waifu= grafo.opposite(husbando,compromiso );//guarda el vertice opuesto a husbando conectado mediante la arista compromiso
		return waifu.getID()+" y "+husbando.getID();
	}
	public static String islaPersonajes(Graph<DecoratedElement<String>, DecoratedElement<Integer>> grafo) {
		Iterator <Vertex<DecoratedElement<String>>> vertices = grafo.getVertices();
		boolean isla = true;
		Vertex<DecoratedElement<String>> v = null;
		v = vertices.next();
		DFS(grafo,v);
		
		while(vertices.hasNext() && isla) {
			v = vertices.next();
			isla=v.getElement().getVisited();
		}
		if(!isla)
			return "Existen subconjuntos de personajes que no se relacionan ";
		else return "No existe";
	}
	public static void DFS(Graph<DecoratedElement<String>, DecoratedElement<Integer>> g, Vertex<DecoratedElement<String>> v) {
		Vertex<DecoratedElement<String>> aux=null;
		Edge<DecoratedElement<Integer>> e= null;
		v.getElement().setVisited(true);
		Iterator<Edge<DecoratedElement<Integer>>> it= g.incidentEdges(v);
		while(it.hasNext()) {
			e=it.next();
			aux=g.opposite(v,e);
			if(!aux.getElement().getVisited()) {
				DFS(g,aux);
			}
		}
		
	}
	public static void tokens(String s) {
	        StringTokenizer st = new StringTokenizer(s,";");
	        while (st.hasMoreTokens()) {
	             System.out.print(st.nextToken()+" "); 
	         }
	        System.out.println();
	    }
	public static void solitario(Graph<DecoratedElement<String>, DecoratedElement<Integer>> grafo) {
		Iterator<Vertex<DecoratedElement<String>>> vertices = grafo.getVertices();
		Vertex<DecoratedElement<String>> v = null;
		while(vertices.hasNext()) {
			v = vertices.next();
			v.getElement().setVisited(false);
		}
		
	}
	public static void BFS(Graph<DecoratedElement<String>, DecoratedElement<Integer>> grafo,Vertex<DecoratedElement<String>> start,Vertex<DecoratedElement<String>> end) {
		Vertex<DecoratedElement<String>> v = null;
		Vertex<DecoratedElement<String>> vPareja = null;
		Iterator<Edge<DecoratedElement<Integer>>> aristas;
		Edge<DecoratedElement<Integer>> e=null;
		Stack <Vertex<DecoratedElement<String>>> pila = new Stack<Vertex<DecoratedElement<String>>>();
		/*Cambiamos de valor los atributos del primer vertice*/
		start.getElement().setVisited(true);
		start.getElement().setParent(null);
		start.getElement().setDistance(0);
		//Guardamos el primer vertice en la pila
		pila.push(start);
		while(!pila.isEmpty()){
			v=pila.pop();
			//Sacamos las aristas adyacentes
			aristas=grafo.incidentEdges(v);
			while(aristas.hasNext()){
				e=aristas.next();
				//Comprobamos que la arista no esté visitada y sea de un valor mayor a 10
				if(!e.getElement().getVisited() && e.getElement().getElement()>=10){
					e.getElement().setVisited(true);
					//Sacamos el vertice que conecta con el vertice v por la arista e
					vPareja=grafo.opposite(v, e);
					//Comprobamos que este vertice no esté visitado
					if(!vPareja.getElement().getVisited()){
						//Cambiamos los valroes del vertice
						vPareja.getElement().setVisited(true);
						//Asignamos como padre de este ultimo vertice el vertice antecesor
						vPareja.getElement().setParent(v.getElement());
						vPareja.getElement().setDistance(v.getElement().getDistance()+1);
						
					}
					/*Si el vertice que obtenemos no es igual que el destinatario, continuamos bucando
					*y añadimos este vertice a la pila
					*/
					if(vPareja.getElement().getElement()!= end.getElement().getElement()){
						pila.push(vPareja);
					}
				
				}
				
			}
		}
		
	}
	
	public static void sacarRuta(Graph<DecoratedElement<String>, DecoratedElement<Integer>> grafo,Vertex<DecoratedElement<String>> end){
		if (end.getElement().getDistance()==0) { //Si no hay distancia (No hay conexión posible)
			System.out.println("No se puede entregar el sobre ya que no hay una red de confianza.");
		}
		else {
		while(end.getElement().getDistance()!=0){
			System.out.print(end.getElement().getID()+"// ");
			end=grafo.getVertex(end.getElement().getParent().getID());
			
			}
			System.out.println(end.getElement().getID());
		}
	}
}
