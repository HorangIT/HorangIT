# JAVA Naming Convention

* A101팀의 프로젝트를 위한 JAVA Naming Convention입니다.
  * NAVER의 캠퍼스 핵데이 java 코딩 컨벤션을 참고했습니다.  

## 파일 공통

1. 파일 인코딩은 UTF-8 사용
2. 새 줄 문자는 Windows형식의 CRLF 사용

## 이름

1. 식별자에는 **영문/숫자/언더스코어**만 허용

   * 변수명, 클래스명, 메서드명 등에는 영어와 숫자만 사용
   * 상수에는 단어 사이의 구분을 위해 언더스코어(_) 사용

2. **한국어 발음대로 표기 금지**

   * 한국어 **고유명사** 예외

     > 나쁜 예: ```jasan``` (자산)
     >
     > 좋은 예: ```assets``` (자산)

3. **패키지 이름**은 **소문자**로 구성

   * 단어 별 구분을 위해 언더스코어(_)나 대문자를 섞지 않음

     > 나쁜 예: ```com.a101.apiGateway```
     >
     > 좋은 예: ```com.a101.apigateway```

4. **클래스/인터페이스 이름**에 **대문자 카멜표기법 적용**

   * 클래스 이름은 단어의 첫 글자를 대문자로 시작하는 대문자 카멜표기법을 사용 (==파스칼 표기법)

     > 나쁜 예: ```public class reservation```
     >
     > 좋은 예: ```public class Reservation```

5. **클래스 이름에 명사 사용**

   * 클래스 이름은 명사나 명사절로 지음

6. **인터페이스 이름**에 **명사/형용사 사용**

   * 인터페이스의 이름은 명사/명사절 || 형용사/형용사절로 지음

     > ```java
     > public interface RowMapper{}
     > public interface AutoClosable{}
     > ```

7. 테스트 클래스는 **'Test'로 끝남**

   * JUnit등으로 작성한 테스트 코드를 담은 클래스는 **'TEST'를 마지막에 붙임**

     > ```public class WatcherTest {}```

8. **메서드 이름**에 **소문자 카멜표기법 적용**

   * 첫 번째 단어를 소문자로 작성하고, 이어지는 단어의 첫 글자를 대문자로 작성하는 소문자 카멜표기법 사용
   * 테스트 클래스의 메서드 이름에서는 언더스코어 허용

9. **메서드 이름은 동사/전치사로 시작**

   * 기본적으로 동사로 시작

   * 다른 타입으로 전환하는 메서드/빌더 패턴을 구현한 클래스의 메서드에서는 전치사 사용 가능

     > 좋은 예:
     >
     > 동사 사용: ```renderHtml()```
     >
     > 전환메서드의 전치사: ```toString()```
     >
     > Builder패턴 적용한 클래스의 메서드의 전치사:``` withUserId(String id)```

10. **상수는 대문자와 언더스코어로 구성**

    * ```static final```로 선언되어 있는 필드일 때를 상수로 간주

    * 상수 이름은 대문자로 작성, 복합어는 언더스코어를 사용하여 단어 구분

      > ```public final String POSTAL_CODE_EXPRESSION = "POST";```

11. **변수에 소문자 카멜표기법 적용**

    * 상수가 아닌 클래스의 **멤버변수/지역변수/메서드 파라미터**에는 **소문자 카멜표기법** 사용

      > 나쁜 예:
      >
      > ```java
      > private boolean Authorized;
      > private int AccessToken;
      > ```
      >
      > 좋은 예:
      >
      > ```java
      > private boolean authorized;
      > private int accessToken;
      > ```

12. **임시 변수 외에는 1글자 이름 사용 금지**

    * 메서드 블럭 범위 이상의 생명 주기를 가지는 변수에는 1글자 변수 금지
    * 반복문 인덱스나 람다 표현식의 파라미터 등 임시 변수에는 사용 가능

## 선언

1. **제한자 선언의 순서**

   ```public protected private abstract static final transient volatile synchronized native strictfp```

2. **애너테이션 선언 후 새 줄 사용**

   * 클래스, 인터페이스, 메서드, 생성자에 붙는 애너테이션은 선언 후 새 줄을 사용

   * 단, 파라미터가 없는 애너테이션 1개는 같은 줄에 선언할 수 있음

     > ```java
     > @RequestMapping("/guests")
     > public void findGuests() {}
     > ```
     >
     > ```java
     > @Override public void destroy() {}
     > ```

3. **한 줄에 한 문장**

   * 문장이 끝나는 ; 뒤에는 새 줄을 삽입

     > ```java
     > int base = 0;
     > int weight = 23;
     > ```

4. **하나의 선언문에는 하나의 변수**

   * 한 문장에서 하나의 변수만을 다룸

     > ```java
     > int base;
     > int weight;
     > ```

5. 배열에서 대괄호는 **타입 뒤에 선언**

   * 배열 선언에 오는 대괄호는 타입의 바로 뒤에 붙임

     > ```String[] names;```

6. `long`형 값의 마지막에 **대문자**`L`붙이기

   * 소문자 l보다 1과의 차이가 커서 가독성에 좋음

     > `long base = 54342342342342L;`

## 들여쓰기

1. **하드탭** 사용
   * Tab 문자를 사용하여 들여 씀
2. 탭의 크기는 **4개의 스페이스**
3. 블럭 들여쓰기
   * 클래스, 메서드, 제어문 등의 코드 블럭이 생길 때마다 1단계를 더 들여 씀

## 중괄호(Braces)

1. **K&R(Kernighan and Richie Style) 스타일**로 중괄호 선언

   * 클래스 선언, 메서드 선언, 조건/반복문 등의 코드 블럭을 감싸는 중괄호에 적용되는 규칙

   * 중괄호 선언은 K&R스타일을 따름

   * 줄의 마지막에서 시작 중괄호를 쓰고, 새 줄을 삽입

   * 블럭을 마친 후에는 새줄 삽입 후 중괄호를 닫음

     > 나쁜 예:
     >
     > ```java
     > public boolean isValidExpression(String exp)
     > {
     >     ...
     >     return true;
     > }
     > 
     > ```
     >
     > 좋은 예:
     >
     > ```java
     > public boolean isValidExpression(String exp) {
     >     ...
     >     return true;
     > }
     > ```

2. 닫는 중괄호와 같은 줄에 ```else, catch, finally, while 선언```

   * else

   * catch, finally

   * do-while 문에서의 while

   * 위 3개의 키워드는 닫는 중괄호와 같은 줄에 작성

     > 나쁜 예:
     >
     > ```java
     > if (line.startWith(WARNING_PREFIX)) {
     >     return LogPattern.WARN;
     > }
     > else if (line.startWith(DANGER_PREFIX)) {
     >     return LogPattern.DANGER;
     > }
     > else {
     >     return LogPattern.NORMAL;
     > }
     > ```
     >
     > 좋은 예:
     >
     > ```java
     > if (line.startWith(WARNING_PREFIX)) {
     >     return LogPattern.WARN;
     > } else if (line.startWith(DANGER_PREFIX)) {
     >     return LogPattern.NORMAL;
     > } else {
     >     return LogPattern.NORMAL;
     > }
     > ```

3. 빈 블럭에 새 줄 없이 중괄호 닫기 허용

   * 내용이 없는 블럭은 같은 줄에서 중괄호 닫는 것을 허용

     > ```public void close() {}```

4. 조건/반복문에 중괄호 필수 사용

   * 조건/반복문이 한 줄로 끝나더라도 중괄호를 활용함

     > 나쁜 예:
     >
     > ```java
     > if (exp == null) return false;
     > 
     > for (char ch : exp.toCharArray()) if (ch == 0) return false;
     > ```
     >
     > 좋은 예:
     >
     > ```java
     > if (exp == null) {
     >     return false;
     > }
     > 
     > for (char ch : exp.toCharArray()) {
     > 
     >     if (ch == 0) {
     >         return false;
     >     }
     > 
     > }
     > ```

## 줄바꿈

1. 줄바꿈 허용 위치

   * 가독성을 위해 줄을 바꾸는 위치는 다음 중의 하나로 함

   * `extends` 선언 후

   * `implements` 선언 후

   * `throws` 선언 후

   * 시작 소괄호(`(`) 선언 후

   * 콤마(`,`) 후

   * `.` 전

   * 연산자 전

     - `+`, `-`, `*`, `/`, `%`
     - `==`, `!=`, `>=`, `>`,`⇐`, `<`, `&&`, `||`
     - `&`, `|`, `^`, `>>>`, `>>`, `<<`, `?`
     - `instanceof`

     > 좋은 예:
     >
     > ```java
     > public boolen isAbnormalAccess (
     >     User user, AccessLog log) {
     > 
     >     String message = user.getId() + "|" | log.getPrefix()
     >         + "|" + SUFFIX;
     > }
     > ```



# 덧붙임

이후 더 추가할 예정입니다!