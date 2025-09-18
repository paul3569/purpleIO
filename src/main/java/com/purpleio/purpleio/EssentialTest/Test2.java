package com.purpleio.purpleio.EssentialTest;

import java.util.Arrays;

/*
   테스트 2 : 사용하지 않는 가장 작은 숫자 찾기
   사용하지 않는 가장 작은 숫자를 찾는 함수를 작성
 */
public class Test2 {

    public static int solution(int[] nums){

        int result = 0;

        // null 체크
        if (nums == null || nums.length == 0) {
            return 0; // 배열이 비었으면 0 반환
        }

        nums = Arrays.stream(nums).distinct().sorted().toArray();

        // 0이 배열에 없으면 0 반환
        if (nums[0] != 0) {
            return 0;
        }

        for(int i=0; i<nums.length; i++){
            int now = nums[i];
            if(i == nums.length-1) return now+1;
            else if(now+1 != nums[i+1]){
                return now+1;
            } else continue;

        }
        return result;
    }

    public static void main(String[] args) {

        int[] nums = {0,1,2,3,4,5,6};
        System.out.println("result : " + solution(nums));
    }
}
