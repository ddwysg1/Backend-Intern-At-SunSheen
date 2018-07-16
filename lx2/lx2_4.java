class Solution {
    public int getMin(int[] A) {
        Arrays.sort(A);
        return A[0];
    }

    public int getMax(int[] A) {
        Arrays.sort(A);
        return A[A.length - 1];
    }

    public int getSum(int[] A) {
        sum = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
        }
        return sum;
    }
    public int getAverage(int[] A) {
        return getSum(A) / A.length;
    }

}