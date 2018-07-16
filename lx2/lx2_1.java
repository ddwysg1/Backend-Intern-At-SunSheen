class Solution {
    public int getIndex(int A, int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (A == nums[i]) {
               return i;
            }
        }
        System.out.println("There is no such number in array");
        return -1;
    }
}