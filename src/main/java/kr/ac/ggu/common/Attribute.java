package kr.ac.ggu.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Attribute {
    public static final int MODE_DOC_TITLE = 1;
    public static final int MODE_DOC_NUMBER = 2;
    public static final int MODE_SINGLE_LINE = 0;
    public static final int MODE_MULTIPLE_LINE = 4;

    public boolean debug = false;

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

    public String RENAME_FORMAT;
    public String RENAME_FORMAT_PARAM_0;
    public String RENAME_FORMAT_PARAM_1;
    public String RENAME_FORMAT_PARAM_2;

    public String MKDIR_FORMAT;
    public String MKDIR_FORMAT_PARAM_0;
    public String MKDIR_FORMAT_PARAM_1;

    private Attribute() {

        try {
            FileInputStream fileInputStream = new FileInputStream("attribute.properties");

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

            RENAME_FORMAT = properties.getProperty("RENAME_FORMAT");
            RENAME_FORMAT_PARAM_0 = properties.getProperty("RENAME_FORMAT_PARAM_0");
            RENAME_FORMAT_PARAM_1 = properties.getProperty("RENAME_FORMAT_PARAM_1");
            RENAME_FORMAT_PARAM_2 = properties.getProperty("RENAME_FORMAT_PARAM_2");

            MKDIR_FORMAT = properties.getProperty("MKDIR_FORMAT");
            MKDIR_FORMAT_PARAM_0 = properties.getProperty("MKDIR_FORMAT_PARAM_0");
            MKDIR_FORMAT_PARAM_1 = properties.getProperty("MKDIR_FORMAT_PARAM_1");

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class LazyHolder {
        public static final Attribute INSTANCE = new Attribute();
    }

    public static Attribute getInstance() {
        return LazyHolder.INSTANCE;
    }
}
