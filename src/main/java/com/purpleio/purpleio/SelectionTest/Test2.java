package com.purpleio.purpleio.SelectionTest;

import java.util.Arrays;

/*
    테스트 2 : 중간 숫자 찾기
    3개의 숫자 중에 2번째 크기의 숫자 위치를 찾는 함수를 작성해주세요.
 */
public class Test2 {


    public static int solution(int[] nums){
        Arrays.sort(nums);
        return nums[1];
    }

    public static void main(String[] args) {

        int[] nums = new int[]{5,1,2};
        System.out.println(solution(nums));
    }
}
