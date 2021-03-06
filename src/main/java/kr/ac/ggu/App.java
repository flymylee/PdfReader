package kr.ac.ggu;

import kr.ac.ggu.common.Attribute;
import kr.ac.ggu.common.Util;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class App
{
    private static final Attribute attribute = Attribute.getInstance();
    private static final Util util = Util.getInstance();


    private App() {}

    public static void main(String[] args) throws IOException {
        // 디버그 모드 감지 시도
        for(int i=0;i<args.length;i++) {
            if(args[i].trim().equals("--debug")) {
                attribute.debug = true;
                // create an array to hold elements after deletion
                String[] copyArray = new String[args.length - 1];

                // copy elements from original array from beginning till index into copyArray
                System.arraycopy(args, 0, copyArray, 0, i);

                // copy elements from original array from i+1 till end into copyArray
                System.arraycopy(args, i + 1, copyArray, i, args.length - i - 1);

                // display the copied array after deletion
                System.out.println("Array after deleting an element: "
                        + Arrays.toString(copyArray));

            }
        }

        String filename;
        File inputFile;

        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);

            filename = util.inputFilename(scanner);
            inputFile = new File(filename);

            while(!inputFile.isFile() && filename.length() > 0) {
                System.out.println("잘못된 파일(경로)입니다. 다시 입력해주세요.");

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