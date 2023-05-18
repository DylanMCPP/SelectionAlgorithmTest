import java.util.Arrays;
import java.lang.Math;

public class SelectionAlgorithms {


    /**
     * Sorts the array in ascending order with a Merge Sort algorithm, then retrives the kth smallest item.
     * @param input the array to be searched
     * @param entry the index of the item to be returned
     * @return the kth item
     */
    public static int mergeSortSelect(int[] input, int entry) {
        return mergeSort(input)[entry-1];
    }

    private static int[] mergeSort(int[] input) {
        if (input.length <= 1)
            return input;
        
        int mid = input.length / 2;

        // recursively call merge sort on left and right sub-arrays until arrays of size 1 are reached
        int[] left = mergeSort(Arrays.copyOfRange(input, 0, mid));
        int[] right = mergeSort(Arrays.copyOfRange(input, mid, input.length));

        // re-combine the arrays in correct order using the merge function
        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        
        // intialize and define size of new combined array
        int[] merged = new int[left.length + right.length];

        // initialize counters for both sub-arrays
        int leftIndex = 0;
        int rightIndex = 0;

        // merge both arrays, the resulting array will be sorted
        // done by comparing the smallest -> greatest items in each sub-array 
        // 1:1 and adding the smaller values first
        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                merged[leftIndex + rightIndex] = left[leftIndex];
                ++leftIndex;
            } else {
                merged[leftIndex + rightIndex] = right[rightIndex];
                ++rightIndex;
            }
        }

        while (leftIndex < left.length) {
            merged[leftIndex + rightIndex] = left[leftIndex];
            ++leftIndex;
        }
        while (rightIndex < right.length) {
            merged[leftIndex + rightIndex] = right[rightIndex];
            ++rightIndex;
        }

        // return merged(sorted) array
        return merged;
    }

    /**
     * Uses a quick sort algorithm to find the kth smallest entry in an array
     * @param array the array to be operated on
     * @param entry the index of the item to be returned
     * @return the kth item
     */
    public static int quickSelect(int[] array, int entry) {
        return quickSelect(array, 0, array.length - 1, entry-1);
    }

    private static int quickSelect(int[] array, int left, int right, int entry) {
        if (left == right)
            return array[left];
        
        int pivotIndex = partition(array, left, right);

        if (entry == pivotIndex)
            return array[entry];
        else if (entry < pivotIndex) 
            return quickSelect(array, left, pivotIndex - 1, entry);
        else 
            return quickSelect(array, pivotIndex + 1, right, entry);
    }

    private static int partition(int[] array, int left, int right) {
        int pivot = array[left];
        int i = left + 1, j = right;

        while (i <= j) {
            while (i <= j && array[i] <= pivot) {
                ++i;
            }
            while (i <= j && array[j] > pivot) {
                --j;
            }

            if (i < j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[left];
        array[left] = array[j];
        array[j] = temp;

        return j;
    }

    /**
     * Uses a quick sort algorithm with a Median of Medians pivot 
     * to find the kth smallest entry in an array
     * @param array the array to be operated on
     * @param entry the index of the item to be returned
     * @return the kth item
     */
    public static int quickSelectMM(int[] array, int entry) {
        return quickSelectMM(array, 0, array.length - 1, entry-1);
    }

    private static int quickSelectMM(int[] array, int left, int right, int entry) {
        if (left == right)
            return array[left];
        
        int pivotIndex = medianOfMedians(array, left, right);

        pivotIndex = partitionMM(array, left, right, pivotIndex);

        if (entry == pivotIndex)
            return array[entry];
        else if (entry < pivotIndex)
            return quickSelect(array, left, pivotIndex -1, entry);
        else
            return quickSelect(array, pivotIndex + 1, right, entry);
    }

    private static int medianOfMedians(int[] array, int left, int right) {
        if (right - left < 5)
            return partitionFive(array, left, right);

        int medianCount = (int) Math.ceil((double)(right - left + 1) / 5);
        int[] medians = new int[medianCount];

        for (int i = 0; i < (medianCount - 1); ++i) {
            int medianLeft = left + i * 5;
            int medianRight = Math.min(left + i * 5 + 4, right);
            medians[i] = medianOfMedians(array, medianLeft, medianRight);
        }

        return medianOfMedians(medians, 0, medianCount - 1);
    }

    private static int partitionFive(int[] array, int left, int right) {
        Arrays.sort(array, left, right + 1);
        return left + (right - left) / 2;
    }

    private static int partitionMM(int[] array, int left, int right, int pivotIndex) {
        int pivot = array[pivotIndex];
        // swap item at pivotIndex with item at right
        int temp = array[pivotIndex];
        array[pivotIndex] = array[right];
        array[right] = temp;

        int newPivotIndex = left;

        for (int i = left; i < right; ++i) {
            if (array[i] < pivot) {
                // swap items
                temp = array[i];
                array[i] = array[newPivotIndex];
                array[newPivotIndex] = temp;
                // incremenet 
                ++newPivotIndex;
            }
        }

        temp = array[newPivotIndex];
        array[newPivotIndex] = array[right];
        array[right] = temp;
        return newPivotIndex;
    }

}
