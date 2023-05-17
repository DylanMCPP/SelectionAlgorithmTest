import java.util.Arrays;

public class SelectionAlgorithms {


    // Merge Select sorts the array in ascending order with a Merge Sort algorithm, then retrives the kth item.

    public static int mergeSortSelect(int[] input, int entry) {
        return mergeSort(input)[entry];
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
}
