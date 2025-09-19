package com.purpleio.purpleio.SelectionTest;

/*
    테스트 4 : 아이큐 테스트
    홀수 또는 짝수중에 하나만 존재하는 값의 순번을 출력하는 함수를 작성해주세요.
    순번의 시작은 0이 아니라 1입니다.
 */
public class Test4 {

    public static int solution(String[] nums){

        int result = 0;

        int oddCnt = 0;
        int evenCnt = 0;
        int oddIndex = 0;
        int evenIndex = 0;

        for(int i=0; i<nums.length; i++){
            if(Integer.parseInt(nums[i]) % 2 == 1){
                oddCnt ++;
                oddIndex = i + 1;
            }
            else{
                evenCnt ++;
                evenIndex = i + 1;
            }
        }

        if(oddCnt == 1){
            result = oddIndex;
        } else if(evenCnt == 1) {
            result = evenIndex;
        } else {
            result = -1;
        }

        return result;

    }


    public static void main(String[] args) {

        String str = "2 1 1 1 1 1 1 3 1 3 7 3 1 1 1 1 1";
        String[] strArr = str.split(" ");

        System.out.println("result : " + solution(strArr));

    }

}
