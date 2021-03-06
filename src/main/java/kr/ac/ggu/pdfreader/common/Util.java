package kr.ac.ggu.pdfreader.common;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Attribute attribute = Attribute.getInstance();
    private static final Cmd cmd = Cmd.getInstance();

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
    private int docReceiveNoInt;
    private String pdfFileName;

    public PrintStream out;
    {
        try {
            out = new PrintStream(System.out, true, attribute.charset.toString());
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
    }

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
     * ??? ???????????? ????????????(This is a method overloading of the above)
     */
    public String regexMatch(String text, String regex) {
        return regexMatch(text, regex, Attribute.MODE_SINGLE_LINE);
    }

    /**
     * ?????? ?????????????????? ???????????? Attribute.codePage ????????? ???????????? ?????????
     */
    public void setCodePage() {
//        regexMatch(cmd.execCommand("chcp"), "");
    }

    /**
     * This will be input the file name
     */
    public String inputFilename(Scanner scanner) {
        System.out.print("PDF ?????? ????????? ???????????????(???: FileName.pdf / 1234 / C:\\Temp\\FileName.pdf): ");

        // ???????????? ???????????????. ???, ????????????(Double Quotation Mark)??? ??????.
        String filename = scanner.nextLine().replaceAll("\\\"", "");

        // ?????? ???????????? ??????. ?????? ???????????? ??????.
        if (filename.length() == 0) {
            System.out.print("Done!");

            // ???????????? ?????? ??? ?????? ??????
            scanner.close();

            System.exit(0);
        }

        // ??????????????? ???????????? ??????
        if (filename.matches("^\\d{1,4}$"))
            filename = getFileNameByNumber(filename);

        return filename;
    }

    /**
     * ??????????????? ???????????? ??????????????? ?????????
     *
     * @param receivedNumber ??????????????????
     */
    public String getFileNameByNumber(String receivedNumber) {
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");

        int currentYear = Integer.parseInt(formatYear.format(System.currentTimeMillis()));
        int currentMonth = Integer.parseInt(formatMonth.format(System.currentTimeMillis()));

        // ?????????(3???~?????? 2???)??? ?????? 1~2?????? ?????? ???????????? 1??? ???
        if (currentMonth < 3)
            currentYear--;

        // ????????? ?????? ??? ??????
        return String.format(attribute.FORMAT_FILE_NAME_BY_NUMBER, String.valueOf(currentYear), receivedNumber);
    }

    /**
     * txt ????????? ???????????? ?????????
     *
     * @param filename ????????? ?????? ??????
     * @param text     ????????? ??????
     * @param charset  ????????????(?????????: UTF-8)
     * @throws IOException
     */
    public void saveFile(String filename, String text, String charset) throws IOException {
        // ?????? ?????? ??????
        File outputFile = new File(filename);

        // Writer ?????? ??????
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile.getPath(), true); // true ????????? ????????? ?????? ????????? ????????? ??????
        OutputStreamWriter outputStreamWriter; // ???????????? ?????? ??????????????? ??????, ????????? UTF-8??? ???????????? ??????
        try {
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, charset);
        } catch(UnsupportedEncodingException unsupportedEncodingException) {
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        }
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        // ???????????? ????????? ??????
        bufferedWriter.write(text);
        bufferedWriter.flush();

        // Writer ?????? ??????
        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();
    }
    /**
     * ??????????????? ?????? ??????, UTF-8 txt ????????? ??????????????? ????????? ????????????
     * @throws IOException
     */
    public void saveFile(String filename, String text) throws IOException {
        saveFile(filename, text, "UTF-8");
    }

    /**
     * ????????? UTF-8??? ??????????????? ?????????
     *
     * @param filename ???????????? ?????? ??????
     * @return ????????? ???????????? ??? String?????? ??????
     * @throws IOException ?????? ?????? ???????????? ??? ???????????? ??????
     */
    public ArrayList<String> loadFile(String filename) throws IOException {
        // Reader ?????? ??????
        FileInputStream fileInputStream = new FileInputStream(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        ArrayList<String> result = new ArrayList<>();

        // ??? ????????? ?????? ??????
        result.add(bufferedReader.readLine());

        // Reader ?????? ??????
        bufferedReader.close();
        inputStreamReader.close();
        fileInputStream.close();

        return result;
    }

    /**
     * PDF ?????? ????????? ???????????? ?????????
     *
     * @param pdfFile ??????????????? ????????? PDF ??????
     * @throws IOException PDF??? ??????????????? ??????????????? ????????? ????????? ??? ???????????? ??????
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

            agencyName = text.split(System.getProperty("line.separator"))[0].trim(); // ?????????
            docName = regexMatch(text, attribute.REGEX_DOCUMENT_NAME).trim(); // ?????? ??????
            if (docName.length() == 0) {
                docName = regexMatch(text, attribute.REGEX_DOCUMENT_NAME_MULTIPLE_INE, Attribute.MODE_DOC_TITLE + Attribute.MODE_MULTIPLE_LINE).trim();
            }
            senderName = regexMatch(text, attribute.REGEX_SENDER_NAME).trim(); // ?????????
            docNo = regexMatch(text, attribute.REGEX_DOCUMENT_ENFORCEMENT_NUMBER).trim(); // ??????????????????
            if (docNo.length() == 0) {
                docNo = regexMatch(text, attribute.REGEX_DOCUMENT_ENFORCEMENT_NUMBER_MULTIPLE_LINE, Attribute.MODE_DOC_NUMBER + Attribute.MODE_MULTIPLE_LINE).trim();
            }
            docRunDay = regexMatch(text, attribute.REGEX_DOCUMENT_ENFORCEMENT_DAY).replaceAll("-", ".").trim(); // ????????????
            docReceiveDay = regexMatch(text, attribute.REGEX_DOCUMENT_RECEIVE_DAY).replaceAll("-", ".").trim(); // ????????????
            docReceiveNo = regexMatch(text, attribute.REGEX_DOCUMENT_RECEIVE_NUMBER).trim(); // ????????????
            try {
                docReceiveNoInt = Integer.parseInt(docReceiveNo);
            } catch (NumberFormatException numberFormatException) {
//                numberFormatException.printStackTrace();
                docReceiveNoInt = 0;
            }

            pdfFileName = pdfFile.getName(); // PDF ?????????(?????? ???)

            // ????????? ????????? ??? ?????? ?????? ????????? ??????
            if (attribute.debug) {
                System.out.println(text);

                System.out.println("_____TEST_____");
                System.out.println("docReceiveDay = " + docReceiveDay);
                System.out.println("agencyName = " + agencyName);
                System.out.println("docNo = " + docNo);
                System.out.println("docName = " + docName);
                System.out.println("_____TEST_____");
            }
            // ????????? ????????? ?????? ????????? ???????????? ?????? ??????
            else {

                // ???????????? ???????????? ??????
                saveFile("result.txt", String.format(attribute.RESULT_FORMAT, runGetter(attribute.RESULT_FORMAT_PARAM_0), runGetter(attribute.RESULT_FORMAT_PARAM_1), runGetter(attribute.RESULT_FORMAT_PARAM_2), runGetter(attribute.RESULT_FORMAT_PARAM_3), runGetter(attribute.RESULT_FORMAT_PARAM_4), runGetter(attribute.RESULT_FORMAT_PARAM_5), runGetter(attribute.RESULT_FORMAT_PARAM_6), runGetter(attribute.RESULT_FORMAT_PARAM_7), runGetter(attribute.RESULT_FORMAT_PARAM_8)));

                // ?????? ?????? ????????? ?????? ????????? ??????
                saveFile("rename.bat", String.format(attribute.RENAME_FORMAT, runGetter(attribute.RENAME_FORMAT_PARAM_0), runGetter(attribute.RENAME_FORMAT_PARAM_1), runGetter(attribute.RENAME_FORMAT_PARAM_2), runGetter(attribute.RENAME_FORMAT_PARAM_3), runGetter(attribute.RENAME_FORMAT_PARAM_4), runGetter(attribute.RENAME_FORMAT_PARAM_5), runGetter(attribute.RENAME_FORMAT_PARAM_6), runGetter(attribute.RENAME_FORMAT_PARAM_7), runGetter(attribute.RENAME_FORMAT_PARAM_8)), "EUC-KR");

                // ????????? ?????? ?????? ????????? ??????
                saveFile("mkdir.bat", String.format(attribute.MKDIR_FORMAT, runGetter(attribute.MKDIR_FORMAT_PARAM_0), runGetter(attribute.MKDIR_FORMAT_PARAM_1), runGetter(attribute.MKDIR_FORMAT_PARAM_2), runGetter(attribute.MKDIR_FORMAT_PARAM_3), runGetter(attribute.MKDIR_FORMAT_PARAM_4), runGetter(attribute.MKDIR_FORMAT_PARAM_5), runGetter(attribute.MKDIR_FORMAT_PARAM_6), runGetter(attribute.MKDIR_FORMAT_PARAM_7), runGetter(attribute.MKDIR_FORMAT_PARAM_8)), "EUC-KR");
            }
        }
    }

// ????????? ????????? ?????? ????????? ????????? ????????? ????????? ????????? ???????????? ?????? ??????
    public Object runGetter(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}