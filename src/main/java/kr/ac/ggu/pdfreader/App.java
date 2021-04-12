package kr.ac.ggu.pdfreader;

import kr.ac.ggu.pdfreader.common.Attribute;
import kr.ac.ggu.pdfreader.common.Cmd;
import kr.ac.ggu.pdfreader.common.Util;

import java.io.*;
import java.util.Scanner;

public class App
{
    private static final Attribute attribute = Attribute.getInstance();
    private static final Util util = Util.getInstance();
    private static final Cmd cmd = Cmd.getInstance();

    private App() {}

    public static void main(String[] args) throws IOException {
//        attribute.codePage = Integer.parseInt(RegExUtils.removeAll(cmd.execCommand("chcp"), "[^\\d]"));  // 실행되는 콘솔창의 코드페이지 확인
//        attribute.setCharset();  // 코드페이지에 맞는 캐릭터셋 지정(EUC-KR, UTF-8 중 양자택일)

        // 디버그 모드 감지 시도
        for(int i=0;i<args.length;i++) {
            if(args[i].trim().equals("--debug")) {
                attribute.debug = true;
                break;
            }
        }

        String filename;
        File inputFile;

        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);

            filename = util.inputFilename(scanner);
            inputFile = new File(filename);

            while(!inputFile.isFile() && filename.length() > 0) {

                util.out.println("잘못된 파일(경로)입니다. 다시 입력해주세요.");

                filename = util.inputFilename(scanner);
            }

            scanner.close();
        } else {
            filename = args[0];
        }

        inputFile = new File(filename);

        if(filename.matches(".+\\.pdf"))
            util.extractInfo(inputFile);

//        if(filename.matches(".*list\\.txt")) {
//            // Reader 객체 생성
//            FileInputStream fileInputStream = new FileInputStream(filename);
//            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            // 행 단위로 파일 읽기
//            while ((filename = bufferedReader.readLine()) != null) {
//                inputFile = new File(filename);
//                util.extractInfo(inputFile); // 문서정보 추출
//            }
//
//            // Reader 객체 닫기
//            bufferedReader.close();
//            inputStreamReader.close();
//            fileInputStream.close();
//        }

        System.out.print("Done!");
    }
}