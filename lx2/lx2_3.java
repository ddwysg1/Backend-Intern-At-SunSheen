class Solution {
    public static void main(String args[]){
        int[][] a = {{0}, {1,2}, {2,3,4}, {3,4,5,6}};
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.printf(a[i][j] + " ");
            }
            System.out.println("");
        }
    }
}