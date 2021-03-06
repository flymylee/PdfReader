# PdfReader

## 1. 소개

1. 전자결재가 도입되지 않은 기관에서의 공문접수 및 서류정리를 위한 프로그램입니다.

2. 자바로 작성되었습니다.

3. 프로그램 실행 위치에 attribute.properties 파일이 있어야합니다.
	1. attribute.properties 파일에 기관별로 서식이 달라도 사용할 수 있도록 매개변수를 정의할 수 있습니다.
  2. 사용자가 정의할 수 있는 매개변수는 정규표현식, String.format, 필드명입니다.
	3. attribute.properties에서 정의하는 매개변수는 다음과 같습니다.

			1. 문서 파일 형식
				- FORMAT_FILE_NAME_BY_NUMBER

			2. 문서 제목 표현식(1줄)
				- REGEX_DOCUMENT_NAME

			3. 문서 제목 표현(2줄)
				- REGEX_DOCUMENT_NAME_MULTIPLE_INE

			4. 발신자
				- REGEX_SENDER_NAME

			5. 문서생산번호
				- REGEX_DOCUMENT_ENFORCEMENT_NUMBER
				- REGEX_DOCUMENT_ENFORCEMENT_NUMBER_MULTIPLE_LINE

			6. 시행일자
				- REGEX_DOCUMENT_ENFORCEMENT_DAY

			7. 접수일자
				- REGEX_DOCUMENT_RECEIVE_DAY

			8. 접수번호
				- REGEX_DOCUMENT_RECEIVE_NUMBER

			9. 문서정보 추출결과 포맷
				- RESULT_FORMAT

			10. RESULT 매개변수
				- RESULT_FORMAT_PARAM_0
				- RESULT_FORMAT_PARAM_1
				- RESULT_FORMAT_PARAM_2
				- RESULT_FORMAT_PARAM_3

			11. 문서 본문 파일명 변경 명령어 포맷
				- RENAME_FORMAT

			12. RENAME 매개변수
				- RENAME_FORMAT_PARAM_0
				- RENAME_FORMAT_PARAM_1
				- RENAME_FORMAT_PARAM_2

			13. 문서별 폴더 생성 명령어 포맷
				- MKDIR_FORMAT

			14. MKDIR 매개변수
				- MKDIR_FORMAT_PARAM_0
				- MKDIR_FORMAT_PARAM_1

## 2. 변경 사항
  - 2021.03.06.(토): 프로젝트 시작

## 3. 라이센스
  - [LICENSE](./LICENSE) 참조

## 4. 오픈소스 소프트웨어

  - [Apache Maven](https://maven.apache.org/) - [[License](http://www.apache.org/licenses/LICENSE-2.0)]
  - [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/) - [[License](http://www.apache.org/licenses/LICENSE-2.0)]
  - [Apache Commons Net](https://commons.apache.org/proper/commons-lang/) - [[License](http://www.apache.org/licenses/LICENSE-2.0)]
  - [Apache PDFBox](https://pdfbox.apache.org/) - [[License](http://www.apache.org/licenses/LICENSE-2.0)]
