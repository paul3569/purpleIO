package com.purpleio.purpleio.EssentialTest;

/*
    테스트 4 : 자동 줄바꿈 프로그램
    입력된 문장을 영문 기준으로 한줄에 최대 80글자를 출력하고 줄바꿈(`\n`) 하는 코드를 작성해주세요.
    한글은 영문 2글자로 인식합니다.
    영문기준 최대 80자로 한글이 포함된 경우 79자까지 표현할 수 있습니다. (81자 X)
    새로운 줄의 첫글자는 빈칸이 될 수 없습니다.
 */
public class Test4 {

    public static StringBuffer solution(String text){
        StringBuffer result = new StringBuffer();
        text.split("");

        int rowLength = 0;
        char tempC;

        for(int i=0; i<text.length(); i++){
            tempC = text.charAt(i);
            if((tempC >= 'A' && tempC <= 'Z') || (tempC >= 'a' && tempC <= 'z')){
                if(rowLength+1 > 80){
                    result.append("\n");
                    result.append(tempC);
                    rowLength = 1;
                } else {
                    result.append(tempC);
                    rowLength++;
                }
            } else if(tempC >= 0xAC00 && tempC <= 0xD7A3) {
                if(rowLength+2 > 80){
                    result.append("\n");
                    result.append(tempC);
                    rowLength = 2;
                } else {
                    result.append(tempC);
                    rowLength+=2;
                }
            } else {
                if(rowLength+1 > 80){
                    if(tempC != ' '){
                        result.append("\n");
                        result.append(tempC);
                        rowLength++;
                    }
                } else {
                    result.append(tempC);
                    rowLength++;
                }
            }
        }

        return result;

    }


    public static void main(String[] args) {
        String text = "이 글은 도커에 대해 1도 모르는 시스템 관리자나 서버 개발자를 대상으로 도커 전반에 대해 얕고 넓은 지식을 담고 있습니다. 도커가 등장한 배경과 도커의 역사, 그리고 도커의 핵심 개념인 컨테이너와 이미지에 대해 알아보고 실제로 도커를 설치하고 컨테이너를 실행해 보도록 하겠습니다.";

        System.out.println(solution(text));
    }
}
