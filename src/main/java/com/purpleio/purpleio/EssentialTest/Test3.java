package com.purpleio.purpleio.EssentialTest;

/*
    테스트 3 : 승점구하기
    점수를 보고 이기면 3점, 비기면 1점, 지면 0점으로 계산하여 전체 승점을 구하는 함수를 작성해주세요.
 */
public class Test3 {

    public static int solution(String[] scores){

        int result = 0;

        for (String score : scores) {
            // ":" 구분자로 나누기
            String[] resultScore = score.split(":");
            int p1 = Integer.parseInt(resultScore[0]);
            int p2 = Integer.parseInt(resultScore[1]);

            if (p1 > p2) {
                result += 3;
            } else if (p1 == p2) {
                result += 1;
            }
        }

        return result;
    }


    public static void main(String[] args) {
        String[] scores = {"3:1", "2:2", "1:3", "0:0", "4:2", "3:3", "2:5", "6:1", "1:1", "0:2"};
        System.out.println("result : " + solution(scores));
    }
}
