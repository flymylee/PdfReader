package kr.ac.ggu.pdfreader.common;

import sun.nio.cs.ext.ExtendedCharsets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Attribute {
    public static final int MODE_DOC_TITLE = 1;
    public static final int MODE_DOC_NUMBER = 2;
    public static final int MODE_SINGLE_LINE = 0;
    public static final int MODE_MULTIPLE_LINE = 4;

    public boolean debug = false;

    public Charset charset = StandardCharsets.UTF_8;  // 유니코드(UTF-8)를 기본으로 설정
    public int codePage = 65001;  // 유니코드(UTF-8)를 기본으로 설정

    public String FORMAT_FILE_NAME_BY_NUMBER;

    public String REGEX_DOCUMENT_NAME;
    public String REGEX_DOCUMENT_NAME_MULTIPLE_INE;
    public String REGEX_SENDER_NAME;
    public String REGEX_DOCUMENT_ENFORCEMENT_NUMBER;
    public String REGEX_DOCUMENT_ENFORCEMENT_NUMBER_MULTIPLE_LINE;
    public String REGEX_DOCUMENT_ENFORCEMENT_DAY;
    public String REGEX_DOCUMENT_RECEIVE_DAY;
    public String REGEX_DOCUMENT_RECEIVE_NUMBER;

    public String RESULT_FORMAT;
    public String RESULT_FORMAT_PARAM_0;
    public String RESULT_FORMAT_PARAM_1;
    public String RESULT_FORMAT_PARAM_2;
    public String RESULT_FORMAT_PARAM_3;
    public String RESULT_FORMAT_PARAM_4;
    public String RESULT_FORMAT_PARAM_5;
    public String RESULT_FORMAT_PARAM_6;
    public String RESULT_FORMAT_PARAM_7;
    public String RESULT_FORMAT_PARAM_8;

    public String RENAME_FORMAT;
    public String RENAME_FORMAT_PARAM_0;
    public String RENAME_FORMAT_PARAM_1;
    public String RENAME_FORMAT_PARAM_2;
    public String RENAME_FORMAT_PARAM_3;
    public String RENAME_FORMAT_PARAM_4;
    public String RENAME_FORMAT_PARAM_5;
    public String RENAME_FORMAT_PARAM_6;
    public String RENAME_FORMAT_PARAM_7;
    public String RENAME_FORMAT_PARAM_8;

    public String MKDIR_FORMAT;
    public String MKDIR_FORMAT_PARAM_0;
    public String MKDIR_FORMAT_PARAM_1;
    public String MKDIR_FORMAT_PARAM_2;
    public String MKDIR_FORMAT_PARAM_3;
    public String MKDIR_FORMAT_PARAM_4;
    public String MKDIR_FORMAT_PARAM_5;
    public String MKDIR_FORMAT_PARAM_6;
    public String MKDIR_FORMAT_PARAM_7;
    public String MKDIR_FORMAT_PARAM_8;

    private final Cmd cmd = Cmd.getInstance();

    private Attribute() {
//        codePage = Integer.parseInt(RegExUtils.removeAll(cmd.execCommand("chcp"), "[^\\d]"));  // 실행되는 콘솔창의 코드페이지 확인
//        setCharset();  // 코드페이지에 맞는 캐릭터셋 지정(EUC-KR, UTF-8 중 양자택일)
//
//        cmd.execCommand("chcp 65001");

        try {
            FileInputStream fileInputStream = new FileInputStream("pdfreader_attribute.properties");

            Properties properties = new Properties();
            properties.load(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));

            FORMAT_FILE_NAME_BY_NUMBER = properties.getProperty("FORMAT_FILE_NAME_BY_NUMBER");
            REGEX_DOCUMENT_NAME = properties.getProperty("REGEX_DOCUMENT_NAME");
            REGEX_DOCUMENT_NAME_MULTIPLE_INE = properties.getProperty("REGEX_DOCUMENT_NAME_MULTIPLE_INE");
            REGEX_SENDER_NAME = properties.getProperty("REGEX_SENDER_NAME");
            REGEX_DOCUMENT_ENFORCEMENT_NUMBER = properties.getProperty("REGEX_DOCUMENT_ENFORCEMENT_NUMBER");
            REGEX_DOCUMENT_ENFORCEMENT_NUMBER_MULTIPLE_LINE = properties.getProperty("REGEX_DOCUMENT_ENFORCEMENT_NUMBER_MULTIPLE_LINE");
            REGEX_DOCUMENT_ENFORCEMENT_DAY = properties.getProperty("REGEX_DOCUMENT_ENFORCEMENT_DAY");
            REGEX_DOCUMENT_RECEIVE_DAY = properties.getProperty("REGEX_DOCUMENT_RECEIVE_DAY");
            REGEX_DOCUMENT_RECEIVE_NUMBER = properties.getProperty("REGEX_DOCUMENT_RECEIVE_NUMBER");

            RESULT_FORMAT = properties.getProperty("RESULT_FORMAT");
            RESULT_FORMAT_PARAM_0 = properties.getProperty("RESULT_FORMAT_PARAM_0");
            RESULT_FORMAT_PARAM_1 = properties.getProperty("RESULT_FORMAT_PARAM_1");
            RESULT_FORMAT_PARAM_2 = properties.getProperty("RESULT_FORMAT_PARAM_2");
            RESULT_FORMAT_PARAM_3 = properties.getProperty("RESULT_FORMAT_PARAM_3");
            RESULT_FORMAT_PARAM_4 = properties.getProperty("RESULT_FORMAT_PARAM_4");
            RESULT_FORMAT_PARAM_5 = properties.getProperty("RESULT_FORMAT_PARAM_5");
            RESULT_FORMAT_PARAM_6 = properties.getProperty("RESULT_FORMAT_PARAM_6");
            RESULT_FORMAT_PARAM_7 = properties.getProperty("RESULT_FORMAT_PARAM_7");
            RESULT_FORMAT_PARAM_8 = properties.getProperty("RESULT_FORMAT_PARAM_8");

            RENAME_FORMAT = properties.getProperty("RENAME_FORMAT");
            RENAME_FORMAT_PARAM_0 = properties.getProperty("RENAME_FORMAT_PARAM_0");
            RENAME_FORMAT_PARAM_1 = properties.getProperty("RENAME_FORMAT_PARAM_1");
            RENAME_FORMAT_PARAM_2 = properties.getProperty("RENAME_FORMAT_PARAM_2");
            RENAME_FORMAT_PARAM_3 = properties.getProperty("RENAME_FORMAT_PARAM_3");
            RENAME_FORMAT_PARAM_4 = properties.getProperty("RENAME_FORMAT_PARAM_4");
            RENAME_FORMAT_PARAM_5 = properties.getProperty("RENAME_FORMAT_PARAM_5");
            RENAME_FORMAT_PARAM_6 = properties.getProperty("RENAME_FORMAT_PARAM_6");
            RENAME_FORMAT_PARAM_7 = properties.getProperty("RENAME_FORMAT_PARAM_7");
            RENAME_FORMAT_PARAM_8 = properties.getProperty("RENAME_FORMAT_PARAM_8");

            MKDIR_FORMAT = properties.getProperty("MKDIR_FORMAT");
            MKDIR_FORMAT_PARAM_0 = properties.getProperty("MKDIR_FORMAT_PARAM_0");
            MKDIR_FORMAT_PARAM_1 = properties.getProperty("MKDIR_FORMAT_PARAM_1");
            MKDIR_FORMAT_PARAM_2 = properties.getProperty("MKDIR_FORMAT_PARAM_2");
            MKDIR_FORMAT_PARAM_3 = properties.getProperty("MKDIR_FORMAT_PARAM_3");
            MKDIR_FORMAT_PARAM_4 = properties.getProperty("MKDIR_FORMAT_PARAM_4");
            MKDIR_FORMAT_PARAM_5 = properties.getProperty("MKDIR_FORMAT_PARAM_5");
            MKDIR_FORMAT_PARAM_6 = properties.getProperty("MKDIR_FORMAT_PARAM_6");
            MKDIR_FORMAT_PARAM_7 = properties.getProperty("MKDIR_FORMAT_PARAM_7");
            MKDIR_FORMAT_PARAM_8 = properties.getProperty("MKDIR_FORMAT_PARAM_8");

            fileInputStream.close();
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("pdfreader_attribute.properties 파일을 찾을 수 없습니다.");

            // 문서 파일 형식
            FORMAT_FILE_NAME_BY_NUMBER = "7004274-금강대 %s-%s.pdf";
            // 문서 제목 표현식(1줄)
            REGEX_DOCUMENT_NAME = "\r\n\\x20*제\\x20*목 (.*?)\r\n";
            // 문서 제목 표현(2줄)
            REGEX_DOCUMENT_NAME_MULTIPLE_INE = "\r\n(.+?)\r\n제목\r\n(.+?)\r\n";
            // 발신자
            REGEX_SENDER_NAME = "\\.\\x20*끝\\x20*\\.\r\n(.*?)\r\n";
            // 문서생산번호
            REGEX_DOCUMENT_ENFORCEMENT_NUMBER = "\r\n시행(.*?)\\(\\x20*\\d{4}[\\-\\/\\.]\\x20*\\d{1,2}[\\-\\/\\.]\\x20*\\d{1,2}\\x20*\\)\\x20*접수";
            REGEX_DOCUMENT_ENFORCEMENT_NUMBER_MULTIPLE_LINE = "\r\n(.+?)\r\n\\x20*시행.*?\\(\\x20*\\d{4}[\\-\\/\\.]\\x20*\\d{1,2}[\\-\\/\\.]\\x20*\\d{1,2}\\x20*\\).*\r\n(.+?)\r\n";
            // 시행일자
            REGEX_DOCUMENT_ENFORCEMENT_DAY = "\r\n시행.*?\\(\\x20*(\\d{4}[\\-\\/\\.]\\x20*\\d{2}[\\-\\/\\.]\\x20*\\d{2})\\x20*\\)";
            // 접수일자
            REGEX_DOCUMENT_RECEIVE_DAY = "접수 .*? \\((\\d{4}\\-\\d{2}\\-\\d{2})\\)\r\n";
            // 접수번호
            REGEX_DOCUMENT_RECEIVE_NUMBER = "접수 .*?\\-(\\d{1,5}) \\(\\d{4}\\-\\d{2}\\-\\d{2}\\)\r\n";

            // 문서정보 추출결과 포맷
            RESULT_FORMAT = "\t\t%s\t%s\t%s\t%s\t%s\r\n";
            // RESULT 매개변수
            RESULT_FORMAT_PARAM_0 = "agencyName";
            RESULT_FORMAT_PARAM_1 = "docNo";
            RESULT_FORMAT_PARAM_2 = "docRunDay";
            RESULT_FORMAT_PARAM_3 = "docReceiveDay";
            RESULT_FORMAT_PARAM_4 = "docName";
            RESULT_FORMAT_PARAM_5 = "senderName";
            RESULT_FORMAT_PARAM_6 = "docReceiveNo";
            RESULT_FORMAT_PARAM_7 = "docReceiveNoInt";
            RESULT_FORMAT_PARAM_8 = "pdfFileName";

            // 문서 본문 파일명 변경 명령어 포맷
            RENAME_FORMAT = "ren \"%s\" \"%04d) (본문) %s.pdf\"\r\n";
            // RENAME 매개변수
            RENAME_FORMAT_PARAM_0 = "pdfFileName";
            RENAME_FORMAT_PARAM_1 = "docReceiveNoInt";
            RENAME_FORMAT_PARAM_2 = "docName";
            RENAME_FORMAT_PARAM_3 = "agencyName";
            RENAME_FORMAT_PARAM_4 = "docNo";
            RENAME_FORMAT_PARAM_5 = "docRunDay";
            RENAME_FORMAT_PARAM_6 = "docReceiveDay";
            RENAME_FORMAT_PARAM_7 = "senderName";
            RENAME_FORMAT_PARAM_8 = "docReceiveNo";

            // 문서별 폴더 생성 명령어 포맷
            MKDIR_FORMAT = "md \"%04d) %s\"\r\n";
            // MKDIR 매개변수
            MKDIR_FORMAT_PARAM_0 = "docReceiveNoInt";
            MKDIR_FORMAT_PARAM_1 = "docName";
            MKDIR_FORMAT_PARAM_2 = "agencyName";
            MKDIR_FORMAT_PARAM_3 = "docNo";
            MKDIR_FORMAT_PARAM_4 = "docRunDay";
            MKDIR_FORMAT_PARAM_5 = "docReceiveDay";
            MKDIR_FORMAT_PARAM_6 = "senderName";
            MKDIR_FORMAT_PARAM_7 = "docReceiveNo";
            MKDIR_FORMAT_PARAM_8 = "pdfFileName";
        }
    }

    public void setCharset() {
        switch (codePage) {
            case 949:
            case 51949:
                charset = new ExtendedCharsets().charsetForName("EUC-KR");
                break;
            case 65001:
            default:
                charset = StandardCharsets.UTF_8;
        }
    }

    private static class LazyHolder {
        public static final Attribute INSTANCE = new Attribute();
    }

    public static Attribute getInstance() {
        return LazyHolder.INSTANCE;
    }
}
