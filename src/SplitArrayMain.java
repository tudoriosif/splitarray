/*
In a given integer array A, we must move every element of A to either list B or list C. (B and C
initially start empty.)
Return true if and only if after such a move, it is possible that the average value of B is equal to
the average value of C, and B and C are both non-empty.
Sample input:
[1,2,3,4,5,6,7,8]
Sample output:
True
Explanation: We can split the array into [1,4,5,8] and [2,3,6,7], and both of them have the average
of 4.5.
Note:
The length of A will be in the range [1, 30].
A[i] will be in the range of [0, 10000].
 */

/*
    Explicatii:
    
    Pornim de la faptul ca valoarea medie a unui array se calculeaza ca fiind suma tuturor elementelor -> sumArr
    impartita la numarul elementelor -> arrLength.
    Presupunem ca putem imparti arrayul in doua subarrayuri care formeaza acelasi average, de unde ne rezulta ecuatiile:
    A - primul SubArray; B - al doilea SubArray;
    1. sumArr = sumA + sumB;
    2. arrLength = lengthA + lengthB;
    3. sumA / lengthA = sumB / lengthB;

    Pornind de la aceste ecuatii => sumA / lengthA = (sumArr - sumA) / (arrLength - lengthA).
    Obtinem o conditie de existenta care consta in: sumA = (lengthA * sumA) / arrLength. !!!!
    Cele doua SubArrayuri sunt cel putin de lungime 1 pana la arrLength / 2 (presupunem).
 */




public class SplitArrayMain {


    public static int sumOfElements(int[] arr){
        int sum = 0;
        for(int i : arr){
            sum += i;
        }
        return sum;
    }

    public static boolean target(int[] arr, int sumA, int lengthA, int start){
        // In cazul in care s-a ajuns la un rezultat corect, adica s-a putut forma suma presupusa din conditia de existenta si cu o lungime posibila presupusa
        // Ne rezulta sumA = 0, cat si ALength = 0
        if(lengthA == 0) return sumA == 0;

        for(int i = start; i < arr.length - lengthA + 1; i++){
            if(i == start || arr[i] != arr[i-1]){ // Verificam sa se execute doar in cazul in care nu exista duplicari ale elementelor
                // Verificam daca putem forma sumA prin scaderea elementelor intr-un mod recursiv (practic consideram ca o adaugare a acestora intr-un SubArray
                // in momentul in care elementele SubArrayului formeaza suma dorita, se poate face split la arrayul initial)
                // Indexul de start a recursivitatii se va incrementa la fiecare pas pentru a trece peste elementele deja parcurse
                if(target(arr, sumA - arr[i], lengthA - 1, i + 1)) return true;
            }
        }
        return false;
    }

    public static boolean splitArrays(int[] arr){

        if(arr.length == 1) return false; // Daca Arrayul are un singur element nu poate fi impartit in 2 arrayuri

        int arrLength = arr.length;
        int sumArr = sumOfElements(arr); // Pentru a afla valoarea medie a Arrayului avem nevoie de suma tuturor elementelor

        // Presupunem ca posibila lungime a SubArrayului este de la 1 la arrLength / 2;
        for(int possibleLength = 1; possibleLength <= arrLength / 2; possibleLength++ ){
            // Verificam conditia de existenta demonstrata la inceput.
            if((sumArr * possibleLength) % arrLength == 0){
                // Suma posibila a SubArrayului:
                int possibleSum = (sumArr * possibleLength) / arrLength;
                // Trebuie sa aflam daca putem forma sumA = possibleSum utilizand possibleLength elemente din vector arr
                // incepand de la indexul 0 al acestuia
                if(target(arr, possibleSum, possibleLength, 0)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(splitArrays(new int[] {1,2,3,4,5,6,7,8}));
        System.out.println(splitArrays(new int[] {2,4,5,7,10,14}));
    }
}
