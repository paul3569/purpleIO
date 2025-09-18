package com.purpleio.purpleio.EssentialTest;

/*
    테스트 5 : 더하고 빼기
    각 자리의 숫자를 더한값을 원래 숫자에서 빼고 결과가 하단에 나열한 과일코드가 나올때까지 계산하는 코드를 작성해주세요.
 */
public class Test5 {

    static String[] fruits = {
            "", "kiwi", "pear", "kiwi", "banana", "melon", "banana", "melon", "pineapple", "apple",
            "pineapple", "cucumber", "pineapple", "cucumber", "orange", "grape", "orange", "grape", "apple",
            "grape", "cherry", "pear", "cherry", "pear", "kiwi", "banana", "kiwi", "apple", "melon",
            "banana", "melon", "pineapple", "melon", "pineapple", "cucumber", "orange", "apple", "orange",
            "grape", "orange", "grape", "cherry", "pear", "cherry", "pear", "apple", "pear", "kiwi",
            "banana", "kiwi", "banana", "melon", "pineapple", "melon", "apple", "cucumber", "pineapple",
            "cucumber", "orange", "cucumber", "orange", "grape", "cherry", "apple", "cherry", "pear",
            "cherry", "pear", "kiwi", "pear", "kiwi", "banana", "apple", "banana", "melon", "pineapple",
            "melon", "pineapple", "cucumber", "pineapple", "cucumber", "apple", "grape", "orange", "grape",
            "cherry", "grape", "cherry", "pear", "cherry", "apple", "kiwi", "banana", "kiwi", "banana",
            "melon", "banana", "melon", "pineapple", "apple", "pineapple"
    };


    public static int calNum(int inputNum){
        String str = String.valueOf(inputNum);
        int tempNum = 0;


        for(int i=0; i<str.length(); i++){
            tempNum += Integer.parseInt(String.valueOf(str.charAt(i)));
        }

        return inputNum - tempNum;
    }


    public static void main(String[] args) {

        int inputNum = 325;

        do{
            inputNum = calNum(inputNum);
        }
        while(inputNum > 100);

        System.out.println(fruits[inputNum]);
    }
}
