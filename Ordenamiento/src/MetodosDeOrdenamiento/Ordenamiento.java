package MetodosDeOrdenamiento;

import javax.swing.*;

public class Ordenamiento {

    static int vec[],copia[];
    
    public static void main(String[] args){
        int tamaño,rango,opcion,izq,der;
        long inicio,fin;
        String resp,salida="";
        String Menu= "******MENU******"
                + "\n1. Mostrar Vector"
                + "\n2. Ordenamiento Shell"
                + "\n3. Ordenamiento Quick Sort"
                + "\n4. Ordenamiento Radix Sort"
                + "\n5. Ordenamiento Bucke Sort"
                + "\n6. Ordenamiento Merge Sort"
                + "\n7. Salir";
        
        tamaño=Integer.parseInt(JOptionPane.showInputDialog("ingrese el tamaño del vector"));
        rango=Integer.parseInt(JOptionPane.showInputDialog("ingrese el rango de numeros del vector"));
        vec= new int[tamaño];
        copia=new int[tamaño];

        for (int i=0;i < tamaño;i++)
        {
            vec[i] = (int) (Math.random()*rango)+1;
            copia[i]=vec[i];
            salida= salida  + "  "+ vec[i];
        }
        JOptionPane.showMessageDialog(null,"  Vector: "+"\n" +"["+ salida +" ]");
        
        do
        {
            opcion=Integer.parseInt(JOptionPane.showInputDialog(Menu));
            switch(opcion)
            {
                case 1:
                    MostrarVec(copia);
                    break;
                case 2:
                    ShellSort(copia);            
                    MostrarVec(copia); 
                    break;
                case 3:
                    inicio=System.nanoTime();
                    QuickSort(0,tamaño-1);
                    fin =System.nanoTime();
                    Object tiempo= (Object)((fin-inicio)*1e-9);
                    JOptionPane.showMessageDialog(null,"El tiempo de ejecución fue:  " +tiempo+"  seg.");
                    MostrarVec(copia);
                    break;
                case 4:
                    RadixSort();
                    MostrarVec(copia);
                    break;
                case 5:
                    int mayor = copia[0];
                    for(int i=1; i < copia.length;i++)
                    {
                        if(mayor < copia[i])
                        {
                        mayor = copia[i];
                        }
                    }
                    BucketSort(mayor); 
                    MostrarVec(copia);
                    
                    break;
                case 6:
                    MergeSort(0,tamaño-1);
                    break;
                case 7:System.exit(0);
                break;
                
                default:JOptionPane.showMessageDialog(null,"Opción no válida");
                break;
            }
        }while(opcion!=7);
    }
    
    public static void MostrarVec(int vec[]){
        String mostrar="";
        for(int i=0; i<vec.length;i++)
        {
            mostrar= mostrar+ "  " + vec[i];
        }
        JOptionPane.showMessageDialog(null,"EL vector es: "+"\n"+"["+mostrar+" ]");
    }

    public static void ShellSort(int copia[]){
        int salto, aux, i;
        long inicio,fin;
        inicio=System.nanoTime();
        boolean cambios;
        for(salto=copia.length/2; salto!=0; salto/=2)
        {
            cambios=true;
            while(cambios){ // Mientras se intercambie algún elemento
                cambios=false;
                for(i=salto; i< copia.length; i++) // se da una pasada
                    if(copia[i-salto]>copia[i]){ // y si están desordenados
                        aux=copia[i]; // se reordenan
                        copia[i]=copia[i-salto];
                        copia[i-salto]=aux;
                        cambios=true; // y se marca como cambio.
                    }
            }
        }
        fin =System.nanoTime();
        Object tiempo= (Object)((fin-inicio)*1e-9);
        JOptionPane.showMessageDialog(null,"El tiempo de ejecución fue:  " +tiempo+"  seg.");
        
    }
    
    public static void QuickSort(int izq, int der) {
        long inicio,fin;
        int pivote=copia[izq]; // tomamos primer elemento como pivote
        int i=izq; // i realiza la búsqueda de izquierda a derecha
        int j=der; // j realiza la búsqueda de derecha a izquierda
        int aux;
        while(i<j){            // mientras no se crucen las búsquedas
            while(copia[i]<=pivote && i<j) i++; // busca elemento mayor que pivote
            while(copia[j]>pivote) j--;         // busca elemento menor que pivote
            if (i<j) 
            {                      // si no se han cruzado                      
                aux= copia[i];                  // los intercambia
                copia[i]=copia[j];
                copia[j]=aux;
            }
        }
        copia[izq]=copia[j]; // se coloca el pivote en su lugar de forma que tendremos
        copia[j]=pivote; // los menores a su izquierda y los mayores a su derecha
        if(izq<j-1)
            QuickSort(izq,j-1); // ordenamos subarray izquierdo
        if(j+1 <der)
            QuickSort(j+1,der); // ordenamos subarray derecho
}
    
    public static void RadixSort(){
        long inicio,fin;
        inicio=System.nanoTime();
        if(copia.length == 0)
            return;
        int[][] np = new int[copia.length][2]; 
        int[] q = new int[0x100];
        int i,j,k,l;
        int f = 0;
        for(k=0;k<4;k++)
        {
            for(i=0;i<(np.length-1);i++)
                np[i][1] = i+1;
            np[i][1] = -1;
            for(i=0;i<q.length;i++)
                q[i] = -1;
            for(f=i=0;i<copia.length;i++)
            {
                j = ((0xFF<<(k<<3))&copia[i])>>(k<<3);
                if(q[j] == -1)
                    l = q[j] = f;
                else
                {
                    l = q[j];
                    while(np[l][1] != -1)
                        l = np[l][1];
                    np[l][1] = f;
                    l = np[l][1];
                }
                f = np[f][1];
                np[l][0] = copia[i];
                np[l][1] = -1;
            }
            for(l=q[i=j=0];i<0x100;i++)
                for(l=q[i];l!=-1;l=np[l][1])
                        copia[j++] = np[l][0];
        }
        fin =System.nanoTime();
        Object tiempo= (Object)((fin-inicio)*1e-9);
        JOptionPane.showMessageDialog(null,"El tiempo de ejecución fue:  " +tiempo+"  seg.");
    }
    
    public static void BucketSort(int mayor){
        long inicio,fin;
        inicio=System.nanoTime();
        int [] bucket=new int[mayor+1];
        for (int i=0; i<bucket.length; i++) 
        {
            bucket[i]=0;
        }
        for (int i=0; i<copia.length; i++) 
        {
            bucket[copia[i]]++;
        }
        int outPos=0;
        for (int i=0; i<bucket.length; i++) 
        {
            for (int j=0; j<bucket[i]; j++) 
            {
                copia[outPos++]=i;
            }
        }
        fin =System.nanoTime();
        Object tiempo= (Object)((fin-inicio)*1e-9);
        JOptionPane.showMessageDialog(null,"El tiempo de ejecución fue:  " +tiempo+"  seg.");
    }
    
    public static void MergeSort(int izq, int der){
        long inicio,fin;
        inicio=System.nanoTime();
        int i, j, k;
        int m=(izq+der)/2;
        int [] B = new int[copia.length]; //array auxiliar
        for (i=izq; i<=der; i++) //copia ambas mitades en el array auxiliar
            B[i]=copia[i];
        i=izq; j=m+1; k=izq;
        while (i<=m && j<=der) //copia el siguiente elemento más grande
            if (B[i]<=B[j])
                copia[k++]=B[i++];
            else
                copia[k++]=B[j++];
        while (i<=m) //copia los elementos que quedan de la
            copia[k++]=B[i++]; //primera mitad (si los hay)
        MostrarVec(copia);
        fin=System.nanoTime();
        Object tiempo= (Object)((fin-inicio)*1e-9);
        JOptionPane.showMessageDialog(null,"El tiempo de ejecución fue: " +tiempo+"  seg.");
 }

}

