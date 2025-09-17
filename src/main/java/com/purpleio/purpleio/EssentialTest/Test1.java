package com.purpleio.purpleio.EssentialTest;

import java.util.HashMap;

/*
   테스트 1 : 두 숫자의 합
   정수 배열 nums와 정수 target이 주어졌을 때, 배열 안의 두 값의 합이 target이 되는 인덱스를 구하세요.
   정확히 하나의 해가 있다고 가정
   정답 배열의 순서는 상관 X
   시간 복잡도가 O(n^2) 보다 좋게 구현
 */
public class Test1 {

    public static int[] solution(int[] nums, int target){

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int key = target - nums[i];
            if (map.containsKey(key)) {
                return new int[] { map.get(key), i };
            }
            map.put(nums[i], i);
        }

        return null;
    }

    public static void main(String[] args) {

        int[] nums = {3,2,4};
        int target = 6;


        int[] result = solution(nums, target);
        System.out.println("result : " + result[0] + "," + result[1]);
    }
}
