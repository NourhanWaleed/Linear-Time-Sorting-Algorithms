import java.util.Arrays;
import java.util.Random;

public class main{

    //Median of Medians
    // It searches for x in arr[l..r], and partitions the array around x.
    static int MedianofMedianspartition(int[] array, int left, int right, int x)
    {
        // Search for x in arr[l..r] and move it to end
        int i;
        for (i = left; i < right; i++)
            if (array[i] == x)
                break;
        swap(array, i, right);
        i = left;
        for (int j = left; j <= right - 1; j++)
        {
            if (array[j] <= x)
            {
                swap(array, i, j);
                i++;
            }
        }
        swap(array, i, right);
        return i;

    }
    // sort the array naively and return middle element(median)
    static int Select(int[] array, int n){
        //Arrays.sort(array);
      //  if(n%2 !=0)
        return array[n/2];
       // else return  ((array[n/2]+array[n/2 -1])/2);
    }
    static int MofMkthSmallest(int[] array, int left, int right, int k)
    {
        if(array.length <=10){
            Arrays.sort(array);
        //    if(array.length %2 != 0)
            return array[(right-left)/2 ];
          //  else return (array[(right-left)/2]+array[(right-left)/2 -1])/2;
        }
        // If k is smaller than number of elements in array
        if (k > 0 && k <= array.length)
        {
          //  int n = array.length ; // Number of elements in arr[l..r]
            //partition the array int  subarrays each has 5 elements

            // There will be max ((n+4)/5) groups;
            int []median = new int[(array.length + 4) / 5];
            int i=0;
            while(i<array.length/5){
                median[i] = Select(array,  left+i*5+5); // finding each median naively as 5 is a small number
                i++; }
            //for (i = 0; i < n/5; i++)


            // For last group with less than 5 elements
            if (i*5 < array.length)
            {
                median[i] = Select(array, left+i*5+array.length%5);
                i++;
            }

            // Find median of all medians using recursive call. If median[] has only one element, then no need of recursive call
            int medOfMed = (i == 1)? median[i - 1]:
                    MofMkthSmallest(median, 0, i - 1, i / 2); //if statement gives an unreachable code for the next line?

            // Partition the array around a random element and
            // get position of pivot element in sorted array
            int position = MedianofMedianspartition(array, left, right, medOfMed);

            // If position is same as k
            if (position-left == k - 1)
                return array[position];
           else if (position-left > k - 1) // If position is more, recur in left
                return MofMkthSmallest(array, left, position - 1, k);

            // Else recur in right
          else  return MofMkthSmallest(array, position + 1, right, k - position + left -1);
        }

        // If k is more than number of elements in array
        return Integer.MAX_VALUE;  //very unlikely case
    }


    //Divide and Conquer


    static int a, b;

    static int[] swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    static int DivAndConqPartition(int arr[], int left, int right) {
        int length = right - left + 1;
        int pivot = (int) (Math.random() % length);
        swap(arr, left + pivot, right);
        int x = arr[right];
        int i = left - 1;
        int j;
        for (j = left; j < right ; j++) {
            if (arr[j] < x) {
                i++;
               swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    static int Median(int arr[], int left, int right, int k) {
        if (left <= right) {
            int partitionIndex = DivAndConqPartition(arr, left, right);
            if (partitionIndex == k) {
                b = arr[partitionIndex];
                if (a != -1)
                    return Integer.MIN_VALUE;
            } else if (partitionIndex == k - 1) {
                a = arr[partitionIndex];
                if (b != -1)
                    return Integer.MIN_VALUE;
            }
            if (partitionIndex >= k)
                return Median(arr, left, partitionIndex - 1, k);
            else
                return Median(arr, partitionIndex + 1, right, k);
        }

        return Integer.MIN_VALUE;
    }

    static void DivAndConqfindMedian(int[] arr, int length) {
        int ans;
        a = -1;
        b = -1;
        if (length % 2 != 0) {
            Median(arr, 0, length - 1, length / 2);
            ans = a;
        } else {
            Median(arr, 0, length - 1, length / 2);
            ans = (a+b)/2  ;
        }
        System.out.println("Median using Divide and Conquer is " + ans);
    }
    public static int[] generatearray(int[] arr, int n) {
        int x = 0;
        Random rand = new Random();
        System.out.print("generating random array of size " + n + "\n");
        for (x = 0; x < n; x++) {

            arr[x] = rand.nextInt(100000);
            //System.out.print(arr[x] +"\t" );
        }//System.out.print("\n" );
        return arr;
    }

    public static void main(String[] args) {

        int n = 100000;
        int[] arr = new int[n];
        arr = generatearray( arr, n);

        double DivAndConQstart = System.currentTimeMillis();
        DivAndConqfindMedian(arr,n);
        double DivAndConQend = System.currentTimeMillis();

        System.out.println("Time to find median using Divide and Conquer:"+(DivAndConQend-DivAndConQstart));

        double naivestart = System.currentTimeMillis();
        Arrays.sort(arr);
        int naivemedian = (n % 2 ==1)? arr[n/2 -1]: (arr[n/2]+arr[n/2 -1])/2;
        double naiveend = System.currentTimeMillis();
        System.out.println("Naive Median is "+naivemedian);
        System.out.println("Time to find median using Naive sort:"+(naiveend -naivestart));

        double Medianofmediansstart = System.currentTimeMillis();
        int  Medianofmediansmedian = MofMkthSmallest(arr,0,n-1,n/2);
        double Medianofmediansend = System.currentTimeMillis();
        System.out.println("Median using Median of medians is "+Medianofmediansmedian);
        System.out.println("Time to find median using median of medians: "+(Medianofmediansend-Medianofmediansstart));

    }}