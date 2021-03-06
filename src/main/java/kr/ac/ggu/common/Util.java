package kr.ac.ggu.common;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Attribute attribute = Attribute.getInstance();

    private static class LazyHolder {
        public static final Util INSTANCE = new Util();
    }

    public static Util getInstance() {
        return Util.LazyHolder.INSTANCE;
    }

    private String agencyName;
    private String docName;
    private String senderName;
    private String docNo;
    private String docRunDay;
    private String docReceiveDay;
    private String docReceiveNo;
    private String pdfFileName;

    /**
     * This will extract the text with regular expression
     *
     * @param text  the original text
     * @param regex regular expression for text extraction from the original text
     * @param mode  is used to distinguish what kind the text is
     * @return the result of text extraction
     */
    public String regexMatch(String text, String regex, int mode) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            switch (mode) {
                case Attribute.MODE_DOC_TITLE + Attribute.MODE_MULTIPLE_LINE:
                    return matcher.group(1) + " " + matcher.group(2);
                case Attribute.MODE_DOC_NUMBER + Attribute.MODE_MULTIPLE_LINE:
                    return matcher.group(1) + matcher.group(2);
                default:
                    return matcher.group(1);
            }
        } else {
            return "";
        }
    }

    /**
     * 위 메소드의 오버로딩(This is a method overloading of the above)
     */
    public String regexMatch(String text, String regex) {
        return regexMatch(text, regex, Attribute.MODE_SINGLE_LINE);
    }

    /**
     * This will be input the file name
     */
    public String inputFilename(Scanner scanner) {
        System.out.print("PDF 파일 이름을 입력하세요(예: FileName.pdf / 1234 / C:\\Temp\\FileName.pdf): ");

        // 파일명을 입력받는다. 단, 큰따옴표(Double Quotation Mark)는 무시.
        String filename = scanner.nextLine().replaceAll("\\\"", "");

        // 그냥 엔터치는 경우. 바로 프로그램 종료.
        if (filename.length() == 0) {
            System.out.print("Done!");

            // 프로그램 종료 전 객체 닫기
            scanner.close();

            System.exit(0);
        }

        // 접수번호만 입력했을 경우
        if (filename.matches("^\\d{1,4}$"))
            filename = getFileNameByNumber(filename);

        return filename;
    }

    /**
     * 접수번호로 파일명을 만들어주는 메소드
     *
     * @param receivedNumber 문서접수번호
     */
    public String getFileNameByNumber(String receivedNumber) {
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");

        int currentYear = Integer.parseInt(formatYear.format(System.currentTimeMillis()));
        int currentMonth = Integer.parseInt(formatMonth.format(System.currentTimeMillis()));

        // 학년도(3월~익년 2월)에 맞춰 1~2월일 경우 연도에서 1을 뺌
        if (currentMonth < 3)
            currentYear--;

        // 파일명 완성 및 반환
        return String.format(attribute.FORMAT_FILE_NAME_BY_NUMBER, String.valueOf(currentYear), receivedNumber);
    }

    /**
     * UTF-8 txt 파일을 저장하는 메소드
     *
     * @param filename 저장할 파일 이름
     * @param text     저장할 내용
     * @throws IOException
     */
    public void saveFile(String filename, String text) throws IOException {
        // 파일 객체 생성
        File outputFile = new File(filename);

        // Writer 객체 생성
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile.getPath(), true); // true 지정시 파일의 기존 내용에 이어서 작성
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        // 파일안에 문자열 쓰기
        bufferedWriter.write(text);
        bufferedWriter.flush();

        // Writer 객체 닫기
        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();
    }

    /**
     * 파일을 UTF-8로 읽어들이는 메소드
     *
     * @param filename 읽어들일 파일 이름
     * @return 파일을 읽어들인 후 String으로 반환
     * @throws IOException 읽기 에러 발생했을 때 발생시킬 예외
     */
    public ArrayList<String> loadFile(String filename) throws IOException {
        // Reader 객체 생성
        FileInputStream fileInputStream = new FileInputStream(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        ArrayList<String> result = new ArrayList<>();

        // 행 단위로 파일 읽기
        result.add(bufferedReader.readLine());

        // Reader 객체 닫기
        bufferedReader.close();
        inputStreamReader.close();
        fileInputStream.close();

        return result;
    }

    /**
     * PDF 문서 정보를 추출하는 메소드
     *
     * @param pdfFile 문서정보를 추출할 PDF 파일
     * @throws IOException PDF를 읽어들이는 과정에서의 오류가 발생할 때 던져지는 예외
     */
    public void extractInfo(File pdfFile) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new IOException("You do not have permission to extract text");
            }

            PDFTextStripper stripper = new PDFTextStripper();

            // This example uses sorting, but in some cases it is more useful to switch it off,
            // e.g. in some files with columns where the PDF content stream respects the
            // column order.
            stripper.setSortByPosition(true);

            // let the magic happen
            String text = stripper.getText(document).trim();

            agencyName = text.split(System.getProperty("line.separator"))[0].trim(); // 기관명
            docName = regexMatch(text, attribute.REGEX_DOCUMENT_NAME).trim(); // 문서 제목
            if (docName.length() == 0) {
                docName = regexMatch(text, attribute.REGEX_DOCUMENT_NAME_MULTIPLE_INE, Attribute.MODE_DOC_TITLE + Attribute.MODE_MULTIPLE_LINE).trim();
            }
            senderName = regexMatch(text, attribute.REGEX_SENDER_NAME).trim(); // 발신자
            docNo = regexMatch(text, attribute.REGEX_DOCUMENT_ENFORCEMENT_NUMBER).trim(); // 문서생산번호
            if (docNo.length() == 0) {
                docNo = regexMatch(text, attribute.REGEX_DOCUMENT_ENFORCEMENT_NUMBER_MULTIPLE_LINE, Attribute.MODE_DOC_NUMBER + Attribute.MODE_MULTIPLE_LINE).trim();
            }
            docRunDay = regexMatch(text, attribute.REGEX_DOCUMENT_ENFORCEMENT_DAY).replaceAll("-", ".").trim(); // 시행일자
            docReceiveDay = regexMatch(text, attribute.REGEX_DOCUMENT_RECEIVE_DAY).replaceAll("-", ".").trim(); // 접수일자
            docReceiveNo = regexMatch(text, attribute.REGEX_DOCUMENT_RECEIVE_NUMBER).trim(); // 접수번호

            pdfFileName = pdfFile.getName(); // PDF 파일명(변경 전)

            // 디버그 모드일 때 문서 정보 콘솔로 출력
            if(attribute.debug) {
                System.out.println(text);

                System.out.println("_____TEST_____");
                System.out.println("docReceiveDay = " + docReceiveDay);
                System.out.println("agencyName = " + agencyName);
                System.out.println("docNo = " + docNo);
                System.out.println("docName = " + docName);
                System.out.println("_____TEST_____");
            }
            // 디버그 모드가 아닐 때에만 실행결과 파일 출력
            else {

                // 문서정보 추출결과 저장
                saveFile("result.txt", String.format(attribute.RESULT_FORMAT, docReceiveDay, agencyName, docNo, docName));

                // 문서 본문 파일명 변경 명령어 생성
                saveFile("rename.bat", String.format(attribute.RENAME_FORMAT, pdfFileName, docReceiveNo, docName));

                // 문서별 폴더 생성 명령어 생성
                saveFile("mkdir.bat", String.format(attribute.MKDIR_FORMAT, docReceiveNo, docName));
            }

            try {
                String resultParam0 = (String) this.getClass().getField(attribute.RESULT_FORMAT_PARAM_0).get(this);
                String resultParam1 = (String) this.getClass().getField(attribute.RESULT_FORMAT_PARAM_1).get(this);
                String resultParam2 = (String) this.getClass().getField(attribute.RESULT_FORMAT_PARAM_2).get(this);
                String resultParam3 = (String) this.getClass().getField(attribute.RESULT_FORMAT_PARAM_3).get(this);
                String renameParam0 = (String) this.getClass().getField(attribute.RENAME_FORMAT_PARAM_0).get(this);
                String renameParam1 = (String) this.getClass().getField(attribute.RENAME_FORMAT_PARAM_1).get(this);
                String renameParam2 = (String) this.getClass().getField(attribute.RENAME_FORMAT_PARAM_2).get(this);
                String mkdirParam0 = (String) this.getClass().getField(attribute.MKDIR_FORMAT_PARAM_0).get(this);
                String mkdirParam1 = (String) this.getClass().getField(attribute.MKDIR_FORMAT_PARAM_1).get(this);
            } catch (NoSuchFieldException | IllegalAccessException noSuchFieldException) {
                noSuchFieldException.printStackTrace();
            }

        }
    }
}